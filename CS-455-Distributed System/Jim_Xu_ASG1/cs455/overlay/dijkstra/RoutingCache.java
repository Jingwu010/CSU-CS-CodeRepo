package cs455overlay.dijkstra;

// Jim Xu
// Class Name: 	RoutingCache
// Class Intro:	RoutingCache is used for storing shortestpath information 

public class RoutingCache {
	
	private int[] dist;
 	private int[] preNode;

	public RoutingCache(int nodeSum, int sourceNode, int sinkNode, int[][] weight, int maxweight){
		ShortestPath st = new ShortestPath(nodeSum, sourceNode,
								sinkNode, weight, maxweight);

		dist = st.retrieveDist();
		preNode = st.retrievePreNode();

	}

	public int[] getRoute(){
		return preNode;
	}

	public void printRout(int des, int st){
 		
 		if(st == des){
 			System.out.println(st);
 			return;
 		}

 		else{
 			System.out.print(st + "->");
 			printRout(des, preNode[st]);
 		}
 		
 	}

	public static void main(String args[]) {
 		int maxInt = 1000;
 		int n = 8;
 		int[][] map = new int[8][8];
 		for (int i = 0; i < 8; i++)
 			for(int j = 0; j < 8; j++){
 				if(i == j) map[i][j] = 0;
 				else map[i][j] = maxInt;
 			}
 		map[0][1] = 1;map[0][2] = 4;map[0][6] = 7;map[0][7] = 6;
 		map[4][3] = 2;map[4][5] = 1;map[4][6] = 5;map[4][2] = 2;
 		map[1][7] = 4;map[1][3] = 9;map[1][2] = 3;
 		map[5][7] = 7;map[5][6] = 3;map[5][3] = 6;
 		map[2][3] = 5;map[7][6] = 2;
 		for (int i = 0; i < 8; i++)
 			for(int j = 0; j < 8; j++){
 				if(map[i][j] == 0 || map[i][j] == maxInt) continue;
 				map[j][i] = map[i][j];
 			}
 		RoutingCache rc = new RoutingCache(8,0,3,map,maxInt);
 		rc.printRout(0,4);
 	}
}
