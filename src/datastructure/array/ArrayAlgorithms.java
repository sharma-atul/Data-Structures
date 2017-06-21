package datastructure.array;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class ArrayAlgorithms {

	/**
	 * Function that returns the kth smallest element in an ArrayList A
	 * 
	 * @param A - array to search
	 * @param k - the value to find the kth smallest element
	 * @return
	 */
	public int QuickSelect(ArrayList<Integer> A, int k)
	{
		if(A.size() == 0)
		{
			return -1;
		}
		
		int random = (int) (Math.random() % A.size());
		int pivot = A.get(random);
		
		ArrayList<Integer> smaller = new ArrayList<Integer>();
		ArrayList<Integer> larger = new ArrayList<Integer>();
		
		for(Integer i : A)
		{
			if(i.intValue() < pivot)
			{
				smaller.add(i);
			}
			else if(i.intValue() > pivot)
			{
				larger.add(i);
			}
		}
		
		if(k <= smaller.size())
		{
			return QuickSelect(smaller,k);
		}
		else if( k > (A.size() - smaller.size()))
		{
			return QuickSelect(larger, k-(A.size() - smaller.size()));
		}
		
		return pivot;
	}
	
	/**
	 * Given array of unsorted numbers, find longest sequence of consecutive
	 * numbers
	 * 
	 * Must be in O(N) time.
	 * 
	 * @param num
	 * @return
	 */
	public static int longestConsecutiveSequence(int[] num) {
		// if array is empty, return 0
		if (num.length == 0) {
			return 0;
		}
	 
		Set<Integer> set = new HashSet<Integer>();
		int max = 1;
		//add all numbers to a set
		for (int e : num)
			set.add(e);
		//find each number, find  "right" and "left" conseq.
		for (int e : num) {
			int left = e - 1;
			int right = e + 1;
			int count = 1;
	 
			while (set.contains(left)) {
				count++;
				set.remove(left);
				left--;
			}
	 
			while (set.contains(right)) {
				count++;
				set.remove(right);
				right++;
			}
	        //max of what we just saw vs. prev max
			max = Math.max(count, max);
		}
	 
		return max;
	}
	
	
	/**
	 * 
	 * Given an array of strings, find the longest common prefix amongst them.
	 * @param strs
	 * @return
	 */
	public String longestCommonPrefix(String[] strs) {
	    if(strs == null || strs.length == 0)
	        return "";
	 
	    //find the shortest string
	    int minLen=Integer.MAX_VALUE;
	    for(String str: strs){
	        if(minLen > str.length())
	            minLen = str.length();
	    }
	    
	    if(minLen == 0) return "";
	 
	    //
	    for(int j=0; j<minLen; j++){
	        char prev='0';
	        //loop that goes over all strings
	        for(int i=0; i<strs.length ;i++){
	        	//for first string, prev char is at j.
	            if(i==0) {
	                prev = strs[i].charAt(j);
	                continue;
	            }
	            //if at this string we find a mismatch
	            // then we know we found max prefix.
	            if(strs[i].charAt(j) != prev){
	                return strs[i].substring(0, j);
	            }
	        }
	    }
	    //by default take first string at maximum prefix since we didn't 
	    //find mismatch.
	    return strs[0].substring(0,minLen);
	}
	
	//How to find the maximum sub-array summation in a sorted 
	//array.
	//O(N)
	//Explanation.  
	// Base case : max = A[0].
	//
	// sum[i] = max(sum[i-1] + A[i], A[i])
	// max = max(curr_max,sum[i]).
	public int maxSubArray(int[] A) {
		int max = A[0];
		int[] sum = new int[A.length];
		sum[0] = A[0];
 
		for (int i = 1; i < A.length; i++) {
			sum[i] = Math.max(A[i], sum[i - 1] + A[i]);
			max = Math.max(max, sum[i]);
		}
 
		return max;
	}
	
	/**
	 * a={1,1,2,2,2,3}
	 * Remove duplicates
	 */
	public int[] removeDuplicates(int[] a)
	{
		if (a.length < 2)
			return a;
		
		int i = 0;
		int j = 1;
		
		while (j < a.length)
		{
			//if match move pointer right
			if(a[i] == a[j])
			{
				j++;
			}
			else
			//put new unique element as far left 
		    //but after prev analyzed elements
			{	i++;
				a[i] = a[j];
				j++;
			}
		}
		
		int[] b = Arrays.copyOf(a, j+1);
		return b;
	}
	
	
}
