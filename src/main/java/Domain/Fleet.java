package Domain;

import Foundation.DB;

import java.util.ArrayList;

public class Fleet {

    private static Fleet fleet=null;
    private ArrayList<Domain.Camper> campers;

    /**
     * Get the Fleet singleton
     * @return the instance
     */
    public static Fleet getInstance(){

        if (fleet==null){
            fleet = new Fleet();
            fleet.updateCampers();
        }

        return fleet;
    }

    /**
     * Gets all the campers
     * @return the campers
     */
    public ArrayList<Domain.Camper> getCampers(){
        return campers;
    }

    /**
     * Gets a specific camper
     * @param licensePlate to that camper
     * @return the specific camper
     */
    public Camper getCamper(String licensePlate){
        for (Camper c:
             campers) {
            if (c.getLicensePlate().equals(licensePlate)){
                return c;
            }
        }
        return null;
    }

    /**
     * Updates the campers via DB
     */
    private void updateCampers(){

        if (campers==null){
            campers = new ArrayList<>();
        } else if (campers.size()>0){
            campers.clear();
        }

        // Get the campers from the DB
        campers = DB.getCampers();

    }

}

