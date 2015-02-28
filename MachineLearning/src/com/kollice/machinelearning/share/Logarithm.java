package com.kollice.machinelearning.share;

public class Logarithm {
	public static double log(double value, double base) {
		return Math.log(value) / Math.log(base);
	}

	public static double log2(double value) {
		return log(value, 2.0);
	}

	public static double log10(double value) {
		return log(value, 10.0);
	}
}
