package application;

public class Card
{
    //enum for color types
    enum Color
    {
        RED, BLUE, GREEN, YELLOW, WILD;

        private static final Color[] cardColor = Color.values();
        public static Color getColor(int i)
        {
            return Color.cardColor[i];
        }
    }

    //enum for value types
    enum Value
    {
        ZERO, ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, DRAW2, SKIP, REVERSE, WILD, WILD4;

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

    public String toFileName() {
        return "data/" +toString()+ ".png";
    }
}