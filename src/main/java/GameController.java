import java.util.ArrayList;

public class GameController {
	 private Dealer dealer;
	    private Player player;
	    private int netChange;
	    private String playerHandOutcome;
	    private String dealerHandOutcome;
	    private int fundsAtRoundStart;
	    private int gameResult;

    // Initializes game controller with dealer and player
    public GameController() {
        dealer = new Dealer();
        player = new Player();
        playerHandOutcome = "";
        dealerHandOutcome = "";
    }

    // Places bets, updating funds accordingly
    public void placeBets(int anteBet, int pairPlusBet) {
        fundsAtRoundStart = player.getFunds(); // Record funds before betting
        player.placeAnte(anteBet);
        if (pairPlusBet > 0) {
            player.placePairPlus(pairPlusBet);
        }
    }

    // Deals cards to player and dealer
    public void dealCards() {
        ArrayList<Card> playerHand = dealer.dealHand();
        ArrayList<Card> dealerHand = dealer.dealHand();

        player.setHand(playerHand);
        dealer.setDealersHand(dealerHand);
    }

    // Records player decision to play or fold
    public void playerDecision(boolean wantsToPlay) {
        player.decidePlay(wantsToPlay);
    }

    // Evaluates hands, calculates net change, and sets outcome
    public void evaluateHands() {
        playerHandOutcome = getHandRankName(ThreeCardLogic.evalHand(player.getHand()));
        dealerHandOutcome = getHandRankName(ThreeCardLogic.evalHand(dealer.getDealersHand()));
        gameResult = 0; // Reset gameResult

        if (player.hasFolded()) {
            handlePlayerFold();
        } else if (!dealerQualifies()) {
            handleDealerNotQualifying();
        } else {
            handleGameResult();
        }

        handlePairPlusWinnings();

        // Calculate net funds change
        netChange = player.getFunds() - fundsAtRoundStart;
    }

    // Handles scenario when player folds
    private void handlePlayerFold() {
        gameResult = -1;
    }

    // Handles scenario when dealer does not qualify
    private void handleDealerNotQualifying() {
        int anteBet = player.getAnteBet();
        int playBet = player.getPlayBet();
        player.setFunds(player.getFunds() + anteBet + playBet); // Return bets
        gameResult = 3;
    }

    // Determines game result based on hand comparisons
    private void handleGameResult() {
        int anteBet = player.getAnteBet();
        int playBet = player.getPlayBet();
        gameResult = ThreeCardLogic.compareHands(dealer.getDealersHand(), player.getHand());

        switch (gameResult) {
            case 2: // Player wins
                player.setFunds(player.getFunds() + (anteBet + playBet) * 2);
                break;
            case 1: // Dealer wins
                break; // Bets lost
            case 0: // Tie
                player.setFunds(player.getFunds() + anteBet + playBet);
                break;
        }
    }

    // Checks and applies Pair Plus winnings if eligible
    private void handlePairPlusWinnings() {
        int pairPlusBet = player.getPairPlusBet();
        if (pairPlusBet > 0 && !player.hasFolded()) {
            int pairPlusWinnings = ThreeCardLogic.evalPPWinnings(player.getHand(), pairPlusBet);
            if (pairPlusWinnings > 0) {
                player.setFunds(player.getFunds() + pairPlusWinnings + pairPlusBet);
            }
        }
    }

    // Checks if dealer qualifies with Queen high or better
    public boolean dealerQualifies() {
        ArrayList<Card> dealerHand = dealer.getDealersHand();
        int handRank = ThreeCardLogic.evalHand(dealerHand);

        // Qualifies with rank better than high card or with Queen high
        if (handRank <= 5 && handRank != 0) {
            return true;
        }
        int highestValue = dealerHand.stream().mapToInt(Card::getValue).max().orElse(0);
        return highestValue >= 12; // Queen or better
    }

    // Helper method to convert hand rank to a string outcome
    private String getHandRankName(int rank) {
        switch (rank) {
            case 1: return "Straight Flush";
            case 2: return "Three of a Kind";
            case 3: return "Straight";
            case 4: return "Flush";
            case 5: return "Pair";
            default: return "High Card";
        }
    }

    // Get outcome descriptions for player and dealer hands
    public String getPlayerHandOutcome() {
        if (player.hasFolded()) {
            return "Folded";
        } else if (gameResult == 3) {
            return "Dealer Did Not Qualify";
        } else {
            return getHandRankName(ThreeCardLogic.evalHand(player.getHand()));
        }
    }

    public String getDealerHandOutcome() {
        return dealerHandOutcome;
    }

    public int getNetChange() {
        return netChange;
    }

    public int getGameResult() {
        return gameResult;
    }

    public Player getPlayer() {
        return player;
    }

    public Dealer getDealer() {
        return dealer;
    }

    // Resets the player and dealer for the next round
    public void resetForNextRound() {
        player.resetForNextRound();
        dealer.clearHand();
    }
    
    // Resets the game state
    public void resetGame() {
        dealer = new Dealer();
        player = new Player();
        netChange = 0;
        gameResult = 0;
        playerHandOutcome = "";
        dealerHandOutcome = "";
        System.out.println("Game has been reset.");
    }
}
