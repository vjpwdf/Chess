package board.piece;

import board.ChessBoard;
import board.move.ChessMove;
import state.State;

/**
 * Created by IntelliJ IDEA.
 * User: vincent
 * Date: 17-Feb-2011
 * Time: 9:40:24 PM
 */
public class PieceMover {
    public static State generateNewStateWithMove(ChessBoard chessBoard, ChessMove chessMove) {
        ChessBoard chessBoardCopy = chessBoard.copy();
        byte[][] board = chessBoardCopy.getBoard();
        byte movingPiece = board[chessMove.getFromFileByte()][chessMove.getFromRank()];
        if(chessMove.getPromotion() != '\0') {
            movingPiece = PieceEnumeration.getByteFromCharacter(chessMove.getPromotion());
        }
        board[chessMove.getFromFileByte()][chessMove.getFromRank()] = 0;
        board[chessMove.getToFileByte()][chessMove.getToRank()] = movingPiece;
        if(chessMove.isCapturedViaEnPassant()) {
            if(PieceEnumeration.isPieceAWhitePiece(movingPiece)) {
                board[chessMove.getToFileByte()][chessMove.getToRank()-1] = 0;
            } else {
                board[chessMove.getToFileByte()][chessMove.getToRank()+1] = 0;
            }
        }
        chessBoardCopy.setBoard(board);
        State state = new State();
        state.setChessBoard(chessBoardCopy);
        state.setMove(chessMove);
        return state;
    }
}
