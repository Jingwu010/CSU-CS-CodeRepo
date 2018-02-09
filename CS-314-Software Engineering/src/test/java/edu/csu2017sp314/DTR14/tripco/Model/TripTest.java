package edu.csu2017sp314.DTR14.tripco.Model;

import static org.junit.Assert.*;

import org.junit.*;

public class TripTest{

	private LocationList locList;

	private ShortestRouteCalculator src;

	@Before
	public void init(){
		locList = new LocationList();
		Location l1 = new Location("Mount Elbert", "39.1177", "-106.4453", "1","Hello", "");
		Location l2 = new Location("Mount Cool", "39", "-106", "4", "Cool", "Weather");
		locList.addLocation(l1);
		locList.addLocation(l2);
		src = new ShortestRouteCalculator(locList, 0, false);
	}
	
	@Test
	public void testConstructor() {
		new Trip(locList, src.getFinalRoute());
	}

	@Test
	public void testCreateTrip() {
		src.findBestNearestNeighbor(false, false);
		Trip trip = new Trip(locList, src.getFinalRoute());
		String tests[][] = trip.createTrip();
		assertTrue("wrong trip", tests[0][0].equals("0,Mount Elbert,39.1177,-106.4453,1"));
		assertTrue("wrong trip", tests[0][1].equals("Hello"));
		assertTrue("wrong trip", tests[0][2].equals(""));

		assertTrue("wrong trip", tests[1][0].equals("25,Mount Cool,39.0,-106.0,4"));
		assertTrue("wrong trip", tests[1][1].equals("Cool"));
		assertTrue("wrong trip", tests[1][2].equals("Weather"));
		
		Trip nonetrip = new Trip(locList, new int[0][0]);
		String testnone[][] = nonetrip.createTrip();
		assertTrue("wrong trip", testnone.length == 0);
	}
}