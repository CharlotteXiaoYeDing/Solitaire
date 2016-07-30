package solitaire.feature;

import solitaire.GameModel.GameModel;
import solitaire.internal.Card;
import solitaire.internal.Location;

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
    public boolean move() {
    	  boolean success = aGameModel.move(aSource,  aDestination,aCard);
          if (success)
          {
          	 aGameModel.logMove(this);
          	 return true;
          }
          return false;
    }

    @Override
    public boolean undo() {
    	return aGameModel.undo(aSource,  aDestination,aCard);
    }

}
