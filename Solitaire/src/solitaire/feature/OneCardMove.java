package solitaire.feature;

import solitaire.GameModel.GameModel;
import solitaire.GameModel.GameModel.CardDeck;
import solitaire.internal.Location;
import solitaire.internal.WorkingStack;
import solitaire.internal.SuitStackManager.SuitStack;

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
    public boolean  move() {
        boolean success = aGameModel. move(aSource, aDestination);
        if (success)
        {
        	 aGameModel.logMove(this);
        	 return true;
        }
        return false;
    }

    @Override
    public boolean undo() {
    	return aGameModel. undo(aSource, aDestination);
    }
}
