package solitaire;

import java.util.Stack;

public class WorkingStackManager
{
	public static void main(String[] args)
	{

	}

	public enum Workingstack
	{
		StackOne, StackTwo, StackThree, StackFour, StackFive, StackSeven
	};

	private final WorkingStack[] aWorkingStack = new WorkingStack[Workingstack.values().length];

	public WorkingStackManager(Deck deck)
	{
		for (int i = 0; i < aWorkingStack.length; i++)
		{
			aWorkingStack[i] = new WorkingStack(deck, (Workingstack.StackOne.ordinal() + 1));
			aWorkingStack[i].view().setVisiblity(true);
		}
	}

	public boolean canAdd(Card pCard, Workingstack workingstack)
	{
		assert pCard != null;
		if (aWorkingStack[workingstack.ordinal()].isEmpty())
		{
			if (pCard.getRank().ordinal() == Card.Rank.KING.ordinal())
			{
				return true;
			}
		}
		else
		{
			if ((pCard.getSuit().ordinal() + aWorkingStack[workingstack.ordinal()].view().getSuit().ordinal()) % 2 != 0)
			{
				if (pCard.getRank().ordinal() == (aWorkingStack[workingstack.ordinal()].view().getRank().ordinal() - 1))
				{
					return true;
				}
			}
		}
		return false;
	}

	public void add(Card pCard, Workingstack workingstack)
	{
		assert canAdd(pCard, workingstack);
		aWorkingStack[workingstack.ordinal()].add(pCard);

	}

	public Card draw(Workingstack workingstack)
	{
		assert canDraw(workingstack);
		Card aCard= aWorkingStack[workingstack.ordinal()].draw();
		if (!aWorkingStack[workingstack.ordinal()].view().isVisible())
		{
			aWorkingStack[workingstack.ordinal()].view().setVisiblity(true);
		}
		return aCard;
	}

	public Stack<Card> drawMultiple(Card pCard, Workingstack workingstack)
	{
		assert canDrawMultiple(pCard, workingstack);
		Stack<Card> aStack = new Stack<>();
		while (pCard != aWorkingStack[workingstack.ordinal()].view())
		{
			aStack.add(aWorkingStack[workingstack.ordinal()].draw());
		}
		aWorkingStack[workingstack.ordinal()].view().setVisiblity(true);
		return aStack;
	}

	public void addMultiple(Stack<Card> aStack, Workingstack workingstack)
	{
		assert canAdd(aStack.peek(), workingstack);
		while (!aStack.isEmpty())
		{
			aWorkingStack[workingstack.ordinal()].add(aStack.pop());
		}
	}
	
	public boolean canDrawMultiple(Card pCard, Workingstack workingstack)
	{
		boolean candraw = false;
		for (Card aCard: viewWorkingStack(workingstack))
		{
			if (pCard == aCard)
			{
				candraw = true;
			}
		}
		return !isEmpty(workingstack) && candraw;
	}

	public boolean canDraw(Workingstack workingstack)
	{
		return !isEmpty(workingstack);
	}

	public boolean isEmpty(Workingstack workingstack)
	{
		return aWorkingStack[workingstack.ordinal()].isEmpty();
	}

	public Stack<Card> viewWorkingStack(Workingstack pWorkingstack)
	{
		Stack<Card> visibleCard = new Stack<>();
		WorkingStack aWorkingstack = aWorkingStack[pWorkingstack.ordinal()];
		for (Card aCard : aWorkingstack)
		{
			if (aCard.isVisible())
			{
				visibleCard.push(aCard);
			}
		}
		return visibleCard;
	}

}

// 7 Deck