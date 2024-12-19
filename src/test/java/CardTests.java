import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class CardTest {
	@Test
	void firstCardConstructorTest() {
		Card card = new Card('S', 14);
		assertEquals('S', card.getSuit(), "Suit of card should be S");
		assertEquals(14, card.getValue(), "Value of card should be 14");
	}
	
	@Test
	void secondCardConstructorTest() {
		Card card = new Card('H', 7);
		assertEquals('H', card.getSuit(), "Suit of card should be H");
		assertEquals(7, card.getValue(), "Value of card should be 7");
	}
	
	@Test
	void firstToStringTest() {
		Card card = new Card('C', 13);
		assertEquals("KC", card.toString(), "toString() should return 'KC'");
	}
	
	@Test
	void secondToStringTest() {
		Card card = new Card('D', 2);
		assertEquals("2D", card.toString(), "toString() should return '2D'");
	}
}