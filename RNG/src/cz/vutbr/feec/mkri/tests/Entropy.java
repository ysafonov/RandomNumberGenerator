package cz.vutbr.feec.mkri.tests;

import java.util.ArrayList;

/* 
* @author Pavel Mazanek; id: 185933
* @author Yehor Safonov; id: 185942
*/

public abstract class Entropy {
	
	//Calculating probability of generating one or zero
	public static double chanceOfOneOrZero(ArrayList<Integer> outputofRNG) {
		double result = 0;
		for (int i = 0; i < outputofRNG.size(); i++) {
			result += outputofRNG.get(i)%2;
		}
		return result/outputofRNG.size();
	}
}
