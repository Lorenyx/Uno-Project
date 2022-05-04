package application.model;
/*
 * the Card class this will represent a single card 
 * it has enums for the Color and Value of the card
 * Color is the suit and value is the face value of the card
 * can be used to get the file name of the png of the type of card being used
 * 
*/
public class Card
{
    //enum for color types
    public enum Color
    {
        RED, BLUE, GREEN, YELLOW,/* WILD */;

        private static final Color[] cardColor = Color.values();
        public static Color getColor(int i)
        {
            return Color.cardColor[i];
        }
    }

    //enum for value types
    public enum Value
    {
        ZERO, ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, DRAW2, SKIP, REVERSE, /* WILD, WILD4 */;

        private static final Value[] cardValue = Value.values();
        public static Value getValue(int i)
        {
            return Value.cardValue[i];
        }
    }

    private final Color color;
    private final Value value;

    //constructor for cards
    public Card (Color cardColor, Value cardValue)
    {
        this.color = cardColor;
        this.value = cardValue;
    }

    //getter for cardColor
    public Color getCardColor() 
    {
        return color;
    }

    //getter for cardValue
    public Value getCardValue() 
    {
        return value;
    }

    //toString 
    public String toString () {
        return color + "_" + value; 
    }

    //toString for toFileName
    public String toFileName() {
        return "../data/" +toString()+ ".png";
    }

    //makes string equaling the name of the specific png file of the card
    public boolean equals(Card c) {
        return (this.color == c.color) && (this.value == c.value);
    }
}