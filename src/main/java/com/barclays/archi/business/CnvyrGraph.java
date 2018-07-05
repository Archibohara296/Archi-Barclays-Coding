package com.barclays.archi.business;

import java.util.*;

import com.barclays.archi.beans.CnvyrLinks;
import com.barclays.archi.beans.CnvyrNodes;

public class CnvyrGraph implements Graph<CnvyrNodes, CnvyrLinks> {

 
    private Map<CnvyrNodes, List<CnvyrLinks>> neighbors = new HashMap<>();

    private Set<CnvyrNodes> nodes = new HashSet<>();

    private Set<CnvyrLinks> links = new HashSet<>();

  
    public void addNode(CnvyrNodes node) {
        if (!neighbors.containsKey(node)) {
            neighbors.put(node, new ArrayList<CnvyrLinks>());
            nodes.add(node);
        }
    }

    
    public boolean isLink(CnvyrNodes from, CnvyrNodes to) {
        List<CnvyrLinks> links = neighbors.get(from);
        if (links != null && !links.isEmpty()) {
            for (CnvyrLinks conveyorLink : links) {
                if (conveyorLink.getTo().equals(to)) {
                    return true;
                }
            }
        }
        return false;
    }

   
    public void addLink(CnvyrNodes from, CnvyrNodes to, double cost) {
        addNode(from);
        addNode(to);

        if (!isLink(from, to)) {
            CnvyrLinks link = new CnvyrLinks(from, to, cost);
            neighbors.get(from).add(link);
            links.add(link);
        }
    }

    @Override
    public CnvyrLinks getLink(CnvyrNodes source, CnvyrNodes target) {
        List<CnvyrLinks> links = neighbors.get(source);
        for (CnvyrLinks link : links) {
            if (link.getTo().equals(target)) {
                return link;
            }
        }
        return null;
    }


    @Override
    public Iterable<CnvyrNodes> getNodes() {
        return nodes;
    }

    @Override
    public int getOrder() {
        return neighbors.size();
    }

    @Override
    public Iterable<CnvyrLinks> getLinks() {
        return links;
    }

    @Override
    public int getSize() {
        return links.size();
    }

    @Override
    public boolean containsNode(CnvyrNodes conveyorNode) {
        return neighbors.containsKey(conveyorNode);
    }

    @Override
    public boolean containsLink(CnvyrLinks conveyorLink) {
        return links.contains(conveyorLink);
    }

  
    public List<CnvyrNodes> findShortestPath(CnvyrNodes source, CnvyrNodes target) {
        List<CnvyrNodes> shortestPath = new ArrayList<>();

        source.setMinDistance(0D);

        PriorityQueue<CnvyrNodes> vertexQueue = new PriorityQueue<>();

        for (CnvyrNodes vertex : nodes) {
            if (!vertex.equals(source)) {
                vertex.setMinDistance(Double.POSITIVE_INFINITY);
                vertex.setPrevious(null);
            } else {
                vertex = source;
            }
            vertexQueue.add(vertex);
        }

        while (!vertexQueue.isEmpty()) {
            CnvyrNodes u = vertexQueue.poll();

            if (u.equals(target)) {
                while (u.getPrevious() != null) {
                    shortestPath.add(u);
                    u = u.getPrevious();
                }
                break;
            }

            vertexQueue.remove(u);

            List<CnvyrLinks> edges = neighbors.get(u);

            for (CnvyrLinks edge : edges) {
                CnvyrNodes v = edge.getTo();

                double weight = edge.getCost();
                double distanceThroughU = u.getMinDistance() + weight;

                if (distanceThroughU < v.getMinDistance()) {
                    v.setMinDistance(distanceThroughU);
                    v.setPrevious(u);
                    vertexQueue.remove(v);
                    vertexQueue.add(v);
                }
            }
        }

        Collections.reverse(shortestPath);

        return shortestPath;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        for (CnvyrNodes node : neighbors.keySet()) {
            sb.append("\n    " + node.getNodeId().getValue() + " -> " + neighbors.get(node));
        }
        return sb.toString();
    }

    public Map<CnvyrNodes, List<CnvyrLinks>> getNeighbors() {
        return neighbors;
    }

}
