package Domain;

import java.util.ArrayList;

public class Season {

    private static Season season=null;
    private ArrayList<Integer> seasonWeeks;

    /**
     * Gets the Season singleton
     * @return the instance
     */
    public static Season getInstance(){

        if (season==null){
            season = new Season();
            season.updateSeason();
        }

        return season;
    }

    /**
     * Gets all the season weeks
     * @return the season weeks
     */
    public ArrayList<Integer> getSeason(){
        return seasonWeeks;
    }

    /**
     * Updates the seasons
     */
    private void updateSeason(){

        if (seasonWeeks==null){
            seasonWeeks = new ArrayList<>();
        } else if (seasonWeeks.size()>0){
            seasonWeeks.clear();
        }

        // ADD SEASONS (HardCoded but can be made with DB)
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