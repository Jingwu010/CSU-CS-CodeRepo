package edu.csu2017sp314.DTR14.tripco.View;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

abstract class SVGWriter {
    private final static String WORKDIR = View.class.getProtectionDomain().getCodeSource().getLocation().getPath();
    private final static String FILEPATH = WORKDIR.substring(0, WORKDIR.indexOf("WEB-INF/"));
	// Width and height of the SVG.
    double width;
    double height;
    
	// The SVG content to be written before whatever elements the user adds
    protected ArrayList<String> header;
	protected ArrayList<String> originalContent;

    // Usually just </svg>
    protected ArrayList<String> footer;
    // What's been added to the SVG:
    protected ArrayList<String> content;
    // Name of file to write
    protected String filename;

    // File reader 
    protected BufferedReader readSVG;
    // Boolean used by file reader
    protected boolean svgTagReached;

	// Common XMLElements 
    protected XMLElement lineElement;
    protected XMLElement textElement;
    protected XMLElement groupElement;
    protected XMLElement titleElement;

    // Constructor 1: Don't use a base SVG
    protected SVGWriter() {
        // Init arraylists and XMLElements 
        header = new ArrayList<String>();
    	content = new ArrayList<String>();
    	footer = new ArrayList<String>();
    	
        createLineElement();
        createTextElement();
        createGroupElement();
    }

    // Constructor 2: Use a base SVG
    protected SVGWriter(String filename) {
        // Init arraylists and XMLElements
    	this();
		originalContent = new ArrayList<String>();
		// Set filename
    	this.filename = filename;
		// Read in the base SVG
    	readFile();
    }

    /*
     * readFile() - read in a base SVG
     * The contents of the SVG will be read into originalContent
     */
    protected void readFile() {
        // Marks if <svg> has been read yet
        svgTagReached = false;
    	openFile();
        String line;
        // while not at the end of the file, continue reading
        while ((line = readLine()) != null) {
            if (!svgTagReached) {
                // ignore content until <svg> is reached
                if (!parseLine(line)) {
                    continue;
                }
            }
            // add the line to originalContent
            originalContent.add(line);
        }
        try {
			readSVG.close();
		} catch (IOException e) {
			System.err.println("failed closing reader");
		}
    }

    /*
     * openFile()
     * Used by readFile() to open a base SVG
     */
    protected void openFile() {
        try {
        	File f = new File(filename);
        	if (!f.exists()) {
        		throw new FileNotFoundException();
        	}
            readSVG = new BufferedReader(new FileReader(f));
            
        } catch (FileNotFoundException e) {
            System.err.println("Could not open source SVG");
        }
    }

    /*
     * readLine()
     * Read a line of the base SVG
     */
    protected String readLine() {
        try {
            return readSVG.readLine();
        } catch(IOException e) {
            System.err.println("Could not read SVG file");
            return null;
        }
    }
    
    /*
     * parseLine - checks to see if the base svg has started
     * args:
     * line - the line of the base svg
     * returns false if the svg content hasn't started
     * returns true and sets svgTagReached to true if it has
     */
    protected boolean parseLine(String line) {
    	if (!line.contains("<svg")) {
            return false;
        }
        svgTagReached = true;
        return true;
    }

    /*
     * initXml() - add XML declaration and SVG tag
     */
    protected void initXml() {
        XMLElement xml = new XMLElement("xml", "version=\"1.0\"");
		
		XMLElement svg = new XMLElement("svg", "width=\"" + width + 
										"\" height=\"" + height + 
										"\" xmlns:svg=\"http://www.w3.org/2000/svg\" " + 
										"xmlns=\"http://www.w3.org/2000/svg\"");
		header.add(xml.getStart());
		header.add(svg.getStart());
		footer.add(svg.getEnd());
    }

    /*
     * mapPoints - convert geographic coordinates to SVG coordinates
     * args:
     * x - longitude
     * y - latitude
     * returns int array with SVG coordinates
     */
    abstract int[] mapPoints(double x, double y);

