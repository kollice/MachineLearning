package com.kollice.machinelearning.chapter6;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.kollice.machinelearning.share.TreeNode;
import com.kollice.machinelearning.share.Utility;

public class Assistant {
	public static int chooseBestFeature(List<String[]> data) throws Exception {
		int bestFeature = -1;
		if (data == null || data.size() == 0) {
			throw new Exception("数据集为空！\n");
		}
		int featuresNum = data.get(0).length - 2;
		double baseEntropy = Shannon.calcShannonEnt(data);
		double bestInfoGain = 0.0;
		Set set = null;
		for (int i = 0; i <= featuresNum; i++) {
			set = new HashSet<String>();
			for (String[] example : data) {
				set.add(example[i]);
			}
			double currentEntropy = 0.0;
			Iterator<String> iterator = set.iterator();
			while (iterator.hasNext()) {
				String value = iterator.next();
				List subData = Utility.splitData(data, i, value);
				double prob = subData.size() / (data.size() * 1.0);
				currentEntropy += prob * Shannon.calcShannonEnt(subData);
			}
			double infoGain = baseEntropy - currentEntropy;
			if (infoGain > bestInfoGain) {
				bestInfoGain = infoGain;
				bestFeature = i;
			}
		}
		return bestFeature;
	}

	public static TreeNode majorityNode(List<String> data) throws Exception {
		if (data == null || data.size() == 0) {
			throw new Exception("数据集为空！\n");
		}
		TreeNode resultNode = new TreeNode();
		resultNode.setValue("-");
		Map counter = new HashMap<>();
		for (String item : data) {
			if (counter.containsKey(item)) {
				counter.put(item, Integer.parseInt(counter.get(item).toString()) + 1);
			} else {
				counter.put("item", 0);
			}
		}
		int maxValue = -1;
		String keyString = "";
		
		for(Object key:counter.keySet()){
			if (Integer.parseInt(counter.get(key).toString()) > maxValue) {
				maxValue = Integer.parseInt(counter.get(key).toString());
				keyString = key.toString();
			}
		}
		
		if (!"".equals(keyString)) {
			resultNode.setName(keyString);
		}
		return resultNode;
	}
	
	public static List getLastFeatureList(List<String[]>data) throws Exception {
		List resultList = new ArrayList<String>();
		if (data == null || data.size() == 0) {
			throw new Exception("数据集为空！\n");
		}
		for(String[] item:data) {
			resultList.add(item[item.length -1]);
		}
		return resultList;
	}
	
	public static boolean isUniqueValue(List<String> list) throws Exception {
		if (list == null || list.size() == 0) {
			throw new Exception("数据集为空！\n");
		}
		String base = list.get(0);
		for (String item:list){
			if (!item.equals(base)) {
				return false;
			}
		}
		return true;
	}
	
	public static Set<String> getSetByIndex(List<String[]>data,int index) throws Exception{
		if (data == null || data.size() == 0) {
			throw new Exception("数据集为空！\n");
		}
		Set resultSet = new HashSet<String>();
		for (String[] item:data) {
			resultSet.add(item[index]);
		}
		return resultSet;
	}
	
	public static String classify(TreeNode tree,String[] sample) throws Exception {
		String resultString= null;
		if (tree == null || sample == null) {
			throw new Exception("数据不全，无法进行分类！\n");
		}
//		if (sample[0].equals(tree.getValue())) {
			if (sample.length == 0) {
				resultString = tree.getName();
			} else {
				String temp = sample[0];
				List list = tree.getChildren();
				if (list == null) {
					throw new Exception("样本数据维度高于树的分类维度！\n");
				}
				for (TreeNode item:(List<TreeNode>)list){
					if(temp.equals(item.getValue())) {
						if (sample.length > 1) {
							return classify(item,Arrays.copyOfRange(sample, 1, sample.length));
						} else {
							String[] arr ={};
							return classify(item,arr);
						}
						
					}
				}
				throw new Exception("存在树以外的值，无法进行分类！\n");
			}
//		} else {
//			throw new Exception("存在树以外的值，无法进行分类！\n");
//		}
		
		return resultString;
	}
}
