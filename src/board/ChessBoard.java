package board;

/**
 * Created by IntelliJ IDEA.
 * User: vincent
 * Date: Feb 14, 2011
 * Time: 7:02:59 PM
 * To change this template use File | Settings | File Templates.
 */
public class ChessBoard {
    byte board[][] = new byte[8][8];

    public byte[][] getBoard() {
        return board;
    }

    public void setBoard(byte[][] board) {
        this.board = board;
    }
}
