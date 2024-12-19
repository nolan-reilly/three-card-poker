import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;

public class ThreeCardLogicTest {

    private ArrayList<Card> hand;
    private ArrayList<Card> dealerHand;
    private ArrayList<Card> playerHand;

    @BeforeEach
    public void setUp() {
        hand = new ArrayList<>();
        dealerHand = new ArrayList<>();
        playerHand = new ArrayList<>();
    }

    @Test
    public void evalHandHighCardTestOne() {
        hand.add(new Card('C', 2));
        hand.add(new Card('D', 5));
        hand.add(new Card('H', 9));
        assertEquals(0, ThreeCardLogic.evalHand(hand), "Hand ('C2','D5','H') should be evaluated as a high card.");
    }
    
    @Test
    public void evalHandHighCardTestTwo() {
        hand.add(new Card('H', 3));
        hand.add(new Card('S', 6));
        hand.add(new Card('C', 11));
        assertEquals(0, ThreeCardLogic.evalHand(hand), "Hand ('H3','S6','C11') should be evaluated as a high card.");
    }
    
    @Test
    public void evalHandHighCardTestThree() {
        hand.add(new Card('S', 4));
        hand.add(new Card('D', 8));
        hand.add(new Card('H', 11));
        assertEquals(0, ThreeCardLogic.evalHand(hand), "Hand ('S4','D8','H11') should be evaluated as a high card.");
    }

    @Test
    public void evalHandHighCardTestFour() {
        hand.add(new Card('C', 6));
        hand.add(new Card('H', 10));
        hand.add(new Card('D', 13));
        assertEquals(0, ThreeCardLogic.evalHand(hand), "Hand ('C6','H10','D13') should be evaluated as a high card.");
    }
    
    @Test
    public void evalHandStraightFlushTestOne() {
        hand.add(new Card('C', 10));
        hand.add(new Card('C', 9));
        hand.add(new Card('C', 8));
        assertEquals(1, ThreeCardLogic.evalHand(hand), "Hand ('C10','C9','C8') should be evaluated as a straight flush.");
    }
    
    @Test
    public void evalHandStraightFlushTestTwo() {
        hand.add(new Card('S', 2));
        hand.add(new Card('S', 3));
        hand.add(new Card('S', 4));
        assertEquals(1, ThreeCardLogic.evalHand(hand), "Hand ('S2','S3','S4') should be evaluated as a straight flush.");
    }
    
    @Test
    public void evalHandStraightFlushTestThree() {
        hand.add(new Card('H', 6));
        hand.add(new Card('H', 7));
        hand.add(new Card('H', 8));
        assertEquals(1, ThreeCardLogic.evalHand(hand), "Hand ('H6','H7','H8') should be evaluated as a straight flush.");
    }

    @Test
    public void evalHandStraightFlushTestFour() {
        hand.add(new Card('D', 9));
        hand.add(new Card('D', 10));
        hand.add(new Card('D', 11));
        assertEquals(1, ThreeCardLogic.evalHand(hand), "Hand ('D9','D10','D11') should be evaluated as a straight flush.");
    }
    
    @Test
    public void evalHandThreeOfAKindTestOne() {
        hand.add(new Card('C', 12));
        hand.add(new Card('H', 12));
        hand.add(new Card('S', 12));
        assertEquals(2, ThreeCardLogic.evalHand(hand), "Hand ('C12','H12','S12') should be evaluated as three of a kind.");
    }
    
    @Test
    public void evalHandThreeOfAKindTestTwo() {
        hand.add(new Card('D', 5));
        hand.add(new Card('C', 5));
        hand.add(new Card('S', 5));
        assertEquals(2, ThreeCardLogic.evalHand(hand), "Hand ('D5','C5','S5') should be evaluated as three of a kind.");
    }
    
