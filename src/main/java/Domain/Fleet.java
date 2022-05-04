package Domain;

import Foundation.DB;

import java.util.ArrayList;

public class Fleet {

    private static Fleet fleet=null;
    private ArrayList<Domain.Camper> campers;

    public static Fleet getInstance(){

        if (fleet==null){
            fleet = new Fleet();
            fleet.setCampers();
        }

        return fleet;
    }

    public ArrayList<Domain.Camper> getCampers(){
        return campers;
    }

    public Camper getCamper(String licensePlate){
        for (Camper c:
             campers) {
            if (c.getLicensePlate().equals(licensePlate)){
                return c;
            }
        }
        return null;
    }

    private void setCampers(){

        if (campers==null){
            campers = new ArrayList<>();
        } else if (campers.size()>0){
            campers.clear();
        }

        // Get the campers from the DB
        campers = DB.getCampers();

    }

}

