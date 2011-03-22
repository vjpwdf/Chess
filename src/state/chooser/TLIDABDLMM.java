package state.chooser;

import board.move.ChessMove;
import board.move.MoveTracker;
import board.piece.Piece;
import client.java.AI;
import state.State;
import state.StateEngine;
import state.StateNode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: vincent
 * Date: 22/03/11
 * Time: 11:41 AM
 * To change this template use File | Settings | File Templates.
 */
public class TLIDABDLMM implements StateChooser {
    private StateNode moveNode;
    private StateNode bestMoveNodeFromPreviousIteration;
    private long startTime = 0;
    private long endTime = 0;

    /**
     * Gets the next state based upon your current state
     *
     * @param state       state to get future state from
     * @param whitePlayer whether the player is white or black
     * @return the next state based upon your current state
     */
    @Override
    public State chooseNextStateBasedOnCurrentState(StateNode state, boolean whitePlayer) {
        startTime = System.currentTimeMillis();
        endTime = startTime + getTimeByHeuristic();
        for (int i = 1; i < 20; i++) {
            state.setChildrenStates(new ArrayList<StateNode>());
            try {
                int alpha = Integer.MIN_VALUE;
                int beta = Integer.MAX_VALUE;
                try {
                    ABMiniMax(state, i, i, whitePlayer, alpha, beta);
                    bestMoveNodeFromPreviousIteration = moveNode;
                } catch (OutOfTimeException e) {
                    return bestMoveNodeFromPreviousIteration.getState();
                }
            } catch (CheckmateException e) {
                return null;
            }
        }
        return moveNode.getState();
    }

    /**
     * TLIDABDLMM algorithm that chooses the next state
     *
     * @param node        current node
     * @param depth       current depth of minimax
     * @param maxDepth    the initial depth passed
     * @param whitePlayer whether to do this for white or black player
     * @param alpha       min ab window
     * @param beta        max ab window
     * @return the heuristic of a terminal state
     * @throws CheckmateException
     * @throws OutOfTimeException
     */
    public int ABMiniMax(StateNode node, int depth, int maxDepth, boolean whitePlayer, int alpha, int beta) throws CheckmateException, OutOfTimeException {
        if (System.currentTimeMillis() > endTime) {
            throw new OutOfTimeException();
        }
        int depthOfState = StateEngine.getDepthOfState(node);
        if (depthOfState < maxDepth && (node.getChildrenStates() == null || node.getChildrenStates().isEmpty())) {
            StateEngine.generateFutureStates(node, depthOfState % 2 == 0 ? whitePlayer : !whitePlayer, node.getParent() == null ? null : node.getParent().getState().getMove());
        }
        if (node.getChildrenStates() == null || node.getChildrenStates().isEmpty()) {
            if (isCheckmate(depth, node, depthOfState, whitePlayer)) {
                if (depth == maxDepth) {
                    throw new CheckmateException("Checkmate");
                }
                if (depthOfState % 2 == 0) {
                    return Integer.MIN_VALUE;
                } else {
                    return Integer.MAX_VALUE;
                }
            }
            return StateEngine.getHeuristicOfState(node, whitePlayer);
        }
        int max;
        if (depthOfState % 2 == 0) {
            max = Integer.MIN_VALUE;
        } else {
            max = Integer.MAX_VALUE;
        }
        int temp = Integer.MIN_VALUE + 1;
        Collections.shuffle(node.getChildrenStates());
        List<StateNode> childrenStates = new ArrayList<StateNode>(node.getChildrenStates());
        for (StateNode child : childrenStates) {
            int miniMaxOfChildNode = ABMiniMax(child, depth - 1, maxDepth, whitePlayer, alpha, beta);

            if (depthOfState % 2 == 0) {
                max = Math.max(max, miniMaxOfChildNode);
                alpha = Math.max(alpha, miniMaxOfChildNode);
                if (beta <= alpha) {
                    prune(child);
                    if (temp != max && depth == maxDepth) {
                        moveNode = child;
                    }
                    break;
                }
            } else {
                max = Math.min(max, miniMaxOfChildNode);
                beta = Math.min(beta, miniMaxOfChildNode);
                if (beta <= alpha) {
                    prune(child);
                    if (temp != max && depth == maxDepth) {
                        moveNode = child;
                    }
                    break;
                }
            }

            if (temp != max && depth == maxDepth) {
                moveNode = child;
            }
            temp = max;
        }
        return max;
    }

    private void prune(StateNode child) {
        List<StateNode> childrenStates = child.getParent().getChildrenStates();
        int childNodeIndex = childrenStates.indexOf(child);
        for (int i = childrenStates.size() - 1; i > childNodeIndex; i--) {
            childrenStates.remove(i);
        }
    }

    /**
     * Checks to see if the state is checkmate
     *
     * @param depth        depth of minimax
     * @param node         node to check to see if is checkmate
     * @param depthOfState depth of the state
     * @param whitePlayer  whether the state is for white or blackplayer
     * @return true if the state is checkmate
     */
    private boolean isCheckmate(int depth, StateNode node, int depthOfState, boolean whitePlayer) {
        if (depth != 0) {
            return true;
        } else {
            List<Piece> piecesForPlayer = node.getState().getChessBoard().getPiecesForPlayer(depthOfState % 2 == 0 ? whitePlayer : !whitePlayer);
            for (Piece piece : piecesForPlayer) {
                List<ChessMove> validPieceMoves = piece.getValidPieceMoves(piece, null, node.getState().getChessBoard(), node.getParent() == null ? null : node.getParent().getState().getMove());
                if (!validPieceMoves.isEmpty()) {
                    return false;
                }
            }

        }
        return true;
    }

    public long getTimeByHeuristic() {
        int numberOfMovesPlayed = MoveTracker.allMoves.size();
        if(numberOfMovesPlayed < 10) {
            return (long) (2000);
        } else if(numberOfMovesPlayed < 20) {
            return (long) (AI.playerTimeRemaining * 0.02)*1000;
        } else if(numberOfMovesPlayed < 30) {
            return (long) (AI.playerTimeRemaining * 0.05)*1000;
        } else if(numberOfMovesPlayed < 40) {
            return (long) (AI.playerTimeRemaining * 0.04)*1000;
        } else {
            return (long) (AI.playerTimeRemaining * 0.01)*1000;
        }
    }
}
