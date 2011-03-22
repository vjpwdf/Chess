package state;

import board.BoardConverter;
import board.ChessBoard;
import board.move.ChessMove;
import board.move.ChessMoveBuilder;
import board.move.MoveTracker;
import board.piece.*;
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
     * Gets the root state of the state engine
     *
     * @return the root state of the state engine
     */
    public StateNode getRootState() {
        return rootState;
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
            chessMoveList.add(iterator.getState().getMove());
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
                if (kingPosition.getX() == opponentsMove.getToFileByte() && kingPosition.getY() == opponentsMove.getToRank()) {
                    return true;
                }
            }
        }
        return false;
    }

    private static PiecePosition getKingPositionForPlayer(boolean whitePlayer, byte[][] board) {
        PiecePosition kingPosition = new PiecePosition();
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                if (whitePlayer) {
                    if (PieceEnumeration.isWhiteKing(board[x][y])) {
                        kingPosition.setX(x);
                        kingPosition.setY(y);
                    }
                } else {
                    if (PieceEnumeration.isBlackKing(board[x][y])) {
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

    /**
     * Gets the depth of the state node
     *
     * @param stateNode state node to get the depth of
     * @return the depth of the state node
     */
    public static int getDepthOfState(StateNode stateNode) {
        StateNode tempNode = stateNode;
        int depth = 0;
        while (tempNode.getParent() != null) {
            tempNode = tempNode.getParent();
            depth++;
        }
        return depth;
    }

    /**
     * Gets the heuristic of the state node being evaluated
     *
     * @param node        state node being evaluated
     * @param whitePlayer whether to evaluate as white or black player
     * @return the heuristic of the state node being evaluated
     */
    public static int getHeuristicOfState(StateNode node, boolean whitePlayer) {
        int hueristic = 0;
//        if (isDraw(node)) {
//            return 0;
//        }
        List<Piece> piecesForPlayer = node.getState().getChessBoard().getPiecesForPlayer(whitePlayer);
        List<Piece> opponentsPieces = node.getState().getChessBoard().getPiecesForPlayer(!whitePlayer);
        for (Piece piece : piecesForPlayer) {
            hueristic += piece.getPieceValue();
        }
        for (Piece piece : opponentsPieces) {
            hueristic -= piece.getPieceValue();
        }
        return hueristic;
    }

    /**
     * Checks to see if the node passed is a draw state
     *
     * @param node node to see if the node passed is a draw state
     * @return true if the node passed is a draw state otherwise false
     */
    private static boolean isDraw(StateNode node) {
        if (noCapturesInLast50Moves(node) || !noPawnMovedInLast50Moves(node) || isKingVsKing(node) || isKingVsKingAnd(node, Bishop.class) || isKingVsKingAnd(node, Knight.class) || isKingAndBishopVsKingAndBishop(node)) {
            return true;
        }
        return false;
    }

    /**
     * Checks to see if the state is king and bishop vs king and bishop
     * @param node to see if the state is king and bishop vs king and bishop
     * @return true if the state is king and bishop vs king and bishop
     */
    private static boolean isKingAndBishopVsKingAndBishop(StateNode node) {
        if (node.getState().getChessBoard().getPieceCount() != 4) {
            return false;
        }
        List<Piece> whitePieces = node.getState().getChessBoard().getPiecesForPlayer(true);
        List<Piece> blackPieces = node.getState().getChessBoard().getPiecesForPlayer(true);
        boolean whiteBishopOnBlackSquare = false;
        boolean blackBishopOnBlackSquare = false;
        if(whitePieces.size() != 2 || blackPieces.size() != 2) {
            return false;
        }
        int bishopCounter = 0;
        for (Piece blackPiece : blackPieces) {
            if (blackPiece instanceof Bishop) {
                bishopCounter++;
                blackBishopOnBlackSquare = blackPiece.getPosition().getX()%2==blackPiece.getPosition().getY()%2;
            }
        }
        for (Piece whitePiece : whitePieces) {
            if (whitePiece instanceof Bishop) {
                bishopCounter++;
                whiteBishopOnBlackSquare = whitePiece.getPosition().getX()%2 == whitePiece.getPosition().getY()%2;
            }
        }
        return bishopCounter==2 && whiteBishopOnBlackSquare == blackBishopOnBlackSquare;
    }

    /**
     * Checks to see if the state is King vs a specific class (such as knight or bishop)
     * @param node  to see if the state is King vs a specific class (such as knight or bishop)
     * @param clazz a specific class (such as knight or bishop)
     * @return true if the situation is king vs king and another piece
     */
    private static boolean isKingVsKingAnd(StateNode node, Class clazz) {
        if (node.getState().getChessBoard().getPieceCount() != 3) {
            return false;
        }
        List<Piece> whitePieces = node.getState().getChessBoard().getPiecesForPlayer(true);
        List<Piece> blackPieces = node.getState().getChessBoard().getPiecesForPlayer(true);
        if (whitePieces.size() == 1) {
            for (Piece blackPiece : blackPieces) {
                if (clazz.isInstance(blackPiece)) {
                    return true;
                }
            }
        } else {
            for (Piece whitePiece : whitePieces) {
                if (clazz.isInstance(whitePiece)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Checks to see if the node is King vs King
     * @param node node to check to see if is king vs king
     * @return true if node is king vs king
     */
    private static boolean isKingVsKing(StateNode node) {
        return node.getState().getChessBoard().getPieceCount() == 2;
    }

    /**
     * Checks to see if there has been any pawn moves in the last 50 moves
     *
     * @param node to see if there has been any pawn moves in the last 50 moves
     * @return if there has been any pawn moves in the last 50 moves
     */
    private static boolean noPawnMovedInLast50Moves(StateNode node) {
        List<Piece> whitePieces = node.getState().getChessBoard().getPiecesForPlayer(true);
        List<Piece> blackPieces = node.getState().getChessBoard().getPiecesForPlayer(false);
        for (Piece piece : whitePieces) {
            if (piece instanceof Pawn) {
                if (pawnMovedInLast50Moves(piece, node, true)) {
                    return false;
                }
            }
        }
        for (Piece piece : blackPieces) {
            if (piece instanceof Pawn) {
                if (pawnMovedInLast50Moves(piece, node, false)) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Checks to see if a pawn has moved in the last 50 moves
     *
     * @param pawn        the pawn
     * @param node        to see if a pawn has moved in the last 50 moves
     * @param whitePlayer for black or white
     * @return if a pawn has moved in the last 50 moves
     */
    private static boolean pawnMovedInLast50Moves(Piece pawn, StateNode node, boolean whitePlayer) {
        List<ChessBoard> allChessBoardsForNode = new ArrayList<ChessBoard>(MoveTracker.allChessBoards);
        StateNode iterator = node;
        while (iterator.getParent() != null) {
            allChessBoardsForNode.add(iterator.getState().getChessBoard());
            iterator = iterator.getParent();
            if (allChessBoardsForNode.size() == 50) {
                break;
            }
        }
        if (allChessBoardsForNode.size() < 50) {
            return false;
        }
        for (int i = 0; i < 50; i++) {
            ChessBoard chessBoard = allChessBoardsForNode.get(allChessBoardsForNode.size() - (i + 1));
            byte[][] board = chessBoard.getBoard();
            if (whitePlayer) {
                if (board[pawn.getPosition().getX()][pawn.getPosition().getY()] != PieceEnumeration.P1_PAWN) {
                    return true;
                }
            }
            if (!whitePlayer) {
                if (board[pawn.getPosition().getX()][pawn.getPosition().getY()] != PieceEnumeration.P2_PAWN) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Checks to see if there has been any captures in the last 50 moves
     *
     * @param node to see if there has been any captures in the last 50 moves
     * @return if there has been any captures in the last 50 moves
     */
    private static boolean noCapturesInLast50Moves(StateNode node) {
        List<ChessBoard> allChessBoardsForNode = new ArrayList<ChessBoard>(MoveTracker.allChessBoards);
        StateNode iterator = node;
        while (iterator.getParent() != null) {
            allChessBoardsForNode.add(iterator.getState().getChessBoard());
            iterator = iterator.getParent();
            if (allChessBoardsForNode.size() == 50) {
                break;
            }
        }
        if (allChessBoardsForNode.size() < 50) {
            return false;
        }
        int chessBoardPieces = allChessBoardsForNode.get(0).getPieceCount();
        for (int i = 0; i < 50; i++) {
            ChessBoard chessBoard = allChessBoardsForNode.get(allChessBoardsForNode.size() - (i + 1));
            int temp = chessBoard.getPieceCount();
            if (chessBoardPieces != temp) {
                return false;
            }
            chessBoardPieces = temp;
        }
        return true;
    }
}
