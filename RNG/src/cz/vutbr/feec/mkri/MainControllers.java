package cz.vutbr.feec.mkri;

import java.net.URL;
import java.util.ResourceBundle;

import cz.vutbr.feec.mkri.generator.MouseGenerator;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;

/**
 * This java class represents project's main controller class. The class
 * implements method {@link #generateMenu()} method, {@link #compareMenu()}
 * method, {@link #initialize()} method, {@link #generate()} method,
 * {@link #infoMenu()} method and {@link #helpMenu()} method.
 * 
 * @author Yehor Safonov; id: 185942
 * @author David Karger; id: 186526
 */

public class MainControllers implements Initializable {
	
	/*
	 * This variable initializes a method to capture Mouse Events like mouse movement,
	 * coordinates of mouse, button clicks and entering/leaving the target area.
	 */
	private EventHandler<MouseEvent> mouseHandler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent mouseEvent) {
        	mouseGenerate(mouseEvent.getEventType().toString(),		// The triggered mouse event (Example: MOUSE_MOVED when the mouse moves)
        			      (int)mouseEvent.getX(),					// The X coordinate in the Area
        			      (int)mouseEvent.getY(),					// The Y coordinate in the Area
        			      (int)mouseEvent.getScreenX(),				// The X coordinate in the User's screen
        			      (int)mouseEvent.getScreenY(),				// The Y coordinate in the User's screen
        			      mouseEvent.getClickCount(),				// How many times the mouse was clicked
        			      mouseEvent.getButton().name().hashCode()	// Hash code of the clicked button
        			     );
        }
    };
	
    /*
     * Variable for the number of previous X and Y coordinates to be saved in the MouseGenerator class.
     */
	private final int RETENTION = 25;
	
	/*
	 * Variables for individual GUI components.
	 */
	@FXML
	private Label randomNumber;
	@FXML
	private TextField minNumber;
	@FXML
	private TextField maxNumber;
	@FXML
	private Label mouseAreaText;
	@FXML
	private Rectangle mouseArea;
	
	/*
	 * Variable for the RNG generation based on Mouse movement
	 */
	private MouseGenerator mouseGenerator;
	
	/*
	 * Variables for timing the duration of:
	 * - how long was the button pressed down
	 * - how long was the mouse inside the area
	 * - how long was the mouse outside the area
	 */
	private int click_duration;
	private int area_duration;
	private int exit_duration;
	
	/*
	 * This method is called when the application starts.
	 * 
	 * @see javafx.fxml.Initializable#initialize(java.net.URL, java.util.ResourceBundle)
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		this.mouseGenerator = new MouseGenerator(this.RETENTION);
		
		this.mouseArea.setOnMouseClicked(mouseHandler);
        this.mouseArea.setOnMouseDragged(mouseHandler);
        this.mouseArea.setOnMouseEntered(mouseHandler);
        this.mouseArea.setOnMouseExited(mouseHandler);
        this.mouseArea.setOnMouseMoved(mouseHandler);
        this.mouseArea.setOnMousePressed(mouseHandler);
        this.mouseArea.setOnMouseReleased(mouseHandler);
        
        this.exit_duration = (int)System.currentTimeMillis();
	}
	
	/*
	 * This method is called when a user moves his mouse on the generate button. While
	 * calling a new instance of the class Generator is created.
	 */
	public void mouseGenerate(String eventType, int areaX, int areaY, int screenX, int screenY, int mouseClicks, int mouseButton) {
		System.out.println("Mouse Event: " + eventType + '\n' + "Mouse X:Y - " + areaX + ':' + areaY);
		this.mouseGenerator.setMin(Integer.parseInt(this.minNumber.getText()));
		this.mouseGenerator.setMax(Integer.parseInt(this.maxNumber.getText()));
		//IDEA: how long was the mouse of the area
		switch(eventType) {
		
		// Used when the mouse is moved inside the area
		case "MOUSE_MOVED":
			this.randomNumber.setText(Integer.toString(this.mouseGenerator.getRandomNumber(eventType,areaX,areaY,screenX,screenY,mouseClicks,0,0)));
			break;
			
		// Used when a button is pressed down inside the area
		case "MOUSE_PRESSED":
			this.click_duration=(int)System.currentTimeMillis();
			break;
			
		// Used when the pressed button is released inside the area
		case "MOUSE_RELEASED":
			this.randomNumber.setText(Integer.toString(this.mouseGenerator.getRandomNumber(eventType,areaX,areaY,screenX,screenY,mouseClicks,(int)System.currentTimeMillis()-click_duration, mouseButton)));
			break;
			
		// Used when the mouse leaves the area
		case "MOUSE_EXITED":
			this.exit_duration=(int)System.currentTimeMillis();
			this.randomNumber.setText(Integer.toString(this.mouseGenerator.getRandomNumber(eventType,areaX,areaY,screenX,screenY,mouseClicks,(int)System.currentTimeMillis()-area_duration, 0)));
			break;
			
		// Used when the mouse enters the area
		case "MOUSE_ENTERED":
			this.area_duration=(int)System.currentTimeMillis();
			this.randomNumber.setText(Integer.toString(this.mouseGenerator.getRandomNumber(eventType,areaX,areaY,screenX,screenY,mouseClicks,(int)System.currentTimeMillis()-exit_duration, 0)));
			break;
		
		// Just to be safe that something is generated
		default:
			this.randomNumber.setText(Integer.toString(this.mouseGenerator.getRandomNumber(eventType,areaX,areaY,screenX,screenY,mouseClicks,0, 0)));
			break;
		}
	}
	
	/*
	 * This method is called when a user clicks on the generate button (Bar menu item).
	 * After the click a user should be able to generate new random number.
	 */
	public void generateMenu() {
		// TODO
		System.out.println("TODO: Generate Menu Action");
	}
	
	/*
	 * This method is called when a user clicks on the Compare button (Bar menu item).
	 * After the click a user should be able to compare different RNGs.
	 */
	public void compareMenu() {
		// TODO
		System.out.println("TODO: Compare Menu Action");
	}
	
	/*
	 * This method is called when a user clicks on the information button (Bar menu item).
	 */
	public void infoMenu() {
		// TODO
		System.out.println("TODO: Info Menu Action");
	}
	
	/*
	 * This method is called when a user clicks on the help button (Bar menu item).
	 */
	public void helpMenu() {
		// TODO
		System.out.println("TODO: Help Menu Action");
	}
}