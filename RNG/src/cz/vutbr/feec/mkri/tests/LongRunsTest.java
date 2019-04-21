package cz.vutbr.feec.mkri.tests;

import java.util.ArrayList;

/* 
* @author Pavel Mazanek; id: 185933
* @author Yehor Safonov; id: 185942
*/

public abstract class LongRunsTest {

	private static int requiredCountOfValues = 20000;
	
	//Long Runs test - detects if there has been a stream of 0s (gap) or 1s (block) longer than 25, if so, the test is failed
		public static double longRunsTest(ArrayList<Integer> outputofRNG){
			
			//Arraylist for checking current length of run
			ArrayList<Integer> stream = new ArrayList<Integer>();
			
			//Filling Arraylist with first 1 or 0, increasing result variable
			stream.add(outputofRNG.get(0)%2);
			double longRunsTestOutput = 1;		
			
			//Cycle that checks if symbol is the same as currently in stream, if so it adds it, if not it resets the stream with the new symbol
			for (int i = 1; i < requiredCountOfValues; i++) {
				if (stream.get(0) == outputofRNG.get(i)%2) {
					stream.add(outputofRNG.get(i)%2);
					if (stream.size() > longRunsTestOutput) {
						longRunsTestOutput = stream.size();
					}
				}
				else {
					stream.clear();
					stream.add(outputofRNG.get(i)%2);
				}
			}
			
			return longRunsTestOutput;
			
		}
		//Long Runs test pass check - comparing the result (longest run) with value from FIPS 140-2
		public static boolean islongRunsTestPassed(double toCompare) {
			if (toCompare < 26) {
				return true;
			} else
				return false;

		}
		
}
