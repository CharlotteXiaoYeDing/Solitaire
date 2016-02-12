import static org.junit.Assert.*;

import java.lang.reflect.Field;
import java.util.Map;

import org.junit.Test;

import solitaire.Card;
import solitaire.SuitStackManager;
import solitaire.Card.Suit;

public class TestSuitStackManager {

    @Test
    public void testAdd() {
        SuitStackManager aManager = new SuitStackManager();
        assertFalse(aManager.canAdd(Card.flyWeightFactory(Card.Rank.FIVE, Card.Suit.DIAMONDS)));
        assertTrue(aManager.canAdd(Card.flyWeightFactory(Card.Rank.ACE, Card.Suit.SPADES)));
        assertTrue(aManager.canAdd(Card.flyWeightFactory(Card.Rank.ACE, Card.Suit.CLUBS)));
        assertTrue(aManager.canAdd(Card.flyWeightFactory(Card.Rank.ACE, Card.Suit.DIAMONDS)));
        assertTrue(aManager.canAdd(Card.flyWeightFactory(Card.Rank.ACE, Card.Suit.HEARTS)));
        aManager.add(Card.flyWeightFactory(Card.Rank.ACE, Card.Suit.SPADES));
        assertFalse(aManager.canAdd(Card.flyWeightFactory(Card.Rank.ACE, Card.Suit.SPADES)));
        assertTrue(aManager.canAdd(Card.flyWeightFactory(Card.Rank.TWO, Card.Suit.SPADES)));
        aManager.add(Card.flyWeightFactory(Card.Rank.TWO, Card.Suit.SPADES));
        aManager.add(Card.flyWeightFactory(Card.Rank.ACE, Card.Suit.CLUBS));
        assertEquals(aManager.viewSuitStack(SuitStackManager.SuitStack.StackClubs), Card.flyWeightFactory(Card.Rank.ACE, Card.Suit.CLUBS));
        assertEquals(aManager.viewSuitStack(SuitStackManager.SuitStack.StackDiamonds), null);
        assertEquals(aManager.viewSuitStack(SuitStackManager.SuitStack.StackSpades), Card.flyWeightFactory(Card.Rank.TWO, Card.Suit.SPADES));
    }

    @Test
    public void testDraw() {
        SuitStackManager aManager = new SuitStackManager();
        aManager.add(Card.flyWeightFactory(Card.Rank.ACE, Card.Suit.SPADES));
        aManager.add(Card.flyWeightFactory(Card.Rank.TWO, Card.Suit.SPADES));
        aManager.add(Card.flyWeightFactory(Card.Rank.ACE, Card.Suit.CLUBS));
        assertTrue(aManager.canDraw(SuitStackManager.SuitStack.StackSpades));
        assertEquals((aManager.draw(SuitStackManager.SuitStack.StackSpades)), Card.flyWeightFactory(Card.Rank.TWO, Card.Suit.SPADES));
        assertTrue(aManager.canDraw(SuitStackManager.SuitStack.StackClubs));
        assertEquals((aManager.draw(SuitStackManager.SuitStack.StackClubs)), Card.flyWeightFactory(Card.Rank.ACE, Card.Suit.CLUBS));
        assertFalse(aManager.canDraw(SuitStackManager.SuitStack.StackDiamonds));
    }

}
