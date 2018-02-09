
//Jim

import java.util.ArrayList;

public class DepGraph {	
	// n: number of nodes
	private int n;
	// number of edges
	private int m;

	// graph has adjLists mapping sources to destination lists 
	private ArrayList<AdjList> graph;
	private boolean debug;

	public DepGraph(boolean debug){
		this.graph = new ArrayList<AdjList>();
		this.debug = debug;
	}

	public String toString(){
		String res = "Dependence Graph: " + n + " nodes, " + m + " edges\n";
		for (int i = 0; i<graph.size(); i++)
			res += i + ": " + graph.get(i) + "\n";
		return res;
	}


        // return whether graph contains the source
	private boolean graphContains(String source){
		for (int i = 0; i<graph.size(); i++){
			if (debug) System.out.println(graph.get(i).getSource() + ":" + source);
			if (graph.get(i).getSource().equals(source) == true) return true;
		}
		return false;
	}
	
	// add **new** source entry with empty destList to end of graph
	public void addAdjList(String source) throws GraphException{
		if (graphContains(source))
			throw new GraphException(source +" already defined!"); 		
		else{
			AdjList temp = new AdjList(source);
			graph.add(temp);
			n++;
		}
	}
	
	// get the adjList of source
	private AdjList getAdjList(String source) throws GraphException{
		if (!graphContains(source))
			throw new GraphException(source +" undefined!");
		else{
			for (int i = 0; i<graph.size(); i++)
				if (graph.get(i).getSource().equals(source) == true)
					return graph.get(i);
		}
		return null;
	}

	// if dest not in the source AdjList add it to the source adjList
	// increment inDegree in dest adjList
	public void addDest(String source, String dest) throws GraphException{
		if (!graphContains(source))
			throw new GraphException(source +" undefined!");
		else{
			AdjList temp = getAdjList(source);
			if (!temp.contains(dest)){
				temp.addDest(dest);
				for (int i = 0; i<graph.size(); i++)
					if (graph.get(i).getSource().equals(dest) == true)
						graph.get(i).incrInDegree();
				m++;
			}
		}

	}

	public void topoPrint() throws GraphException{
		for (int i = 0; i < n; i++)
			graph.get(i).setTemDegree();
		int cnt = n;
		while (cnt > 0){
			ArrayList<String> output = new ArrayList<String>();
			boolean[] vis = new boolean[n];
			for (int i = 0; i < n; i++){
				vis[i] = false;
				if (graph.get(i).getTemDegree() == 0 && graph.get(i).getFlag() == false){
					vis[i] = true;
					output.add(graph.get(i).getSource());
					cnt --;
				}
			}
			for (int i = 0; i < n; i++){
				if (vis[i] == true){
					graph.get(i).setFlag();
					for(int j = 0; j < graph.get(i).size(); j++)
						getAdjList(graph.get(i).getDest(j)).deTemDegree();
				}
			}
			System.out.println(output.toString());
		}
       // copy all inDegrees to temporary inDegrees, as these will be decremented to zero
       // repeat
       //   1. find sources with temporary inDegree 0 (making sure only new ones are found, see step 3)
       //   2. print these on one line in definition order in format [SOURCE1, SOURCE2, ... , SOURCEn]
       //   3. decrement the temporary inDegree of all successors of the sources found above
    }


	public static void main(String[] args) throws GraphException{
		DepGraph dG = new DepGraph(false);
		dG.addAdjList("a");
		dG.addAdjList("b");
		dG.addAdjList("c");
		dG.addAdjList("d");
		dG.addAdjList("e");
		//dG.addAdjList("e");  // checking graph exception

		dG.addDest("a","c");
		dG.addDest("a","d");

		dG.addDest("b","c");
		dG.addDest("b","d");

		dG.addDest("c","d");

		dG.addDest("c","e");
		dG.addDest("d","e");

		System.out.println(dG);

	}


}