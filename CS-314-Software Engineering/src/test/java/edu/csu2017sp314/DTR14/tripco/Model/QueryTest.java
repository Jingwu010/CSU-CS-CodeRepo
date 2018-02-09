package edu.csu2017sp314.DTR14.tripco.Model;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import org.junit.Test;

public class QueryTest {
	
	private static final Query query = new Query();

	@Test
	public void testContinent2countries(){
		String contient = "NA";
		String ans = query.continent2countries(contient);
		assertThat(ans, notNullValue() );
		assertThat(ans, not(containsString("Asia")));
		assertThat(ans, containsString("Antigua and Barbuda"));
	}
	
	@Test
	public void testCountry2regions(){
		String country = "CN";
		String ans = query.country2regions(country);
		assertThat(ans, notNullValue() );
		assertThat(ans, not(containsString("Colorado")));
		assertThat(ans, not(containsString("New Mexico")));
		assertThat(ans, containsString("Tianjin Municipality"));
		assertThat(ans, containsString("Inner Mongolia Autonomous Region"));
	}
	
	
	@Test
	public void testRegion2airports(){
		String type = "heliport";
		String region = "US-CO";
		String[] ans = query.region2airports(region, type);
		assertThat(ans[0], notNullValue() );
		assertThat(ans[1], notNullValue() );
		assertThat(ans[0], not(containsString("1CD1")));
		assertThat(ans[1], not(containsString("Reed Airport")));
		assertThat(ans[0], containsString("CO46"));
		assertThat(ans[1], containsString("Mercy Regional Medical Center Heliport"));
		assertThat(ans[0], containsString("CO33"));
		assertThat(ans[1], containsString("Cheyenne Mountain Heliport"));
	}
	
	@Test
	public void testInitQuery(){
		String[] ans = query.initQuery();
		assertThat(ans[0], notNullValue() );
		assertThat(ans[1], notNullValue() );
		assertThat(ans[2], notNullValue() );
		
		assertThat(ans[0], containsString("small_airport"));
		assertThat(ans[0], containsString("large_airport"));
		assertThat(ans[0], containsString("heliport"));
		
		assertThat(ans[1], containsString("Asia"));
		assertThat(ans[1], containsString("Europe"));
		assertThat(ans[1], containsString("Oceania"));
		
		assertThat(ans[2], containsString("Saint Helena"));
		assertThat(ans[2], containsString("Burkina Faso"));
		assertThat(ans[2], containsString("Hong Kong"));
		
	}
	
	@Test
	public void testPlanTrip(){
		String[] ids = {"ZBNY", "K1V8", "US-0029", "6VG8", "CHH2"};
		query.planTrip(ids);
 	}
	
	@Test
	public void testPrzQuery(){
		String[] ids = {"ZBNY", "K1V8", "US-0029", "6VG8", "CHH2"};
		String[] ans = query.przQuery(ids);
		
		assertThat(ans, notNullValue() );
		assertThat(ans.length, is(5));
		
		assertThat(ans[0], startsWith("ZBNY"));
		assertThat(ans[2], startsWith("US-0029"));
		assertThat(ans[4], startsWith("CHH2"));
		
		assertThat(ans[1], containsString("North America"));
		assertThat(ans[3], containsString("Longview Heliport"));
		
		assertThat(ans[2], containsString("40.216599"));
		assertThat(ans[4], containsString("Burlington"));
	}
	
	@Test
	public void testSearchQuery(){
		String q = "Denver";
		String[] ans = query.searchQuery(q);
		
		assertThat(ans[0], notNullValue() );
		assertThat(ans[1], notNullValue() );
		
		assertThat(ans[0], containsString("Denver International Airport"));
		assertThat(ans[0], containsString("Kauffman Heliport"));
		
		assertThat(ans[1], containsString("CO41"));
		assertThat(ans[1], containsString("KAPA"));
		
		assertThat(ans[0], not(containsString("Beijing Shahezhen Air Base")));
		assertThat(ans[1], not(containsString("ZBBB")));
	}
}

