package concurrency.threads.threadpool;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class DynamicThreadPool {
	
	private AtomicInteger threadCount;
	private AtomicBoolean execute;
	private BlockingQueue <Runnable> queue;
	
	private int corePoolSize;
	private int maximumPoolSize;
	private int keepAliveTime;

	private ThreadPoolExecutor threadPoolExecutor;
	
	public DynamicThreadPool(int maxSize, int keepAliveTime){
		validateParameters(maxSize, keepAliveTime);
		
		queue = new LinkedBlockingQueue<Runnable>();

		threadPoolExecutor = new ThreadPoolExecutor(
			    corePoolSize, maximumPoolSize, 
			    keepAliveTime, 
			    TimeUnit.SECONDS, 
			    queue);

		threadPoolExecutor.allowCoreThreadTimeOut(true);
	}


	
	/**
     * Execute the task, task must be of Runnable type.
     */
    public synchronized void  execute(Runnable task) throws Exception{
        
        if(threadPoolExecutor.isShutdown()){
        	System.out.println("Thread pool " + "" + " is being Shutdown.\n"
        					+ "No tasks are allowed to be scheduled.");
        
        }
        //add the task to the queue
        //this will cause a thread in the work pool to pick this up and execute
        queue.put(task);
        System.out.println("task has been added.");
    }
    
    public synchronized void shutdown()throws Exception{
    	threadPoolExecutor.shutdown();
    	if(threadPoolExecutor.awaitTermination(120, TimeUnit.SECONDS)){
    		//all threads complete
    	}
    	else{
    		//log the error.
    	}
    	
    }
    
    
    /**
     * Helper methods
     * 
     */
	private void validateParameters(int maxSize, int keepAliveTime) {
		if(validateSize(maxSize)){
			corePoolSize = maxSize;
			maximumPoolSize = maxSize;
			this.keepAliveTime = keepAliveTime;
		}
		if(validateKeepAlive(keepAliveTime)){
			this.keepAliveTime = keepAliveTime;
		}
	}

	private boolean validateSize(int maxSize) {
		if(maxSize < 0){
			this.corePoolSize = 1;
			this.maximumPoolSize = 1;
			System.out.println("Invalid value for thread pool size, using default 1.");
			return false;
		}

		return true;
	}
	
	private boolean validateKeepAlive(int keepAlive){
		if(keepAlive < 0){
			this.keepAliveTime = 60;
			System.out.println("Invalid value for max thread lifetime, using default " + 
					this.keepAliveTime+ " seconds.");
			return false;
		}
		return true;
	}

}
