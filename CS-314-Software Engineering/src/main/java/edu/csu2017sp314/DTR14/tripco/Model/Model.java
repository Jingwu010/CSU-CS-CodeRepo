package edu.csu2017sp314.DTR14.tripco.Model;


public class Model{
	
	// args: location list 	
	// # have a reference of location list
	// # a reference of location list used for external interface
	private static LocationList locList = new LocationList();

	private ShortestRouteCalculator src;
	private boolean KM;
	// Constructor 
	// args: csvFileName
	// # build a new location list which store the information from csv file
	// # build a new CSVReader which handle the file and auto add location to the list
	// # calculate a shortestneighborroute
	// Improvement: ShortestRouteCalculator is in process and need improvement
	public Model(String[] subset, boolean km){
		KM=km;
		locList = new LocationList();
	}
	
	public boolean planTrip(boolean run2Opt, boolean run3Opt, String[] selection){
                setLocList(selection);
		src = new ShortestRouteCalculator(locList, 0, KM);
		if (run3Opt) {
			src.findBestNearestNeighbor(true, true);
		} else if (run2Opt) {
			src.findBestNearestNeighbor(true, false);
		} else {
			src.findBestNearestNeighbor(false, false);
		}
        return true;
	}

	public String[][] reteriveTrip(){
		Trip trip = new Trip(locList, src.getFinalRoute());
		return trip.createTrip();
	}
	
	//Set Location list from DB data for subset
	public static void setLocList(String[] subset){
		String [] titles = "id,name,longitude,latitude".split(",");
		for(int i=0;i<subset.length;i++){
			locList.lineHandler(subset[i], titles, new String[0]);
		}
	}
}