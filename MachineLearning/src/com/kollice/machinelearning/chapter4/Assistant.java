package com.kollice.machinelearning.chapter4;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.kollice.machinelearning.share.Logarithm;

public class Assistant {
	public static int[] genDataVector(List<String> dict, List<String> data)
			throws Exception {
		if (dict == null || dict.size() == 0) {
			throw new Exception("字典为空！\n");
		}
		if (data == null || data.size() == 0) {
			throw new Exception("数据集为空！\n");
		}
		int[] result = new int[dict.size()];
		for (String item : data) {
			if (dict.contains(item)) {
				result[dict.indexOf(item)] = 1;
			} else {
				System.out.println(String.format(
						"the word: %s is not in my Vocabulary!", item));
			}
		}
		return result;
	}
	
	public static int[] genBagDataVector(List<String> dict, List<String> data)
			throws Exception {
		if (dict == null || dict.size() == 0) {
			throw new Exception("字典为空！\n");
		}
		if (data == null || data.size() == 0) {
			throw new Exception("数据集为空！\n");
		}
		int[] result = new int[dict.size()];
		for (String item : data) {
			if (dict.contains(item)) {
				result[dict.indexOf(item)] += 1;
			} else {
				System.out.println(String.format(
						"the word: %s is not in my Vocabulary!", item));
			}
		}
		return result;
	}

	/**
	 * 根据训练数据产生的单词存在与否矩阵和训练分类向量利用条件概率计算出文档属于各个分类的概率
	 * 
	 * @param matrix
	 *            根据训练数据产生的单词存在与否矩阵
	 * @param category
	 *            训练分类向量
	 * @return Map (p0Vect：属于分类值为0的概率；p1Vect：属于分类值为0的概率；pAbusive：文档属于分类值为1的概率)
	 * @throws Exception
	 */
	public static Map getTrainProbability(int[][] matrix, int[] category)
			throws Exception {
		if (matrix == null || matrix.length == 0) {
			throw new Exception("单词存在矩阵为空！\n");
		}
		if (category == null || category.length == 0) {
			throw new Exception("训练分类为空！\n");
		}
		Map result = new HashMap<String, Double>();

		int docNum = matrix.length;
		int wordNum = matrix[0].length;
		int categorySum = 0;
		for (int item : category) {
			categorySum += item;
		}

		Double pAbusive = new Double(categorySum / (docNum * 1.0));
		result.put("pAbusive", pAbusive);

		int[] p0Nums = new int[wordNum];
		int[] p1Nums = new int[wordNum];

		for (int i = 0; i < wordNum; i++) {
			p0Nums[i] = 1;
			p1Nums[i] = 1;
		}

		double p0Denom = 2.0;
		double p1Denom = 2.0;

		for (int i = 0; i < docNum; i++) {
			if (category[i] == 1) {
				for (int j = 0; j < wordNum; j++) {
					p1Nums[j] += matrix[i][j];
					p1Denom += matrix[i][j];
				}
			} else {
				for (int j = 0; j < wordNum; j++) {
					p0Nums[j] += matrix[i][j];
					p0Denom += matrix[i][j];
				}
			}
		}

		double[] p1Vect = new double[wordNum];
		double[] p0Vect = new double[wordNum];

		for (int k = 0; k < wordNum; k++) {
			p1Vect[k] = Logarithm.ln(p1Nums[k] / p1Denom);
			p0Vect[k] = Logarithm.ln(p0Nums[k] / p0Denom);
		}

		result.put("p1Vect", p1Vect);
		result.put("p0Vect", p0Vect);

		return result;
	}

	public static int classfic(int[] data, Map trainer) throws Exception {
		int result = -1;
		if (data == null || data.length == 0) {
			throw new Exception("数据集为空！\n");
		}
		if (trainer == null || null == trainer.get("p1Vect")
				|| null == trainer.get("p0Vect")
				|| null == trainer.get("pAbusive")) {
			throw new Exception("训练集为空或不合法！\n");
		}
		double[] p1Vect = (double[]) trainer.get("p1Vect");
		double[] p0Vect = (double[]) trainer.get("p0Vect");
		Double pAbusive = (Double) trainer.get("pAbusive");
		double sumP1 = 0;
		double sumP0 = 0;
		for (int i = 0; i < Math.min(Math.min(p1Vect.length, p0Vect.length),
				data.length); i++) {
			sumP1 += data[i] * p1Vect[i];
			sumP0 += data[i] * p0Vect[i];
		}
		double classficByP1 = sumP1 + Logarithm.ln(pAbusive.doubleValue());
		double classficByP0 = sumP0 + Logarithm.ln(1 - pAbusive.doubleValue());
		
		if (classficByP1 > classficByP0) {
			result = 1;
		} else {
			result = 0;
		}
		return result;
	}
}
