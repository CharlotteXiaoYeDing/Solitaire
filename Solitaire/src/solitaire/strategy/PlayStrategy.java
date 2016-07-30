package solitaire.strategy;

import solitaire.GameModel.GameModel;

public interface PlayStrategy {
    void move(GameModel pGameModel);
    boolean hasNextMove(GameModel pGameModel);
}
