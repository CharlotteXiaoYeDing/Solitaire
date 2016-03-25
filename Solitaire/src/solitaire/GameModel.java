package solitaire;

import java.util.ArrayList;
import java.util.Stack;

import solitaire.Card.Suit;
import solitaire.SuitStackManager.SuitStack;
import solitaire.WorkingStackManager.Workingstack;

public final class GameModel {
    public enum CardDeck implements Location 
    {
        DECK, DISCARD
    }
    private static final GameModel INSTANCE = new GameModel();
    private Deck aDeck = new Deck();
    private WorkingStackManager aWorkingStack;
    private SuitStackManager aSuitStackManager;
    private Stack<Card> aDiscard;
    private UndoManager aUndoManager = new UndoManager(GameModel.getInstance());
    private PlayStrategy aPlayingStrategy = new StrategyOne();
    private ArrayList<GameModelListener> aListenerList = new ArrayList<>();
    

    private GameModel() {
    }
    
//    public static void main (String[] args)
//    {
//        double sum = 0;
//        int max =0;
//   
//        for (int i = 0; i < 10000; i++)
//        {
//            GameModel.getInstance().reset();
//            while (GameModel.getInstance().hasNextMove())
//            {
//                GameModel.getInstance().autoplay();
//            }
//            int score = GameModel.getInstance().getScore();
//            sum = sum + score;
//            if (score > max)
//            {
//                max = score;
//            }
//        }
//        double average = sum/10000;
//        System.out.println(average);
//        System.out.println(max);
//        
//    }

    public static GameModel getInstance() {
        return INSTANCE;
    }

    public boolean hasNextMove()
    {
        return aPlayingStrategy.hasNextMove(GameModel.getInstance());
    }
    
    /**
     * Initiate a new deck and shuffle it, set the first card of the deck to be
     * visible Initiate an empty stack as discard deck Initiate WorkingStack and
     * SuitStack
     */
    public void reset() {
        aDeck.reset();
        aDeck.shuffle();
        aDiscard = new Stack<Card>();
        aWorkingStack = new WorkingStackManager(aDeck);
        aSuitStackManager = new SuitStackManager();
        notifyListener();
    }

    private void notifyListener() {
        for (GameModelListener aListener: aListenerList)
        {
            aListener.gameStateChanged();
        }
    }

    public void discard() {
        assert !isDeckEmpty();
        aDiscard.add(aDeck.draw());
        notifyListener();
    }
    
    public void undoDiscard()
    {
        aDeck.add(aDiscard.pop());
        notifyListener();
    }

    public void autoplay() {
         aPlayingStrategy.move(this);
    }

    public boolean isDeckEmpty() {
        return aDeck.isEmpty();
    }

    public boolean isDiscardEmpty() {
        return aDiscard.isEmpty();
    }
    
    public Move getDiscardMove()
    {
        return new DiscardMove(GameModel.getInstance());
    }
    
    public Move getOneCardMove(Card pCard, Location pDestination)
    {
        Location pSource;
        if (pCard.equals(aDiscard.peek()))
        {
            pSource = CardDeck.DISCARD;
        }
        else
        {
            pSource = aWorkingStack.getLocation(pCard);
        }
        return new OneCardMove(pSource, pDestination, GameModel.getInstance());
    }

    public Stack<Card> viewWorkingStack(Location pWorkingstack) {
        return aWorkingStack.viewWorkingStack((Workingstack) pWorkingstack);
    }

    public Card viewSuitStack(Location pSuitStack) {
        return aSuitStackManager.viewSuitStack((SuitStack) pSuitStack);
    }

    public int getScore()
    {
        return aSuitStackManager.getScore();
    }

    public boolean canMoveFromSuitStacktoWorkingStack(Location pSuitStack, Location pWorkingstack) {
        return aSuitStackManager.canDraw(pSuitStack)
                && aWorkingStack.canAdd(aSuitStackManager.viewSuitStack((SuitStack) pSuitStack), (Workingstack) pWorkingstack);
    }

    public void moveFromSuitStacktoWorkingStack(Location pSuitStack, Location pWorkingstack) {
        assert canMoveFromSuitStacktoWorkingStack(pSuitStack, pWorkingstack);
        aWorkingStack.add(aSuitStackManager.draw((SuitStack) pSuitStack), (Workingstack) pWorkingstack);
        notifyListener();
    }

    public boolean canMoveFromDiscardtoWorkingStack(Location pWorkingstack) {
        return !isDiscardEmpty() && aWorkingStack.canAdd(aDiscard.peek(), (Workingstack) pWorkingstack);
    }

    public void moveFromDiscardtoWorkingStack(Location pWorkingstack) {
        assert canMoveFromDiscardtoWorkingStack(pWorkingstack);
        aWorkingStack.add(aDiscard.pop(), (Workingstack) pWorkingstack);
        notifyListener();
    }

    public boolean canMoveFromDiscardtoSuitStack() {
        return !isDiscardEmpty() && aSuitStackManager.canAdd(aDiscard.peek());
    }

    public void moveFromDiscardtoSuitStack() {
        assert canMoveFromDiscardtoSuitStack();
        aSuitStackManager.add(aDiscard.pop());
        notifyListener();
    }

    public boolean canMoveFromWorkingStacktoSuitStack(Location pWorkingstack) {
        return aWorkingStack.canDraw((Workingstack) pWorkingstack)
                && aSuitStackManager.canAdd(aWorkingStack.viewWorkingStack((Workingstack) pWorkingstack).firstElement());
    }

    public void moveFromWorkingStacktoSuitStack(Location pWorkingstack) {
        assert canMoveFromWorkingStacktoSuitStack(pWorkingstack);
        aSuitStackManager.add(aWorkingStack.draw((Workingstack) pWorkingstack));
        notifyListener();
    }

    public boolean canMoveFromWorkingStacktoWorkingStack(Location sWorkingstack, Card pCard,
            Location dWorkingstack) {
        return aWorkingStack.canDrawMultiple(pCard, (Workingstack) sWorkingstack) && aWorkingStack.canAdd(pCard, (Workingstack) dWorkingstack);
    }

    public void moveFromWorkingStacktoWorkingStack(Location sWorkingstack, Card pCard, Location dWorkingstack) {
        assert canMoveFromWorkingStacktoWorkingStack(sWorkingstack, pCard, dWorkingstack);
        aWorkingStack.addMultiple(aWorkingStack.drawMultiple(pCard, (Workingstack) sWorkingstack), (Workingstack) dWorkingstack);
        notifyListener();
    }
    
    public void undoMoveFromDiscardtoSuitStack(Location pSuitStack)
    {
        aDiscard.push(aSuitStackManager.draw((SuitStack) pSuitStack));
        notifyListener();
    }
    
    public void undoMoveFromDiscardtoWorkingStack(Location pWorkingstack) {
        aDiscard.push(aWorkingStack.draw((Workingstack) pWorkingstack));
        notifyListener();
    }
    
    public void logMove(Move pMove){
        aUndoManager.addMove(pMove);
    }

    public void addListener(GameModelListener aGameModelListener) {
        aListenerList.add(aGameModelListener);
    }

    public Card peekDiscardPile() {
        
        return aDiscard.peek();
    }
}
