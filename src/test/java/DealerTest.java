import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;

public class DealerTest {

    private Dealer dealer;

    @BeforeEach
    public void setUp() {
        dealer = new Dealer(); // Create a new Dealer before each test
    }

    @Test
    public void dealHandReturnsThreeCardsTest() {
        ArrayList<Card> hand = dealer.dealHand();
        assertEquals(3, hand.size(), "The dealer's hand should contain exactly 3 cards after dealing.");
    }

    @Test
    public void dealHandReducesDeckSizeTest() {
        int initialDeckSize = dealer.getCardsLeft();
        dealer.dealHand();
        int newDeckSize = dealer.getCardsLeft();
        assertEquals(initialDeckSize - 3, newDeckSize, "Dealing a hand should reduce the deck size by 3.");
    }

    @Test
    public void shuffleDeckWhenDeckSizeIsLowTest() {
        while (dealer.getCardsLeft() > 34) {
            dealer.dealHand();
        }
        
        assertTrue(dealer.getCardsLeft() <= 34, "Deck should have 18 or fewer cards before reshuffling.");

        dealer.shuffleDeck();
        assertEquals(52, dealer.getCardsLeft(), "Deck should be reshuffled to 52 cards when low.");
    }

    @Test
    public void getDealersHandReturnsCorrectHandTest() {
        ArrayList<Card> hand = dealer.dealHand();
        ArrayList<Card> returnedHand = dealer.getDealersHand();
        assertEquals(hand, returnedHand, "The returned dealer's hand should match the dealt hand.");
    }

    @Test
    public void dealHandClearsPreviousHandTest() {
        ArrayList<Card> firstHand = dealer.dealHand();
        dealer.dealHand();
        ArrayList<Card> secondHand = dealer.getDealersHand();
        assertNotEquals(firstHand, secondHand, "Each deal should produce a new hand and clear the previous one.");
    }

    @Test
    public void shuffleDeckDoesNotShufflePrematurelyTest() {
        int initialDeckSize = dealer.getCardsLeft();
        dealer.shuffleDeck();
        assertEquals(initialDeckSize, dealer.getCardsLeft(), "Deck should not be shuffled if it has more than 18 cards.");
    }

    @Test
    public void dealersHandIsMutableCopyTest() {
        dealer.dealHand();
        ArrayList<Card> returnedHand = dealer.getDealersHand();
        returnedHand.clear();
        assertEquals(3, dealer.getDealersHand().size(), "Clearing the returned hand should not affect the dealer's original hand.");
    }
}