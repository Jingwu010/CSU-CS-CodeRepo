package cs455overlay.util;

// Jim Xu
// Class Name: 	OverlayCreator
// Class Intro:	OverlayCreator creates an connected graph with no partition
//				each node connect with given number of other nodes

public class OverlayCreator {

	public int[][] generateOverlay(int nodeSum, int connectionNum){
		int[][] tmpMap = new int[nodeSum][nodeSum];		//a 2-D map indicates one node connect to other
		int[] nodeCount = new int[nodeSum];		//a temp array store for the node numbers
		for(int i = 0; i < nodeSum; i++){
			nodeCount[i] = 0;
			for (int j = 0; j < nodeSum; j++)
				tmpMap[i][j] = 0;
		}
		// initiation


		for(int i = 0; i < nodeSum; i++){
			//System.out.println("\n-----\n now checking " + i);
			int j = 0;
			int check = 0;
			for(j = 1; j <= connectionNum/2; j++){
				check = (i+j) % nodeSum;
				//System.out.print("check " + i + " " + check  + " | ");
				if (nodeCount[i] <= connectionNum && nodeCount[check] <= connectionNum) {
					tmpMap[i][check] = tmpMap[check][i] = 1;
					nodeCount[i] ++;
					nodeCount[check] ++;
				}
				check = ((i-j)+nodeSum) % nodeSum;
				//System.out.print("check " + i + " " + check  + " | ");
				if (nodeCount[i] <= connectionNum && nodeCount[check] <= connectionNum){
					tmpMap[i][check] = tmpMap[check][i] = 1;
					nodeCount[i] ++;
					nodeCount[check] ++;
				}
			}
			if(connectionNum%2 == 1){
				check = (nodeSum/2 + i) % nodeSum;
				if (nodeCount[i] <= connectionNum && nodeCount[check] <= connectionNum) {
					tmpMap[i][check] = tmpMap[check][i] = 1;
					nodeCount[i] ++;
					nodeCount[check] ++;
				}
			}
		}
		return tmpMap;
	}

	
}
