package solitaire;

import solitaire.GameModel.CardDeck;
import solitaire.SuitStackManager.SuitStack;

public class OneCardMove implements Move {

    private Location aSource;
    private Location aDestination;

    public OneCardMove(Location pSource, Location pDestination) {
        aSource = pSource;
        aDestination = pDestination;
    }

    @Override
    public void move(GameModel pGameModel) {
        assert isLegalized(pGameModel);
        if (aSource instanceof SuitStack && aDestination instanceof WorkingStack) {
            pGameModel.moveFromSuitStacktoWorkingStack(aSource, aDestination);
        }
        if (aSource instanceof WorkingStack && aDestination instanceof SuitStack) {
            pGameModel.moveFromWorkingStacktoSuitStack(aSource);
        }
        if (aSource instanceof CardDeck && aDestination instanceof SuitStack) {
            pGameModel.moveFromDiscardtoSuitStack();
        }
        if (aSource instanceof CardDeck && aDestination instanceof WorkingStack) {
            pGameModel.moveFromDiscardtoWorkingStack(aDestination);
        }
        pGameModel.logMove(this);
    }

    @Override
    public boolean isLegalized(GameModel pGameModel) {
        if (aSource instanceof SuitStack && aDestination instanceof WorkingStack) {
            return pGameModel.canMoveFromSuitStacktoWorkingStack(aSource, aDestination);
        }
        if (aSource instanceof WorkingStack && aDestination instanceof SuitStack) {
            return pGameModel.canMoveFromWorkingStacktoSuitStack(aSource);
        }
        if (aSource instanceof CardDeck && aDestination instanceof SuitStack) {
            return pGameModel.canMoveFromDiscardtoSuitStack();
        }
        if (aSource instanceof CardDeck && aDestination instanceof WorkingStack) {
            return pGameModel.canMoveFromDiscardtoWorkingStack(aDestination);
        }
        return false;
    }

    @Override
    public void undo(GameModel pGameModel) {
        if (aSource instanceof SuitStack && aDestination instanceof WorkingStack) {
            pGameModel.moveFromWorkingStacktoSuitStack(aDestination);
        }
        if (aSource instanceof WorkingStack && aDestination instanceof SuitStack) {
            pGameModel.moveFromSuitStacktoWorkingStack(aDestination, aSource);
        }
        if (aSource instanceof CardDeck && aDestination instanceof SuitStack) {
            pGameModel.undoMoveFromDiscardtoSuitStack(aDestination);
        }
        if (aSource instanceof CardDeck && aDestination instanceof WorkingStack) {
            pGameModel.undoMoveFromDiscardtoWorkingStack(aDestination);
        }
    }

}
