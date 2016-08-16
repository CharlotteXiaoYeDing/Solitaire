import static org.junit.Assert.*;

import java.lang.reflect.Field;

import org.junit.Before;
import org.junit.Test;

import solitaire.GameModel.GameModel;
import solitaire.internal.Card;
import solitaire.internal.Deck;
import solitaire.internal.WorkingStack;
import solitaire.internal.WorkingStackManager;

public class TestWorkingStack {
  WorkingStack aWorkingStack;
  Deck aDeck;
  int aVisible;
  Field visible;
  
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
              WorkingStackManager aWorkingStackManager = (WorkingStackManager) workingStackManager.get(GameModel.getInstance());
              Field workingStack = WorkingStackManager.class.getDeclaredField("aWorkingStack");
              workingStack.setAccessible(true);
              aWorkingStack  = ((WorkingStack[]) workingStack.get(aWorkingStackManager))[1];
              visible = WorkingStack.class.getDeclaredField("aVisible");
              visible.setAccessible(true);
              aVisible  = (int) visible.get(aWorkingStack);
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
  public void testInit()
  {
	  assertEquals(aWorkingStack.peek(), Card.flyWeightFactory(Card.Rank.JACK, Card.Suit.HEARTS));
	  assertFalse(aWorkingStack.getVisibility(Card.flyWeightFactory(Card.Rank.QUEEN, Card.Suit.HEARTS)));
  }
  
  @Test
  public void testAdd(){
	  aWorkingStack.push(Card.flyWeightFactory(Card.Rank.QUEEN, Card.Suit.DIAMONDS));
	  aWorkingStack.push(Card.flyWeightFactory(Card.Rank.TWO, Card.Suit.HEARTS));
	  assertTrue(aWorkingStack.getVisibility(Card.flyWeightFactory(Card.Rank.TWO, Card.Suit.HEARTS)));
	  aWorkingStack.push(Card.flyWeightFactory(Card.Rank.FIVE, Card.Suit.SPADES));
	  assertTrue(aWorkingStack.getVisibility(Card.flyWeightFactory(Card.Rank.FIVE, Card.Suit.SPADES)));
	  assertEquals(aWorkingStack.peek(), Card.flyWeightFactory(Card.Rank.FIVE, Card.Suit.SPADES));
  }
  
  @Test
  public void testDraw() throws IllegalArgumentException, IllegalAccessException
  {
	  aWorkingStack.push(Card.flyWeightFactory(Card.Rank.QUEEN, Card.Suit.DIAMONDS));
	  aWorkingStack.push(Card.flyWeightFactory(Card.Rank.TWO, Card.Suit.HEARTS));
	  assertEquals(aVisible, 1);
	  assertEquals(aWorkingStack.draw(),Card.flyWeightFactory(Card.Rank.TWO, Card.Suit.HEARTS) );
	  assertFalse(aWorkingStack.getVisibility(Card.flyWeightFactory(Card.Rank.QUEEN, Card.Suit.HEARTS)));
	  assertEquals(aVisible, 1);
	  assertEquals(aWorkingStack.draw(),Card.flyWeightFactory(Card.Rank.QUEEN, Card.Suit.DIAMONDS));
	  assertEquals(aVisible, 1);
	  assertEquals(aWorkingStack.draw(),Card.flyWeightFactory(Card.Rank.JACK, Card.Suit.HEARTS));
	  assertEquals((int) visible.get(aWorkingStack), 0);
	  assertTrue(aWorkingStack.getVisibility(Card.flyWeightFactory(Card.Rank.QUEEN, Card.Suit.HEARTS)));
  }

}
