package edu.csu2017sp314.DTR14.tripco.View;

import static org.junit.Assert.assertTrue;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import org.junit.BeforeClass;
import org.junit.Test;

public class ItineraryLegTest {

	private static String w, w2;
	private String[] location = {
			"KDEN", "Denver International Airport",
			"39.861698150635", "-104.672996521",
			"5431", "Denver", "Colorado", "United States",
			"North America", 
			"http://en.wikipedia.org/wiki/Denver_International_Airport",
			"http://en.wikipedia.org/wiki/Colorado",
			"http://en.wikipedia.org/wiki/United_States"
	};
		
	
	@BeforeClass
	public static void createTestFile() {
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
		w = "";
		w2 = "";
		w += ("<leg>\n"); w2 += ("<leg>\n");
		w += ("\t<sequence>1</sequence>\n"); w2 += ("\t<sequence>1</sequence>\n");
		w += ("\t<start>\n"); w2 += ("\t<start>\n");
		w += (inner); w2 += (inner);
		w += ("\t</start>\n\t<finish>\n"); w2 += ("\t</start>\n\t<finish>\n");
		w += (inner); w2 += (inner);
		w += ("\t</finish>\n"); w2 += ("\t</finish>\n");
		w += ("\t<distance>0</distance>\n"); w2 += ("\t<distance>0</distance>\n");
		w += ("\t<units>miles</units>\n"); w2 += ("\t<units>miles</units>\n");
		w += ("</leg>\n"); w2 += ("</leg>\n");
		w2 += ("<leg>\n");
		w2 += ("\t<sequence>2</sequence>\n");
		w2 += ("\t<start>\n");
		w2 += (inner);
		w2 += ("\t</start>\n\t<finish>\n");
		w2 += (inner);
		w2 += ("\t</finish>\n");
		w2 += ("\t<distance>10</distance>\n");
		w2 += ("\t<units>miles</units>\n");
		w2 += ("</leg>\n");
	}
	
	@Test
	public void testToString() {
		ItineraryLeg il = new ItineraryLeg(location, location, 0, 1, "miles");
				
		assertTrue(il.toString().equals(w));
	}
	
	@Test
	public void testReset() {
		ItineraryLeg il = new ItineraryLeg(location, location, 0, 1, "miles");
		String testString = il.toString();
		il.reset(location, location, 10, 2);
		testString += il.toString();
		assertTrue(testString.equals(w2));
	}
	
}
