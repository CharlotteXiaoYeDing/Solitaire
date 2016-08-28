package solitaire.GameModel;

import java.util.ArrayList;
import java.util.Stack;

import solitaire.feature.DiscardMove;
import solitaire.feature.Move;
import solitaire.feature.MultipleCardsMove;
import solitaire.feature.OneCardMove;
import solitaire.feature.UndoManager;
import solitaire.internal.Card;
import solitaire.internal.Deck;
import solitaire.internal.Location;
import solitaire.internal.SuitStackManager;
import solitaire.internal.WorkingStackManager;
import solitaire.internal.SuitStackManager.SuitStack;
import solitaire.internal.WorkingStackManager.Workingstack;

/**
 * Game Model is a singleton.
 * @author Charlotte
 */
public final class GameModel
{
	/**
	 * @author Charlotte
	 */
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
	private ArrayList<GameModelListener> aListenerList = new ArrayList<>();

	private GameModel()
	{
	}

	/**
	 * @return game model
	 */
	public static GameModel getInstance()
	{
		return INSTANCE;
	}

	/**
	 * Initiate a new deck and shuffle it. Initiate an empty stack as
	 * discard deck. Initiate WorkingStack and SuitStack.
	 */
	public void reset()
	{
		aDeck.reset();
		aDeck.shuffle();
		aDiscard = new Stack<Card>();
		aWorkingStack = new WorkingStackManager(aDeck);
		aSuitStackManager = new SuitStackManager();
		notifyListener();
	}

	/**
	 * @return the score of the game
	 */
	public int getScore()
	{
		return aSuitStackManager.getScore();
	}

	/**
	 * @param aGameModelListener
	 */
	public void addListener(GameModelListener aGameModelListener)
	{
		aListenerList.add(aGameModelListener);
	}

	private void notifyListener()
	{
		for (GameModelListener aListener : aListenerList)
		{
			aListener.gameStateChanged();
		}
	}

	/**
	 * @return whether discard successfully
	 */
	public boolean discard()
	{
		if (!aDeck.isEmpty())
		{
			aDiscard.add(aDeck.draw());
			notifyListener();
			return true;
		}
		return false;
	}

	/**
	 * @return whether undo successfully
	 */
	public boolean undoDiscard()
	{
		if (!aDiscard.isEmpty())
		{
			aDeck.push(aDiscard.pop());
			notifyListener();
			return true;
		}
		return false;
	}
	
	/**
	 * @return Discard Move
	 */
	public Move getDiscardMove()
	{
		return new DiscardMove(GameModel.getInstance());
	}
	
	/**
	 * @param pSource
	 * @param pDestination
	 * @return whether move successfully
	 */
	public boolean move(Location pSource, Location pDestination)
	{
		if (pSource.equals(CardDeck.DISCARD) && pDestination instanceof SuitStack)
		{
			if (canDraw(pSource) && canAdd(aDiscard.peek(), pDestination))
			{
				aSuitStackManager.add(aDiscard.pop());
				notifyListener();
				return true;
			}
		}

		if (pSource.equals(CardDeck.DISCARD) && pDestination instanceof Workingstack)
		{
			if (canDraw(pSource) && canAdd(aDiscard.peek(), pDestination))
			{
				aWorkingStack.add(aDiscard.pop(), (Workingstack) pDestination);
				notifyListener();
				return true;
			}
		}
		if (pSource instanceof SuitStack && pDestination instanceof Workingstack)
		{
			if (canDraw(pSource)
					&& canAdd(aSuitStackManager.viewSuitStack((SuitStack) pSource), pDestination))
			{
				aWorkingStack.add(aSuitStackManager.draw((SuitStack) pSource), (Workingstack) pDestination);
				notifyListener();
				return true;
			}
		}
		if (pDestination instanceof SuitStack && pSource instanceof Workingstack)
		{
			if (canDraw(pSource)
					&& aSuitStackManager.canAdd(aWorkingStack.getVisibleWorkingStack((Workingstack) pSource).peek()))
			{
				aSuitStackManager.add(aWorkingStack.draw((Workingstack) pSource));
				notifyListener();
				return true;
			}
		}
		if (pSource instanceof Workingstack && pDestination instanceof Workingstack)
		{
			if (canDraw(pSource) && aWorkingStack
					.canAdd((aWorkingStack.getVisibleWorkingStack((Workingstack) pSource)).peek(), (Workingstack) pDestination))
			{
				aWorkingStack.add(aWorkingStack.draw((Workingstack) pSource), (Workingstack) pDestination);
				notifyListener();
				return true;
			}
		}
		return false;
	}

	/**
	 * @param pSource
	 * @param pDestination
	 * @param pCard
	 * @return whether move successfully
	 */
	public boolean move(Location pSource, Location pDestination, Card pCard)
	{
		if (canDraw(pSource) && canAdd(pCard, pDestination))
		{
			aWorkingStack.addMultiple(aWorkingStack.drawMultiple(pCard, (Workingstack) pSource), (Workingstack) pDestination);
			notifyListener();
			return true;
		}
		return false;
	}

