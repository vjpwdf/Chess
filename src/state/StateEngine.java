package state;

import board.BoardConverter;
import board.ChessBoard;
import board.move.ChessMove;
import board.move.ChessMoveBuilder;
import board.move.MoveTracker;
import board.piece.Piece;
import board.piece.PieceEnumeration;
import board.piece.PieceMover;
import board.piece.PiecePosition;
import client.java.AI;
import state.chooser.StateChooser;

import java.util.ArrayList;
import java.util.List;

/**
 * User: vincent
 * Date: Feb 14, 2011
 * Time: 7:29:07 PM
 */
public class StateEngine {
    private StateNode rootState;

    /**
     * Sets the root state
     *
     * @param rootState the root state node
     */
    public void setRootState(StateNode rootState) {
        this.rootState = rootState;
    }

    /**
     * Sets the current byte board
     *
     * @param board byte board to set of root state
     */
    public void setCurrentBoard(char[][] board) {
        rootState.getState().setChessBoard(BoardConverter.convertCharBoardToByteBoard(board));
    }

    /**
     * Gets the next state to enter into based upon the state chooser
     *
     * @param stateChooser  chooser that chooses the next state
     * @param isWhitePlayer whether this client is white or black
     * @return the next chess move to execute
     */
    public ChessMove getNextStateFromStateChooser(StateChooser stateChooser, boolean isWhitePlayer) {
        rootState.getState().setMove(null);
        rootState.setChildrenStates(new ArrayList<StateNode>());
        rootState.setParent(null);
        State state = stateChooser.chooseNextStateBasedOnCurrentState(rootState, isWhitePlayer);
        return state == null ? null : state.getMove();
    }

    /**
     * Generates future states from the passed state
     *
     * @param state         the state to generate the future states of
     * @param isWhitePlayer whether to generate move of black or white
     * @param lastMove      the last move executed that brought it to this state
     */
    public static void generateFutureStates(StateNode state, boolean isWhitePlayer, ChessMove lastMove) {
        List<Piece> chessPieces = state.getState().getChessBoard().getPiecesForPlayer(isWhitePlayer);
        List<Piece> opponentsChessPieces = state.getState().getChessBoard().getPiecesForPlayer(!isWhitePlayer);
        List<ChessMove> allValidChessPieceMoves = new ArrayList<ChessMove>();
        lastMove = convertLastMoveFromServer(lastMove);
        getAllValidChessPieceMoves(state, lastMove, chessPieces, opponentsChessPieces, allValidChessPieceMoves);
//        addCastelingIfPossible(state, chessPieces, allValidChessPieceMoves, isWhitePlayer);
        addCastelingIfPossible(state, allValidChessPieceMoves, isWhitePlayer);
        buildNewStatesFromMoves(allValidChessPieceMoves, state.getState().getChessBoard(), state);
    }

    private static void addCastelingIfPossible(StateNode state, List<ChessMove> allValidChessPieceMoves, boolean whitePlayer) {
        byte[][] board = state.getState().getChessBoard().getBoard();
        if (whitePlayer) {
            if (!pieceHasMovedFrom(state, 'e', 0)) {
                if (!pieceHasMovedFrom(state, 'h', 0) && board[6][0] == PieceEnumeration.FREE_SPACE && board[5][0] == PieceEnumeration.FREE_SPACE) {
                    allValidChessPieceMoves.add(ChessMoveBuilder.buildChessMoveForCasteling(new PiecePosition(4, 0), new PiecePosition(7, 0)));
                }
                if (!pieceHasMovedFrom(state, 'a', 0) && board[1][0] == PieceEnumeration.FREE_SPACE && board[2][0] == PieceEnumeration.FREE_SPACE && board[3][0] == PieceEnumeration.FREE_SPACE) {
                    allValidChessPieceMoves.add(ChessMoveBuilder.buildChessMoveForCasteling(new PiecePosition(4, 0), new PiecePosition(0, 0)));
                }
            }
        } else {
            if (!pieceHasMovedFrom(state, 'e', 7)) {
                if (!pieceHasMovedFrom(state, 'h', 7) && board[6][7] == PieceEnumeration.FREE_SPACE && board[5][7] == PieceEnumeration.FREE_SPACE) {
                    allValidChessPieceMoves.add(ChessMoveBuilder.buildChessMoveForCasteling(new PiecePosition(4, 7), new PiecePosition(7, 7)));
                }
                if (!pieceHasMovedFrom(state, 'a', 7) && board[1][7] == PieceEnumeration.FREE_SPACE && board[2][7] == PieceEnumeration.FREE_SPACE && board[3][7] == PieceEnumeration.FREE_SPACE) {
                    allValidChessPieceMoves.add(ChessMoveBuilder.buildChessMoveForCasteling(new PiecePosition(4, 7), new PiecePosition(0, 7)));
                }
            }
        }
    }

    private static boolean pieceHasMovedFrom(StateNode state, char fromFile, int fromRank) {
        List<ChessMove> chessMoveList = new ArrayList<ChessMove>(MoveTracker.allMoves);
        StateNode iterator = state;
        while(iterator.getParent() != null) {
            chessMoveList.add(state.getState().getMove());
            iterator = iterator.getParent();
        }
        for (ChessMove move : chessMoveList) {
            if (move.getFromFile() == fromFile && move.getFromRank() == fromRank) {
                return true;
            }
        }
        return false;
    }

