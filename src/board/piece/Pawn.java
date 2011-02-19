package board.piece;

import board.ChessBoard;
import board.move.ChessMove;
import board.move.ChessMoveBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * User: vincent
 * Date: Feb 14, 2011
 * Time: 7:33:02 PM
 */
public class Pawn extends Piece {

    /**
     * Gets all valid piece moves for the pawn
     * @param chessPiece piece to get moves for
     * @param opponentsChessPieces all opponents chess pieces
     * @param chessBoard the current chess board
     * @param lastMove the last move performed
     * @return a list of valid chess moves for this piece
     */
    @Override
    public List<ChessMove> getValidPieceMoves(Piece chessPiece, List<Piece> opponentsChessPieces, ChessBoard chessBoard, ChessMove lastMove) {
        List<ChessMove> initialDoubleJumpMove = getInitialDoubleJumpMoveIfPossible(chessPiece, chessBoard);
        List<ChessMove> singleJumpMoves = getSingleJumpMovesIfPossible(chessPiece, chessBoard);
        List<ChessMove> captureMoves = getCaptureMoves(chessPiece, chessBoard);
        List<ChessMove> captureEnPassantMoves = captureEnPassantMoves(chessPiece, chessBoard, lastMove);

        List<ChessMove> allValidPawnMoves = new ArrayList<ChessMove>();
        allValidPawnMoves.addAll(initialDoubleJumpMove);
        allValidPawnMoves.addAll(singleJumpMoves);
        allValidPawnMoves.addAll(captureMoves);
        allValidPawnMoves.addAll(captureEnPassantMoves);

        convertPawnsOnEndOfBoardToPromotions(chessPiece, allValidPawnMoves);

        return allValidPawnMoves;
    }

    /**
     * Converts all states in which the pawn is at the end of the board and promotes them to one of the following: Q, N, R, B
     * @param chessPiece pawn piece
     * @param allValidPawnMoves all valid pawn moves this far
     */
    private void convertPawnsOnEndOfBoardToPromotions(Piece chessPiece, List<ChessMove> allValidPawnMoves) {
        List<ChessMove> validPawnMovesCopy = new ArrayList<ChessMove>(allValidPawnMoves);
        PiecePosition piecePosition = chessPiece.getPosition();
        for (ChessMove validPawnMove : validPawnMovesCopy) {
            if ((chessPiece.isWhitePlayer() && validPawnMove.getToRank() == 7) || (!chessPiece.isWhitePlayer() && validPawnMove.getToRank() == 0)) {
                allValidPawnMoves.add(ChessMoveBuilder.buildChessMoveFromPositions(piecePosition, new PiecePosition(validPawnMove.getToFileByte(), validPawnMove.getToRank()), 'R'));
                allValidPawnMoves.add(ChessMoveBuilder.buildChessMoveFromPositions(piecePosition, new PiecePosition(validPawnMove.getToFileByte(), validPawnMove.getToRank()), 'N'));
                allValidPawnMoves.add(ChessMoveBuilder.buildChessMoveFromPositions(piecePosition, new PiecePosition(validPawnMove.getToFileByte(), validPawnMove.getToRank()), 'B'));
                allValidPawnMoves.add(ChessMoveBuilder.buildChessMoveFromPositions(piecePosition, new PiecePosition(validPawnMove.getToFileByte(), validPawnMove.getToRank()), 'Q'));
                allValidPawnMoves.remove(validPawnMove);
            }
        }
    }

    /**
     * Gets all possible captures via en passant
     * @param chessPiece pawn piece
     * @param chessBoard the current chess board
     * @param lastMove last move performed, must have been a double jump pawn move
     * @return all possible captures via en passant
     */
    private List<ChessMove> captureEnPassantMoves(Piece chessPiece, ChessBoard chessBoard, ChessMove lastMove) {
        List<ChessMove> captureMoves = new ArrayList<ChessMove>();
        if (lastMove == null) {
            return captureMoves;
        }

        PiecePosition pawnPosition = chessPiece.getPosition();
        byte[][] board = chessBoard.getBoard();
        if (chessPiece.isWhitePlayer()) {
            if (board[lastMove.getToFileByte()][lastMove.getToRank()] != PieceEnumeration.P2_PAWN) {
                return captureMoves;
            }
            if (lastMove.getFromRank() == 6 && lastMove.getToRank() == 4) {
                if (pawnPosition.equals(new PiecePosition(lastMove.getFromFileByte() - 1, lastMove.getToRank()))) {
                    captureMoves.add(ChessMoveBuilder.buildChessMoveFromPositions(pawnPosition, new PiecePosition(pawnPosition.getX() + 1, pawnPosition.getY() + 1), true));
                }
                if (pawnPosition.equals(new PiecePosition(lastMove.getFromFileByte() + 1, lastMove.getToRank()))) {
                    captureMoves.add(ChessMoveBuilder.buildChessMoveFromPositions(pawnPosition, new PiecePosition(pawnPosition.getX() - 1, pawnPosition.getY() + 1), true));
                }
            }
        } else {
            if (board[lastMove.getToFileByte()][lastMove.getToRank()] != PieceEnumeration.P1_PAWN) {
                return captureMoves;
            }
            if (lastMove.getFromRank() == 1 && lastMove.getToRank() == 3) {
                if (pawnPosition.equals(new PiecePosition(lastMove.getFromFileByte() - 1, lastMove.getToRank()))) {
                    captureMoves.add(ChessMoveBuilder.buildChessMoveFromPositions(pawnPosition, new PiecePosition(pawnPosition.getX() + 1, pawnPosition.getY() - 1), true));
                }
                if (pawnPosition.equals(new PiecePosition(lastMove.getFromFileByte() + 1, lastMove.getToRank()))) {
                    captureMoves.add(ChessMoveBuilder.buildChessMoveFromPositions(pawnPosition, new PiecePosition(pawnPosition.getX() - 1, pawnPosition.getY() - 1), true));
                }
            }
        }
        return captureMoves;
    }

