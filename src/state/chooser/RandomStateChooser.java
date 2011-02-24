package state.chooser;

import state.State;
import state.StateEngine;
import state.StateNode;

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
        if(state.getChildrenStates().size() == 0) {
            return null;
        }
        return state.getChildrenStates().get(Math.abs(new Random().nextInt()%state.getChildrenStates().size())).getState();
    }
}
