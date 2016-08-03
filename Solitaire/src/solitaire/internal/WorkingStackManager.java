
package solitaire.internal;

import java.util.Stack;

public class WorkingStackManager {

    public enum Workingstack implements Location {
        StackOne, StackTwo, StackThree, StackFour, StackFive, StackSix, StackSeven
    };

    private final WorkingStack[] aWorkingStack = new WorkingStack[Workingstack.values().length];

    public WorkingStackManager(Deck deck) {
        for (int i = 0; i < aWorkingStack.length; i++) {
            aWorkingStack[i] = new WorkingStack(deck, (Workingstack.StackOne.ordinal() + 1 + i));
        }
    }
    
    public Location getLocation(Card pCard)
    {
        int index = 0;
        for (WorkingStack aws: aWorkingStack)
        {
            if (pCard.equals(aws.peek()))
            {
                return WorkingStackManager.Workingstack.values()[index];
            }
            index++;
        }
        return null;
    }

    public boolean canAdd(Card pCard, Workingstack workingstack) {
        assert pCard != null;
        if (aWorkingStack[workingstack.ordinal()].isEmpty()) {
            if (pCard.getRank().ordinal() == Card.Rank.KING.ordinal()) {
                return true;
            }
        } else {
            if ((pCard.getSuit().ordinal() + aWorkingStack[workingstack.ordinal()].peek().getSuit().ordinal())
                    % 2 != 0) {
                if (pCard.getRank()
                        .ordinal() == (aWorkingStack[workingstack.ordinal()].peek().getRank().ordinal() - 1)) {
                    return true;
                }
            }
        }
        return false;
    }

    public void add(Card pCard, Workingstack workingstack) {
        assert canAdd(pCard, workingstack);
        aWorkingStack[workingstack.ordinal()].push(pCard);
    }

    public void addMultiple(Stack<Card> aStack, Workingstack workingstack) {
        assert canAdd(aStack.lastElement(), workingstack);
        while (!aStack.isEmpty()) {
            aWorkingStack[workingstack.ordinal()].push(aStack.pop());
        }
    }
    
    public boolean canDraw(Workingstack workingstack) {
    	if ( aWorkingStack[workingstack.ordinal()].isEmpty())
    	{
    		return false;
    	}
    	Card card = aWorkingStack[workingstack.ordinal()].peek();
        return aWorkingStack[workingstack.ordinal()].getVisibility(card);
    }
    
    public Card draw(Workingstack workingstack) {
        assert canDraw(workingstack);
        Card aCard = aWorkingStack[workingstack.ordinal()].draw();
        return aCard;
    }

    public Stack<Card> drawMultiple(Card pCard, Workingstack workingstack) {
        assert canDrawMultiple(pCard, workingstack);
        Stack<Card> aStack = new Stack<>();
        while (pCard != aWorkingStack[workingstack.ordinal()].peek()) {
            aStack.push(aWorkingStack[workingstack.ordinal()].draw());
        }
        aStack.push(aWorkingStack[workingstack.ordinal()].draw());
        return aStack;
    }

    public boolean canDrawMultiple(Card pCard, Workingstack workingstack) {
    	if (aWorkingStack[workingstack.ordinal()].isEmpty())
    	{
    		return false;
    	}
        return aWorkingStack[workingstack.ordinal()].getVisibility(pCard);
    }
    
    public Card[] getWorkingStack(Workingstack pWorkingstack)
    {

        Card[] cardStack = new Card[pWorkingstack.ordinal()+1];
        int i = 0;
        for(Card aC: aWorkingStack[pWorkingstack.ordinal()])
        {
            cardStack[i] = aC;
            i++;
        }
        return cardStack;
    }
    
    public Card[] getVisibleWorkingStack(Workingstack pWorkingstack)
    {
        Card[] cardStack = new Card[pWorkingstack.ordinal()+1];
        int i = 0;
        for(Card aC: aWorkingStack[pWorkingstack.ordinal()])
        {
        	if (aWorkingStack[pWorkingstack.ordinal()].getVisibility(aC))
        	{
        		cardStack[i] = aC;
                i++;
        	}
        }
        return cardStack;
    }

    @Override
    public String toString() {
        String s = "";
        for (Workingstack pWorkingstack : Workingstack.values()) {
            s = s + pWorkingstack.name() + " ";
            for (Card c : aWorkingStack[pWorkingstack.ordinal()]) {
            	if (aWorkingStack[pWorkingstack.ordinal()].getVisibility(c))
            	{
            		s = s + c.toString() + " ";
            	}
            }
            s = s + "\n";
        }
        return s;
    }

}
