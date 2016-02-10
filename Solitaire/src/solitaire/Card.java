package solitaire;

/**
 * @author April
 */
public class Card {
    /**
     * A Card's rank.
     */
    public enum Rank {
        ACE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN, JACK, QUEEN, KING;
    }

    /**
     * A Card's suit.
     * 
     */
    public enum Suit {
        CLUBS, DIAMONDS, SPADES, HEARTS
    }

    private boolean visible;
    private static Card[][] aFlyWeight = new Card[Suit.values().length][Rank.values().length];
    private Rank aRank; // Invariant: != null
    private Suit aSuit; // Invariant: != null

    /**
     * @param pRank
     *            The Rank of the card
     * @param pSuit
     *            The Suit of the card
     * @pre pRank != null && pSuit != null
     */
    private Card(Rank pRank, Suit pSuit) {
        assert pRank != null && pSuit != null;
        aRank = pRank;
        aSuit = pSuit;
        visible = false;
    }

    /**
     * @return the Rank of the card.
     * @pre can only return Rank if the Card is visible
     * @post return != null
     */
    public Rank getRank() {
        assert isVisible();
        return aRank;
    }

    /**
     * @return the Suit of the card.
     * @pre can only return Suit if the Card is visible
     * @post return != null
     */
    public Suit getSuit() {
        assert isVisible();
        return aSuit;
    }

    /**
     * @return if the card is visible
     */
    public boolean isVisible() {
        return this.visible;
    }

    /**
     * Modify the visibility of the Card.
     * 
     * @param pVisbility
     *            If the card is visible
     */
    public void setVisiblity(boolean pVisbility) {
        if (visible != pVisbility) {
            visible = pVisbility;
        }
    }

    @Override
    public String toString() {
        return aRank + " of " + aSuit;
    }

    /**
     * Using FlyWeight Design pattern to ensure the uniqueness of Card
     * 
     * @param pRank
     *            the Rank of Card to be drawn
     * @param pSuit
     *            the Suit of Card to be drawn
     * @return a Card of specified Rank and Suit
     */
    public static Card flyWeightFactory(Rank pRank, Suit pSuit) {
        if (aFlyWeight[pSuit.ordinal()][pRank.ordinal()] == null) {
            aFlyWeight[pSuit.ordinal()][pRank.ordinal()] = new Card(pRank, pSuit);
        }
        return aFlyWeight[pSuit.ordinal()][pRank.ordinal()];
    }
}
