import java.util.ArrayList;
import java.util.Collections;

public class ThreeCardLogic {

    // Evaluates hand ranking
    public static int evalHand(ArrayList<Card> hand) {
        boolean isFlush = isFlush(hand);
        boolean isStraight = isStraight(hand);
        boolean isThreeOfAKind = isThreeOfAKind(hand);
        boolean isPair = isPair(hand);

        if (isFlush && isStraight) {
        	return 1;
        } else if (isThreeOfAKind) {
        	return 2;
        } else if (isStraight) {
        	return 3;
        } else if (isFlush) {
        	return 4;
        } else if (isPair) {
        	return 5;
        } else {
        	return 0; // High Card
        }
    }

    // Calculates Pair Plus winnings based on hand rank
    public static int evalPPWinnings(ArrayList<Card> hand, int bet) {
        switch (evalHand(hand)) {
            case 1: return bet * 40;
            case 2: return bet * 30;
            case 3: return bet * 6;
            case 4: return bet * 3;
            case 5: return bet * 1;
            default: return 0;
        }
    }

    // Compares dealer's and player's hands
    public static int compareHands(ArrayList<Card> dealer, ArrayList<Card> player) {
    	// Invert rankings
    	int dealerScore = (evalHand(dealer) == 0) ? 0 : 6 - evalHand(dealer);
    	int playerScore = (evalHand(player) == 0) ? 0 : 6 - evalHand(player);

        if (playerScore > dealerScore) {
        	return 2;
        } else if (dealerScore > playerScore) {
        	return 1;
        } else {
        	return compareHighCards(dealer, player); // If tied, compare high cards
        }
    }

    // Helper to compare high cards when ranks are tied
    private static int compareHighCards(ArrayList<Card> dealer, ArrayList<Card> player) {
        ArrayList<Integer> dealerValues = new ArrayList<>();
        ArrayList<Integer> playerValues = new ArrayList<>();
        
        // Collect card values from dealer and player hands
        for (Card card : dealer) {
            dealerValues.add(card.getValue());
        }

        for (Card card : player) {
            playerValues.add(card.getValue());
        }

        dealerValues.sort(Collections.reverseOrder());
        playerValues.sort(Collections.reverseOrder());

        // Compare each card value from highest to lowest and return high card winner
        for (int i = 0; i < 3; i++) {
            if (playerValues.get(i) > dealerValues.get(i)) {
            	return 2;
            } else if (dealerValues.get(i) > playerValues.get(i)) {
            	return 1;
            }
        }
        
        return 0; // tie if all values are equal
    }

    // Returns true if all cards have the same suit
    private static boolean isFlush(ArrayList<Card> hand) {
        char suit = hand.get(0).getSuit();
        for (Card card : hand) {
            if (card.getSuit() != suit) return false;
        }
        return true;
    }

    // Returns true if cards form a consecutive sequence
    private static boolean isStraight(ArrayList<Card> hand) {
        ArrayList<Integer> values = new ArrayList<>();
        
        // Collect card values from hand
        for (Card card : hand) {
        	values.add(card.getValue());
        }
        
        Collections.sort(values);

        return (values.get(1) == values.get(0) + 1 && values.get(2) == values.get(1) + 1) ||
               (values.contains(14) && values.contains(2) && values.contains(3)) ||
               (values.contains(12) && values.contains(13) && values.contains(14));
    }

    // Returns true if all cards have the same value
    private static boolean isThreeOfAKind(ArrayList<Card> hand) {
        int value = hand.get(0).getValue();
        return hand.get(1).getValue() == value && hand.get(2).getValue() == value;
    }

    // Returns true if any two cards have the same value
    private static boolean isPair(ArrayList<Card> hand) {
        int val0 = hand.get(0).getValue();
        int val1 = hand.get(1).getValue();
        int val2 = hand.get(2).getValue();

        return val0 == val1 || val1 == val2 || val0 == val2;
    }
}