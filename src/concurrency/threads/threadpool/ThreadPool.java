package concurrency.threads.threadpool;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
 
 
/**
 * ThreadPool 
   
 * Creates a thread pool that reuses a fixed number of threads to execute tasks.
 * 
 * Can be at most N size threads active processing tasks.
 * 
 *
 * If all threads are active and processing, additional tasks submitted are queued
 *
 * When shutdown of ThreadPool is called, existing tasks that were submitted  are
 * executed, but no new tasks will be executed.
 *
 * @author Atul Sharma
 */
public class ThreadPool {
 
    private BlockingQueue<Runnable> taskQueue;
   
    /*
     * Once pool shutDown will be initiated, poolShutDownInitiated will become true.
     */
    private boolean poolShutDownInitiated = false;
 
    /* Constructor of ThreadPool
     * size= is a number of threads that exist in ThreadPool.
     * size number of threads are created and started.  *
     */
    public ThreadPool(int size){
        taskQueue = new LinkedBlockingQueue<Runnable>(size);
 
        //Create and start size number of threads.
        for(int i=1; i<=size; i++){
           PoolWorkerThread PoolWorkerThread = new PoolWorkerThread(taskQueue,this);
           PoolWorkerThread.setName("Thread-"+i);
           System.out.println("Thread-"+i +" created in ThreadPool.");
           PoolWorkerThread.start();   //start thread
        }
       
    }
 
   
    /**
     * Execute the task, task must be of Runnable type.
     */
    public synchronized void  execute(Runnable task) throws Exception{
        if(this.poolShutDownInitiated)
           throw new Exception("ThreadPool has been shutDown, no further tasks can be added");
 
        /*
         * Add task in sharedQueue,
         * and notify all waiting threads that task is available.  
         */
        System.out.println("task has been added.");
        this.taskQueue.put(task);
    }
 
 
    public boolean isPoolShutDownInitiated() {
           return poolShutDownInitiated;
    }
 
 
    /**
     * Initiates shutdown of ThreadPool, previously submitted tasks
     * are executed, but no new tasks will be accepted.
     */
    public synchronized void shutdown(){
       this.poolShutDownInitiated = true;
        System.out.println("ThreadPool SHUTDOWN initiated.");
    }
 
}
 
 
/**
 * These threads are created and started from constructor of ThreadPool class.
 */
 class PoolWorkerThread extends Thread {
 
    private BlockingQueue<Runnable> taskQueue;
    private ThreadPool threadPool;
 
    public PoolWorkerThread(BlockingQueue<Runnable> queue,
    		ThreadPool threadPool){
        taskQueue = queue;
        this.threadPool=threadPool;
       
    }
 
    public void run() {
           try {
                  /*
                   * ThreadPool's threads will keep on running until ThreadPool is shutDown
                   * or there is no work left to be done.
                   */
                  while (true) {    
                        System.out.println(Thread.currentThread().getName()
                                      +" is READY to execute task.");
                        /* Take from the queue if something is there*/
                        Runnable runnable = taskQueue.take();
                        System.out.println(Thread.currentThread().getName()
                                      +" has taken task.");
                        //Now, execute task with current thread.
                        runnable.run();                
                        
                        System.out.println(Thread.currentThread().getName()
                                      +" has EXECUTED task.");
                        
                        /*
                         * 1) Check whether pool shutDown has been initiated or not,
                         * if pool shutDown has been initiated and
                         * 
                         * 2) taskQueue does not contain any
                         *    unExecuted task (i.e. taskQueue's size is 0 )
                         * 
                         *   interrupt the thread.
                         */
                        if(this.threadPool.isPoolShutDownInitiated()
                                      &&  this.taskQueue.size()==0){
                               this.interrupt();
                               /*
                                *  Interrupting basically sends a message to the thread
                                *  indicating it has been interrupted but it doesn't cause
                                *  a thread to stop immediately,
                                * 
                                *  if sleep is called, thread immediately throws InterruptedException
                                */
                               Thread.sleep(1);  
                        }
                        
                  }
           } catch (InterruptedException e) {
                  System.out.println(Thread.currentThread().getName()+" has been STOPPED.");
           }
    }
}
