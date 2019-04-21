package cz.vutbr.feec.mkri.tests;

import java.util.ArrayList;

/* 
* @author Pavel Mazanek; id: 185933
* @author Yehor Safonov; id: 185942
*/

public abstract class MonobitTest {
	
	private static int requiredCountOfValues = 20000;
	
	// Monobit test
	public static double applyMonobitTest(ArrayList<Integer> outputofRNG) {
		double result = 0;
		for (int i = 0; i < requiredCountOfValues; i++) {
			result = result + outputofRNG.get(i)%2;
		}
		return result;
	}
	
	//Monobit test pass check - comparing the result value with values from FIPS 140-2
	public static boolean isMonobitTestPassed(double toCompare) {
		if ((9725 < toCompare) && (toCompare < 10275)) {
			return true;
		} else
			return false;
	}
	
}
