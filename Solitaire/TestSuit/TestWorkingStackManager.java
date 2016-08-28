import static org.junit.Assert.*;

import java.lang.reflect.Field;
import java.util.Stack;

import org.junit.Before;
import org.junit.Test;

import solitaire.GameModel.GameModel;
import solitaire.internal.Card;
import solitaire.internal.WorkingStackManager;

/**
 * @author Charlotte
 */
public class TestWorkingStackManager {
	WorkingStackManager wst;
	Stack<Card> stackOne;
	Stack<Card> stackTwo;
	Stack<Card> stackThree;
	Stack<Card> stackFour;
	Stack<Card> stackFive;
	Stack<Card> stackSix;
	Stack<Card> stackSeven;

	@Before
	public void setUp() {
		try {
			Field deck = GameModel.class.getDeclaredField("aDeck");
			deck.setAccessible(true);
			try {
				deck.set(GameModel.getInstance(), new DeckOrdered());
				GameModel.getInstance().reset();
				Field workingStackManager = GameModel.class.getDeclaredField("aWorkingStack");
				workingStackManager.setAccessible(true);
				wst = (WorkingStackManager) workingStackManager.get(GameModel.getInstance());
				stackOne = (wst.getVisibleWorkingStack(WorkingStackManager.Workingstack.StackOne));
				stackTwo = (wst.getVisibleWorkingStack(WorkingStackManager.Workingstack.StackTwo));
				stackThree = (wst.getVisibleWorkingStack(WorkingStackManager.Workingstack.StackThree));
				stackFour = (wst.getVisibleWorkingStack(WorkingStackManager.Workingstack.StackFour));
				stackFive = (wst.getVisibleWorkingStack(WorkingStackManager.Workingstack.StackFive));
				stackSix = (wst.getVisibleWorkingStack(WorkingStackManager.Workingstack.StackSix));
				stackSeven = (wst.getVisibleWorkingStack(WorkingStackManager.Workingstack.StackSeven));
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
	public void testConstructor() {
		assertEquals(Card.flyWeightFactory(Card.Rank.KING, Card.Suit.HEARTS), (stackOne.peek()));
		assertEquals(Card.flyWeightFactory(Card.Rank.JACK, Card.Suit.HEARTS), (stackTwo.peek()));
		assertEquals(Card.flyWeightFactory(Card.Rank.EIGHT, Card.Suit.HEARTS), (stackThree.peek()));
		assertEquals(Card.flyWeightFactory(Card.Rank.FOUR, Card.Suit.HEARTS), (stackFour.peek()));
		assertEquals(Card.flyWeightFactory(Card.Rank.QUEEN, Card.Suit.SPADES), (stackFive.peek()));
		assertEquals(Card.flyWeightFactory(Card.Rank.SIX, Card.Suit.SPADES), (stackSix.peek()));
		assertEquals(Card.flyWeightFactory(Card.Rank.QUEEN, Card.Suit.DIAMONDS), (stackSeven.peek()));
	}

	@Test
	public void testAdd() {
		Card c1 = Card.flyWeightFactory(Card.Rank.JACK, Card.Suit.DIAMONDS);
		assertTrue(wst.canAdd(c1, (WorkingStackManager.Workingstack.StackFive)));
		wst.add(c1, (WorkingStackManager.Workingstack.StackFive));
		stackFive = (wst.getVisibleWorkingStack(WorkingStackManager.Workingstack.StackFive));
		assertEquals(Card.flyWeightFactory(Card.Rank.QUEEN, Card.Suit.SPADES), stackFive.get(0));
		assertEquals(c1, stackFive.peek());
		assertTrue(wst.canAdd(Card.flyWeightFactory(Card.Rank.JACK, Card.Suit.SPADES),
				(WorkingStackManager.Workingstack.StackSeven)));
		wst.add(Card.flyWeightFactory(Card.Rank.JACK, Card.Suit.SPADES), (WorkingStackManager.Workingstack.StackSeven));
		stackSeven = (wst.getVisibleWorkingStack(WorkingStackManager.Workingstack.StackSeven));
		assertEquals(Card.flyWeightFactory(Card.Rank.QUEEN, Card.Suit.DIAMONDS), stackSeven.get(0));
		assertEquals(Card.flyWeightFactory(Card.Rank.JACK, Card.Suit.SPADES), stackSeven.peek());
		assertEquals(2, stackSeven.size());
		assertTrue(wst.canAdd(Card.flyWeightFactory(Card.Rank.FIVE, Card.Suit.DIAMONDS),
				(WorkingStackManager.Workingstack.StackSix)));
		assertFalse(wst.canAdd(Card.flyWeightFactory(Card.Rank.QUEEN, Card.Suit.DIAMONDS),
				(WorkingStackManager.Workingstack.StackOne)));
		assertFalse(wst.canAdd(Card.flyWeightFactory(Card.Rank.THREE, Card.Suit.SPADES),
				(WorkingStackManager.Workingstack.StackTwo)));
		assertFalse(wst.canAdd(Card.flyWeightFactory(Card.Rank.SEVEN, Card.Suit.DIAMONDS),
				(WorkingStackManager.Workingstack.StackThree)));
		assertFalse(wst.canAdd(Card.flyWeightFactory(Card.Rank.TEN, Card.Suit.CLUBS),
				(WorkingStackManager.Workingstack.StackFour)));
	}

	@Test
	public void testDraw() {
		assertTrue(wst.canDraw((WorkingStackManager.Workingstack.StackTwo)));
		assertEquals(Card.flyWeightFactory(Card.Rank.JACK, Card.Suit.HEARTS),
				wst.draw((WorkingStackManager.Workingstack.StackTwo)));
		assertTrue(wst.canDraw((WorkingStackManager.Workingstack.StackTwo)));
		assertEquals(Card.flyWeightFactory(Card.Rank.QUEEN, Card.Suit.HEARTS),
				wst.draw((WorkingStackManager.Workingstack.StackTwo)));
		assertFalse(wst.canDraw((WorkingStackManager.Workingstack.StackTwo)));
	}

	@Test
	public void testAddAndDrawMultiple() {
		wst.add(Card.flyWeightFactory(Card.Rank.JACK, Card.Suit.SPADES), (WorkingStackManager.Workingstack.StackSeven));
		wst.add(Card.flyWeightFactory(Card.Rank.TEN, Card.Suit.HEARTS), (WorkingStackManager.Workingstack.StackSeven));
		
		assertTrue(wst.canDraw(
				(WorkingStackManager.Workingstack.StackSeven)));
		assertFalse(wst.canDraw(
				(WorkingStackManager.Workingstack.StackSeven)));

		wst.draw((WorkingStackManager.Workingstack.StackFive));
		stackFive = (wst.getVisibleWorkingStack(WorkingStackManager.Workingstack.StackFive));
		assertEquals(Card.flyWeightFactory(Card.Rank.KING, Card.Suit.SPADES), stackFive.peek());
		assertTrue(wst.canAdd(Card.flyWeightFactory(Card.Rank.QUEEN, Card.Suit.DIAMONDS),
				(WorkingStackManager.Workingstack.StackFive)));
		wst.addMultiple(wst.drawMultiple(Card.flyWeightFactory(Card.Rank.QUEEN, Card.Suit.DIAMONDS),
				(WorkingStackManager.Workingstack.StackSeven)), (WorkingStackManager.Workingstack.StackFive));
		stackSeven = (wst.getVisibleWorkingStack(WorkingStackManager.Workingstack.StackSeven));
		assertEquals(Card.flyWeightFactory(Card.Rank.KING, Card.Suit.DIAMONDS), stackSeven.peek());

	}

}
