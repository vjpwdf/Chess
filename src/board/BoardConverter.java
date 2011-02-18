package board;

import board.piece.PieceEnumeration;

/**
 * Created by IntelliJ IDEA.
 * User: vincent
 * Date: 17-Feb-2011
 * Time: 3:51:24 PM
 */
public class BoardConverter {
    public static ChessBoard convertCharBoardToByteBoard(char[][] board) {
        ChessBoard chessBoard = new ChessBoard();
        byte byteBoard[][] = new byte[8][8];

        for(int x = 0; x < 8; x++) {
            for(int y = 0; y < 8; y++) {
                byteBoard[y][x] = PieceEnumeration.getByteFromCharacter(board[x][y]);
            }
        }

        chessBoard.setBoard(byteBoard);
        return chessBoard;
    }
}