	/**
	 * @param pDestination
	 * @return whether can be drawn
	 */
	public boolean canDraw(Location pDestination)
	{
		if (pDestination.equals(CardDeck.DECK))
		{
			if (!aDeck.isEmpty())
			{
				return true;
			}
		}
		
		if (pDestination.equals(CardDeck.DISCARD))
		{
			if (!aDiscard.isEmpty())
			{
				return true;
			}
		}

		if (pDestination instanceof SuitStack)
		{
			if (aSuitStackManager.canDraw(pDestination))
			{
				return true;
			}
		}
		if (pDestination instanceof Workingstack)
		{
			if (aWorkingStack.canDraw((Workingstack) pDestination))
			{
				return true;
			}
		}
		
		return false;
	}

	/**
	 * @param top
	 * @param pDestination
	 * @return whether can be added
	 */
	public boolean canAdd(Card top, Location pDestination)
	{
		if (pDestination instanceof SuitStack)
		{
			if (aSuitStackManager.canAdd(top))
			{
				return true;
			}
		}
		
		if (pDestination instanceof Workingstack)
		{
			if (aWorkingStack.canAdd(top, (Workingstack) pDestination))
			{

				return true;
			}
		}
		
		return false;
	}

	/**
	 * @param pSource
	 * @param pDestination
	 * @return whether undo successfully13
	 */
	public boolean undo(Location pSource, Location pDestination)
	{
		if (pSource instanceof CardDeck && pDestination instanceof SuitStack)
		{
			if (aSuitStackManager.canDraw(pDestination))
			{
				aDiscard.push(aSuitStackManager.draw((SuitStack) pDestination));
				notifyListener();
				return true;
			}
		}
		if (pSource instanceof CardDeck && pDestination instanceof Workingstack)
		{
			if (aWorkingStack.canDraw((Workingstack) pDestination))
			{
				aDiscard.push(aWorkingStack.draw((Workingstack) pDestination));
				notifyListener();
				return true;
			}
		}
		if (pSource instanceof SuitStack && pDestination instanceof Workingstack)
		{
			return move(pDestination, pSource);
		}
		if (pSource instanceof Workingstack && pDestination instanceof SuitStack)
		{
			return move(pDestination, pSource);
		}
		if (pSource instanceof Workingstack && pDestination instanceof Workingstack)
		{
			return move(pDestination, pSource);
		}
		return false;
	}

	/**
	 * @param pSource
	 * @param pDestination
	 * @param pCard
	 * @return whether undo successfully
	 */
	public boolean undo(Location pSource, Location pDestination, Card pCard)
	{
		return move(pDestination, pSource, pCard);
	}
	
	/**
	 * @param top
	 * @param pDestination
	 * @return a move
	 */
	public Move getCardMove(Card top, Location pDestination)
	{
		if (top.equals(peekDiscard()))
		{
			return new OneCardMove(CardDeck.DISCARD, pDestination, getInstance());
		}
		for (SuitStack index : SuitStack.values())
		{
			if (top.equals(peekSuitStack(index)))
			{
				return new OneCardMove(index, pDestination, getInstance());
			}
		}
		for (Workingstack ws : Workingstack.values())
		{
			if (!aWorkingStack.getVisibleWorkingStack(ws).isEmpty()
					&& aWorkingStack.getVisibleWorkingStack(ws).peek().equals(top))
			{
				return new OneCardMove(ws, pDestination, getInstance());
			}
			for (Card c : aWorkingStack.getVisibleWorkingStack(ws))
			{
				if (c.equals(top))
				{
					return new MultipleCardsMove(ws, pDestination, c, getInstance());
				}
			}
		}
		return null;
	}

	/**
	 * @param pMove
	 */
	public void logMove(Move pMove)
	{
		aUndoManager.addMove(pMove);
	}

	/**
	 * @param aIndex
	 * @return the top card of a suit stack
	 */
	public Card peekSuitStack(SuitStack aIndex)
	{
		return aSuitStackManager.viewSuitStack(aIndex);
	}

	/**
	 * @return the top card of a discard deck
	 */
	public Card peekDiscard()
	{
		if (aDiscard.isEmpty())
		{
			return null;
		}
		return aDiscard.peek();
	}

	/**
	 * @param pCard
	 * @param aIndex
	 * @return whether card is visible
	 */
	public boolean isVisibleInWorkingStack(Card pCard, Workingstack aIndex)
	{
		return aWorkingStack.getVisibleWorkingStack(aIndex).contains(pCard);
	}

	/**
	 * @param aIndex
	 * @return stack in an array
	 */
	public Card[] getStack(Workingstack aIndex)
	{
		Card[] card = new Card[aWorkingStack.getWorkingStack(aIndex).size()];
		for (int i = 0; i < aWorkingStack.getWorkingStack(aIndex).size(); i++)
		{
			card[i] = aWorkingStack.getWorkingStack(aIndex).get(i);
		}
		return card;
	}

	public void undoLast()
	{
		aUndoManager.undo();
		
	}
}
