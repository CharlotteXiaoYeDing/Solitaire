import static org.junit.Assert.*;

import org.junit.Test;

import solitaire.Card;
import solitaire.Deck;

public class TestDeck {

    @Test
    public void test() {
        Deck aDeck = new Deck();
        aDeck.shuffle();
        assertFalse(aDeck.isEmpty());
        assertFalse(aDeck.draw() == Card.flyWeightFactory(Card.Rank.ACE, Card.Suit.CLUBS));
        assertFalse(aDeck.draw() == Card.flyWeightFactory(Card.Rank.TWO, Card.Suit.CLUBS));
        assertFalse(aDeck.draw() == Card.flyWeightFactory(Card.Rank.THREE, Card.Suit.CLUBS));
        for (int i = 0; i < 49; i++) {
            aDeck.draw();
        }
        assertTrue(aDeck.isEmpty());
    }

}
