package board.piece;

import board.ChessBoard;
import board.move.ChessMove;
import board.move.ChessMoveBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: vincent
 * Date: Feb 14, 2011
 * Time: 7:35:12 PM
 * To change this template use File | Settings | File Templates.
 */
public class Rook extends Piece{
    @Override
    public List<ChessMove> getValidPieceMoves(Piece chessPiece, List<Piece> opponentsChessPieces, ChessBoard chessBoard, ChessMove lastMove) {
        List<ChessMove> validBishopMoves = new ArrayList<ChessMove>();
        byte[][] board = chessBoard.getBoard();
        PiecePosition piecePosition = chessPiece.getPosition();
        getValidRookMovesForHorizontalPath(1, 0, validBishopMoves, board, chessPiece, piecePosition);
        getValidRookMovesForHorizontalPath(-1, 0, validBishopMoves, board, chessPiece, piecePosition);
        getValidRookMovesForHorizontalPath(0, 1, validBishopMoves, board, chessPiece, piecePosition);
        getValidRookMovesForHorizontalPath(0, -1, validBishopMoves, board, chessPiece, piecePosition);
        return validBishopMoves;
    }

    private void getValidRookMovesForHorizontalPath(int xInc, int yInc, List<ChessMove> validBishopMoves, byte[][] board, Piece chessPiece, PiecePosition piecePosition) {
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
