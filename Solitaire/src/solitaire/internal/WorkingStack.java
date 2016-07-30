package solitaire.internal;

import java.util.Iterator;
import java.util.Stack;

public class WorkingStack implements Iterable<Card> {
    private final Stack<Card> aWorkingStack = new Stack<>();
    private int aVisible;

    public WorkingStack(Deck deck, int num) {
        for (int i = 0; i < num; i++) {
            aWorkingStack.add(deck.draw());
        }
        aVisible = num-1;
    }

    public void push(Card pCard) {
        aWorkingStack.add(pCard);

    }

    public Card draw() {
        assert !aWorkingStack.isEmpty();
        if (aVisible == aWorkingStack.size()-1)
        {
        	aVisible--;
        }
        return aWorkingStack.pop();
        
    }

    public Card peek() {
        assert !aWorkingStack.isEmpty();
        return aWorkingStack.peek();
    }

    public boolean isEmpty() {
        return aWorkingStack.isEmpty();
    }

    public boolean getVisibility(Card pCard)
    {
    	int index = aWorkingStack.indexOf(pCard);
    	return aVisible<=index;
    }
    
    @Override
    public Iterator<Card> iterator() {
        return aWorkingStack.iterator();
    }
}
