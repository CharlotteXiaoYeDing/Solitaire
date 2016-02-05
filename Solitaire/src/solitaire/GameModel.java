package solitaire;

import java.util.Stack;

import solitaire.Card.Suit;
import solitaire.WorkingStackManager.Workingstack;

public final class GameModel
{
	private static final GameModel INSTANCE = new GameModel();
	private Deck aDeck;
	private WorkingStackManager aWorkingStack;
	private SuitStackManager aSuitStackManager;
	private Deck aDiscard;

	private GameModel()
	{
	}

	public static GameModel getInstance()
	{
		return INSTANCE;
	}

	public void reset()
	{
		aDiscard = new Deck();
		aDeck = new Deck();
		aDeck.shuffle();
		aWorkingStack = new WorkingStackManager(aDeck);
		aDeck.peek().setVisiblity(true);
		aSuitStackManager = new SuitStackManager();
	}

	public void discard()
	{
		assert !isDeckEmpty();
		aDiscard.add(aDeck.draw());
		if (!isDeckEmpty())
		{
			aDeck.peek().setVisiblity(true);
		}
	}

	public void autoplay(PlayStrategy pStrategy)
	{
		pStrategy.play();
	}

	public boolean isDeckEmpty()
	{
		return aDeck.isEmpty();
	}

	public boolean isDiscardEmpty()
	{
		return aDiscard.isEmpty();
	}

	public Stack<Card> viewWorkingStack(Workingstack pWorkingstack)
	{
		return aWorkingStack.viewWorkingStack(pWorkingstack);
	}

	public Card viewSuitStack(Suit pSuit)
	{
		return aSuitStackManager.viewSuitStack(pSuit);
	}

	public int getScore()
	{
		int score = 0;
		for (Suit aSuit : Card.Suit.values())
		{
			if (viewSuitStack(aSuit) != null)
			{
				int sum = viewSuitStack(aSuit).getRank().ordinal() + 1;
				score = score + sum;
			}
		}
		return score;
	}

	public boolean canMoveFromSuitStacktoWorkingStack(Suit pSuit, Workingstack pWorkingstack)
	{
		return aSuitStackManager.canDraw(pSuit)
				&& aWorkingStack.canAdd(aSuitStackManager.viewSuitStack(pSuit), pWorkingstack);
	}

	public void moveFromSuitStacktoWorkingStack(Suit pSuit, Workingstack pWorkingstack)
	{
		assert canMoveFromSuitStacktoWorkingStack(pSuit, pWorkingstack);
		aWorkingStack.add(aSuitStackManager.draw(pSuit), pWorkingstack);
	}

	public boolean canMoveFromDiscardtoWorkingStack(Workingstack pWorkingstack)
	{
		return !isDiscardEmpty() && aWorkingStack.canAdd(aDiscard.peek(), pWorkingstack);
	}

	public void moveFromDiscardtoWorkingStack(Workingstack pWorkingstack)
	{
		assert canMoveFromDiscardtoWorkingStack(pWorkingstack);
		aWorkingStack.add(aDiscard.draw(), pWorkingstack);
	}

	public boolean canMoveFromDiscardtoSuitStack()
	{
		return !isDiscardEmpty() && aSuitStackManager.canAdd(aDiscard.peek());
	}

	public void moveFromDiscardtoSuitStack()
	{
		assert canMoveFromDiscardtoSuitStack();
		aSuitStackManager.add(aDiscard.draw());
	}

	public boolean canMoveFromWorkingStacktoSuitStack(Workingstack pWorkingstack)
	{
		return aWorkingStack.canDraw(pWorkingstack)&&aSuitStackManager.canAdd(aWorkingStack.viewWorkingStack(pWorkingstack).firstElement());
	}
	public void moveFromWorkingStacktoSuitStack(Workingstack pWorkingstack)
	{
		assert canMoveFromWorkingStacktoSuitStack(pWorkingstack);
		aSuitStackManager.add(aWorkingStack.draw(pWorkingstack));
	}
	
	public boolean canMoveFromWorkingStacktoWorkingStack(Workingstack sWorkingstack, Card pCard, Workingstack dWorkingstack)
	{
		return aWorkingStack.canDrawMultiple(pCard, sWorkingstack) && aWorkingStack.canAdd(pCard, dWorkingstack);
	}

	public void moveFromWorkingStacktoWorkingStack(Workingstack sWorkingstack, Card pCard, Workingstack dWorkingstack)
	{
		assert canMoveFromWorkingStacktoWorkingStack(sWorkingstack, pCard, dWorkingstack);
		aWorkingStack.addMultiple(aWorkingStack.drawMultiple(pCard, sWorkingstack), dWorkingstack);
	}
}
