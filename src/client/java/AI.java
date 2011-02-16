package client.java;

import com.sun.jna.Pointer;
import player.Player;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

///The class implementing gameplay logic.
class AI extends BaseAI {
    public String username() {
        return "Shell AI";
    }

    public String password() {
        return "password";
    }

    public int letterToFile(char letter) {
        if (letter >= 'a' && letter <= 'h')
            return letter - 'a' + 1;
        else
            return 0; // Invalid
    }

    //This function is called each time it is your turn
    //Return true to end your turn, return false to ask the server for updated information
    public boolean run() throws IOException {
        // See if we're the white player or not : White is player 0, Black is player 1
        boolean isWhitePlayer = false;

        if (getPlayerId() == Player.PLAYER_1)
            isWhitePlayer = true;

        if (isWhitePlayer) {
            System.out.println("Playing white : Time remaining : " + getPlayer0Time());
        } else {
            System.out.println("Playing black : Time remaining : " + getPlayer1Time());
        }

        // Show the move the opponent did, if any
        if (moves.length != 0) {
            // Since in chess we can only do one move, we can assume moves[0] is valid.
            int movedPieceFile = moves[0].getToFile();
            int movedPieceRank = moves[0].getToRank();

            // Find the piece with this given ID
            int indexOfPieceFound = -1;

            for (int x = 0; x < pieces.length; ++x) {
                if (pieces[x].getFile() == movedPieceFile &&
                        pieces[x].getRank() == movedPieceRank) {
                    indexOfPieceFound = x;
                    break;
                }
            }

            if (indexOfPieceFound == -1) {
                System.out.println("Unknown piece moved from " + moves[0].getFromFile() + moves[0].getFromRank());
            } else {
                System.out.println("Opponent moved piece " + (char) pieces[indexOfPieceFound].getType() + " to " + moves[0].getToFile() + moves[0].getToRank());
            }
        }

        System.out.println("");

        // Show the current board
        // The framework doesn't store it as a board, instead its a collection of pieces : so we need to make an array with the current board state
        char[][] board = new char[8][8];
        for(int i = 0; i < 8; i++) {
            for(int x = 0; x < 8; x++) {
                board[i][x] = '.';
            }
        }

        // Now place all the current pieces on the board
        // Note that all files and ranks are 1 based, instead of 0 based.
        for (Piece piece : pieces) {
            if (piece.getOwner() == 0) // White use uppercase
                board[8 - piece.getRank()][piece.getFile() - 1] = (char) piece.getType();
            else // Black use lower
                board[8 - piece.getRank()][piece.getFile() - 1] = Character.toLowerCase((char) piece.getType());
        }

        // Now that we stored all the current pieces, display the board
        System.out.println("   | a | b | c | d | e | f | g | h |   ");

        for (int x = 0; x < 8; ++x) {
            System.out.println("---+---+---+---+---+---+---+---+---+---");

            System.out.print(8 - x + "  | ");

            for (int y = 0; y < 8; ++y) {
                System.out.print(board[x][y] + " | ");
            }

            System.out.println("");
        }

        System.out.println("---+---+---+---+---+---+---+---+---+---");
        System.out.println("   | a | b | c | d | e | f | g | h |   ");

        System.out.println("");

        boolean validInput = false;

        // Look until we get something we understand
        while (!validInput) {
            // Take input from the keyboard
            System.out.println("Input a move : format is <file><rank><file><rank> (like d2e2) : for promotions put an extra character with the type (e2f2Q)");
            System.out.println("move> ");

            String moveString;
            InputStreamReader converter = new InputStreamReader(System.in);
            BufferedReader in = new BufferedReader(converter);
            moveString = in.readLine();

            // Parse the string : should be 4 or 5 characters
            if (moveString.length() != 4 && moveString.length() != 5) {
                System.out.println("Invalid input (wrong size)");
                continue;
            }

            // 1, 3, and 5 should be characters : 2 and 4 are numbers
            int fromFile = letterToFile(moveString.charAt(0));
            int fromRank = moveString.charAt(1) - '0';
            int toFile = letterToFile(moveString.charAt(2));
            int toRank = moveString.charAt(3) - '0';
            char promoteType = (moveString.length() == 5) ? moveString.charAt(4) : '\0';

            // Make sure stuff was valid
            if (fromFile == 0 || fromRank == 0 || toFile == 0 || toRank == 0) {
                System.out.println("Invalid input (invalid character in rank or file)");
                continue;
            }

            // Make sure ranks are in range, since we don't validate it earlier.
            // Files are good, because letterToFile checks it.
            if (fromRank < 1 || fromRank > 8 || toRank < 1 || toRank > 8) {
                System.out.println("Invalid input (bad rank number)");
                continue;
            }

            // Make sure promote type is a valid character, if we have one
            if (promoteType != '\0' && promoteType != 'R' && promoteType != 'N' && promoteType != 'B' && promoteType != 'Q') {
                System.out.println("Invalid input (promote type is invalid : make sure you capitalized it)");
                continue;
            }

            // Looks good : lets perform this move
            // Figure out what piece we're moving
            int indexOfPieceMoving = -1;
            for (int x = 0; x < pieces.length; ++x) {
                if (pieces[x].getFile() == fromFile && pieces[x].getRank() == fromRank) {
                    indexOfPieceMoving = x;
                    break;
                }
            }

            if (indexOfPieceMoving == -1) {
                System.out.println("Invalid input (no piece at location)");
                continue;
            }

            // Move the piece
            /// \note We DONT check whether this piece can actually move here. The server will error if we give it a bad move, but we have no way of knowing.
            pieces[indexOfPieceMoving].move(toFile, toRank, promoteType);

            // And we're done
            validInput = true;
        }

        System.out.println("Waiting for response...");

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
