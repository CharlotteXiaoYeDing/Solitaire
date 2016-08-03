import static org.junit.Assert.*;

import java.lang.reflect.Field;
import org.junit.Before;
import org.junit.Test;

import solitaire.GameModel.GameModel;
import solitaire.internal.Card;
import solitaire.internal.Deck;
import solitaire.internal.WorkingStackManager;
import solitaire.internal.WorkingStackManager.Workingstack;

public class TestWorkingStackManager {
	WorkingStackManager wst;
	Card[] stackOne;
	Card[] stackTwo;
	Card[] stackThree;
	Card[] stackFour;
	Card[] stackFive;
	Card[] stackSix;
	Card[] stackSeven;

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
		assertEquals(Card.flyWeightFactory(Card.Rank.KING, Card.Suit.HEARTS), (stackOne[0]));
		assertEquals(Card.flyWeightFactory(Card.Rank.JACK, Card.Suit.HEARTS), (stackTwo[0]));
		assertEquals(Card.flyWeightFactory(Card.Rank.EIGHT, Card.Suit.HEARTS), (stackThree[0]));
		assertEquals(Card.flyWeightFactory(Card.Rank.FOUR, Card.Suit.HEARTS), (stackFour[0]));
		assertEquals(Card.flyWeightFactory(Card.Rank.QUEEN, Card.Suit.SPADES), (stackFive[0]));
		assertEquals(Card.flyWeightFactory(Card.Rank.SIX, Card.Suit.SPADES), (stackSix[0]));
		assertEquals(Card.flyWeightFactory(Card.Rank.QUEEN, Card.Suit.DIAMONDS), (stackSeven[0]));
	}

	@Test
	public void testAdd() {
		Card c1 = Card.flyWeightFactory(Card.Rank.JACK, Card.Suit.DIAMONDS);
		assertTrue(wst.canAdd(c1, (WorkingStackManager.Workingstack.StackFive)));
		wst.add(c1, (WorkingStackManager.Workingstack.StackFive));
		stackFive = (wst.getVisibleWorkingStack(WorkingStackManager.Workingstack.StackFive));
		assertEquals(Card.flyWeightFactory(Card.Rank.QUEEN, Card.Suit.SPADES), stackFive[0]);
		assertEquals(c1, stackFive[1]);
		assertTrue(wst.canAdd(Card.flyWeightFactory(Card.Rank.JACK, Card.Suit.SPADES),
				(WorkingStackManager.Workingstack.StackSeven)));
		wst.add(Card.flyWeightFactory(Card.Rank.JACK, Card.Suit.SPADES), (WorkingStackManager.Workingstack.StackSeven));
		stackSeven = (wst.getVisibleWorkingStack(WorkingStackManager.Workingstack.StackSeven));
		assertEquals(Card.flyWeightFactory(Card.Rank.QUEEN, Card.Suit.DIAMONDS), stackSeven[0]);
		assertEquals(Card.flyWeightFactory(Card.Rank.JACK, Card.Suit.SPADES), stackSeven[1]);
		assertEquals(null, stackSeven[2]);
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
		assertTrue(wst.canDrawMultiple(Card.flyWeightFactory(Card.Rank.QUEEN, Card.Suit.DIAMONDS),
				(WorkingStackManager.Workingstack.StackSeven)));
		assertFalse(wst.canDrawMultiple(Card.flyWeightFactory(Card.Rank.KING, Card.Suit.DIAMONDS),
				(WorkingStackManager.Workingstack.StackSeven)));

		wst.draw((WorkingStackManager.Workingstack.StackFive));
		stackFive = (wst.getVisibleWorkingStack(WorkingStackManager.Workingstack.StackFive));
		assertEquals(Card.flyWeightFactory(Card.Rank.KING, Card.Suit.SPADES), stackFive[0]);
		assertTrue(wst.canAdd(Card.flyWeightFactory(Card.Rank.QUEEN, Card.Suit.DIAMONDS),
				(WorkingStackManager.Workingstack.StackFive)));
		wst.addMultiple(wst.drawMultiple(Card.flyWeightFactory(Card.Rank.QUEEN, Card.Suit.DIAMONDS),
				(WorkingStackManager.Workingstack.StackSeven)), (WorkingStackManager.Workingstack.StackFive));
		stackSeven = (wst.getVisibleWorkingStack(WorkingStackManager.Workingstack.StackSeven));
		assertEquals(Card.flyWeightFactory(Card.Rank.KING, Card.Suit.DIAMONDS), stackSeven[0]);

	}

}
