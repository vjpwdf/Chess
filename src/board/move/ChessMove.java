package board.move;

/**
 * Created by IntelliJ IDEA.
 * User: vincent
 * Date: 17-Feb-2011
 * Time: 5:03:10 PM
 */
public class ChessMove {
    private char fromFile;
    private char toFile;
    private byte fromRank;
    private byte toRank;
    private char promotion = '\0';
    private boolean capturedViaEnPassant = false;

    public char getFromFile() {
        return fromFile;
    }

    public void setFromFile(char fromFile) {
        this.fromFile = fromFile;
    }

    public byte getFromFileByte() {
        return (byte) (fromFile - 97);
    }

    public char getToFile() {
        return toFile;
    }

    public void setToFile(char toFile) {
        this.toFile = toFile;
    }

    public byte getFromRank() {
        return fromRank;
    }

    public void setFromRank(byte fromRank) {
        this.fromRank = fromRank;
    }

    public byte getToRank() {
        return toRank;
    }

    public void setToRank(byte toRank) {
        this.toRank = toRank;
    }

    public char getPromotion() {
        return promotion;
    }

    public void setPromotion(char promotion) {
        this.promotion = promotion;
    }

    public int getToFileByte() {
        return (byte) (toFile - 97);
    }

    public boolean isCapturedViaEnPassant() {
        return capturedViaEnPassant;
    }

    public void setCapturedViaEnPassant(boolean capturedViaEnPassant) {
        this.capturedViaEnPassant = capturedViaEnPassant;
    }

    @Override
    public String toString() {
        return "" + fromFile + (int)fromRank + toFile + (int)toRank + (promotion=='\0'?"":promotion);
    }
}
