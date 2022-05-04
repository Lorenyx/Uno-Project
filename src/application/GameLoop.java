package application;

public class GameLoop {
    private Player currentTurn; //  change to int
    private Player[] players;
    private Deck drawPile;
    private Deck discardPile;
    private BoardController board;

    public static Card topCard;

    public GameLoop(BoardController board) {
        this.board = board;
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
        topCard = drawPile.nextCard();
        board.setTopCardImage();
    }

    // public Deck getDeck()

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