    @Test
    public void evalHandThreeOfAKindTestThree() {
        hand.add(new Card('H', 8));
        hand.add(new Card('S', 8));
        hand.add(new Card('D', 8));
        assertEquals(2, ThreeCardLogic.evalHand(hand), "Hand ('H8','S8','D8') should be evaluated as three of a kind.");
    }

    @Test
    public void evalHandThreeOfAKindTestFour() {
        hand.add(new Card('C', 11));
        hand.add(new Card('D', 11));
        hand.add(new Card('H', 11));
        assertEquals(2, ThreeCardLogic.evalHand(hand), "Hand ('C11','D11','H11') should be evaluated as three of a kind.");
    }

    @Test
    public void evalHandStraightTestOne() {
        hand.add(new Card('D', 8));
        hand.add(new Card('C', 7));
        hand.add(new Card('D', 6));
        assertEquals(3, ThreeCardLogic.evalHand(hand), "Hand ('D8','C7','D6') should be evaluated as a straight.");
    }
    
    @Test
    public void evalHandStraightTestTwo() {
        hand.add(new Card('H', 4));
        hand.add(new Card('S', 5));
        hand.add(new Card('D', 6));
        assertEquals(3, ThreeCardLogic.evalHand(hand), "Hand ('H4','S5','D6') should be evaluated as a straight.");
    }
    
    @Test
    public void evalHandStraightTestThree() {
        hand.add(new Card('H', 5));
        hand.add(new Card('D', 6));
        hand.add(new Card('C', 7));
        assertEquals(3, ThreeCardLogic.evalHand(hand), "Hand ('H5','D6','C7') should be evaluated as a straight.");
    }

    @Test
    public void evalHandStraightTestFour() {
        hand.add(new Card('C', 10));
        hand.add(new Card('H', 11));
        hand.add(new Card('S', 12));
        assertEquals(3, ThreeCardLogic.evalHand(hand), "Hand ('C10','H11','S12') should be evaluated as a straight.");
    }
    
    @Test
    public void evalHandStraightTestWithAceLow() {
        hand.add(new Card('D', 2));
        hand.add(new Card('C', 3));
        hand.add(new Card('H', 4));
        assertEquals(3, ThreeCardLogic.evalHand(hand), "Hand ('D2','C3','H4') should be evaluated as a straight.");
    }

    @Test
    public void evalHandStraightTestWithHighValues() {
        hand.add(new Card('D', 12));
        hand.add(new Card('H', 13));
        hand.add(new Card('S', 14));
        assertEquals(3, ThreeCardLogic.evalHand(hand), "Hand ('D12','H13','S14') should be evaluated as a straight.");
    }

    @Test
    public void evalHandFlushTestOne() {
        hand.add(new Card('D', 13));
        hand.add(new Card('D', 9));
        hand.add(new Card('D', 7));
        assertEquals(4, ThreeCardLogic.evalHand(hand), "Hand ('D14','D9','D7') should be evaluated as a flush.");
    }
    
    @Test
    public void evalHandFlushTestTwo() {
        hand.add(new Card('C', 9));
        hand.add(new Card('C', 2));
        hand.add(new Card('C', 14));
        assertEquals(4, ThreeCardLogic.evalHand(hand), "Hand ('C9','C2','C14') should be evaluated as a flush.");
    }
    
    @Test
    public void evalHandFlushTestThree() {
        hand.add(new Card('S', 3));
        hand.add(new Card('S', 7));
        hand.add(new Card('S', 13));
        assertEquals(4, ThreeCardLogic.evalHand(hand), "Hand ('S3','S7','S13') should be evaluated as a flush.");
    }

    @Test
    public void evalHandFlushTestFour() {
        hand.add(new Card('H', 4));
        hand.add(new Card('H', 10));
        hand.add(new Card('H', 14));
        assertEquals(4, ThreeCardLogic.evalHand(hand), "Hand ('H4','H10','H14') should be evaluated as a flush.");
    }
    
