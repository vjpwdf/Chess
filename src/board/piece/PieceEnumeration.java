package board.piece;

import java.util.HashMap;
import java.util.Map;

/**
 * User: vincent
 * Date: Feb 14, 2011
 * Time: 7:04:31 PM
 */
public class PieceEnumeration {
    public static byte FREE_SPACE = 0;
    public static byte P1_PAWN = 1;
    public static byte P1_KNIGHT = 2;
    public static byte P1_BISHOP = 3;
    public static byte P1_ROOK = 4;
    public static byte P1_KING = 5;
    public static byte P1_QUEEN = 6;
    public static byte P2_PAWN = 7;
    public static byte P2_KNIGHT = 8;
    public static byte P2_BISHOP = 9;
    public static byte P2_ROOK = 10;
    public static byte P2_KING = 11;
    public static byte P2_QUEEN = 12;

    public static char FREE_SPACE_CHAR = '.';
    public static char P1_PAWN_CHAR = 'P';
    public static char P1_KNIGHT_CHAR = 'N';
    public static char P1_BISHOP_CHAR = 'B';
    public static char P1_ROOK_CHAR = 'R';
    public static char P1_KING_CHAR = 'K';
    public static char P1_QUEEN_CHAR = 'Q';
    public static char P2_PAWN_CHAR = 'p';
    public static char P2_KNIGHT_CHAR = 'n';
    public static char P2_BISHOP_CHAR = 'b';
    public static char P2_ROOK_CHAR = 'r';
    public static char P2_KING_CHAR = 'k';
    public static char P2_QUEEN_CHAR = 'q';

    public static Map<Character, Byte> characterToByteMapping;

    /**
     * Initialize the map correlating the character to a byte for memory efficiency
     * @return the character to byte map
     */
    private static Map<Character, Byte> initializeCharacterToByteMap() {
        Map<Character, Byte> characterToByteMap = new HashMap<Character, Byte>();
        characterToByteMap.put(FREE_SPACE_CHAR, FREE_SPACE);
        characterToByteMap.put(P1_PAWN_CHAR, P1_PAWN);
        characterToByteMap.put(P1_KNIGHT_CHAR, P1_KNIGHT);
        characterToByteMap.put(P1_BISHOP_CHAR, P1_BISHOP);
        characterToByteMap.put(P1_ROOK_CHAR, P1_ROOK);
        characterToByteMap.put(P1_KING_CHAR, P1_KING);
        characterToByteMap.put(P1_QUEEN_CHAR, P1_QUEEN);
        characterToByteMap.put(P2_PAWN_CHAR, P2_PAWN);
        characterToByteMap.put(P2_KNIGHT_CHAR, P2_KNIGHT);
        characterToByteMap.put(P2_BISHOP_CHAR, P2_BISHOP);
        characterToByteMap.put(P2_ROOK_CHAR, P2_ROOK);
        characterToByteMap.put(P2_KING_CHAR, P2_KING);
        characterToByteMap.put(P2_QUEEN_CHAR, P2_QUEEN);
        return characterToByteMap;
    }

    /**
     * Gets the byte from the character to byte map correlating the pieces and their corresponding byte value
     * @param character the character to get the byte representation of
     * @return the byte representation of the passed character
     */
    public static byte getByteFromCharacter(Character character) {
        if(characterToByteMapping == null) {
            characterToByteMapping = initializeCharacterToByteMap();
        }
        return characterToByteMapping.get(character);        
    }

    /**
     * Checks to see of the piece is a white piece
     * @param piece piece to check to see if is a white piece
     * @return if the piece is a white piece
     */
    public static boolean isPieceAWhitePiece(byte piece) {
        return piece <= 6 && piece >= 1;
    }

    /**
     * Checks to see of the piece is a black piece
     * @param piece piece to check to see if is a black piece
     * @return if the piece is a black piece
     */
    public static boolean isPieceABlackPiece(byte piece) {
        return piece >= 7;
    }

    /**
     * Checks to see of the piece is a white king piece
     * @param piece piece to check to see if is a white king piece
     * @return if the piece is a white king piece
     */
    public static boolean isWhiteKing(byte piece) {
        return piece == P1_KING;
    }

    /**
     * Checks to see of the piece is a black king piece
     * @param piece piece to check to see if is a black king piece
     * @return if the piece is a black king piece
     */
    public static boolean isBlackKing(byte piece) {
        return piece == P2_KING;
    }
}
