package state;

import board.ChessBoard;
import board.move.ChessMove;

/**
 * Created by IntelliJ IDEA.
 * User: vincent
 * Date: Feb 14, 2011
 * Time: 7:29:43 PM
 * To change this template use File | Settings | File Templates.
 */
public class State {
    private ChessMove move;
    private ChessBoard chessBoard;

    public ChessMove getMove() {
        return move;
    }

    public void setMove(ChessMove move) {
        this.move = move;
    }

    public ChessBoard getChessBoard() {
        return chessBoard;
    }

    public void setChessBoard(ChessBoard chessBoard) {
        this.chessBoard = chessBoard;
    }
}
