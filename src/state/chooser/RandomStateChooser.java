package state.chooser;

import state.State;
import state.StateEngine;
import state.StateNode;

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
        return state.getChildrenStates().get(Math.abs(random.nextInt()%state.getChildrenStates().size())).getState();
    }
}
