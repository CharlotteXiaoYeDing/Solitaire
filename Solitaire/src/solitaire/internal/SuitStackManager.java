package solitaire.internal;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Charlotte
 */
public class SuitStackManager
{
	private final Map<SuitStack, Card> aSuitStackManager = new HashMap<>();

	/**
	 * @author Charlotte
	 */
	public enum SuitStack implements Location
	{
		StackClubs, StackDiamonds, StackSpades, StackHearts
	}

	/**
	 * @param pSuitStack
	 * @return the top card of the given suit stack
	 */
	public Card viewSuitStack(SuitStack pSuitStack)
	{
		if (aSuitStackManager.containsKey(pSuitStack))
		{
			return aSuitStackManager.get(pSuitStack);
		}
		else
		{
			return null;
		}
	}

	/**
	 * @param pCard
	 */
	public void add(Card pCard)
	{
		assert pCard != null;
		assert canAdd(pCard);
		if (!aSuitStackManager.containsKey(SuitStack.values()[pCard.getSuit().ordinal()]))
		{
			aSuitStackManager.put(SuitStack.values()[pCard.getSuit().ordinal()], pCard);
		}
		else
		{
			aSuitStackManager.replace(SuitStack.values()[pCard.getSuit().ordinal()], pCard);
		}
	}

	/**
	 * @param pCard
	 * @return whether a card can be added to the suit stack
	 */
	public boolean canAdd(Card pCard)
	{
		assert pCard != null;
		if (!aSuitStackManager.containsKey(SuitStack.values()[pCard.getSuit().ordinal()]))
		{
			if (pCard.getRank().ordinal() == Card.Rank.ACE.ordinal())
			{
				return true;
			}
		}
		else
		{
			if ((aSuitStackManager.get(SuitStack.values()[pCard.getSuit().ordinal()]).getRank().ordinal() + 1) == pCard
					.getRank().ordinal())
			{
				return true;
			}
		}
		return false;
	}

	/**
	 * @param pSuitStack
	 * @return the card drawn
	 */
	public Card draw(SuitStack pSuitStack)
	{
		assert canDraw(pSuitStack);
		Card pCard = aSuitStackManager.get(pSuitStack);
		if (pCard.getRank().ordinal() == 0)
		{
			aSuitStackManager.remove(pSuitStack, pCard);
		}
		else
		{
			aSuitStackManager.replace(pSuitStack,
					Card.flyWeightFactory(Card.Rank.values()[pCard.getRank().ordinal() - 1], pCard.getSuit()));
		}
		return pCard;
	}

	/**
	 * @param pSuitStack
	 * @return whether a card can be drawn from the given suit stack
	 */
	public boolean canDraw(Location pSuitStack)
	{
		if (aSuitStackManager.containsKey(pSuitStack))
		{
			return true;
		}
		return false;
	}

	/**
	 * @return score of the game
	 */
	public int getScore()
	{
		int score = 0;
		for (SuitStack aSuitStack : SuitStack.values())
		{
			if (viewSuitStack(aSuitStack) != null)
			{
				int sum = viewSuitStack(aSuitStack).getRank().ordinal() + 1;
				score = score + sum;
			}
		}
		return score;
	}

}
