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
 * Date: 04/03/11
 * Time: 3:50 PM
 * To change this template use File | Settings | File Templates.
 */
public class MiniMaxStateChooser implements StateChooser {
    private StateNode moveNode;
    private Random random = new Random();
    @Override
    public State chooseNextStateBasedOnCurrentState(StateNode state, boolean whitePlayer) {
        for (int i = 1; i < 5; i++) {
            state.setChildrenStates(new ArrayList<StateNode>());
            MiniMax(state, i, i, whitePlayer);
        }
        return moveNode.getState();
    }

    public int MiniMax(StateNode node, int depth, int maxDepth, boolean whitePlayer) {
        int depthOfState = StateEngine.getDepthOfState(node);
        if(depthOfState < maxDepth && (node.getChildrenStates() == null || node.getChildrenStates().isEmpty())) {
            StateEngine.generateFutureStates(node, depthOfState%2==0?whitePlayer:!whitePlayer, node.getParent()==null?null:node.getParent().getState().getMove());
        }
        if (node.getChildrenStates() == null || node.getChildrenStates().isEmpty()) {
            return StateEngine.getHeuristicOfState(node, whitePlayer);
        }
        int max = Integer.MIN_VALUE;
        int temp = Integer.MIN_VALUE+1;
        List<StateNode> childrenStates = node.getChildrenStates();
        for (StateNode child : childrenStates) {
            int miniMaxOfChildNode = -MiniMax(child, depth - 1, maxDepth, whitePlayer);

            if(miniMaxOfChildNode == max && depth == maxDepth && random.nextInt()%5 == 0) {
                moveNode = child;
            }
            max = Math.max(max, miniMaxOfChildNode);
            if(temp != max && depth == maxDepth) {
                moveNode = child;
            }
            temp = max;
        }
        return max;
    }
}
