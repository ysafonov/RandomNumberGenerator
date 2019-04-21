package cz.vutbr.feec.mkri.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;

/**
 * Class prepresent controllers which gives information about project.
 * @author Yehor Safonov; id: 185942
 */

public class InformationWindowControllers implements Initializable {

	/*
	 * Variable for individual GUI component.
	 */
	@FXML
	private Text informationText;
	
	@FXML
	private Text authorsText;

	/*
	 * This method is called when the application starts.
	 * 
	 * @see javafx.fxml.Initializable#initialize(java.net.URL,
	 * java.util.ResourceBundle)
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		informationText.setText(
				"Information about project: \n\nThere is an open-sourced Java project which was created as a part of the subject"
				+ " Cryptography in informatics (MKRI) at The Faculty of Electrical Engineering,"
				+ " Brno University of Technology (BUT). This project represents the application"
				+ " for generating random numbers which are widely used in modern cryptography.\n ");
		authorsText.setText("Authors:\n"
				+ "\n1) Yehor Safonov - programming, git management, graphical user interface;"
				+ "\n2) Pavel Mazánek - programming, RNG modules, documentation, research;"
				+ "\n3) David Karger - programming, testing, RNG modules;"
				+ "\n4) David Pecl - RNG modules architecture, documentation, research.");
	}
}