import static org.junit.Assert.*;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Stack;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import solitaire.Card;
import solitaire.Card.Suit;
import solitaire.Deck;
import solitaire.DeckOrdered;
import solitaire.GameModel;
import solitaire.SuitStackManager;
import solitaire.WorkingStackManager;
import solitaire.WorkingStackManager.Workingstack;

public class TestGameModel {

    static GameModel aGameModel;
    static Deck aDeck;
    static SuitStackManager aSuitStackManager;
    static WorkingStackManager aWorkingStackManager;
    static Stack<Card> aDiscard;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
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
    }

    @Test
    public void testReset() {
        assertFalse(aGameModel.isDeckEmpty());
        assertTrue(aGameModel.isDiscardEmpty());
        assertTrue(aDeck.peek().isVisible());
        assertNotNull(aWorkingStackManager);
        assertNotNull(aSuitStackManager);
    }

    @Test
    public void testDiscard() {
        if (!aGameModel.isDeckEmpty()) {
            while (!aGameModel.isDeckEmpty()) {
                assertTrue(aDeck.peek().isVisible());
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
    }

    @Test
    public void testMove() {
        if (!aGameModel.isDeckEmpty()) {
            aGameModel.discard();
            assertFalse(aGameModel.canMoveFromDiscardtoSuitStack());
            assertFalse(aGameModel.canMoveFromDiscardtoWorkingStack(Workingstack.StackOne));
            assertFalse(aGameModel.canMoveFromDiscardtoWorkingStack(Workingstack.StackTwo));
            assertFalse(aGameModel.canMoveFromDiscardtoWorkingStack(Workingstack.StackThree));
            assertFalse(aGameModel.canMoveFromDiscardtoWorkingStack(Workingstack.StackFour));
            assertTrue(aGameModel.canMoveFromDiscardtoWorkingStack(Workingstack.StackFive));
            assertFalse(aGameModel.canMoveFromDiscardtoWorkingStack(Workingstack.StackSix));
            assertFalse(aGameModel.canMoveFromDiscardtoWorkingStack(Workingstack.StackSeven));
            aGameModel.moveFromDiscardtoWorkingStack(Workingstack.StackFive);
            assertFalse(aGameModel.canMoveFromWorkingStacktoSuitStack(Workingstack.StackOne));
            assertFalse(aGameModel.canMoveFromWorkingStacktoSuitStack(Workingstack.StackTwo));
            assertFalse(aGameModel.canMoveFromWorkingStacktoSuitStack(Workingstack.StackThree));
            assertFalse(aGameModel.canMoveFromWorkingStacktoSuitStack(Workingstack.StackFour));
            assertFalse(aGameModel.canMoveFromWorkingStacktoSuitStack(Workingstack.StackFive));
            assertFalse(aGameModel.canMoveFromWorkingStacktoSuitStack(Workingstack.StackSix));
            assertFalse(aGameModel.canMoveFromWorkingStacktoSuitStack(Workingstack.StackSeven));
            assertTrue(aGameModel.canMoveFromWorkingStacktoWorkingStack(Workingstack.StackFive,
                    Card.flyWeightFactory(Card.Rank.QUEEN, Card.Suit.SPADES), Workingstack.StackOne));
            assertFalse(aGameModel.canMoveFromWorkingStacktoWorkingStack(Workingstack.StackFour,
                    Card.flyWeightFactory(Card.Rank.QUEEN, Card.Suit.SPADES), Workingstack.StackThree));
            aGameModel.moveFromWorkingStacktoWorkingStack(Workingstack.StackFive,
                    Card.flyWeightFactory(Card.Rank.QUEEN, Card.Suit.SPADES), Workingstack.StackOne);
            assertTrue(aGameModel.canMoveFromWorkingStacktoWorkingStack(Workingstack.StackSeven,
                    Card.flyWeightFactory(Card.Rank.QUEEN, Card.Suit.DIAMONDS), Workingstack.StackFive));
            aGameModel.moveFromWorkingStacktoWorkingStack(Workingstack.StackSeven,
                    Card.flyWeightFactory(Card.Rank.QUEEN, Card.Suit.DIAMONDS), Workingstack.StackFive);
            assertFalse(aGameModel.canMoveFromSuitStacktoWorkingStack(Card.Suit.CLUBS, Workingstack.StackSeven));
            assertFalse(aGameModel.canMoveFromWorkingStacktoSuitStack(Workingstack.StackFour));
            System.out.println(aDeck.toString());
            System.out.println(aSuitStackManager.toString());
            System.out.println(aWorkingStackManager.toString());
        }
    }

}
