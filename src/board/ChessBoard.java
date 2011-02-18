package board;

import board.piece.Piece;
import board.piece.PieceEnumeration;
import board.piece.PieceFactory;

import java.util.ArrayList;
import java.util.List;

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

    public List<Piece> getPiecesForPlayer(boolean isWhitePlayer) {
        List<Piece> piecesForPlayer = new ArrayList<Piece>();
        for(int x = 0; x < 8; x++) {
            for(int y = 0; y < 8; y++) {
                if(board[x][y] == PieceEnumeration.FREE_SPACE) {
                    //Do nothing   
                } else if(isWhitePlayer && PieceEnumeration.isPieceAWhitePiece(board[x][y])) {
                    piecesForPlayer.add(PieceFactory.getPieceFromByteValue(board[x][y], x, y, isWhitePlayer));
                } else if(!isWhitePlayer && PieceEnumeration.isPieceABlackPiece(board[x][y])) {
                    piecesForPlayer.add(PieceFactory.getPieceFromByteValue(board[x][y], x, y, isWhitePlayer));
                }
            }
        }
        return piecesForPlayer;
    }
}
