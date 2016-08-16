package solitaire.GUI;

import java.util.HashMap;
import java.util.Map;

import javafx.scene.image.Image;
import solitaire.internal.Card;

public final class CardImages
{
	private static final String IMAGE_LOCATION = "images/";
	private static final String IMAGE_SUFFIX = ".gif";
	private static final String[] RANK_CODES = { "a", "2", "3", "4", "5", "6", "7", "8", "9", "t", "j", "q", "k" };
	private static final String[] SUIT_CODES = { "c", "d", "s", "h" };

	private static Map<String, Image> aCards = new HashMap<String, Image>();

	private CardImages()
	{
	}

	public static Image getCard(Card pCard)
	{
		assert pCard != null;
		return getCard(getCode(pCard));
	}

	public static Image getBack()
	{
		return getCard("b");
	}

	private static String getCode(Card pCard)
	{
		return RANK_CODES[pCard.getRank().ordinal()] + SUIT_CODES[pCard.getSuit().ordinal()];
	}

	private static Image getCard(String pCode)
	{
		Image image = (Image) aCards.get(pCode);
		if (image == null)
		{
			image = new Image(
					CardImages.class.getClassLoader().getResourceAsStream(IMAGE_LOCATION + pCode + IMAGE_SUFFIX));
			aCards.put(pCode, image);
		}
		return image;
	}
}