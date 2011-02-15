package board.piece;

/**
 * Created by IntelliJ IDEA.
 * User: vincent
 * Date: Feb 14, 2011
 * Time: 7:34:09 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class Piece {
    private PiecePosition position;

    public PiecePosition getPosition() {
        return position;
    }

    public void setPosition(PiecePosition position) {
        this.position = position;
    }
}
