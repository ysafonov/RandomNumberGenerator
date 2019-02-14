package cz.vutbr.feec.mkri.generator;
import java.util.concurrent.ThreadLocalRandom;

public class Generator {
	
	private int minimumNumber;
	private int maximumNumber;
	private int randomNumber;
	
	public Generator(int min, int max){
		minimumNumber = min;
		maximumNumber = max;
		randomNumber = ThreadLocalRandom.current().nextInt(minimumNumber, maximumNumber + 1);
	}
	
	public int getRandomNumber(){
		return randomNumber;
	}
}

