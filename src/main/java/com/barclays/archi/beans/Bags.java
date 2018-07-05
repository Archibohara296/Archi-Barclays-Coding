package com.barclays.archi.beans;

import com.barclays.archi.constants.Gate;

public class Bags {
    private String id;
    private Gate entryPoint;
    private String flightId;

    public Bags(){

    }

    public Bags(String bagId, Gate entryPoint, String flightId){
        this.id = bagId;
        this.entryPoint = entryPoint;
        this.flightId = flightId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Gate getEntryPoint() {
        return entryPoint;
    }

    public void setEntryPoint(Gate entryPoint) {
        this.entryPoint = entryPoint;
    }

    public String getFlightId() {
        return flightId;
    }

    public void setFlightId(String flightId) {
        this.flightId = flightId;
    }

    @Override
    public String toString() {
        return "Bag{" +
                "id='" + id + '\'' +
                ", entryPoint=" + entryPoint +
                ", flightId='" + flightId + '\'' +
                '}';
    }
}
