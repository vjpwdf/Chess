package state.chooser;

import state.State;
import state.StateEngine;

/**
 * Created by IntelliJ IDEA.
 * User: vincent
 * Date: 17-Feb-2011
 * Time: 4:10:58 PM
 */
public class RandomStateChooser implements StateChooser {
    @Override
    public State chooseNextStateBasedOnCurrentState(State state, boolean isWhitePlayer) {
        StateEngine.generateFutureStates(state, isWhitePlayer);
        return null;
    }
}
