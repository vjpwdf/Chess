package client.java;

import board.move.ChessMove;
import board.move.ChessMoveBuilder;
import board.move.MoveTracker;
import board.piece.PieceMover;
import com.sun.jna.Pointer;
import player.Player;
import state.State;
import state.StateEngine;
import state.StateNode;
import state.chooser.StateChooser;
import state.chooser.TLIDABDLMM;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class AI extends BaseAI {
    public static StateEngine stateEngine = new StateEngine();
    private StateChooser stateChooser = new TLIDABDLMM();
    private static Boolean isWhitePlayer = null;
    public static int playerTimeRemaining;

    /**
     * Constructor for the AI main runner
     *
     * @param c a pointer to the connection object
     */
    public AI(Pointer c) {
        super(c);
        initializeStateEngine();
    }

    /**
     * Initialize the state engine
     */
    public static void initializeStateEngine() {
        StateNode initialStateNode = new StateNode();
        State initialState = new State();
        initialStateNode.setState(initialState);
        stateEngine.setRootState(initialStateNode);
    }

    /**
     * Gets the username of the client
     *
     * @return the username fo the client
     */
    public String username() {
        return "Shell AI";
    }

    /**
     * Gets the password for the game
     *
     * @return password for the game
     */
    public String password() {
        return "password";
    }

    /**
     * Converts a character into its corresponding integer representation
     *
     * @param letter the file to convert
     * @return its corresponding integer representation
     */
    public static int letterToFile(char letter) {
        if (letter >= 'a' && letter <= 'h')
            return letter - 'a' + 1;
        else
            return 0; // Invalid
    }

    /**
     * This function is called each time it is your turn
     *
     * @return Return true to end your turn, return false to ask the server for updated information
     * @throws IOException
     */
    public boolean run() throws IOException {
        setPlayerToWhiteOrBlack();

        displayTimeRemaining();

        if (moves.length != 0) {
            int movedPieceFile = moves[0].getToFile();
            int movedPieceRank = moves[0].getToRank();
            MoveTracker.allMoves.add(ChessMoveBuilder.convertMoveToChessMove(moves[0]));

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
                System.out.println("Opponent moved piece " + (char) (pieces[indexOfPieceFound].getType()) + " to " + ((char) (moves[0].getToFile() + 96)) + moves[0].getToRank());
            }
        }

        System.out.println("");

        char[][] board = new char[8][8];
        for (int i = 0; i < 8; i++) {
            for (int x = 0; x < 8; x++) {
                board[i][x] = '.';
            }
        }


        for (Piece piece : pieces) {
            if (piece.getOwner() == 0) {
                board[8 - piece.getRank()][piece.getFile() - 1] = (char) piece.getType();
            } else {
                board[8 - piece.getRank()][piece.getFile() - 1] = Character.toLowerCase((char) piece.getType());
            }
        }

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

        if (System.getProperty("manual").equals("false")) {
            stateEngine.setCurrentBoard(board);
            MoveTracker.allChessBoards.add(stateEngine.getRootState().getState().getChessBoard());
            ChessMove nextMoveToPerform = stateEngine.getNextStateFromStateChooser(stateChooser, isWhitePlayer);
            if (nextMoveToPerform == null) {
                System.out.println("Checkmate/Stalemate");
                return true;
            }
            MoveTracker.allChessBoards.add(PieceMover.generateNewStateWithMove(stateEngine.getRootState().getState().getChessBoard(), nextMoveToPerform).getChessBoard());
            System.out.println("Making move: " + nextMoveToPerform.getFromFile() + (nextMoveToPerform.getFromRank() + 1) + nextMoveToPerform.getToFile() + (nextMoveToPerform.getToRank() + 1) + nextMoveToPerform.getPromotion());
            MoveTracker.allMoves.add(nextMoveToPerform);
            if (nextMoveToPerform.isCasteling()) {
                if (nextMoveToPerform.getToFile() - nextMoveToPerform.getFromFile() > 0) {
                    pieces[getIndexOfPiece(nextMoveToPerform)].move(letterToFile(nextMoveToPerform.getFromFile())+2, nextMoveToPerform.getFromRank() + 1, nextMoveToPerform.getPromotion());
                } else {
                    pieces[getIndexOfPiece(nextMoveToPerform)].move(letterToFile(nextMoveToPerform.getFromFile())-2, nextMoveToPerform.getFromRank() + 1, nextMoveToPerform.getPromotion());
                }
            } else {
                pieces[getIndexOfPiece(nextMoveToPerform)].move(letterToFile(nextMoveToPerform.getToFile()), nextMoveToPerform.getToRank() + 1, nextMoveToPerform.getPromotion());
            }
        }

        while (!validInput && System.getProperty("manual").equals("true")) {
            System.out.println("Input a move : format is <file><rank><file><rank> (like d2e2) : for promotions put an extra character with the type (e2f2Q)");
            System.out.println("move> ");

            String moveString;
            InputStreamReader converter = new InputStreamReader(System.in);
            BufferedReader in = new BufferedReader(converter);
            moveString = in.readLine();

            if (moveString.length() != 4 && moveString.length() != 5) {
                System.out.println("Invalid input (wrong size)");
                continue;
            }

            int fromFile = letterToFile(moveString.charAt(0));
            int fromRank = moveString.charAt(1) - '0';
            int toFile = letterToFile(moveString.charAt(2));
            int toRank = moveString.charAt(3) - '0';
            char promoteType = (moveString.length() == 5) ? moveString.charAt(4) : '\0';

            if (fromFile == 0 || fromRank == 0 || toFile == 0 || toRank == 0) {
                System.out.println("Invalid input (invalid character in rank or file)");
                continue;
            }

            if (fromRank < 1 || fromRank > 8 || toRank < 1 || toRank > 8) {
                System.out.println("Invalid input (bad rank number)");
                continue;
            }

            if (promoteType != '\0' && promoteType != 'R' && promoteType != 'N' && promoteType != 'B' && promoteType != 'Q') {
                System.out.println("Invalid input (promote type is invalid : make sure you capitalized it)");
                continue;
            }

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

            pieces[indexOfPieceMoving].move(toFile, toRank, promoteType);

            validInput = true;
        }

        System.out.println("Waiting for response...");

        return true;
    }

    /**
     * Displays the time remaining if you are white or black
     */
    private void displayTimeRemaining() {
        if (isWhitePlayer) {
            playerTimeRemaining = getPlayer0Time();
            System.out.println("Playing white : Time remaining : " + playerTimeRemaining);
        } else {
            playerTimeRemaining = getPlayer1Time();
            System.out.println("Playing black : Time remaining : " + playerTimeRemaining);
        }
    }

    /**
     * Sets the player value to white or black
     */
    private void setPlayerToWhiteOrBlack() {
        if (isWhitePlayer == null) {
            isWhitePlayer = getPlayerId() == Player.WHITE_PLAYER;
        }
    }

    /**
     * Gets the index of hte piece that should be moved
     *
     * @param nextMoveToPerform the move to be performed on found piece
     * @return the index of hte piece that should be moved
     */
    private int getIndexOfPiece(ChessMove nextMoveToPerform) {
        for (int i = 0; i < pieces.length; i++) {
            if (pieces[i].getFile() == letterToFile(nextMoveToPerform.getFromFile()) && pieces[i].getRank() == nextMoveToPerform.getFromRank() + 1) {
                return i;
            }
        }
        return 0;
    }

    /**
     * Call before clients first turn
     */
    public void init() {
    }


    /**
     * Called after your last turn
     */
    public void end() {
    }
}
