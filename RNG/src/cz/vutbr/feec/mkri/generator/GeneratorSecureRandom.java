package cz.vutbr.feec.mkri.generator;

import java.nio.ByteBuffer;
import java.security.SecureRandom;
import java.util.ArrayList;

/* 
* @author David Pecl;
* @author Yehor Safonov; id: 185942
*/

public abstract class GeneratorSecureRandom {

	public static void setArray(ArrayList<Integer> input, int size ) {
		SecureRandom r = new SecureRandom();
		byte bytes[] = new byte[4];    
	   
	    for (int i = 0; i < size; i++) {
	    	r.nextBytes(bytes);
	    	if(ByteBuffer.wrap(bytes).getInt() % 2 == 0) input.add(0);
	 	    else input.add(1);
	    }
	   
	}
	
}
