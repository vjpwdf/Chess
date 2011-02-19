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
    private boolean casteling = false;

    /**
     * Gets the file the piece came from
     * @return the file the piece came from
     */
    public char getFromFile() {
        return fromFile;
    }

    /**
     * Sets the file the piece came from
     * @param fromFile the file the piece came from
     */
    public void setFromFile(char fromFile) {
        this.fromFile = fromFile;
    }

    /**
     * Gets the rank the piece came from
     * @return the rank the piece came from
     */
    public byte getFromFileByte() {
        return (byte) (fromFile - 97);
    }

    /**
     * Gets the file the piece is going to
     * @return the file the piece is going to
     */
    public char getToFile() {
        return toFile;
    }

    /**
     * Sets the file the piece is going to
     * @param toFile the file the piece is going to
     */
    public void setToFile(char toFile) {
        this.toFile = toFile;
    }

    /**
     * Gets the rank where the piece is coming from
     * @return the rank where the piece is coming from
     */
    public byte getFromRank() {
        return fromRank;
    }

    /**
     * Sets the rank where the piece is coming from
     * @param fromRank where the piece is coming from
     */
    public void setFromRank(byte fromRank) {
        this.fromRank = fromRank;
    }

    /**
     * Gets what rank the piece is going to
     * @return what rank the piece is going to
     */
    public byte getToRank() {
        return toRank;
    }

    /**
     * Sets what rank the piece is going to
     * @param toRank the rank the piece is going to
     */
    public void setToRank(byte toRank) {
        this.toRank = toRank;
    }

    /**
     * If the piece is promoted with this move, set what piece it should be promoted to
     * @return the promotion piece
     */
    public char getPromotion() {
        return promotion;
    }
    /**
     * If the piece is promoted with this move, set what piece it should be promoted to
     * @param promotion the promotion piece
     */
    public void setPromotion(char promotion) {
        this.promotion = promotion;
    }

    /**
     * Converts the character into a byte
     * @return file byte
     */
    public int getToFileByte() {
        return (byte) (toFile - 97);
    }

    /**
     * If the piece was captured via en passant, this will be true
     * @return if the piece was captured en passant
     */
    public boolean isCapturedViaEnPassant() {
        return capturedViaEnPassant;
    }

    /**
     * If the piece was captured via en passant, set this to be true
     * @param capturedViaEnPassant if the piece was captured en passant
     */
    public void setCapturedViaEnPassant(boolean capturedViaEnPassant) {
        this.capturedViaEnPassant = capturedViaEnPassant;
    }

    /**
     * Get whether the move is for casteling
     * @return whether the move is for casteling
     */
    public boolean isCasteling() {
        return casteling;
    }

    /**
     * Set whether the move is for casteling
     * @param casteling whether the move is for casteling
     */
    public void setCasteling(boolean casteling) {
        this.casteling = casteling;
    }

    /**
     * Converts object to a string
     * @return object to a string
     */
    @Override
    public String toString() {
        return "" + fromFile + (int)fromRank + toFile + (int)toRank + (promotion=='\0'?"":promotion);
    }
}
