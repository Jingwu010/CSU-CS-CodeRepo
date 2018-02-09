package edu.csu2017sp314.DTR14.tripco.View;

import static org.junit.Assert.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import javafx.application.Application;

public class ViewTest {
	
	private View view;
	private String test;
	private static String dir;
	
	@BeforeClass
	public static void createTestFile() throws IOException {
		BufferedWriter bw = new BufferedWriter(new FileWriter("test.svg"));
		bw.write("<?xml version=\"1.0\" ?>\n<svg\nwidth=\"10\"\nheight=\"10\">\n");
		bw.write("<svg width=\"1066.6073\" height=\"783.0824\" xmlns:svg=\"http://www.w3.org/2000/svg\" xmlns=\"http://www.w3.org/2000/svg\">");
		bw.write("</svg>");
		bw.close();
	}
	
	@BeforeClass
	public static void initRoot(){
		dir = System.getProperty("user.dir");
    	if (dir.contains("src")) {
    		dir = dir + "/main/resources/";
    	} else {
    		dir = dir + "/src/main/resources/";
    	}
	}
	
	
	@Test
	public void testConstructor() {
		
		// test full constructor
		View v1 = new View("hello", 300, new boolean[] {false, false, false}, true, 1);
		assertTrue("rootName not read correctly", v1.getRootName().equals("hello"));

		View v2 = new View("hello.csv", 300, new boolean[] {true, true, true}, false, 1);
		assertTrue(".csv extension not properly removed from rootName", v2.getRootName().equals("hello"));

	}
	
	@Test
	public void testGetRootName() {
		View v3 = new View("helloWorld.csv", 300, new boolean[] {false, false, false}, true, 1);
		assertTrue("getRootName() method not returning correct root name", v3.getRootName().equals("helloWorld"));
	}


	@Test
	public void testAddLeg() {
		view = new View("helloWorld.csv", 300, new boolean[] {false, true, false}, true,1);
		test = view.addLeg(new double[]{40, -108, 39, -107}, "Not Denver", "id1", "Not CO Springs", "id2", 50);
		assertTrue(test.equals("m"));
		view = new View("helloWorld.csv", 300, new boolean[] {true, true, true}, true,1);
		test = view.addLeg(new double[]{40, -108, 39, -107}, "Denver", "id2", "CO Springs", "id3", 50);
		assertTrue(test.equals("mi"));
	}

	@Test
	public void testWriteFiles(){
		View v4 = new View("helloWorld.csv", 300, new boolean[] {false, false, false}, true, 1);
		v4.writeFiles();
		assertTrue(new File(dir + "View.js").exists());
		assertTrue(new File(dir + "helloWorld.xml").exists());
		assertTrue(new File(dir + "helloWorld.svg").exists());
	}
	
	@AfterClass() 
	public static void deleteTestFile() {
		new File("test.svg").delete();
		new File(dir + "View.js").delete();
		new File(dir + "helloWorld.xml").delete();
		new File(dir + "helloWorld.svg").delete();
	}

}
