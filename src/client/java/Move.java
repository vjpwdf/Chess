package client.java;

import com.sun.jna.Pointer;

///A chess move
public class Move {
    Pointer ptr;
    int ID;
    int iteration;

    public Move(Pointer p) {
        ptr = p;
        ID = Client.INSTANCE.moveGetId(ptr);
        iteration = BaseAI.iteration;
    }

    boolean validify() {
        if (iteration == BaseAI.iteration) return true;
        for (int i = 0; i < BaseAI.moves.length; i++) {
            if (BaseAI.moves[i].ID == ID) {
                ptr = BaseAI.moves[i].ptr;
                iteration = BaseAI.iteration;
                return true;
            }
        }
        throw new ExistentialError();
    }

    //commands


    //getters

    ///Unique Identifier
    public int getId() {
        validify();
        return Client.INSTANCE.moveGetId(ptr);
    }

    ///The initial file location
    public int getFromFile() {
        validify();
        return Client.INSTANCE.moveGetFromFile(ptr);
    }

    ///The initial rank location
    public int getFromRank() {
        validify();
        return Client.INSTANCE.moveGetFromRank(ptr);
    }

    ///The final file location
    public int getToFile() {
        validify();
        return Client.INSTANCE.moveGetToFile(ptr);
    }

    ///The final rank location
    public int getToRank() {
        validify();
        return Client.INSTANCE.moveGetToRank(ptr);
    }

    ///The type of the piece for pawn promotion. Q=Queen, B=Bishop, N=Knight, R=Rook
    public int getPromoteType() {
        validify();
        return Client.INSTANCE.moveGetPromoteType(ptr);
    }
}
