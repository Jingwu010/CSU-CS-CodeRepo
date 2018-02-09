/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

//Requires Java EE
//Should be run within NetBeans
package edu.csu2017sp314.DTR14.tripco;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.nio.ByteBuffer;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.websocket.CloseReason;
import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.RemoteEndpoint;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import edu.csu2017sp314.DTR14.tripco.Model.Query;
import edu.csu2017sp314.DTR14.tripco.View.SelectionWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.JsonArray;

//Corrosponds to Server endpoint name in Server.js for clients
@ServerEndpoint("/websocket")
public class WebSocket {
    //Default root directory for Server End point
    //Working directory for everything called within executable classes
    private final static String workdir = WebSocket.class.getProtectionDomain().getCodeSource().getLocation().getPath();
    //Resource files root directory
    //SVG/XML filepath start here
    //Equal to /resources folder
    private final static String fileRoot= workdir.substring(0, workdir.indexOf("WEB-INF/"));

    private final static Query query = new Query();
    
    private static FileOutputStream fos = null;

    private static File uploadedFile;
    
    private String imagePath;
    
    //Primary Message Handling method. 
    //default stub is string, changed to void for our example
    @OnMessage
    public void onMessage(Session session, String message) {
        System.out.println("[ServerSide] message : " + message);
        //Make message JSON
        JsonReader reader = Json.createReader(new StringReader(message));
        JsonObject json = reader.readObject();//Recieved Message JSON
        //Grab key of message
        String selectionKey = removeQuotes(json.get("Key").toString());
        
        // Message Redirection
        switch (selectionKey){
            
            /* Case <--Init-->
             * This will be called when Webpage First setting up
             * Input Json {Key = "Init", Value = ""}
             * Output Json {Key = "Init", Type = "(types)", Continent = "(continents)", Country = "(countries)"}
             */
            case "Init":
                initWebPage(session);
                break;

            /* Case <--Continent-->
             * This will be called when Webpage First setting up
             * Input Json {Key = "DefContinent", Continent = "Continent"}
             * Output Json {Key = "DefContinent", Country = "countries"}
             */
            case "Continent":
                defContinent(session, json);
                break;

            /* Case <--Country-->
             * This will be called when Webpage First setting up
             * Input Json {Key = "DefCountry", Country = "country"}
             * Output Json {Key = "DefCountry", Region = "regions"}
             */
            case "Country":
                defCountry(session, json);
                break;

            /* Case <--Region-->
             * This will be called when Webpage First setting up
             * Input Json {Key = "DefRegion", Region = "region", Type = "type"}
             * Output Json {Key = "DefRegion", Identifier = "idts", Name = "AirportNames"}
             */
            case "Region":
                defRegion(session, json);
                break;

            /* Case <--Search-->
             * This will be called when Webpage Search specific content
             * Input Json {Key = "Search", Value = "content"}
             * Output Json {Key = "Search", Identifier = "idts", Name = "AirportNames"}
             */ 
            case "Search":
                searchQuery(session, json);
                break;

            /* Case <--ReadXML-->  NOTE:THIS IS IN MY TODO LIST
             * This will be called when User upload XML File
             * Input Json {Key = "ReadXML", FileName = "fileName", Data = "data"}
             * Output Json {Key = "ReadXML", Status = "(true/false)"}
             */ 
            case "ReadXML":
                processXMLFile(session, json);
                break;
                
            /* Case <--DownloadXML-->  NOTE:THIS IS IN MY TODO LIST
             * This will be called when User downloads XML File
             * Input Json {Key = "DownloadXML", Title = "Title", Data = "data"}
             * Output Json {Key = "DownloadXML", Status = "(true/false)"}
             */ 
            case "DownloadXML":
                writeXML(session, json);
                break;
                

            /* Case <--PlanTrip-->
             * This will be called when Webpage Search specific content
             * Input Json {Key = "PlanTrip", Name = "name", Identifier = "idts", Option = "opts(true/false)"}
             * boolean [1] = _id, [2] = _mileage, [3] = _name [4] = _km
             * Output Json {Key = "PlanTrip", XML = "xmlPath", SVG = "svgPath"}
             * When Plantrip is Done, an JSON obj will be sent to JSX
             * JSX should load the file according to the path.
             */ 
            case "PlanTrip":
                planTrip(session, json);
                break;
        }
    }
    
