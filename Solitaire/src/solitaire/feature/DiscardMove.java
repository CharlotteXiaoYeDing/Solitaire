package solitaire.feature;

import solitaire.GameModel.GameModel;

public class DiscardMove implements Move
{

	GameModel aGameModel;

	public DiscardMove(GameModel pGameModel)
	{
		aGameModel = pGameModel;
	}

	@Override
	public boolean move()
	{
		if (aGameModel.discard())
		{
			aGameModel.logMove(this);
			return true;
		}
		return false;

	}

	@Override
	public boolean undo()
	{
		return aGameModel.undoDiscard();
	}

}
