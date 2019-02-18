package cz.vutbr.feec.mkri.generator;

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
	private final int MOUSE_CLICKED = 101;
	private final int MOUSE_PRESSED = 101;
	private final int MOUSE_RELEASED = 101;
	private final int MOUSE_MOVED = 101;
	private final int MOUSE_ENTERED = 101;
	private final int MOUSE_EXITED = 101;
	
	private int min;
	private int max;
	
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
		//switch(type) {
		//case "MOUSE_MOVED":
			for (int i = 0; i < previousAreaX.length; i++)
				sum += ((areaX*previousScreenY[i] + areaY*previousScreenX[i]) *A)/MOUSE_MOVED - ((screenY*previousAreaX[i] + screenX*previousAreaY[i]) *B)/MOUSE_MOVED;
		/*	break;
		case "MOUSE_CLICKED":
			break;
		case "MOUSE_PRESSED":
			break;
		case "MOUSE_RELEASED":
			break;
		case "MOUSE_ENTERED":
			break;
		case "MOUSE_EXITED":
			break;
		default:
			sum = areaX*areaY*screenX*screenY;
			break;
		}*/
		this.previousAreaX[this.position] = areaX;
		this.previousAreaY[this.position] = areaY;
		this.previousScreenX[this.position] = screenX;
		this.previousScreenY[this.position] = screenY;
		this.position = (this.position+1)%this.previousAreaX.length;
		if(sum>min)
			return (sum)%(this.max-this.min)+min;
		else
			return Math.abs(sum)%(this.max-this.min)+min;
	}
}
