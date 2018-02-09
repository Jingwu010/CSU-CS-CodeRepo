package edu.csu2017sp314.DTR14.tripco.View;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class HTMLItinerary {

	private ArrayList<String> header;
	private ArrayList<String> footer;
	private ArrayList<String> content;
	private int size;
	private int leg;
	String units;
	
	
	public HTMLItinerary(int size, String file1, String file2, String units) {
		leg = 1;
		this.units = units;
		header = new ArrayList<String>();
		footer = new ArrayList<String>();
		content = new ArrayList<String>();
		this.size = size;
		readBase(file1, file2);
		initTopology();
	}
	
	private void readBase(String file1, String file2) {
		try {
			String line = "";
			BufferedReader br = new BufferedReader(new FileReader(file1));
			while ((line = br.readLine()) != null) {
				header.add(line);
			}
			br.close();
			br = new BufferedReader(new FileReader(file2));
			while ((line = br.readLine()) != null) {
				footer.add(line);
			}
			br.close();
			
		} catch (IOException e2) {
			System.err.println("HTML itin: File not read correctly");
		} 
	}
	
	private void initTopology() {
		String init = "[{colorIndex: 'graph-1', ids: [";
		for (int i = 1; i < size; i++) {
			init += "'leg" + i + "', ";
		}
		init += "'leg" + size + "']}]}>";
		content.add(init);
	}
	
	protected void addLeg(ItineraryLeg l) {
		if (leg == 1) {
			insertNewLeg(l.getLocation1(), l.getMileage());
			addMileageHeader(l.getMileage());
		}
		insertNewLeg(l.getLocation2(), l.getMileage());
		if (leg != size) {
			addMileageHeader(l.getMileage());
		}
	}
	
	private void addMileageHeader(int mileage) {
		String head = "<Heading tag='h3' align='center'>"
				+ "Travel " + mileage + " " + units
				+ "</Heading><br />";
		content.add(head);
	}
	
	/*
	 * 
	 * <Topology.Parts direction="row">
                    <Topology.Part id='leg1' status='ok' label='1' align='center' />
                    <Topology.Parts direction="column">
                        <Topology.Part id="leg1loc" status="unknown" justify="start"
                            align="center">
                            <Paragraph size="large">
                                <b>Airport:</b> Denver International Airport
                            </Paragraph>
                        </Topology.Part>
                        <Topology.Part id="leg1lat" status="unknown" justify="start"
                            align="center">
                            <Paragraph size="large">
                                <b>Latitude:</b> 39.8561° N
                            </Paragraph>
                        </Topology.Part>
                        <Topology.Part id="leg1lon" status="unknown" justify="start"
                            align="center">
                            <Paragraph size="large">
                                <b>Longitude:</b> 104.6737° W
                            </Paragraph>
                        </Topology.Part>
                    </Topology.Parts>
                </Topology.Parts>
                <br />
                <br />
	 */
	
	private void insertNewLeg(String[] location, int mileage) {
		content.add("<Topology.Parts direction='row'><Topology.Part id='leg"
				+ leg + "' status='ok' label='" + leg + "' align='center' />"
				+ "<Topology.Parts direction='column'>");
		content.add(getAirportInfo(location));
		content.add(getLocationInfo(location));
		content.add(getCoordinatesInfo(location));
		content.add("</Topology.Parts></Topology.Parts><br />");
		//if (leg == size) {
		//	content.add("<Heading tag='h3' align='center'>"
		//			+ "Travel " + mileage + " " + units + " to:</Heading>");
		//}
	}
	
	private String getAirportInfo(String[] location) {
		String airport = "<Topology.Part id='leg" + leg + "port' status='unknown' "
				+ "justify='start' align='center' > <Paragraph size='large' margin='none'>"
				+ "<b>Airport: </b> "
				+ "<Anchor href='" + escapeQuotes(location[9]) + "'> " + escapeQuotes(location[1]) + "</Anchor></Paragraph> </Topology.Part>";
		return airport;
	}
	
	
	/*
	 * <Topology.Part id="leg1loc" status="unknown" justify="start" align="center">
                            <Paragraph size="large" margin="none">
                                <b>Location:</b> Municipality, Region, Country, Continent
                            </Paragraph>
                        </Topology.Part>
	 */
	private String getLocationInfo(String[] location) {
		String loc = "<Topology.Part id='" + leg + "loc' status='unknown' justify='start' "
				+ "align='center'><Paragraph size='large' margin='none'><b>Location: </b>"
				+ escapeQuotes(location[5]) + ", " 
				+ "<Anchor href='" + escapeQuotes(location[10]) + "'> " + escapeQuotes(location[6]) + "</Anchor>, " 
				+ "<Anchor href='" + escapeQuotes(location[11]) + "'> " + escapeQuotes(location[7]) + "</Anchor>, " + escapeQuotes(location[8])
				+ "</Paragraph></Topology.Part>";
		return loc;
	}
	
	/*
	 * <Topology.Part id="leg1coor" status="unknown" justify="start"
                            align="center">
                            <Paragraph size="large">
                                <b>Coordinates:</b> 104.6737° W, 39.8561° N
                            </Paragraph>
                        </Topology.Part>
	 */
	private String getCoordinatesInfo(String[] location) {
		String coordinates = "<Topology.Part id='leg" + leg++ + "coor' status='unknown' "
				+ "justify='start' align='center'><Paragraph margin='none' size='large'>"
				+ "<b>Coordinates:</b> ";
		double latitude = Double.parseDouble(location[2]);
		double longitude = Double.parseDouble(location[3]);
		String geo = "";
		if (latitude < 0) {
			geo += -latitude + "° W, ";
		} else {
			geo += latitude + "° E, ";
		}
		if (longitude < 0) {
			geo += -longitude + "° S";
		} else {
			geo += longitude + "° N";
		}
		coordinates += geo + "</Paragraph></Topology.Part>";
		return coordinates;
	}
	
	public void writeHTMLItinerary() {
		content.add("</Topology>");
		String loc = System.getProperty("user.dir");
		if (loc.contains("src")) {
			loc += "/main/resources/";
		} else {
			loc += "/src/main/resources/";
		}
		
    	try {
			// New BufferedWriter with filename of original input file
    		BufferedWriter write = new BufferedWriter(new FileWriter(loc + "itinerary.html"));
			// Write the contents of the original SVG, as well as whatever header elements added
			for (String s: header) 
				write.write(s + "\n");

			// Write the newly added SVG content
    		for (String s : content)
    			write.write(s + "\n");

			// Close the content of the original SVG
    		for (String s: footer) 
    			write.write(s + "\n");
    		
    		write.close();
    		
    	} catch (IOException e) {
    		
    	}
	}
	
	public String escapeQuotes(String loc) {
		if (loc.contains("'")) {
			loc = loc.replaceAll("'", "");
		}
		return loc;
	}
	
	public static void main(String[] args) {
		String dir = System.getProperty("user.dir");
		dir += "/main/resources/";
		String file1 = dir + "base1.html";
		String file2 = dir + "base2.html";
		System.out.println(file1);
		HTMLItinerary h = new HTMLItinerary(2, file1, file2, "miles");
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
		h.addLeg(il);
		System.out.println(h.header);
		System.out.println(h.footer);
		System.out.println(h.content);
		h.writeHTMLItinerary();
	}
	
}