package cz.vutbr.feec.mkri;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * This java class represents project's main class.The class implements 
 * two methods the {@link #main(String[])} method and the {@link #start(Stage)} 
 * method. As a root of the RNG project a fxml document is used.
 * 
 * @author Yehor Safonov; id: 185942
 * @author David Karger; id: 186526
 */

public class Main extends Application{
	
	@Override
	public void start(Stage primaryStage) {
		Parent root = null;
		try {
			root = FXMLLoader.load(getClass().getResource("/cz/vutbr/feec/mkri/views/MainWindow.fxml"));
			Scene scene = new Scene(root);
	        primaryStage.setScene(scene);
	    	primaryStage.setResizable(false);
			primaryStage.setTitle("Random Number Generator");
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
	
}
