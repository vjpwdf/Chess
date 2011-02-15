package state;

import board.ChessBoard;

/**
 * Created by IntelliJ IDEA.
 * User: vincent
 * Date: Feb 14, 2011
 * Time: 7:29:43 PM
 * To change this template use File | Settings | File Templates.
 */
public class State {
    private ChessBoard chessBoard;

    public ChessBoard getChessBoard() {
        return chessBoard;
    }

    public void setChessBoard(ChessBoard chessBoard) {
        this.chessBoard = chessBoard;
    }
}
