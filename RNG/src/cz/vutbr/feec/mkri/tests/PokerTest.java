package cz.vutbr.feec.mkri.tests;

import java.util.ArrayList;
import java.util.Arrays;

/* 
* @author Pavel Mazanek; id: 185933
* @author Yehor Safonov; id: 185942
*/

public abstract class PokerTest {
	// Poker test
	
	private static int requiredCountOfValues = 20000;
	
		public static double pokerTest(ArrayList<Integer> outputofRNG) {

			// 4 bit segment variations
			int[] i0 = { 0, 0, 0, 0 };
			int[] i1 = { 0, 0, 0, 1 };
			int[] i2 = { 0, 0, 1, 0 };
			int[] i3 = { 0, 0, 1, 1 };
			int[] i4 = { 0, 1, 0, 0 };
			int[] i5 = { 0, 1, 0, 1 };
			int[] i6 = { 0, 1, 1, 0 };
			int[] i7 = { 0, 1, 1, 1 };
			int[] i8 = { 1, 0, 0, 0 };
			int[] i9 = { 1, 0, 0, 1 };
			int[] i10 = { 1, 0, 1, 0 };
			int[] i11 = { 1, 0, 1, 1 };
			int[] i12 = { 1, 1, 0, 0 };
			int[] i13 = { 1, 1, 0, 1 };
			int[] i14 = { 1, 1, 1, 0 };
			int[] i15 = { 1, 1, 1, 1 };

			// 4 bit segment appearance counter
			int f0 = 0;
			int f1 = 0;
			int f2 = 0;
			int f3 = 0;
			int f4 = 0;
			int f5 = 0;
			int f6 = 0;
			int f7 = 0;
			int f8 = 0;
			int f9 = 0;
			int f10 = 0;
			int f11 = 0;
			int f12 = 0;
			int f13 = 0;
			int f14 = 0;
			int f15 = 0;

			// test
			for (int i = 0; i < requiredCountOfValues - 4; i = i + 4) {
				int[] tmp = { 0, 0, 0, 0 };
				tmp[0] = outputofRNG.get(i)%2;
				tmp[1] = outputofRNG.get(i + 1)%2;
				tmp[2] = outputofRNG.get(i + 2)%2;
				tmp[3] = outputofRNG.get(i + 3)%2;

				if (Arrays.equals(tmp, i0)) {
					f0 = f0 + 1;
				}
				if (Arrays.equals(tmp, i1)) {
					f1 = f1 + 1;
				}
				if (Arrays.equals(tmp, i2)) {
					f2 = f2 + 1;
				}
				if (Arrays.equals(tmp, i3)) {
					f3 = f3 + 1;
				}
				if (Arrays.equals(tmp, i4)) {
					f4 = f4 + 1;
				}
				if (Arrays.equals(tmp, i5)) {
					f5 = f5 + 1;
				}
				if (Arrays.equals(tmp, i6)) {
					f6 = f6 + 1;
				}
				if (Arrays.equals(tmp, i7)) {
					f7 = f7 + 1;
				}
				if (Arrays.equals(tmp, i8)) {
					f8 = f8 + 1;
				}
				if (Arrays.equals(tmp, i9)) {
					f9 = f9 + 1;
				}
				if (Arrays.equals(tmp, i10)) {
					f10 = f10 + 1;
				}
				if (Arrays.equals(tmp, i11)) {
					f11 = f11 + 1;
				}
				if (Arrays.equals(tmp, i12)) {
					f12 = f12 + 1;
				}
				if (Arrays.equals(tmp, i13)) {
					f13 = f13 + 1;
				}
				if (Arrays.equals(tmp, i14)) {
					f14 = f14 + 1;
				}
				if (Arrays.equals(tmp, i15)) {
					f15 = f15 + 1;
				}
			}

			//formula by FIPS 140-2
			double pokerTestOutput = (0.0032 * ((f0 * f0) + (f1 * f1) + (f2 * f2) + (f3 * f3) + (f4 * f4) + (f5 * f5) + (f6 * f6)
					+ (f7 * f7) + (f8 * f8) + (f9 * f9) + (f10 * f10) + (f11 * f11) + (f12 * f12) + (f13 * f13)
					+ (f14 * f14) + (f15 * f15))) - 5000;
			return pokerTestOutput;
		}

		//Poker test pass check - comparing the result value with values from FIPS 140-2
		public static boolean isPokerTestPassed(double toCompare) {
			if ((2.16 < toCompare) && (toCompare < 46.17)) {
				return true;
			} else
				return false;
		}
}

