package edu.csu2017sp314.DTR14.tripco.View;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

public class XMLElementTest {

	@Test
	public void testXMLElementConstructor1() {
		XMLElement svg = new XMLElement("svg", "width=\"300\" height=\"300\"");
		assertTrue(svg.type.equals("svg"));
		assertTrue(svg.attributes.equals(" width=\"300\" height=\"300\""));
	}
	
	@Test
	public void testXMLElementConstructor2() {
		ArrayList<String> s = new ArrayList<String>();
		XMLElement svg = new XMLElement("svg", s);
		assertTrue(svg.type.equals("svg"));
	}

	@Test
	public void testBuildAttributeString() {
		ArrayList<String> s = new ArrayList<String>();
		s.add("width");
		s.add("300");
		s.add("height");
		s.add("300");
		XMLElement svg = new XMLElement("svg", s);
		assertTrue(svg.attributes.equals(" width=\"300\" height=\"300\""));
	}
	
	@Test
	public void testBuildStart1() {
		XMLElement svg = new XMLElement("svg", "width=\"300\" height=\"300\"");
		assertTrue(svg.start.equals("<svg width=\"300\" height=\"300\">"));
	}
	
	@Test
	public void testBuildStart2() {
		XMLElement line = new XMLElement("line", "id=\"leg1\" y2=\"110\" x2=\"500\" y1=\"100\" x1=\"100\" stroke-width=\"3\" stroke=\"#999999\"");
		assertTrue(line.start.equals("<line id=\"leg1\" y2=\"110\" x2=\"500\" y1=\"100\" x1=\"100\" stroke-width=\"3\" stroke=\"#999999\" />"));
	}
	
	@Test
	public void testBuildStart3() {
		XMLElement comment = new XMLElement("comment", "Here is a comment");
		assertTrue(comment.start.equals("<!--  Here is a comment -->"));
	}
	
	@Test
    // Trip trip;
	public void testBuildStart4() {
		XMLElement xml = new XMLElement("xml", "version=\"1.0\"");
		assertTrue(xml.start.equals("<?xml version=\"1.0\"?>"));
	}
	
	@Test
	public void testBuildEnd1() {
		XMLElement svg = new XMLElement("svg", "width=\"300\" height=\"300\"");
		assertTrue(svg.end.equals("</svg>"));
	}
	
	@Test
	public void testBuildEnd2() {
		XMLElement line = new XMLElement("line", "id=\"leg1\" y2=\"110\" x2=\"500\" y1=\"100\" x1=\"100\" stroke-width=\"3\" stroke=\"#999999\"");
		assertTrue(line.end.equals(""));
	}
	
	@Test
	public void testBuildEnd3() {
		XMLElement comment = new XMLElement("comment", "Here is a comment");
		assertTrue(comment.end.equals(""));
	}
	
	@Test
	public void testBuildEnd4() {
		XMLElement xml = new XMLElement("xml", "version=\"1.0\"");
		assertTrue(xml.end.equals(""));
	}
}
