package application;

import application.Card.Color;
import javafx.fxml.FXML;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class Player{
  
  private String name;
  private Deck hand;
  final int HAND_LIMIT = 7;
  @FXML private VBox displayV;
  @FXML private HBox displayH;
  
  //constructor for only name
  public Player(String name)
  {
    this.name = name;
    hand = new Deck();
  }
  
  public void setDisplay(HBox display) {
    displayH = display;
    displayV = null;
  }

  public void setDisplay(VBox display) {
    displayV = display;
    displayH = null;
  }

  

  /**
    * adds specifiied card to players hand. 
    * 
    * @return returns true if card added, false otherwise
    */
  public boolean addCard(Card c)
  {
    if (hand.size() >= HAND_LIMIT ) {
      return false;
    } /* else */ 
      hand.addCard(c);
    return true;
  }
  
  //removes a card from the given index of the arraylist
  //returns the card that was removed
  public void removeCard(Card card)
  {
    hand.removeCard(card);
  }
  
  //perfromMove will take in the top card of the discard deck and the index of the card the player is trying to play
  //check if the card is valid, and removes card. Returns null if it doesn't match. (throws Exception so surround in try/catch)
  public boolean performMove(Card cardPlayed) {
    //TODO delete this line
    System.out.println(hand.size());
      if (!isValid(cardPlayed)) {
        System.out.println("Move is not valid");
        return false;
      }
      //TODO check for UNO called
      removeCard(cardPlayed);
      Deck.topCard = cardPlayed;
      if (hand.isEmpty()) {
        // assign self as winner
        Main.gameLoop.setWinner(this);
      }
      //TODO delete this line
      System.out.println(hand.size());
      return true;
  }
  
  //helper function for checking valid matches
  public boolean isValid(Card card) {
    if (card.getCardColor() == Color.WILD) {
      // Wild card always accepatable
      return true;
    }
    if (card.getCardColor() == GameLoop.topCard.getCardColor())
      return true;
    if (card.getCardValue() == GameLoop.topCard.getCardValue()) {
      return true;
    }
    // else
    return false;
  }
  
  //***Will be performed at the start of the turn before the player even tries to pick a card***
  //canMove will take in the top card of the discard pile and check with all the players cards to see if that player
  //can actually play something (so the game doesn't brick up) if they can't play, a card will be added to hand or if
  //the hand is full a card will be discarded and the turn is skipped
  public boolean canMove(Card cardInPlay) {
	  for(int i = 0; i < hand.size()-1; i++) {
		  if(isValid(hand.get(i))) {
			  return true;
		  }  
	  }
	  return false;
  }
}