    private JsonObject buildJSON(String key, String value){
        JsonObject json = Json.createObjectBuilder()
           .add("Key", key)
           .add("Value", value).build();
        return json;
    }

    //Send response message
    private void sendBack(Session session, JsonObject json){
        RemoteEndpoint.Basic remote = session.getBasicRemote();//Get Session remote end 
        try{
            System.out.println(json.toString());
            remote.sendText(json.toString());            
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    
    private void sendBack(Session session, JsonArray array){
        JsonObject arrayJson = Json.createObjectBuilder()
                .add("Key", "PlanTrip")
                .add("Array", array)
                .add("Image", imagePath)
                .build();
        RemoteEndpoint.Basic remote = session.getBasicRemote();//Get Session remote end 
        try{
            remote.sendText(arrayJson.toString());            
        } catch(IOException e){
            e.printStackTrace();
        }
    }

    // refer to the comments in switch syntax
    private void initWebPage(Session session){
        String[] answer = query.initQuery();
        JsonObject jso = Json.createObjectBuilder()
            .add("Key", "Init")
            .add("Type", answer[0])
            .add("Continent", answer[1])
            .add("Country", answer[2])
            .build();
        sendBack(session, jso);
    }

    // refer to the comments in switch syntax
    private void defContinent(Session session, JsonObject json){
        String continent = removeQuotes(json.get("Continent").toString());
        String answer = query.continent2countries(continent);
        JsonObject jso = Json.createObjectBuilder()
            .add("Key", "DefContinent")
            .add("Country", answer)
            .build();
        sendBack(session, jso);
    }

    // refer to the comments in switch syntax
    private void defCountry(Session session, JsonObject json){
        String country = removeQuotes(json.get("Country").toString());
        String answer = query.country2regions(country);
        JsonObject jso = Json.createObjectBuilder()
            .add("Key", "DefCountry")
            .add("Region", answer)
            .build();
        sendBack(session, jso);
    }

    // refer to the comments in switch syntax
    private void defRegion(Session session, JsonObject json){
        String type = removeQuotes(json.get("Type").toString());
        String region = removeQuotes(json.get("Region").toString());
        String[] answer = query.region2airports(region, type);
        JsonObject jso = Json.createObjectBuilder()
            .add("Key", "DefRegion")
            .add("Identifier", answer[0])
            .add("Name", answer[1])
            .build();
        sendBack(session, jso);
    }

    // refer to the comments in switch syntax
    private void searchQuery(Session session, JsonObject json){
        String content = removeQuotes(json.get("Value").toString());
        String[] answer = query.searchQuery(content);
        JsonObject jso = Json.createObjectBuilder()
            .add("Key", "Search")
            .add("Identifier", answer[0])
            .add("Name", answer[1])
            .add("Country", answer[2])
            .add("Continent", answer[3])
            .add("Type", answer[4])
            .build();
        sendBack(session, jso);
    }

    private void processXMLFile(Session session, JsonObject json) {   
        String fileName = removeQuotes(json.get("FileName").toString());
        System.out.println("[ServerSide] Server Accept " + fileName);
        uploadedFile = new File(fileRoot + session.getId() + "/" + fileName);
        try {
            fos = new FileOutputStream(uploadedFile);
        } catch (FileNotFoundException e) {     
            e.printStackTrace();
        }
    }
    
    private void writeXML(Session session, JsonObject json) {
        String title = removeQuotes(json.get("Title").toString());
        System.out.println("[ServerSide]: download xml"+title);
        String loc = System.getProperty("user.dir");
        String data=json.get("data").toString();
        data = data.replace("\"","");
        SelectionWriter sw = new SelectionWriter(data.split(","), title, fileRoot+ session.getId() + "/");
        sw.writeXML();
        System.out.println("[ServerSide]: download xml:"+sw.path);
        JsonObject jso = Json.createObjectBuilder()
            .add("Key", "DownloadXML")
            .add("Path", session.getId()+"/"+title+".xml")
            .build();
        sendBack(session, jso);
    }

    @OnMessage
    public void processUpload(ByteBuffer data, Session session) { 
        boolean status = true;
        while(data.hasRemaining()) {         
            try {
                fos.write(data.get());
            } catch (IOException e) {               
                e.printStackTrace();
            }
        }
        try {
            fos.flush();
            fos.close();                
        } catch (IOException e) { 
            status = false;
            e.printStackTrace();
        }
        finally{
            String[] subSet = null;
            try {
                subSet = new XMLReader().
                    readSelectFile(uploadedFile.getAbsolutePath(), new StringBuilder());
            } catch (FileNotFoundException ex) {
                Logger.getLogger(WebSocket.class.getName()).log(Level.SEVERE, null, ex);
            }
            String[] answer = query.searchQuery(subSet);
            JsonObject jso = Json.createObjectBuilder()
                .add("Key", "ReadXML")
                .add("Identifier", answer[0])
                .add("Name", answer[1])
                .add("Country", answer[2])
                .add("Continent", answer[3])
                .add("Type", answer[4])
                .build();
            sendBack(session, jso);
        }
    }

    // refer to the comments in switch syntax
    private void planTrip(Session session, JsonObject json){
        // String name = removeQuotes(json.get("Name").toString());
        String[] idts = removeQuotes(json.get("Identifier").toString()).split(",");
        // String[] options = removeQuotes(json.get("Option").toString()).split(",");
        // boolean[] opts = new boolean[options.length];
        // for(int i = 0; i < options.length; i++)
        //    opts[i] = options[i].equals("true") ? true : false;
        // new TripCo(name, opts, idts);
        imagePath = json.get("Title").toString();
        System.out.println("[ServerSide] Start to Plan Trip ");
        TripCo trip = new TripCo(imagePath, new boolean[] {false, false, false, false}, idts);
                try {
                    trip.initiate();
                } catch (Exception e) {
                    e.printStackTrace();
                }
        imagePath += ".svg";
        JsonArray array = trip.getJsonItinerary();
        sendBack(session, array);
        System.out.println("AHHHH");
    }

    //Removes quotes from strings for JSON handling
    private String removeQuotes(String string){
        return string.replaceAll("\"", "");
    }

    //Default method called when connection open
    @OnOpen
    public void onOpen(Session session, EndpointConfig conf) {
        new File(fileRoot + session.getId()).mkdir();
        System.out.println("[ServerSide] message : " + fileRoot + session.getId());//Print statement sanity check
        System.out.println("[ServerSide] " + session.getId() + " open successfully");
    }
    
    //Default method called on error
    @OnError
    public void onError(Session session, Throwable t) {
        deleteDir(new File(fileRoot + session.getId()));
        System.out.println("[ServerSide] " + session.getId() + " gets error");
        t.printStackTrace();
    }

    //Default method called when session closed, record keeping done here
    @OnClose
    public void onClose(Session session, CloseReason reason) {
        System.out.println("[ServerSide] " + deleteDir(new File(fileRoot + session.getId())));
        System.out.println("[ServerSide] " + session.getId() + " socket closed");
        System.out.println("[ServerSide] " + reason.getReasonPhrase());
    }
    
    private boolean deleteDir(File dir) { 
        if (dir.isDirectory()) { 
            String[] children = dir.list(); 
            for (int i = 0; i < children.length; i++)
                deleteDir(new File(dir, children[i])); 
        }  
        return dir.delete(); 
    }    
}






