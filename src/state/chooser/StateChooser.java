package state.chooser;

import state.State;

/**
 * Created by IntelliJ IDEA.
 * User: vincent
 * Date: Feb 14, 2011
 * Time: 7:38:04 PM
 * To change this template use File | Settings | File Templates.
 */
public interface StateChooser {
    public State chooseNextStateBasedOnCurrentState(State state, boolean whitePlayer);
}
