package edu.csu2017sp314.DTR14.tripco.Presenter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import edu.csu2017sp314.DTR14.tripco.Presenter.Presenter;

public class PresenterTest {
	
	private ArrayList<File> files;
    private Presenter prez;
    private boolean[] options;
        
    @BeforeClass
	public static void writeTestFile() {
		String testfile = "test.csv";
		try {
			BufferedWriter w = new BufferedWriter(new FileWriter(testfile));
			w.write("name,id,Elevation,Estimated Prominence," 
					+ "latitude,longitude,Quadrangle,Range" 
					+ "\nMount Elbert,1,14433,9093,39.1177,"
					+ "-106.4453,Mount Elbert,Sawatch"
					+ "\nMount Massive,2,14421,1961,39.1875,"
					+ "-106.4756,Mount Massive,Sawatch"
					+ "\nMount Harvard,3,14420,2360,38.9243,"
					+ "-106.3208,Mount Harvard,Sawatch"
					+ "\nBlanca Peak,4,14345,5325,37.5774,"
					+ "-105.4857,Blanca Peak,Sangre de Cristo)");
			w.close();
		} catch (IOException e) {
			System.err.println("Failed to open new file for writing - CSVReaderTest");
			e.printStackTrace();
		}
		try {
			new File("test.svg").createNewFile();
			new File("test.xml").createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
    @Before
    public void initObjects() {
        files = new ArrayList<File>();
        files.add(new File("test.csv"));
        options = new boolean[4];
        Arrays.fill(options, false);
        prez = new Presenter(options, "test.xml", new String[0]);
    }

	@Test
	public void testConstructor(){
        // test full constructor
		new Presenter(options, "test.xml", new String[0]);
		
	}
	
	// TODO: Create a headless mode (i.e., don't launch a web browser) so Travis can run this test
//	public void testRun() {
////		try{
////			prez.run(false);
////		} catch(IOException anIOException){
////			System.out.println("Error in generating an IOException");
////			System.out.println("We tried to get the webpage to launch without the server");
////            System.out.println("A bandaid yes, but we tried, and it looks like it didn't work");
////            System.out.println("But the XML and svg files should be in the directory with the proper names/data");
////		}
//	}
        
	@AfterClass() 
	public static void deleteTestFile() {
		new File("test.svg").delete();
		new File("test.csv").delete();
		new File("test.xml").delete();
	}
	//TODO
	//LOOK HERE
	
	//Not sure why, but when running through Junit in eclipse it will fail
	//To initalize
	//But when running the main below (which just calls all the methods Junit would)
	//It runs fine and nothings fails
	//I'm not sure, and you may not care I understand that, 
	//But just wanted to put it out there

}
