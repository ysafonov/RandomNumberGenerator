package cz.vutbr.feec.mkri.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import cz.vutbr.feec.mkri.Main;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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

public class MouseAreaControllers implements Initializable {

	/*
	 * This variable initializes a method to capture Mouse Events like mouse
	 * movement, coordinates of mouse, button clicks and entering/leaving the
	 * target area.
	 */
	private EventHandler<MouseEvent> mouseHandler = new EventHandler<MouseEvent>() {
		@Override
		public void handle(MouseEvent mouseEvent) {
			mouseGenerate(mouseEvent.getEventType().toString(), // The triggered
																// mouse event
																// (Example:
																// MOUSE_MOVED
																// when the
																// mouse moves)
					(int) mouseEvent.getX(), // The X coordinate in the Area
					(int) mouseEvent.getY(), // The Y coordinate in the Area
					(int) mouseEvent.getScreenX(), // The X coordinate in the
													// User's screen
					(int) mouseEvent.getScreenY(), // The Y coordinate in the
													// User's screen
					mouseEvent.getClickCount(), // How many times the mouse was
												// clicked
					mouseEvent.getButton().name().hashCode() // Hash code of the
																// clicked
																// button
			);
		}
	};
	
	@FXML
	private Rectangle mouseArea;
	
	/*
	 * Variables for timing the duration of: - how long was the button pressed
	 * down - how long was the mouse inside the area - how long was the mouse
	 * outside the area
	 */
	private int click_duration;
	private int area_duration;
	private int exit_duration;
	
	/*
	 * This method is called when the application starts.
	 * 
	 * @see javafx.fxml.Initializable#initialize(java.net.URL,
	 * java.util.ResourceBundle)
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
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
	 * This method is called when a user moves his mouse on the generate button.
	 * While calling a new instance of the class Generator is created.
	 */
	public void mouseGenerate(String eventType, int areaX, int areaY, int screenX, int screenY, int mouseClicks,
			int mouseButton) {
		System.out.println("Mouse Event: " + eventType + '\n' + "Mouse X:Y - " + areaX + ':' + areaY);
		
		// IDEA: how long was the mouse of the area
		switch (eventType) {
		// Used when the mouse is moved inside the area
		case "MOUSE_MOVED":
			GenerateWindowControllers.RNG.doMyThing(eventType, areaX, areaY, screenX, screenY, mouseClicks, 0, 0);
			break;
			
		// Used when a button is pressed down inside the area
		case "MOUSE_PRESSED":
			this.click_duration = (int) System.currentTimeMillis();
			break;
			
		// Used when the pressed button is released inside the area
		case "MOUSE_RELEASED":
			GenerateWindowControllers.RNG.doMyThing(eventType, areaX, areaY, screenX, screenY, mouseClicks,(int) System.currentTimeMillis() - click_duration, mouseButton);
			break;
			
		// Used when the mouse leaves the area
		case "MOUSE_EXITED":
			this.exit_duration = (int) System.currentTimeMillis();
			GenerateWindowControllers.RNG.doMyThing(eventType, areaX, areaY, screenX, screenY, mouseClicks,(int) System.currentTimeMillis() - area_duration, 0);
			break;
			
		// Used when the mouse enters the area
		case "MOUSE_ENTERED":
			this.area_duration = (int) System.currentTimeMillis();
			GenerateWindowControllers.RNG.doMyThing(eventType, areaX, areaY, screenX, screenY, mouseClicks,(int) System.currentTimeMillis() - exit_duration, 0);
			break;
			
		// Just to be safe that something is generated
		default:
			GenerateWindowControllers.RNG.doMyThing(eventType, areaX, areaY, screenX, screenY, mouseClicks, 0, 0);
			break;
		}
		
		if(Main.generator_configuration.output_count > 1) {
			if(Main.generator_configuration.OUTPUT.size() == Main.generator_configuration.output_count)
				// End generator window
				this.notifyOutput("set is ready");
		}
		else
			this.notifyOutput("one number is ready");
	}
	
	private void notifyOutput(String input) {
		// Notify the number output window that one number or the hole set is ready.
	}
}