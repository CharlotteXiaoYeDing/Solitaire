package solitaire;

public class DiscardMove implements Move {

    GameModel aGameModel;

    public DiscardMove(GameModel pGameModel) {
        aGameModel = pGameModel;
    }

    @Override
    public void move() {
        assert isLegalized();
        aGameModel.discard();
        aGameModel.logMove(this);
    }

    @Override
    public boolean isLegalized() {
        return !aGameModel.isDeckEmpty();
    }

    @Override
    public void undo() {
        aGameModel.undoDiscard();
    }

}
