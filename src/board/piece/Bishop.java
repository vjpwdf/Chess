package board.piece;

import board.ChessBoard;
import board.move.ChessMove;
import board.move.ChessMoveBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * User: vincent
 * Date: Feb 14, 2011
 * Time: 7:35:47 PM
 */
public class Bishop extends Piece {

    /**
     * Gets all valid piece moves for the bishop
     * @param chessPiece piece to get moves for
     * @param opponentsChessPieces all opponents chess pieces
     * @param chessBoard the current chess board
     * @param lastMove the last move performed
     * @return a list of valid chess moves for this piece
     */
    @Override
    public List<ChessMove> getValidPieceMoves(Piece chessPiece, List<Piece> opponentsChessPieces, ChessBoard chessBoard, ChessMove lastMove) {
        List<ChessMove> validBishopMoves = new ArrayList<ChessMove>();
        byte[][] board = chessBoard.getBoard();
        PiecePosition piecePosition = chessPiece.getPosition();
        getValidBishopMovesForVerticalPath(1, 1, validBishopMoves, board, chessPiece, piecePosition);
        getValidBishopMovesForVerticalPath(1, -1, validBishopMoves, board, chessPiece, piecePosition);
        getValidBishopMovesForVerticalPath(-1, 1, validBishopMoves, board, chessPiece, piecePosition);
        getValidBishopMovesForVerticalPath(-1, -1, validBishopMoves, board, chessPiece, piecePosition);
        return validBishopMoves;
    }

    @Override
    public int getPieceValue() {
        return 3;
    }

    /**
     * Iterates in a particular direction and gets all valid moves in that direction including captures and free space movements
     * @param xInc x iterator
     * @param yInc y iterator
     * @param validBishopMoves current list of valid moves
     * @param board the current board
     * @param chessPiece piece to get the moves for
     * @param piecePosition current piece position on the board
     */
    private void getValidBishopMovesForVerticalPath(int xInc, int yInc, List<ChessMove> validBishopMoves, byte[][] board, Piece chessPiece, PiecePosition piecePosition) {
        int xIncOriginal = xInc;
        int yIncOriginal = yInc;
        if(chessPiece.isWhitePlayer()) {
            while(piecePosition.getX()+xInc <= 7 && piecePosition.getX()+xInc >= 0 && piecePosition.getY()+yInc <= 7 && piecePosition.getY()+yInc >= 0 &&
                    (board[piecePosition.getX()+xInc][piecePosition.getY()+yInc] == PieceEnumeration.FREE_SPACE || PieceEnumeration.isPieceABlackPiece(board[piecePosition.getX()+xInc][piecePosition.getY()+yInc]))) {
                validBishopMoves.add(ChessMoveBuilder.buildChessMoveFromPositions(piecePosition, new PiecePosition(piecePosition.getX()+xInc, piecePosition.getY()+yInc)));
                if(PieceEnumeration.isPieceABlackPiece(board[piecePosition.getX()+xInc][piecePosition.getY()+yInc])) {
                    return;
                }
                xInc += xIncOriginal;
                yInc += yIncOriginal;
            }
        } else {
            while(piecePosition.getX()+xInc <= 7 && piecePosition.getX()+xInc >= 0 && piecePosition.getY()+yInc <= 7 && piecePosition.getY()+yInc >= 0 &&
                    (board[piecePosition.getX()+xInc][piecePosition.getY()+yInc] == PieceEnumeration.FREE_SPACE || PieceEnumeration.isPieceAWhitePiece(board[piecePosition.getX()+xInc][piecePosition.getY()+yInc]))) {
                validBishopMoves.add(ChessMoveBuilder.buildChessMoveFromPositions(piecePosition, new PiecePosition(piecePosition.getX()+xInc, piecePosition.getY()+yInc)));     
                if(PieceEnumeration.isPieceAWhitePiece(board[piecePosition.getX()+xInc][piecePosition.getY()+yInc])) {
                    return;
                }
                xInc += xIncOriginal;
                yInc += yIncOriginal;
            }
        }
    }
}
