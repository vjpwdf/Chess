package board.piece;

import board.ChessBoard;
import board.move.ChessMove;

import java.util.List;

/**
 * User: vincent
 * Date: Feb 14, 2011
 * Time: 7:34:09 PM
 */
public abstract class Piece {
    private PiecePosition position;
    private boolean isWhitePlayer;

    /**
     * Returns the pieces position on the board
     * @return the piece position on the board
     */
    public PiecePosition getPosition() {
        return position;
    }

    /**
     * Sets the piece position on the board
     * @param position the position on the board
     */
    public void setPosition(PiecePosition position) {
        this.position = position;
    }

    /**
     * Gets whether this piece is a white piece or black
     * @return whether this piece is a white piece or black
     */
    public boolean isWhitePlayer() {
        return isWhitePlayer;
    }

    /**
     * Sets whether this piece is a white piece or black
     * @param whitePlayer whether this piece is a white piece or black
     */
    public void setWhitePlayer(boolean whitePlayer) {
        isWhitePlayer = whitePlayer;
    }

    /**
     * Gets all valid piece moves for the piece
     * @param chessPiece piece to get moves for
     * @param opponentsChessPieces all opponents chess pieces
     * @param chessBoard the current chess board
     * @param lastMove the last move performed
     * @return a list of valid chess moves for this piece
     */
    public abstract List<ChessMove> getValidPieceMoves(Piece chessPiece, List<Piece> opponentsChessPieces, ChessBoard chessBoard, ChessMove lastMove);
}
