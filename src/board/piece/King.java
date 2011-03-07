package board.piece;

import board.ChessBoard;
import board.move.ChessMove;
import board.move.ChessMoveBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * User: vincent
 * Date: Feb 14, 2011
 * Time: 7:36:45 PM
 */
public class King extends Piece {
    /**
     * Gets all valid piece moves for the king
     * @param chessPiece piece to get moves for
     * @param opponentsChessPieces all opponents chess pieces
     * @param chessBoard the current chess board
     * @param lastMove the last move performed
     * @return a list of valid chess moves for this piece
     */
    @Override
    public List<ChessMove> getValidPieceMoves(Piece chessPiece, List<Piece> opponentsChessPieces, ChessBoard chessBoard, ChessMove lastMove) {
        List<ChessMove> validKingMoves = new ArrayList<ChessMove>();
        PiecePosition piecePosition = chessPiece.getPosition();
        byte[][] board = chessBoard.getBoard();
        addValidMoveIfAvailable(1, 1, piecePosition, board, chessPiece, validKingMoves);
        addValidMoveIfAvailable(-1, -1, piecePosition, board, chessPiece, validKingMoves);
        addValidMoveIfAvailable(1, -1, piecePosition, board, chessPiece, validKingMoves);
        addValidMoveIfAvailable(-1, 1, piecePosition, board, chessPiece, validKingMoves);
        addValidMoveIfAvailable(1, 0, piecePosition, board, chessPiece, validKingMoves);
        addValidMoveIfAvailable(-1, 0, piecePosition, board, chessPiece, validKingMoves);
        addValidMoveIfAvailable(0, 1, piecePosition, board, chessPiece, validKingMoves);
        addValidMoveIfAvailable(0, -1, piecePosition, board, chessPiece, validKingMoves);
        return validKingMoves;
    }

    /**
     * Gets the value of the piece for heuristics
     * @return the value of the piece for heuristics
     */
    @Override
    public int getPieceValue() {
        return 0;
    }

    /**
     * Adds a valid move for the king if possible
     * @param xInc x advance
     * @param yInc y advance
     * @param piecePosition the kings position on the board
     * @param board the current board
     * @param chessPiece king piece
     * @param validKingMoves all valid king moves so far
     */
    private void addValidMoveIfAvailable(int xInc, int yInc, PiecePosition piecePosition, byte[][] board, Piece chessPiece, List<ChessMove> validKingMoves) {
        if(piecePosition.getX() + xInc < 0 || piecePosition.getX() +xInc > 7 || piecePosition.getY() +yInc < 0 || piecePosition.getY() + yInc > 7) {
            return;
        }
        if(chessPiece.isWhitePlayer()) {
            if(board[piecePosition.getX()+xInc][piecePosition.getY()+yInc] == PieceEnumeration.FREE_SPACE || PieceEnumeration.isPieceABlackPiece(board[piecePosition.getX()+xInc][piecePosition.getY()+yInc])) {
                validKingMoves.add(ChessMoveBuilder.buildChessMoveFromPositions(piecePosition, new PiecePosition(piecePosition.getX()+xInc, piecePosition.getY()+yInc)));
            }
        } else {
            if(board[piecePosition.getX()+xInc][piecePosition.getY()+yInc] == PieceEnumeration.FREE_SPACE || PieceEnumeration.isPieceAWhitePiece(board[piecePosition.getX()+xInc][piecePosition.getY()+yInc])) {
                validKingMoves.add(ChessMoveBuilder.buildChessMoveFromPositions(piecePosition, new PiecePosition(piecePosition.getX()+xInc, piecePosition.getY()+yInc)));
            }
        }
    }
}
