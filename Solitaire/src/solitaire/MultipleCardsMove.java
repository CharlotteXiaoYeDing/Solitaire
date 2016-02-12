package solitaire;

public class MultipleCardsMove implements Move {

    private Location aSource;
    private Location aDestination;
    private Card aCard;
    
    public Location getaSource() {
        return aSource;
    }

    public Location getaDestination() {
        return aDestination;
    }

    public Card getaCard() {
        return aCard;
    }

    public MultipleCardsMove(Location pSource, Location pDestination, Card pCard) {
        aSource = pSource;
        aDestination = pDestination;
        aCard = pCard;
    }

    @Override
    public void move(GameModel pGameModel) {
        assert isLegalized(pGameModel);
        pGameModel.moveFromWorkingStacktoWorkingStack(aSource, aCard, aDestination);
        pGameModel.logMove(this);
    }

    @Override
    public boolean isLegalized(GameModel pGameModel) {
        return pGameModel.canMoveFromWorkingStacktoWorkingStack(aSource, aCard, aDestination);
    }

    @Override
    public void undo(GameModel pGameModel) {
        MultipleCardsMove aMove= new MultipleCardsMove(aDestination, aSource, aCard);
        aMove.move(pGameModel);
    }

}
