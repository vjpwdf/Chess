package board.piece;

import board.ChessBoard;
import board.move.ChessMove;
import board.move.ChessMoveBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * User: vincent
 * Date: Feb 14, 2011
 * Time: 7:34:58 PM
 */
public class Queen extends Piece {
    /**
     * Gets all valid piece moves for the queen
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
        getValidQueenMovesForPath(1, 0, validBishopMoves, board, chessPiece, piecePosition);
        getValidQueenMovesForPath(-1, 0, validBishopMoves, board, chessPiece, piecePosition);
        getValidQueenMovesForPath(0, 1, validBishopMoves, board, chessPiece, piecePosition);
        getValidQueenMovesForPath(0, -1, validBishopMoves, board, chessPiece, piecePosition);
        getValidQueenMovesForPath(1, 1, validBishopMoves, board, chessPiece, piecePosition);
        getValidQueenMovesForPath(1, -1, validBishopMoves, board, chessPiece, piecePosition);
        getValidQueenMovesForPath(-1, 1, validBishopMoves, board, chessPiece, piecePosition);
        getValidQueenMovesForPath(-1, -1, validBishopMoves, board, chessPiece, piecePosition);
        return validBishopMoves;
    }

    /**
     * Gets the value of the piece for heuristics
     * @return the value of the piece for heuristics
     */
    @Override
    public int getPieceValue() {
        return 9;
    }

    /**
     * Iterates in a particular direction and gets all valid moves in that direction including captures and free space movements
     * @param xInc x iterator
     * @param yInc y iterator
     * @param validQueenMoves current list of valid moves
     * @param board the current board
     * @param chessPiece piece to get the moves for
     * @param piecePosition current piece position on the board
     */
    private void getValidQueenMovesForPath(int xInc, int yInc, List<ChessMove> validQueenMoves, byte[][] board, Piece chessPiece, PiecePosition piecePosition) {
        int xIncOriginal = xInc;
        int yIncOriginal = yInc;
        if(chessPiece.isWhitePlayer()) {
            while(piecePosition.getX()+xInc <= 7 && piecePosition.getX()+xInc >= 0 && piecePosition.getY()+yInc <= 7 && piecePosition.getY()+yInc >= 0 &&
                    (board[piecePosition.getX()+xInc][piecePosition.getY()+yInc] == PieceEnumeration.FREE_SPACE || PieceEnumeration.isPieceABlackPiece(board[piecePosition.getX()+xInc][piecePosition.getY()+yInc]))) {
                validQueenMoves.add(ChessMoveBuilder.buildChessMoveFromPositions(piecePosition, new PiecePosition(piecePosition.getX()+xInc, piecePosition.getY()+yInc)));
                if(PieceEnumeration.isPieceABlackPiece(board[piecePosition.getX()+xInc][piecePosition.getY()+yInc])) {
                    return;
                }
                xInc += xIncOriginal;
                yInc += yIncOriginal;
            }
        } else {
            while(piecePosition.getX()+xInc <= 7 && piecePosition.getX()+xInc >= 0 && piecePosition.getY()+yInc <= 7 && piecePosition.getY()+yInc >= 0 &&
                    (board[piecePosition.getX()+xInc][piecePosition.getY()+yInc] == PieceEnumeration.FREE_SPACE || PieceEnumeration.isPieceAWhitePiece(board[piecePosition.getX()+xInc][piecePosition.getY()+yInc]))) {
                validQueenMoves.add(ChessMoveBuilder.buildChessMoveFromPositions(piecePosition, new PiecePosition(piecePosition.getX()+xInc, piecePosition.getY()+yInc)));
                if(PieceEnumeration.isPieceAWhitePiece(board[piecePosition.getX()+xInc][piecePosition.getY()+yInc])) {
                    return;
                }
                xInc += xIncOriginal;
                yInc += yIncOriginal;
            }
        }
    }
}
