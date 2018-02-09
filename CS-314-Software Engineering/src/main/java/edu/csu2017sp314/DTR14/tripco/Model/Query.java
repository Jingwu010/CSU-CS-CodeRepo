package edu.csu2017sp314.DTR14.tripco.Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.*;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Query {

	private final String driver = "com.mysql.jdbc.Driver";
	private final String theURL = "jdbc:mysql://129.82.45.59:3306/cs314";
	private final String user = "bcgood";
	private final String pass = "830271534";
	private final String itin = "SELECT airports.id,airports.name,latitude,longitude,elevation_ft,"+
			"municipality,regions.name,countries.name,continents.name,airports.wikipedia_link,"+
			"regions.wikipedia_link,countries.wikipedia_link FROM continents INNER JOIN"+
			" countries ON countries.continent = continents.id INNER JOIN"+
			" regions ON regions.iso_country = countries.code INNER JOIN" +
			" airports ON airports.iso_region = regions.code ";
	private final String types = "SELECT distinct type FROM airports;";
	private final String conts = "SELECT name FROM continents;";
	private final String counts = "SELECT name FROM countries;";	
	private Map<String, String> countryMap = new HashMap<String, String>();
	private Map<String, String> countinentMap = new HashMap<String, String>();

	public Query(){
		try	{ // connect to the database 
			Class.forName(driver); 
			Connection conn = DriverManager.getConnection(theURL, user, pass);
			try { // create a statement
				Statement st = conn.createStatement();
				try { // submit a query
					String query = "select id, name from continents;";
					ResultSet rs = st.executeQuery(query);
					try { // iterate through the query results
						while(rs.next()){
							countinentMap.put(rs.getString(1), rs.getString(2));
						}
					} finally { rs.close(); }

					query = "select code, name from countries;";
					rs = st.executeQuery(query);
					try { // iterate through the query results
						while(rs.next()){
							countryMap.put(rs.getString(1), rs.getString(2));
						}
					} finally { rs.close(); }

				} finally { st.close(); }
			} finally { conn.close(); }
		} catch (ClassNotFoundException | SQLException e) {
			System.err.printf("Exception: ");
			System.err.println(e.getMessage());
		}
	}
	// This will be called when Webpage First setting up
	// Returns
	// Type = "(types)"
	// Contient = "(continents)"
	// Country = "(countries)"
	public String[] initQuery(){
		String[] ret = {"","",""};
		try	{ // connect to the database 
			Class.forName(driver); 
			Connection conn = DriverManager.getConnection(theURL, user, pass);	
			try {
				Statement st = conn.createStatement();
				try {//Queries
					ResultSet rs = st.executeQuery(types);
					try{ //Grab types
						String type = "";
						while(rs.next()){
							type+=rs.getString(1)+",";
						}
						type = type.substring(0, type.length()-1);//Trim trailing ','
						ret[0]=type;
					}finally { rs.close(); }
					rs = st.executeQuery(conts);
					try { //Grab continents
						String cont = "";
						while (rs.next()){
							cont+=rs.getString(1)+",";
						}
						cont = cont.substring(0, cont.length()-1);//Trim trailing ','
						ret[1]=cont;
					} finally { rs.close(); }
					rs = st.executeQuery(counts);
					try { //Grab countries
						String count = "";
						while (rs.next()){
							count+=rs.getString(1)+",";
						}
						count = count.substring(0, count.length()-1);//Trim trailing ','
						ret[2]=count;
					} finally { rs.close(); }
				} finally { st.close(); }
			} finally { conn.close(); }
		} catch (Exception e) {
			System.err.printf("Exception: ");
			System.err.println(e.getMessage());
		}
		return ret;
	}

	// Returns
	// ret Continent = "continent"
	public String continent2countries(String continent){
		String ret = "";
		try	{//Connect to DB 
			Class.forName(driver); 
			Connection conn = DriverManager.getConnection(theURL, user, pass);	
			try{
				Statement st = conn.createStatement();
				try {//Grab code from name
					String query = "SELECT name FROM countries where continent = '"+ continent +"';";
					ResultSet rs = st.executeQuery(query);
					try{//Grab code from rs
						while(rs.next()){
							ret+=rs.getString(1)+",";
						}
						ret = ret.substring(0, ret.length()-1);
					} finally { rs.close(); }
				} finally { st.close(); }
			} finally { conn.close(); }
		} catch (Exception e) {
			System.err.printf("Exception: ");
			System.err.println(e.getMessage());
		}
		return ret;
	}

	// Returns
	// ret Region = "regions"
	public String country2regions(String country){
		String ret = "";
		try	{//Connect to DB 
			Class.forName(driver); 
			Connection conn = DriverManager.getConnection(theURL, user, pass);	
			try{
				Statement st = conn.createStatement();
				try {//Grab code from name
					String query = "SELECT name FROM regions where iso_country = '" + country + "';";
					ResultSet rs = st.executeQuery(query);
					try{//Grab names from rs
						while(rs.next()){
							ret+=rs.getString(1)+",";
						}
						ret=ret.substring(0, ret.length()-1);
					} finally { rs.close(); }
				} finally { st.close(); }
			} finally { conn.close(); }
		} catch (Exception e) {
			System.err.printf("Exception: ");
			System.err.println(e.getMessage());
		}
		return ret;
	}

	// Returns
	// ret[0] Identifier = "idts"
	// ret[1] Name = "AirportNames"
	public String[] region2airports(String region, String type){
		String[] ret = {"",""};
		try	{ // connect to the database 
			Class.forName(driver); 
			Connection conn = DriverManager.getConnection(theURL, user, pass);
			try { // create a statement
				Statement st = conn.createStatement();
				try { //Make list of regions
					String query = "SELECT name, id FROM airports WHERE iso_region = '"+region+"'";
					//Add type condition to query if not empty
					if(type.isEmpty()){
						query+=";";
					}
					else{
						query+=" and type = '"+type+"';";
					}
					ResultSet rs = st.executeQuery(query);
					try { //Grab countries
						while(rs.next()){
							ret[0] += rs.getString(2) + ",";
							ret[1] += rs.getString(1) + ",";
						}
						ret[0] = ret[0].substring(0, ret[0].length()-1);
						ret[1] = ret[1].substring(0, ret[1].length()-1);
					} finally { rs.close(); }
					//System.out.println(ret.toString());
				} finally { st.close(); }
			} finally { conn.close(); }
		} catch (Exception e) {
			System.err.printf("Exception: ");
			System.err.println(e.getMessage());
		}
		return ret;
	}

	//Order of itinerary query result set
	//airport id, airport name, lat, long, elevation, municipality, regions, country, continent, 
	//air link, region link, country link
	//Itinerary constructor
	//M-DB-ITIN
	public String[] przQuery(String[] ids){
		String[] ret = new String[ids.length];
		try	{ // connect to the database 
			Class.forName(driver); 
			Connection conn = DriverManager.getConnection(theURL, user, pass);	
			try { // create a statement
				Statement st = conn.createStatement();
				try { // submit a query
					String query = itin+"WHERE airports.id in "+id2in(ids)+
							" ORDER BY FIELD"+id2field(ids)+";";
					System.out.println(query);
					ResultSet rs = st.executeQuery(query);
					try { // iterate through the query results and print
						int i=0;
						while(rs.next()){
							String add="";
							add+=rs.getString(1)+","+rs.getString(2)+","+rs.getString(3)+","+rs.getString(4)+",";
							add+=rs.getString(5)+","+rs.getString(6)+","+rs.getString(7)+","+rs.getString(8)+",";
							add+=rs.getString(9)+","+rs.getString(10)+","+rs.getString(11)+","+rs.getString(12);
							ret[i] = add;
							i++;
						}
					} finally { rs.close(); }
				} finally { st.close(); }
			} finally { conn.close(); }
		} catch (Exception e) {
			System.err.printf("Exception: ");
			System.err.println(e.getMessage());
		}
		return ret;
	}

	//ID query for Model trip planning
	//M-DB-TRIP
	public String[] planTrip(String[] ids){
		ArrayList<String> ret = new ArrayList<String>();
		try	{ // connect to the database 
			Class.forName(driver); 
			Connection conn = DriverManager.getConnection(theURL, user, pass);
			try { // create a statement
				Statement st = conn.createStatement();
				try { // submit a query
					String query = "SELECT id,name,longitude,latitude FROM airports WHERE id in "+id2in(ids)+
							"ORDER BY FIELD"+id2field(ids)+";";
					ResultSet rs = st.executeQuery(query);
					try { // iterate through the query results
						while(rs.next()){
							String add=rs.getString(1)+","+rs.getString(2)+","+rs.getString(3)+","+rs.getString(4);
							ret.add(add);
						}
					} finally { rs.close(); }
				} finally { st.close(); }
			} finally { conn.close(); }
		} catch (ClassNotFoundException | SQLException e) {
			System.err.printf("Exception: ");
			System.err.println(e.getMessage());
		}
		ret.trimToSize();
		String[] r = ret.toArray(new String[ret.size()]);
		return r;
	}

	private String id2in(String [] id){
		String r = "(";
		for(int i=0;i<id.length;i++){
			r+="'"+id[i].trim()+"',";
		}
		r=r.substring(0, r.length()-1);//Trim trailing ','
		r+=")";
		return r;
	}

	private String id2field(String [] id){
		String r = "(airports.id,";
		for(int i=0;i<id.length;i++){
			r+="'"+id[i].trim()+"',";
		}
		r=r.substring(0, r.length()-1);//Trim trailing ','
		r+=")";
		return r;
	}

	//Search Query for given search token
	public String[] searchQuery(String token){
		String[] ret = {"","","","",""};
		try	{ // connect to the database 
			Class.forName(driver); 
			Connection conn = DriverManager.getConnection(theURL, user, pass);
			try { // create a statement
				Statement st = conn.createStatement();		
				try { // submit a query
					String query = "SELECT id, name, iso_country, continent, type FROM airports WHERE name like '%"+token+"%' ";
					query+="or municipality like '%"+token+"%' ";
					query+="or id like '%"+token+"%' ";
					query+="or keywords like '%"+token+"%' ";
					query+=";";
					ResultSet rs = st.executeQuery(query);
					try { // iterate through the query results and print
						while (rs.next()){
							ret[0]+=rs.getString(1)+",";
							ret[1]+=rs.getString(2)+",";
							ret[2]+=countryMap.get(rs.getString(3))+",";
							ret[3]+=countinentMap.get(rs.getString(4))+",";
							ret[4]+=rs.getString(5)+",";
						}
						ret[0] = ret[0].substring(0, ret[0].length()-1);//Remove trailing ,
						ret[1] = ret[1].substring(0, ret[1].length()-1);
						ret[2] = ret[2].substring(0, ret[2].length()-1);
						ret[3] = ret[3].substring(0, ret[3].length()-1);
						ret[4] = ret[4].substring(0, ret[4].length()-1);
					} finally { rs.close(); }
				} finally { st.close(); }
			} finally { conn.close(); }
		} catch (Exception e) {
			System.err.printf("Exception: ");
			System.err.println(e.getMessage());
		}
		return ret;
	}

	public String[] searchQuery(String[] ids){
		String[] ret = {"","","","",""};
		try	{ // connect to the database 
			Class.forName(driver); 
			Connection conn = DriverManager.getConnection(theURL, user, pass);
			try { // create a statement
				Statement st = conn.createStatement();		
				try { // submit a query
					String query = "SELECT id, name, iso_country, continent, type FROM airports WHERE id in "+id2in(ids)+ ";";
					ResultSet rs = st.executeQuery(query);
					try { // iterate through the query results and print
						while (rs.next()){
							ret[0]+=rs.getString(1)+",";
							ret[1]+=rs.getString(2)+",";
							ret[2]+=countryMap.get(rs.getString(3))+",";
							ret[3]+=countinentMap.get(rs.getString(4))+",";
							ret[4]+=rs.getString(5)+",";
						}
						ret[0] = ret[0].substring(0, ret[0].length()-1);//Remove trailing ,
						ret[1] = ret[1].substring(0, ret[1].length()-1);
						ret[2] = ret[2].substring(0, ret[2].length()-1);
						ret[3] = ret[3].substring(0, ret[3].length()-1);
						ret[4] = ret[4].substring(0, ret[4].length()-1);
					} finally { rs.close(); }
				} finally { st.close(); }
			} finally { conn.close(); }
		} catch (Exception e) {
			System.err.printf("Exception: ");
			System.err.println(e.getMessage());
		}
		return ret;
	}
}