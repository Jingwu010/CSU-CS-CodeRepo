package cs455overlay.dijkstra;

// Jim Xu
// Class Name: 	ShortestPath
// Class Intro:	ShortestPath is calculating the shorestpath in a given map
//				then send the data back to routing cache to store it

public class ShortestPath {
	private int sourceNode;
	private int sinkNode;
	private int nodeSum;
	private int maxweight;
	private int[][] map;
	private int[] dist;
 	private int[] preNode;

 	public ShortestPath(int nodeSum, int sourceNode, int sinkNode, int[][] weight, int maxweight){
 		this.nodeSum = nodeSum;
 		this.sourceNode = sourceNode;
 		this.sinkNode = sinkNode;
 		this.maxweight = maxweight;
 		map = new int[nodeSum][nodeSum];
 		dist = new int[nodeSum];
 		preNode = new int[nodeSum];
 		for(int i = 0; i < nodeSum; i++)
 			for(int j = 0; j < nodeSum; j++)
 				map[i][j] = weight[i][j];
 		computePath();
 	}

 	public void computePath(){
 		boolean[] vis = new boolean[nodeSum]; //check if node i is used.

 		for(int i = 0; i < nodeSum; i++){
 			vis[i] = false;		// all nodes are not used in the begining
 			dist[i] = map[sourceNode][i];
 			if(dist[i] == maxweight) preNode[i] = -1; //not accessible
 			else preNode[i] = sourceNode;
 		}
 		dist[sourceNode] = 0;
 		vis[sourceNode] = true;
 		for (int j = 0; j < nodeSum - 1; j++){

 			//step 1: find the shortest node dist
 			int mins = maxweight;
 			int temNode = sourceNode;
 			for (int i = 0; i < nodeSum; i++){
 				if (mins > dist[i] && vis[i] == false){
 					mins = dist[i];
 					temNode = i;
 				}
 			}
 
  			//Step 2: use the chosen node to compute short path
 			vis[temNode] = true;
 			for (int i = 0; i < nodeSum; i++){
 				if(vis[temNode] == true && map[temNode][i] == maxweight) continue;
 				if(dist[i] <= dist[temNode] + map[temNode][i]) continue;

				dist[i] = dist[temNode] + map[temNode][i];
				preNode[i] = temNode;
 				

 			}

 			//Step 3: loop for n-1 times
 		}

 	}

 	public int[] retrieveDist(){
 		return dist;
 	}

 	public int[] retrievePreNode(){
 		return preNode;
 	}
 	
 	
}
