package cz.vutbr.feec.mkri.controllers;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;

import cz.vutbr.feec.mkri.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

/**
 * This java class represents project's main controller class. The class
 * implements method {@link #generateMenu()} method, {@link #compareMenu()}
 * method, {@link #initialize()} method,
 * {@link #infoMenu()} method and {@link #helpMenu()} method.
 * 
 * @author Yehor Safonov; id: 185942;
 */

public class MainControllers implements Initializable {
	/*
	 * Root anchor pane.
	 */
	@FXML
	private AnchorPane rootWindow;
	
	/*
	 * This method is called when the application starts. It calls a generate menu method.
	 * 
	 * @see javafx.fxml.Initializable#initialize(java.net.URL,
	 * java.util.ResourceBundle)
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		try {
			this.generateMenu();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/*
	 * This method is called when a user clicks on the generate button (Bar menu
	 * item). After the click a user should be able to generate new random
	 * number.
	 */
	public void generateMenu() throws IOException {
		Main.generator_configuration.OUTPUT.clear();
		AnchorPane pane = FXMLLoader.load(getClass().getResource("/cz/vutbr/feec/mkri/views/GenerateWindow.fxml"));
		rootWindow.getChildren().clear();
		rootWindow.getChildren().setAll(pane);
	}

	/*
	 * This method is called when a user clicks on the Compare button (Bar menu
	 * item). After the click a user should be able to compare different RNGs.
	 */
	public void compareMenu() throws IOException {
		Main.generator_configuration.OUTPUT.clear();
		this.configureGeneratorForCompare();
		if(Main.generator_configuration.use_mouse) {
			AnchorPane mouse = FXMLLoader.load(getClass().getResource("/cz/vutbr/feec/mkri/views/MouseArea.fxml"));
			rootWindow.getChildren().clear();
			rootWindow.getChildren().setAll(mouse);
		}
		AnchorPane pane = FXMLLoader.load(getClass().getResource("/cz/vutbr/feec/mkri/views/CompareWindow.fxml"));
		rootWindow.getChildren().clear();
		rootWindow.getChildren().setAll(pane);
	}

	/*
	 * This method is called when a user clicks on the information button (Bar
	 * menu item).
	 */
	public void infoMenu() throws IOException {
		AnchorPane pane = FXMLLoader.load(getClass().getResource("/cz/vutbr/feec/mkri/views/InformationWindow.fxml"));
		rootWindow.getChildren().clear();
		rootWindow.getChildren().setAll(pane);
	}

	/*
	 * This method is called when a user clicks on the help button (Bar menu
	 * item).
	 */
	public void helpMenu() throws IOException, URISyntaxException {
		Desktop.getDesktop().browse(new URI("https://github.com/ysafonov/RandomNumberGenerator/wiki"));
	}
	
	private void configureGeneratorForCompare() {
		Main.generator_configuration.output_range = true;
		Main.generator_configuration.range_min = 0;
		Main.generator_configuration.range_max = 1;
		Main.generator_configuration.output_file = false;
		Main.generator_configuration.output_bytes = false;
		Main.generator_configuration.output_double = false;
	}
}