package com.ID3;

import java.util.ArrayList;

public class TreeNode {
	//父节点
	TreeNode parent;
	
	//指向父节点分类的那个属性名称
	String parentAttribute;
	
	//结点名称
	String nodeName;
	
	//该节点名称分类指定的属性值数组
	ArrayList<String> attributes=new ArrayList<String>();
	
	//该节点的子节点数组
	ArrayList<TreeNode> childNodes=new ArrayList<TreeNode>();

}
