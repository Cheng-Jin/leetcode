package cs.nju.edu.cn.solution;

import java.util.ArrayList;
import java.util.List;

/*
 * @description:  Given a binary tree, return the bottom-up level order traversal of its nodes' values. (ie, from left to right, level by level from leaf to root).
 * @author: Cheng Jin
 * @date: 下午9:08:22 2015年10月8日
 * 
 */
public class binaryTreeLevelOrderTraversal2 {

	public static  class TreeNode {
		int val;
		TreeNode left;
		TreeNode right;

		TreeNode(int x) {
			val = x;
		}
	}

	public List<List<Integer>> levelOrderBottom(TreeNode root) {
		List<List<Integer>> results = new ArrayList<>();
		currentLever(results, root);
		return results;
	}
	
	public void currentLever(List<List<Integer>> results, TreeNode currentNode) {
		if(currentNode == null)
			return;
		List<Integer> parent = new ArrayList<>(),
							 children = new ArrayList<>();
		parent.add(currentNode.val);
		if(currentNode.left != null){
			children.add(currentNode.left.val);
			currentLever(results, currentNode.left);
		}
		if(currentNode.right != null){
			children.add(currentNode.right.val);
			currentLever(results, currentNode.right);
		}
		results.add(parent);
		results.add(children);
		return;
	}

	public static void main(String[] args) {
	binaryTreeLevelOrderTraversal2 b2 = new binaryTreeLevelOrderTraversal2();
		TreeNode root = new TreeNode(0);
		root.left = new TreeNode(1);
		root.right = new TreeNode(2);
		System.out.println(b2.levelOrderBottom(root));
	}
}
