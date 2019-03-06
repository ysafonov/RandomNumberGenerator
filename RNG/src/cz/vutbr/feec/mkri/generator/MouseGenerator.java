package cz.vutbr.feec.mkri.generator;

import java.math.BigInteger;
import java.security.MessageDigest;

/**
 * This class is used for generating Random numbers based
 * on the Mouse events in an area of the GUI.
 * 
 * @author Yehor Safonov; id: 185942
 * @author: David Karger; id: 186526
 */

public class MouseGenerator {
	
	/*
	 * TODO:
	 * - combine with other useful RNG inputs (keyboard, HW monitor, etc.)
	 * - TBA
	 */
	
	/*
	 * Variable used for operating the hardware sensors
	 */
	//private ComponentGenerator com_gen;
	
	/*
	 * Variables for making the output more random
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
	
	/*
	 * The limits of the output
	 */
	private int min;
	private int max;
	
	/*
	 * Java.Security class for Cryptographic algorithms
	 */
	private MessageDigest md;
	
	/*
	 * Arrays to have numbers based on previous mouse locations.
	 */
	private int[] previousAreaX;
	private int[] previousScreenX;
	private int[] previousAreaY;
	private int[] previousScreenY;
	
	/*
	 * Variable to know which position is to be renewed
	 */
	private int position;
	
	public MouseGenerator(int retention) {
		
		// Initialization of the Hardware sensor operating class
		//this.com_gen = new ComponentGenerator();
		// Starting a new thread that operates a method for data retrieval from hardware sensors.
		//this.com_gen.start();
		
		this.min = -100;
		this.max = 100;
		
		this.previousAreaX = new int [retention];
		this.previousScreenX = new int [retention];
		this.previousAreaY = new int [retention];
		this.previousScreenY = new int [retention];
		
		this.position = 0;
		
		// Setting the hashing algorithm
		try { this.md = MessageDigest.getInstance("MD5"); }
		catch(Exception e) { e.printStackTrace(); }
	}
	
	// Set the range of the random number
	public void setMin(int min) { this.min = min; }
	public void setMax(int max) { this.max = max; }
	
	/*
	 * This method generates random numbers based on previous inputs.
	 * Number of previous inputs are based by the MainController.RETENTION variable
	 * 
	 * TODO: Combine calculation with hardware sensors (line: 149)
	 */
	public int getRandomNumber(String type, int areaX, int areaY, int screenX, int screenY, int mouseClicks, int duration, int mouseButton) {
		
		// Variable which value is returned after the calculation
		int sum = 0;
		
		switch(type) {
		
		// Mouse has be moved inside the area
		case "MOUSE_MOVED":
			if(mouseClicks==0) {
				for (int i = 0; i < previousAreaX.length; i++)
					sum += ((areaX*previousScreenY[i] + areaY*previousScreenX[i]) *A)/MOUSE_MOVED - ((screenY*previousAreaX[i] + screenX*previousAreaY[i]) *B)/MOUSE_MOVED;
			} else {
				for (int i = 0; i < previousAreaX.length; i++)
					sum += mouseClicks*((areaX*previousScreenY[i] + areaY*previousScreenX[i]) *A)/MOUSE_MOVED - ((screenY*previousAreaX[i] + screenX*previousAreaY[i]) *B)/MOUSE_MOVED;
			}
			break;
			
		// A mouse button has been released
		case "MOUSE_RELEASED":
			for (int i = 0; i < previousAreaX.length; i++)
				sum += duration*mouseClicks*mouseButton*((areaX*previousScreenY[i] + areaY*previousScreenX[i]) *A)/MOUSE_RELEASED - ((screenY*previousAreaX[i] + screenX*previousAreaY[i]) *B)/MOUSE_RELEASED;
			break;
			
		// Mouse has entered the area
		case "MOUSE_ENTERED":
			if(mouseClicks==0) {
				for (int i = 0; i < previousAreaX.length; i++)
					sum += duration*(((areaX*previousScreenY[i] + areaY*previousScreenX[i]) *A)/MOUSE_ENTERED - ((screenY*previousAreaX[i] + screenX*previousAreaY[i]) *B)/MOUSE_ENTERED);
			} else {
				for (int i = 0; i < previousAreaX.length; i++)
					sum += duration*mouseClicks*((areaX*previousScreenY[i] + areaY*previousScreenX[i]) *A)/MOUSE_ENTERED - ((screenY*previousAreaX[i] + screenX*previousAreaY[i]) *B)/MOUSE_ENTERED;
			}
			break;
			
		// Mouse has exited the area
		case "MOUSE_EXITED":
			if(mouseClicks==0) {
				for (int i = 0; i < previousAreaX.length; i++)
					sum += duration*((areaX*previousScreenY[i] + areaY*previousScreenX[i]) *A)/MOUSE_EXITED - ((screenY*previousAreaX[i] + screenX*previousAreaY[i]) *B)/MOUSE_EXITED;
			} else {
				for (int i = 0; i < previousAreaX.length; i++)
					sum += mouseClicks*duration*(((areaX*previousScreenY[i] + areaY*previousScreenX[i]) *A)/MOUSE_EXITED - ((screenY*previousAreaX[i] + screenX*previousAreaY[i]) *B)/MOUSE_EXITED);
			}
			break;
			
		// Just to be safe and something is returned
		default:
			sum = areaX*areaY*screenX*screenY;
			break;
		}
		
		/*
		 * Replacing values at given position to keep the retention actual.
		 */
		this.previousAreaX[this.position] = areaX;
		this.previousAreaY[this.position] = areaY;
		this.previousScreenX[this.position] = screenX;
		this.previousScreenY[this.position] = screenY;
		
		/*
		 * Incrementing the position counter so next time a oldest value is replaced.
		 */
		this.position = (this.position+1)%this.previousAreaX.length;
		
		//sum = this.calculate_MD5(this.com_gen.getRandom(sum));
		sum = this.calculate_MD5(sum);
		
		/*
		 * Regulating output to be inside the minimum and maximum numbers inputed in the GUI
		 */
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
	
	/*
	 * This method calculates the MD5 hash from the input and returns its Integer conversion
	 */
	public int calculate_MD5(int input) {
		try { return (new BigInteger(this.md.digest(String.valueOf(input).getBytes()))).intValue(); }
		catch(Exception e) { e.printStackTrace(); }
		return 0;
	}
}
