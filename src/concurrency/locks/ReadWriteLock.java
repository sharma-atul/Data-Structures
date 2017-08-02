package concurrency.locks;

/**
 * A simple implementation of a ReadWrite Lock
 * @author chhibba
 *
 */
public class ReadWriteLock implements ReadWriteLockIntf{
	protected int numReaders = 0;
	protected int numWriters = 0;
	protected int numWriteRequests = 0;
	
	public ReadWriteLock(){
		
	}
	
	/**
	 * If there are no writers or write requests then allow through 
	 * and increment number of writers
	 * @throws InterruptedException
	 */
	public synchronized void lockRead() throws InterruptedException{

		while(areWriters() || areWriteRequests()){
			wait();
		}
		numReaders++;
	}
	
	/**
	 * Decrement number of readers after unlocking AND
	 * notify all threads waiting on lock.
	 */
	public synchronized void unlockRead(){
		numReaders--;
		notifyAll();
	}

	/**
	 * Initially we increment write requests (decrement when allowed to acquire lock)
	 * 
	 * If there are readers or writers, 
	 */
	public synchronized void lockWrite() throws InterruptedException{
		numWriteRequests++;
		while(areWriters() || areReaders()){
			wait();
		}
		numWriteRequests--;
		numWriters++;
	}

	/**
	 * Decrement the number of writers when done with the lock AND
	 * notify all threads waiting on lock.
	 */
	public synchronized void unlockWrite() throws InterruptedException{
		numWriters--;
		notifyAll();
	}

	/**
	 * Check whether there are any current threads reading
	 * @return
	 */
	protected synchronized boolean areReaders(){
		return numReaders > 0;
	}
	/**
	 * Check wherther there are any current threads requesting write access
	 * @return
	 */
	protected synchronized boolean areWriteRequests(){
		return numWriteRequests > 0;
	}
	
	/**
	 * Check wherther there are any current threads requesting write access
	 * @return
	 */
	protected synchronized boolean areWriters(){
		return numWriters > 0;
	}
}
