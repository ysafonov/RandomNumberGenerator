package cz.vutbr.feec.mkri.generator;

import java.util.ArrayList;

public abstract class GeneratorMath {

	public static void setArray(ArrayList<Integer> input, int size ) {
		for (int i = 0; i < size; i++) {
			input.add((int)Math.floor(Math.random() * Math.floor(2)));
		}
		
	}
	
}
