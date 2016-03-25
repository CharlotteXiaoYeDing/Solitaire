package solitaire;

public class MultipleCardsMove implements Move {

    private Location aSource;
    private Location aDestination;
    private Card aCard;
    GameModel aGameModel;

    public Location getaSource() {
        return aSource;
    }

    public Location getaDestination() {
        return aDestination;
    }

    public Card getaCard() {
        return aCard;
    }

    public MultipleCardsMove(Location pSource, Location pDestination, Card pCard, GameModel pGameModel) {
        aSource = pSource;
        aDestination = pDestination;
        aCard = pCard;
        aGameModel = pGameModel;
    }

    @Override
    public void move() {
        assert isLegalized();
        aGameModel.moveFromWorkingStacktoWorkingStack(aSource, aCard, aDestination);
        aGameModel.logMove(this);
    }

    @Override
    public boolean isLegalized() {
        return aGameModel.canMoveFromWorkingStacktoWorkingStack(aSource, aCard, aDestination);
    }

    @Override
    public void undo() {
        MultipleCardsMove aMove = new MultipleCardsMove(aDestination, aSource, aCard, aGameModel);
        aMove.move();
    }

}
