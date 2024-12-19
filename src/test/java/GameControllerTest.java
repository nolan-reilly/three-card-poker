import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

public class GameControllerTest {

    private GameController gameController;

    @BeforeEach
    public void setUp() {
        gameController = new GameController();
        gameController.getPlayer().setFunds(500); // Initial funds set to $500
    }

    @Test
    public void placeValidBetsTest() {
        gameController.placeBets(10, 5);
        Player player = gameController.getPlayer();
        assertEquals(500 - 10 - 5, player.getFunds(), "Funds should be reduced by the sum of bets.");
        assertEquals(10, player.getAnteBet(), "Ante bet should be $10.");
        assertEquals(5, player.getPairPlusBet(), "Pair Plus bet should be $5.");
    }

    @Test
    public void placeInvalidAnteBetTest() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            gameController.placeBets(3, 5); // Invalid Ante bet
        });
        assertEquals("Ante bet must be between $5 and $25.", exception.getMessage());
    }

    @Test
    public void placeInvalidPairPlusBetTest() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            gameController.placeBets(10, 30); // Invalid Pair Plus bet
        });
        assertEquals("Pair Plus bet must be between $5 and $25.", exception.getMessage());
    }

    @Test
    public void placeBetsInsufficientFundsTest() {
        gameController.getPlayer().setFunds(10); // Funds set to $10
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            gameController.placeBets(10, 5); // Total bets exceed available funds
        });
        assertEquals("Insufficient funds for the Pair Plus bet.", exception.getMessage());
    }

    @Test
    public void dealCardsTest() {
        gameController.placeBets(10, 5);
        gameController.dealCards();
        assertNotNull(gameController.getPlayer().getHand(), "Player should have a hand after dealing.");
        assertNotNull(gameController.getDealer().getDealersHand(), "Dealer should have a hand after dealing.");
        assertEquals(3, gameController.getPlayer().getHand().size(), "Player should have 3 cards.");
        assertEquals(3, gameController.getDealer().getDealersHand().size(), "Dealer should have 3 cards.");
    }

    @Test
    public void playerDecidesToPlayTest() {
        gameController.placeBets(10, 0);
        gameController.dealCards();
        gameController.playerDecision(true);
        assertFalse(gameController.getPlayer().hasFolded(), "Player should not have folded.");
    }

    @Test
    public void playerDecidesToFoldTest() {
        gameController.placeBets(10, 0);
        gameController.dealCards();
        gameController.playerDecision(false);
        assertTrue(gameController.getPlayer().hasFolded(), "Player should have folded.");
    }

    @Test
    public void evaluateHandsPlayerWinsWithDealerNotQualifyingTest() {
        ArrayList<Card> dealerHand = new ArrayList<>();
        dealerHand.add(new Card('H', 2));
        dealerHand.add(new Card('D', 4));
        dealerHand.add(new Card('S', 6));
        gameController.getDealer().setDealersHand(dealerHand);

        ArrayList<Card> playerHand = new ArrayList<>();
        playerHand.add(new Card('C', 14));
        playerHand.add(new Card('D', 13));
        playerHand.add(new Card('H', 12));
        gameController.getPlayer().setHand(playerHand);

        gameController.placeBets(10, 0);
        gameController.playerDecision(true);
        gameController.evaluateHands();

        int expectedFunds = 500;
        assertEquals(expectedFunds, gameController.getPlayer().getFunds(), 
            "Player should have Ante and Play bets returned due to dealer not qualifying.");
    }

    @Test
    public void evaluateHandsPlayerLosesTest() {
        ArrayList<Card> dealerHand = new ArrayList<>();
        dealerHand.add(new Card('H', 14));
        dealerHand.add(new Card('D', 13));
        dealerHand.add(new Card('S', 12));
        gameController.getDealer().setDealersHand(dealerHand);

        ArrayList<Card> playerHand = new ArrayList<>();
        playerHand.add(new Card('C', 2));
        playerHand.add(new Card('D', 3));
        playerHand.add(new Card('H', 4));
        gameController.getPlayer().setHand(playerHand);

        gameController.placeBets(10, 0);
        gameController.playerDecision(true);
        gameController.evaluateHands();

        int expectedFunds = 500 - 10 - 10;
        assertEquals(expectedFunds, gameController.getPlayer().getFunds(), "Player should have lost Ante and Play bets.");
    }
    
    @Test
    public void evaluateHandsDealerDoesNotQualifyTest() {
        ArrayList<Card> dealerHand = new ArrayList<>();
        dealerHand.add(new Card('H', 10));
        dealerHand.add(new Card('D', 3));
        dealerHand.add(new Card('S', 8));
        gameController.getDealer().setDealersHand(dealerHand);

        ArrayList<Card> playerHand = new ArrayList<>();
        playerHand.add(new Card('C', 11));
        playerHand.add(new Card('D', 7));
        playerHand.add(new Card('H', 5));
        gameController.getPlayer().setHand(playerHand);

        gameController.placeBets(10, 0);
        gameController.playerDecision(true);
        gameController.evaluateHands();

        int expectedFunds = 500 - 10 - 10 + 10 + 10;
        assertEquals(expectedFunds, gameController.getPlayer().getFunds(), "Player should have Ante and Play bets returned.");
    }

    @Test
    public void pairPlusWinTest() {
        ArrayList<Card> playerHand = new ArrayList<>();
        playerHand.add(new Card('H', 9));
        playerHand.add(new Card('D', 9));
        playerHand.add(new Card('S', 5));
        gameController.getPlayer().setHand(playerHand);

        ArrayList<Card> dealerHand = new ArrayList<>();
        dealerHand.add(new Card('C', 2));
        dealerHand.add(new Card('D', 3));
        dealerHand.add(new Card('H', 4));
        gameController.getDealer().setDealersHand(dealerHand);

        gameController.placeBets(10, 5);
        gameController.playerDecision(true);
        gameController.evaluateHands();

        int expectedFunds = 485;
        assertEquals(expectedFunds, gameController.getPlayer().getFunds(),
            "Player should receive Pair Plus payout and Ante/Play bets returned.");
    }

    @Test
    public void playerFoldsTest() {
        ArrayList<Card> playerHand = new ArrayList<>();
        playerHand.add(new Card('H', 7));
        playerHand.add(new Card('D', 5));
        playerHand.add(new Card('S', 3));
        gameController.getPlayer().setHand(playerHand);

        ArrayList<Card> dealerHand = new ArrayList<>();
        dealerHand.add(new Card('C', 14));
        dealerHand.add(new Card('D', 9));
        dealerHand.add(new Card('H', 8));
        gameController.getDealer().setDealersHand(dealerHand);

        gameController.placeBets(10, 5);
        gameController.dealCards();
        gameController.playerDecision(false);
        gameController.evaluateHands();

        int expectedFunds = 500 - 10 - 5;
        assertEquals(expectedFunds, gameController.getPlayer().getFunds(), "Player should lose Ante and Pair Plus bets when folding.");
    }

    @Test
    public void evaluateHandsDealerQualifiesAndPlayerWinsTest() {
        ArrayList<Card> dealerHand = new ArrayList<>();
        dealerHand.add(new Card('H', 12));
        dealerHand.add(new Card('D', 8));
        dealerHand.add(new Card('S', 7));
        gameController.getDealer().setDealersHand(dealerHand);

        ArrayList<Card> playerHand = new ArrayList<>();
        playerHand.add(new Card('C', 14));
        playerHand.add(new Card('D', 13));
        playerHand.add(new Card('H', 12));
        gameController.getPlayer().setHand(playerHand);

        gameController.placeBets(10, 0);
        gameController.playerDecision(true);
        gameController.evaluateHands();

        int expectedFunds = 500 - 20 + 40;
        assertEquals(expectedFunds, gameController.getPlayer().getFunds(),
            "Player should have won Ante and Play bets.");
    }
    
    @Test
    public void playerLosesAllBetsAgainstDealerStraightFlushTest() {
        ArrayList<Card> playerHand = new ArrayList<>();
        playerHand.add(new Card('H', 2));
        playerHand.add(new Card('D', 5));
        playerHand.add(new Card('S', 8));
        gameController.getPlayer().setHand(playerHand);

        ArrayList<Card> dealerHand = new ArrayList<>();
        dealerHand.add(new Card('C', 9));
        dealerHand.add(new Card('C', 10));
        dealerHand.add(new Card('C', 11));
        gameController.getDealer().setDealersHand(dealerHand);

        gameController.placeBets(10, 25);
        gameController.playerDecision(true);
        gameController.evaluateHands();

        int expectedFunds = 500 - 10 - 25 - 10;

        assertEquals(expectedFunds, gameController.getPlayer().getFunds(),
            "Player should lose Ante, Play, and Pair Plus bets when dealer has a straight flush.");
    }
    
    @Test
    public void pairPlusOnlyWinPlayerLosesToDealerTest() {
        ArrayList<Card> dealerHand = new ArrayList<>();
        dealerHand.add(new Card('H', 14));
        dealerHand.add(new Card('D', 13));
        dealerHand.add(new Card('S', 12));
        gameController.getDealer().setDealersHand(dealerHand);

        ArrayList<Card> playerHand = new ArrayList<>();
        playerHand.add(new Card('C', 9)); 
        playerHand.add(new Card('D', 9)); 
        playerHand.add(new Card('H', 5));
        gameController.getPlayer().setHand(playerHand);

        gameController.placeBets(10, 5);
        gameController.playerDecision(true);
        gameController.evaluateHands();

        int pairPlusWinnings = 5 * 1;
        int expectedFunds = 500 - 10 - 10 + pairPlusWinnings;
        assertEquals(expectedFunds, gameController.getPlayer().getFunds(),
            "Player should lose Ante/Play but win Pair Plus payout.");
    }

    @Test
    public void evaluateHandsDealerQualifiesAndTieTest() {
        ArrayList<Card> dealerHand = new ArrayList<>();
        dealerHand.add(new Card('H', 14));
        dealerHand.add(new Card('D', 13));
        dealerHand.add(new Card('S', 12));
        gameController.getDealer().setDealersHand(dealerHand);

        ArrayList<Card> playerHand = new ArrayList<>();
        playerHand.add(new Card('C', 14));
        playerHand.add(new Card('D', 13));
        playerHand.add(new Card('H', 12));
        gameController.getPlayer().setHand(playerHand);

        gameController.placeBets(10, 0);
        gameController.playerDecision(true);
        gameController.evaluateHands();

        int expectedFunds = 500;
        assertEquals(expectedFunds, gameController.getPlayer().getFunds(), "Player should have Ante and Play bets returned on a tie.");
    }
    
    @Test
    public void pairPlusBetLostTest() {
        ArrayList<Card> playerHand = new ArrayList<>();
        playerHand.add(new Card('H', 2));
        playerHand.add(new Card('D', 5));
        playerHand.add(new Card('S', 7));
        gameController.getPlayer().setHand(playerHand);
        
        ArrayList<Card> dealerHand = new ArrayList<>();
        dealerHand.add(new Card('C', 9));
        dealerHand.add(new Card('D', 10));
        dealerHand.add(new Card('H', 12));
        gameController.getDealer().setDealersHand(dealerHand);

        gameController.placeBets(10, 5);
        gameController.playerDecision(true);
        gameController.evaluateHands();

        int expectedFunds = 500 - 10 - 10 - 5;
        assertEquals(expectedFunds, gameController.getPlayer().getFunds(),
            "Player should lose Ante, Play, and Pair Plus bets when losing the Pair Plus bet.");
    }
    
    @Test
    public void playerLosesToDealerWithHighCardTest() {
        ArrayList<Card> dealerHand = new ArrayList<>();
        dealerHand.add(new Card('H', 14));
        dealerHand.add(new Card('D', 9));
        dealerHand.add(new Card('S', 7));
        gameController.getDealer().setDealersHand(dealerHand);

        ArrayList<Card> playerHand = new ArrayList<>();
        playerHand.add(new Card('C', 13));
        playerHand.add(new Card('D', 8));
        playerHand.add(new Card('H', 6));
        gameController.getPlayer().setHand(playerHand);

        gameController.placeBets(10, 0);
        gameController.playerDecision(true);
        gameController.evaluateHands();

        int expectedFunds = 500 - 10 - 10;
        assertEquals(expectedFunds, gameController.getPlayer().getFunds(),
            "Player should lose Ante and Play bets when dealer wins with higher high card.");
    }

    @Test
    public void insufficientFundsAfterRoundTest() {
        gameController.getPlayer().setFunds(3);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            gameController.placeBets(5, 0);
        });
        assertEquals("Insufficient funds for Ante bet.", exception.getMessage(), 
            "Should throw insufficient funds error for Ante bet.");
    }

    @Test
    public void gameStateAfterResetTest() {
        gameController.placeBets(10, 5);
        gameController.dealCards();
        gameController.playerDecision(true);
        gameController.evaluateHands();

        gameController.resetGame();

        assertNotNull(gameController.getPlayer(), "Player should be reinitialized.");
        assertEquals(500, gameController.getPlayer().getFunds(), "Player's funds should reset to initial amount.");
        assertEquals(0, gameController.getPlayer().getAnteBet(), "Ante bet should reset to 0.");
        assertEquals(0, gameController.getPlayer().getPlayBet(), "Play bet should reset to 0.");
        assertEquals(0, gameController.getPlayer().getPairPlusBet(), "Pair Plus bet should reset to 0.");
        assertTrue(gameController.getPlayer().getHand().isEmpty(), "Player's hand should be empty after reset.");
    }

    @Test
    public void gameResetTest() {
        gameController.placeBets(10, 5);
        gameController.dealCards();
        gameController.playerDecision(true);
        gameController.evaluateHands();

        gameController.resetGame();

        assertNotNull(gameController.getDealer(), "Dealer should be reinitialized.");
        assertNotNull(gameController.getPlayer(), "Player should be reinitialized.");
        assertEquals(500, gameController.getPlayer().getFunds(), "Player's funds should reset to initial amount.");
    }
}