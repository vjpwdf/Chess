package client.java;

import client.java.Client;
import com.sun.jna.Pointer;

///A chess piece
class Piece {
    Pointer ptr;
    int ID;
    int iteration;

    public Piece(Pointer p) {
        ptr = p;
        ID = Client.INSTANCE.pieceGetId(ptr);
        iteration = BaseAI.iteration;
    }

    boolean validify() {
        if (iteration == BaseAI.iteration) return true;
        for (int i = 0; i < BaseAI.pieces.length; i++) {
            if (BaseAI.pieces[i].ID == ID) {
                ptr = BaseAI.pieces[i].ptr;
                iteration = BaseAI.iteration;
                return true;
            }
        }
        throw new ExistentialError();
    }

    //commands

    ///
    int move(int file, int rank, int type) {
        validify();
        return Client.INSTANCE.pieceMove(ptr, file, rank, type);
    }

    //getters

    ///Unique Identifier
    public int getId() {
        validify();
        return Client.INSTANCE.pieceGetId(ptr);
    }

    ///The owner of the piece
    public int getOwner() {
        validify();
        return Client.INSTANCE.pieceGetOwner(ptr);
    }

    ///The letter this piece is at (1-8)
    public int getFile() {
        validify();
        return Client.INSTANCE.pieceGetFile(ptr);
    }

    ///The number this piece is at (1-8)
    public int getRank() {
        validify();
        return Client.INSTANCE.pieceGetRank(ptr);
    }

    ///1=has moved, 0=has not moved
    public int getHasMoved() {
        validify();
        return Client.INSTANCE.pieceGetHasMoved(ptr);
    }

    ///The letter that describes this piece's type. K=King, Q=Queen, B=Bishop, N=Knight, R=Rook, P=Pawn
    public int getType() {
        validify();
        return Client.INSTANCE.pieceGetType(ptr);
    }

}
