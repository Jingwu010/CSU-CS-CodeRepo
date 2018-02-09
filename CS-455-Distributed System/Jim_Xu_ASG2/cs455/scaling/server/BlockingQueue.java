// Jim Xu

package cs455.scaling.server;

import java.util.*;

public class BlockingQueue {  
  
    private LinkedList<Object> queue;
    private int limit;  
  
    public BlockingQueue(int limit) {  
        this.limit = limit;  
        queue = new LinkedList<Object>();  
    }  
    
    public BlockingQueue() {    
        queue = new LinkedList<Object>();  
    } 

    public synchronized void enqueue(Object item)  
            throws InterruptedException {  
        if (this.queue.size() == 0) {  
            this.notifyAll();  
        }  
        this.queue.add(item);             
        //return this.queue.get(this.queue.size()-1);  
    }  
  
    public synchronized Object dequeue()  
            throws InterruptedException {  
        while (this.queue.size() == 0) {   
            this.wait();  
        }  
        return this.queue.remove(0);  
    }  
      
    public synchronized int size(){  
        return this.queue.size();  
    }  
}  