package edu.csu2017sp314.DTR14.tripco.Presenter;

public class Message {
	
	//Content is an array holding comma separated Strings to be processed based on the code
	public String[] content;
	public String code;
	//Code is an identifying dash separated String
	//Example: M-DB-Cy2Rn
	//The fist section (before the first dash) should be a single character marking the destination object
	//M=Model, V=View, P=Presenter
	//The second section should be the type of action for content processing
	//DB=database query, UP=update, ST=set 
	//The third section should specify what type of content is being processed
	//CY2RN = DB query to get list of RegioN for a specific CountrY
	//RN2IL = DB query to get list of IndividuaL airports from a specific RegioN
	//CT2CY = DB query to get list of CountrYs from a specific ContinenT
	//INIT = DB init query to get list of types, continents, and countries
	//
	//ITIN = DB PLAN query to grab necessary data for itinerary writing
	//IL = Individual airports drop down in GUI
	//CY = Country drop down in GUI
	//RN = Region drop down in GUI
	//
	
	
	public Message(String[] cont, String Code){
		content = cont;
		code = Code;
	}
}