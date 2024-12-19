import java.util.ArrayList;
import java.util.Collections;

public class Deck extends ArrayList<Card> {
    private ArrayList<Card> cards;

    // Initializes a shuffled deck of 52 cards
    public Deck() {
        cards = new ArrayList<>();
        newDeck();
    }
    
    // Fills the deck with 52 cards and shuffles them
    public void newDeck() {
        cards.clear();
        char[] suits = {'C', 'D', 'S', 'H'};
        
        for (char suit : suits) {
            for (int value = 2; value <= 14; value++) {
                cards.add(new Card(suit, value));
            }
        }
        
        Collections.shuffle(cards); // Shuffle the deck
    }
    
    // Deals a card from the top; throws if deck is empty
    public Card dealCard() { 
        if (cards.isEmpty()) throw new IllegalStateException("The deck is empty. Create a new deck.");
        return cards.remove(0);
    }

    // Returns the number of remaining cards
    public int cardsLeft() { return cards.size(); }
}