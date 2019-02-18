package cz.vutbr.feec.mkri.generator;
import java.util.concurrent.ThreadLocalRandom;

public class Generator {
	
	private int minimumNumber;
	private int maximumNumber;
	
	public Generator(){
		minimumNumber = 0;
		maximumNumber = 100;
	}
	
	public void setMinimumNumber(int minimumNumber) { this.minimumNumber = minimumNumber; }

	public void setMaximumNumber(int maximumNumber) { this.maximumNumber = maximumNumber; }

	public int getRandomNumber(){
		return ThreadLocalRandom.current().nextInt(minimumNumber, maximumNumber + 1);
	}
}

