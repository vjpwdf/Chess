package client.java;

import com.sun.jna.Pointer;
import player.Player;

///The class implementing gameplay logic.
class AI extends BaseAI {
    public String username() {
        return "Shell AI";
    }

    public String password() {
        return "password";
    }

    //This function is called each time it is your turn
    //Return true to end your turn, return false to ask the server for updated information
    public boolean run() {
        if(getPlayerId() == Player.PLAYER_1) {
            
        }
        return true;
    }


    //This function is called once, before your first turn
    public void init() {
    }

    //This function is called once, after your last turn
    public void end() {
    }


    public AI(Pointer c) {
        super(c);
    }
}
