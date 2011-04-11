package state.chooser;

import state.State;
import state.StateNode;

import java.util.Comparator;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: vincent
 * Date: 11/04/11
 * Time: 3:20 PM
 * To change this template use File | Settings | File Templates.
 */
public class StateComparator implements Comparator<StateNode> {
    private Map<State,Integer> historyTable;

    public StateComparator(Map<State, Integer> historyTable) {
        this.historyTable = historyTable;
    }

    @Override
    public int compare(StateNode one, StateNode two) {
        Integer oneValue = getValueForStateNode(one);
        Integer twoValue = getValueForStateNode(two);
        return oneValue.compareTo(twoValue);
    }

    private Integer getValueForStateNode(StateNode node) {
        Integer value = historyTable.get(node.getState());
        if(value == null) {
            return 0;
        }
        return value;
    }
}
