package edu.csu2017sp314.DTR14.tripco.View;

import java.util.ArrayList;
import java.util.Arrays;

public class ItineraryLeg {
	
	private int mileage;
	private String[] head = {
		"id", "name", "latitude", "longitude", "elevation",
		"municipality", "region", "country", "continent",
		"airportURL", "regionURL", "countryURL"
	};
	private String[] loc1;
	private String[] loc2;
	private int sequence;
	
	private ArrayList<XMLElement> location;
	private ArrayList<XMLElement> leg;
	
	private String xml;
	private String units;
	
	/*
	 * ItineraryLeg - Represents a leg of a trip
	 * Used so writing the XML itinerary is easy
	 * args:
	 * location1 - first location of the trip
	 * location2 - second location of the trip
	 * mileage - the mileage of the leg
	 * location string array format is as follows:
	 * ID, name, lat, long, elevation, municipality, region, country,
	 * continent, airport URL, region URL, country URL
	 */
	public ItineraryLeg(String[] location1, String[] location2, int mileage, int sequence, String units) {
		this.mileage = mileage;
		this.loc1 = location1;
		this.loc2 = location2;
		this.sequence = sequence;
		this.units = units;
		
		location = new ArrayList<XMLElement>();
		leg = new ArrayList<XMLElement>();
		
		populateLocations();
		populateLeg();
		createString();
	}
	
	private void populateLocations() {
		
		
		for (int i = 0; i < 12; i++) {
			location.add(new XMLElement(head[i], ""));
		}
	}
	
	private void populateLeg() {
		leg.add(new XMLElement("leg", ""));
		leg.add(new XMLElement("sequence", ""));
		leg.add(new XMLElement("start", ""));
		leg.add(new XMLElement("finish", ""));
		leg.add(new XMLElement("distance", ""));
		leg.add(new XMLElement("units", ""));
	}
	
	private void createString() {
		xml = "";
		addStartOfLeg();
		addLocation(true);
		addLocation(false);
		addEndOfLeg();
	}
	
	private void addStartOfLeg() {
		xml += leg.get(0).getStart() + "\n";
		xml += "\t" + leg.get(1).getStart();
		xml += sequence;
		xml += leg.get(1).getEnd() + "\n";
	}
	
	private void addLocation(boolean start) {
		if (start) {
			xml += "\t" + leg.get(2).getStart() + "\n";
		} else {
			xml += "\t" + leg.get(3).getStart() + "\n";
		}
		
		for (int i = 0; i < 12; i++) {
			xml += "\t\t" + location.get(i).getStart();
			if (start) {
				xml += replaceName(loc1[i]);
			} else {
				xml += replaceName(loc2[i]);
			}
			xml += location.get(i).getEnd() + "\n";
		}
		
		if (start) {
			xml += "\t" + leg.get(2).getEnd() + "\n";
		} else {
			xml += "\t" + leg.get(3).getEnd() + "\n";
		}
	}
	
	private void addEndOfLeg() {
		xml += "\t" + leg.get(4).getStart() + mileage;
		xml += leg.get(4).getEnd() + "\n";
		xml += "\t" + leg.get(5).getStart() + units;
		xml += leg.get(5).getEnd() + "\n";
		xml += leg.get(0).getEnd() + "\n";
	}
	
	@Override
	public String toString() {
		return xml;
	}
	
    private String replaceName(String origin){
    	String temp = origin;
    	temp = temp.replaceAll("&", "&amp;");
    	temp = temp.replaceAll("<", "&lt;");
    	temp = temp.replaceAll(">", "&gt;");
        temp = temp.replaceAll("%", "&#37;");
    	return  temp;
    }
	protected void reset(String[] location1, String[] location2, int mileage, int sequence) {
		loc1 = location1;
		loc2 = location2;
		this.mileage = mileage;
		this.sequence = sequence;
		
		createString();
	}
	
	public String[] getLocation1() {
		return loc1;
	}
	
	public String[] getLocation2() {
		return loc2;
	}
	
	public int getMileage() {
		return mileage;
	}
	
	public static void main(String[] args) {
		String[] location = {
			"KDEN", "Denver International Airport",
			"39.861698150635", "-104.672996521",
			"5431", "Denver", "Colorado", "United States",
			"North America", 
			"http://en.wikipedia.org/wiki/Denver_International_Airport",
			"http://en.wikipedia.org/wiki/Colorado",
			"http://en.wikipedia.org/wiki/United_States"
		};
		ItineraryLeg il = new ItineraryLeg(location, location, 0, 1, "miles");
		System.out.println(il);
		location[11] = "hello0";
		il.reset(location, location, 0, 1);
		System.out.println(il);
	}
}