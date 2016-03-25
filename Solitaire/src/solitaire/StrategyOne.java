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
        possibleMove.pop().move();
    }

    public boolean hasNextMove(GameModel pGameModel) {
        collectPossibleMove(pGameModel);
        return !possibleMove.isEmpty();
    }

    public void collectPossibleMove(GameModel pGameModel) {
        possibleMove = new Stack<>();
        Move aMove = new DiscardMove(pGameModel);
        if (aMove.isLegalized()) {
            possibleMove.push(aMove);
        }
        for (Workingstack aWorkingStack : Workingstack.values()) {
            Stack<Card> aStack = pGameModel.viewWorkingStack(aWorkingStack);
            for (Workingstack aWorkingStack1 : Workingstack.values()) {
                if (aWorkingStack == aWorkingStack1) {
                    break;
                }
                for (Card aCard : aStack) {
                    aMove = new MultipleCardsMove(aWorkingStack, aWorkingStack1, aCard, pGameModel);
                    if (aMove.isLegalized()) {
                        possibleMove.push(aMove);
                    }
                }
            }
        }
        for (Workingstack aWorkingStack : Workingstack.values()) {
            aMove = new OneCardMove(CardDeck.DISCARD, aWorkingStack, pGameModel);
            if (aMove.isLegalized()) {
                possibleMove.push(aMove);
            }
        }
        for (Workingstack aWorkingStack : Workingstack.values()) {
            for (SuitStack aSuitStack : SuitStack.values()) {
                aMove = new OneCardMove(aWorkingStack, aSuitStack, pGameModel);
                if (aMove.isLegalized()) {
                    possibleMove.push(aMove);
                }
                aMove = new OneCardMove(aSuitStack, aWorkingStack, pGameModel);
                if (aMove.isLegalized()) {
                    possibleMove.push(aMove);
                }
            }
        }
        for (SuitStack aSuitStack : SuitStack.values()) {
            aMove = new OneCardMove(CardDeck.DISCARD, aSuitStack, pGameModel);
            if (aMove.isLegalized()) {
                possibleMove.push(aMove);
            }
        }

    }
}
