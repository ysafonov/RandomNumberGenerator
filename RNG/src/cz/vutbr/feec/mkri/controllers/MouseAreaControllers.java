package cz.vutbr.feec.mkri.controllers;

import java.io.IOException;
import java.net.URL;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Paths;
import java.util.ResourceBundle;

import cz.vutbr.feec.mkri.Main;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
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
			mouseGenerate(mouseEvent, // The triggered mouse event (Example: MOUSE_MOVED when the mouse moves)
					(int) mouseEvent.getX(), // The X coordinate in the Area
					(int) mouseEvent.getY(), // The Y coordinate in the Area
					(int) mouseEvent.getScreenX(), // The X coordinate in the User's screen
					(int) mouseEvent.getScreenY(), // The Y coordinate in the User's screen
					mouseEvent.getClickCount(), // How many times the mouse was clicked
					mouseEvent.getButton().name().hashCode() // Hash code of the clicked button
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
		
		this.exit_duration = (int) System.currentTimeMillis();
	}

	/*
	 * This method is called when a user moves his mouse on the generate button.
	 * While calling a new instance of the class Generator is created.
	 */
	public void mouseGenerate(Event event, int areaX, int areaY, int screenX, int screenY, int mouseClicks, int mouseButton) {
		System.out.println("Mouse Event: " + event.getEventType().toString() + '\n' + "Mouse X:Y - " + areaX + ':' + areaY);
		
		if(Main.generator_configuration.OUTPUT.size()<Main.generator_configuration.set_items) {
			switch (event.getEventType().toString()) {
			// Used when the mouse is moved inside the area
			case "MOUSE_MOVED":
				Main.RNG.doMyThing(event.getEventType().toString(), areaX, areaY, screenX, screenY, mouseClicks, 0, 0);
				break;
				
			// Used when the mouse is moved inside the area
			case "MOUSE_DRAGGED":
				Main.RNG.doMyThing(event.getEventType().toString(), areaX, areaY, screenX, screenY, mouseClicks, mouseButton, this.click_duration-(int)System.currentTimeMillis());
				break;
			
			// Used when a button is pressed down inside the area
			case "MOUSE_PRESSED":
				this.click_duration = (int) System.currentTimeMillis();
				break;
			
			// Used when the pressed button is released inside the area
			case "MOUSE_RELEASED":
				Main.RNG.doMyThing(event.getEventType().toString(), areaX, areaY, screenX, screenY, mouseClicks, (int) System.currentTimeMillis() - click_duration, mouseButton);
				break;
				
			// Used when the mouse leaves the area
			case "MOUSE_EXITED":
				this.exit_duration = (int) System.currentTimeMillis();
				Main.RNG.doMyThing(event.getEventType().toString(), areaX, areaY, screenX, screenY, mouseClicks, (int) System.currentTimeMillis() - area_duration, 0);
				break;
				
			// Used when the mouse enters the area
			case "MOUSE_ENTERED":
				this.area_duration = (int) System.currentTimeMillis();
				Main.RNG.doMyThing(event.getEventType().toString(), areaX, areaY, screenX, screenY, mouseClicks, (int) System.currentTimeMillis() - exit_duration, 0);
				break;
				
			// Just to be safe that something is generated
			default:
				Main.RNG.doMyThing(event.getEventType().toString(), areaX, areaY, screenX, screenY, mouseClicks, 0, 0);
				break;
			}
			
			System.out.println(Main.generator_configuration.OUTPUT.get(Main.generator_configuration.OUTPUT.size()-1));
			System.out.println(Main.generator_configuration.OUTPUT.size());
			if (Main.generator_configuration.set_items > 1) {
				if (Main.generator_configuration.OUTPUT.size() == Main.generator_configuration.set_items)
					this.notifyOutput("set is ready", event);
			} else if(Main.generator_configuration.OUTPUT.size()==1)
				this.notifyOutput("one number is ready", event);
		}
	}
	
	private void notifyOutput(String input, Event event) {
		try {
			System.out.println(input);
			if(Main.generator_configuration.output_file)
				this.writeFile();
			showResultNumber(event);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void showResultNumber(Event event) throws IOException {
		Parent tmp = ((Node) event.getTarget()).getScene().getRoot();
		AnchorPane ap = (AnchorPane) tmp.lookup("#rootWindow");
		AnchorPane pane;
		if(MainControllers.nextScene.equals("result"))
			pane = FXMLLoader.load(getClass().getResource("/cz/vutbr/feec/mkri/views/RandomResult.fxml"));
		else
			pane = FXMLLoader.load(getClass().getResource("/cz/vutbr/feec/mkri/views/CompareWindow.fxml"));
	    ap.getChildren().clear();
		ap.getChildren().setAll(pane);
	}
	
	/*
	 * Write the RNGs into a file
	 */
	private void writeFile() {
		try {
			
			String output = "";
			if(Main.generator_configuration.set_items > 1) {
				for(String s:Main.generator_configuration.OUTPUT)
					output += s + Main.generator_configuration.set_separator;
			}
			else
				output = Main.generator_configuration.OUTPUT.get(0);
			
			try { Files.createDirectories(Paths.get("output")); } catch (FileAlreadyExistsException e) { /* No problem here */ }
			Files.write(Paths.get(Main.generator_configuration.file_path), output.getBytes());
			
    		Main.generator_configuration.OUTPUT.clear();
    		Main.generator_configuration.OUTPUT.add("Data writen to file.");
    		
        } catch (Exception e) { e.printStackTrace(); }
	}
}