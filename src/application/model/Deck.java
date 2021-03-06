package application.model;
/*
 * The Deck class contains 108 Uno cards. This class creates deck object and 
 * handles things that happen to the deck: initializing, shuffling, getting size of,
 * getting next card, adding and removing cards from the deck to the players hands. 
 */
import java.util.ArrayList;
import java.util.Collections;
// import java.util.Random;

public class Deck {
/*
 *The Deck class contains 108 Uno cards  
 */
	private ArrayList<Card> cards;
	public static Card topCard;
	
    //constructor
	public Deck () {
		cards = new ArrayList<>();
	}

    //top of discard pile
    public void setTopCard(Card card) {
        // Should be called after a card is played
        topCard = card;
    }
    
    public Card get(int index) {
        return cards.get(index);
    }

        //finds the next card
    public Card nextCard() {
        Card c = cards.get(0); // get card at front of array
        cards.remove(0); // remove card from ArrayList 
        return c;
    }

    //draws the next card
    public Card drawCard() {
        return nextCard();
    }
	
    //get card at front of array
    public Card peekCard() {
        return cards.get(0); // get card at front of array
    }

    //adds card
    public void addCard(Card c) {
        cards.add(c);
    }

    public void removeCard(Card c) {
        // may need a try/catch
        for (Card d : cards) {
            if (c.equals(d)) {
                cards.remove(d);
                return;
            }
        }
    }

	 /*
     * initializes the deck and resets it
	 * default fillDeck method, fills the deck with the base 108 cards, essentially the setter
     * types of cards per color: 1 (0 card), 2 (1 - 9 cards), 2 (draw2, skip, reverse)
    */
	public void init() //default fillDeck method, fills the deck with the base 108 cards, essentially the setter
	{
		Card.Color[] colors = Card.Color.values();
		// cardsInDeck = 0;
		
		for(int i = 0; i < colors.length-1; i++) 
		{
			Card.Color color = colors[i];
			
			cards.add( new Card(color, Card.Value.getValue(0))); //gets the 1 one card
			
			for (int j = 1; j < 10; j++) 
			{									//gets the rest of the number cards twice
				cards.add( new Card(color, Card.Value.getValue(j)));
				cards.add( new Card(color, Card.Value.getValue(j)));
			}
			
			Card.Value[] values = new Card.Value[] {Card.Value.DRAW2, Card.Value.SKIP, Card.Value.REVERSE}; //gets the draw2, skip, and reverse cards twice
			for(Card.Value value : values)
			{
				cards.add( new Card(color, value));
				cards.add( new Card(color, value));
			}
		}
		
        // Card.Value[] values = new Card.Value[] {Card.Value.WILD, Card.Value.WILD4}; //gets wild and wild+4 four times
        // for(Card.Value value : values)
        // {
        //     for(int i = 0; i < 4; i++)
        //     {
        //         cards.add( new Card(Card.Color.WILD, value));
        //     }
        // }
	}

    public void replaceDeckWith(ArrayList<Card> cards) {
        this.cards = cards;
    }

    //checks if the cards are empty
    public boolean isEmpty(){
        return cards.isEmpty();
    }

    public int size() {
        return cards.size();
    }

    //shuffles the cards
    public void shuffle() {
        Collections.shuffle(cards);
        // int n = cards.size();
        // Random random = new Random();
        
        // for(int i = 0; i < cards.size(); i++) {
        //     int randomValue = i + random.nextInt(n - 1);
        //     Card randomCard = cards.get(randomValue);
        //     cards.set(randomValue, cards.get(i));
        //     cards.set(i, randomCard);
        // }
    }
			
}

