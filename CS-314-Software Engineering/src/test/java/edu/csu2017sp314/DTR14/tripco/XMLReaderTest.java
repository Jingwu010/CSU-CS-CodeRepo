package edu.csu2017sp314.DTR14.tripco;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import edu.csu2017sp314.DTR14.tripco.XMLReader;

import static org.junit.Assert.assertTrue;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import org.junit.AfterClass;

public class XMLReaderTest {
	
	private XMLReader xmlr;
	
	@BeforeClass
	public static void writeTestFile() throws IOException {
		String testfile = "test.xml";
		try {
			BufferedWriter w = new BufferedWriter(new FileWriter(testfile));
			w.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
					+ "\n<selection >"
					+ "\n<title >temp.xml</title>"
					+ "\n<filename >test.csv</filename>"
		    		+ "\n<destinations>"
		        	+ "\n<id >2</id>"
					+ "\n<id >5</id>"
		    		+ "\n</destinations>"
					+ "\n</selection>");
			w.close();
			w = new BufferedWriter(new FileWriter("testBadXML.xml"));
			w.write("<dependency>"
					+ "\n<groupId>junit</groupId>"
					+ "\n<artifactId>junit</artifactId>"
					+ "\n<version>4.11</version>"
					+ "\n<scope>test</scope>"
					+ "\n</dependency>");
			w.close();
		} catch (IOException e) {
			System.err.println("Failed to open new file for writing - XMLReaderTest");
			e.printStackTrace();
		}
		testfile = "testBadXML.xml";
	}
	
	@Before
	public void initiate(){
		xmlr = new XMLReader();
	}
	
	@Test
	public void TestConStructor() throws FileNotFoundException{
		String[] file = new String[2];
		file[0] = "testBadXML.xml";
		file[1] = "test.xml";
		// test bad xml
		String testArr[] = xmlr.readSelectFile(file[0], new StringBuilder());
		assertTrue(testArr.length == 0);
		// test correct xml
		String indexArr[] = {"2", "5"};
		StringBuilder sb = new StringBuilder();
		testArr = xmlr.readSelectFile(file[1], sb);
		assertTrue(testArr.length == indexArr.length);
		assertTrue(testArr[0].equals(indexArr[0]));
		assertTrue(testArr[1].equals(indexArr[1]));
		assertTrue(sb.toString().equals("test.csv"));
	}

	@AfterClass
	public static void removeTestFile() {
		File f = null;
		f = new File("test.xml");
		f.delete();
		f = new File("testBadXML.xml");
		f.delete();
	}
}