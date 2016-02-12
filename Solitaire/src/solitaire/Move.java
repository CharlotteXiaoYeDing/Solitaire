package solitaire;

public interface Move {
    
    void move(GameModel pGameModel);
    boolean isLegalized(GameModel pGameModel);
    void undo(GameModel pGameModel);

}
