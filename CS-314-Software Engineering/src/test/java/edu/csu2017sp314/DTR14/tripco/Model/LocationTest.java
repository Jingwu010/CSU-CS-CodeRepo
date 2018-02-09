package edu.csu2017sp314.DTR14.tripco.Model;

import static org.junit.Assert.*;

import org.junit.*;

public class LocationTest{

	@Test
	public void testConstructor1() {
		
		Location loc = new Location("name1", "134.23", "-424.32", "id1","denver,4231", "city,altitude");

		assertTrue("name not read correctly", loc.getName().equals("name1"));
		assertTrue("latitude not read correctly", loc.getLatitude() == 134.23);
		assertTrue("longitude not read correctly", loc.getLongitude() == -424.32);
		assertTrue("info not read correctly", loc.getExtras().equals("denver,4231"));
		assertTrue("info not read correctly", loc.getTemplate().equals("city,altitude"));
	}

	@Test
	public void testConstructor2() {
		
		Location loc = new Location("name2", "-424.32", "134.23");

		assertTrue("name not read correctly", loc.getName().equals("name2"));
		assertTrue("latitude not read correctly", loc.getLatitude() == -424.32);
		assertTrue("longitude not read correctly", loc.getLongitude() == 134.23);
		assertTrue("info not read correctly", loc.getExtras().equals(""));
		assertTrue("info not read correctly", loc.getTemplate().equals(""));
	}

	@Test
	public void testGetName() {
		
		Location loc = new Location("name1", "134.23", "-424.32", "id1","denver,4231", "city,altitude");

		assertTrue("name not read correctly", loc.getName().equals("name1"));
	}

	@Test
	public void testGetLatitude() {
		
		Location loc = new Location("name1", "134.23", "-424.32", "id2","denver,4231", "city,altitude");

		assertTrue("latitude not read correctly", loc.getLatitude() == 134.23);
	}

	@Test
	public void testGetLongitude() {
		
		Location loc = new Location("name1", "134.23", "-424.32", "id1","denver,4231", "city,altitude");

		assertTrue("longitude not read correctly", loc.getLongitude() == -424.32);
	}

	@Test
	public void testGetInfo() {
		
		Location loc = new Location("name1", "134.23", "-424.32", "id2","denver,4231", "city,altitude");

		assertTrue("info not read correctly", loc.getExtras().equals("denver,4231"));
	}

	@Test
	public void testGetTemplate() {
		
		Location loc = new Location("name1", "134.23", "-424.32", "id3","denver,4231", "city,altitude");

		assertTrue("info not read correctly", loc.getTemplate().equals("city,altitude"));
	}


}