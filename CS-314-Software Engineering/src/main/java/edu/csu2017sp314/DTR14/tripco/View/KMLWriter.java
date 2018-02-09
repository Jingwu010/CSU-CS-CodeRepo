/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.csu2017sp314.DTR14.tripco.View;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author bcgood
 */
public class KMLWriter {
    // Includes <xml> and <trip> elements:
    ArrayList<String> header;
    // All of the locations added with addLocation method
    ArrayList<String> locs;
    // Contains </trip>
    ArrayList<String> footer;
    // Contains coordinates String for locations, in the order they are added
    //Used to draw LineString for the route in addRoute()
    ArrayList<String> coors;
    
    /*
     * KMLWriter constructor - initialize the XML structure
     */
    public KMLWriter(String name) {
        header = new ArrayList<String>();
        locs = new ArrayList<String>();
        footer = new ArrayList<String>();
        coors = new ArrayList<String>();
        // Add header and footer, including whitespace for nicer formatting when printing
        XMLElement xml = new XMLElement("xml", "version=\"1.0\" encoding=\"UTF-8\"");
        header.add(xml.getStart());
        header.add("\n");
        XMLElement kml = new XMLElement("kml", "xmlns=\"http://www.opengis.net/kml/2.2\"");
        header.add(kml.getStart());
        header.add("\n");
        XMLElement doc = new XMLElement("Document","");
        header.add("\t");
        header.add(doc.getStart());
        header.add("\n");
        header.add("\t\t");
        header.add("<name>"+replaceName(name)+"</name>");
        header.add("\n");
        footer.add("\t");
        footer.add(doc.getEnd());
        footer.add("\n");
        footer.add(kml.getEnd());
        footer.add("\n");//End of file newline
    } 

    //Adds individual locations as Placemarks into the locs list
    public void addLocation(String id, String name, Double lon, Double lat){
        locs.add("\t\t"); //Indent to be within Document tag
        locs.add("<Placemark>");//Open Placemark
        locs.add("\n");
        locs.add("\t\t\t");//Indent to be within Placemaker tag
        locs.add("<name>"+replaceName(id)+"</name>");//Add unique airport id as name tag
        locs.add("\n");
        locs.add("\t\t\t");//Indent to be within Placemaker tag
        locs.add("<description>"+replaceName(name)+"</description>");//Add full airport name as description tag
        locs.add("\n");
        locs.add("\t\t\t");//Indent to be within Placemaker tag
        locs.add("<Point>");//Open Point
        locs.add("\n");
        locs.add("\t\t\t\t");//Indent to be within Point tag
        String coor = Double.toString(lon)+","+Double.toString(lat)+",0";//Make coordinate string, default 0 altitude
        coors.add(coor);//Add to list of coordinates
        locs.add("<coordinates>"+coor+"</coordinates>");//Add coordinate
        locs.add("\n");
        locs.add("\t\t\t");//Indent to be within Placemaker tag
        locs.add("</Point>");
        locs.add("\n");
        locs.add("\t\t");//Indent to be within Document tag
        locs.add("</Placemark>");//Close Placemark
        locs.add("\n");//Newline for next location
    }
    
    public void addRoute(){
        if(coors.isEmpty()){
            return;
        }
        locs.add("\t\t"); //Indent to be within Document tag
        locs.add("<Placemark>");//Open Placemark
        locs.add("\n");
        locs.add("\t\t\t");//Indent to be within Placemaker tag
        locs.add("<name>Route</name>");//Add unique airport id as name tag
        locs.add("\n");
        locs.add("\t\t\t");//Indent to be within Placemaker tag
        locs.add("<description>Route for the trip</description>");//Add full airport name as description tag
        locs.add("\n");
        locs.add("\t\t\t");//Indent to be within Placemaker tag
        locs.add("<LineString>");//Open LineString
        locs.add("\n");
        locs.add("\t\t\t\t");//Indent to be within LineString tag
        locs.add("<extrude>0</extrude>");
        locs.add("\n");
        locs.add("\t\t\t\t");//Indent to be within LineString tag
        locs.add("<tessellate>1</tessellate>");
        locs.add("\n");
        locs.add("\t\t\t\t");//Indent to be within LineString tag
        locs.add("<coordinates> ");
        locs.add(coors.get(0));
        if(coors.size()>1){
            locs.add("\n");
            for(int i=1;i<coors.size();i++){
                locs.add("\t\t\t\t\t");//Indent to be within coordinates tag
                locs.add(coors.get(i));
                locs.add("\n");
            }
            locs.add("\t\t\t\t");//Indent to be within LineString tag
            locs.add("</coordinates>");//Close coordinates
            locs.add("\n");
        }
        else{
            locs.add("</coordinates>");//Close coordinates
            locs.add("\n");
        }
        locs.add("\t\t\t");//Indent to be within Placemaker tag
        locs.add("</LineString>");
        locs.add("\n");
        locs.add("\t\t");//Indent to be within Document tag
        locs.add("</Placemark>");//Close Placemark
        locs.add("\n");//Newline for next location
    }

    public ArrayList<String> writeKML(String filename) {
        // Add all of the XML to a single ArrayList:
        ArrayList<String> data = new ArrayList<String>();
    	data.addAll(header);
    	data.addAll(locs);
    	data.addAll(footer);
    	String loc = System.getProperty("user.dir");
    	if (loc.contains("src")) {
    		loc += "/main/resources/";
    	} else {
    		loc += "/src/main/resources/";
    	}
     
		
      	try {
            // New BufferedWriter with filename of original input file
      		BufferedWriter write = new BufferedWriter(new FileWriter(loc+filename));
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
    
    public static void main(String[] args) {
    	KMLWriter k = new KMLWriter("test");
        ArrayList<String> t = new ArrayList<String>();
        t.add("KDEN,Denver Intl,-104.672996521,39.861698150635");
        t.add("KDAL,Dallas Love Field,-96.85179901123047,32.84709930419922");
        t.add("KDFW,Dallas Fort Worth International Airport,-97.03800201416016,32.89680099487305");
        //t.add();
        for(int i=0;i<t.size();i++){
            String[] blah = t.get(i).split(",");
            k.addLocation(blah[0], blah[1], Double.parseDouble(blah[2]), Double.parseDouble(blah[3]));
        }
        k.addRoute();
        k.writeKML("test.kml");
    }
    
}