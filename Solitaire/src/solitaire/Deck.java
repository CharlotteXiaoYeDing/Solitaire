package solitaire;

import java.util.Collections;
import java.util.Iterator;
import java.util.Stack;
import solitaire.Card.Suit;
import solitaire.Card.Rank;

/**
 * An Iterable implementation of cards.
 */
public class Deck implements Iterable<Card> {
    protected final Stack<Card> aCards = new Stack<>();

    /**
     * Empty Deck constructor
     */
    public Deck() {
    }

    /**
     * Clear original Deck, add 52 cards to it and shuffle the Deck
     */
    public void shuffle() {
        aCards.clear();
        for (Suit suit : Suit.values()) {
            for (Rank rank : Rank.values()) {
                aCards.push(Card.flyWeightFactory(rank, suit));
            }
        }
        Collections.shuffle(aCards);
    }

    /**
     * @return if the Deck is empty
     */
    public boolean isEmpty() {
        return aCards.isEmpty();
    }

    /**
     * @return the Card that is drawn
     * @pre aCards.isEmpty == false
     */
    public Card draw() {
        assert !isEmpty();
        return aCards.pop();
    }

    /**
     * @param pCard
     *            the Card to be added
     */
    public void add(Card pCard) {
        aCards.push(pCard);
    }

    /**
     * @return the Card on top of the Deck
     * @pre aCards.isEmpty == false
     */
    public Card peek() {
        assert !isEmpty();
        return aCards.peek();
    }

    @Override
    public Iterator<Card> iterator() {
        return aCards.iterator();
    }

}
