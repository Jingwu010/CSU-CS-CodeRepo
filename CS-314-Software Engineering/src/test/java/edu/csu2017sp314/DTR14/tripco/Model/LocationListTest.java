package edu.csu2017sp314.DTR14.tripco.Model;

import static org.junit.Assert.*;

import org.junit.*;

public class LocationListTest{

	private LocationList loclist;

	@Before
	public void init(){
		loclist = new LocationList();
	}
	
	@Test
	public void testConstructor() {
		new LocationList();
	}

	@Test
	public void testLineHandler() {
		// Test without selection
		loclist = new LocationList();
		String[] title = {"name", "latitude", "longitude", "zipcode"};
		loclist.lineHandler("ft collins, 2391.2, 9312.3, 80525" , title, new String[0]);
		assertTrue("wrong Handler" , loclist.get(0).getName().equals("ft collins"));
		assertTrue("wrong Handler" , loclist.get(0).getLatitude() == 2391.2);
		assertTrue("wrong Handler" , loclist.get(0).getLongitude() == 9312.3);
		assertTrue("wrong Handler" , loclist.get(0).getExtras().equals("80525"));
		assertTrue("wrong Handler" , loclist.get(0).getTemplate().equals("zipcode"));
		// Test duplicate is not added
		loclist.lineHandler("ft collins, 2391.2, 9312.3, 80525" , title, new String[0]);
		assertTrue(loclist.getsize() == 1);
		// Test add second location
		loclist.lineHandler("ft morgan, 300, 300, 80521" , title, new String[0]);
		assertTrue(loclist.getsize() == 2);
		assertTrue(loclist.get(1).getName().equals("ft morgan"));
		
		// Test with selection
		String[] titleid = {"name", "id", "latitude", "longitude", "other"};
		loclist = new LocationList();
		loclist.lineHandler("A, 0, 30, 30, cool", titleid, new String[]{"1"});
		loclist.lineHandler("B, 1, 40, 40, nice", titleid, new String[]{"1"});
		assertTrue(loclist.get(0).getName().equals("B"));
	}
	
	@Test
	public void testAddLocation(){
		loclist = new LocationList();
		loclist.addLocation(new Location("A", "1", "2", "id1", "A B", "name"));
		assertTrue("wrong add function", loclist.getsize() == 1);
		// Test duplicate is not added
		loclist.addLocation(new Location("A", "1", "2", "id1", "A B", "name"));
		assertTrue("wrong add function", loclist.getsize() == 1);
		loclist.addLocation(new Location("A", "1", "2"));
		assertTrue("wrong add function", loclist.getsize() == 1);
		// Test add another
		loclist.addLocation(new Location("A", "1", "3"));
		assertTrue("wrong add function", loclist.getsize() == 2);
		loclist.addLocation(new Location("A", "2", "2"));
		assertTrue("wrong add function", loclist.getsize() == 3);
		loclist.addLocation(new Location("B", "1", "2"));
		assertTrue("wrong add function", loclist.getsize() == 4);
	}
	
	@Test
	public void testGetsize() {
		
		// change the constance before you test the getsize()!
		loclist = new LocationList();
		loclist.addLocation(new Location("A", "1", "2", "id1", "A B", "name"));
		assertTrue("wrong size", loclist.getsize() == 1);
	}

	@Test
	public void testGet() {
		loclist = new LocationList();
		loclist.addLocation(new Location("A", "1", "2", "A","B C D E F", "id,lat,lon"));
		assertTrue("wrong get", loclist.get(0).getName().equals("A"));
		assertTrue("wrong get", loclist.get(0).getLatitude() == 1);
		assertTrue("wrong get", loclist.get(0).getLongitude() == 2);
		assertTrue("wrong get", loclist.get(0).getExtras().equals("B C D E F"));
		assertTrue("wrong get", loclist.get(0).getTemplate().equals("id,lat,lon"));
	}

}
