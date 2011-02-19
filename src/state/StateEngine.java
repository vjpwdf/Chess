package state;

import board.BoardConverter;
import board.ChessBoard;
import board.move.ChessMove;
import board.move.ChessMoveBuilder;
import board.piece.Piece;
import board.piece.PieceEnumeration;
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

    /**
     * Returns the root state that the state engine is currently in
     * @return root state
     */
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
        lastMove = convertLastMoveFromServer(lastMove);
        getAllValidChessPieceMoves(state, lastMove, chessPieces, opponentsChessPieces, allValidChessPieceMoves);
        buildNewStatesFromMoves(allValidChessPieceMoves, state.getState().getChessBoard(), state);
    }

    private static ChessMove convertLastMoveFromServer(ChessMove lastMove) {
        if(AI.moves != null && AI.moves.length > 0) {
            lastMove = ChessMoveBuilder.convertMoveToChessMove(AI.moves[0]);
        }
        return lastMove;
    }

    private static void getAllValidChessPieceMoves(StateNode state, ChessMove lastMove, List<Piece> chessPieces, List<Piece> opponentsChessPieces, List<ChessMove> allValidChessPieceMoves) {
        for (Piece chessPiece : chessPieces) {
            List<ChessMove> validChessPieceMoves = chessPiece.getValidPieceMoves(chessPiece, opponentsChessPieces, state.getState().getChessBoard(), lastMove);
            if (validChessPieceMoves != null) {
                allValidChessPieceMoves.addAll(validChessPieceMoves);
            }
        }
    }

    private static void buildNewStatesFromMoves(List<ChessMove> allValidChessPieceMoves, ChessBoard chessBoard, StateNode state) {
        if(state.getChildrenStates() == null) {
            state.setChildrenStates(new ArrayList<StateNode>());
        }
        for (ChessMove validChessPieceMove : allValidChessPieceMoves) {
            State futureState = PieceMover.generateNewStateWithMove(chessBoard, validChessPieceMove);
            StateNode futureStateNode = new StateNode();
            futureStateNode.setParent(state);
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

    public static boolean isCheckState(ChessBoard chessBoard) {
        byte[][] board = chessBoard.getBoard();
        int kingCount = 0;
        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++) {
                if(PieceEnumeration.isBlackKing(board[i][j]) || PieceEnumeration.isWhiteKing(board[i][j])) {
                    kingCount++;
                }
            }
        }
        return kingCount != 2;
    }
}
