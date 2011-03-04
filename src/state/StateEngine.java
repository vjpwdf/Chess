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
        addCastelingIfPossible(state, allValidChessPieceMoves, isWhitePlayer);
        buildNewStatesFromMoves(allValidChessPieceMoves, state.getState().getChessBoard(), state, isWhitePlayer);
    }

    /**
     * Adds casteling to a list of moves if it is possible
     *
     * @param state                   state to check for casteling of
     * @param allValidChessPieceMoves list of current valid chess piece moves
     * @param whitePlayer             whether the state is for black or white
     */
    private static void addCastelingIfPossible(StateNode state, List<ChessMove> allValidChessPieceMoves, boolean whitePlayer) {
        ChessBoard chessBoard = state.getState().getChessBoard();
        byte[][] board = chessBoard.getBoard();
        if (whitePlayer) {
            if (!pieceHasMovedFrom(state, 'e', 0) && PieceEnumeration.isWhiteKing(board[4][0])) {
                if (!pieceHasMovedFrom(state, 'h', 0) && board[6][0] == PieceEnumeration.FREE_SPACE && board[5][0] == PieceEnumeration.FREE_SPACE && board[7][0] == PieceEnumeration.P1_ROOK) {
                    ChessMove kingSideCastelMove = ChessMoveBuilder.buildChessMoveForCasteling(new PiecePosition(4, 0), new PiecePosition(7, 0));
                    if (kingDoesNotPassThroughCheck(chessBoard, kingSideCastelMove, 'f', 0, whitePlayer)) {
                        allValidChessPieceMoves.add(kingSideCastelMove);
                    }
                }
                if (!pieceHasMovedFrom(state, 'a', 0) && board[1][0] == PieceEnumeration.FREE_SPACE && board[2][0] == PieceEnumeration.FREE_SPACE && board[3][0] == PieceEnumeration.FREE_SPACE && board[0][0] == PieceEnumeration.P1_ROOK) {
                    ChessMove queenSideCastelMove = ChessMoveBuilder.buildChessMoveForCasteling(new PiecePosition(4, 0), new PiecePosition(0, 0));
                    if (kingDoesNotPassThroughCheck(chessBoard, queenSideCastelMove, 'd', 0, whitePlayer)) {
                        allValidChessPieceMoves.add(queenSideCastelMove);
                    }
                }
            }
        } else {
            if (!pieceHasMovedFrom(state, 'e', 7) && PieceEnumeration.isBlackKing(board[4][7])) {
                if (!pieceHasMovedFrom(state, 'h', 7) && board[6][7] == PieceEnumeration.FREE_SPACE && board[5][7] == PieceEnumeration.FREE_SPACE && board[7][7] == PieceEnumeration.P2_ROOK) {
                    ChessMove kingSideCastelMove = ChessMoveBuilder.buildChessMoveForCasteling(new PiecePosition(4, 7), new PiecePosition(7, 7));
                    if (kingDoesNotPassThroughCheck(chessBoard, kingSideCastelMove, 'f', 7, whitePlayer)) {
                        allValidChessPieceMoves.add(kingSideCastelMove);
                    }
                }
                if (!pieceHasMovedFrom(state, 'a', 7) && board[1][7] == PieceEnumeration.FREE_SPACE && board[2][7] == PieceEnumeration.FREE_SPACE && board[3][7] == PieceEnumeration.FREE_SPACE && board[0][7] == PieceEnumeration.P2_ROOK) {
                    ChessMove queenSideCastelMove = ChessMoveBuilder.buildChessMoveForCasteling(new PiecePosition(4, 7), new PiecePosition(0, 7));
                    if (kingDoesNotPassThroughCheck(chessBoard, queenSideCastelMove, 'd', 7, whitePlayer)) {
                        allValidChessPieceMoves.add(queenSideCastelMove);
                    }
                }
            }
        }
    }

    private static boolean kingDoesNotPassThroughCheck(ChessBoard board, ChessMove castelMove, char toFile, int toRank, boolean isWhitePlayer) {
        State state = PieceMover.generateNewStateWithMove(board, castelMove);
        List<Piece> opponentsChessPieces = state.getChessBoard().getPiecesForPlayer(!isWhitePlayer);
        for (Piece opponentsChessPiece : opponentsChessPieces) {
            List<ChessMove> chessMoves = opponentsChessPiece.getValidPieceMoves(opponentsChessPiece, null, state.getChessBoard(), null);
            for (ChessMove chessMove : chessMoves) {
                if (chessMove.getToFile() == toFile && chessMove.getToRank() == toRank) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Get whether piece has moved from a given file or rank
     *
     * @param state    state to check to see if piece has moved from
     * @param fromFile file to check to see if piece moved from
     * @param fromRank rank to check to see if piece moved from
     * @return whether piece has moved from a given file or rank
     */
    private static boolean pieceHasMovedFrom(StateNode state, char fromFile, int fromRank) {
        List<ChessMove> chessMoveList = new ArrayList<ChessMove>(MoveTracker.allMoves);
        StateNode iterator = state;
        while (iterator.getParent() != null) {
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
     * @param whitePlayer             what player to generate opponents moves for
     */
    private static void buildNewStatesFromMoves(List<ChessMove> allValidChessPieceMoves, ChessBoard chessBoard, StateNode state, boolean whitePlayer) {
        if (state.getChildrenStates() == null) {
            state.setChildrenStates(new ArrayList<StateNode>());
        }
        state.getChildrenStates().clear();
        for (ChessMove validChessPieceMove : allValidChessPieceMoves) {
            State futureState = PieceMover.generateNewStateWithMove(chessBoard, validChessPieceMove);
            StateNode futureStateNode = new StateNode();
            futureStateNode.setParent(state);
            futureStateNode.setState(futureState);
            if (!futureStateCausesCheck(futureState, whitePlayer)) {
                state.getChildrenStates().add(futureStateNode);
            }
        }
    }

    private static boolean futureStateCausesCheck(State futureState, boolean whitePlayer) {
        List<Piece> opponentsPieces = futureState.getChessBoard().getPiecesForPlayer(!whitePlayer);
        PiecePosition kingPosition = getKingPositionForPlayer(whitePlayer, futureState.getChessBoard().getBoard());
        for (Piece opponentsPiece : opponentsPieces) {
            List<ChessMove> opponentsMoves = opponentsPiece.getValidPieceMoves(opponentsPiece, null, futureState.getChessBoard(), futureState.getMove());
            for (ChessMove opponentsMove : opponentsMoves) {
                if(kingPosition.getX() == opponentsMove.getToFileByte() && kingPosition.getY() == opponentsMove.getToRank()) {
                    return true;
                }
            }
        }
        return false;
    }

    private static PiecePosition getKingPositionForPlayer(boolean whitePlayer, byte[][] board) {
        PiecePosition kingPosition = new PiecePosition();
        for(int x = 0; x < 8; x++) {
            for(int y = 0; y < 8; y++) {
                if(whitePlayer) {
                    if(PieceEnumeration.isWhiteKing(board[x][y])) {
                        kingPosition.setX(x);
                        kingPosition.setY(y);
                    }
                } else {
                    if(PieceEnumeration.isBlackKing(board[x][y])) {
                        kingPosition.setX(x);
                        kingPosition.setY(y);
                    }
                }
            }
        }
        return kingPosition;
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

    public static int getDepthOfState(StateNode stateNode) {
        StateNode tempNode = stateNode;
        int depth = 0;
        while(tempNode.getParent() != null) {
            tempNode = tempNode.getParent();
            depth++;
        }
        return depth;
    }

    public static int getHeuristicOfState(StateNode node, boolean whitePlayer) {
        int hueristic = 0;
        List<Piece> piecesForPlayer = node.getState().getChessBoard().getPiecesForPlayer(whitePlayer);
        List<Piece> opponentsPieces = node.getState().getChessBoard().getPiecesForPlayer(!whitePlayer);
        for (Piece piece : piecesForPlayer) {
            hueristic -= piece.getPieceValue();
        }
        for (Piece piece : opponentsPieces) {
            hueristic += piece.getPieceValue();
        }
        return hueristic;
    }
}
