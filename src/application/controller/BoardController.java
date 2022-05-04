package application.controller;

import application.Main;
import application.model.Card;
import application.model.Deck;
import application.model.GameLoop;
import application.model.Player;
/* The BoardController class controllers the main Uno game scene
 * it shows the human player their hand of cards by dynamically loading each card separately
 * it also allows the human player to select their discard card by clicking and it will be moved into the discard pile
 * this class also shows the number of cards in the CPU's hand at anytime 
 */
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.BoxBlur;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.util.Duration;


public class BoardController {
	
	Draggable drag = new Draggable();//Object to make player cards draggable
	DropShadow hoverShadow = new DropShadow(); //Object for the glowing border on the player cards
	BoxBlur blur = new BoxBlur();
	private double temp; // Holds the original xLayout when dragging the playerCard
	// Will hold the original x and y coordinates of the player cards
	private double x1, x2, x3, x4, x5, x6,x7;
	private double y1, y2, y3, y4, y5, y6,y7;
	int unoTemp = 1;

	@FXML 
	private HBox playerHandDisplay, cpu2HandDisplay;
	@FXML
	private VBox cpu1HandDisplay, cpu3HandDisplay;

	@FXML
	private ImageView topCard;
	@FXML
	private ImageView deck;
	@FXML
	private Button drawButton;
	@FXML
	private AnchorPane pane;
	@FXML
    private Region colorSelector;
	@FXML
	private Button greenButton;
	@FXML
	private Button redButton;
	@FXML
	private Button blueButton;
	@FXML
	private Button yellowButton;
	@FXML
	private Button unoButton;
	@FXML
	private Label gameOverLabel;
	
	
	//Gets the original X and Y Layout of the players Cards
	//Needed for making sure moving pieces can go back to original places when needed
	public void initialize() {
		gameOverLabel.setVisible(false);
		// Set opposite displays to correct rotation
		cpu2HandDisplay.setRotate(180);
		cpu3HandDisplay.setRotate(180);
		// creates blur for hovering
		hoverShadow.setBlurType(BlurType.GAUSSIAN);
		hoverShadow.setColor(Color.WHITE);
		hoverShadow.setSpread(.90);
		// assign gameplay variables
		Main.gameLoop = new GameLoop(this);
	}
	
	public void addCardToPlayer(Card C) {
		// Does not check if too many cards have been added
		ImageView addedCard = cardToImage(C);
		playerHandDisplay.getChildren().add(addedCard);
	}

	public void addCardToCPU1() {
		// Does not check if too many cards have been added
		ImageView addedCard = cardToImageV();
		// addedCard.setRotate(90);
		cpu1HandDisplay.getChildren().add(addedCard);
	}
	public void addCardToCPU2() {
		// Does not check if too many cards have been added
		ImageView addedCard = cardToImageH();
		// addedCard.setRotate(180);
		cpu2HandDisplay.getChildren().add(addedCard);
	}
	public void addCardToCPU3() {
		// Does not check if too many cards have been added
		ImageView addedCard = cardToImageV();
		// addedCard.setRotate(-90);		
		cpu3HandDisplay.getChildren().add(addedCard);
	}

	public void removeCardToCPU1() {
		cpu1HandDisplay.getChildren().remove(0);
		return;
	}
	public void removeCardToCPU2() {
		cpu3HandDisplay.getChildren().remove(0);
		return;
	}
	public void removeCardToCPU3() {
		cpu2HandDisplay.getChildren().remove(0);
		return;
	}

	public void setTopCardImage() {
		Image img = new Image(getClass().getResource(Deck.topCard.toFileName()).toExternalForm());
		topCard.setImage(img);
	}

	private ImageView cardToImage(Card C) {
		Image img = new Image(getClass().getResource(C.toFileName()).toExternalForm());
		ImageView iv = new ImageView();
		iv.setImage(img);
		iv.setFitWidth(75);
		iv.setPreserveRatio(true);
		// <ImageView fx:id="card2" fitHeight="147.0" fitWidth="102.0" layoutX="222.0" layoutY="609.0" onMouseEntered="#onCardHover" onMouseExited="#offCardHover" onMouseReleased="#cardDropped" pickOnBounds="true">
		iv.setOnMouseEntered((MouseEvent ent) -> {
			onCardHover(ent);
		});
		iv.setOnMouseExited((MouseEvent ent) -> {
			offCardHover(ent);
		});
		iv.setOnMouseReleased((MouseEvent ent) -> {
			cardDropped(ent);
		});
		iv.setOnMouseClicked((MouseEvent ent) -> {
			actionCardPlayed(ent);
		});
		return iv;
	}
	private ImageView cardToImageH() {
		Image img = new Image(getClass().getResource("../data/UNO_FRONT.PNG").toExternalForm());
		ImageView iv = new ImageView();
		iv.setImage(img);
		iv.setFitWidth(75);
		iv.setPreserveRatio(true);
		return iv;
	}
	private ImageView cardToImageV() {
		Image img = new Image(getClass().getResource("../data/UNO_FRONT_VERTICAL.PNG").toExternalForm());
		ImageView iv = new ImageView();
		iv.setImage(img);
		iv.setFitHeight(75);
		iv.setPreserveRatio(true);
		return iv;
	}

