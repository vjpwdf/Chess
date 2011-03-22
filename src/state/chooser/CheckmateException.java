package state.chooser;

/**
 * Created by IntelliJ IDEA.
 * User: vincent
 * Date: 22/03/11
 * Time: 12:20 PM
 * To change this template use File | Settings | File Templates.
 */
public class CheckmateException extends Throwable {
    private String checkmate;

    public CheckmateException(String checkmate) {
        this.checkmate = checkmate;
    }
}
