// Jim Xu

package cs455.scaling.server;

import java.util.*;

public class ThreadPool extends Thread{  
  
    private BlockingQueue workerQueue;
    private BlockingQueue taskQueue;
    private boolean isStopped;  
    private int maxNoOfWorkers;

    public ThreadPool(int maxNoOfWorkers) {  
        this.maxNoOfWorkers = maxNoOfWorkers;
        workerQueue = new BlockingQueue(maxNoOfWorkers); 
        taskQueue = new BlockingQueue();
        isStopped = false;
    }  

    public void initiate(){
        for(int i = 0; i < maxNoOfWorkers; i++){
            if(this.isStopped)throw   
                    new IllegalStateException("ThreadPool is stopped");  
            try {  
                WorkerThread workerthread = new WorkerThread(workerQueue, i);
                Thread thread = new Thread(workerthread); 
                thread.setName("Worker" + String.valueOf(i));
                thread.start();
                this.workerQueue.enqueue((WorkerThread)workerthread);
                
            } catch (InterruptedException ex) {  
               ex.printStackTrace();  
            }  
        }
    }

    public void run(){
        while(true){
            try{
                WorkerThread workerthread = (WorkerThread)workerQueue.dequeue();
                Task newTask = (Task)taskQueue.dequeue();
                workerthread.assignTask(newTask);
                synchronized(workerthread){
                    workerthread.notify();
                }
            }catch (Exception e) {    
                e.printStackTrace();
                System.out.println("Error in retrieving task!");
            }  
        }
    }

    public void addTask(Task work){  
        try {  
            taskQueue.enqueue((Task)work);
        } catch (InterruptedException ex) {  
           System.out.println("Error in adding task!");
        }  
    }  

    public static void main(String args[]){
        ThreadPool tp = new ThreadPool(10);
        tp.initiate();
    }
}  
  
