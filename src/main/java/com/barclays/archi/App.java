package com.barclays.archi;

import com.barclays.archi.beans.Bags;
import com.barclays.archi.beans.CnvyrNodes;
import com.barclays.archi.beans.Departures;
import com.barclays.archi.business.CnvyrGraph;
import com.barclays.archi.constants.Gate;
import com.barclays.archi.input.BagsHandler;
import com.barclays.archi.input.CnvyrGraphHandler;
import com.barclays.archi.input.DepartureHandler;

import java.util.List;
import java.util.Map;

public class App {

    public static void main(String[] args) {

        CnvyrGraph cg = null;
        Map<String, Departures> flightIdToDepartureMap = null;
        Map<String, Bags> bagIdToBagMap = null;

        
        CnvyrGraphHandler conveyorGraphHandler = new CnvyrGraphHandler();
        try {
            conveyorGraphHandler.process();
            cg = conveyorGraphHandler.getConveyorGraph();
            //System.out.println(conveyorGraph);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        DepartureHandler flightDepartureHandler = new DepartureHandler();
        try {
            flightDepartureHandler.process();
            flightIdToDepartureMap = flightDepartureHandler.getFlightIdToDepartureMap();
            //System.out.println(flightIdToDepartureMap);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        BagsHandler bagHandler = new BagsHandler();
        try {
            bagHandler.process();
            bagIdToBagMap = bagHandler.getBagIdToBagMap();
            //System.out.println(bagIdToBagMap);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        StringBuffer output = new StringBuffer();

        for (Map.Entry<String, Bags> entry : bagIdToBagMap.entrySet()) {
            Bags bag = entry.getValue();
            String bagId = bag.getId();
            String flightId = bag.getFlightId();
            Gate sourceGate = bag.getEntryPoint();

            output.append(bagId + " ");


            Gate departureGate = null;
            if (flightId.equals("ARRIVAL")) {
                departureGate = sourceGate.BAGGAGE_CLAIM;
            } else {
                departureGate = flightIdToDepartureMap.get(flightId).getDepartureGate();
            }

            CnvyrNodes sourceNode = new CnvyrNodes(sourceGate, sourceGate.getValue());
            CnvyrNodes targetNode = new CnvyrNodes(departureGate, departureGate.getValue());
            List<CnvyrNodes> shortestPath = cg.findShortestPath(sourceNode, targetNode);

            if (!shortestPath.isEmpty()) {
                output.append(sourceGate.getValue() + " ");
                CnvyrNodes prevNode = shortestPath.get(0);
                output.append(prevNode.getNodeId().getValue() + " ");

                for (int i = 1; i < shortestPath.size(); i++) {
                    CnvyrNodes current = shortestPath.get(i);
                    prevNode = current;
                    output.append(current.getNodeId().getValue() + " ");
                }
                output.append(": " + prevNode.getMinDistance());
                output.append(System.lineSeparator());
            } else { //PATH NOT FOUND
                output.append("PATH_NOT_EXISTS");
                output.append(System.lineSeparator());
            }
        }

        System.out.println(output.toString());
    }
}
