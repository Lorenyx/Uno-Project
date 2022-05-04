package application.model;

import application.controller.BoardController;

public class GameLoop {
    
    private Player[] players;
    private Deck drawPile;
    private Deck discardPile;
    private BoardController board;
    

    // public static Card topCard;
    public static int turnNum = 0; //  change to int
    public static boolean isClockwise = true; // 1 for clockwise, 0 for counter-clockwise 

    private static Player winner;

    public GameLoop(BoardController board) {
        this.board = board;
        winner = null;
        drawPile = new Deck(); 
        drawPile.init();
        // initialize empty deck
        discardPile = new Deck(); // after a player moves a card, place it into discard pile

        Player PLAYER = new Player("PLUS ONE");
        // PLAYER.setDisplay(BoardController.get);
        AI CPU1 = new AI("CPU1", AI.Difficulty.EASY);
        AI CPU2 = new AI("CPU2", AI.Difficulty.EASY);
        AI CPU3 = new AI("CPU3", AI.Difficulty.EASY);
        // add all players to iterable loop
        players = new Player[]{PLAYER, CPU1, CPU2, CPU3};
        drawPile.shuffle(); // shuffle deck of cards
        handCardsToPlayers();
        // pull inital card to start playing
        setTopCard(drawPile.nextCard());
    }

    // public Deck getDeck()
    public void cycle() {
        Card prev = Deck.topCard;
        int turnsSinceUpdate = 0;
        while ((turnNum%4) != 0) {
            if (GameLoop.getWinner() != null) {
                board.actionAnnounceWinner();
            }
            if (turnsSinceUpdate >= 4) {
                return;
            }
            AI cur = (AI) players[turnNum%4];
            if (isClockwise) {
                cycleClockwise(cur);
            } else {
                cycleCounter(cur);
            }
            if (Deck.topCard.equals(prev)) {
                turnsSinceUpdate++;
            } else {
                prev = Deck.topCard;
            }
            System.out.println("Player"+(turnNum%4)+" played: "+Deck.topCard);
        }
        
    }

    // Helper function for playing AI in clockwise fashion
    private void cycleClockwise(AI ai) {
        if (ai.performMove()) {
            switch (Deck.topCard.getCardValue().toString()) {
                case "SKIP":
                    turnNum+=2;
                break;
                case "REVERSE":
                    isClockwise = !isClockwise;
                    turnNum--;
                break;
                case "DRAW2":
                    players[(turnNum+1)%4].drawCard();
                    players[(turnNum+1)%4].drawCard();
                    turnNum+=2;
                break;
                default:
                    turnNum++;
            }
        } else {
            turnNum++;
            return;
        }
        
    }

    // helper function to play AI in counterclockwise fashion
    private void cycleCounter(AI ai) {
        if (ai.performMove()) {
            switch (Deck.topCard.getCardValue().toString()) {
                case "SKIP":
                    turnNum-=2;
                break;
                case "REVERSE":
                    isClockwise = !isClockwise;
                    turnNum++;
                break;
                case "DRAW2":
                    players[(turnNum-1)%4].drawCard();
                    players[(turnNum-1)%4].drawCard();
                    turnNum-=2;
                break;
                default:
                    turnNum--;
            }
        } else {
            turnNum++;
            return;
        }
    }

    // Gets the human player
    public Player getPlayer() {
        return players[0];
    }

    // Assigns winner to person that ran out of cards first
    public void setWinner(Player p) {
        winner = p;
    }

    // returns who the current winner is, null if no winners
    public static Player getWinner() {
        return winner;
    }

    // draws a card from the draw pile
    public Card drawCardFromPile() {
        return drawPile.drawCard();
    }


    // Sets the card at the top of the discard pile
    public void setTopCard(Card top) {
        Deck.topCard = top;
        board.setTopCardImage();
    }

    // init function to fill eveyr players hands
    public void handCardsToPlayers() {
        // draw card from drawPile and hand to each player
        for (int i=0; i<7; i++) {
            for (int j=0; j<4; j++) {
                Card c = drawPile.drawCard(); // pull card off top of deck
                players[j].addCard(c); //  add card to players hand
                switch (j) {
					case 0: // player
						board.addCardToPlayer(c);
					break;
					case 1:
						board.addCardToCPU1();
					break;
					case 2:
						board.addCardToCPU2();
					break;
					case 3:
						board.addCardToCPU3();
					break;
				}
            }
        }
    } 
}
