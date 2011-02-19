package state.chooser;

import state.State;
import state.StateEngine;
import state.StateNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * User: vincent
 * Date: 17-Feb-2011
 * Time: 4:10:58 PM
 */
public class RandomStateChooser implements StateChooser {
    /**
     * Gets the next state based upon your current state by performing a random valid move
     * @param state state to get future state from
     * @param isWhitePlayer whether the player is white or black
     * @return the next state based upon your current state
     */
    @Override
    public State chooseNextStateBasedOnCurrentState(StateNode state, boolean isWhitePlayer) {
        StateEngine.generateFutureStates(state, isWhitePlayer, null);
        Random random = new Random();
        List<StateNode> futureMoves = state.getChildrenStates();
        List<StateNode> validFutureMoves = new ArrayList<StateNode>();
        for (StateNode futureNode : futureMoves) {
            StateEngine.generateFutureStates(futureNode, !isWhitePlayer, futureNode.getState().getMove());
            List<StateNode> opponentsMoves = new ArrayList<StateNode>(futureNode.getChildrenStates());
            boolean shouldKeepState = true;
            for (StateNode node : opponentsMoves) {
                if(StateEngine.isCheckState(node.getState().getChessBoard())) {
                    shouldKeepState = false;
                    break;
                }
            }
            if(shouldKeepState) {
                validFutureMoves.add(futureNode);
            }
        }
        state.setChildrenStates(validFutureMoves);
        if(state.getChildrenStates().size() == 0) {
            return null;
        }
        return state.getChildrenStates().get(Math.abs(random.nextInt()%state.getChildrenStates().size())).getState();
    }
}
