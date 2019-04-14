package cz.vutbr.feec.mkri.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;

/**
 * This java class represents project's Compare Pane controller class.
 * 
 * @author Yehor Safonov; id: 185942
 */

public class RandomResultControllers implements Initializable {

	/*
	 * This method is called when the application starts.
	 * 
	 * @see javafx.fxml.Initializable#initialize(java.net.URL,
	 * java.util.ResourceBundle)
	 */
	
	@FXML
	private Text randomNumberResult;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {

		randomNumberResult.setText("Funguje to!"); 
	}
	

}