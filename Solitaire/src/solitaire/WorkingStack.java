package solitaire;

import java.util.Iterator;
import java.util.Stack;

public class WorkingStack implements Iterable<Card>
{
	private final Stack<Card> aWorkingStack = new Stack<>();
	
	public WorkingStack(Deck deck, int num)
	{
		for (int i = 0; i< num; i++)
		{
			aWorkingStack.add(deck.draw());
		}
	}
	
	public void add(Card pCard)
	{
		this.add(Card.flyWeightFactory(pCard.getRank(), pCard.getSuit()));
	}
	
	public Card draw()
	{
		return aWorkingStack.pop();
	}
	
	public Card view()
	{
		return aWorkingStack.peek();
	}
	
	public boolean isEmpty()
	{
		return aWorkingStack.isEmpty();
	}
	
	@Override
	public Iterator<Card> iterator()
	{
		return aWorkingStack.iterator();
	}
}
