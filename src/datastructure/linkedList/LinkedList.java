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
		boolean $ret = false;
		
		if(_count == 0)
		{
			return $ret;
		}
		else
		{
			Node<T> currentNode = _head;
			Node<T> previousNode = null;
			
			while(currentNode != null)
			{
				if(currentNode.value.equals(val))
				{
					//removing the head
					if(_head == currentNode)
					{
						_head = _head.next;
						currentNode.next = null;
					}
					
					//otherwise removing element in the list
					else
					{
						Node<T> nextNode = currentNode.next;
						previousNode = currentNode;
						previousNode.next = nextNode;

						//not the last element
						if(nextNode != null)
						{	
							currentNode.next = null;
						}
						else //removing the tail
						{
							_tail = previousNode;
						}
					}
					_count--;
					$ret = true;	
				}
				previousNode = currentNode;
				currentNode = currentNode.next;
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

	    while (currentNode != null) 
	    {
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
