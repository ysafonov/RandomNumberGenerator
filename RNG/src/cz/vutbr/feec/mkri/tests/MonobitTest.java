package cz.vutbr.feec.mkri.tests;

import java.util.ArrayList;

public abstract class MonobitTest {
	
	private static int requiredCountOfValues = 20000;
	
	public static double applyMonobitTest(ArrayList<Integer> outputofRNG) {
		double result = 0;
		for (int i = 0; i < requiredCountOfValues; i++) {
			result = result + outputofRNG.get(i)%2;
		}
		return result;
	}
	
	public static boolean isMonobitTestPassed(double toCompare) {
		if ((9725 < toCompare) && (toCompare < 10275)) {
			return true;
		} else
			return false;
	}
	
}
