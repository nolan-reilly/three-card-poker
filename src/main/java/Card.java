public class Card {
	private char suit;
	private int value;
	
	// Initializes the card's suit and value
	Card(char suit, int value) {
		this.suit = suit;
		this.value = value;
	}

	public char getSuit() {
		return suit;
	}
	
	public int getValue() {
		return value;
	}
	
	// Returns card as a string with face cards as letters
    @Override
    public String toString() {
        String valueStr;
        switch (value) {
            case 14:
                valueStr = "A";
                break;
            case 13:
                valueStr = "K";
                break;
            case 12:
                valueStr = "Q";
                break;
            case 11:
                valueStr = "J";
                break;
            default:
                valueStr = String.valueOf(value);
        }
        return valueStr + suit;
    }
}