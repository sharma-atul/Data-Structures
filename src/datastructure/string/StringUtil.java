package datastructure.string;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class StringUtil 
{
	public static String sortWord(String word)
	{
		char[] chars = word.toCharArray();
	    List<Character> charList = new ArrayList<Character>();
	    
	    for(char c : chars)
	    {
	    	charList.add(c);
	    }

	    Collections.sort(charList);
	    
	    StringBuilder builder = new StringBuilder();
	    for (Character character : charList)
	    {
	      builder.append(character);
	    }

	    return builder.toString();
	}
	
	public static List<String> wordBreak(String s, Set<String> dict) {
	    //create an array of ArrayList<String>
	    List<String> dp[] = new ArrayList[s.length()+1];
	    dp[0] = new ArrayList<String>();
	 
	    for(int i=0; i<s.length(); i++){
	        if( dp[i] == null ) 
	            continue; 
	 
	        for(String word:dict){
	            int len = word.length();
	            int end = i+len; //figure out what bucket to fit it in.
	            if(end > s.length()) //too big to fit.
	                continue;
	 
	            if(s.substring(i,end).equals(word)){ //store the word
	                if(dp[end] == null){
	                    dp[end] = new ArrayList<String>(); //if no storage, create
	                }
	                dp[end].add(word);
	            }
	        }
	    }
	 
	    List<String> result = new LinkedList<String>();
	    if(dp[s.length()] == null) 
	        return result; 
	 
	    ArrayList<String> temp = new ArrayList<String>();
	    dfs(dp, s.length(), result, temp);
	 
	    return result;
	}
	 
	public static void dfs(List<String> dp[],int end,List<String> result, ArrayList<String> tmp){
	    if(end <= 0){
	        String path = tmp.get(tmp.size()-1);
	        for(int i=tmp.size()-2; i>=0; i--){
	            path += " " + tmp.get(i) ;
	        }
	 
	        result.add(path);
	        return;
	    }
	 
	    for(String str : dp[end]){
	        tmp.add(str);
	        dfs(dp, end-str.length(), result, tmp);
	        tmp.remove(tmp.size()-1);
	    }
	}
	
}
