/*
XMLElement
Every element of an XML (<text>, <path>, etc) should use this
*/

package edu.csu2017sp314.DTR14.tripco.View;

import java.util.ArrayList;

public class XMLElement {
	// The XML element the object represents ("svg", "path", etc)
	String type;
	// The attributes of the element
	String attributes;
	ArrayList<String> attributesList;
    // The opening tag of an element (e.g. <text font-size="12">)
    String start;
    // The ending tag of an element (e.g. </text>). Will equal "" if element has no ending tag
    String end;

    /*
     * XMLElement Constructor 1
     * args:
     * type - the XML element's name
     * attributes - a string containing already correctly formatted attributes of the element
     * 
     * usage example (build <svg> element with attributes height="300" width="300"):
     * XMLElement("svg", "height=\"300\" width=\"300\"");
     * 
     * special cases:
     * type = "comment" - constructs an XML comment. Add comment text in attributes field
     */
    public XMLElement(String type, String attributes) {
    	this.type = type;
    	if (attributes.length() != 0) {
    		this.attributes = " ".concat(attributes);
    	} else {
    		this.attributes = attributes;
    	}
    	if (buildStart())
    		buildEnd();
    	else
    		end = "";
    }
    
    /*
     * XMLElement Constructor 2
     * args:
     * type - the XML element's name
     * attributes - an arraylist of strings containing attributes, formatted as follows:
     * 	attributes.at(0, 2, 4, ...): attribute name
     * 	attributes.at(1, 3, 5, ...): attribute value (unformatted)
     * 	ex: attributes: ["width", "300", "height", "300"]
     */
    public XMLElement(String type, ArrayList<String> attributes) {
    	this.type = type;
    	attributesList = attributes;
    	this.attributes = buildAttributeString(attributes);
    	if (buildStart())
    		buildEnd();
    	else
    		end = "";
    }
    
    /*
     * Builds the start tag based on the XML element's name and attributes
     * Returns false if the XML element has no end tag
     */
    public boolean buildStart() {
    	// Switch to determine if special cases exist
    	switch (type) {
    	// Special case: "comment" - create an XML comment
    	case "comment":
    		start = "<!-- " + attributes + " -->";
    		return false;
    	// Special case: "xml" - create an XML header
    	case "xml":
    		start = "<?xml" + attributes + "?>";
    		return false;
    	// Special case: elements with no end tag
    	case "path": case "defs": case "rect": case "polyline": case "line": 
    		start = "<" + type + "" + attributes + " />";
    		return false;
    	// Default: create the start tag, mark that an end tag should be created
    	default:
    		start = "<" + type + "" + attributes + ">";
    		return true;
    	}
    }
    
    /*
     * Builds the end tag based on the XML element's name
     */
    public void buildEnd() {
    	end = "</" + type + ">";
    }
    
    public String buildAttributeString(ArrayList<String> attributes) {
    	String attributeList = "";
    	for (int i = 0; i < attributes.size(); i += 2) {
    		if (i == 0) {
    			attributeList += " ";
    		}
    		attributeList += attributes.get(i) + "=\"" + attributes.get(i + 1) + "\"";
    		if (i != attributes.size() - 2)
    			attributeList += " ";
    	}
    	return attributeList;
    }
    
    // Return the start tag
    public String getStart() {return start;}
    // Return the end tag
    public String getEnd() {return end;}

	public void updateAttributes(ArrayList<String> newAttributes) {
		for (int i = 1; i < attributesList.size(); i += 2) {
			attributesList.set(i, newAttributes.get(i / 2));
		}
		this.attributes = buildAttributeString(attributesList);
		buildStart();
	}
}