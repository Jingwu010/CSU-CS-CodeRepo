import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

// Jim Xu

public class Scheduler{
	public static void main(String[] args) throws FileNotFoundException{
		int quantum;
		Scanner scan = new Scanner( new File (args[0]));
		quantum = Integer.parseInt(args[1]);
		//System.out.println("quantum is :" + quantum);
		String line;
		List <Process> process = new ArrayList<Process>();
		int count = 0;
		while ( scan.hasNextLine()){
			line = scan.nextLine();
			String[] gets = line.split(",");
			//System.out.println(gets[0] + "." + gets[1] + "." + gets[2]);
        	Process newprocess = new Process(gets[0], 
        									Integer.parseInt(gets[1]),
        										Integer.parseInt(gets[2]));
        	process.add(newprocess);
        	count ++;
		}
		List <Process> pcopy = new ArrayList<>(process);
		
		Scanner gets = new Scanner(System.in);
		while(true){
			System.out.print("Which scheduling algorithm would you like to use? (FCFS, PRESJF, RR, EXIT):");
			String s = gets.next();
			s = s.toUpperCase();
			if (s.equals("EXIT")) System.exit(0);
			System.out.println("==========================================");
			if (s.equals("FCFS")){
				System.out.println("FCFS SCHEDULING ALGORITHM: ");
				System.out.println("==========================================");
				System.out.println("PID   StBurst   EBurst");
				Fcfs fcfs =  new Fcfs(new ArrayList<>(process), count);
			}
			else if (s.equals("PRESJF")){
				System.out.println("PRESJF SCHEDULING ALGORITHM: ");
				System.out.println("==========================================");
				System.out.println("PID   StBurst   EBurst");
				Presjf sjf = new Presjf(new ArrayList<>(process), count);
			}
			else if (s.equals("RR")){
				System.out.println("RR SCHEDULING ALGORITHM: ");
				System.out.println("==========================================");
				System.out.println("PID   StBurst   EBurst");
				RoundRobin rr = new RoundRobin(new ArrayList<>(process), count, quantum);
			}
			else {
				System.out.println("Error scheduling algorithm!");
			}
			System.out.println("==========================================");
		}
	}
}