package datastructure.hashmap;

import java.lang.Integer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;


public class HashMap<Integer,V> {
	private final int SIZE_OF_TABLE = 64;
	
	private class HashEntry { 
		private Integer key;
		private V value; 
		public HashEntry(Integer key, V value) 
		{ 
			this.key = key; 
			this.value = value; 
		} 
		public Integer getKey(){ return key;}
		public V getValue() { return value;}
	}
	
	List<List<HashEntry>> table = new ArrayList<List<HashEntry>>(SIZE_OF_TABLE);

	public V get(Integer key)
			throws NoSuchElementException{

		int index = hashCode(key); 
		if (table.get(index) != null) { 
			for(HashEntry entry : table.get(index)){ 
				if(entry.getKey().equals(key))
					return entry.getValue(); 
			} 
		} 

		throw new NoSuchElementException("The key: \"" + key + 
				"\" was not found in the map!"); 
	}

	public void put(Integer key, V value) {
		//calculate the hashCode 
		int index = hashCode(key); //mod the size of the map
		boolean existingBucket = true;
		//new thing to put into hashmap
		HashEntry hashEntry = new HashEntry(key, value); 

		List<HashEntry> chain = table.get(index);
		//new bucket.
		if (chain == null) {
			existingBucket = false;
			chain = new ArrayList<>(); 
		} 
		Iterator<HashEntry> it = chain.iterator(); 

		while(it.hasNext()){ 
			if(it.next().getKey().equals(key)){ 
				it.remove(); //duplicate key, remove/replace
				break; 
			} 
		} 
		chain.add(hashEntry); 
		
		if(!existingBucket){
			//shouldn't be any consequences because we know there 
			//was nothing here to begin with
			table.add(index, chain);
		}
	}

	private int hashCode(Integer key) {
		int h = ((java.lang.Integer) key).intValue();
		return (int)(h & java.lang.Integer.MAX_VALUE - 1) % SIZE_OF_TABLE;
	}

}
