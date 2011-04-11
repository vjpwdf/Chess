package board;

import board.piece.Piece;
import board.piece.PieceEnumeration;
import board.piece.PieceFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * User: vincent
 * Date: Feb 14, 2011
 * Time: 7:02:59 PM
 */
public class ChessBoard {
    byte board[][] = new byte[8][8];

    /**
     * Gets the byte board
     *
     * @return the byte chess board
     */
    public byte[][] getBoard() {
        return board;
    }

    /**
     * Sets the byte chess board
     *
     * @param board the byte chess board
     */
    public void setBoard(byte[][] board) {
        this.board = board;
    }

    /**
     * Returns a list of pieces for the passed player off the current board
     *
     * @param isWhitePlayer whether to get pieces for white or black
     * @return a list of pieces for the passed player off the current board
     */
    public List<Piece> getPiecesForPlayer(boolean isWhitePlayer) {
        List<Piece> piecesForPlayer = new ArrayList<Piece>();
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                if (board[x][y] == PieceEnumeration.FREE_SPACE) {
                    //Do nothing   
                } else if (isWhitePlayer && PieceEnumeration.isPieceAWhitePiece(board[x][y])) {
                    piecesForPlayer.add(PieceFactory.getPieceFromByteValue(board[x][y], x, y, isWhitePlayer));
                } else if (!isWhitePlayer && PieceEnumeration.isPieceABlackPiece(board[x][y])) {
                    piecesForPlayer.add(PieceFactory.getPieceFromByteValue(board[x][y], x, y, isWhitePlayer));
                }
            }
        }
        return piecesForPlayer;
    }

    /**
     * Makes a copy of the chess board object
     *
     * @return a copy of the chess board object
     */
    public ChessBoard copy() {
        ChessBoard chessBoard = new ChessBoard();
        byte copiedBoard[][] = new byte[8][8];
        for (int i = 0; i < 8; i++) {
            System.arraycopy(board[i], 0, copiedBoard[i], 0, 8);
        }
        chessBoard.setBoard(copiedBoard);
        return chessBoard;
    }

    public int getPieceCount() {
        int pieceCount = 0;
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                if (board[x][y] != PieceEnumeration.FREE_SPACE) {
                    pieceCount++;
                }
            }
        }
        return pieceCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ChessBoard board2 = (ChessBoard) o;
        byte[][] board1 = board2.getBoard();

        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                if(board[x][y] != board1[x][y]) {
                    return false;
                }
            }
        }

        return true;
    }

    @Override
    public int hashCode() {
        int hashCode = 0;
        for (int i = 0; i < 8; i++) {
            hashCode += Arrays.toString(board[i]).hashCode() * i;
        }
        return hashCode;
    }
}
