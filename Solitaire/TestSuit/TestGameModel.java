import static org.junit.Assert.*;

import java.lang.reflect.Field;
import java.util.Stack;

import org.junit.Before;
import org.junit.Test;

import solitaire.GameModel.GameModel;
import solitaire.GameModel.GameModel.CardDeck;
import solitaire.internal.Card;
import solitaire.internal.Deck;
import solitaire.internal.SuitStackManager;
import solitaire.internal.SuitStackManager.SuitStack;
import solitaire.internal.WorkingStackManager;
import solitaire.internal.WorkingStackManager.Workingstack;

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
        
    }
    
    @Test 
    public void testInit(){
    	assertNotNull(aWorkingStackManager);
        assertNotNull(aSuitStackManager);
        assertNotNull(aDiscard);
    }

    @Test
    public void testDiscard() {
    	assertEquals(aDeck.peek(), Card.flyWeightFactory(Card.Rank.JACK , Card.Suit.DIAMONDS));
        assertTrue(aGameModel.discard());
        assertEquals(aDiscard.peek(), Card.flyWeightFactory(Card.Rank.JACK , Card.Suit.DIAMONDS));
        assertEquals(aDeck.peek(), Card.flyWeightFactory(Card.Rank.TEN, Card.Suit.DIAMONDS));
    }

    @Test
    public void testUndoDiscard() {
    	assertTrue(aGameModel.discard());
    	aGameModel.undoDiscard();
    	assertEquals(aDeck.peek(), Card.flyWeightFactory(Card.Rank.JACK , Card.Suit.DIAMONDS));
    	assertTrue(aDiscard.isEmpty());
    }
    

    @Test
    public void testMoveFromDiscardToSuitStackManager() {
    	assertTrue(aGameModel.discard());
    	assertTrue(aGameModel.discard());
    	assertTrue(aGameModel.discard());
    	assertTrue(aGameModel.discard());
    	assertTrue(aGameModel.discard());
    	assertTrue(aGameModel.discard());
    	assertTrue(aGameModel.discard());
    	assertTrue(aGameModel.discard());
    	assertTrue(aGameModel.discard());
    	assertTrue(aGameModel.discard());
    	assertTrue(aGameModel.discard());
        assertEquals(aDiscard.peek(), Card.flyWeightFactory(Card.Rank.ACE , Card.Suit.DIAMONDS));
        assertNull(aSuitStackManager.viewSuitStack(SuitStack.StackDiamonds));
        assertTrue(aGameModel.move(CardDeck.DISCARD, SuitStack.StackDiamonds));
        assertEquals(aSuitStackManager.viewSuitStack(SuitStack.StackDiamonds),Card.flyWeightFactory(Card.Rank.ACE , Card.Suit.DIAMONDS));
    }
    
    @Test 
    public void testMoveFromDiscardToWorkingStack(){
    	assertTrue(aDiscard.isEmpty());
    	assertTrue(aGameModel.discard());
    	assertEquals(aDiscard.peek(), Card.flyWeightFactory(Card.Rank.JACK , Card.Suit.DIAMONDS));
    	Stack<Card> stackFive = (aWorkingStackManager.getVisibleWorkingStack(WorkingStackManager.Workingstack.StackFive));
    	assertEquals(stackFive.peek(), Card.flyWeightFactory(Card.Rank.QUEEN , Card.Suit.SPADES));
    	assertTrue(aGameModel.move(CardDeck.DISCARD, Workingstack.StackFive));
    	assertTrue(aDiscard.isEmpty());
    	stackFive = (aWorkingStackManager.getVisibleWorkingStack(WorkingStackManager.Workingstack.StackFive));
    	assertEquals(stackFive.peek(), Card.flyWeightFactory(Card.Rank.JACK , Card.Suit.DIAMONDS));
    }
    
    @Test 
    public void testMoveFromWorkingStacktoSuitStack(){
    	aWorkingStackManager.draw(Workingstack.StackFive);
    	aWorkingStackManager.draw(Workingstack.StackFive);
    	Stack<Card> stackFive = (aWorkingStackManager.getVisibleWorkingStack(WorkingStackManager.Workingstack.StackFive));
    	assertEquals(stackFive.peek(), Card.flyWeightFactory(Card.Rank.ACE , Card.Suit.HEARTS));
    	assertTrue(aGameModel.move(Workingstack.StackFive,SuitStack.StackHearts));
    	stackFive = (aWorkingStackManager.getVisibleWorkingStack(WorkingStackManager.Workingstack.StackFive));
    	assertEquals(stackFive.peek(), Card.flyWeightFactory(Card.Rank.TWO , Card.Suit.HEARTS));
    	assertEquals(aSuitStackManager.viewSuitStack(SuitStack.StackHearts),Card.flyWeightFactory(Card.Rank.ACE , Card.Suit.HEARTS));
    }
    
    @Test
    public void testMoveFromSuitStacktoWorkingStack(){
    	aSuitStackManager.add(Card.flyWeightFactory(Card.Rank.ACE , Card.Suit.SPADES));
    	aSuitStackManager.add(Card.flyWeightFactory(Card.Rank.TWO , Card.Suit.SPADES));
    	aSuitStackManager.add(Card.flyWeightFactory(Card.Rank.THREE , Card.Suit.SPADES));
    	assertTrue(aGameModel.move(SuitStack.StackSpades, Workingstack.StackFour));
    	Stack<Card> stackFour = (aWorkingStackManager.getVisibleWorkingStack(WorkingStackManager.Workingstack.StackFour));
    	assertEquals(stackFour.peek(), Card.flyWeightFactory(Card.Rank.THREE , Card.Suit.SPADES));
    	assertEquals(aSuitStackManager.viewSuitStack(SuitStack.StackSpades),Card.flyWeightFactory(Card.Rank.TWO , Card.Suit.SPADES));	
    }
    
    @Test
    public void testMoveFromWorkingStacktoWorkingStack()
    {
    	Stack<Card> stackFour = (aWorkingStackManager.getVisibleWorkingStack(WorkingStackManager.Workingstack.StackFour));
    	assertEquals(stackFour.peek(), Card.flyWeightFactory(Card.Rank.FOUR , Card.Suit.HEARTS));
    	aWorkingStackManager.draw(Workingstack.StackFour);
    	stackFour = (aWorkingStackManager.getVisibleWorkingStack(WorkingStackManager.Workingstack.StackFour));
    	assertEquals(stackFour.peek(), Card.flyWeightFactory(Card.Rank.FIVE , Card.Suit.HEARTS));
    	assertTrue(aGameModel.move(Workingstack.StackFour, Workingstack.StackSix));
    	stackFour = (aWorkingStackManager.getVisibleWorkingStack(WorkingStackManager.Workingstack.StackFour));
    	assertEquals(stackFour.peek(), Card.flyWeightFactory(Card.Rank.SIX , Card.Suit.HEARTS));
    	Stack<Card> stackSix = (aWorkingStackManager.getVisibleWorkingStack(WorkingStackManager.Workingstack.StackSix));
    	assertEquals(stackSix.peek(), Card.flyWeightFactory(Card.Rank.FIVE , Card.Suit.HEARTS));
    }
    
    @Test
    public void testMoveMultipleFromWorkingStacktoWorkingStack()
    {
    	aWorkingStackManager.add(Card.flyWeightFactory(Card.Rank.SEVEN , Card.Suit.SPADES), Workingstack.StackThree);
    	aWorkingStackManager.add(Card.flyWeightFactory(Card.Rank.SIX , Card.Suit.DIAMONDS), Workingstack.StackThree);
    	aWorkingStackManager.add(Card.flyWeightFactory(Card.Rank.FIVE , Card.Suit.SPADES), Workingstack.StackThree);
    	aWorkingStackManager.add(Card.flyWeightFactory(Card.Rank.THREE , Card.Suit.SPADES), Workingstack.StackFour);
    	assertTrue(aGameModel.move(Workingstack.StackFour, Workingstack.StackThree, Card.flyWeightFactory(Card.Rank.FOUR , Card.Suit.HEARTS)));
    	Stack<Card> stackFour = (aWorkingStackManager.getVisibleWorkingStack(WorkingStackManager.Workingstack.StackFour));
    	assertEquals(stackFour.peek(), Card.flyWeightFactory(Card.Rank.FIVE , Card.Suit.HEARTS));
    	Stack<Card> stackThree = (aWorkingStackManager.getVisibleWorkingStack(WorkingStackManager.Workingstack.StackThree));
    	assertEquals(stackThree.peek(), Card.flyWeightFactory(Card.Rank.THREE , Card.Suit.SPADES));
    	assertEquals(stackThree.size(),6);
    }

//    @Test
//    public void testAutoplay() throws NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
//        class Fuse {
//            private boolean aTriggered = false;
//        }
//        final Fuse latch = new Fuse();
//        Field playS = aGameModel.getClass().getDeclaredField("aPlayingStrategy");
//        playS.setAccessible(true);
//        playS.set(aGameModel, new StrategyOne() {
//            public void move(GameModel pGameModel) {
//                latch.aTriggered = true;
//            }
//        });
//        aGameModel.autoplay();
//        assertTrue(latch.aTriggered);
//
//    }

}
