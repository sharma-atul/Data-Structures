package datastructure.tree;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class TreeAlgorithms
{
	public static <T> List<T> preOrderTraversal(TreeNode<T> root)
	{
		List<T> $ret = Collections.emptyList();

		if(root==null)
		{
			return $ret;
		}

		//pre
		$ret.add(root.getValue());
		$ret.addAll(preOrderTraversal(root.getLeft()));
		$ret.addAll(preOrderTraversal(root.getRight()));

		return $ret;
	}
	
	public static <T> List<T> inOrderTraversal(TreeNode<T> root)
	{
		List<T> $ret = Collections.emptyList();

		if(root==null)
		{
			return $ret;
		}
		
		$ret.addAll(inOrderTraversal(root.getLeft()));
		$ret.add(root.getValue());
		$ret.addAll(inOrderTraversal(root.getRight()));
		return $ret;
	}
	
	public static <T> List<T> postOrderTraversal(TreeNode<T> root)
	{
		List<T> $ret = Collections.emptyList();

		if(root==null)
		{
			return $ret;
		}
		//post
		$ret.addAll(postOrderTraversal(root.getLeft()));
		$ret.addAll(postOrderTraversal(root.getRight()));
		$ret.add(root.getValue());

		return $ret;
	}
	
	public static <T> int findMinDepth(TreeNode<T> root)
	{
		if(root == null){
            return 0;
        }
 
        LinkedList<TreeNode<T>> nodes = new LinkedList<TreeNode<T>>();
        LinkedList<Integer> counts = new LinkedList<Integer>();
 
        nodes.add(root);
        counts.add(1);
 
        while(!nodes.isEmpty()){
            TreeNode<T> curr = nodes.remove();
            int count = counts.remove();
 
            if(curr.getLeft() != null){
                nodes.add(curr.getLeft());
                counts.add(count+1);
            }
 
            if(curr.getRight() != null){
                nodes.add(curr.getRight());
                counts.add(count+1);
            }
 
            if(curr.getLeft() == null && curr.getRight() == null){
                return count;
            }
        }
 
        return 0;
		
	}
}
