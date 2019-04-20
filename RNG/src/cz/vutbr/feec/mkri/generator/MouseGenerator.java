package cz.vutbr.feec.mkri.generator;

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
	 * Variables for making the output more random
	 */
	private final int initialSeedA = 186526;
	private final int initialSeedB = 185942;
	
	/*
	 * Some values to create some more salt to the generation.
	 */
	private final int MOUSE_RELEASED = 101;
	private final int MOUSE_MOVED = 722;
	private final int MOUSE_ENTERED = 441;
	private final int MOUSE_EXITED = 327;
	private final int MOUSE_DRAGGED = 177;
	
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
		
		this.previousAreaX = new int [retention];
		this.previousScreenX = new int [retention];
		this.previousAreaY = new int [retention];
		this.previousScreenY = new int [retention];
		
		this.position = 0;
		
	}
	
	/*
	 * This method generates random numbers based on previous inputs.
	 * Number of previous inputs are based by the MainController.RETENTION variable
	 * 
	 * TODO: Combine calculation with hardware sensors (line: 149)
	 */
	public int getRandomNumber(String type, int areaX, int areaY, int screenX, int screenY, int mouseClicks, int duration, int mouseButton) {
		
		// Variable which value is returned after the calculation
		int sum = 0;
		
		if(this.position==0) {
			sum = areaX*areaY*screenX*screenY;
		}
		else {
			switch(type) {
		
			// Mouse has be moved inside the area
			case "MOUSE_MOVED":
				if(mouseClicks==0) {
					for (int i = 0; i < previousAreaX.length; i++)
						sum += ((areaX*previousScreenY[i] + areaY*previousScreenX[i]) *initialSeedA)/MOUSE_MOVED - ((screenY*previousAreaX[i] + screenX*previousAreaY[i]) *initialSeedB)/MOUSE_MOVED;
				} else {
					for (int i = 0; i < previousAreaX.length; i++)
						sum += mouseClicks*((areaX*previousScreenY[i] + areaY*previousScreenX[i]) *initialSeedA)/MOUSE_MOVED - ((screenY*previousAreaX[i] + screenX*previousAreaY[i]) *initialSeedB)/MOUSE_MOVED;
				}
				break;
				
			// A mouse button has been released
			case "MOUSE_RELEASED":
				for (int i = 0; i < previousAreaX.length; i++)
					sum += duration*mouseClicks*mouseButton*((areaX*previousScreenY[i] + areaY*previousScreenX[i]) *initialSeedA)/MOUSE_RELEASED - ((screenY*previousAreaX[i] + screenX*previousAreaY[i]) *initialSeedB)/MOUSE_RELEASED;
				break;
				
			// Mouse has entered the area
			case "MOUSE_ENTERED":
				if(mouseClicks==0) {
					for (int i = 0; i < previousAreaX.length; i++)
						sum += duration*(((areaX*previousScreenY[i] + areaY*previousScreenX[i]) *initialSeedA)/MOUSE_ENTERED - ((screenY*previousAreaX[i] + screenX*previousAreaY[i]) *initialSeedB)/MOUSE_ENTERED);
				} else {
					for (int i = 0; i < previousAreaX.length; i++)
						sum += duration*mouseClicks*((areaX*previousScreenY[i] + areaY*previousScreenX[i]) *initialSeedA)/MOUSE_ENTERED - ((screenY*previousAreaX[i] + screenX*previousAreaY[i]) *initialSeedB)/MOUSE_ENTERED;
				}
				break;
				
			// Mouse has exited the area
			case "MOUSE_EXITED":
				if(mouseClicks==0) {
					for (int i = 0; i < previousAreaX.length; i++)
						sum += duration*((areaX*previousScreenY[i] + areaY*previousScreenX[i]) *initialSeedA)/MOUSE_EXITED - ((screenY*previousAreaX[i] + screenX*previousAreaY[i]) *initialSeedB)/MOUSE_EXITED;
				} else {
					for (int i = 0; i < previousAreaX.length; i++)
						sum += mouseClicks*duration*(((areaX*previousScreenY[i] + areaY*previousScreenX[i]) *initialSeedA)/MOUSE_EXITED - ((screenY*previousAreaX[i] + screenX*previousAreaY[i]) *initialSeedB)/MOUSE_EXITED);
				}
				break;
			
			// Mouse has exited the area
			case "MOUSE_DRAGGED":
				if(mouseClicks==0) {
					for (int i = 0; i < previousAreaX.length; i++)
						sum += duration*((areaX*previousScreenY[i] + areaY*previousScreenX[i]) *initialSeedA)/MOUSE_DRAGGED - ((screenY*previousAreaX[i] + screenX*previousAreaY[i]) *initialSeedB)/MOUSE_DRAGGED;
					} else {
						for (int i = 0; i < previousAreaX.length; i++)
							sum += mouseClicks*duration*(((areaX*previousScreenY[i] + areaY*previousScreenX[i]) *initialSeedA)/MOUSE_DRAGGED - ((screenY*previousAreaX[i] + screenX*previousAreaY[i]) *initialSeedB)/MOUSE_DRAGGED);
				}
				break;
				
			// Just to be safe and something is returned
			default:
				sum = areaX*areaY*screenX*screenY;
				break;
			}
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
		
		return sum;
	}
	
}
