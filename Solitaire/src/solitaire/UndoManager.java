package solitaire;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class UndoManager implements Move {

    Stack<Move> aHistory = new Stack<>();

    public void addMove(Move pMove) {
        aHistory.add(pMove);
    }

    public UndoManager() {
    }

    @Override
    public boolean isLegalized(GameModel pGameModel) {
        return aHistory.isEmpty();
    }

    public void undo(GameModel pGameModel) {
        assert isLegalized(pGameModel);
        aHistory.pop().undo(pGameModel);
    }

    @Override
    public void move(GameModel pGameModel) {
        throw new UnsupportedOperationException();

    }

}
