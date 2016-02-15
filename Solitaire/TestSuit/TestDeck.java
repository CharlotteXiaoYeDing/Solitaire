import static org.junit.Assert.*;

import org.junit.Test;

import solitaire.Card;
import solitaire.Deck;

public class TestDeck {

    @Test
    public void test() {
        Deck aDeck = new Deck();
        aDeck.reset();
        assertFalse(aDeck.isEmpty());
        assertTrue(aDeck.draw() == Card.flyWeightFactory(Card.Rank.KING, Card.Suit.HEARTS));
        assertTrue(aDeck.peek() == Card.flyWeightFactory(Card.Rank.QUEEN, Card.Suit.HEARTS));
        aDeck.add(Card.flyWeightFactory(Card.Rank.KING, Card.Suit.HEARTS));
        assertEquals(aDeck.peek(), Card.flyWeightFactory(Card.Rank.KING, Card.Suit.HEARTS));
    }

}
