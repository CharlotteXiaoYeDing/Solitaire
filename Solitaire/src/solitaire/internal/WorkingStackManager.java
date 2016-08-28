
package solitaire.internal;

import java.util.Stack;

/**
 * @author Charlotte
 */
public class WorkingStackManager
{

	/**
	 * @author Charlotte
	 */
	public enum Workingstack implements Location
	{
		StackOne, StackTwo, StackThree, StackFour, StackFive, StackSix, StackSeven
	};

	private final WorkingStack[] aWorkingStack = new WorkingStack[Workingstack.values().length];

	/**
	 * Constructor.
	 * @param deck
	 */
	public WorkingStackManager(Deck deck)
	{
		for (int i = 0; i < aWorkingStack.length; i++)
		{
			aWorkingStack[i] = new WorkingStack(deck, (Workingstack.StackOne.ordinal() + 1 + i));
		}
	}

	/**
	 * @param pCard
	 * @param workingstack
	 * @return whether a card can be added to the given working stack
	 */
	public boolean canAdd(Card pCard, Workingstack workingstack)
	{
		assert pCard != null;
		if (aWorkingStack[workingstack.ordinal()].isEmpty())
		{
			return true;
		}
		else
		{
			if ((pCard.getSuit().ordinal() + aWorkingStack[workingstack.ordinal()].peek().getSuit().ordinal()) % 2 != 0)
			{
				if (pCard.getRank().ordinal() == (aWorkingStack[workingstack.ordinal()].peek().getRank().ordinal() - 1))
				{
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * @param pCard
	 * @param workingstack
	 */
	public void add(Card pCard, Workingstack workingstack)
	{
		assert canAdd(pCard, workingstack);
		aWorkingStack[workingstack.ordinal()].push(pCard);
	}

	/**
	 * @param aStack
	 * @param workingstack
	 */
	public void addMultiple(Stack<Card> aStack, Workingstack workingstack)
	{
		assert canAdd(aStack.lastElement(), workingstack);
		while (!aStack.isEmpty())
		{
			aWorkingStack[workingstack.ordinal()].push(aStack.pop());
		}
	}

	/**
	 * @param workingstack
	 * @return whether a card can be drawn from working stack
	 */
	public boolean canDraw(Workingstack workingstack)
	{
		if (aWorkingStack[workingstack.ordinal()].isEmpty())
		{
			return false;
		}
		Card card = aWorkingStack[workingstack.ordinal()].peek();
		return aWorkingStack[workingstack.ordinal()].getVisibility(card);
	}

	/**
	 * @param workingstack
	 * @return the card that is drawn
	 */
	public Card draw(Workingstack workingstack)
	{
		assert canDraw(workingstack);
		Card aCard = aWorkingStack[workingstack.ordinal()].draw();
		return aCard;
	}

	/**
	 * @param pCard
	 * @param workingstack
	 * @return the stack of card drawn
	 */
	public Stack<Card> drawMultiple(Card pCard, Workingstack workingstack)
	{
		assert canDraw(workingstack);
		Stack<Card> aStack = new Stack<>();
		while (pCard != aWorkingStack[workingstack.ordinal()].peek())
		{
			aStack.push(aWorkingStack[workingstack.ordinal()].draw());
		}
		aStack.push(aWorkingStack[workingstack.ordinal()].draw());
		return aStack;
	}

	/**
	 * @param pWorkingstack
	 * @return the stack of working stack
	 */
	public Stack<Card> getWorkingStack(Workingstack pWorkingstack)
	{
		Stack<Card> stack = new Stack<>();

		for (Card aC : aWorkingStack[pWorkingstack.ordinal()])
		{
			stack.push(aC);
		}
		return stack;
	}

	/**
	 * @param pWorkingstack
	 * @return the stack of visible working stack
	 */
	public Stack<Card> getVisibleWorkingStack(Workingstack pWorkingstack)
	{
		Stack<Card> stack = new Stack<>();

		for (Card aC : aWorkingStack[pWorkingstack.ordinal()])
		{
			if (aWorkingStack[pWorkingstack.ordinal()].getVisibility(aC))
			{
				stack.push(aC);
			}
		}
		return stack;
	}
}
