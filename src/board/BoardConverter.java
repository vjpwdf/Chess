package board;

import board.piece.PieceEnumeration;

/**
 * Created by IntelliJ IDEA.
 * User: vincent
 * Date: 17-Feb-2011
 * Time: 3:51:24 PM
 */
public class BoardConverter {
    /**
     * Converts the character board displayed into a byte board for efficiency purposes
     * @param board the board to convert
     * @return the a chess board object with a byte representation of the character board
     */
    public static ChessBoard convertCharBoardToByteBoard(char[][] board) {
        ChessBoard chessBoard = new ChessBoard();
        byte byteBoard[][] = new byte[8][8];

        for(int x = 0; x < 8; x++) {
            for(int y = 0; y < 8; y++) {
                byteBoard[y][7-x] = PieceEnumeration.getByteFromCharacter(board[x][y]);
            }
        }

        chessBoard.setBoard(byteBoard);
        return chessBoard;
    }
}
