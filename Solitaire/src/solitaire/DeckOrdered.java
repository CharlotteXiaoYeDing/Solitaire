package solitaire;

import java.util.Stack;

import solitaire.Card.Rank;
import solitaire.Card.Suit;

public class DeckOrdered extends Deck{
    
    
    public DeckOrdered()
    {
    }

    @Override
    public void shuffle() {
        aCards.clear();
        for (Suit suit : Suit.values()) {
            for (Rank rank : Rank.values()) {              
                aCards.push(Card.flyWeightFactory(rank, suit));
            }
        }
    }

}