    @Test
    public void evalHandPairTestOne() {
        hand.add(new Card('C', 13));
        hand.add(new Card('D', 9));
        hand.add(new Card('H', 13));
        assertEquals(5, ThreeCardLogic.evalHand(hand), "Hand ('C13','D9','H13') should be evaluated as a pair.");
    }
    
    @Test
    public void evalHandPairTestTwo() {
        hand.add(new Card('H', 10));
        hand.add(new Card('C', 10));
        hand.add(new Card('D', 3));
        assertEquals(5, ThreeCardLogic.evalHand(hand), "Hand ('H10','C10','D3') should be evaluated as a pair.");
    }
    
    @Test
    public void evalHandPairTestThree() {
        hand.add(new Card('D', 7));
        hand.add(new Card('C', 7));
        hand.add(new Card('H', 9));
        assertEquals(5, ThreeCardLogic.evalHand(hand), "Hand ('D7','C7','H9') should be evaluated as a pair.");
    }

    @Test
    public void evalHandPairTestFour() {
        hand.add(new Card('S', 2));
        hand.add(new Card('H', 2));
        hand.add(new Card('C', 8));
        assertEquals(5, ThreeCardLogic.evalHand(hand), "Hand ('S2','H2','C8') should be evaluated as a pair.");
    }

    @Test
    public void evalPPWinningsHighCardTest() {
        hand.add(new Card('C', 2));
        hand.add(new Card('D', 5));
        hand.add(new Card('H', 9));
        assertEquals(0, ThreeCardLogic.evalPPWinnings(hand, 10), "Winnings for a high card hand ('C2','D5','H9') with a bet of 10 should be 0.");
    }

    @Test
    public void evalPPWinningsPairTest() {
        hand.add(new Card('H', 7));
        hand.add(new Card('C', 7));
        hand.add(new Card('D', 2));
        assertEquals(10, ThreeCardLogic.evalPPWinnings(hand, 10), "Winnings for a pair hand ('H7','C7','D2') with a bet of 10 should be 10.");
    }

    @Test
    public void evalPPWinningsFlushTest() {
        hand.add(new Card('D', 13));
        hand.add(new Card('D', 9));
        hand.add(new Card('D', 5));
        assertEquals(30, ThreeCardLogic.evalPPWinnings(hand, 10), "Winnings for a flush hand ('D13','D9','D5') with a bet of 10 should be 30.");
    }

    @Test
    public void evalPPWinningsStraightTest() {
        hand.add(new Card('H', 3));
        hand.add(new Card('S', 4));
        hand.add(new Card('D', 5));
        assertEquals(60, ThreeCardLogic.evalPPWinnings(hand, 10), "Winnings for a straight hand ('H3','S4','D5') with a bet of 10 should be 60.");
    }

    @Test
    public void evalPPWinningsThreeOfAKindTest() {
        hand.add(new Card('C', 10));
        hand.add(new Card('D', 10));
        hand.add(new Card('H', 10));
        assertEquals(300, ThreeCardLogic.evalPPWinnings(hand, 10), "Winnings for a three of a kind hand ('C10','D10','H10') with a bet of 10 should be 300.");
    }

    @Test
    public void evalPPWinningsStraightFlushTest() {
        hand.add(new Card('S', 6));
        hand.add(new Card('S', 7));
        hand.add(new Card('S', 8));
        assertEquals(400, ThreeCardLogic.evalPPWinnings(hand, 10), "Winnings for a straight flush hand ('S6','S7','S8') with a bet of 10 should be 400.");
    }
    
    @Test
    public void compareHandsDealerWinsWithStraightFlush() {
        dealerHand.add(new Card('S', 10));
        dealerHand.add(new Card('S', 9));
        dealerHand.add(new Card('S', 8));

        playerHand.add(new Card('H', 3));
        playerHand.add(new Card('S', 5));
        playerHand.add(new Card('D', 7));

        assertEquals(1, ThreeCardLogic.compareHands(dealerHand, playerHand), 
                "Dealer should win with a straight flush against the player's high card.");
    }

