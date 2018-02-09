package edu.csu2017sp314.DTR14.tripco;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.junit.AfterClass;
import org.junit.Assert;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import edu.csu2017sp314.DTR14.tripco.TripCo;

public class TripCoTest {
	
	private ArrayList<File> files;
    private TripCo tc;
	private boolean[] opts;

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
        files.add(new File("Colorado14ers.csv"));
        tc = new TripCo();
        opts = new boolean[]{false, true, false, false, true, true, false};
    }

	@Test
	public void testConstructor(){
        // test empty constructor, no side effect
        new TripCo();
        
        // test full constructor
	}
	
	@Test
	public void testToString(){
		Assert.assertEquals("TripCo is an interactive Colorado trip planning application", tc.toString());
	}  
    
    @Test
	public void testInitiate(){
        try {
	        tc.initiate();
	        fail("Expected an FileNotFoundException to be thrown");
	        fail("Expected an Exception to be thrown");
	    } catch (FileNotFoundException anFileNotFoundException) {
	        System.out.println("Throwing anFileNotFoundException" );
	        anFileNotFoundException.getStackTrace();
	    } catch (Exception anException){
	    	System.out.println("Throwing anException" );
	    	anException.getStackTrace();
        } 
        
	}
    
    @AfterClass() 
	public static void deleteTestFile() {
		new File("test.svg").delete();
		new File("test.csv").delete();
		new File("test.xml").delete();
	}
}