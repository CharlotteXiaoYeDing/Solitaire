package solitaire.feature;

import java.util.Stack;

import solitaire.GameModel.GameModel;

public class UndoManager implements Move
{

	Stack<Move> aHistory = new Stack<>();
	GameModel aGameModel;

	public void addMove(Move pMove)
	{
		aHistory.add(pMove);
	}

	public UndoManager(GameModel pGameModel)
	{
		aGameModel = pGameModel;
	}

	public boolean undo()
	{
		if (!aHistory.isEmpty())
		{
			aHistory.pop().undo();
		}
		return false;
	}

	@Override
	public boolean move()
	{
		throw new UnsupportedOperationException();
	}
}
