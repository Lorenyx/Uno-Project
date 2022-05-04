package application;


public class Player{
  
  private String name;
  private Deck hand;
  final int HAND_LIMIT = 7;
  
  //constructor for only name
  public Player(String name)
  {
    this.name = name;
    hand = new Deck();
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
    } /* else */ hand.addCard(c);
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
  public Card performMove(Card cardInPlay, int indexOfPlayerCard) throws Exception
  {
      // Card cardInQuestion = hand.get(indexOfPlayerCard);
      
      // if(isValid(cardInPlay, cardInQuestion))
      // {
    	//   Card c = hand.remove(indexOfPlayerCard);
    	//   return c;
      // }
      // else
      // {
    	//   System.out.println("Invalid match");
    	//   return null;
      // }
      return null;
  }
  
  //helper function for checking valid matches
  public boolean isValid(Card c, Card d)
  {
    // //TODO delete getType because special cards are representated as nums
	  // if(c.getType().equals(d.getType()) || c.getColor().equals(d.getColor()) || c.getNum() == d.getNum())
		//   return true;
	  // else
		  return false;
  }
  
  //***Will be performed at the start of the turn before the player even tries to pick a card***
  //canMove will take in the top card of the discard pile and check with all the players cards to see if that player
  //can actually play something (so the game doesn't brick up) if they can't play, a card will be added to hand or if
  //the hand is full a card will be discarded and the turn is skipped
  public boolean canMove(Card cardInPlay)
  {
	  boolean res = false;
	  // for(int i = 0; i < hand.size()-1; i++)
	  // {
		//   if(isValid(cardInPlay, hand.get(i)))
		//   {
		// 	  res = true;
		// 	  break;
		//   }  
	  // }
	  return res;
	  
  }
  
}