    /**
     * Gets all direct capture moves for the pawn
     * @param chessPiece the pawn piece
     * @param chessBoard the current board
     * @return all direct capture moves for the pawn
     */
    private List<ChessMove> getCaptureMoves(Piece chessPiece, ChessBoard chessBoard) {
        List<ChessMove> captureMoves = new ArrayList<ChessMove>();
        PiecePosition pawnPosition = chessPiece.getPosition();
        byte[][] board = chessBoard.getBoard();
        if (chessPiece.isWhitePlayer()) {
            if (pawnPosition.getX() != 0 && PieceEnumeration.isPieceABlackPiece(board[pawnPosition.getX() - 1][pawnPosition.getY() + 1])) {
                captureMoves.add(ChessMoveBuilder.buildChessMoveFromPositions(pawnPosition, new PiecePosition(pawnPosition.getX() - 1, pawnPosition.getY() + 1)));
            }
            if (pawnPosition.getX() != 7 && PieceEnumeration.isPieceABlackPiece(board[pawnPosition.getX() + 1][pawnPosition.getY() + 1])) {
                captureMoves.add(ChessMoveBuilder.buildChessMoveFromPositions(pawnPosition, new PiecePosition(pawnPosition.getX() + 1, pawnPosition.getY() + 1)));
            }
        } else if (!chessPiece.isWhitePlayer()) {
            if (pawnPosition.getX() != 0 && PieceEnumeration.isPieceAWhitePiece(board[pawnPosition.getX() - 1][pawnPosition.getY() - 1])) {
                captureMoves.add(ChessMoveBuilder.buildChessMoveFromPositions(pawnPosition, new PiecePosition(pawnPosition.getX() - 1, pawnPosition.getY() - 1)));
            }
            if (pawnPosition.getX() != 7 && PieceEnumeration.isPieceAWhitePiece(board[pawnPosition.getX() + 1][pawnPosition.getY() - 1])) {
                captureMoves.add(ChessMoveBuilder.buildChessMoveFromPositions(pawnPosition, new PiecePosition(pawnPosition.getX() + 1, pawnPosition.getY() - 1)));
            }
        }
        return captureMoves;
    }

    /**
     * Gets all single jump moves for the pawn
     * @param chessPiece the pawn piece
     * @param chessBoard the current board
     * @return all single jump moves for the pawn
     */
    private List<ChessMove> getSingleJumpMovesIfPossible(Piece chessPiece, ChessBoard chessBoard) {
        List<ChessMove> singleJumpMoves = new ArrayList<ChessMove>();
        PiecePosition pawnPosition = chessPiece.getPosition();
        byte[][] board = chessBoard.getBoard();
        if (chessPiece.isWhitePlayer()) {
            if (board[pawnPosition.getX()][pawnPosition.getY() + 1] == PieceEnumeration.FREE_SPACE) {
                singleJumpMoves.add(ChessMoveBuilder.buildChessMoveFromPositions(pawnPosition, new PiecePosition(pawnPosition.getX(), pawnPosition.getY() + 1)));
            }
        } else if (!chessPiece.isWhitePlayer()) {
            if (board[pawnPosition.getX()][pawnPosition.getY() - 1] == PieceEnumeration.FREE_SPACE) {
                singleJumpMoves.add(ChessMoveBuilder.buildChessMoveFromPositions(pawnPosition, new PiecePosition(pawnPosition.getX(), pawnPosition.getY() - 1)));
            }
        }
        return singleJumpMoves;
    }

    /**
     * Gets all valid double jump moves for the pawn
     * @param chessPiece the pawn piece
     * @param chessBoard the current chess board
     * @return all valid double jump moves for the pawn
     */
    private List<ChessMove> getInitialDoubleJumpMoveIfPossible(Piece chessPiece, ChessBoard chessBoard) {
        List<ChessMove> doubleJumpMove = new ArrayList<ChessMove>();
        PiecePosition pawnPosition = chessPiece.getPosition();
        byte[][] board = chessBoard.getBoard();
        if (chessPiece.isWhitePlayer() && pawnPosition.equals(new PiecePosition(pawnPosition.getX(), 1))) {
            if (board[pawnPosition.getX()][pawnPosition.getY() + 1] == PieceEnumeration.FREE_SPACE &&
                    board[pawnPosition.getX()][pawnPosition.getY() + 2] == PieceEnumeration.FREE_SPACE) {
                doubleJumpMove.add(ChessMoveBuilder.buildChessMoveFromPositions(pawnPosition, new PiecePosition(pawnPosition.getX(), pawnPosition.getY() + 2)));
            }
        } else if (!chessPiece.isWhitePlayer() && pawnPosition.equals(new PiecePosition(pawnPosition.getX(), 6))) {
            if (board[pawnPosition.getX()][pawnPosition.getY() - 1] == PieceEnumeration.FREE_SPACE &&
                    board[pawnPosition.getX()][pawnPosition.getY() - 2] == PieceEnumeration.FREE_SPACE) {
                doubleJumpMove.add(ChessMoveBuilder.buildChessMoveFromPositions(pawnPosition, new PiecePosition(pawnPosition.getX(), pawnPosition.getY() - 2)));
            }
        }
        return doubleJumpMove;
    }
}
