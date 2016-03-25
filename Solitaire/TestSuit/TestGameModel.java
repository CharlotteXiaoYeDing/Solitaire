import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Stack;

import org.junit.Before;
import org.junit.Test;

import solitaire.Card;
import solitaire.Deck;
import solitaire.DeckOrdered;
import solitaire.GameModel;
import solitaire.StrategyOne;
import solitaire.SuitStackManager;
import solitaire.SuitStackManager.SuitStack;
import solitaire.WorkingStackManager;
import solitaire.WorkingStackManager.Workingstack;

public class TestGameModel {

    static GameModel aGameModel;
    static Deck aDeck;
    static SuitStackManager aSuitStackManager;
    static WorkingStackManager aWorkingStackManager;
    static Stack<Card> aDiscard;

    @SuppressWarnings("unchecked")
    @Before
    public void setUp() throws Exception {
        aGameModel = GameModel.getInstance();
        Field fDeck = GameModel.class.getDeclaredField("aDeck");
        fDeck.setAccessible(true);
        fDeck.set(aGameModel, new DeckOrdered());
        aGameModel.reset();
        aDeck = (Deck) fDeck.get(aGameModel);
        Field fSuitStackManager = GameModel.class.getDeclaredField("aSuitStackManager");
        fSuitStackManager.setAccessible(true);
        aSuitStackManager = (SuitStackManager) fSuitStackManager.get(aGameModel);
        Field fWorkingStackManager = GameModel.class.getDeclaredField("aWorkingStack");
        fWorkingStackManager.setAccessible(true);
        aWorkingStackManager = (WorkingStackManager) fWorkingStackManager.get(aGameModel);
        Field fDiscard = GameModel.class.getDeclaredField("aDiscard");
        fDiscard.setAccessible(true);
        aDiscard = (Stack<Card>) fDiscard.get(aGameModel);
        assertFalse(aGameModel.isDeckEmpty());
        assertTrue(aGameModel.isDiscardEmpty());
        assertNotNull(aWorkingStackManager);
        assertNotNull(aSuitStackManager);
    }

    @Test
    public void testDiscard() {
        aDeck.reset();
        aDeck.shuffle();
        while (!aGameModel.isDeckEmpty()) {
            Card c = aDeck.peek();
            aGameModel.discard();
            if (!aGameModel.isDeckEmpty()) {
                assertNotEquals(c, aDeck.peek());
            }
            assertEquals(c, aDiscard.peek());
        }
        assertTrue(aGameModel.isDeckEmpty());
        assertFalse(aGameModel.isDiscardEmpty());

    }

    @Test
    public void testUndoDiscard() {
        if (aGameModel.isDeckEmpty() != true) {
            aGameModel.discard();
        }
        Card c = aDiscard.peek();
        aGameModel.undoDiscard();
        if (!aGameModel.isDiscardEmpty()) {
            assertNotEquals(c, aDiscard.peek());
        }
        assertEquals(c, aDeck.peek());
    }

    @Test
    public void testMove() {
        aDeck.reset();
        for (int i = 0; i < 28; i++) {
            aDeck.draw();
        }
        aGameModel.discard();
        assertTrue(aGameModel.canMoveFromDiscardtoWorkingStack(Workingstack.StackFive));
        aGameModel.moveFromDiscardtoWorkingStack(Workingstack.StackFive);
        aGameModel.discard();
        assertFalse(aGameModel.canMoveFromDiscardtoSuitStack());
        assertFalse(aGameModel.canMoveFromDiscardtoWorkingStack(Workingstack.StackTwo));
        aGameModel.discard();
        aGameModel.discard();
        aGameModel.discard();
        assertFalse(aGameModel.canMoveFromDiscardtoWorkingStack(Workingstack.StackThree));
        aGameModel.discard();
        aGameModel.discard();
        assertTrue(aGameModel.canMoveFromDiscardtoWorkingStack(Workingstack.StackSix));
        aGameModel.moveFromDiscardtoWorkingStack(Workingstack.StackSix);
        aGameModel.discard();
        aGameModel.discard();
        assertFalse(aGameModel.canMoveFromDiscardtoWorkingStack(Workingstack.StackFour));
        aGameModel.discard();
        aGameModel.discard();
        assertTrue(aGameModel.canMoveFromDiscardtoSuitStack());
        aGameModel.moveFromDiscardtoSuitStack();
        assertTrue(aGameModel.canMoveFromWorkingStacktoWorkingStack(Workingstack.StackFive,
                Card.flyWeightFactory(Card.Rank.QUEEN, Card.Suit.SPADES), Workingstack.StackOne));
        aGameModel.moveFromWorkingStacktoWorkingStack(Workingstack.StackFive,
                Card.flyWeightFactory(Card.Rank.QUEEN, Card.Suit.SPADES), Workingstack.StackOne);
        aGameModel.discard();
        aGameModel.discard();
        aGameModel.discard();
        assertTrue(aGameModel.canMoveFromDiscardtoWorkingStack(Workingstack.StackSeven));
        aGameModel.moveFromDiscardtoWorkingStack(Workingstack.StackSeven);
        aGameModel.discard();
        assertTrue(aGameModel.canMoveFromDiscardtoWorkingStack(Workingstack.StackTwo));
        aGameModel.moveFromDiscardtoWorkingStack(Workingstack.StackTwo);
        aGameModel.discard();
        aGameModel.discard();
        assertFalse(aGameModel.canMoveFromWorkingStacktoWorkingStack(Workingstack.StackFive,
                Card.flyWeightFactory(Card.Rank.QUEEN, Card.Suit.SPADES), Workingstack.StackOne));
        assertFalse(aGameModel.canMoveFromWorkingStacktoSuitStack(Workingstack.StackSeven));
        aGameModel.discard();
        assertTrue(aGameModel.canMoveFromDiscardtoWorkingStack(Workingstack.StackThree));
        Card c1 = aDiscard.peek();
        aGameModel.moveFromDiscardtoWorkingStack(Workingstack.StackThree);
        aGameModel.undoMoveFromDiscardtoWorkingStack(Workingstack.StackThree);
        c1 = aDiscard.peek();
        aGameModel.undoMoveFromDiscardtoSuitStack(SuitStack.StackDiamonds);
        assertEquals(aDiscard.peek(), Card.flyWeightFactory(Card.Rank.ACE, Card.Suit.DIAMONDS));
    }

    @Test
    public void testAutoplay() throws NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
        class Fuse {
            private boolean aTriggered = false;
        }
        final Fuse latch = new Fuse();
        Field playS = aGameModel.getClass().getDeclaredField("aPlayingStrategy");
        playS.setAccessible(true);
        playS.set(aGameModel, new StrategyOne() {
            public void move(GameModel pGameModel) {
                latch.aTriggered = true;
            }
        });
        aGameModel.autoplay();
        assertTrue(latch.aTriggered);

    }

}
