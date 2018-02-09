package edu.csu2017sp314.DTR14.tripco.Model;

public class Trip{

	// args: locList 		
	// # an array list store a list of locations
	private LocationList locList;

	// args: route matrix - 2D
	// # the first column store the route
	// # the second column store the accumulated dis
	private int[][] route;

	protected Trip(LocationList locList, int[][] route){
		this.locList = locList;
		this.route = route;
	}

	protected String[][] createTrip(){
		String[][] strings = new String[route.length][3];
		int cnt = 0;
		for(int i = 0; i < route.length; i++){
			String[] news = new String[3];
			news[0] = news[1] = news[2] = "";
			news[0] += Integer.toString(route[i][1]) + ",";
			news[0] += locList.get(route[i][0]).getName() + ",";
			news[0] += Double.toString(locList.get(route[i][0]).getLatitude()) + ",";
			news[0] += Double.toString(locList.get(route[i][0]).getLongitude()) + ",";
			news[0] += locList.get(route[i][0]).getIdt();
			news[1] += locList.get(route[i][0]).getExtras();
			news[2] += locList.get(route[i][0]).getTemplate();
			strings[cnt++] = news;
		}
		return strings;
	}

}