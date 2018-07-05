package com.barclays.archi.beans;

import java.util.Objects;


public final class CnvyrLinks {

    private CnvyrNodes from;
    private CnvyrNodes to;
    private double cost;

    public CnvyrLinks(CnvyrNodes from, CnvyrNodes to, double cost) {
        this.from = from;
        this.to = to;
        this.cost = cost;
    }

    public CnvyrNodes getFrom() {
        return from;
    }

    public CnvyrNodes getTo() {
        return to;
    }

    public double getCost() {
        return cost;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || !(obj instanceof CnvyrNodes)) {
            return false;
        }

        CnvyrLinks other = (CnvyrLinks) obj;

        return (this.from.equals(other.from) && this.to.equals(other.to));
    }

    @Override
    public int hashCode() {
        return Objects.hash(from, to);
    }

    @Override
    public String toString() {
        return "Link [" + from.getNodeId().getValue() + "->" + to.getNodeId().getValue() + " : " + cost + "]";
    }
}
