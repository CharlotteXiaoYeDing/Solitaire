package solitaire;

import java.util.Stack;

public class UndoManager implements Move {

    Stack<Move> aHistory = new Stack<>();
    GameModel aGameModel;

    public void addMove(Move pMove) {
        aHistory.add(pMove);
    }

    public UndoManager(GameModel pGameModel) {
        aGameModel = pGameModel;
    }

    @Override
    public boolean isLegalized() {
        return aHistory.isEmpty();
    }

    public void undo() {
        assert isLegalized();
        aHistory.pop().undo();
    }

    @Override
    public void move() {
        throw new UnsupportedOperationException();
    }
}