	private Card imageToCard(ImageView iv) {
		String fileName = iv.getImage().getUrl();
		String lines[] = fileName.split("/");
		String baseName = lines[lines.length - 1];
		lines = baseName.split("_");
		String colorStr = lines[0];
		String valueStr = lines[1].substring(0, lines[1].lastIndexOf(".png"));
		Card.Color color = Card.Color.valueOf(colorStr);
		Card.Value value = Card.Value.valueOf(valueStr);
		return new Card(color, value);
	}
	public void onCardHover(MouseEvent event) {
		//Creates image view object that is loaded with the information of the card being hovered over
		ImageView card = (ImageView) event.getSource();
		
		//Sets temp to the x layout of the image view
		//Needed to set the card back to the original x position if not played
		temp = card.getLayoutX();
		
		//Test if the card is touching the discard pile
		if(!(card.getBoundsInParent().intersects(topCard.getBoundsInParent()))) {
			//Used to create outline effect for the card being hovered over

		
			//Used to make card able to be dragged
			drag.makeDraggable(card);
			card.setEffect(hoverShadow); //Sets the outline effect to the card
			card.setLayoutY(574);//Raises the card up to set level
		}
		
	}
	
	public void offCardHover(MouseEvent event) {
		//Creates image view object that is loaded with the information of the card being hovered over
		ImageView card = (ImageView) event.getSource();
		
		//Test if the card is touching the discard pile
		if((card.intersects(topCard.getBoundsInLocal()))) {
			//Gets rid of outline effect on card 
			card.setEffect(null);
			//sets card back to original position if the card is not played
			card.setLayoutX(temp);
			card.setLayoutY(609);
		}
	}
	
	public void cardDropped(MouseEvent event) {
		//Test if the card is touching the discard pile and if the image on the card is blank
		ImageView card = (ImageView) event.getSource();
		if(card.getBoundsInParent().intersects(topCard.getBoundsInParent()) && (card.getImage() != null)) {
			
			topCard.setImage(card.getImage());//sets the image of the discard pile to that of the card played
			card.setImage(null);//sets them image of the card played to null;
			
			if(false)//if the card will call on the to string to test in the card name is WILD.jpg
			{
				colorSelector();
			}
			if(true)//Check to see if the amount of cards in the players hand is set to 1
			{
				unoButton.setVisible(true);//Set unoButton to be visible
				//Creates a pause transition lasting 2 seconds
				PauseTransition visiblePause = new PauseTransition(Duration.seconds(2)); 
				//After pause transition finishes lambda expressions checks if the card was clicked or not
				visiblePause.setOnFinished(event2 ->{ 
					unoButton.setVisible(false);
					if(unoTemp != 0) {
						//Add cards
						unoTemp = 1;
						System.out.println("UNO wasn't pressed");
					}
					else {
						unoTemp = 1;
						System.out.println("Uno Pressed");
					}
				});
				visiblePause.play();
			}
		}
	}
	
	public void actionCardPlayed(MouseEvent event) {
		ImageView iv = (ImageView) event.getSource();
		Card playedCard = imageToCard(iv);
		Player p = Main.gameLoop.getPlayer();
		if (p.performMove(playedCard)) {
			playerHandDisplay.getChildren().remove(iv);
			System.out.println("Entering cycle");
			Main.gameLoop.cycle();
		}
	}
	
	public void actionSkipTurn() {
		Main.gameLoop.turnNum++;
		Main.gameLoop.cycle();
	}

	public void onUnoClicked(MouseEvent event) {
		unoButton.setVisible(false);
		unoTemp = 0;
	}
	
	public void colorSelector() {
		topCard.setEffect(blur);
		deck.setEffect(blur);
		drawButton.setEffect(blur);
		
		colorSelector.setVisible(true);
		redButton.setVisible(true);
		blueButton.setVisible(true);
		greenButton.setVisible(true);
		yellowButton.setVisible(true);
	}
	
	public void onColorButton(MouseEvent event) {
		Button colorButton = (Button) event.getSource();
		
		topCard.setEffect(null);
		deck.setEffect(null);
		drawButton.setEffect(null);
		
		colorSelector.setVisible(false);
		redButton.setVisible(false);
		blueButton.setVisible(false);
		greenButton.setVisible(false);
		yellowButton.setVisible(false);
		
		System.out.println(colorButton.getId());
		
	}
	
	public void onCardPlayer(MouseEvent event) {
		ImageView cardImage = (ImageView) event.getSource();
		// Card card = 
	}
	public void actionDrawCard(MouseEvent event) {
		Player p = Main.gameLoop.getPlayer();
		Card drawn = p.drawCard();
		addCardToPlayer(drawn);
		
	}
	
	public void actionAnnounceWinner() {
		gameOverLabel.setVisible(true);
		try {
			Thread.sleep(1000);
		} catch (Exception E) {

		}
	}
}
