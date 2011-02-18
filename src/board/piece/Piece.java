package board.piece;

import board.ChessBoard;
import board.move.ChessMove;
import client.java.Move;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: vincent
 * Date: Feb 14, 2011
 * Time: 7:34:09 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class Piece {
    private PiecePosition position;
    private boolean isWhitePlayer;

    public PiecePosition getPosition() {
        return position;
    }

    public void setPosition(PiecePosition position) {
        this.position = position;
    }

    public boolean isWhitePlayer() {
        return isWhitePlayer;
    }

    public void setWhitePlayer(boolean whitePlayer) {
        isWhitePlayer = whitePlayer;
    }

    public abstract List<ChessMove> getValidPieceMoves(Piece chessPiece, List<Piece> oponentsChessPieces, ChessBoard chessBoard);
}
