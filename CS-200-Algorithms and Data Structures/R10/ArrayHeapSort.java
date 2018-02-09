import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class ArrayHeapSort {

	private int[] A;

	public ArrayHeapSort(int[] A){
		this.A = A;
		buildHeap();
	}
	private void swapArr(int i, int j){
		int t = A[i];
		A[i] = A[j];
		A[j] = t;
		i = j;
	}
	private void heapify(int i, int size){
	// left and right subtrees are already heaps
	// need to bubble element i in place top down
		while (true){
			//TimeUnit.SECONDS.sleep(3);
			if (i >= size / 2) return;
			if (A[i] >= A[2 * i + 2] && A[i] >= A[2 * i + 1]) return;
			if (A[2 * i + 2] > A[2 * i + 1]){
				swapArr(i, 2 * i + 2);
				i = 2 * i + 2;
			}
			else {
				swapArr(i, 2 * i + 1);
				i = 2 * i +1;
			}
		}
	}



	// implement the buildHeap algorithm described in the lecture notes 
	private void buildHeap(){
		for (int i = (A.length - 2 ) / 2 ; i >= 0 ; i--){
			//pre: the tree rooted at index is a semiheap
			//i.e., the sub trees are heaps
			heapify(i, A.length); // bubble down
			//post: the tree rooted at index is a heap
			
		}
	}

	// implement the in place heapSort algorithm described in the lecture notes
	public void heapSort(){
		for (int i = A.length - 1; i >= 0; i--){
			swapArr(i, 0);
			// try{
			// 	System.out.println("sorted : " + toString());
			// 	Thread.sleep(1000);
			// }catch (InterruptedException ex){}
			for (int j = i-1 ; j >= 0 ; j--)
				heapify(j, i-1);
			
		}

	}

	// return the heap content
	public String toString(){
		return Arrays.toString(A);
	}

	public static void main(String[] args) throws FileNotFoundException{
		// scanner for input file
		Scanner scan = new Scanner( new File (args[0]));
		// first int in input: number of ints to sort following 
		int n = Integer.parseInt(scan.next());
		int[] A = new int[n];
		for(int i = 0; i<n; i++)
			A[i] = Integer.parseInt(scan.next());
		System.out.println("    in: " + Arrays.toString(A));
		ArrayHeapSort ahs = new ArrayHeapSort(A);
		System.out.println("  heap: " + ahs);
		ahs.heapSort();
		System.out.println("sorted: " + ahs);

	}
}