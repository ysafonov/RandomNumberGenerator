package cz.vutbr.feec.mkri.controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import cz.vutbr.feec.mkri.Main;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.ScrollEvent;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

/**
 * This java class represents project's Compare Pane controller class.
 * 
 * @author Yehor Safonov; id: 185942
 */

public class RandomResultControllers implements Initializable {



	@FXML
	private Text randomNumberResult;
	@FXML
	private Text randomNumberResult1;
	@FXML
	private Text randomNumberResult2;
	@FXML
	private Text randomNumberResult3;
	@FXML
	private Text randomNumberResult4;
	@FXML
	private Text randomNumberResult5;
	@FXML
	private Text randomNumberResult6;

	@FXML
	private Rectangle rectangleResult;

	private ArrayList<String> arrayForShowing;
	private int shift;

	/*
	 * This method is called when the application starts.
	 * 
	 * @see javafx.fxml.Initializable#initialize(java.net.URL,
	 * java.util.ResourceBundle)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		generateArrayListFirstGenerator();

		randomNumberResult.setText(arrayForShowing.get(0));
		if (arrayForShowing.size() >= 2)
			randomNumberResult1.setText(arrayForShowing.get(1));
		if (arrayForShowing.size() >= 3)
			randomNumberResult2.setText(arrayForShowing.get(2));
		if (arrayForShowing.size() >= 4)
			randomNumberResult3.setText(arrayForShowing.get(3));
		if (arrayForShowing.size() >= 5)
			randomNumberResult4.setText(arrayForShowing.get(4));
		if (arrayForShowing.size() >= 6)
			randomNumberResult5.setText(arrayForShowing.get(5));
		if (arrayForShowing.size() >= 7)
			randomNumberResult6.setText(arrayForShowing.get(6));

		rectangleResult.setOnScroll(new EventHandler() {
			@Override
			public void handle(Event event) {
				if (((ScrollEvent) event).getDeltaY() == -107) {
					shift++;
					if (shift < arrayForShowing.size()) {
						randomNumberResult.setText(arrayForShowing.get(shift));
						if (shift < arrayForShowing.size() - 1) {
							randomNumberResult1.setText(arrayForShowing.get(shift+1));
						} else {
							randomNumberResult1.setText("");
						}
						if (shift < arrayForShowing.size() - 2) {
							randomNumberResult2.setText(arrayForShowing.get(shift + 2));
						} else {
							randomNumberResult2.setText("");
						}
						if (shift < arrayForShowing.size() - 3) {
							randomNumberResult3.setText(arrayForShowing.get(shift + 3));
						} else {
							randomNumberResult3.setText("");
						}
						if (shift < arrayForShowing.size() - 4) {
							randomNumberResult4.setText(arrayForShowing.get(shift + 4));
						} else {
							randomNumberResult4.setText("");
						}
						if (shift < arrayForShowing.size() - 5) {
							randomNumberResult5.setText(arrayForShowing.get(shift + 5));
						} else {
							randomNumberResult5.setText("");
						}
						if (shift < arrayForShowing.size() - 6) {
							randomNumberResult6.setText(arrayForShowing.get(shift + 6));
						} else {
							randomNumberResult6.setText("");
						}
					} 
				}
			}
		});
	}

	private void generateArrayListFirstGenerator() {
		arrayForShowing = Main.generator_configuration.OUTPUT;
	}

}