package board.piece;

import board.ChessBoard;
import board.move.ChessMove;
import board.move.ChessMoveBuilder;
import client.java.Move;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: vincent
 * Date: Feb 14, 2011
 * Time: 7:33:02 PM
 * To change this template use File | Settings | File Templates.
 */
public class Pawn extends Piece {

    @Override
    public List<ChessMove> getValidPieceMoves(Piece chessPiece, List<Piece> opponentsChessPieces, ChessBoard chessBoard) {
        List<ChessMove> initialDoubleJumpMove = getInitialDoubleJumpMoveIfPossible(chessPiece, opponentsChessPieces, chessBoard);
        List<ChessMove> singleJumpMoves = getSingleJumpMovesIfPossible(chessPiece, opponentsChessPieces, chessBoard);

        List<ChessMove> allValidPawnMoves = new ArrayList<ChessMove>();
        allValidPawnMoves.addAll(initialDoubleJumpMove);
        allValidPawnMoves.addAll(singleJumpMoves);
        return allValidPawnMoves;
    }

    private List<ChessMove> getSingleJumpMovesIfPossible(Piece chessPiece, List<Piece> opponentsChessPieces, ChessBoard chessBoard) {
        List<ChessMove> singleJumpMoves = new ArrayList<ChessMove>();
        PiecePosition pawnPosition = chessPiece.getPosition();
        byte[][] board = chessBoard.getBoard();
        if(chessPiece.isWhitePlayer()) {
            if(board[pawnPosition.getX()][pawnPosition.getY()-1] == PieceEnumeration.FREE_SPACE) {
                singleJumpMoves.add(ChessMoveBuilder.buildChessMoveFromPositions(pawnPosition, new PiecePosition(pawnPosition.getX(), pawnPosition.getY()-1)));
            }
        } else if (!chessPiece.isWhitePlayer()) {
            if(board[pawnPosition.getX()][pawnPosition.getY()+1] == PieceEnumeration.FREE_SPACE) {
                singleJumpMoves.add(ChessMoveBuilder.buildChessMoveFromPositions(pawnPosition, new PiecePosition(pawnPosition.getX(), pawnPosition.getY()+1)));
            }
        }
        return singleJumpMoves;
    }

    private List<ChessMove> getInitialDoubleJumpMoveIfPossible(Piece chessPiece, List<Piece> opponentsChessPieces, ChessBoard chessBoard) {
        List<ChessMove> doubleJumpMove = new ArrayList<ChessMove>();
        PiecePosition pawnPosition = chessPiece.getPosition();
        byte[][] board = chessBoard.getBoard();
        if(chessPiece.isWhitePlayer() && pawnPosition.equals(new PiecePosition(pawnPosition.getX(), 6))) {
            if(board[pawnPosition.getX()][pawnPosition.getY()-1] == PieceEnumeration.FREE_SPACE &&
                    board[pawnPosition.getX()][pawnPosition.getY()-2] == PieceEnumeration.FREE_SPACE) {
                doubleJumpMove.add(ChessMoveBuilder.buildChessMoveFromPositions(pawnPosition, new PiecePosition(pawnPosition.getX(), pawnPosition.getY()-2)));
            }
        } else if (!chessPiece.isWhitePlayer() && pawnPosition.equals(new PiecePosition(pawnPosition.getX(), 1))) {
            if(board[pawnPosition.getX()][pawnPosition.getY()+1] == PieceEnumeration.FREE_SPACE &&
                    board[pawnPosition.getX()][pawnPosition.getY()+2] == PieceEnumeration.FREE_SPACE) {
                doubleJumpMove.add(ChessMoveBuilder.buildChessMoveFromPositions(pawnPosition, new PiecePosition(pawnPosition.getX(), pawnPosition.getY()+2)));
            }
        }
        return doubleJumpMove;
    }
}
