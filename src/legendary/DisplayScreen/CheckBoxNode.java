package legendary.DisplayScreen;

import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

import javax.swing.tree.TreeNode;

public class CheckBoxNode implements TreeNode{
	String text;

	boolean selected;
	List<TreeNode> children;
	
	public CheckBoxNode(String text, boolean selected, List<TreeNode> children) {
		this.text = text;
		this.selected = selected;
		this.children = children;
	}

	public void addChild(TreeNode child) {
		this.children.add(child);
	}
	
	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean newValue) {
		selected = newValue;
	}

	public String getText() {
		return text;
	}

	public void setText(String newValue) {
		text = newValue;
	}

	public String toString() {
		return getClass().getName() + "[" + text + "/" + selected + "]";
	}

	@Override
	public TreeNode getChildAt(int childIndex) {
		// TODO Auto-generated method stub
		return this.children.get(childIndex);
	}

	@Override
	public int getChildCount() {
		// TODO Auto-generated method stub
		return this.children.size();
	}

	@Override
	public TreeNode getParent() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getIndex(TreeNode node) {
		// TODO Auto-generated method stub
		for(int i = 0; i < this.children.size(); i++){
			if(this.children.get(i).equals(node)) {
				return i;
			}
		}
		
		return -1;
	}

	@Override
	public boolean getAllowsChildren() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isLeaf() {
		// TODO Auto-generated method stub
		return this.children.isEmpty();
	}

	@Override
	public Enumeration<TreeNode> children() {
		// TODO Auto-generated method stub
		return Collections.enumeration(this.children);
	}
}
