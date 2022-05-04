package application.model;
/*
The AI class handles the non human players on the board.
This class extends the player and includes a functon to pick its next card for automation

*/
class AI extends Player {
    public static enum Difficulty {EASY, MEDIUM, HARD};
    private Difficulty difficulty; // 0 - easiest, 3 - hardest
    
    public AI(String name, Difficulty difficulty) {
        super(name);
        this.difficulty = difficulty;
    }
    
    public boolean performMove() {
        Deck hand = getDeck();
        if (!canMove()) {
            return false;
        }
        for(int i = 0; i < hand.size()-1; i++) {
            if(performMove(hand.get(i))) {
                try {
                    Thread.sleep(750);
                    return true;
                } catch (Exception E) {

                }
            }
        }
        return false;

    }

    
}