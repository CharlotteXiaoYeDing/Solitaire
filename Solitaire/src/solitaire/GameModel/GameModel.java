package solitaire.GameModel;

import java.util.ArrayList;
import java.util.Stack;

import solitaire.GameModel.GameModel.CardDeck;
import solitaire.feature.DiscardMove;
import solitaire.feature.Move;
import solitaire.feature.MultipleCardsMove;
import solitaire.feature.OneCardMove;
import solitaire.feature.UndoManager;
import solitaire.internal.Card;
import solitaire.internal.Deck;
import solitaire.internal.Location;
import solitaire.internal.SuitStackManager;
import solitaire.internal.WorkingStack;
import solitaire.internal.WorkingStackManager;
import solitaire.internal.Card.Suit;
import solitaire.internal.SuitStackManager.SuitStack;
import solitaire.internal.WorkingStackManager.Workingstack;
import solitaire.strategy.PlayStrategy;

public final class GameModel {
	public enum CardDeck implements Location {
		DECK, DISCARD
	}

	private static final GameModel INSTANCE = new GameModel();
	private Deck aDeck = new Deck();
	private WorkingStackManager aWorkingStack;
	private SuitStackManager aSuitStackManager;
	private Stack<Card> aDiscard;
	private UndoManager aUndoManager = new UndoManager(GameModel.getInstance());
	// private PlayStrategy aPlayingStrategy = new StrategyOne();
	private ArrayList<GameModelListener> aListenerList = new ArrayList<>();

	private GameModel() {
	}

