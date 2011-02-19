package board.move;

import board.piece.PiecePosition;
import client.java.Move;

/**
 * Created by IntelliJ IDEA.
 * User: vincent
 * Date: 17-Feb-2011
 * Time: 5:05:19 PM
 */
public class ChessMoveBuilder {
    /**
     * Builds a chess move from two positions
     * @param x from position
     * @param y to position
     * @return a valid chess move
     */
    public static ChessMove buildChessMoveFromPositions(PiecePosition x, PiecePosition y) {
        ChessMove chessMove = new ChessMove();
        chessMove.setFromRank((byte) x.getY());
        chessMove.setFromFile((char) (x.getX()+97));
        chessMove.setToRank((byte) y.getY());
        chessMove.setToFile((char) (y.getX()+97));
        return chessMove;
    }

    /**
     * Builds a chess move from two positions
     * @param x from position
     * @param y to position
     * @param promotion if the piece was promoted during the move
     * @return a valid chess move
     */
    public static ChessMove buildChessMoveFromPositions(PiecePosition x, PiecePosition y, char promotion) {
        ChessMove chessMove = new ChessMove();
        chessMove.setFromRank((byte) x.getY());
        chessMove.setFromFile((char) (x.getX()+97));
        chessMove.setToRank((byte) y.getY());
        chessMove.setToFile((char) (y.getX()+97));
        chessMove.setPromotion(promotion);
        return chessMove;
    }

    /**
     * Builds a chess move from two positions
     * @param x from position
     * @param y to position
     * @param capturedViaEnPassant if the piece was captured via en passant
     * @return a valid chess move
     */
    public static ChessMove buildChessMoveFromPositions(PiecePosition x, PiecePosition y, boolean capturedViaEnPassant) {
        ChessMove chessMove = new ChessMove();
        chessMove.setFromRank((byte) x.getY());
        chessMove.setFromFile((char) (x.getX()+97));
        chessMove.setToRank((byte) y.getY());
        chessMove.setToFile((char) (y.getX()+97));
        chessMove.setCapturedViaEnPassant(capturedViaEnPassant);
        return chessMove;
    }

    /**
     * Converts a server move into a readable chess move (to avoid making excessive server calls)
     * @param move move to be converted
     * @return readable chess move
     */
    public static ChessMove convertMoveToChessMove(Move move) {
        ChessMove chessMove = new ChessMove();
        chessMove.setFromFile((char) (move.getFromFile()+96));
        chessMove.setFromRank((byte) (move.getFromRank()-1));
        chessMove.setToFile((char) (move.getToFile()+96));
        chessMove.setToRank((byte) (move.getToRank()-1));
        return chessMove;
    }
}
