package com.barclays.archi.beans;

import com.barclays.archi.constants.Gate;


public class CnvyrNodes implements Comparable<CnvyrNodes>{

    private Gate nodeId;
    private String nodeName;
    private Double minDistance = Double.POSITIVE_INFINITY;
    private CnvyrNodes previous;

    public CnvyrNodes(Gate nodeId, String nodeName){
        this.nodeId = nodeId;
        this.nodeName = nodeName;
    }

    public Gate getNodeId() {
        return nodeId;
    }

    public String getNodeName() {
        return nodeName;
    }

    public Double getMinDistance() {
        return minDistance;
    }

    public void setMinDistance(Double minDistance) {
        this.minDistance = minDistance;
    }

    public CnvyrNodes getPrevious() {
        return previous;
    }

    public void setPrevious(CnvyrNodes previous) {
        this.previous = previous;
    }

    @Override
    public boolean equals(Object obj){
        if (this == obj){
            return true;
        }

        if (obj == null || !(obj instanceof CnvyrNodes)){
            return false;
        }

        CnvyrNodes other = (CnvyrNodes) obj;

        return (this.nodeId.getValue().equals(other.nodeId.getValue()));
    }

    @Override
    public int hashCode() {
        return nodeId.getValue().hashCode();
    }

    public int compareTo(CnvyrNodes other) {
        return Double.compare(minDistance, other.minDistance);
    }
}