    @Test
    public void compareHandsPlayerWinsWithThreeOfAKind() {
        dealerHand.add(new Card('H', 7));
        dealerHand.add(new Card('S', 5));
        dealerHand.add(new Card('D', 3));

        playerHand.add(new Card('C', 9));
        playerHand.add(new Card('H', 9));
        playerHand.add(new Card('S', 9));

        assertEquals(2, ThreeCardLogic.compareHands(dealerHand, playerHand), 
                "Player should win with three of a kind against the dealer's high card.");
    }

    @Test
    public void compareHandsPlayerWinsWithFlushOverPair() {
        dealerHand.add(new Card('C', 4));
        dealerHand.add(new Card('D', 4));
        dealerHand.add(new Card('H', 10));

        playerHand.add(new Card('S', 7));
        playerHand.add(new Card('S', 2));
        playerHand.add(new Card('S', 11));

        assertEquals(2, ThreeCardLogic.compareHands(dealerHand, playerHand), 
                "Player should win with a flush against the dealer's pair.");
    }

    @Test
    public void compareHandsDealerWinsWithStraightOverHighCard() {
        dealerHand.add(new Card('H', 4));
        dealerHand.add(new Card('S', 5));
        dealerHand.add(new Card('D', 6));

        playerHand.add(new Card('C', 2));
        playerHand.add(new Card('D', 8));
        playerHand.add(new Card('S', 13));

        assertEquals(1, ThreeCardLogic.compareHands(dealerHand, playerHand), 
                "Dealer should win with a straight against the player's high card.");
    }

    @Test
    public void compareHandsTieWithHighCard() {
        dealerHand.add(new Card('S', 3));
        dealerHand.add(new Card('D', 10));
        dealerHand.add(new Card('C', 7));

        playerHand.add(new Card('H', 3));
        playerHand.add(new Card('C', 10));
        playerHand.add(new Card('D', 7));

        assertEquals(0, ThreeCardLogic.compareHands(dealerHand, playerHand), 
                "Dealer and Player should tie with identical high card values.");
    }

    @Test
    public void compareHandsPlayerWinsWithPairHighCardTieBreaker() {
        dealerHand.add(new Card('H', 5));
        dealerHand.add(new Card('S', 5));
        dealerHand.add(new Card('D', 3));

        playerHand.add(new Card('C', 5));
        playerHand.add(new Card('D', 5));
        playerHand.add(new Card('S', 4));

        assertEquals(2, ThreeCardLogic.compareHands(dealerHand, playerHand), 
                "Player should win with a higher pair.");
    }
    
    @Test
    public void compareHandsDealerWinsByHighCardInTie() {
        dealerHand.add(new Card('H', 10));
        dealerHand.add(new Card('S', 6));
        dealerHand.add(new Card('D', 3));

        playerHand.add(new Card('C', 10));
        playerHand.add(new Card('D', 6));
        playerHand.add(new Card('S', 2));

        assertEquals(1, ThreeCardLogic.compareHands(dealerHand, playerHand), 
                "Dealer should win with a higher high card in a tie-breaking scenario.");
    }

    @Test
    public void compareHandsPlayerWinsTestOne() {
        dealerHand.add(new Card('C', 2));
        dealerHand.add(new Card('D', 5));
        dealerHand.add(new Card('H', 9));

        playerHand.add(new Card('H', 10));
        playerHand.add(new Card('C', 10));
        playerHand.add(new Card('S', 3));

        assertEquals(2, ThreeCardLogic.compareHands(dealerHand, playerHand), 
                     "Player should win with a pair against the dealer's high card.");
    }

    @Test
    public void compareHandsPlayerWinsTestTwo() {
        dealerHand.add(new Card('S', 4));
        dealerHand.add(new Card('C', 4));
        dealerHand.add(new Card('D', 6));

        playerHand.add(new Card('H', 9));
        playerHand.add(new Card('H', 3));
        playerHand.add(new Card('H', 14));

        assertEquals(2, ThreeCardLogic.compareHands(dealerHand, playerHand), "Player should win with a flush against the dealer's pair.");
    }

