package solitaire.strategy;

import solitaire.GameModel.GameModel;

/**
 * @author Charlotte
 */
public interface PlayStrategy
{
	void move(GameModel pGameModel);

	boolean hasNextMove(GameModel pGameModel);
}
