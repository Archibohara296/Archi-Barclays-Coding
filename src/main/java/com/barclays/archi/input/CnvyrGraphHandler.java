package com.barclays.archi.input;

import com.barclays.archi.beans.CnvyrNodes;
import com.barclays.archi.business.CnvyrGraph;
import com.barclays.archi.constants.Gate;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


public class CnvyrGraphHandler implements InputHandler {

    private CnvyrGraph conveyorGraph;

    private Map<Gate, CnvyrNodes> gateNodeMap = new HashMap<>();

    @Override
    public void process() throws Exception {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("input.txt").getFile());

        Scanner scanner = null;
        try {
            scanner = new Scanner(file);
            boolean startGraphSection = false;
            boolean endGraphSection = false;

            while (scanner.hasNextLine() && !endGraphSection) {
                String line = scanner.nextLine();

                if (line.trim().equals("")){
                    continue;
                }

                if (line.startsWith("# Section:")) { //start of a new section
                    if (!line.endsWith("Conveyor System")) { //skip
                        startGraphSection = false;
                        continue;
                    } else if (line.endsWith("Conveyor System")) {
                        startGraphSection = true;
                        conveyorGraph = new CnvyrGraph();
                        continue;
                    } else if (startGraphSection && !line.endsWith("Conveyor System")) {
                        endGraphSection = true;
                    }
                }

                if (startGraphSection && !endGraphSection) {
                    //format :: <Node 1> <Node 2> <travel_time>
                    String tokens[] = line.split(" ");
                    if (tokens.length != 3) {
                        throw new IOException("BAD INPUT FORMAT...");
                    }
                    String from = tokens[0];
                    String to = tokens[1];
                    int cost = Integer.parseInt(tokens[2]);

                    Gate fromGate = Gate.getGate(from);
                    Gate toGate = Gate.getGate(to);

                    if (fromGate == null || toGate == null) {
                        throw new IOException("INVALID GATE FOUND...");
                    }

                    
                    conveyorGraph.addLink(createNode(fromGate, gateNodeMap), createNode(toGate, gateNodeMap), cost);
                    conveyorGraph.addLink(createNode(toGate, gateNodeMap), createNode(fromGate, gateNodeMap), cost);
                }

            }
        } finally {
            scanner.close();
        }
    }

    public CnvyrGraph getConveyorGraph() {
        return conveyorGraph;
    }

    public void setConveyorGraph(CnvyrGraph conveyorGraph) {
        this.conveyorGraph = conveyorGraph;
    }

    private CnvyrNodes createNode(Gate gate, Map<Gate, CnvyrNodes> nodeMap) {
        if (nodeMap.containsKey(gate)) {
            return nodeMap.get(gate);
        }
        CnvyrNodes conveyorNode = new CnvyrNodes(gate, gate.getValue());
        nodeMap.put(gate, conveyorNode);
        return conveyorNode;
    }
}
