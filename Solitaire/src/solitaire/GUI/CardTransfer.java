package solitaire.GUI;

import solitaire.internal.Card;

public class CardTransfer
{
	private static final String SEPARATOR = ";";

	private Card aCard;

	public CardTransfer(String pString)
	{
		assert pString != null;
		aCard = Card.get(pString);
	}

	public static String serialize(Card pCard)
	{
		return pCard.getIDString();
	}

	public Card getCard()
	{
		return aCard;
	}
}
