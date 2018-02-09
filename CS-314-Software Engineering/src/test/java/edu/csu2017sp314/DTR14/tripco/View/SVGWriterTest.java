package edu.csu2017sp314.DTR14.tripco.View;

import static org.junit.Assert.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.junit.Test;

public class SVGWriterTest {

	
	@Test
	public void testMapPoints() {		
		WorldMapWriter w = new WorldMapWriter();
		int[] test = w.mapPoints(-109, -41);
		assertTrue(test[0] == (71 * 4) && test[1] == 131 * 4);
		test = w.mapPoints(109, 41);
		assertTrue(test[0] == 289 * 4 && test[1] == 196);
		test = w.mapPoints(0, 0);
		assertTrue(test[0] == 720 && test[1] == 360);
		test = w.mapPoints(-180, 90);
		assertTrue(test[0] == 0 && test[1] == 0);
		test = w.mapPoints(180, -90);
		assertTrue(test[0] == 1440 && test[1] == 720);
	}
	
	@Test
	public void testAddTitle() {
		WorldMapWriter w = new WorldMapWriter();
		w.addTitle("Test", "test");
		assertTrue(w.content.get(0).contains("font-size=\"24"));
		assertTrue(w.content.get(0).contains("x=\"720"));
		assertTrue(w.content.get(0).contains("y=\"30"));
	}
	
	@Test
	public void testAddFooter() {	
		WorldMapWriter w = new WorldMapWriter();
		w.addFooter("Test", "test");
		assertTrue(w.content.get(0).contains("font-size=\"24"));
		assertTrue(w.content.get(0).contains("x=\"720"));
		assertTrue(w.content.get(0).contains("y=\"700"));
	}

	@Test
	public void testAddLineLabel() {
		WorldMapWriter s = new WorldMapWriter();
		// Label should be halfway between the two points:
		int[] test = s.mapPoints(-105.5, 39);
		s.addLineLabel("Test Label", "testlabel", new double[]{-109, 41, -102, 37});
		assertTrue(s.content.get(0).contains("x=\"" + -105));
		assertTrue(s.content.get(0).contains("y=\"" + 39));
	}
	
	@Test
	public void testAddLabel() {
		WorldMapWriter s = new WorldMapWriter();
		s.addLabel("Test Label", "test id", new double[] {-109, 41});
		int[] test = s.mapPoints(-109, 41);
		System.out.println(s.content.get(0));		
		assertTrue(s.content.get(0).contains("x=\"" + -109));
		assertTrue(s.content.get(0).contains("y=\"" + 41));
		assertTrue(s.content.get(0).contains(">Test Label<"));
	}
	
	@Test
	public void testWriteSVG() {
		WorldMapWriter s = new WorldMapWriter();
		ArrayList<String> testData = s.writeSVG("test0.svg");
		assertTrue(testData.get(0).equals("<?xml version=\"1.0\"?>"));
		assertTrue(testData.get(1).contains("<svg"));
		assertTrue(testData.get(2).equals("</svg>"));
		String loc = System.getProperty("user.dir");
		if (loc.contains("src")) {
			loc += "/main/resources/";
		} else {
			loc += "/src/main/resources/";
		}
		
		File f = new File(loc+"test0.svg");
		f.delete();
	}
	
	@Test
	public void testNewGroup() {
		WorldMapWriter s = new WorldMapWriter();
		s.newGroup("Test group");
		assertTrue(s.content.get(0).equals("<g id=\"Test group\">"));
		assertTrue(s.content.get(1).equals("<title>Test group</title>"));
	}

	@Test
	public void testEndGroup() {
		WorldMapWriter s = new WorldMapWriter();
		s.endGroup();
		assertTrue(s.content.get(0).equals("</g>"));
	}
	
	@Test
	public void testAddWorldLine() {
		WorldMapWriter w = new WorldMapWriter();
		boolean wrap;
		wrap = w.addWorldLine(new double[] {-179, 0, 179, 0});
		assertTrue(wrap);
		assertTrue(w.content.size() == 2);
		wrap = w.addWorldLine(new double[] {-1, 0, 1, 0});
		assertFalse(wrap);
		assertTrue(w.content.size() == 3);
		wrap = w.addWorldLine(new double[] {0, 0, 180.25, 0});
		assertTrue(wrap);
		wrap = w.addWorldLine(new double[] {-180.25, 0, 0, 0});
		assertTrue(wrap);
		wrap = w.addWorldLine(new double[] {-180, 0, 0, 0});
		assertFalse(wrap);
	}

}