	public static GameModel getInstance() {
		return INSTANCE;
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
	
	public int getScore() {
		return aSuitStackManager.getScore();
	}

	public void addListener(GameModelListener aGameModelListener) {
		aListenerList.add(aGameModelListener);
	}

	private void notifyListener() {
		for (GameModelListener aListener : aListenerList) {
			aListener.gameStateChanged();
		}
	}

	public boolean discard() {
		if (!aDeck.isEmpty()) {
			aDiscard.add(aDeck.draw());
			notifyListener();
			return true;
		}
		return false;
	}

	public boolean undoDiscard() {
		if (!aDiscard.isEmpty()) {
			aDeck.push(aDiscard.pop());
			notifyListener();
			return true;
		}
		return false;
	}

	public boolean undo(Location from, Location to) {
		if (from instanceof CardDeck && to instanceof SuitStack) {
			if (aSuitStackManager.canDraw(to)) {
				aDiscard.push(aSuitStackManager.draw((SuitStack) to));
				notifyListener();
				return true;
			}
		}
		if (from instanceof CardDeck && to instanceof Workingstack) {
			if (aWorkingStack.canDraw((Workingstack) to)) {
				aDiscard.push(aWorkingStack.draw((Workingstack) to));
				notifyListener();
				return true;
			}
		}
		if (from instanceof SuitStack && to instanceof Workingstack) {
			return move(to, from);
		}
		if (to instanceof SuitStack && from instanceof Workingstack) {
			return move(to, from);
		}
		if (from instanceof Workingstack && to instanceof Workingstack) {
			return move(to, from);
		}
		return false;
	}


	public boolean undo(Location from, Location to, Card pCard) {
		return move(to, from, pCard);
	}

	public void logMove(Move pMove) {
		aUndoManager.addMove(pMove);
	}

	public Move getDiscardMove() {
		return new DiscardMove(GameModel.getInstance());
	}

	public boolean isCompleted() {
		//To Do
		return true;
	}
	
	public Card peekSuitStack(SuitStack aIndex) {
		return aSuitStackManager.viewSuitStack(aIndex);
	}

	public Card peekDiscard()
	{
		if (aDiscard.isEmpty())
		{
			return null;
		}
		return aDiscard.peek();
	}
	
	public boolean move(Location from, Location to) {

		if (from instanceof CardDeck && to instanceof SuitStack) {
			if (!aDiscard.isEmpty() && aSuitStackManager.canAdd(aDiscard.peek())) {
				aSuitStackManager.add(aDiscard.pop());
				notifyListener();
				return true;
			}
		}

		if (from instanceof CardDeck && to instanceof Workingstack) {
			if ((!aDiscard.isEmpty()) && aWorkingStack.canAdd(aDiscard.peek(), (Workingstack) to)) {
				aWorkingStack.add(aDiscard.pop(), (Workingstack) to);
				notifyListener();

				return true;
			}
		}
		if (from instanceof SuitStack && to instanceof Workingstack) {
			if (aSuitStackManager.canDraw(from)
					&& aWorkingStack.canAdd(aSuitStackManager.viewSuitStack((SuitStack) from), (Workingstack) to)) {
				aWorkingStack.add(aSuitStackManager.draw((SuitStack) from), (Workingstack) to);
				notifyListener();
				return true;
			}
		}
		if (to instanceof SuitStack && from instanceof Workingstack) {
			if (aWorkingStack.canDraw((Workingstack) from)
					&& aSuitStackManager.canAdd(aWorkingStack.getVisibleWorkingStack((Workingstack) from).peek())) {
				aSuitStackManager.add(aWorkingStack.draw((Workingstack) from));
				notifyListener();
				return true;
			}
		}
		if (from instanceof Workingstack && to instanceof Workingstack) {
			if (aWorkingStack.canDraw((Workingstack) from)
					&& aWorkingStack.canAdd((aWorkingStack.getVisibleWorkingStack((Workingstack) from)).peek(),(Workingstack) to)) {
				aWorkingStack.add(aWorkingStack.draw((Workingstack) from), (Workingstack) to);
				notifyListener();
				return true;
			}
		}
		return false;
	}
	
	public boolean move(Location from, Location to, Card pCard) {
		if (aWorkingStack.canDrawMultiple(pCard, (Workingstack) from)
				&& aWorkingStack.canAdd(pCard, (Workingstack) to)) {
			aWorkingStack.addMultiple(aWorkingStack.drawMultiple(pCard, (Workingstack) from), (Workingstack) to);
			notifyListener();
			return true;
		}
		return false;
	}
	
	public boolean canDraw( Location from)
	{
		if (from.equals(CardDeck.DECK))
		{
			if (!aDeck.isEmpty() ) {
				return true;
			}
		}
		if (from.equals(CardDeck.DISCARD)) {
			if (!aDiscard.isEmpty() ) {
				return true;
			}
		}

		if (from instanceof SuitStack) {
			if (aSuitStackManager.canDraw(from)) {
				return true;
			}
		}
		if (from instanceof Workingstack) {
			if (aWorkingStack.canDraw((Workingstack) from)) {
				return true;
			}
		}
		return false;
	}
	
	public boolean canAdd(Card top, Location to) {
		if (to instanceof SuitStack) {
			if (aSuitStackManager.canAdd(top)) {
				return true;
			}
		}
		if (to instanceof Workingstack) {
			if (aWorkingStack.canAdd(top,(Workingstack) to)) {
				return true;
			}
		}
		return false;
	}

	public Move getCardMove(Card top, Location pLocation) {
		if (top.equals(peekDiscard()))
		{
			return new OneCardMove(CardDeck.DISCARD, pLocation, getInstance());
		}
		for (SuitStack index: SuitStack.values())
		{
			if (top.equals(peekSuitStack(index)))
			{
				return new OneCardMove(index, pLocation, getInstance());
			}
		}
		for (Workingstack ws: Workingstack.values())
		{
			if (!aWorkingStack.getVisibleWorkingStack(ws).isEmpty() && aWorkingStack.getVisibleWorkingStack(ws).peek().equals(top))
			{
				return new OneCardMove(ws, pLocation, getInstance());
			}
			for (Card c: aWorkingStack.getVisibleWorkingStack(ws))
				{
					if (c.equals(top))
					{
						 return new MultipleCardsMove(ws, pLocation, c, getInstance());
					}
				}
		}
		return null;
	}

	//
	//
	// public boolean isVisibleInWorkingStack(Card pCard, Workingstack
	// pWorkingstack) {
	// Stack<Card> aVisibleStack = aWorkingStack.viewWorkingStack((Workingstack)
	// pWorkingstack);
	// for (Card card : aVisibleStack) {
	// if (pCard.equals(card)) {
	// return true;
	// }
	// }
	// return false;
	// }
	//
	// public Card viewSuitStack(Location pSuitStack) {
	// return aSuitStackManager.viewSuitStack((SuitStack) pSuitStack);
	// }
	//
	//
	// public Card peekDiscardPile() {
	//
	// return aDiscard.peek();
	// }
	//
	// public Card[] getWorkingStack(Workingstack aIndex) {
	// return aWorkingStack.getWorkingStack(aIndex);
	// }

	// public Stack<Card> viewWorkingStack(Workingstack pWorkingStack) {
	//
	// return aWorkingStack.viewWorkingStack(pWorkingStack);
	// }

	// public boolean hasNextMove() {
	// return aPlayingStrategy.hasNextMove(GameModel.getInstance());
	// }
	//
	// public void autoplay() {
	// aPlayingStrategy.move(this);
	// }
}
