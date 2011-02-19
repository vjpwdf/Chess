package state.chooser;

import state.State;
import state.StateEngine;
import state.StateNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by IntelliJ IDEA.
 * User: vincent
 * Date: 17-Feb-2011
 * Time: 4:10:58 PM
 */
public class RandomStateChooser implements StateChooser {
    @Override
    public State chooseNextStateBasedOnCurrentState(StateNode state, boolean isWhitePlayer) {
        StateEngine.generateFutureStates(state, isWhitePlayer, null);
        Random random = new Random();
        List<StateNode> futureMoves = new ArrayList<StateNode>(state.getChildrenStates());
        for (StateNode futureNode : futureMoves) {
            StateEngine.generateFutureStates(futureNode, !isWhitePlayer, futureNode.getState().getMove());
            List<StateNode> opponentsMoves = new ArrayList<StateNode>(futureNode.getChildrenStates());
            for (StateNode node : opponentsMoves) {
                if(StateEngine.isCheckState(node.getState().getChessBoard())) {
                    futureNode.getParent().getChildrenStates().remove(futureNode);
                }
            }
        }
        return state.getChildrenStates().get(Math.abs(random.nextInt()%state.getChildrenStates().size())).getState();
    }
}
