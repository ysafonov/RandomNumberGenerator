package cz.vutbr.feec.mkri.tests;

import java.util.ArrayList;

public abstract class RunTest {

	private static int requiredCountOfValues = 20000;
	
	// Runs test
		public static int[] runsTest(ArrayList<Integer> outputofRNG) {

			int[] lengthOfRun = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
			ArrayList<Integer> stream = new ArrayList<Integer>();
			stream.add(outputofRNG.get(0)%2);

			for (int i = 1; i < requiredCountOfValues; i++) {
				if (stream.get(0) == outputofRNG.get(i)%2) {
					stream.add(outputofRNG.get(i)%2);
				} else {
					if (stream.size() > 5) {
						if (stream.get(0) == 0) {
							lengthOfRun[5]++;
							stream.clear();
							stream.add(outputofRNG.get(i)%2);
						} else if (stream.get(0) == 1) {
							lengthOfRun[11]++;
							stream.clear();
							stream.add(outputofRNG.get(i)%2);
						}

					} else {
						switch (stream.size()) {
						case 1:
							if (stream.get(0) == 0) {
								lengthOfRun[0]++;
								stream.clear();
								stream.add(outputofRNG.get(i)%2);
							} else if (stream.get(0) == 1) {
								lengthOfRun[6]++;
								stream.clear();
								stream.add(outputofRNG.get(i)%2);
							}
							break;
						case 2:
							if (stream.get(0) == 0) {
								lengthOfRun[1]++;
								stream.clear();
								stream.add(outputofRNG.get(i)%2);
							} else if (stream.get(0) == 1) {
								lengthOfRun[7]++;
								stream.clear();
								stream.add(outputofRNG.get(i)%2);
							}
							break;
						case 3:
							if (stream.get(0) == 0) {
								lengthOfRun[2]++;
								stream.clear();
								stream.add(outputofRNG.get(i)%2);
							} else if (stream.get(0) == 1) {
								lengthOfRun[8]++;
								stream.clear();
								stream.add(outputofRNG.get(i)%2);
							}
							break;
						case 4:
							if (stream.get(0) == 0) {
								lengthOfRun[3]++;
								stream.clear();
								stream.add(outputofRNG.get(i)%2);
							} else if (stream.get(0) == 1) {
								lengthOfRun[9]++;
								stream.clear();
								stream.add(outputofRNG.get(i)%2);
							}
							break;
						case 5:
							if (stream.get(0) == 0) {
								lengthOfRun[4]++;
								stream.clear();
								stream.add(outputofRNG.get(i)%2);
							} else if (stream.get(0) == 1) {
								lengthOfRun[10]++;
								stream.clear();
								stream.add(outputofRNG.get(i)%2);
							}
							break;
						default:
							break;
						}
					}
				}
			}
			return lengthOfRun;
		}
		
		
		public static boolean isRunsTestPassed(int[] toCompare) {
			if ((2343 < toCompare[0]) && (toCompare[0] < 2657) && (2343 < toCompare[6]) && (toCompare[6] < 2657)
					&& (1135 < toCompare[1]) && (toCompare[1] < 1365) && (1135 < toCompare[7]) && (toCompare[7] < 1365)
					&& (542 < toCompare[2]) && (toCompare[2] < 708) && (542 < toCompare[8]) && (toCompare[8] < 708)
					&& (251 < toCompare[3]) && (toCompare[3] < 373) && (251 < toCompare[9]) && (toCompare[9] < 373)
					&& (111 < toCompare[4]) && (toCompare[4] < 201) && (111 < toCompare[10]) && (toCompare[10] < 201)
					&& (111 < toCompare[5]) && (toCompare[5] < 201) && (111 < toCompare[11]) && (toCompare[11] < 201)) {
				return true;
			} else
				return false;
		}
}
