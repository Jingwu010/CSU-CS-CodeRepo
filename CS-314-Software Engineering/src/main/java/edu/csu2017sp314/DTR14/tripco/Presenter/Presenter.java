//DTR-14
//Presenter.java
//Takes ArrayList of files and boolean array for options
package edu.csu2017sp314.DTR14.tripco.Presenter;

import edu.csu2017sp314.DTR14.tripco.Model.Model;
import edu.csu2017sp314.DTR14.tripco.Model.Query;
import edu.csu2017sp314.DTR14.tripco.View.ItineraryLeg;
import edu.csu2017sp314.DTR14.tripco.View.View;
import javax.json.JsonArray;

public class Presenter {

    // boolean[] opt = {_id, _mileage, _name, _unit};
    private boolean[] options;
    private String name;
    private String[] subSet;
    private String unit;
    private Model model;
    private View view;
    private Query query;
    private JsonArray jsonItinerary;
    
    public Presenter(boolean[] options, String Name, String[] sub) {
        this.options = options;
        subSet = sub;
        name = Name;
        if(options[3])
            this.unit="Km";
        else
            this.unit = "Miles";
        query = new Query();
    }
    
    public void run(){
        model = new Model(subSet, options[3]);
        viewHandler(modelHandler());
    }
    private String[][] modelHandler(){
        String[] results = query.planTrip(subSet);
        model.planTrip(true, true, results);
        return model.reteriveTrip();
    }
    
    
    private void viewHandler(String[][] s){
        String[][] route = s;
        String[] total = route[route.length - 1][0].split(",");
        int totalMileage = Integer.parseInt(total[0]);
        boolean[] label = {options[0], options[1], options[2]};
        view = new View(name, totalMileage, label, options[3], route.length);
        viewItin(route);
        viewWriter(route);
    }
    
    private void viewWriter(String[][] route){
        
        for (int i = 0; i < route.length - 1; i++) {
            //Essentials string splitarg0
            //[0]=Accumulated Dist, [1]=name, [2]=lat, [3]=long [4]=id;
            String[] essentials1 = route[i][0].split(",");
            String[] essentials2 = route[i + 1][0].split(",");
            //Leg Distance calculated by difference between accumulated miles
            //First locations should have "0" as accumulated distance
            //System.out.println(Arrays.toString(essentials1));
            int mile = Integer.parseInt(essentials2[0]) - Integer.parseInt(essentials1[0]);

            double[] coordinates = {Double.parseDouble(essentials1[3]), Double.parseDouble(essentials1[2]), 
                    Double.parseDouble(essentials2[3]), Double.parseDouble(essentials2[2])};
            
            view.addLeg(coordinates, essentials1[1], essentials1[4],
                    essentials2[1], essentials2[4],  mile);
            
        }
        //Write the files
        view.writeFiles();
    }
    
    
    //Write itinerary with new detailed legs
    private void viewItin(String[][] route){
        String[] ids = new String[route.length];
        for(int i=0;i<route.length;i++){
            String[] info = route[i][0].split(",");
            ids[i] = info[4];
        }
        String[] message = new Query().przQuery(subSet);
        for(int j=0;j<message.length-1;j++){
             String[] essentials1 = route[j][0].split(",");
             String[] essentials2 = route[j + 1][0].split(",");
             int mile = Integer.parseInt(essentials2[0]) - Integer.parseInt(essentials1[0]);
             ItineraryLeg itinLeg = new ItineraryLeg(message[j].split(","), 
                                        message[j+1].split(","), mile, j+1, unit);
             view.addItinLeg(itinLeg);
        }
        jsonItinerary = view.getJsonItinerary();
    }
    //For Debugging
//    private void printlines(){
//      System.out.println("Filename = " + name);
//      System.out.println("_i = " + options[0]);
//      System.out.println("_m = " + options[1]);
//      System.out.println("_n = " + options[2]);
//      for(int i = 0; i < subSet.length; i++)
//          System.out.println("subSet = " + subSet[i]);
//    }

    public JsonArray getJsonItinerary() {
        return jsonItinerary;
    }
}
