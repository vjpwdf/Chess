package state;

import board.ChessBoard;
import board.move.ChessMove;

/**
 * User: vincent
 * Date: Feb 14, 2011
 * Time: 7:29:43 PM
 */
public class State {
    private ChessMove move;
    private ChessBoard chessBoard;

    /**
     * Gets the move that brought the state to where it is
     * @return the move that brought the state to where it is
     */
    public ChessMove getMove() {
        return move;
    }

    /**
     * Sets the move that brought the state to where it is
     * @param move the move that brought the state to where it is
     */
    public void setMove(ChessMove move) {
        this.move = move;
    }

    /**
     * Gets the current chess board of this state
     * @return the chess board of this state
     */
    public ChessBoard getChessBoard() {
        return chessBoard;
    }

    /**
     * Sets the chess board of this state
     * @param chessBoard the chess board of this state
     */
    public void setChessBoard(ChessBoard chessBoard) {
        this.chessBoard = chessBoard;
    }
}
