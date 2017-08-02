package datastructure.array;

public class BinaryHeap {
	private int[] data;
	private int size;
	
	public BinaryHeap(int size){
		this.size = size;
		data = new int[this.size];
	}
	
	private int getLeftChildIndex(int parentIndex){	
		return (parentIndex*2) + 1;
	}
	private int getRightChildIndex(int parentIndex){
		return (parentIndex*2) + 2;
	}
	private int getParentIndex(int childIndex){
		return (childIndex -1) / 2;
	}

	public int getMinimum() {
		if (isEmpty())
			throw new HeapException("Heap is empty");
		else
			return data[0];
	}

	public boolean isEmpty() {
		return (size == 0);
	}
	
	public void insert(int value){
		if(size == data.length) throw new HeapException("Heap is full.");
		
		data[size-1] = value;
		size++;
		heapifyUp();
	}
	
	private void heapifyUp() {
		int parentIndex,temp;
		
		int currentIndex = (size-1);
		
		while(currentIndex > 0){
			parentIndex = getParentIndex(currentIndex);
			//swap
			if(data[parentIndex] > data[currentIndex]){
				temp  = data[parentIndex];
				data[parentIndex] = data[currentIndex];
				data[currentIndex] = temp;
				currentIndex = parentIndex;
			}
			else{ 
				//if no swap, then done.
				currentIndex = -1;
				break;
			}
		}
	}

	public int removeMin(){
		int ret;
		if (isEmpty())
			throw new HeapException("Heap is empty");
		else {
			ret = data[0];
			data[0] = data[size - 1];
			size--;
			if (size > 0)
				heapifyDown(0);
		}
		return ret;
	}
	
    private void heapifyDown(int nodeIndex) {
        int leftChildIndex, rightChildIndex, minIndex, tmp;
        
        leftChildIndex = getLeftChildIndex(nodeIndex);
        rightChildIndex = getRightChildIndex(nodeIndex);
        // REMEMBER there can't be a node with ONLY A RIGHT CHILD
        // VIOLATES HEAP BEING COMPLETE TREE
        // EITHER NO CHILD, ONLY LEFT CHILD, OR BOTH CHILDREN
        if (rightChildIndex >= size) { //no right
              if (leftChildIndex >= size)
                    return; //no right and no left
              else{
            	  //no right only left.
                    minIndex = leftChildIndex;
              }
        } else { //have right and left
              if (data[leftChildIndex] <= data[rightChildIndex])
                    minIndex = leftChildIndex; //left is smallest
              else
                    minIndex = rightChildIndex; //right is smallest
        }
        
        if (data[nodeIndex] > data[minIndex]) {//swap
              tmp = data[minIndex];
              data[minIndex] = data[nodeIndex];
              data[nodeIndex] = tmp;
              heapifyDown(minIndex);
        }
  }
    
	public class HeapException extends RuntimeException{
		
		private static final long serialVersionUID = 4372679978117450788L;

		public HeapException(String s){
			super(s);
		}
	}
}
