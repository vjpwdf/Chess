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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        State state = (State) o;

        if (chessBoard != null ? !chessBoard.equals(state.getChessBoard()) : state.chessBoard != null) return false;
        if (move != null ? !move.equals(state.move) : state.move != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = move != null ? move.hashCode() : 0;
        result = 31 * result + (chessBoard != null ? chessBoard.hashCode() : 0);
        return result;
    }
}
