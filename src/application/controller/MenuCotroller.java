package application.controller;
/* The MenuController class controllers the main menu scene
 * It has two buttons: start button and instructions button
 * They are used to navigate through the menu 
 */
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class MenuCotroller {

	@FXML
    private ScrollPane rulesPane;

    @FXML
    private Rectangle rulesBox;

    @FXML
    private Text text;

    @FXML
    private Label closeHowToPlay;
    
    //Makes sure the rulesPane visibility is set to false at the start
    public void intialize() {
    	rulesPane.setVisible(false);
    }
    
    //Sets rules pane to visible when how to play is clicked on
    public void onHowToPlay(MouseEvent event) {
    	rulesPane.setVisible(true);
    }
   
    //Loads the game scene when start is clicked on
	public void onStart(MouseEvent event) {
    	try {
			Parent root = FXMLLoader.load(getClass().getResource("../view/Board.fxml"));
			Scene scene = new Scene(root, 800, 800);
			Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
			
			stage.setScene(scene);
			stage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
    }
    
	//Sets rulesPane visibility to false when it is closed 
    public void minimizeRules(MouseEvent event) {
    	rulesPane.setVisible(false);
    }
}
