package state;

import board.BoardConverter;
import board.move.ChessMove;
import board.piece.Piece;
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
        return stateChooser.chooseNextStateBasedOnCurrentState(rootState.getState(), isWhitePlayer).getMove();
    }

    public static void generateFutureStates(State state, boolean isWhitePlayer) {
        List<Piece> chessPieces = state.getChessBoard().getPiecesForPlayer(isWhitePlayer);
        List<Piece> opponentsChessPieces = state.getChessBoard().getPiecesForPlayer(!isWhitePlayer);
        List<ChessMove> allValidChessPieceMoves = new ArrayList<ChessMove>();
        for (Piece chessPiece : chessPieces) {
            List<ChessMove> validChessPieceMoves = chessPiece.getValidPieceMoves(chessPiece, opponentsChessPieces, state.getChessBoard());
            if (validChessPieceMoves != null) {
                allValidChessPieceMoves.addAll(validChessPieceMoves);
            }
        }
        
    }
}
