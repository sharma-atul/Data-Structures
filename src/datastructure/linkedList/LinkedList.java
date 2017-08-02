package datastructure.linkedList;

public class LinkedList<T>{
	
	class Node<T>
	{
		T value;
		Node next;
	}
	
	Node<T> _head;
	Node<T> _tail;
	int _count;
	
	public LinkedList()
	{
		_head = null;
		_tail = null;
		_count = 0;
	}
	
	public void add(Node<T> n)
	{
		if(_count == 0)
		{
			_head = n;
			_tail = _head;
		}
		else
		{
			_tail.next = n;
			n.next = null;
			_tail = n;
		}
		_count++;
	}
	
	public boolean remove(T val)
	{
		if(_count == 0) return false;
		boolean $ret = false;
		
		Node<T> pointer = _head;
		while(pointer.next != null && pointer != null){
			if(pointer.next.value.equals(val)){
				$ret = true;
				pointer.next = pointer.next.next;
				_count--;
			}else{
				pointer = pointer.next;
			}	
		}
		return $ret;
	}
	
	//pass in head as parameter
	public void reverseListIterative(Node<T> node) {
	    if (node == null || node.next == null) {
	        return;
	    }
	    Node<T> currentNode = node;
	    Node<T> previousNode = null;
	    Node<T> nextNode = null;
//a->b->c
	    while (currentNode != null){
	        nextNode = currentNode.next;
	        currentNode.next = previousNode;
	        previousNode = currentNode;
	        currentNode = nextNode;
	    }
	    //exit loop when currentNode == null (ie. one past the tail)
	    //so previousNode is the tail
	    //so make it the head of now reversed list.
	    _head = previousNode;
	}
	
	public void reverseListRecursive(Node<T> node)
	{
		 //check for empty list 
		 if(node == null || _count == 0)
		    return;

		/* if we are at the TAIL node:
		    recursive base case:
		 */
		if(node.next == null) 
		{ 
			//set HEAD to current TAIL since we are reversing list
			_head = node; 
			return; //base case;
		}

		reverseListRecursive(node.next);
		node.next.next = node;
		node.next = null; //set the old next pointer to null
	}
	
	public static void main(String[] args)
	{
		LinkedList<String> list = new LinkedList<String>();
	}
}
