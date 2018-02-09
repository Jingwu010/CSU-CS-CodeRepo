import java.util.*;

// Jim Xu

public class Process{
	private int length;
	private String pid;
	private int at, bd, last, et;
	public Process(String pid, int at, int bd){
		this.pid = pid;
		this.at = at;
		this.bd = bd;
		this.last = bd;
		this.et = at;
	}
	public String getPid(){
		return this.pid;
	}
	public int getAt(){
		return this.at;
	}
	public int getBd(){
		return this.bd;
	}
	public void setLast(){
		this.last = bd;
	}
	public int getLast(){
		return this.last;
	}
	public int minusLast(int minus){
		return this.last -= minus;
	}
	public int getEt(){
		return this.et;
	}
	public void setEt(int time){
		this.et = time;
	}
	public void Speak(){
		System.out.println("(" + pid + " , " + at +" , " + last +")");
	}
}

class MyComparator1 implements Comparator<Process> {
    @Override
    public int compare(Process p0, Process p1) {
        int a0 = p0.getAt();
        int a1 = p1.getAt();
        //System.out.println("here is comparing:" +a0 + "::" + a1 + "==" 
        //						+ (a0 > a1? -1 : (a0 == a1) ? 0 : 1));
        return (a0 < a1? -1 : (a0 == a1) ? 0 : 1);
    }
}

class MyComparator2 implements Comparator<Process> {
    @Override
    public int compare(Process p0, Process p1) {
        int a0 = p0.getLast();
        int a1 = p1.getLast();
        //System.out.println("here is comparing:" +a0 + "::" + a1 + "==" 
        //						+ (a0 > a1? -1 : (a0 == a1) ? 0 : 1));
        return (a0 < a1? -1 : (a0 == a1) ? 0 : 1);
    }
}