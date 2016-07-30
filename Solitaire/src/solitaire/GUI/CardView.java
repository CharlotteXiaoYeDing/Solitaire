package solitaire.GUI;

import solitaire.internal.Card;

public class CardView {
    private Card aCard;
    private boolean aVisibility = false;
    
    public CardView (Card pCard)
    {
        aCard = pCard;
    }

    public boolean getVisibility()
    {
        return aVisibility;
    }
    
    public void setVisibility(boolean pVisiblity)
    {
        aVisibility =  pVisiblity;
    }
    
    public Card getCard()
    {
        return aCard;
    }
}
