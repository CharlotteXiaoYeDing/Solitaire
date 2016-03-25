package solitaire;

import solitaire.GameModel.CardDeck;
import solitaire.SuitStackManager.SuitStack;

public class OneCardMove implements Move {

    private Location aSource;
    private Location aDestination;
    GameModel aGameModel;

    public OneCardMove(Location pSource, Location pDestination, GameModel pGameModel) {
        aSource = pSource;
        aDestination = pDestination;
        aGameModel = pGameModel;
    }

    @Override
    public void move() {
        assert isLegalized();
        if (aSource instanceof SuitStack && aDestination instanceof WorkingStack) {
            aGameModel.moveFromSuitStacktoWorkingStack(aSource, aDestination);
        }
        if (aSource instanceof WorkingStack && aDestination instanceof SuitStack) {
            aGameModel.moveFromWorkingStacktoSuitStack(aSource);
        }
        if (aSource instanceof CardDeck && aDestination instanceof SuitStack) {
            aGameModel.moveFromDiscardtoSuitStack();
        }
        if (aSource instanceof CardDeck && aDestination instanceof WorkingStack) {
            aGameModel.moveFromDiscardtoWorkingStack(aDestination);
        }
        aGameModel.logMove(this);
    }

    @Override
    public boolean isLegalized() {
        if (aSource instanceof SuitStack && aDestination instanceof WorkingStack) {
            return aGameModel.canMoveFromSuitStacktoWorkingStack(aSource, aDestination);
        }
        if (aSource instanceof WorkingStack && aDestination instanceof SuitStack) {
            return aGameModel.canMoveFromWorkingStacktoSuitStack(aSource);
        }
        if (aSource instanceof CardDeck && aDestination instanceof SuitStack) {
            return aGameModel.canMoveFromDiscardtoSuitStack();
        }
        if (aSource instanceof CardDeck && aDestination instanceof WorkingStack) {
            return aGameModel.canMoveFromDiscardtoWorkingStack(aDestination);
        }
        return false;
    }

    @Override
    public void undo() {
        if (aSource instanceof SuitStack && aDestination instanceof WorkingStack) {
            aGameModel.moveFromWorkingStacktoSuitStack(aDestination);
        }
        if (aSource instanceof WorkingStack && aDestination instanceof SuitStack) {
            aGameModel.moveFromSuitStacktoWorkingStack(aDestination, aSource);
        }
        if (aSource instanceof CardDeck && aDestination instanceof SuitStack) {
            aGameModel.undoMoveFromDiscardtoSuitStack(aDestination);
        }
        if (aSource instanceof CardDeck && aDestination instanceof WorkingStack) {
            aGameModel.undoMoveFromDiscardtoWorkingStack(aDestination);
        }
    }
}
