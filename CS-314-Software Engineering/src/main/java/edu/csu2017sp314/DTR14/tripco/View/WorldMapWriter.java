package edu.csu2017sp314.DTR14.tripco.View;

public class WorldMapWriter extends SVGWriter {

	public WorldMapWriter(String filename) {
		super(filename);
		width = 1440;
		height = 720;
		initXml();
		scaleBase();
	}
	
	public WorldMapWriter() {
		super();
		header.add("<?xml version=\"1.0\"?>");
		header.add("<svg>");
		footer.add("</svg>");
		width = 1440;
		height = 720;
	}
	
	@Override
	int[] mapPoints(double x, double y) {
		int[] mappedPoints = new int[2];
		mappedPoints[0] = (int)Math.round((180 + x) * 4);
		mappedPoints[1] = (int)Math.round((90 - y) * 4);
		return mappedPoints;
	}

	@Override
	void addTitle(String text, String id) {
		double[] coordinates = {width / 2, 30};
		XMLElement title = addText(text, coordinates, 24, id, true, false);
		content.add(title.getStart());
		content.add(text);
		content.add(title.getEnd());
	}

	@Override
	void addFooter(String text, String id) {
		double[] coordinates = {width / 2, height - 20};
		XMLElement foot = addText(text, coordinates, 24, id, true, false);
		content.add(foot.getStart());
		content.add(text);
		content.add(foot.getEnd());
	}
	
	/*
	 * addWorldLine - add lines to the world map 
	 * if the distance is shorter when wrapping around the world,
	 * two lines will be drawn to emulate the wrapping. Otherwise,
	 * it will draw one line as normal 
	 */
	public boolean addWorldLine(double[] coordinates) {
		int[] loc1 = mapPoints(coordinates[0], coordinates[1]);
		int[] loc2 = mapPoints(coordinates[2], coordinates[3]);
		for (int i = 0; i < 2; i++) {
			coordinates[i] = loc1[i];
			coordinates[i + 2] = loc2[i];
		}
		int difference = loc1[0] - loc2[0];
		if (difference > 720 || difference < -720) {
			addWrappedLine(coordinates);
			return true;
		} else {
			addLine(coordinates, "blue", 2, false);
			return false;
		}
	}
	
	private void addWrappedLine(double[] coordinates) {
		double tempCoordinate;
		if (coordinates[0] > coordinates[2]) {
			for (int i = 0; i < 2; i++) {
				tempCoordinate = coordinates[i];
				coordinates[i] = coordinates[i + 2];
				coordinates[i + 2] = tempCoordinate;
			}
		}
		tempCoordinate = -(1440 - coordinates[2]);
		addLine(new double[] {coordinates[0], coordinates[1],
				tempCoordinate, coordinates[3]}, "blue", 2, false);
		tempCoordinate = 1440 + coordinates[0];
		addLine(new double[] {tempCoordinate, coordinates[1],
				coordinates[2], coordinates[3]}, "blue", 2, false);
	}

	private void scaleBase() {
		originalContent.add(0, "<g transform=\"matrix(1.40625 0 0 1.40625 0 0)\">");
		originalContent.add(originalContent.size(), "</g>");
	}

	public static void main(String[] args) {
		WorldMapWriter wmw = new WorldMapWriter();
		wmw.addTitle("Here is a test title", "title");
		wmw.addFooter("Here is a test footer", "footer");
		wmw.addWorldLine(new double[] {-104.6737, 39.8561, 144.8410, -37.6690});
		wmw.writeSVG("cool.svg");
	}
}
