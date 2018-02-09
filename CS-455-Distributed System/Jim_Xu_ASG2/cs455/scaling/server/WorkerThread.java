// Jim Xu

package cs455.scaling.server;

public class WorkerThread extends Thread {  
  
    private BlockingQueue workerQueue = null;  
    private Task task;
    private int id;
  
    public WorkerThread(BlockingQueue queue, int id) {  
        workerQueue = queue;  
        this.id = id;
    }  

    public void run() { 
        synchronized(this){
            while(true){
                try{
                    this.wait();
                } catch (InterruptedException ex) {  
                    ex.printStackTrace();
                    System.out.println("failed to notify");
                }

                task.func(); 

                try {  
                    this.workerQueue.enqueue(this);  
                } catch (InterruptedException ex) {  
                   ex.printStackTrace();  
                }
            }  
        }
    }  

    public void assignTask(Task task){
        this.task = task;
    }
}  