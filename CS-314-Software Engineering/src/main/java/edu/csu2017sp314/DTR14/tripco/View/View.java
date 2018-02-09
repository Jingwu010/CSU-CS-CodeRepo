/*
View.java
Creates and displays viewable files and pages from data provided by the Presenter 
*/

package edu.csu2017sp314.DTR14.tripco.View;

import javax.json.JsonArray;


public class View{
    private final static String WORKDIR = View.class.getProtectionDomain().getCodeSource().getLocation().getPath();
    private final static String FILEPATH = WORKDIR.substring(0, WORKDIR.indexOf("WEB-INF/"));
    // Name of the CSV input file, sans the .csv extension 
	private String title;
    // Optionally display labels on the SVG
    private boolean distances;
    private boolean names;
    private int legCount;
    private boolean ids;
    private boolean miles;
    
    private int totalDistance;
    
    // ItineraryWriter and SVGWriter:
    private ItineraryWriter itinWrite;
    private WorldMapWriter svgWrite;
    private JSONItinerary jsonWrite;

    /*
    View Command Line constructor
    args:
    title - The title chosen 
    SVGFile - The name of the input SVGFile (should be "coloradoMap.svg" for spring 1)
    totalDistance - the total distance of the trip
	labels - whether to display distances, names, and ids on the map or not
    */
    
    public View(String title, int totalDistance, boolean[] labels, boolean miles, int numberLocs){
    	legCount = 0;
    	this.ids = labels[0];
        this.distances = labels[1];
        this.names = labels[2];
    	this.title = title;
        this.totalDistance = totalDistance;
    	String dir = System.getProperty("user.dir") + "/main/resources/";
    	String units;
    	if (miles) {
    		units = "kilometers";
    	} else {
    		units = "miles";
    	}
    	itinWrite = new ItineraryWriter();
    	jsonWrite = new JSONItinerary();
        svgWrite = new WorldMapWriter(FILEPATH + "World3.svg");
    	svgWrite.addTitle(title, "Map Title");
        setFooter();
    }
    
    
    private void setFooter() {
    	if (miles) {
        	svgWrite.addFooter(totalDistance + " kilometers", "mapfooter");
        } else {
        	svgWrite.addFooter(totalDistance + " miles", "mapfooter");
        }
    }
    
    public String getRootName() {
    	return title;
    }
    
    //Called by Input gui, sets options from gui, starts svgwriter, wakes up Presenter thread
    
    /*
     * Add a line to the SVG and a leg to the XML. Automatically converts from geographic coordinates.
     * If any of the command line flags are true, labels will automatically be generated as well
     * args:
     * startLocationLat, startLocationLong - lat and long of the first location of the leg 
     * startLocationName, startLocationID, name and Id of the first location of the leg 
     * endLocationLat, endLocationLong - lat and long of the second location of the leg
     * endLocationName, endLocationID, name and Id of the second location of the leg
     * mileage - the mileage between the two locations
     */
    public String addLeg(double[] coordinates, String startLocationName, String startLocationID,
    						String endLocationName, String endLocationID, int mileage) {
    	String testString = "";
    	svgWrite.newGroup("leg" + Integer.toString(++legCount));
        svgWrite.addWorldLine(coordinates);
        //itinWrite.addLeg(startLocationName, endLocationName, mileage);
        
        if (this.distances) {
        	testString += "m";
            svgWrite.addLineLabel(Integer.toString(mileage), "mileage" 
            		+ Integer.toString(legCount), coordinates);
        }

        if (this.names) {
        	testString += "n";
            svgWrite.addLabel(startLocationName, "loc" + legCount, coordinates);
        }

        if (this.ids) {
        	testString += "i";
            svgWrite.addLabel(startLocationID, "loc" + legCount, coordinates);
        }
        svgWrite.endGroup();
        return testString;
    }
    
    public void addItinLeg(ItineraryLeg il){
    	itinWrite.addDetailedLeg(il);
    	jsonWrite.addLeg(il);
    }

    /*
     * Write the SVG and XML files once all the legs have been added
     */
    public void writeFiles() {
        svgWrite.writeSVG(title + ".svg");
        itinWrite.writeXML(FILEPATH + title + ".xml");
        new GenerateJavascript(getRootName());
    }

    public JsonArray getJsonItinerary() {
        return jsonWrite.getItinerary();
    }
}