    @Test
    public void compareHandsDealerWinsTestOne() {
        dealerHand.add(new Card('D', 6));
        dealerHand.add(new Card('H', 7));
        dealerHand.add(new Card('C', 8));

        playerHand.add(new Card('S', 3));
        playerHand.add(new Card('H', 5));
        playerHand.add(new Card('D', 10));

        assertEquals(1, ThreeCardLogic.compareHands(dealerHand, playerHand), 
                     "Dealer should win with a straight against the player's high card.");
    }

    @Test
    public void compareHandsDealerWinsTestTwo() {
        dealerHand.add(new Card('C', 8));
        dealerHand.add(new Card('D', 8));
        dealerHand.add(new Card('H', 8));

        playerHand.add(new Card('S', 5));
        playerHand.add(new Card('H', 6));
        playerHand.add(new Card('D', 7));

        assertEquals(1, ThreeCardLogic.compareHands(dealerHand, playerHand), "Dealer should win with three of a kind against the player's straight.");
    }

    @Test
    public void compareHandsTieTestOne() {
        dealerHand.add(new Card('C', 11));
        dealerHand.add(new Card('H', 8));
        dealerHand.add(new Card('D', 5));

        playerHand.add(new Card('S', 11));
        playerHand.add(new Card('D', 8));
        playerHand.add(new Card('H', 5));

        assertEquals(0, ThreeCardLogic.compareHands(dealerHand, playerHand), "It should be a tie when both hands have the same high card.");
    }

    @Test
    public void compareHandsTieTestTwo() {
        dealerHand.add(new Card('S', 2));
        dealerHand.add(new Card('S', 10));
        dealerHand.add(new Card('S', 7));

        playerHand.add(new Card('S', 7));
        playerHand.add(new Card('S', 10));
        playerHand.add(new Card('S', 2));

        assertEquals(0, ThreeCardLogic.compareHands(dealerHand, playerHand), "It should be a tie when both hands have the same flush.");
    }
    
    @Test
    public void evalHandStraightFlushTest() {
        ArrayList<Card> hand = new ArrayList<>();
        hand.add(new Card('H', 10));
        hand.add(new Card('H', 11));
        hand.add(new Card('H', 12));
        assertEquals(1, ThreeCardLogic.evalHand(hand), "Expected Straight Flush (1)");
    }

    @Test
    public void evalHandThreeOfAKindTest() {
        ArrayList<Card> hand = new ArrayList<>();
        hand.add(new Card('S', 8));
        hand.add(new Card('H', 8));
        hand.add(new Card('D', 8));
        assertEquals(2, ThreeCardLogic.evalHand(hand), "Expected Three of a Kind (2)");
    }

    @Test
    public void evalHandStraightTest() {
        ArrayList<Card> hand = new ArrayList<>();
        hand.add(new Card('S', 2));
        hand.add(new Card('H', 3));
        hand.add(new Card('D', 4));
        assertEquals(3, ThreeCardLogic.evalHand(hand), "Expected Straight (3)");
    }

    @Test
    public void evalHandFlushTest() {
        ArrayList<Card> hand = new ArrayList<>();
        hand.add(new Card('C', 2));
        hand.add(new Card('C', 7));
        hand.add(new Card('C', 10));
        assertEquals(4, ThreeCardLogic.evalHand(hand), "Expected Flush (4)");
    }

    @Test
    public void compareHandsPlayerWinsWithHighCardAceTest() {
        playerHand.add(new Card('H', 3));
        playerHand.add(new Card('D', 14));
        playerHand.add(new Card('C', 8));

        dealerHand.add(new Card('S', 4));
        dealerHand.add(new Card('H', 2));
        dealerHand.add(new Card('S', 12));

        assertEquals(2, ThreeCardLogic.compareHands(dealerHand, playerHand),
                "Player should win with a high card Ace against dealer's high card Queen.");
    }
    
