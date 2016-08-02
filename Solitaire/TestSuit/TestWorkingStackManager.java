import static org.junit.Assert.*;

import java.lang.reflect.Field;
import java.util.Stack;

import org.junit.Before;
import org.junit.Test;

import solitaire.GameModel.GameModel;
import solitaire.internal.Card;
import solitaire.internal.Deck;
import solitaire.internal.WorkingStack;
import solitaire.internal.WorkingStackManager;
import solitaire.internal.WorkingStackManager.Workingstack;

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
                Field workingStackManager = GameModel.class.getDeclaredField("aWorkingStack");
                workingStackManager.setAccessible(true);
                wst = (WorkingStackManager) workingStackManager.get(GameModel.getInstance());
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
    	Card[] stackOne = (wst.getVisibleWorkingStack(WorkingStackManager.Workingstack.StackOne));
    	Card[] stackTwo = (wst.getVisibleWorkingStack(WorkingStackManager.Workingstack.StackTwo));
    	Card[] stackThree = (wst.getVisibleWorkingStack(WorkingStackManager.Workingstack.StackThree));
    	Card[] stackFour = (wst.getVisibleWorkingStack(WorkingStackManager.Workingstack.StackFour));
    	Card[] stackFive = (wst.getVisibleWorkingStack(WorkingStackManager.Workingstack.StackFive));
    	Card[] stackSix = (wst.getVisibleWorkingStack(WorkingStackManager.Workingstack.StackSix));
    	Card[] stackSeven = (wst.getVisibleWorkingStack(WorkingStackManager.Workingstack.StackSeven));
        assertEquals(Card.flyWeightFactory(Card.Rank.KING, Card.Suit.HEARTS),
                (stackOne[0]));
        assertEquals(Card.flyWeightFactory(Card.Rank.JACK, Card.Suit.HEARTS),
                (stackTwo[0]));
        assertEquals(Card.flyWeightFactory(Card.Rank.EIGHT, Card.Suit.HEARTS),
                (stackThree[0]));
        assertEquals(Card.flyWeightFactory(Card.Rank.FOUR, Card.Suit.HEARTS),
                (stackFour[0]));
        assertEquals(Card.flyWeightFactory(Card.Rank.QUEEN, Card.Suit.SPADES),
                (stackFive[0]));
        assertEquals(Card.flyWeightFactory(Card.Rank.SIX, Card.Suit.SPADES),
                (stackSix[0]));
        assertEquals(Card.flyWeightFactory(Card.Rank.QUEEN, Card.Suit.DIAMONDS),
                (stackSeven[0]));
    }
//
//    @Test
//    public void testAddDraw() { 
//        Card c1 = aDeck.draw();
//        assertTrue(wst.canAdd(c1, (WorkingStackManager.Workingstack.StackFive)));
//        wst.add(c1, (WorkingStackManager.Workingstack.StackFive));
//        assertFalse(aDeck.peek().equals(c1));
//        assertEquals(c1, wst.viewWorkingStack(Workingstack.StackFive).peek());
//        assertFalse(wst.canAdd(c1, (WorkingStackManager.Workingstack.StackSeven)));
//        assertFalse(wst.canAdd(aDeck.peek(), (WorkingStackManager.Workingstack.StackSix)));
//        assertFalse(wst.canAdd(aDeck.draw(), (WorkingStackManager.Workingstack.StackOne)));
//        assertFalse(wst.canAdd(aDeck.draw(), (WorkingStackManager.Workingstack.StackTwo)));
//        assertFalse(wst.canAdd(aDeck.draw(), (WorkingStackManager.Workingstack.StackThree)));
//        assertFalse(wst.canAdd(aDeck.draw(), (WorkingStackManager.Workingstack.StackFour)));
//        Card c2 = wst.viewWorkingStack(WorkingStackManager.Workingstack.StackTwo).peek();
//        assertTrue(wst.canDraw((WorkingStackManager.Workingstack.StackTwo)));
//        wst.draw((WorkingStackManager.Workingstack.StackTwo));
//        assertNotEquals(c2, wst.viewWorkingStack(WorkingStackManager.Workingstack.StackTwo).peek());
//        assertTrue(wst.canDraw((WorkingStackManager.Workingstack.StackTwo)));
//        wst.draw((WorkingStackManager.Workingstack.StackTwo));
//        assertFalse(wst.canDraw((WorkingStackManager.Workingstack.StackTwo)));
//        assertFalse(wst.canAdd(aDeck.draw(), (WorkingStackManager.Workingstack.StackTwo)));
//        assertTrue(wst.canAdd(aDeck.peek(), WorkingStackManager.Workingstack.StackSix));
//        wst.add(aDeck.draw(), WorkingStackManager.Workingstack.StackSix);
//        assertFalse(wst.canAdd(aDeck.draw(), (WorkingStackManager.Workingstack.StackFour)));
//        assertFalse(wst.canAdd(aDeck.draw(), (WorkingStackManager.Workingstack.StackOne)));
//        assertFalse(wst.canAdd(aDeck.draw(), (WorkingStackManager.Workingstack.StackTwo)));
//        assertFalse(wst.canAdd(aDeck.draw(), (WorkingStackManager.Workingstack.StackThree)));
//        assertTrue(wst.canAdd(aDeck.peek(), (WorkingStackManager.Workingstack.StackTwo)));
//        wst.add(aDeck.draw(), (WorkingStackManager.Workingstack.StackTwo));
//        wst.add(aDeck.draw(), (WorkingStackManager.Workingstack.StackOne));
//        wst.add(aDeck.draw(), (WorkingStackManager.Workingstack.StackSeven));
//        wst.add(aDeck.draw(), (WorkingStackManager.Workingstack.StackFive));
//        System.out.print(wst.toString());
//        System.out.println(aDeck.peek());
//        Card c3 = wst.viewWorkingStack(WorkingStackManager.Workingstack.StackSeven).peek();
//        assertTrue(wst.canDrawMultiple(Card.flyWeightFactory(Card.Rank.QUEEN, Card.Suit.DIAMONDS),
//                (WorkingStackManager.Workingstack.StackSeven)));
//        assertFalse(wst.canDrawMultiple(Card.flyWeightFactory(Card.Rank.QUEEN, Card.Suit.DIAMONDS),
//                (WorkingStackManager.Workingstack.StackFive)));
//        assertTrue(wst.canAdd(Card.flyWeightFactory(Card.Rank.QUEEN, Card.Suit.DIAMONDS),
//                (WorkingStackManager.Workingstack.StackTwo)));
//        wst.addMultiple(wst.drawMultiple(Card.flyWeightFactory(Card.Rank.QUEEN, Card.Suit.DIAMONDS),
//                (WorkingStackManager.Workingstack.StackSeven)), (WorkingStackManager.Workingstack.StackTwo));
//        assertNotEquals(Card.flyWeightFactory(Card.Rank.QUEEN, Card.Suit.DIAMONDS),
//                wst.viewWorkingStack(WorkingStackManager.Workingstack.StackSeven).peek());
//        assertEquals(c3, wst.viewWorkingStack(WorkingStackManager.Workingstack.StackTwo).peek());
//    }

}