    /**
     * See if player can castel
     *
     * @param state                   current state
     * @param chessPieces             list of chess pieces
     * @param allValidChessPieceMoves all valid chess moves this far
     * @param isWhitePlayer           indicator if white or black player
     */
    private static void addCastelingIfPossible(StateNode state, List<Piece> chessPieces, List<ChessMove> allValidChessPieceMoves, boolean isWhitePlayer) {
        List<client.java.Piece> rookPieces = new ArrayList<client.java.Piece>();
        client.java.Piece kingPiece = null;
        byte[][] board = state.getState().getChessBoard().getBoard();
        if (AI.pieces == null) {
            return;
        }
        for (client.java.Piece piece : AI.pieces) {
            if (isWhitePlayer) {
                if ((((char) piece.getType()) == 'R' || ((char) piece.getType()) == 'K') && piece.getHasMoved() == 0 && piece.getOwner() == 0) {
                    if (((char) piece.getType()) == 'K') {
                        kingPiece = piece;
                    } else {
                        rookPieces.add(piece);
                    }
                }
            } else {
                if ((((char) piece.getType()) == 'R' || ((char) piece.getType()) == 'K') && piece.getHasMoved() == 0 && piece.getOwner() == 1) {
                    if (((char) piece.getType()) == 'K') {
                        kingPiece = piece;
                    } else {
                        rookPieces.add(piece);
                    }
                }
            }
        }
        if (kingPiece == null || rookPieces.isEmpty()) {
            return;
        }
        if (isWhitePlayer) {
            for (client.java.Piece rookPiece : rookPieces) {
                if (rookPiece.getFile() == 8 && board[6][0] == PieceEnumeration.FREE_SPACE && board[5][0] == PieceEnumeration.FREE_SPACE) {
                    allValidChessPieceMoves.add(ChessMoveBuilder.buildChessMoveForCasteling(new PiecePosition(4, 0), new PiecePosition(7, 0)));
                }
                if (rookPiece.getFile() == 0 && board[1][0] == PieceEnumeration.FREE_SPACE && board[2][0] == PieceEnumeration.FREE_SPACE && board[3][0] == PieceEnumeration.FREE_SPACE) {
                    allValidChessPieceMoves.add(ChessMoveBuilder.buildChessMoveForCasteling(new PiecePosition(4, 0), new PiecePosition(0, 0)));
                }
            }
        } else {
            for (client.java.Piece rookPiece : rookPieces) {
                if (rookPiece.getFile() == 8 && board[6][7] == PieceEnumeration.FREE_SPACE && board[5][7] == PieceEnumeration.FREE_SPACE) {
                    allValidChessPieceMoves.add(ChessMoveBuilder.buildChessMoveForCasteling(new PiecePosition(4, 7), new PiecePosition(7, 7)));
                }
                if (rookPiece.getFile() == 0 && board[1][7] == PieceEnumeration.FREE_SPACE && board[2][7] == PieceEnumeration.FREE_SPACE && board[3][7] == PieceEnumeration.FREE_SPACE) {
                    allValidChessPieceMoves.add(ChessMoveBuilder.buildChessMoveForCasteling(new PiecePosition(4, 7), new PiecePosition(0, 7)));
                }
            }
        }
    }

    /**
     * Converts the last move from the server into a readable chess move (for performance reasons)
     *
     * @param lastMove last move from the server
     * @return a readable chess move
     */
    private static ChessMove convertLastMoveFromServer(ChessMove lastMove) {
        if (AI.moves != null && AI.moves.length > 0) {
            lastMove = ChessMoveBuilder.convertMoveToChessMove(AI.moves[0]);
        }
        if (lastMove != null) {
            MoveTracker.allMoves.add(lastMove);
        }
        return lastMove;
    }

    /**
     * Gets all valid chess piece moves
     *
     * @param state                   state to get the chess board from
     * @param lastMove                last move required to get to the state passed
     * @param chessPieces             all of the piece on the current board
     * @param opponentsChessPieces    all of the opponents pieces on the current board
     * @param allValidChessPieceMoves all valid chess piece moves that can be performed
     */
    private static void getAllValidChessPieceMoves(StateNode state, ChessMove lastMove, List<Piece> chessPieces, List<Piece> opponentsChessPieces, List<ChessMove> allValidChessPieceMoves) {
        for (Piece chessPiece : chessPieces) {
            List<ChessMove> validChessPieceMoves = chessPiece.getValidPieceMoves(chessPiece, opponentsChessPieces, state.getState().getChessBoard(), lastMove);
            if (validChessPieceMoves != null) {
                allValidChessPieceMoves.addAll(validChessPieceMoves);
            }
        }
    }

    /**
     * Builds new states from a list of moves passed and a state
     *
     * @param allValidChessPieceMoves all valid chess piece moves to perform
     * @param chessBoard              the board to perform the moves on
     * @param state                   the state to perform the moves on
     */
    private static void buildNewStatesFromMoves(List<ChessMove> allValidChessPieceMoves, ChessBoard chessBoard, StateNode state) {
        if (state.getChildrenStates() == null) {
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

    /**
     * Converts the chess board and move into a state node
     *
     * @param board     the chess board
     * @param chessMove the chess move
     * @return a state node containing the chess board and move
     */
    public static StateNode convertBoardToState(ChessBoard board, ChessMove chessMove) {
        StateNode stateNode = new StateNode();
        State state = new State();
        state.setMove(chessMove);
        state.setChessBoard(board);
        stateNode.setState(state);
        return stateNode;
    }

    /**
     * Checks to see if the chess board passed is check by counting the number of kings.  If a king was captured, then the state will need to be removed
     *
     * @param chessBoard board to check for check
     * @return whether the passed board is in check
     */
    public static boolean isCheckState(ChessBoard chessBoard) {
        byte[][] board = chessBoard.getBoard();
        int kingCount = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (PieceEnumeration.isBlackKing(board[i][j]) || PieceEnumeration.isWhiteKing(board[i][j])) {
                    kingCount++;
                }
            }
        }
        return kingCount != 2;
    }
}
