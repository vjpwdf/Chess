package board.move;

import board.piece.PiecePosition;

/**
 * Created by IntelliJ IDEA.
 * User: vincent
 * Date: 17-Feb-2011
 * Time: 5:05:19 PM
 */
public class ChessMoveBuilder {
    public static ChessMove buildChessMoveFromPositions(PiecePosition x, PiecePosition y) {
        ChessMove chessMove = new ChessMove();
        chessMove.setFromRank((byte) x.getY());
        chessMove.setFromFile((char) (x.getX()+97));
        chessMove.setToRank((byte) y.getY());
        chessMove.setToFile((char) (y.getX()+97));
        return chessMove;
    }
}
