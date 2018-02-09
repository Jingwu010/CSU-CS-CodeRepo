/*
 * ItineraryWriter - Produce a trip itinerary in XML & HTML/Grommet format
 */

package edu.csu2017sp314.DTR14.tripco.View;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class ItineraryWriter {
    // Includes <xml> and <trip> elements:
    ArrayList<String> header;
    // All of the legs added with addLeg method
    ArrayList<String> legs;
    // Contains </trip>
    ArrayList<String> footer;
    // The number of legs of the trip
    int numLegs;
    private final static String WORKDIR = View.class.getProtectionDomain().getCodeSource().getLocation().getPath();
    // the FILEPATH we want to write to is a few folders up. This refers to the web folder
    private final static String FILEPATH = WORKDIR.substring(0, WORKDIR.indexOf("WEB-INF/"));
    // For HTML versions:
    ArrayList<String> htmlHeader;
    ArrayList<String> htmlLegs;
    ArrayList<String> htmlFooter;
    XMLElement html;
    XMLElement head;
    XMLElement meta;
    XMLElement link;
    XMLElement script;
    XMLElement div;

    /*
     * ItineraryWriter constructor - initialize the XML structure
     */
    public ItineraryWriter() {
        header = new ArrayList<String>();
        legs = new ArrayList<String>();
        footer = new ArrayList<String>();

        // Add header and footer, including whitespace for nicer formatting when printing
        XMLElement xml = new XMLElement("xml", "version=\"1.0\" encoding=\"UTF-8\"");
        header.add(xml.getStart());
        header.add("\n");
        XMLElement trip = new XMLElement("trip", "");
        header.add(trip.getStart());
        header.add("\n");
        footer.add("\n");
        footer.add(trip.getEnd());
    } 

    public void addLeg(ItineraryLeg leg) {
        String newLeg = leg.toString();
        newLeg = replaceName(newLeg);
        legs.add(newLeg);
    }

    /*
     * DEPRECATED METHOD, USE FOR COLORADO MAP ONLY 
     * addLeg - add a leg to the itinerary 
     * args:
     * startLocation - where the leg begins
     * endLocation - where the leg ends (should be the same as the startLocation of the next leg)
     * mileage - the mileage between the two locations
     */
    public ArrayList<String> addLeg(String startLocation, String endLocation, int mileage) {
        // Store the leg
        ArrayList<String> leg = new ArrayList<String>();
    	
        // Create XMLElement objects for each element of a leg
        XMLElement l = new XMLElement("leg", "");
        XMLElement seq = new XMLElement("sequence", "");
        XMLElement start = new XMLElement("start", "");
        XMLElement finish = new XMLElement("finish", "");
        XMLElement miles = new XMLElement("mileage", "");

        // Add the elements, with additional whitespace for nicer formatting
        leg.add("\n");
        leg.add(l.getStart());
        leg.add("\n");
        leg.add(seq.getStart());
        // Increment numLegs: 
        leg.add(Integer.toString(++numLegs));
        leg.add(seq.getEnd());
        leg.add("\n");
        leg.add(start.getStart());
        leg.add(replaceName(startLocation));
        leg.add(start.getEnd());
        leg.add("\n");
        leg.add(finish.getStart());
        leg.add(replaceName(endLocation));
        leg.add(finish.getEnd());
        leg.add("\n");
        leg.add(miles.getStart());
        leg.add(Integer.toString(mileage));
        leg.add(miles.getEnd());
        leg.add("\n");
        leg.add(l.getEnd());
        leg.add("\n");
        legs.addAll(leg);

        // Return the leg (mostly for JUnit)
    	return leg;
    }

    public ArrayList<String> writeXML(String filename) {
        // Add all of the XML to a single ArrayList:
        ArrayList<String> data = new ArrayList<String>();
    	data.addAll(header);
    	data.addAll(legs);
    	data.addAll(footer);
    	String loc = System.getProperty("user.dir");
    	if (loc.contains("src")) {
    		loc += "/main/resources/";
    	} else {
    		loc += "/src/main/resources/";
    	}
     
		
      	try {
            File test = new File(filename);            
            test.createNewFile();

            BufferedWriter write = new BufferedWriter(new FileWriter(test));
            // Loop through and write all of the XML data
            for(String s : data) {
                write.write(s);
            }
            // Close the BufferedWriter
            write.close();
      	} catch (IOException e) {
      		System.out.println("location does not exist");
      	}
        // Return the ArrayList data
     	return data;
    }
    
    private String replaceName(String origin){
    	String temp = origin;
    	temp = temp.replaceAll("&", "&amp;");
    	temp = temp.replaceAll("<", "&lt;");
    	temp = temp.replaceAll(">", "&gt;");
        temp = temp.replaceAll("%", "&#37;");
    	return  temp;
    }
    
    public void addDetailedLeg(ItineraryLeg newLeg) {
    	legs.add(newLeg.toString());
    }
    
    @Override
    public String toString() {
    	return legs.toString();
    }
    
    public static void main(String[] args) {
    	ItineraryWriter w = new ItineraryWriter();
    	
    	w.addLeg("here", "there", 3);
    	w.addLeg("there", "then", 9999);
    	w.writeXML("test.xml");
    }
    
}