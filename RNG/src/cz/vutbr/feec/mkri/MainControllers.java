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
	 * Possibility to add mouseEvent.getButton() getting which button was pressed.
	 */
	
	/*
	 * This variable allows to capture Mouse Events like mouse movement, button clicks and entering/leaving the target area.
	 */
	private EventHandler<MouseEvent> mouseHandler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent mouseEvent) { mouseGenerate(mouseEvent.getEventType().toString(),(int)mouseEvent.getX(),(int)mouseEvent.getY(),(int)mouseEvent.getScreenX(),(int)mouseEvent.getScreenY(),mouseEvent.getClickCount()); }
    };
	
    // Variable for the number of previous X and Y coordinates are saved in MouseGenerator class.
	private final int RETENTION = 25;

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
	
	private MouseGenerator mouseGenerator;
	
	private int click_duration = 0;
	/*
	 * This method is called when the application starts.
	 * 
	 * @see javafx.fxml.Initializable#initialize(java.net.URL,
	 * java.util.ResourceBundle)
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
	}
	
	/*
	 * This method is called when a user moves his mouse on the generate button. While
	 * calling a new instance of the class Generator is created.
	 */
	//IDEA: how long was the mouse of the area
	public void mouseGenerate(String eventType, int areaX, int areaY, int screenX, int screenY, int mouseClicks) {
		System.out.println("Mouse Event: " + eventType + '\n' + "Mouse X:Y - " + areaX + ':' + areaY);
		this.mouseGenerator.setMin(Integer.parseInt(this.minNumber.getText()));
		this.mouseGenerator.setMax(Integer.parseInt(this.maxNumber.getText()));
		if(eventType.equals("MOUSE_PRESSED"))
			click_duration=(int)System.currentTimeMillis();
		else if(eventType.equals("MOUSE_RELEASED"))
			this.randomNumber.setText(Integer.toString(this.mouseGenerator.getRandomNumber(eventType,areaX,areaY,screenX,screenY,mouseClicks,(int)System.currentTimeMillis()-click_duration)));
		else
			this.randomNumber.setText(Integer.toString(this.mouseGenerator.getRandomNumber(eventType,areaX,areaY,screenX,screenY,mouseClicks,0)));		
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