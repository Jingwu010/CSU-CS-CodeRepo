//DTR-14
//TripCo.java
//main method for TripCo project
//Parses arguments and sends them to presenter
package edu.csu2017sp314.DTR14.tripco;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import edu.csu2017sp314.DTR14.tripco.Presenter.Presenter;
import javax.json.JsonArray;

public class TripCo{
	//List of input .csv files
	private ArrayList<File> files;
	//Optional argument booleans
	private String name;			//.svg
	private boolean _id;		//-i
	private boolean _mileage;	//-d
	private boolean _name;		//-n
	private boolean _kilo;
	private String[] subset;
        private JsonArray jsonItinerary;
        
	//Std usage message
	private static void usage(){
		System.out.println("TripCo is a trip planning program that creates the shortest trip from a given list of locations");
		System.out.println("TripCo takes an (absolute if not in directory diretly above src) file path to a .csv file of longitude and lattitude coordinates to construct a trip from");
		System.out.println(".csv Location files should have the first line as a template line with labels for subsequent lines' data");
		System.out.println("Optional arguments:");
		System.out.println("	-i : shows the ID of the locations on the map");
		System.out.println("	-m : Display mileage of legs on map");
		System.out.println("	-n : shows the names of the locations on the map");
		System.out.println("EX: TripCo -mn list.csv");
	}
	
	public TripCo(){
		name = "";
		_id = false;
		_mileage = false;
		_name = false;
		_kilo = false;
		files = new ArrayList<File>();
	}
	
	//	boolean 
	//	[0] = ID, [1] = mileage, [2] = name, [3] = kilo
	public TripCo(String Name, boolean[] opts, String[] subset){
		name = Name;
		this._id = opts[0];
		this._mileage = opts[1];
		this._name = opts[2];
		this._kilo = opts[3];
		this.subset = subset;
	}

	//To string method
	@Override
	public String toString(){
		return "TripCo is an interactive Colorado trip planning application";
	}

	public void initiate() throws FileNotFoundException, Exception{
		boolean[] opt = {_id, _mileage, _name, _kilo};
		//Instantiate Presenter, put in running loop to check for needed updates
		Presenter present = new Presenter(opt, name, subset);
        
		present.run();
                
                jsonItinerary = present.getJsonItinerary();
		
		/* Open js webpage with proper port set
		 * Send XML
		 * Presenter.sendFileToClient(out.get(0))
		 * Send SVG
		 * Presenter.sendFileToClient(out.get(1))
		 * Loop for rest/interactions
		 */
	}
	
	public static void main(String[] args) throws FileNotFoundException, InterruptedException, Exception{

		String _xml = "";
		String _svg = "";
		boolean[] opts = new boolean[7];
		ArrayList<File> files = new ArrayList<File>();

		//Need at least one input file
		if(args.length < 1){
			usage(); return;
		}
		//Parse args
		for(int h = 0; h < args.length; h++){
			
			String arg = args[h];
			
			if(arg.length()>=5){
				if(arg.substring(arg.length()-4, arg.length()).equalsIgnoreCase(".csv"))
					files.add(new File(arg));
				else if(arg.substring(arg.length()-4, arg.length()).equalsIgnoreCase(".xml"))
					_xml += arg;
				else if(arg.substring(arg.length()-4, arg.length()).equalsIgnoreCase(".svg"))
					_svg += arg;
			} else if(arg.length() == 2 && arg.charAt(0) == '-'){
				//Switch for optional flags, so order don't matter
				caseHandler(arg, opts);
			}else {
				System.out.println("Argument: '" +arg+"' not a recognized argument");
				System.out.println("Argument: '" +arg+"' will be ignored");
			}
		}
		if(opts[1] && opts[3]){
			System.out.println("Cannot display both ID's and names of locations");
			System.out.println("Pick one options -i or -n (default)");
			usage();
			return;
		}
		
	}
	
	static void caseHandler(String arg, boolean[] opts){
		switch(arg.charAt(1)){
			case 'i': opts[0] = true; break;
			case 'd': opts[1] = true; break;
			case 'n': opts[2] = true; break;
			case 'k': opts[3] = true; break;
			case 'm': opts[3] = false; break;
			default:{
				System.out.println("Argument: '" +arg+"' not a recognized argument");
				System.out.println("Argument: '" +arg+"' will be ignored");
				break;
			}
		}
	}

        JsonArray getJsonItinerary() {
            return jsonItinerary;
        }
        
}