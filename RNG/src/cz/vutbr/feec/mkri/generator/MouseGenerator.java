package cz.vutbr.feec.mkri.generator;

import java.math.BigInteger;
import java.security.MessageDigest;

/**
 * This class is used for generating Random numbers based
 * on the Mouse events in an area of the GUI.
 * 
 * @author: David Karger; id: 186526
 */

public class MouseGenerator {
	
	/*
	 * TODO:
	 * - combine with other useful RNG inputs (keyboard, HW monitor, etc.)
	 * - TBA
	 */
	
	private final int A = 186526;
	private final int B = 185942;
	
	/*
	 * Some values to create some more salt to the generation.
	 */
	private final int MOUSE_RELEASED = 101;
	private final int MOUSE_MOVED = 722;
	private final int MOUSE_ENTERED = 441;
	private final int MOUSE_EXITED = 327;
	
	private int min;
	private int max;
	
	private MessageDigest md;
	
	/*
	 * Arrays to have numbers based on previous mouse locations.
	 */
	
	private int[] previousAreaX;
	private int[] previousScreenX;
	private int[] previousAreaY;
	private int[] previousScreenY;
	private int position;
	
	public MouseGenerator(int retention) {
		this.min = 0;
		this.max = 100;
		this.previousAreaX = new int [retention];
		this.previousScreenX = new int [retention];
		this.previousAreaY = new int [retention];
		this.previousScreenY = new int [retention];
		this.position = 0;
		
		try { this.md = MessageDigest.getInstance("MD5"); }
		catch(Exception e) { e.printStackTrace(); }
	}
	
	// Set the range of the random number
	public void setMin(int min) { this.min = min; }
	public void setMax(int max) { this.max = max; }

	/*
	 * This method generates random numbers based on previous inputs.
	 * Number of previous inputs are based by the MainController.RETENTION variable
	 */
	//TODO: add other types with possible other calculation and use mouseClicks and duration
	public int getRandomNumber(String type, int areaX, int areaY, int screenX, int screenY, int mouseClicks, int duration) {
		int sum = 0;
		switch(type) {
		case "MOUSE_MOVED":
			for (int i = 0; i < previousAreaX.length; i++)
				sum += ((areaX*previousScreenY[i] + areaY*previousScreenX[i]) *A)/MOUSE_MOVED - ((screenY*previousAreaX[i] + screenX*previousAreaY[i]) *B)/MOUSE_MOVED;
			break;
		case "MOUSE_RELEASED":
			for (int i = 0; i < previousAreaX.length; i++)
				sum += duration*((areaX*previousScreenY[i] + areaY*previousScreenX[i]) *A)/MOUSE_RELEASED - ((screenY*previousAreaX[i] + screenX*previousAreaY[i]) *B)/MOUSE_RELEASED;
			break;
		case "MOUSE_ENTERED":
			for (int i = 0; i < previousAreaX.length; i++)
				sum += ((areaX*previousScreenY[i] + areaY*previousScreenX[i]) *A)/MOUSE_ENTERED - ((screenY*previousAreaX[i] + screenX*previousAreaY[i]) *B)/MOUSE_ENTERED;
			break;
		case "MOUSE_EXITED":
			for (int i = 0; i < previousAreaX.length; i++)
				sum += ((areaX*previousScreenY[i] + areaY*previousScreenX[i]) *A)/MOUSE_EXITED - ((screenY*previousAreaX[i] + screenX*previousAreaY[i]) *B)/MOUSE_EXITED;
			break;
		default:
			sum = areaX*areaY*screenX*screenY;
			break;
		}
		this.previousAreaX[this.position] = areaX;
		this.previousAreaY[this.position] = areaY;
		this.previousScreenX[this.position] = screenX;
		this.previousScreenY[this.position] = screenY;
		this.position = (this.position+1)%this.previousAreaX.length;
		
		sum = this.calculate_MD5(sum);
		if(this.min != 0) {
			if(sum>this.min)
				return sum%this.max;
			if(sum<this.min)
				while(sum<this.min)
					sum+=Math.abs(this.min);
			return sum;
		}
		return Math.abs(sum)%this.max;
	}
	
	private int calculate_MD5(int input) {
		try { return (new BigInteger(this.md.digest(String.valueOf(input).getBytes()))).intValue(); }
		catch(Exception e) { e.printStackTrace(); }
		return 0;
	}
}
