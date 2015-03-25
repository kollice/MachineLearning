package com.kollice.machinelearning.share;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utility {
	public static List<String[]> splitData(List<String[]> data, int axis,
			String value) throws Exception {
		List<String[]> resultList = null;
		if (data == null || data.size() == 0) {
			throw new Exception("数据集为空！\n");
		}
		resultList = new ArrayList<String[]>();
		for (String[] item : data) {
			if ((value == null && item[axis] == null)
					|| value.equals(item[axis].toString())) {
				resultList.add((String[]) deleteArrayItemAtIndex(item, axis));
			}
		}
		return resultList;
	}

	public static Object[] deleteArrayItemAtIndex(Object[] array, int index) {
		Object[] temp = array.clone();
		for (int i = index; i < array.length - 1; i++) {
			temp[i] = temp[i + 1];
		}
		return Arrays.copyOfRange(temp, 0, temp.length - 1);
	}

	public static List<String[]> readDataFile(String filePath) {
		List<String[]> resultList= new ArrayList<String[]>();
		InputStreamReader read = null;
		BufferedReader bufferedReader = null;
		try {
			String encoding = "GBK";
			File file = new File(filePath);
			if (file.isFile() && file.exists()) {
				read = new InputStreamReader(
				new FileInputStream(file), encoding);
				bufferedReader = new BufferedReader(read);
				String lineTxt = null;
				while ((lineTxt = bufferedReader.readLine()) != null) {
					String[] lineData = lineTxt.split("\t");
					resultList.add(lineData);
				}
			} else {
				System.out.println("找不到指定的文件！");
			}
		} catch (Exception e) {
			System.out.println("读取文件内容出错！");
			e.printStackTrace();
		} finally {
			if (read != null) {
				try {
					read.close();
					bufferedReader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return resultList;
	}
	
	public static String readFileToString(String filePath) {
		String result = null;
		InputStreamReader read = null;
		BufferedReader bufferedReader = null;
		try {
			String encoding = "GBK";
			File file = new File(filePath);
			if (file.isFile() && file.exists()) {
				read = new InputStreamReader(
				new FileInputStream(file), encoding);
				bufferedReader = new BufferedReader(read);
				String lineTxt = null;
				while ((lineTxt = bufferedReader.readLine()) != null) {
					if (result == null && lineTxt != null && lineTxt.length() > 0) {
						result ="";
					}
					result += lineTxt;
				}
			} else {
				System.out.println("找不到指定的文件！");
			}
		} catch (Exception e) {
			System.out.println("读取文件内容出错！");
			e.printStackTrace();
		} finally {
			if (read != null) {
				try {
					read.close();
					bufferedReader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}
	
	/**
	 * 对数据集进行去重的操作，返回唯一值的列表。
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public static List<String> deduplication(List<String[]> data) throws Exception {
		if (data == null || data.size() == 0) {
			throw new Exception("数据集为空！\n");
		}
		List<String> result = new ArrayList<String>();
		for (String[] items : data) {
			for(String item : items) {
				if (!result.contains(item)) {
					result.add(item);
				}
			}
		}
		return result;
	}
	
	public static List<String> textParse(String str) throws Exception {
		if (str == null || str.length() == 0) {
			return null;
		}
		Pattern pattern = Pattern.compile("\\w*");
		Matcher matcher = pattern.matcher(str.toLowerCase());
		List<String> resultList = new ArrayList<String>();
		while (matcher.find()) {
			if (matcher.group().length() > 2) {
				resultList.add(matcher.group());
			}
		}
		return resultList;
	}
}
