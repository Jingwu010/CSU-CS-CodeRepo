package cs455overlay.util;

// Jim Xu
// Class Name: 	StatisticsCollectorAndDisplay
// Class Intro:	StatisticsCollectorAndDisplay stores the overlay
//				and print overlay map

public class StatisticsCollectorAndDisplay {
	private int nodeSum;
	private int[][] overlayMap;
	private int connectionNum;

	public StatisticsCollectorAndDisplay(int nodeSum, int connectionNum){
		this.nodeSum = nodeSum;
		this.connectionNum = connectionNum;
		OverlayCreator oc = new OverlayCreator();
		overlayMap = oc.generateOverlay(nodeSum, connectionNum);
	}

	// get the overlay map
	public int[][] retrieveMap(){
		return overlayMap;
	}

	public static void main(String args[]) {
		int nodeSum = 6;
		int connectionNum = 3;
		StatisticsCollectorAndDisplay sc = new StatisticsCollectorAndDisplay(nodeSum, connectionNum);

		int[][] map = sc.retrieveMap();
		System.out.println(nodeSum + " --- " + connectionNum +" ---- ");
		for(int i = 0; i < nodeSum; i++){
			for (int j = 0; j < nodeSum; j++)
				System.out.print(" " + map[i][j]);
			System.out.println();
		}

	}
}
