package datastructure.tree;

public class TreeNode<T> {
	private TreeNode<T> left;
	private TreeNode<T> right;
	private T value;
	
	public TreeNode(T val)
	{
		this.value = val;
	}
	
	public void setLeft(TreeNode<T> node)
	{
		this.left = node;
	}
	
	public TreeNode<T> getLeft()
	{
		return left;
	}
	
	public void setRight(TreeNode<T> node)
	{
		this.right = node;
	}
	
	public TreeNode<T> getRight()
	{
		return right;
	}
	
	public void setValue(T val)
	{
		this.value = val;
	}
	
	public T getValue()
	{
		return value;
	}
	
}
