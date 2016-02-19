package solitaire;

public interface PlayStrategy {
    void move(GameModel pGameModel);
    boolean hasNextMove(GameModel pGameModel);
}
