package state.chooser;

import state.State;
import state.StateNode;

/**
 * User: vincent
 * Date: Feb 14, 2011
 * Time: 7:38:04 PM
 */
public interface StateChooser {
    /**
     * Gets the next state based upon your current state
     * @param state state to get future state from
     * @param whitePlayer whether the player is white or black
     * @return the next state based upon your current state
     */
    public State chooseNextStateBasedOnCurrentState(StateNode state, boolean whitePlayer);
}
