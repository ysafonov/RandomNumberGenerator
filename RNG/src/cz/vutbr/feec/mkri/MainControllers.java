package cz.vutbr.feec.mkri;

import java.net.URL;
import java.util.ResourceBundle;

import cz.vutbr.feec.mkri.generator.Generator;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

/**
 * This java class represents project's main controller class. The class
 * implements method {@link #generateMenu()} method, {@link #compareMenu()}
 * method, {@link #initialize()} method, {@link #generate()} method,
 * {@link #infoMenu()} method and {@link #helpMenu()} method.
 * 
 * @author Yehor Safonov; id185942
 */

public class MainControllers implements Initializable {
	@FXML
	private Button generateButton;

	@FXML
	private Label randomNumber;

	@FXML
	private Label textGeneratedNumber;

	@FXML
	private TextField minimumNumber;

	@FXML
	private TextField maximumNumber;

	/*
	 * This method is called when a user clicks on the generate button (Bar menu item).
	 * After the click a user should be able to generate new random number.
	 */
	public void generateMenu() {
		// TODO
		System.out.println("TODO: Generate Menu Action");
	}

	/*
	 * This method is called when a user clicks on the information button (Bar menu item).
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

	/*
	 * This method is called when a user clicks on the generate button. While
	 * calling a new instance of the class Generator is created.
	 */
	public void generate() {
		System.out.println("TODO: Generate Random Nember Action");
		textGeneratedNumber.setVisible(true);
		
		Generator generator = new Generator(0, Integer.MAX_VALUE - 1);
		randomNumber.setText(Integer.toString(generator.getRandomNumber()));
	}

	/*
	 * This method is called when an application starts.
	 * 
	 * @see javafx.fxml.Initializable#initialize(java.net.URL,
	 * java.util.ResourceBundle)
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		System.out.println("TODO: Initialize Component");
		textGeneratedNumber.setVisible(false);
	}
}
