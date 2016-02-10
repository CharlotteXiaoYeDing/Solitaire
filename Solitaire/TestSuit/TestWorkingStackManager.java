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
    Deck aDeck;

    @Before
    public void testConstructor() {
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
//                wst.print();
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
    public void testAddDraw() {
        for (Card aCard: aDeck)
        {
            aCard.setVisiblity(true);
        }

        //JACK DIAMONDS
        assertTrue(wst.canAdd(aDeck.peek(), (WorkingStackManager.Workingstack.StackFive)));
        wst.add(aDeck.draw(), (WorkingStackManager.Workingstack.StackFive));
        assertFalse(wst.canAdd(aDeck.peek(), (WorkingStackManager.Workingstack.StackTwo)));
        assertFalse(wst.canAdd(aDeck.peek(), (WorkingStackManager.Workingstack.StackSix)));
        //TEN
        aDeck.draw();
        aDeck.draw();
        aDeck.draw();
        //SEVEN
        assertFalse(wst.canAdd(aDeck.draw(), (WorkingStackManager.Workingstack.StackOne)));
        assertFalse(wst.isEmpty(WorkingStackManager.Workingstack.StackTwo));
        assertTrue(wst.canDraw((WorkingStackManager.Workingstack.StackTwo)));
        wst.draw((WorkingStackManager.Workingstack.StackTwo));
        assertTrue(wst.canDraw((WorkingStackManager.Workingstack.StackTwo)));
        wst.draw((WorkingStackManager.Workingstack.StackTwo));
        assertTrue(wst.isEmpty(WorkingStackManager.Workingstack.StackTwo));
        assertFalse(wst.canDraw((WorkingStackManager.Workingstack.StackTwo)));
        //SIX
        assertFalse(wst.canAdd(aDeck.draw(), (WorkingStackManager.Workingstack.StackTwo)));
        assertTrue(wst.canAdd(aDeck.peek(), WorkingStackManager.Workingstack.StackSix));
        //FIVE
        wst.add(aDeck.peek(), WorkingStackManager.Workingstack.StackSix);
        aDeck.draw();
        assertFalse(wst.canAdd(aDeck.draw(), (WorkingStackManager.Workingstack.StackFour)));
        aDeck.draw();
        aDeck.draw();
        aDeck.draw();
        assertTrue(wst.canAdd(aDeck.peek(), (WorkingStackManager.Workingstack.StackTwo)));
        wst.add(aDeck.draw(), (WorkingStackManager.Workingstack.StackTwo));
        wst.add(aDeck.draw(),  (WorkingStackManager.Workingstack.StackOne));
        wst.add(aDeck.draw(),  (WorkingStackManager.Workingstack.StackSeven));
        wst.add(aDeck.draw(),  (WorkingStackManager.Workingstack.StackFive));
        wst.print();
        assertTrue(wst.canDrawMultiple(Card.flyWeightFactory(Card.Rank.QUEEN, Card.Suit.DIAMONDS), (WorkingStackManager.Workingstack.StackSeven)));
        assertFalse(wst.canDrawMultiple(Card.flyWeightFactory(Card.Rank.QUEEN, Card.Suit.DIAMONDS), (WorkingStackManager.Workingstack.StackFive)));
        assertTrue(wst.canAdd(Card.flyWeightFactory(Card.Rank.QUEEN, Card.Suit.DIAMONDS), (WorkingStackManager.Workingstack.StackTwo)));
        System.out.println(wst.drawMultiple(Card.flyWeightFactory(Card.Rank.QUEEN, Card.Suit.DIAMONDS), (WorkingStackManager.Workingstack.StackSeven)).lastElement());
        wst.addMultiple(wst.drawMultiple(Card.flyWeightFactory(Card.Rank.QUEEN, Card.Suit.DIAMONDS), (WorkingStackManager.Workingstack.StackSeven)),(WorkingStackManager.Workingstack.StackTwo));
    }

}
