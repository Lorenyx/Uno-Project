package application;

import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
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
	private ImageView DiscardPile;
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
	
	// GAME EVENT VARIABLES
	private Player currentTurn; //  change to int
    private Player[] players;
    private Deck drawPile;
    private Deck discardPile;
	
	
	//Gets the original X and Y Layout of the players Cards
	//Needed for making sure moving pieces can go back to original places when needed
	public void initialize() {

		// Set opposite displays to correct rotation
		cpu2HandDisplay.setRotate(180);
		cpu3HandDisplay.setRotate(180);
		// creates blur for hovering
		hoverShadow.setBlurType(BlurType.GAUSSIAN);
		hoverShadow.setColor(Color.WHITE);
		hoverShadow.setSpread(.90);
		// assign gameplay variables
		drawPile = new Deck(); 
        drawPile.init();
        // initialize empty deck
        discardPile = new Deck(); // after a player moves a card, place it into discard pile

        Player PLAYER = new Player("PLUS ONE");
        // PLAYER.setDisplay(BoardController.get);
        AI CPU1 = new AI("CPU1", AI.Difficulty.EASY);
        AI CPU2 = new AI("CPU2", AI.Difficulty.EASY);
        AI CPU3 = new AI("CPU3", AI.Difficulty.EASY);

        players = new Player[]{PLAYER, CPU1, CPU2, CPU3};
        drawPile.shuffle(); // shuffle deck of cards
        // Hand out 7 cards to each player
        for (int i=0; i<7; i++) {
            for (int j=0; j<4; j++) {
                Card c = drawPile.drawCard(); // pull card off top of deck
                players[j].addCard(c); //  add card to players hand
                switch (j) {
					case 0: // player
						addCardToPlayer(c);
					break;
					case 1:
						addCardToCPU1();
					break;
					case 2:
						addCardToCPU2();
					break;
					case 3:
						addCardToCPU3();
					break;
				}
            }
        }
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
		return iv;
	}

	private ImageView cardToImageH() {
		Image img = new Image(getClass().getResource("data/UNO_FRONT.PNG").toExternalForm());
		ImageView iv = new ImageView();
		iv.setImage(img);
		iv.setFitWidth(75);
		iv.setPreserveRatio(true);
		return iv;
	}
	private ImageView cardToImageV() {
		Image img = new Image(getClass().getResource("data/UNO_FRONT_VERTICAL.PNG").toExternalForm());
		ImageView iv = new ImageView();
		iv.setImage(img);
		iv.setFitHeight(75);
		iv.setPreserveRatio(true);
		return iv;
	}

	public void onCardHover(MouseEvent event) {
		//Creates image view object that is loaded with the information of the card being hovered over
		ImageView card = (ImageView) event.getSource();
		
		//Sets temp to the x layout of the image view
		//Needed to set the card back to the original x position if not played
		temp = card.getLayoutX();
		
		//Test if the card is touching the discard pile
		if(!(card.getBoundsInParent().intersects(DiscardPile.getBoundsInParent()))) {
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
		if((card.intersects(DiscardPile.getBoundsInLocal()))) {
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
		if(card.getBoundsInParent().intersects(DiscardPile.getBoundsInParent()) && (card.getImage() != null)) {
			
			DiscardPile.setImage(card.getImage());//sets the image of the discard pile to that of the card played
			card.setImage(null);//sets them image of the card played to null;
			
			cardShift();//Call to card shift to shift the cards down when a card is played
			
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
		//Call to fixLayout
		fixLayout();
	}
	
	
	public void onUnoClicked(MouseEvent event) {
		unoButton.setVisible(false);
		unoTemp = 0;
	}
	
	public void colorSelector() {
		DiscardPile.setEffect(blur);
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
		
		DiscardPile.setEffect(null);
		deck.setEffect(null);
		drawButton.setEffect(null);
		
		colorSelector.setVisible(false);
		redButton.setVisible(false);
		blueButton.setVisible(false);
		greenButton.setVisible(false);
		yellowButton.setVisible(false);
		
		System.out.println(colorButton.getId());
		
	}
	
	public void drawCard(MouseEvent event) {
		//TODO complete implementing this
		//sets image of the card drawn from deck will be dynamic after full implementation
		Image image = new Image(getClass().getResource("data/RED_REVERSE.png").toExternalForm());
		ImageView card = (ImageView) event.getSource();
		//Sets the image of the first blank card it finds when the draw card is clicked
		// if(card1.getImage() == null) {
		// 	card1.setImage(image);
		// }
		// else if(card2.getImage() == null) {
		// 	card2.setImage(image);
		// }
		// else if(card3.getImage() == null) {
		// 	card3.setImage(image);
		// }
		// else if(card4.getImage() == null) {
		// 	card4.setImage(image);
		// }
		// else if(card5.getImage() == null) {
		// 	card5.setImage(image);
		// }
		// else if(card6.getImage() == null) {
		// 	card6.setImage(image);
		// }
		// else if(card7.getImage() == null) {
		// 	card7.setImage(image);
		// }
		
		//Call to fixLayout
		fixLayout();
		
	}
	
	public void cardShift() {
				
				//Shifts the cards down when a card is played so there is no gaps
				// if(card1.getImage() == null ) {
				// 	card1.setImage(card2.getImage());
				// 	card2.setImage(null);
				// }
				// if(card2.getImage() == null) {
				// 	card2.setImage(card3.getImage());
				// 	card3.setImage(null);
				// }
				// if(card3.getImage() == null) {
				// 	card3.setImage(card4.getImage());
				// 	card4.setImage(null);
				// }
				// if(card4.getImage() == null) {
				// 	card4.setImage(card5.getImage());
				// 	card5.setImage(null);
				// }
				// if(card5.getImage() == null) {
				// 	card5.setImage(card6.getImage());
				// 	card6.setImage(null);
				// }
				// if(card6.getImage() == null) {
				// 	card6.setImage(card7.getImage());
				// 	card7.setImage(null);
				// }
	}
	
	//Function is used to keep the layout correct to make sure no bugs mess it up
	public void fixLayout() {
		
		//Sets the cards to the correct position when not being touched
		// card1.setLayoutX(x1);
		// card1.setLayoutY(y1);
		// card2.setLayoutX(x2);
		// card2.setLayoutY(y2);
		// card3.setLayoutX(x3);
		// card3.setLayoutY(y3);
		// card4.setLayoutX(x4);
		// card4.setLayoutY(y4);
		// card5.setLayoutX(x5);
		// card5.setLayoutY(y5);
		// card6.setLayoutX(x6);
		// card6.setLayoutY(y6);
		// card7.setLayoutX(x7);
		// card7.setLayoutY(y7);
		
		// //Keeps the cards overlapping in the same way
		// card1.toFront();
		// card2.toFront();
		// card3.toFront();
		// card4.toFront();
		// card5.toFront();
		// card6.toFront();
		// card7.toFront();
	}
}
