package application;

import java.util.ArrayList;
import java.util.Collections;
// import java.util.Random;

public class Deck {
/*
 *The Deck class contains 108 Uno cards  
 */
	private ArrayList<Card> cards;
	static Card topCard;
	
	public Deck () {
		cards = new ArrayList<>();
	}

    public void setTopCard(Card card) {
        // Should be called after a card is played
        topCard = card;
    }

    public Card get(int index) {
        return cards.get(index);
    }

    public Card nextCard() {
        Card c = cards.get(0); // get card at front of array
        cards.remove(0); // remove card from ArrayList 
        return c;
    }

    public Card drawCard() {
        return nextCard();
    }
	
    public Card peekCard() {
        return cards.get(0); // get card at front of array
    }

    public void addCard(Card c) {
        cards.add(c);
    }

    public void removeCard(Card c) {
        // may need a try/catch
        cards.remove(c);
    }

	//initializes the deck and resets it
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
		
        Card.Value[] values = new Card.Value[] {Card.Value.WILD, Card.Value.WILD4}; //gets wild and wild+4 four times
        for(Card.Value value : values)
        {
            for(int i = 0; i < 4; i++)
            {
                cards.add( new Card(Card.Color.WILD, value));
            }
        }
	}

    public void replaceDeckWith(ArrayList<Card> cards) {
        this.cards = cards;
    }

    public boolean isEmpty(){
        return cards.isEmpty();
    }

    public int size() {
        return cards.size();
    }

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

