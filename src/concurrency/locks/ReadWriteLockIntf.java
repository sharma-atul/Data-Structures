package concurrency.locks;

public interface ReadWriteLockIntf {
	
	public abstract void lockRead() throws InterruptedException;
	public abstract void unlockRead();
	public abstract void lockWrite() throws InterruptedException;
	public abstract void unlockWrite()throws InterruptedException;

}
