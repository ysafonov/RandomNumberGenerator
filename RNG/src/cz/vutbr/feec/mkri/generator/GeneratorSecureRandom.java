package cz.vutbr.feec.mkri.generator;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.util.ArrayList;

public abstract class GeneratorSecureRandom {

	public static void setArray(ArrayList<Integer> input, int size ) throws NoSuchAlgorithmException, NoSuchProviderException {
		SecureRandom secureRandomGenerator = SecureRandom.getInstance("SHA1PRNG", "SUN");
		for (int i = 0; i < size; i++) {
			input.add(secureRandomGenerator.nextInt(2));
		}
		
	}
}
