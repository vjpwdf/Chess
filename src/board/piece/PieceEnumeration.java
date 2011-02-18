package board.piece;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: vincent
 * Date: Feb 14, 2011
 * Time: 7:04:31 PM
 * To change this template use File | Settings | File Templates.
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

    public static byte getByteFromCharacter(Character character) {
        if(characterToByteMapping == null) {
            characterToByteMapping = initializeCharacterToByteMap();
        }
        return characterToByteMapping.get(character);        
    }

    public static boolean isPieceAWhitePiece(byte piece) {
        return piece <= 6;
    }

    public static boolean isPieceABlackPiece(byte piece) {
        return piece >= 7;
    }
}
