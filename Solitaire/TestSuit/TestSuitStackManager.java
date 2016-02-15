import static org.junit.Assert.*;

import java.lang.reflect.Field;
import java.util.Map;

import org.junit.Test;

import solitaire.Card;
import solitaire.SuitStackManager;
import solitaire.Card.Suit;

public class TestSuitStackManager {

    SuitStackManager aManager = new SuitStackManager();
    private final Card c1 = Card.flyWeightFactory(Card.Rank.FIVE, Card.Suit.DIAMONDS);
    private final Card c2 = Card.flyWeightFactory(Card.Rank.ACE, Card.Suit.SPADES);
    private final Card c3 = Card.flyWeightFactory(Card.Rank.ACE, Card.Suit.CLUBS);
    private final Card c4 = Card.flyWeightFactory(Card.Rank.ACE, Card.Suit.DIAMONDS);
    private final Card c5 = Card.flyWeightFactory(Card.Rank.ACE, Card.Suit.HEARTS);
    private final Card c6 = Card.flyWeightFactory(Card.Rank.TWO, Card.Suit.SPADES);
                    
    
    
    @Test
    public void testAdd() {
        c1.setVisibility(true);
        c2.setVisibility(true);
        c3.setVisibility(true);
        c4.setVisibility(true);
        c5.setVisibility(true);
        c6.setVisibility(true);
        assertFalse(aManager.canAdd(c1));
        assertTrue(aManager.canAdd(c2));
        assertTrue(aManager.canAdd(c3));
        assertTrue(aManager.canAdd(c4));
        assertTrue(aManager.canAdd(c5));
        aManager.add(c2);
        assertFalse(aManager.canAdd(c2));
        assertTrue(aManager.canAdd(c6));
        aManager.add(c6);
        aManager.add(c3);
        assertEquals(aManager.viewSuitStack(SuitStackManager.SuitStack.StackClubs), c3);
        assertEquals(aManager.viewSuitStack(SuitStackManager.SuitStack.StackDiamonds), null);
        assertEquals(aManager.viewSuitStack(SuitStackManager.SuitStack.StackSpades), c6);
        assertEquals(aManager.getScore(), 3);
        assertTrue(aManager.canDraw(SuitStackManager.SuitStack.StackSpades));
        assertEquals((aManager.draw(SuitStackManager.SuitStack.StackSpades)), c6);
        assertTrue(aManager.canDraw(SuitStackManager.SuitStack.StackClubs));
        assertEquals((aManager.draw(SuitStackManager.SuitStack.StackClubs)), c3);
        assertFalse(aManager.canDraw(SuitStackManager.SuitStack.StackDiamonds));
        
    }

}
