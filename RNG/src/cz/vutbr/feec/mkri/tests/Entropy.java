package cz.vutbr.feec.mkri.tests;

import java.util.ArrayList;

public abstract class Entropy {
	
	public static double chanceOfOneOrZero(ArrayList<Integer> outputofRNG) {
		double result = 0;
		for (int i = 0; i < outputofRNG.size(); i++) {
			result += outputofRNG.get(i)%2;
		}
		return result/outputofRNG.size();
	}
}
