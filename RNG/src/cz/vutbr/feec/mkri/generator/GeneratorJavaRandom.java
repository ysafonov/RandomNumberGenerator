package cz.vutbr.feec.mkri.generator;

import java.util.ArrayList;
import java.util.Random;

public abstract class GeneratorJavaRandom {

	public static void setArray(ArrayList<Integer> input, int size ) {
		Random r = new Random();
		for (int i = 0; i < size; i++) {
			input.add(r.nextInt(2));
		}
		
	}
	
}
