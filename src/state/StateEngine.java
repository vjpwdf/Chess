package state;

import board.BoardConverter;
import board.ChessBoard;
import board.move.ChessMove;
import board.move.ChessMoveBuilder;
import board.piece.Piece;
import board.piece.PieceMover;
import client.java.AI;
import client.java.Move;
import state.chooser.StateChooser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: vincent
 * Date: Feb 14, 2011
 * Time: 7:29:07 PM
 * To change this template use File | Settings | File Templates.
 */
public class StateEngine {
    private StateNode rootState;

    public StateNode getRootState() {
        return rootState;
    }

    public void setRootState(StateNode rootState) {
        this.rootState = rootState;
    }

    public void setCurrentBoard(char[][] board) {
        rootState.getState().setChessBoard(BoardConverter.convertCharBoardToByteBoard(board));
    }

    public ChessMove getNextStateFromStateChooser(StateChooser stateChooser, boolean isWhitePlayer) {
        rootState.getState().setMove(null);
        rootState.setChildrenStates(new ArrayList<StateNode>());
        return stateChooser.chooseNextStateBasedOnCurrentState(rootState, isWhitePlayer).getMove();
    }

    public static void generateFutureStates(StateNode state, boolean isWhitePlayer, ChessMove lastMove) {
        List<Piece> chessPieces = state.getState().getChessBoard().getPiecesForPlayer(isWhitePlayer);
        List<Piece> opponentsChessPieces = state.getState().getChessBoard().getPiecesForPlayer(!isWhitePlayer);
        List<ChessMove> allValidChessPieceMoves = new ArrayList<ChessMove>();
        try {
            lastMove = ChessMoveBuilder.convertMoveToChessMove(AI.moves[0]);
        } catch (Exception e) {

        }
        for (Piece chessPiece : chessPieces) {
            List<ChessMove> validChessPieceMoves = chessPiece.getValidPieceMoves(chessPiece, opponentsChessPieces, state.getState().getChessBoard(), lastMove);
            if (validChessPieceMoves != null) {
                allValidChessPieceMoves.addAll(validChessPieceMoves);
            }
        }
        buildNewStatesFromMoves(allValidChessPieceMoves, state.getState().getChessBoard(), state);
        System.out.println("");
    }

    private static void buildNewStatesFromMoves(List<ChessMove> allValidChessPieceMoves, ChessBoard chessBoard, StateNode state) {
        if(state.getChildrenStates() == null) {
            state.setChildrenStates(new ArrayList<StateNode>());
        }
        for (ChessMove validChessPieceMove : allValidChessPieceMoves) {
            State futureState = PieceMover.generateNewStateWithMove(chessBoard, validChessPieceMove);
            StateNode futureStateNode = new StateNode();
            futureStateNode.setState(futureState);
            state.getChildrenStates().add(futureStateNode);
        }
    }

    public static StateNode convertBoardToState(ChessBoard board, ChessMove chessMove) {
        StateNode stateNode = new StateNode();
        State state = new State();
        state.setMove(chessMove);
        state.setChessBoard(board);
        stateNode.setState(state);
        return stateNode;
    }
}
