import static org.junit.Assert.*;

import org.junit.Test;

import solitaire.Card;

public class TestCard {

    @Test
    public void test() {
        Card c1 = Card.flyWeightFactory(Card.Rank.ACE, Card.Suit.CLUBS);
        Card c2 = Card.flyWeightFactory(Card.Rank.ACE, Card.Suit.CLUBS);
        assertTrue(c1.hashCode() == c2.hashCode());
        assertEquals(c1.toString(), "ACE of CLUBS");
        assertFalse(c1.isVisible());
        c1.setVisibility(false);
        assertFalse(c1.isVisible());
        c1.setVisibility(true);
        assertTrue(c1.isVisible());
        assertTrue(c1.getRank() == Card.Rank.ACE);
        assertTrue(c1.getSuit() == Card.Suit.CLUBS);
        
    }
}
