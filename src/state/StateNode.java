package state;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: vincent
 * Date: Feb 14, 2011
 * Time: 7:29:17 PM
 * To change this template use File | Settings | File Templates.
 */
public class StateNode {
    private StateNode parent;
    private List<StateNode> childrenStates;

    public StateNode getParent() {
        return parent;
    }

    public void setParent(StateNode parent) {
        this.parent = parent;
    }

    public List<StateNode> getChildrenStates() {
        return childrenStates;
    }

    public void setChildrenStates(List<StateNode> childrenStates) {
        this.childrenStates = childrenStates;
    }
}
