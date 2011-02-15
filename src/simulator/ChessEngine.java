package simulator;

import board.ChessBoard;
import board.piece.PieceEnumeration;

/**
 * Created by IntelliJ IDEA.
 * User: vincent
 * Date: Feb 14, 2011
 * Time: 7:10:04 PM
 * To change this template use File | Settings | File Templates.
 */
public class ChessEngine {
    public void play() {
        ChessBoard chessBoard = buildInitialChessBoard();
    }

    private ChessBoard buildInitialChessBoard() {
        byte[][] board = new byte[][] {
            {PieceEnumeration.P2_ROOK, PieceEnumeration.P2_KNIGHT, PieceEnumeration.P2_BISHOP, PieceEnumeration.P2_KING, PieceEnumeration.P2_QUEEN, PieceEnumeration.P2_BISHOP, PieceEnumeration.P2_KNIGHT, PieceEnumeration.P2_ROOK},
            {PieceEnumeration.P2_PAWN, PieceEnumeration.P2_PAWN, PieceEnumeration.P2_PAWN, PieceEnumeration.P2_PAWN, PieceEnumeration.P2_PAWN, PieceEnumeration.P2_PAWN, PieceEnumeration.P2_PAWN, PieceEnumeration.P2_PAWN},
            {PieceEnumeration.FREE_SPACE, PieceEnumeration.FREE_SPACE, PieceEnumeration.FREE_SPACE, PieceEnumeration.FREE_SPACE, PieceEnumeration.FREE_SPACE, PieceEnumeration.FREE_SPACE, PieceEnumeration.FREE_SPACE, PieceEnumeration.FREE_SPACE},
            {PieceEnumeration.FREE_SPACE, PieceEnumeration.FREE_SPACE, PieceEnumeration.FREE_SPACE, PieceEnumeration.FREE_SPACE, PieceEnumeration.FREE_SPACE, PieceEnumeration.FREE_SPACE, PieceEnumeration.FREE_SPACE, PieceEnumeration.FREE_SPACE},
            {PieceEnumeration.FREE_SPACE, PieceEnumeration.FREE_SPACE, PieceEnumeration.FREE_SPACE, PieceEnumeration.FREE_SPACE, PieceEnumeration.FREE_SPACE, PieceEnumeration.FREE_SPACE, PieceEnumeration.FREE_SPACE, PieceEnumeration.FREE_SPACE},
            {PieceEnumeration.FREE_SPACE, PieceEnumeration.FREE_SPACE, PieceEnumeration.FREE_SPACE, PieceEnumeration.FREE_SPACE, PieceEnumeration.FREE_SPACE, PieceEnumeration.FREE_SPACE, PieceEnumeration.FREE_SPACE, PieceEnumeration.FREE_SPACE},
            {PieceEnumeration.P1_PAWN, PieceEnumeration.P1_PAWN, PieceEnumeration.P1_PAWN, PieceEnumeration.P1_PAWN, PieceEnumeration.P1_PAWN, PieceEnumeration.P1_PAWN, PieceEnumeration.P1_PAWN, PieceEnumeration.P1_PAWN},
            {PieceEnumeration.P1_ROOK, PieceEnumeration.P1_KNIGHT, PieceEnumeration.P1_BISHOP, PieceEnumeration.P1_KING, PieceEnumeration.P1_QUEEN, PieceEnumeration.P1_BISHOP, PieceEnumeration.P1_KNIGHT, PieceEnumeration.P1_ROOK}
        };
        ChessBoard chessBoard = new ChessBoard();
        chessBoard.setBoard(board);
        return chessBoard;
    }
}
