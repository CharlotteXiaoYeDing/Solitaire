package solitaire.internal;

import java.util.HashMap;
import java.util.Map;

import solitaire.internal.Card.Suit;

public class SuitStackManager {
    private final Map<SuitStack, Card> aSuitStackManager = new HashMap<>();
    
    public enum SuitStack implements Location
    {
        StackClubs, StackDiamonds, StackSpades, SuitHearts
    }

    public Card viewSuitStack(SuitStack pSuitStack) {
        if (aSuitStackManager.containsKey(pSuitStack)) {
            return aSuitStackManager.get(pSuitStack);
        } else {
            return null;
        }
    }

    public void add(Card pCard) {
        assert pCard != null;
        assert canAdd(pCard);
        if (!aSuitStackManager.containsKey(SuitStack.values()[pCard.getSuit().ordinal()])) {
            aSuitStackManager.put(SuitStack.values()[pCard.getSuit().ordinal()], pCard);
        } else {
            aSuitStackManager.replace(SuitStack.values()[pCard.getSuit().ordinal()], pCard);
        }
    }

    public boolean canAdd(Card pCard) {
        assert pCard != null;
        if (!aSuitStackManager.containsKey(SuitStack.values()[pCard.getSuit().ordinal()])) {
            if (pCard.getRank().ordinal() == Card.Rank.ACE.ordinal()) {
                return true;
            }
        } else {
            if ((aSuitStackManager.get(SuitStack.values()[pCard.getSuit().ordinal()]).getRank().ordinal() + 1) == pCard.getRank().ordinal()) {
                return true;
            }
        }
        return false;
    }

    public Card draw(SuitStack pSuitStack) {
        assert canDraw(pSuitStack);
        Card pCard = aSuitStackManager.get(pSuitStack);
        if (pCard.getRank().ordinal() == 0) {
            aSuitStackManager.remove(pSuitStack, pCard);
        } else {
            aSuitStackManager.replace(pSuitStack,
                    Card.flyWeightFactory(Card.Rank.values()[pCard.getRank().ordinal() - 1], pCard.getSuit()));
        }
        return pCard;
    }

    public boolean canDraw(Location pSuitStack) {
        if (aSuitStackManager.containsKey(pSuitStack)) {
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        String s = "";
        for (Suit suit : Card.Suit.values()) {
            if (viewSuitStack(SuitStack.values()[suit.ordinal()]) == null) {
                s = s + suit + "Empty" + "\n";
            } else {
                s = s + suit + " " + viewSuitStack(SuitStack.values()[suit.ordinal()]).toString() + "\n";
            }
        }
        return s;
    }
    
    public int getScore() {
        int score = 0;
        for (SuitStack aSuitStack : SuitStack.values()) {
            if (viewSuitStack(aSuitStack) != null) {
                int sum = viewSuitStack(aSuitStack).getRank().ordinal() + 1;
                score = score + sum;
            }
        }
        return score;
    }

}
