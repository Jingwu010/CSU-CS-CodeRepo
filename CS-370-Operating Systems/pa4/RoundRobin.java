import java.util.*;
import java.lang.Math;

// Jim Xu

public class RoundRobin{
	private List <Process> process;
	private int length;
	private int quantum;
	private int runtime;
    private int trtime;
    private int watime;
    private int times;
	private Queue <Process> rrqueue = new LinkedList<Process>();
	public RoundRobin(List <Process> process, int length, int quantum){
		this.process = new ArrayList <Process> (process);
		this.length = length;
		this.quantum = quantum;
        times = 0;
		Collections.sort(this.process, new MyComparator1());
        System.out.printf("Average waiting time: %.2f\n", getWaitingTime());
        System.out.printf("Average turnaround time: %.2f\n", getTurnaroundTime());
        System.out.printf("Throughput: %.2f\n", getThroughPut());
	}
    private int RR(int choice, int times){
        int index = 0;
        int gettime;
        runtime = process.get(index).getAt();
        gettime = 0;
        rrqueue.add(process.get(index++));
        //if processes all into queue and queue is empty, breaks
        while (index < length || !rrqueue.isEmpty()){
            Process temp = rrqueue.remove();
            int tt = Math.min(quantum, temp.getLast());
            runtime += tt;
            temp.minusLast(tt);
            //calculate times
            if (times == 0) System.out.printf("%-6s%-10d%-10d\n", temp.getPid(), runtime - tt, runtime);
            if (choice == 2){
                gettime += runtime - tt - temp.getEt();
                temp.setEt(runtime);
            }
            if (choice == 1  && temp.getLast() == 0) gettime += runtime - temp.getAt();
            
            //Add to queue
            while(index < length && runtime >= process.get(index).getAt()){
                rrqueue.add(process.get(index++));
            }
            if (temp.getLast() > 0) rrqueue.add(temp);
            if (index < length && rrqueue.isEmpty()){
                runtime = process.get(index).getAt();
                rrqueue.add(process.get(index++));
            }   
        }
        return gettime;
    }
    public double getTurnaroundTime(){
        for(int i = 0; i < length; i++)
            process.get(i).setLast();
        return (trtime = RR(1, times ++)) / (double)length;
    }
    
    public double getWaitingTime(){
        for(int i = 0; i < length; i++){
            process.get(i).setLast();
            process.get(i).setEt(process.get(i).getAt());
        }
        return (watime = RR(2, times ++))/(double)length;
    }
    
    public double getThroughPut(){
        return length/(double)runtime;
    }
    
}
