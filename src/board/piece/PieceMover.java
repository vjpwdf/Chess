package board.piece;

import board.ChessBoard;
import board.move.ChessMove;
import state.State;

/**
 * User: vincent
 * Date: 17-Feb-2011
 * Time: 9:40:24 PM
 */
public class PieceMover {
    /**
     * Moves the piece appropriately and generates a new state from the move on the current board
     *
     * @param chessBoard the current board
     * @param chessMove  to move to be performed on the current board
     * @return a new state from the move on the current board
     */
    public static State generateNewStateWithMove(ChessBoard chessBoard, ChessMove chessMove) {
        ChessBoard chessBoardCopy = chessBoard.copy();
        byte[][] board = chessBoardCopy.getBoard();
        if (chessMove.isCasteling()) {
            if (chessMove.getToFileByte() == 7) {
                board[6][chessMove.getFromRank()] = board[4][chessMove.getFromRank()];
                board[4][chessMove.getFromRank()] = 0;

                board[5][chessMove.getFromRank()] = board[7][chessMove.getFromRank()];
                board[7][chessMove.getFromRank()] = 0;
            }
            if (chessMove.getToFileByte() == 0) {
                board[2][chessMove.getFromRank()] = board[4][chessMove.getFromRank()];
                board[4][chessMove.getFromRank()] = 0;

                board[3][chessMove.getFromRank()] = board[0][chessMove.getFromRank()];
                board[0][chessMove.getFromRank()] = 0;
            }
        } else {
            byte movingPiece = board[chessMove.getFromFileByte()][chessMove.getFromRank()];
            if (chessMove.getPromotion() != '\0') {
                movingPiece = PieceEnumeration.getByteFromCharacter(chessMove.getPromotion());
            }
            board[chessMove.getFromFileByte()][chessMove.getFromRank()] = 0;
            board[chessMove.getToFileByte()][chessMove.getToRank()] = movingPiece;
            if (chessMove.isCapturedViaEnPassant()) {
                if (PieceEnumeration.isPieceAWhitePiece(movingPiece)) {
                    board[chessMove.getToFileByte()][chessMove.getToRank() - 1] = 0;
                } else {
                    board[chessMove.getToFileByte()][chessMove.getToRank() + 1] = 0;
                }
            }
        }
        chessBoardCopy.setBoard(board);
        State state = new State();
        state.setChessBoard(chessBoardCopy);
        state.setMove(chessMove);
        return state;
    }
}