    @Test
    public void evalHandPairTest() {
        ArrayList<Card> hand = new ArrayList<>();
        hand.add(new Card('S', 5));
        hand.add(new Card('H', 5));
        hand.add(new Card('D', 9));
        assertEquals(5, ThreeCardLogic.evalHand(hand), "Expected Pair (5)");
    }

    @Test
    public void evalHandHighCardTest() {
        ArrayList<Card> hand = new ArrayList<>();
        hand.add(new Card('S', 2));
        hand.add(new Card('H', 5));
        hand.add(new Card('D', 9));
        assertEquals(0, ThreeCardLogic.evalHand(hand), "Expected High Card (0)");
    }

    @Test
    public void evalPPWinningsStraightFlushTestTwo() {
        ArrayList<Card> hand = new ArrayList<>();
        hand.add(new Card('H', 10));
        hand.add(new Card('H', 11));
        hand.add(new Card('H', 12));
        int bet = 5;
        assertEquals(200, ThreeCardLogic.evalPPWinnings(hand, bet), "Expected Pair Plus Winnings for Straight Flush (5 * 40)");
    }

    @Test
    public void evalPPWinningsThreeOfAKind() {
        ArrayList<Card> hand = new ArrayList<>();
        hand.add(new Card('S', 8));
        hand.add(new Card('H', 8));
        hand.add(new Card('D', 8));
        int bet = 5;
        assertEquals(150, ThreeCardLogic.evalPPWinnings(hand, bet), "Expected Pair Plus Winnings for Three of a Kind (5 * 30)");
    }

    @Test
    public void evalPPWinningsPairTestTwo() {
        ArrayList<Card> hand = new ArrayList<>();
        hand.add(new Card('S', 5));
        hand.add(new Card('H', 5));
        hand.add(new Card('D', 9));
        int bet = 5;
        assertEquals(5, ThreeCardLogic.evalPPWinnings(hand, bet), "Expected Pair Plus Winnings for Pair (5 * 1)");
    }

    @Test
    public void compareHandsPlayerWinsTest() {
        ArrayList<Card> dealerHand = new ArrayList<>();
        dealerHand.add(new Card('S', 5));
        dealerHand.add(new Card('H', 5));
        dealerHand.add(new Card('D', 8));

        ArrayList<Card> playerHand = new ArrayList<>();
        playerHand.add(new Card('S', 9));
        playerHand.add(new Card('H', 9));
        playerHand.add(new Card('D', 9));

        assertEquals(2, ThreeCardLogic.compareHands(dealerHand, playerHand), "Expected Player to Win (2)");
    }

    @Test
    public void compareHandsDealerWinsTest() {
        ArrayList<Card> dealerHand = new ArrayList<>();
        dealerHand.add(new Card('S', 8));
        dealerHand.add(new Card('H', 8));
        dealerHand.add(new Card('D', 8));

        ArrayList<Card> playerHand = new ArrayList<>();
        playerHand.add(new Card('S', 7));
        playerHand.add(new Card('H', 7));
        playerHand.add(new Card('D', 7));

        assertEquals(1, ThreeCardLogic.compareHands(dealerHand, playerHand), "Expected Dealer to Win (1)");
    }

    @Test
    public void compareHandsTieTest() {
        ArrayList<Card> dealerHand = new ArrayList<>();
        dealerHand.add(new Card('S', 5));
        dealerHand.add(new Card('H', 9));
        dealerHand.add(new Card('D', 13));

        ArrayList<Card> playerHand = new ArrayList<>();
        playerHand.add(new Card('C', 5));
        playerHand.add(new Card('S', 9));
        playerHand.add(new Card('H', 13));

        assertEquals(0, ThreeCardLogic.compareHands(dealerHand, playerHand), "Expected Tie (0)");
    }
}