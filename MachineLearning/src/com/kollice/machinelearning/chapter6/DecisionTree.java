package com.kollice.machinelearning.chapter6;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import com.kollice.machinelearning.share.TreeNode;
import com.kollice.machinelearning.share.Utility;

public class DecisionTree {
	private TreeNode decisionTree;

	public TreeNode getDecisionTree() {
		return decisionTree;
	}

	public void setDecisionTree(TreeNode decisionTree) {
		this.decisionTree = decisionTree;
	}

	public DecisionTree() {
		super();
	}

	public DecisionTree(List<String[]>data,List<String> labels) throws Exception {
		super();
		this.decisionTree = createDecisionTree(data, labels);
	}

	public static TreeNode createDecisionTree(List<String[]>data,List<String> labels) throws Exception {
		TreeNode result = null;
		List lastFeatureList = Assistant.getLastFeatureList(data);
		
		if (Assistant.isUniqueValue(lastFeatureList)) {
			result = new TreeNode();
			result.setName((String)lastFeatureList.get(0));
			return result;
		}
		
		if (data.get(0).length == 1) {
			return Assistant.majorityNode(lastFeatureList);
		}
		
		int bestFeatureIndex = Assistant.chooseBestFeature(data);
		result = new TreeNode();
		result.setName(labels.get(bestFeatureIndex));
		List newLabels = new ArrayList<String>();
		newLabels.addAll(labels);
		newLabels.remove(labels.get(bestFeatureIndex));
		Set<String> set = Assistant.getSetByIndex(data,bestFeatureIndex);
		List<TreeNode> children  = new ArrayList<TreeNode>();
		for (String value:set) {
			TreeNode temp = createDecisionTree(Utility.splitData(data, bestFeatureIndex, value), newLabels);
			if (temp.getName() != null) {
				temp.setValue(value);
			}
			children.add(temp);
		}
		if (children.size() > 0) {
			result.setChildren(children);
		}
		return result;
	}
	
	public String classify(String[] sample) throws Exception {
		String resultString= null;
		try {
			resultString = Assistant.classify(this.decisionTree,sample);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultString;
	}
	
	/* *********************************************************************************************************************** */
	
	private static List<String[]> getData() {
		List<String[]> resultList = new ArrayList<String[]>();
		String[][] orgin = {{"1","1","yes"},{"1","1","yes"},{"1","0","no"},{"0","1","no"},{"0","1","no"}};
		for (String[] item1: orgin) {
			resultList.add(item1);
		}
		return resultList;
	}
	
	private static List<String> getLabel() {
		List<String> result = new ArrayList<String>();
		result.add("no surfacing");
		result.add("flippers");
		return result;
	}
	
	private static List readFile() throws Exception {
		return Utility.readDataFile("C:\\Users\\yangty\\git\\MachineLearningLocal\\MachineLearning\\src\\com\\kollice\\machinelearning\\chapter6\\sample\\lenses.txt");
	}

	private static List getFeatures() throws Exception {
		List<String> result = new ArrayList<String>();
		result.add("age");
		result.add("prescript");
		result.add("astigmatic");
		result.add("tearRate");
		return result;
	}
	
//	public static void main(String[] args) {
////		List data = getData();
////		List labels =getLabel();
////		double result;
//		try {
//			List data = readFile();
//			List labels =getFeatures();
//			
//// 			验证生成决策树
//			TreeNode treeNode = createDecisionTree(data, labels);
//			System.out.println(treeNode.toString("   ", ""));
//
////			验证香农熵计算正确性
////			result = Shannon.calcShannonEnt(data);
////			System.out.println("----------> " + result);
//		
////			验证依照特征值划分数据子集正确性
////			List list = Utility.splitData(data,1,"1");
////			for (String[] item:(List<String[]>)list){
////				System.out.println("+++++++ " + Arrays.toString(item));
////			}
//			
////			验证选择最优特征正确性
////			int axis = Assistant.chooseBestFeature(data);
////			System.out.println("-------------> " + axis);
//			
////			验证决策树对样本分类的正确性
////			DecisionTree tree = new DecisionTree(data,labels);
////			System.out.println("------------> 样本[1,0]的分类结果为：" + tree.classify("1,0".split(",")));
////			System.out.println("------------> 样本[0]的分类结果为：" + tree.classify("0".split(",")));
////			System.out.println("------------> 样本[1,1]的分类结果为：" + tree.classify("1,1".split(",")));
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
	
	/* *********************************************************************************************************************** */
}
