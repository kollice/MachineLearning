package com.kollice.machinelearning.chapter6;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.kollice.machinelearning.share.Logarithm;

public class Shannon {
	public static double calcShannonEnt(List<String[]> types) throws Exception {
		double result = 0.0;
		if (types == null || types.size() == 0) {
			throw new Exception("∑÷¿‡Œ™ø’£°\n");
		}
		int count = types.size();
		Map typeMap = new HashMap<>();
		for (String[] item : types) {
			String itemString = item[item.length - 1];
			if (typeMap.containsKey(itemString)) {
				typeMap.put(itemString, Integer.parseInt(typeMap.get(itemString).toString()) + 1);
			} else {
				typeMap.put(itemString, 1);
			}
		}
		for (Object key : typeMap.keySet()) {
			double prob = Integer.parseInt(typeMap.get(key).toString())/((double)count);
			result -= prob * Logarithm.log2(prob);
		}
		return result;
	}
}
