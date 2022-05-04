package application.controller;
/*
 * This is the Draggable class which allows the cards to be dragged from the user's hand into the discard pile
 */
import javafx.scene.Node;

public class Draggable {
	
	private double mouseAnchorX;
	private double mouseAnchorY;
	
	public void makeDraggable(Node node) {
		
		node.setOnMousePressed(mouseEvent -> {
            mouseAnchorX = mouseEvent.getX();
            mouseAnchorY = mouseEvent.getY();
        });

        node.setOnMouseDragged(mouseEvent -> {
            node.setLayoutX(mouseEvent.getSceneX() - mouseAnchorX);
            node.setLayoutY(mouseEvent.getSceneY() - mouseAnchorY);
        });
    }
}
