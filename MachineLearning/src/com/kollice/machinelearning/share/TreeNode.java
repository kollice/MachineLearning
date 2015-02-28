package com.kollice.machinelearning.share;

import java.util.List;

public class TreeNode {
	private String name;
	private String value;
	private List<TreeNode> children;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public List<TreeNode> getChildren() {
		return children;
	}

	public void setChildren(List<TreeNode> children) {
		this.children = children;
	}

	public TreeNode(String name, String value, List<TreeNode> children) {
		super();
		this.name = name;
		this.value = value;
		this.children = children;
	}
	
	public TreeNode(String name, String value) {
		super();
		this.name = name;
		this.value = value;
	}

	public TreeNode() {
		super();
	}

	public String toString(String lftStr, String append) {
		StringBuilder b = new StringBuilder();
		if (value == null) {
			b.append(append + name);
		} else {
			b.append(append + value + " : " + name);
		}
		
		b.append("\n");
		if (children != null && children.size() > 0) {
			for (int i = 0; i < children.size() - 1; i++) {
				b.append(lftStr + children.get(i).toString(lftStr + "    ©¦", "    ©À"));
			}
			b.append(lftStr + children.get(children.size() - 1).toString(lftStr + "     ", "    ©¸"));
		}
		return b.toString();
	}
}
