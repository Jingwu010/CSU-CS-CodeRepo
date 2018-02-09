import java.util.*;
import java.lang.Math;

// Jim Xu

public class Presjf{
	private List <Process> process;
	private int length;
	private int runtime;
    private int trtime;
    private int watime;
    private int times;
	private Queue<Process> rrqueue = new PriorityQueue<Process> (new MyComparator2());
	public Presjf(List <Process> process, int length){
		this.process = new ArrayList <Process> (process);
		this.length = length;
        times = 0;
		//System.out.println("quantum = " + quantum);
		Collections.sort(this.process, new MyComparator1());
        System.out.printf("Average waiting time: %.2f\n", getWaitingTime());
        System.out.printf("Average turnaround time: %.2f\n", getTurnaroundTime());
        System.out.printf("Throughput: %.2f\n", getThroughPut());
	}
    private void qSpeak(){
    	PriorityQueue<Process> pcopy = new PriorityQueue<>(rrqueue);
		System.out.println("---" + runtime);
    	while(!pcopy.isEmpty()){
    		 pcopy.remove().Speak();

    	}
    }
    private int Psjf(int choice, int times){
    	int index = 0;
        int gettime;
        runtime = process.get(index).getAt();
        gettime = 0;
        rrqueue.add(process.get(index++));
        int ttt = 0;
        while (index < length || !rrqueue.isEmpty()){
        	Process temp = rrqueue.remove();
            int tt = Math.min(temp.getLast(), 
                    index < length ? process.get(index).getAt() - runtime : temp.getLast());
            ttt += tt;
            runtime += tt;
            temp.minusLast(tt);

            //Add to queue IF NO IDLE time
            while(index < length && runtime >= process.get(index).getAt()){
                rrqueue.add(process.get(index++));
            }
            if (temp.getLast() > 0) rrqueue.add(temp);
            
            //calculate times and check if preemtion occurs
            if ((rrqueue.isEmpty() || !rrqueue.peek().getPid().equals(temp.getPid()))){
                if (times == 0) System.out.printf("%-6s%-10d%-10d\n", temp.getPid(), runtime - ttt, runtime);
                if (choice == 1  && temp.getLast() == 0) gettime += runtime - temp.getAt();
                if (choice == 2) {
                    gettime += runtime - ttt - temp.getEt();
                    temp.setEt(runtime);
                }
                ttt = 0;
            }
            //Add to queue IF HAS IDLE time
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
        return (trtime = Psjf(1, times++)) / (double)length;
    }
    
    public double getWaitingTime(){
        for(int i = 0; i < length; i++){
            process.get(i).setLast();
            process.get(i).setEt(process.get(i).getAt());
        }
        return (watime = Psjf(2, times++))/(double)length;
    }
    
    public double getThroughPut(){
        return length/(double)runtime;
    }
}
    