    /*
	 * addLine - add a line to the SVG write queue
	 * args:
     * coordinates: the x and y coordinates of each point 
	 *  Order: x1, y1, x2, y2 
	 * color - the color of the line (accepts SVG color names or hex values with #)
	 * width - the width of the line 
	 * map - whether or not the points need to be mapped to the SVG coordinates
	 */
    public void addLine(int[] coordinates, String color, int width, boolean map) {
		addLine(new double[]{coordinates[0], coordinates[1],
            coordinates[2], coordinates[3]}, color, width, map);
    }

	
	public void addLine(double[] coordinates, String color, int width, boolean map) {
		int[] point1;
		int[] point2;
		// Get new attributes to update the <line> element 
		ArrayList<String> attributes = new ArrayList<String>();
		// If geographic -> SVG coordinate mapping should be enabled
		if (map) {
			point1 = mapPoints(coordinates[0], coordinates[1]);
			point2 = mapPoints(coordinates[2], coordinates[3]);
			for (int i = 0; i < 2; i++) {
            	attributes.add(Integer.toString(point1[i]));
            }
        	for (int i = 0; i < 2; i++) {
        		attributes.add(Integer.toString(point2[i]));
        	}
		// If not using coordinate mapping
		}
        if (!map) {
        	for (int i = 0; i < 4; i++) {
        		attributes.add(Double.toString(coordinates[i]));
        	}
        }
        
        attributes.add(color);
        attributes.add(Integer.toString(width));
        // Update the <line> attributes and add the new line to the SVG
		lineElement.updateAttributes(attributes);
    	content.add(lineElement.getStart());
	}

	/*
	 * createLineElement() - initialize the <line> element for the SVG 
	 * When lines are added via the addLine method, these attributes
	 * will be updated with the correct values 
	 */
    private void createLineElement() {
		// Add attributes to arraylist for XMLElement constructor
        ArrayList<String> attributes = new ArrayList<String>();
        attributes.add("x1");
        attributes.add("0");
        attributes.add("y1");
        attributes.add("0");
        attributes.add("x2");
        attributes.add("0");
        attributes.add("y2");
        attributes.add("0");
        attributes.add("stroke");
        attributes.add("#000000");
        attributes.add("stroke-width");
        attributes.add("0");
		// init line element 
        lineElement = new XMLElement("line", attributes);
    }

    /*
	 * newGroup - Add a <g> tag to the SVG for grouping
	 * args:
	 * groupTitle - the name of the group, placed in the <title> section
	 */
	public void newGroup(String groupTitle) {
		ArrayList<String> group = new ArrayList<String>();
		group.add(groupTitle);
		groupElement.updateAttributes(group);
		content.add(groupElement.getStart());
		content.add(titleElement.getStart() + groupTitle + titleElement.getEnd());
	}
	
	/*
	 * createGroupElement - init g and title XMLElements
	 */
	private void createGroupElement() {
		ArrayList<String> group = new ArrayList<String>();
		// make sure every group has an id 
		group.add("id");
		group.add("default");
		groupElement = new XMLElement("g", group);
		titleElement = new XMLElement("title", "");
	}

	/*
	 * endGroup - close a group by adding the </g> tag to the content queue
	 */
	public void endGroup() {
		content.add(groupElement.getEnd());
	}

    /*
	 * === PRIVATE METHOD ===
	 * To add text to your SVG, use a method like addTitle, addLabel, or addFooter (not yet implemented)
	 *
	 * addText - return text to add to an SVG
	 * args:
	 * text - the text to display
	 * x - the x position of the text
	 * y - the y position of the text
	 * size - the font size
	 * id - the XML id of the text element
	 * center - center text on x, y if true
	 * map - whether or not the points need to be mapped to the SVG coordinates
	 */
	protected XMLElement addText(String text, double[] coordinates, int size, String id, boolean center, boolean map) {
		if (map) {
			int[] point = mapPoints(coordinates[0], coordinates[1]);
			coordinates[0] = (double)point[0];
			coordinates[1] = (double)point[1];
		}

		ArrayList<String> attributes = new ArrayList<String>();
		
        attributes.add(Integer.toString((int)coordinates[0]));
		attributes.add(Integer.toString((int)coordinates[1]));
        attributes.add(Integer.toString(size));
        attributes.add(id);
        attributes.add("Sans-serif");
        if (center) {
			attributes.add("middle");
		} else {
            attributes.add("start");
        }
        textElement.updateAttributes(attributes);
		return textElement;
	}

