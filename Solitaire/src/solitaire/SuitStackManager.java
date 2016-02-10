package solitaire;

import solitaire.Card.Suit;
import java.util.HashMap;
import java.util.Map;

public class SuitStackManager {
    private final Map<Suit, Card> aSuitStackManager;

    public SuitStackManager() {
        aSuitStackManager = new HashMap<>();
    }

    public Card viewSuitStack(Suit pSuit) {
        if (aSuitStackManager.containsKey(pSuit)) {
            return aSuitStackManager.get(pSuit);
        } else {
            return null;
        }
    }

    public void add(Card pCard) {
        assert pCard != null;
        assert canAdd(pCard);
        if (!aSuitStackManager.containsKey(pCard.getSuit())) {
            aSuitStackManager.put(pCard.getSuit(), pCard);
        } else {
            aSuitStackManager.replace(pCard.getSuit(), pCard);
        }
    }

    public boolean canAdd(Card pCard) {
        assert pCard != null;
        if (!aSuitStackManager.containsKey(pCard.getSuit())) {
            if (pCard.getRank().ordinal() == Card.Rank.ACE.ordinal()) {
                return true;
            }
        } else {
            if ((aSuitStackManager.get(pCard.getSuit()).getRank().ordinal() + 1) == pCard.getRank().ordinal()) {
                return true;
            }
        }
        return false;
    }

    public Card draw(Suit pSuit) {
        assert canDraw(pSuit);
        Card pCard = aSuitStackManager.get(pSuit);
        if (pCard.getRank().ordinal() == 0) {
            aSuitStackManager.remove(pSuit, pCard);
        } else {
            aSuitStackManager.replace(pSuit,
                    Card.flyWeightFactory(Card.Rank.values()[pCard.getRank().ordinal() - 1], pSuit));
        }
        return pCard;
    }

    public boolean canDraw(Suit pSuit) {
        if (aSuitStackManager.containsKey(pSuit)) {
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        String s = "";
        for (Suit suit : Card.Suit.values()) {
            if (viewSuitStack(suit) == null) {
                s = s + suit + "Empty" + "\n";
            } else {
                s = s + suit + " " + viewSuitStack(suit).toString() + "\n";
            }
        }
        return s;
    }

}
