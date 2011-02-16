package client.java;

import com.sun.jna.Pointer;

/// \brief A basic AI interface.

///This class implements most the code an AI would need to interface with the lower-level game code.

///AIs should extend this class to get a lot of builer-plate code out of the way
///The provided AI class does just that.
public abstract class BaseAI {
    static Move[] moves;
    static Piece[] pieces;
    Pointer connection;
    static int iteration;
    boolean initialized;

    public BaseAI(Pointer c) {
        connection = c;
    }

    ///
    ///Make this your username, which should be provided.
    public abstract String username();

    ///
    ///Make this your password, which should be provided.
    public abstract String password();

    ///
    ///This is run on turn 1 before run
    public abstract void init();

    ///
    ///This is run every turn . Return true to end the turn, return false
    ///to request a status update from the server and then immediately rerun this function with the
    ///latest game status.
    public abstract boolean run();

    ///
    ///This is run on after your last turn.
    public abstract void end();


    public boolean startTurn() {
        iteration++;
        int count = 0;
        count = Client.INSTANCE.getMoveCount(connection);
        moves = new Move[count];
        for (int i = 0; i < count; i++) {
            moves[i] = new Move(Client.INSTANCE.getMove(connection, i));
        }
        count = Client.INSTANCE.getPieceCount(connection);
        pieces = new Piece[count];
        for (int i = 0; i < count; i++) {
            pieces[i] = new Piece(Client.INSTANCE.getPiece(connection, i));
        }

        if (!initialized) {
            initialized = true;
            init();
        }
        return run();
    }


    ///How many turns it has been since the beginning of the game
    int getTurnNumber() {
        return Client.INSTANCE.getTurnNumber(connection);
    }

    ///Player Number; either 0 or 1
    int getPlayerId() {
        return Client.INSTANCE.getPlayerID(connection);
    }

    ///What number game this is for the server
    int getGameNumber() {
        return Client.INSTANCE.getGameNumber(connection);
    }

    ///How many turns until the game ends because no pawn has moved and no piece has been taken
    int getTurnsUntilStalemate() {
        return Client.INSTANCE.getTurnsToStalemate(connection);
    }

    ///Player 0's time remaining
    int getPlayer0Time() {
        return Client.INSTANCE.getPlayer0Time(connection);
    }

    ///Player 1's time remaining
    int getPlayer1Time() {
        return Client.INSTANCE.getPlayer1Time(connection);
    }
}
