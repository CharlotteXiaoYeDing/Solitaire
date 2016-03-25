package solitaire;

public class CardView {
    private Card aCard;
    private boolean aVisibility = false;
    
    public CardView (Card pCard)
    {
        aCard = pCard;
    }

    public boolean isVisible()
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
