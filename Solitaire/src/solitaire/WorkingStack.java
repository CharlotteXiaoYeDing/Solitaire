package solitaire;

import java.util.Iterator;
import java.util.Stack;

public class WorkingStack implements Iterable<CardView> {
    private final Stack<CardView> aWorkingStack = new Stack<>();

    public WorkingStack(Deck deck, int num) {
        for (int i = 0; i < num; i++) {
            aWorkingStack.add(new CardView(deck.draw()));
        }
        aWorkingStack.get(num-1).setVisibility(true);
        
    }

    public void add(CardView pCard) {
        aWorkingStack.add(pCard);

    }

    public CardView draw() {
        assert !aWorkingStack.isEmpty();
        return aWorkingStack.pop();
    }

    public CardView view() {
        assert !aWorkingStack.isEmpty();
        return aWorkingStack.peek();
    }

    public boolean isEmpty() {
        return aWorkingStack.isEmpty();
    }

    @Override
    public Iterator<CardView> iterator() {
        return aWorkingStack.iterator();
    }
}
