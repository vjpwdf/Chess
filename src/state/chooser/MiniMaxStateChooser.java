package state.chooser;

import board.move.ChessMove;
import board.piece.Piece;
import state.State;
import state.StateEngine;
import state.StateNode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: vincent
 * Date: 04/03/11
 * Time: 3:50 PM
 * To change this template use File | Settings | File Templates.
 */
public class MiniMaxStateChooser implements StateChooser {
    private StateNode moveNode;

    /**
     * Gets the next state based upon your current state
     * @param state state to get future state from
     * @param whitePlayer whether the player is white or black
     * @return the next state based upon your current state
     */
    @Override
    public State chooseNextStateBasedOnCurrentState(StateNode state, boolean whitePlayer) {
        for (int i = 1; i < 4; i++) {
            state.setChildrenStates(new ArrayList<StateNode>());
            try {
                MiniMax(state, i, i, whitePlayer);
            } catch (Exception e) {
                return null;
            }
        }
        return moveNode.getState();
    }

    /**
     * ID-Minimax algorithm that chooses the next state
     * @param node current node
     * @param depth current depth of minimax
     * @param maxDepth the initial depth passed
     * @param whitePlayer whether to do this for white or black player
     * @return the heuristic of a terminal state
     * @throws Exception if can't generate next states
     */
    public int MiniMax(StateNode node, int depth, int maxDepth, boolean whitePlayer) throws Exception {
        int depthOfState = StateEngine.getDepthOfState(node);
        if(depthOfState < maxDepth && (node.getChildrenStates() == null || node.getChildrenStates().isEmpty())) {
            StateEngine.generateFutureStates(node, depthOfState%2==0?whitePlayer:!whitePlayer, node.getParent()==null?null:node.getParent().getState().getMove());
        }
        if (node.getChildrenStates() == null || node.getChildrenStates().isEmpty()) {
            if(isCheckmate(depth, node, depthOfState, whitePlayer)) {
                if(depth == maxDepth) {
                    throw new Exception("Checkmate");
                }
                if(depthOfState % 2 == 0) {
                    return Integer.MIN_VALUE;
                } else {
                    return Integer.MAX_VALUE;
                }
            }
            return StateEngine.getHeuristicOfState(node, whitePlayer);
        }
        int max;
        if(depthOfState % 2 == 0) {
            max = Integer.MIN_VALUE;
        } else {
            max = Integer.MAX_VALUE;
        }
        int temp = Integer.MIN_VALUE+1;
        List<StateNode> childrenStates = node.getChildrenStates();
        Collections.shuffle(childrenStates);
        for (StateNode child : childrenStates) {
            int miniMaxOfChildNode = MiniMax(child, depth - 1, maxDepth, whitePlayer);

            if(depthOfState % 2 == 0) {
                max = Math.max(max, miniMaxOfChildNode);
            } else {
                max = Math.min(max, miniMaxOfChildNode);
            }

            if(temp != max && depth == maxDepth) {
                moveNode = child;
            }
            temp = max;
        }
        return max;
    }

    /**
     * Checks to see if the state is checkmate
     * @param depth depth of minimax
     * @param node node to check to see if is checkmate
     * @param depthOfState depth of the state
     * @param whitePlayer whether the state is for white or blackplayer
     * @return true if the state is checkmate
     */
    private boolean isCheckmate(int depth, StateNode node, int depthOfState, boolean whitePlayer) {
        if(depth!=0) {
            return true;
        } else {
            List<Piece> piecesForPlayer = node.getState().getChessBoard().getPiecesForPlayer(depthOfState % 2 == 0 ? whitePlayer : !whitePlayer);
            for (Piece piece : piecesForPlayer) {
                List<ChessMove> validPieceMoves = piece.getValidPieceMoves(piece, null, node.getState().getChessBoard(), node.getParent() == null ? null : node.getParent().getState().getMove());
                if(!validPieceMoves.isEmpty()) {
                    return false;
                }
            }

        }
        return true;
    }
}
