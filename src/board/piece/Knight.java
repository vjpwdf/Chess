package board.piece;

import board.ChessBoard;
import board.move.ChessMove;
import board.move.ChessMoveBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * User: vincent
 * Date: Feb 14, 2011
 * Time: 7:35:28 PM
 */
public class Knight extends Piece {
    /**
     * Gets all valid piece moves for the knight
     * @param chessPiece piece to get moves for
     * @param opponentsChessPieces all opponents chess pieces
     * @param chessBoard the current chess board
     * @param lastMove the last move performed
     * @return a list of valid chess moves for this piece
     */
    @Override
    public List<ChessMove> getValidPieceMoves(Piece chessPiece, List<Piece> opponentsChessPieces, ChessBoard chessBoard, ChessMove lastMove) {
        PiecePosition piecePosition = chessPiece.getPosition();
        byte[][] board = chessBoard.getBoard();
        List<ChessMove> validKnightMoves = new ArrayList<ChessMove>();
        getMoveForMovement(2, 1, piecePosition, board, chessPiece, validKnightMoves);
        getMoveForMovement(1, 2, piecePosition, board, chessPiece, validKnightMoves);

        getMoveForMovement(-1, 2, piecePosition, board, chessPiece, validKnightMoves);
        getMoveForMovement(-2, 1, piecePosition, board, chessPiece, validKnightMoves);
        
        getMoveForMovement(-2, -1, piecePosition, board, chessPiece, validKnightMoves);
        getMoveForMovement(-1, -2, piecePosition, board, chessPiece, validKnightMoves);
        
        getMoveForMovement(1, -2, piecePosition, board, chessPiece, validKnightMoves);
        getMoveForMovement(2, -1, piecePosition, board, chessPiece, validKnightMoves);

        return validKnightMoves;
    }

    /**
     * Gets the value of the piece for heuristics
     * @return the value of the piece for heuristics
     */
    @Override
    public int getPieceValue() {
        return 3;
    }

    /**
     * Checks to see if the knight can either capture or move into a free space
     * @param xInc x advance
     * @param yInc y advance
     * @param piecePosition knight position on board
     * @param board the chess board
     * @param chessPiece the knight piece
     * @param validKnightMoves all valid knight moves so far
     */
    private void getMoveForMovement(int xInc, int yInc, PiecePosition piecePosition, byte[][] board, Piece chessPiece, List<ChessMove> validKnightMoves) {
        if(piecePosition.getX() + xInc > 7 || piecePosition.getX() + xInc < 0 || piecePosition.getY() + yInc > 7 || piecePosition.getY() + yInc < 0) {
            return;    
        }
        if (chessPiece.isWhitePlayer()) {
            if (PieceEnumeration.isPieceABlackPiece(board[piecePosition.getX() + xInc][piecePosition.getY() + yInc]) ||
                    board[piecePosition.getX() + xInc][piecePosition.getY() + yInc] == PieceEnumeration.FREE_SPACE) {
                validKnightMoves.add(ChessMoveBuilder.buildChessMoveFromPositions(piecePosition, new PiecePosition(piecePosition.getX() + xInc, piecePosition.getY() + yInc)));
            }
        } else {
            if (PieceEnumeration.isPieceAWhitePiece(board[piecePosition.getX() + xInc][piecePosition.getY() + yInc]) ||
                    board[piecePosition.getX() + xInc][piecePosition.getY() + yInc] == PieceEnumeration.FREE_SPACE) {
                validKnightMoves.add(ChessMoveBuilder.buildChessMoveFromPositions(piecePosition, new PiecePosition(piecePosition.getX() + xInc, piecePosition.getY() + yInc)));
            }
        }
    }
}
