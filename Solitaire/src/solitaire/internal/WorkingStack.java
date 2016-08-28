package solitaire.internal;

import java.util.Iterator;
import java.util.Stack;

/**
 * @author Charlotte
 */
public class WorkingStack implements Iterable<Card>
{
	private final Stack<Card> aWorkingStack = new Stack<>();
	private int aVisible;

	/**
	 * Constructor.
	 * @param deck
	 * @param num
	 */
	public WorkingStack(Deck deck, int num)
	{
		for (int i = 0; i < num; i++)
		{
			aWorkingStack.add(deck.draw());
		}
		aVisible = num - 1;
	}

	/**
	 * @param pCard
	 */
	public void push(Card pCard)
	{
		aWorkingStack.push(pCard);
	}

	/**
	 * @return the card that is drawn
	 */
	public Card draw()
	{
		assert !aWorkingStack.isEmpty();
		if (aVisible == aWorkingStack.size() - 1)
		{
			aVisible--;
		}
		return aWorkingStack.pop();

	}

	/**
	 * @return the last card added
	 */
	public Card peek()
	{
		assert !aWorkingStack.isEmpty();
		return aWorkingStack.peek();
	}

	/**
	 * @return whether a working stack is empty
	 */
	public boolean isEmpty()
	{
		return aWorkingStack.isEmpty();
	}

	/**
	 * @param pCard
	 * @return whether a card is visible
	 */
	public boolean getVisibility(Card pCard)
	{
		int index = aWorkingStack.indexOf(pCard);
		return aVisible <= index;
	}

	@Override
	public Iterator<Card> iterator()
	{
		return aWorkingStack.iterator();
	}
}
