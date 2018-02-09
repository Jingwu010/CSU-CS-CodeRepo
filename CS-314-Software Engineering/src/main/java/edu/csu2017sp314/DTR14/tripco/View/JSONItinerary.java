package edu.csu2017sp314.DTR14.tripco.View;

import java.math.BigDecimal;
import java.util.ArrayList;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonString;
import javax.json.stream.JsonParser;

public class JSONItinerary {
    
    JsonArray json;
    private int legCount = 1;
    ArrayList<JsonObject> legs;
    
    public JSONItinerary() {
        legs = new ArrayList<JsonObject>();
    }
    
    public void addLeg(ItineraryLeg il) {
        String[] leg = il.getLocation1();
        JsonObject ok = Json.createObjectBuilder()
                .add("num", legCount++)
                .add("id", leg[0])
                .add("name", leg[1])
                .add("latitude", leg[2])
                .add("longitude", leg[3])
                .add("elevation", leg[4])
                .add("municipality", leg[5])
                .add("region", leg[6])
                .add("country", leg[7])
                .add("continent", leg[8])
                .add("airportURL", leg[9])
                .add("regionURL", leg[10])
                .add("countryURL", leg[11])
                .add("mileage", il.getMileage())
                .build();
        legs.add(ok);
    }
    
    private void buildItinerary() {
        JsonArrayBuilder jab = Json.createArrayBuilder();
        int i = 0;
        for (JsonObject ils : legs) {
            
            jab.add(ils);
            i++;
        }
        json = jab.build();
    }
    
    JsonArray getItinerary() {
        buildItinerary();
        return json;
    }
    
    public static void main(String[] args) {
        String[] yall = {
			"KDEN", "Denver International Airport",
			"39.861698150635", "-104.672996521",
			"5431", "Denver", "Colorado", "United States",
			"North America", 
			"http://en.wikipedia.org/wiki/Denver_International_Airport",
			"http://en.wikipedia.org/wiki/Colorado",
			"http://en.wikipedia.org/wiki/United_States"
		};
        String[] yall2 = {
			"WOOO", "Celebration International Airport",
			"49.861698150635", "-106.672996521",
			"5431", "Detroit", "Chicago", "United States",
			"North America", 
			"http://en.wikipedia.org/wiki/Denver_International_Airport",
			"http://en.wikipedia.org/wiki/Colorado",
			"http://en.wikipedia.org/wiki/United_States"
		};
        ItineraryLeg il = new ItineraryLeg(yall, yall2, 0, 1, "miles");
        ItineraryLeg il2 = new ItineraryLeg(yall2, yall, 0, 1, "miles");
        JSONItinerary jsoni = new JSONItinerary();
        jsoni.addLeg(il);
        jsoni.addLeg(il2);
        JsonArray ja = jsoni.getItinerary();
        System.out.println("huh");
        System.out.println(ja);
    }
}
