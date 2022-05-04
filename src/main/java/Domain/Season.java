package Domain;

import java.util.ArrayList;

public class Season {

    private static Season season=null;
    private ArrayList<Integer> seasonWeeks;

    public static Season getInstance(){

        if (season==null){
            season = new Season();
            season.setSeason();
        }

        return season;
    }

    public ArrayList<Integer> getSeason(){
        return seasonWeeks;
    }

    private void setSeason(){

        if (seasonWeeks==null){
            seasonWeeks = new ArrayList<>();
        } else if (seasonWeeks.size()>0){
            seasonWeeks.clear();
        }

        // ADD SEASONS
        for (int i = 23; i < 30; i++) {
            seasonWeeks.add(i);
        }
        for (int i = 1; i < 7; i++) {
            seasonWeeks.add(i);
        }
        for (int i = 45; i < 51; i++) {
            seasonWeeks.add(i);
        }
    }

}