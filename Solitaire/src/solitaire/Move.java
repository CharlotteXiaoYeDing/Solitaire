package solitaire;

public interface Move {
    
    void move();
    boolean isLegalized();
    void undo();

}
