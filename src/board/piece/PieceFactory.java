package board.piece;

/**
 * User: vincent
 * Date: 17-Feb-2011
 * Time: 4:30:17 PM
 */
public class PieceFactory {
    /**
     * Builds the correct abstract piece based upon its character byte representation, whether it is a white piece, and its current position on the board
     * @param pieceValue the byte representation of the piece
     * @param x its x position on the board
     * @param y its y position on the board
     * @param isWhitePlayer whether the piece is for a white player or black
     * @return the correct abstract piece
     */
    public static Piece getPieceFromByteValue(byte pieceValue, int x, int y, boolean isWhitePlayer) {
        Piece piece;

        if (pieceValue == PieceEnumeration.P1_PAWN || pieceValue == PieceEnumeration.P2_PAWN) {
            piece = new Pawn();
        } else if (pieceValue == PieceEnumeration.P1_ROOK || pieceValue == PieceEnumeration.P2_ROOK) {
            piece = new Rook();
        } else if (pieceValue == PieceEnumeration.P1_KNIGHT || pieceValue == PieceEnumeration.P2_KNIGHT) {
            piece = new Knight();
        } else if (pieceValue == PieceEnumeration.P1_BISHOP || pieceValue == PieceEnumeration.P2_BISHOP) {
            piece = new Bishop();
        } else if (pieceValue == PieceEnumeration.P1_QUEEN || pieceValue == PieceEnumeration.P2_QUEEN) {
            piece = new Queen();
        } else if (pieceValue == PieceEnumeration.P1_KING || pieceValue == PieceEnumeration.P2_KING) {
            piece = new King();
        } else {
            throw new RuntimeException("A piece could not be created for " + pieceValue);
        }

        PiecePosition piecePosition = new PiecePosition();
        piecePosition.setX(x);
        piecePosition.setY(y);
        piece.setPosition(piecePosition);
        piece.setWhitePlayer(isWhitePlayer);
        return piece;
    }
}
