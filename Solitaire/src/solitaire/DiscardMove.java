package solitaire;

public class DiscardMove implements Move{

    @Override
    public void move(GameModel pGameModel) {
        assert isLegalized(pGameModel);
        pGameModel.discard(); 
        pGameModel.logMove(this);
    }

    @Override
    public boolean isLegalized(GameModel pGameModel) {
        return !pGameModel.isDeckEmpty();
    }

    @Override
    public void undo(GameModel pGameModel) {
        pGameModel.undoDiscard();
    }


}
