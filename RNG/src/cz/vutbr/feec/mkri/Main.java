package cz.vutbr.feec.mkri;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


/**
 * This java class represents project's main class. The class implements 
 * two methods the {@link #main(String[])} method and the {@link #start(Stage)} 
 * method. As a root of the RNG project is used a fxml document. The project contains
 * an application.css file, which defines custom styles for GUI.
 * 
 * @author Yehor Safonov; id185942
 */

public class Main extends Application{

	@Override
	public void start(Stage primaryStage) {
		Parent root = null;
		try {
			root = FXMLLoader.load(getClass().getResource("MainWindow.fxml"));
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
