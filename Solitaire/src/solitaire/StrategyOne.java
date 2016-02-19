package solitaire;

import java.util.Stack;

import solitaire.GameModel.CardDeck;
import solitaire.SuitStackManager.SuitStack;
import solitaire.WorkingStackManager.Workingstack;

public class StrategyOne implements PlayStrategy {

    Stack<Move> possibleMove;
    
    public StrategyOne() {

    }

    @Override
    public void move(GameModel pGameModel) {
        assert hasNextMove(pGameModel);
        collectPossibleMove(pGameModel);
        possibleMove.pop().move(pGameModel);
    }
    
    public boolean hasNextMove(GameModel pGameModel)
    {
        collectPossibleMove(pGameModel);
        return !possibleMove.isEmpty();
    }
    
    public void collectPossibleMove(GameModel pGameModel)
    {
        possibleMove = new Stack<>();
        if ((new DiscardMove()).isLegalized(pGameModel))
        {
            possibleMove.push(new DiscardMove());
        }
        for (Workingstack aWorkingStack: Workingstack.values())
        {
            Stack<Card> aStack= pGameModel.viewWorkingStack(aWorkingStack);
            for (Workingstack aWorkingStack1: Workingstack.values())
            {
                if (aWorkingStack == aWorkingStack1)
                {
                    break;
                }
                for (Card aCard: aStack)
                {
                    if (new MultipleCardsMove(aWorkingStack, aWorkingStack1, aCard).isLegalized(pGameModel))
                    {
                        possibleMove.push(new MultipleCardsMove(aWorkingStack, aWorkingStack1, aCard));
                    }
                }
            }
        }
        for (Workingstack aWorkingStack: Workingstack.values())
        {
            if (new OneCardMove(CardDeck.DISCARD, aWorkingStack).isLegalized(pGameModel))
            {
                possibleMove.push(new OneCardMove(CardDeck.DISCARD, aWorkingStack));
            }
        }
        for (Workingstack aWorkingStack: Workingstack.values())
        {
            for (SuitStack aSuitStack: SuitStack.values())
            {
                if (new OneCardMove(aWorkingStack, aSuitStack).isLegalized(pGameModel))
                {
                    possibleMove.push(new OneCardMove(aWorkingStack, aSuitStack));
                }
                if (new OneCardMove(aSuitStack, aWorkingStack).isLegalized(pGameModel))
                {
                    possibleMove.push(new OneCardMove(aSuitStack, aWorkingStack));
                }
            }
        }
        for (SuitStack aSuitStack: SuitStack.values())
        {
            if (new OneCardMove(CardDeck.DISCARD, aSuitStack).isLegalized(pGameModel))
            {
                possibleMove.push(new OneCardMove(CardDeck.DISCARD, aSuitStack));
            }
        }
        
    }
}