	/*
	 * createTextElement - init XML text element 
	 * attributes will be overwritten for each new 
	 * piece of text added to the SVG 
	 */
	private void createTextElement() {
        ArrayList<String> attributes = new ArrayList<String>();
        attributes.add("x");
		attributes.add("0");
		attributes.add("y");
		attributes.add("0");
		attributes.add("font-size");
		attributes.add("0");
		attributes.add("id");
		attributes.add("0");
		attributes.add("font-family");
		attributes.add("Sans-serif");
        attributes.add("text-anchor");
        attributes.add("start");
        textElement = new XMLElement("text", attributes);
    }

    	/*
	 * addTitle - add a title to an SVG. The title will be centered in the upper padding of the SVG
	 * text - the title text
	 * id - the SVG id of the text element
	 */
    abstract void addTitle(String text, String id);
    abstract void addFooter(String text, String id);

	/*
	 * addLineLabel - add a text label in the middle of two x,y coordinates
	 * text - the text of the label
	 * id - the id attribute of the text (e.g. "leg1")
	 * coordinates - x1, y1, x2, y2 - the coordinates of a line 
	 * 	coordinates should be in decimal geographic form
	 */
	public void addLineLabel(String text, String id, double[] coordinates) {
		double x1 = coordinates[0];
		double y1 = coordinates[1];
		double x2 = coordinates[2];
		double y2 = coordinates[3];
		XMLElement txt;
		if (x1 - x2 > 720 || x1 - x2 < -720) {
			if (x1 < x2) {
				txt = addText(text, new double[]{x1 - 10, y1}, 16, id, false, false);
			} else {
				txt = addText(text, new double[]{x2 - 10, y2}, 16, id, false, false);
			}
		} else {
			txt = addText(text, new double[]{x1 + (x2 - x1) / 2, y1 + (y2 - y1) / 2}, 16, id, false, false);
		}
		
		content.add(txt.getStart() + text + txt.getEnd());
	}

	/*
	 * addLabel - add text label at any x, y coordinates 
	 * text - the text of the label 
	 * id - the id attribute of the text 
	 * coordinate - x1, y1 - the coordinates of a location 
	 *	coordinates should be in decimal geographic form 
	 */
	public void addLabel(String text, String id, double[] coordinates) {
		XMLElement txt = addText(text, coordinates, 16, id, false, false);
		content.add(txt.getStart() + text + txt.getEnd());
	}

	/*
	 * writeSVG - output the SVG to a file
	 * Returns an ArrayList of all the contents for testing 
	 */
    public ArrayList<String> writeSVG(String filename) {
    	ArrayList<String> testData = new ArrayList<String>();
    	testData.addAll(header);
    	if (originalContent != null)
    		testData.addAll(originalContent);
    	testData.addAll(content);
    	testData.addAll(footer);
		
    	try {
            BufferedWriter write = new BufferedWriter(new FileWriter(FILEPATH + filename));
			for (String s: header) 
				write.write(s + "\n");

			// Write the old SVG if it exists
			if (originalContent != null)
				for (String s: originalContent)
					write.write(s + "\n");
    		
			// Write the newly added SVG content
    		for (String s : content)
    			write.write(s + "\n");

			// Close the content of the original SVG
    		for (String s: footer) 
    			write.write(s + "\n");
    		
    		write.close();
    		
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
    	return testData;
    }
}
