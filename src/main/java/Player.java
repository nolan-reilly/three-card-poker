import java.util.ArrayList;

public class Player {
    private ArrayList<Card> hand;
    private int anteBet;
    private int playBet;
    private int pairPlusBet;
    private boolean folded;
    private int funds;
    private int fundsAtRoundStart;
    private int totalWinnings;
    private String playerHandOutcome;
    private int gameResult;

    // Constructor initializes player with default values and starting funds
    public Player() {
        hand = new ArrayList<>();
        anteBet = 0;
        playBet = 0;
        pairPlusBet = 0;
        folded = false;
        funds = 500;
    }

    // Places an Ante bet if within allowed range and deducts from funds
    public void placeAnte(int bet) {
        if (bet < 5 || bet > 25) {
            throw new IllegalArgumentException("Ante bet must be between $5 and $25.");
        }
        if (bet > funds) {
            throw new IllegalArgumentException("Insufficient funds for Ante bet.");
        }
        anteBet = bet;
        funds -= bet;
    }

    // Places a Pair Plus bet if within allowed range and deducts from funds
    public void placePairPlus(int bet) {
        if (bet < 5 || bet > 25) {
            throw new IllegalArgumentException("Pair Plus bet must be between $5 and $25.");
        }
        if (bet > funds) {
            throw new IllegalArgumentException("Insufficient funds for the Pair Plus bet.");
        }
        pairPlusBet = bet;
        funds -= bet;
    }
    
    // Places a Play bet equal to the Ante bet if player funds allow it
    private void placePlayBet() {
        playBet = anteBet;
        if (playBet > funds) {
            throw new IllegalArgumentException("Insufficient funds for Play bet.");
        }
        funds -= playBet;
    }

    // Decides to either play or fold based on willPlay
    public void decidePlay(boolean willPlay) {
        if (willPlay) {
            placePlayBet();
        } else {
            fold();
        }
    }

    // Marks the player as folded
    private void fold() {
        folded = true;
    }

    // Getters and setters for player properties
    
    public ArrayList<Card> getHand() {
        return new ArrayList<>(this.hand);
    }

    public void setHand(ArrayList<Card> hand) {
        this.hand = new ArrayList<>(hand);
    }

    public int getAnteBet() {
        return anteBet;
    }

    public int getPlayBet() {
        return playBet;
    }

    public int getPairPlusBet() {
        return pairPlusBet;
    }

    public boolean hasFolded() {
        return folded;
    }

    public int getFunds() {
        return funds;
    }

    public void setFunds(int amount) {
        funds = amount;
    }
    
    public int getFundsAtRoundStart() {
        return fundsAtRoundStart;
    }

    public void setFundsAtRoundStart(int fundsAtRoundStart) {
        this.fundsAtRoundStart = fundsAtRoundStart;
    }

    public int getNetChange() {
        return totalWinnings;
    }

    public void setNetChange(int netChange) {
        this.totalWinnings = netChange;
    }

    public String getPlayerHandOutcome() {
        return playerHandOutcome;
    }

    public void setPlayerHandOutcome(String playerHandOutcome) {
        this.playerHandOutcome = playerHandOutcome;
    }

    public int getGameResult() {
        return gameResult;
    }

    public void setGameResult(int gameResult) {
        this.gameResult = gameResult;
    }

    // Resets player state for the next round
    public void resetForNextRound() {
        anteBet = 0;
        playBet = 0;
        pairPlusBet = 0;
        hand.clear();
        folded = false;
    }
}
