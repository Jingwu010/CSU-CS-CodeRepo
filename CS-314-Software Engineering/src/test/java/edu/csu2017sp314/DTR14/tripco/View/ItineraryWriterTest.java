package edu.csu2017sp314.DTR14.tripco.View;

import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

public class ItineraryWriterTest {

	String testString;
	
	@Test
	public void testConstructor() {
		ItineraryWriter w = new ItineraryWriter();
		
		assertTrue(w.header.get(0).equals("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"));
		assertTrue(w.header.get(2).equals("<trip>"));
		assertTrue(w.footer.get(1).equals("</trip>"));
	}

	
	@Test
	public void testNewLeg() {
		ItineraryWriter w = new ItineraryWriter();
		
		ArrayList<String> testData = w.addLeg("here", "there", 3);
		
		assertTrue(testData.get(1).equals("<leg>"));
		assertTrue(testData.get(3).equals("<sequence>"));
		assertTrue(testData.get(4).equals("1"));
		assertTrue(testData.get(5).equals("</sequence>"));
		
		assertTrue(testData.get(7).equals("<start>"));
		assertTrue(testData.get(8).equals("here"));
		assertTrue(testData.get(9).equals("</start>"));		
		
		assertTrue(testData.get(11).equals("<finish>"));
		assertTrue(testData.get(12).equals("there"));
		assertTrue(testData.get(13).equals("</finish>"));
		
		assertTrue(testData.get(15).equals("<mileage>"));
		assertTrue(testData.get(16).equals("3"));
		assertTrue(testData.get(17).equals("</mileage>"));
		assertTrue(testData.get(19).equals("</leg>"));
	}
	
	@Before
	public void createTestString() {
		String inner = "\t\t<id>KDEN</id>\n" 
				+ "\t\t<name>Denver International Airport</name>\n"
				+ "\t\t<latitude>39.861698150635</latitude>\n"
				+ "\t\t<longitude>-104.672996521</longitude>\n"
				+ "\t\t<elevation>5431</elevation>\n"
				+ "\t\t<municipality>Denver</municipality>\n"
				+ "\t\t<region>Colorado</region>\n"
				+ "\t\t<country>United States</country>\n"
				+ "\t\t<continent>North America</continent>\n"
				+ "\t\t<airportURL>http://en.wikipedia.org/wiki/"
				+ "Denver_International_Airport</airportURL>\n" 
				+ "\t\t<regionURL>http://en.wikipedia.org/wiki/Colorado</regionURL>\n"
				+ "\t\t<countryURL>http://en.wikipedia.org/wiki/United_States</countryURL>\n";
		testString = "";
		testString += ("<leg>\n");
		testString += ("\t<sequence>1</sequence>\n");
		testString += ("\t<start>\n"); 
		testString += (inner); 
		testString += ("\t</start>\n\t<finish>\n");
		testString += (inner); 
		testString += ("\t</finish>\n"); 
		testString += ("\t<distance>0</distance>\n");
		testString += ("\t<units>miles</units>\n");
		testString += ("</leg>\n"); 
	}
	
	@Test
	public void testAddDetailedLeg() {
		ItineraryWriter w = new ItineraryWriter();
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
		w.addDetailedLeg(il);
		assertTrue(w.toString().substring(1, w.toString().length() - 1).equals(testString));
	}
	
	@Test
	public void testWriteXML() {
		ItineraryWriter w = new ItineraryWriter();
		
		ArrayList<String> testData = w.writeXML("test0.xml");
		
		assertTrue(testData.contains("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"));
		assertTrue(testData.contains("<trip>"));
		assertTrue(testData.contains("</trip>"));
		String loc = System.getProperty("user.dir");
		if (loc.contains("src")) {
			loc += "/main/resources/";
		} else {
			loc += "/src/main/resources/";
		}
		
		File f = new File(loc+"test0.xml");
		f.delete();
	}
}
