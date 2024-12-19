import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.HashSet;

public class DeckTest {

    private Deck deck;

    @BeforeEach
    public void setUp() {
        deck = new Deck();
    }

    @Test
    public void deckConstructorTest() {
        assertEquals(52, deck.cardsLeft(), "A new deck should have 52 cards.");
    }

    @Test
    public void newDeckTest() {
        deck.newDeck();
        assertEquals(52, deck.cardsLeft(), "After calling newDeck(), the deck should have 52 cards.");
    }

    @Test
    public void dealCardReducesDeckSizeTest() {
        Card dealtCard = deck.dealCard();
        assertNotNull(dealtCard, "Dealt card should not be null.");
        assertEquals(51, deck.cardsLeft(), "Dealing one card should reduce the deck size by one.");
    }

    @Test
    public void dealAllCardsTest() {
        for (int i = 0; i < 52; i++) {
            deck.dealCard();
        }
        assertEquals(0, deck.cardsLeft(), "Dealing all cards should leave the deck with 0 cards.");
        assertThrows(IllegalStateException.class, deck::dealCard, "Dealing a card from an empty deck should throw an exception.");
    }

    @Test
    public void deckHasUniqueCardsTest() {
        HashSet<String> uniqueCards = new HashSet<>();
        while (deck.cardsLeft() > 0) {
            Card card = deck.dealCard();
            String cardRepresentation = card.toString();
            assertFalse(uniqueCards.contains(cardRepresentation), "Each card should be unique in the deck.");
            uniqueCards.add(cardRepresentation);
        }
        assertEquals(52, uniqueCards.size(), "The deck should contain 52 unique cards.");
    }
}