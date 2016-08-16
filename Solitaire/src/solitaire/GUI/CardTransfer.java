package solitaire.GUI;

import solitaire.internal.Card;

public class CardTransfer
{
	private static final String SEPARATOR = ";";

	private Card[] aCards;

	public CardTransfer(String pString)
	{
		assert pString != null && pString.length() > 0;
		String[] tokens = pString.split(SEPARATOR);
		aCards = new Card[tokens.length];
		for (int i = 0; i < tokens.length; i++)
		{
			aCards[i] = Card.get(tokens[i]);
		}
		assert aCards.length > 0;
	}

	public static String serialize(Card[] pCards)
	{
		String lReturn = pCards[0].getIDString();
		for (int i = 1; i < pCards.length; i++)
		{
			lReturn += ";" + pCards[i].getIDString();
		}
		return lReturn;
	}

	public Card getTop()
	{
		return aCards[0];
	}

	public int size()
	{
		return aCards.length;
	}
}
