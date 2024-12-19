import java.util.ArrayList;

public class Dealer {
    private Deck theDeck;
    private ArrayList<Card> dealersHand;

    // Initializes a new deck and an empty dealer's hand
    public Dealer() {
        theDeck = new Deck();
        dealersHand = new ArrayList<>();
    }

    // Deals a new hand of three cards to the dealer, shuffling if needed
    public ArrayList<Card> dealHand() {
    	shuffleDeck(); // Reshuffle deck if low on cards
        dealersHand.clear();
        
        for (int i = 0; i < 3; i++) {
            dealersHand.add(theDeck.dealCard());
        }

        // Set the dealer's hand
        setDealersHand(dealersHand);
        
        return new ArrayList<>(dealersHand);
    }

    // Shuffles the deck if there are 34 or fewer cards remaining
    public void shuffleDeck() {
        if (theDeck.cardsLeft() <= 34) {
            theDeck.newDeck();
            System.out.println("Deck reshuffled as it reached the card limit.");
        }
    }

    // Sets the dealer's hand to the provided hand
    public void setDealersHand(ArrayList<Card> hand) { this.dealersHand = new ArrayList<>(hand); }

    // Returns a copy of the dealer's hand
    public ArrayList<Card> getDealersHand() { return new ArrayList<>(dealersHand); }
    
    // Checks how many cards are left in the deck
    public int getCardsLeft() { return theDeck.cardsLeft(); }
    
    // Clears the dealers hand
    public void clearHand() { dealersHand.clear(); }
}