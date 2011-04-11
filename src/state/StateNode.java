package state;

import java.util.List;

/**
 * User: vincent
 * Date: Feb 14, 2011
 * Time: 7:29:17 PM
 */
public class StateNode {
    private StateNode parent;
    private State state;
    private List<StateNode> childrenStates;

    /**
     * Gets the parent state node
     * @return the parent state node
     */
    public StateNode getParent() {
        return parent;
    }

    /**
     * Sets the parent state node
     * @param parent the parent state node
     */
    public void setParent(StateNode parent) {
        this.parent = parent;
    }

    /**
     * Gets the state of the node
     * @return the state of the node
     */
    public State getState() {
        return state;
    }

    /**
     * Sets the state of this node
     * @param state the state of this node
     */
    public void setState(State state) {
        this.state = state;
    }

    /**
     * Gets a list of children state nodes
     * @return a list of children state nodes
     */
    public List<StateNode> getChildrenStates() {
        return childrenStates;
    }

    /**
     * Sets a list of children state nodes for the current state node
     * @param childrenStates a list of children state nodes for the current state node
     */
    public void setChildrenStates(List<StateNode> childrenStates) {
        this.childrenStates = childrenStates;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StateNode stateNode = (StateNode) o;

        if (state != null ? !state.equals(stateNode.state) : stateNode.state != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return state != null ? state.hashCode() : 0;
    }
}
