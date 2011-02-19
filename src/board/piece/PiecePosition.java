package board.piece;

/**
 * User: vincent
 * Date: Feb 14, 2011
 * Time: 7:33:27 PM
 */
public class PiecePosition {
    private int x;
    private int y;

    /**
     * Constructor to create a piece position with x and y coordinates
     * @param x x position of the piece
     * @param y y position of the piece
     */
    public PiecePosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Default constructor
     */
    public PiecePosition() {
    }

    /**
     * Gets the x position of the piece
     * @return x position of the piece
     */
    public int getX() {
        return x;
    }

    /**
     * Sets the x position of the piece
     * @param x x position of the piece
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Gets the y position of the piece
     * @return the y position of the piece
     */
    public int getY() {
        return y;
    }

    /**
     * Sets the y position of the piece
     * @param y the y position of the piece
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * Equals operator that checks to see if the x and y coordinates are equal
     * @param o must be a PiecePosition type
     * @return whether the two PiecePosition objects are equal
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PiecePosition that = (PiecePosition) o;

        return x == that.x && y == that.y;
    }

    /**
     * Converts the object into a hash code for quick equals comparison
     * @return hash code for quick equals comparison
     */
    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        return result;
    }
}
