import java.util.*;
import java.lang.Math;

// Jim Xu

public class Fcfs{
	private List <Process> process;
	private int length;
    private int runtime;
    private int trtime;
    private int watime;
	public Fcfs(List <Process> process, int length){
		this.process = new ArrayList <Process> (process);
		this.length = length;
		Collections.sort(this.process, new MyComparator1());
        System.out.printf("Average waiting time: %.2f\n", getWaitingTime());
        System.out.printf("Average turnaround time: %.2f\n", getTurnaroundTime());
        System.out.printf("Throughput: %.2f\n", getThroughPut());
	}
    public double getTurnaroundTime(){
        runtime = 0;
        trtime = 0;
        for (int i = 0; i < length; i++){
            runtime = Math.max(runtime, process.get(i).getAt()) + process.get(i).getBd();
            trtime += runtime - process.get(i).getAt();
        }
        return trtime / (double)length;
    }
    
    public double getWaitingTime(){
        runtime = 0;
        watime = 0;
        for (int i = 0; i < length; i++){
            runtime = Math.max(runtime, process.get(i).getAt()) + process.get(i).getBd();
            watime += runtime - process.get(i).getBd() - process.get(i).getAt();
            System.out.printf("%-6s%-10d%-10d\n", process.get(i).getPid(), runtime - process.get(i).getBd(), runtime);
        }
        return (watime/(double)length);
    }
    
    public double getThroughPut(){
        return length/(double)runtime;
    }
    
}

