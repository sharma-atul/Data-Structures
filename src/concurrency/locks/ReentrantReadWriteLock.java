package concurrency.locks;

import java.util.HashMap;
import java.util.Map;

/**
 * A re-entrant ReadWrite Lock
 * 
 * This means 
 * 
 * if the thread N has a read lock, it can acquire the read lock again.
 * if the thread N has a write lock, it can acquire another write lock.
 * if the thread N has a read lock, it can acquire a WRITE lock as well.
 * 
 * @author chhibba
 *
 */
public class ReentrantReadWriteLock implements ReadWriteLockIntf{
	/** 
	 * INSTANCE VARIABLES 
	 */
	int numWriters = 0;
	int numWriteRequests = 0;
	private Map<Thread, Integer> readThreads = new HashMap<Thread,Integer>();
	private Thread writingThread;
	
	/**
	 * STATIC INITIALIZATION
	 */
	private static ReentrantReadWriteLock INSTANCE;

	private ReentrantReadWriteLock(){

	}

	public static ReentrantReadWriteLock getInstance(){
		if( INSTANCE == null)
			INSTANCE = new ReentrantReadWriteLock();
		return INSTANCE;
	}

	/**
	 * PUBLIC METHODS
	 */


	public synchronized void lockRead() throws InterruptedException{
		Thread callingThread = Thread.currentThread();
		//wait to be granted read access
		while(! canGrantReadAccess(callingThread)){
			wait();
		}
		// if granted, increment read access count for this thread
		readThreads.put(callingThread,
				(getReadAccessCount(callingThread) + 1));
	}


	public synchronized void unlockRead(){
		Thread callingThread = Thread.currentThread();
		//SAFETY CHECK
		// Ensure the unlocker of the read lock has acquired the read lock
		if(!isReaderThread(callingThread)){
			throw new IllegalMonitorStateException("Calling Thread does not" +
					" hold a read lock on this ReadWriteLock");
		}
		
		//Clean up number of read accesses this thread has.
		int accessCount = getReadAccessCount(callingThread);
		
		if(accessCount == 1){ 
			//if this thread has only one remaining read access
			readThreads.remove(callingThread); 
		}
		else { //decrement the number of read access this thread has
			readThreads.put(callingThread, (accessCount -1)); 
		}
		//notify those who might be waiting
		notifyAll();
	}

	public synchronized void lockWrite() throws InterruptedException{
		numWriteRequests++;
		Thread callingThread = Thread.currentThread();
		//Wait until we can be granted write access
		while(! canGrantWriteAccess(callingThread)){
			wait();
		}
		//requests down, write access up, record the thread as the writer
		numWriteRequests--;
		numWriters++;
		writingThread = callingThread;
	}

	
	public synchronized void unlockWrite() throws InterruptedException{
		//SAFETY CHECK
		// Ensure the unlocker of the write lock is the current holder of the write lock
		if(!isWriterThread(Thread.currentThread())){
			throw new IllegalMonitorStateException("Calling Thread does not" +
					" hold the write lock on this ReadWriteLock");
		}
		 
		//decrement number of writers and SHOULD ALWAYS lead to no writing thread.
		if(--numWriters == 0){
			writingThread = null;
		}
		//notify those who might be waiting
		notifyAll();
	}





	/**
	 * PRIVATE METHODS
	 */

/**
 * The conditions on blocking read access are: 
 * 
 * 1. there is an active writer that is not callingThread
 * OR
 * 2. callingThread does not have read access
 *      and there are threads requesting write access.
 *  
 *
 * @param callingThread
 * @return
 */
	private boolean canGrantReadAccess(Thread callingThread){
		if( isWriterThread(callingThread)) 
			return true;
		if( hasActiveWriter()) 
			return false;
		if( isReaderThread(callingThread)) 
			return true;
		if( areWriteRequests()) 
			return false;
		
		return true;
	}

	/**
	 * The conditions on blocking write access are
	 *
	 * 1. There is more than one thread currently with read access 
	 *	    OR
	 * 	  There is one thread with read access and it isn't callThread.
	 * 
	 * 2. There is a thread currently with write access that isn't callingThread.
	 *   
	 * @param callingThread is the thread calling the lock for write access
	 * @return
	 */
	private boolean canGrantWriteAccess(Thread callingThread){

		if(isOnlyReaderThread(callingThread))    
			return true;
		if(areReaders())                   
			return false;
		if(writingThread == null)          
			return true;
		if(!isWriterThread(callingThread))       
			return false;
		//default return true
		return true;
	}

	private int getReadAccessCount(Thread callingThread){
		Integer getReadAccessCount = readThreads.get(callingThread);
		if(getReadAccessCount == null) return 0;
		return getReadAccessCount.intValue();
	}




	private boolean isReaderThread(Thread callingThread){
		return readThreads.get(callingThread) != null;
	}

	private boolean isOnlyReaderThread(Thread callingThread){
		return readThreads.size() == 1 &&
				readThreads.get(callingThread) != null;
	}

	private boolean hasActiveWriter(){
		return writingThread != null;
	}

	private boolean isWriterThread(Thread callingThread){
		return writingThread == callingThread;
	}

	private boolean areReaders(){
		return readThreads.size() > 0;
	}

	/**
	 * Check wherther there are any current threads requesting write access
	 * @return
	 */
	private synchronized boolean areWriteRequests(){
		return numWriteRequests > 0;
	}

	/**
	 * Check wherther there are any current threads requesting write access
	 * @return
	 */
	private synchronized boolean areWriters(){
		return numWriters > 0;
	}
}
