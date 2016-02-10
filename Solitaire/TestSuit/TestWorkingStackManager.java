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

public class TestWorkingStackManager {
    WorkingStackManager wst;

    @Before
    public void test() {
        try {
            Field deck = GameModel.class.getDeclaredField("aDeck");
            deck.setAccessible(true);
            try {
                deck.set(GameModel.getInstance(), new DeckOrdered());
                GameModel.getInstance().reset();
                Field workingstack = GameModel.class.getDeclaredField("aWorkingStack");
                workingstack.setAccessible(true);
                wst = (WorkingStackManager) workingstack.get(GameModel.getInstance());
                wst.print();
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
    public void testAddDraw(){
        Card c1 =  Card.flyWeightFactory(Card.Rank.QUEEN, Card.Suit.CLUBS);
        Card c2 = Card.flyWeightFactory(Card.Rank.QUEEN, Card.Suit.SPADES);
        c1.setVisiblity(true);
        c2.setVisiblity(true);
        assertTrue(wst.canAdd(c1,
                (WorkingStackManager.Workingstack.StackOne)));
        assertTrue(wst.canAdd(c2,
                (WorkingStackManager.Workingstack.StackOne)));
        assertFalse(wst.canAdd(Card.flyWeightFactory(Card.Rank.QUEEN, Card.Suit.HEARTS),
                (WorkingStackManager.Workingstack.StackOne)));
        assertFalse(wst.canAdd(Card.flyWeightFactory(Card.Rank.QUEEN, Card.Suit.DIAMONDS),
                (WorkingStackManager.Workingstack.StackOne)));
        wst.add(Card.flyWeightFactory(Card.Rank.QUEEN, Card.Suit.CLUBS), (WorkingStackManager.Workingstack.StackOne));
        assertFalse(wst.isEmpty(WorkingStackManager.Workingstack.StackTwo));
        assertTrue(wst.canDraw((WorkingStackManager.Workingstack.StackTwo)));
        wst.draw((WorkingStackManager.Workingstack.StackTwo));
        assertTrue(wst.canDraw((WorkingStackManager.Workingstack.StackTwo)));
        wst.draw((WorkingStackManager.Workingstack.StackTwo));
        assertTrue(wst.isEmpty(WorkingStackManager.Workingstack.StackTwo));
        assertFalse(wst.canDraw((WorkingStackManager.Workingstack.StackTwo)));
        assertFalse(wst.canAdd(Card.flyWeightFactory(Card.Rank.QUEEN, Card.Suit.HEARTS),
                (WorkingStackManager.Workingstack.StackTwo)));
        assertTrue(wst.canAdd(Card.flyWeightFactory(Card.Rank.KING, Card.Suit.CLUBS),
                (WorkingStackManager.Workingstack.StackTwo)));
        assertTrue(wst.canAdd(Card.flyWeightFactory(Card.Rank.KING, Card.Suit.HEARTS),
                (WorkingStackManager.Workingstack.StackTwo)));
    }

}
