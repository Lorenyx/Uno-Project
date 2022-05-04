package application;
import application.model.GameLoop;
/*
 * Main is a class that contains main method to run your program 
 * It also grabs the Menu.fxml file and loads it in
 */
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

	public static GameLoop gameLoop;

	@Override
	public void start(Stage stage) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("view/Menu.fxml"));
			Scene scene = new Scene(root, 800, 800);
			
			stage.setScene(scene);
			stage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
