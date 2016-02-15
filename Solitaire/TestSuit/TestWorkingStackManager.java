import static org.junit.Assert.*;

import java.lang.reflect.Field;
import java.util.Stack;

import org.junit.Before;
import org.junit.Test;

import solitaire.Card;
import solitaire.Deck;
import solitaire.DeckOrdered;
import solitaire.GameModel;
import solitaire.WorkingStack;
import solitaire.WorkingStackManager;
import solitaire.WorkingStackManager.Workingstack;

public class TestWorkingStackManager {
    WorkingStackManager wst;
    Deck aDeck;

    @Before
    public void setUp() {
        try {
            Field deck = GameModel.class.getDeclaredField("aDeck");
            deck.setAccessible(true);
            try {
                deck.set(GameModel.getInstance(), new DeckOrdered());
                GameModel.getInstance().reset();
                aDeck = (Deck) deck.get(GameModel.getInstance());
                Field workingstack = GameModel.class.getDeclaredField("aWorkingStack");
                workingstack.setAccessible(true);
                wst = (WorkingStackManager) workingstack.get(GameModel.getInstance());
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }
    
    @Test
    public void testConstructor()
    {
        assertEquals(Card.flyWeightFactory(Card.Rank.KING, Card.Suit.HEARTS),
                (wst.viewWorkingStack(WorkingStackManager.Workingstack.StackOne)).pop());
        assertEquals(Card.flyWeightFactory(Card.Rank.JACK, Card.Suit.HEARTS),
                (wst.viewWorkingStack(WorkingStackManager.Workingstack.StackTwo)).pop());
        assertEquals(Card.flyWeightFactory(Card.Rank.EIGHT, Card.Suit.HEARTS),
                (wst.viewWorkingStack(WorkingStackManager.Workingstack.StackThree)).pop());
        assertEquals(Card.flyWeightFactory(Card.Rank.FOUR, Card.Suit.HEARTS),
                (wst.viewWorkingStack(WorkingStackManager.Workingstack.StackFour)).pop());
        assertEquals(Card.flyWeightFactory(Card.Rank.QUEEN, Card.Suit.SPADES),
                (wst.viewWorkingStack(WorkingStackManager.Workingstack.StackFive)).pop());
        assertEquals(Card.flyWeightFactory(Card.Rank.SIX, Card.Suit.SPADES),
                (wst.viewWorkingStack(WorkingStackManager.Workingstack.StackSix)).pop());
        assertEquals(Card.flyWeightFactory(Card.Rank.QUEEN, Card.Suit.DIAMONDS),
                (wst.viewWorkingStack(WorkingStackManager.Workingstack.StackSeven)).pop());
    }

    @Test
    public void testAddDraw() {
        for (Card aCard : aDeck) {
            aCard.setVisibility(true);
        }   
        Card c1 = aDeck.draw();
        assertTrue(wst.canAdd(c1, (WorkingStackManager.Workingstack.StackFive)));
        wst.add(c1, (WorkingStackManager.Workingstack.StackFive));
        assertFalse(aDeck.peek().equals(c1));
        assertEquals(c1, wst.viewWorkingStack(Workingstack.StackFive).peek());
        assertFalse(wst.canAdd(c1, (WorkingStackManager.Workingstack.StackSeven)));
        assertFalse(wst.canAdd(aDeck.peek(), (WorkingStackManager.Workingstack.StackSix)));
        assertFalse(wst.canAdd(aDeck.draw(), (WorkingStackManager.Workingstack.StackOne)));
        assertFalse(wst.canAdd(aDeck.draw(), (WorkingStackManager.Workingstack.StackTwo)));
        assertFalse(wst.canAdd(aDeck.draw(), (WorkingStackManager.Workingstack.StackThree)));
        assertFalse(wst.canAdd(aDeck.draw(), (WorkingStackManager.Workingstack.StackFour)));
        Card c2 = wst.viewWorkingStack(WorkingStackManager.Workingstack.StackTwo).peek();
        assertTrue(wst.canDraw((WorkingStackManager.Workingstack.StackTwo)));
        assertTrue(wst.viewWorkingStack(Workingstack.StackTwo).peek().isVisible());
        wst.draw((WorkingStackManager.Workingstack.StackTwo));
        assertNotEquals(c2, wst.viewWorkingStack(WorkingStackManager.Workingstack.StackTwo).peek());
        assertTrue(wst.canDraw((WorkingStackManager.Workingstack.StackTwo)));
        wst.draw((WorkingStackManager.Workingstack.StackTwo));
        assertFalse(wst.canDraw((WorkingStackManager.Workingstack.StackTwo)));
        assertFalse(wst.canAdd(aDeck.draw(), (WorkingStackManager.Workingstack.StackTwo)));
        assertTrue(wst.canAdd(aDeck.peek(), WorkingStackManager.Workingstack.StackSix));
        wst.add(aDeck.draw(), WorkingStackManager.Workingstack.StackSix);
        assertFalse(wst.canAdd(aDeck.draw(), (WorkingStackManager.Workingstack.StackFour)));
        assertFalse(wst.canAdd(aDeck.draw(), (WorkingStackManager.Workingstack.StackOne)));
        assertFalse(wst.canAdd(aDeck.draw(), (WorkingStackManager.Workingstack.StackTwo)));
        assertFalse(wst.canAdd(aDeck.draw(), (WorkingStackManager.Workingstack.StackThree)));
        assertTrue(wst.canAdd(aDeck.peek(), (WorkingStackManager.Workingstack.StackTwo)));
        wst.add(aDeck.draw(), (WorkingStackManager.Workingstack.StackTwo));
        wst.add(aDeck.draw(), (WorkingStackManager.Workingstack.StackOne));
        wst.add(aDeck.draw(), (WorkingStackManager.Workingstack.StackSeven));
        wst.add(aDeck.draw(), (WorkingStackManager.Workingstack.StackFive));
        System.out.print(wst.toString());
        System.out.println(aDeck.peek());
        Card c3 = wst.viewWorkingStack(WorkingStackManager.Workingstack.StackSeven).peek();
        assertTrue(wst.canDrawMultiple(Card.flyWeightFactory(Card.Rank.QUEEN, Card.Suit.DIAMONDS),
                (WorkingStackManager.Workingstack.StackSeven)));
        assertFalse(wst.canDrawMultiple(Card.flyWeightFactory(Card.Rank.QUEEN, Card.Suit.DIAMONDS),
                (WorkingStackManager.Workingstack.StackFive)));
        assertTrue(wst.canAdd(Card.flyWeightFactory(Card.Rank.QUEEN, Card.Suit.DIAMONDS),
                (WorkingStackManager.Workingstack.StackTwo)));
        wst.addMultiple(wst.drawMultiple(Card.flyWeightFactory(Card.Rank.QUEEN, Card.Suit.DIAMONDS),
                (WorkingStackManager.Workingstack.StackSeven)), (WorkingStackManager.Workingstack.StackTwo));
        assertNotEquals(Card.flyWeightFactory(Card.Rank.QUEEN, Card.Suit.DIAMONDS),
                wst.viewWorkingStack(WorkingStackManager.Workingstack.StackSeven).peek());
        assertEquals(c3, wst.viewWorkingStack(WorkingStackManager.Workingstack.StackTwo).peek());
    }

}
