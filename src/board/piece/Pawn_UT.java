package board.piece;

import board.BoardConverter;
import board.ChessBoard;
import board.move.ChessMove;
import org.junit.Test;
import state.State;
import state.StateEngine;
import state.StateNode;

import java.util.HashMap;
import java.util.Map;

import static junit.framework.Assert.assertEquals;

/**
 * Created by IntelliJ IDEA.
 * User: vincent
 * Date: 18-Feb-2011
 * Time: 2:02:12 PM
 */
public class Pawn_UT {
    @Test
    public void testGetValidPieceMoves_InitialWhiteMoves() throws Exception {
        char charBoard[][] = new char[][] {
                {'.', '.', '.', '.', '.', '.', '.', '.'},
                {'p', 'p', 'p', 'p', 'p', 'p', 'p', 'p'},
                {'.', '.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.', '.'},
                {'P', 'P', 'P', 'P', 'P', 'P', 'P', 'P'},
                {'.', '.', '.', '.', '.', '.', '.', '.'}
        };
        ChessBoard board = BoardConverter.convertCharBoardToByteBoard(charBoard);
        StateNode state = StateEngine.convertBoardToState(board, null);
        StateEngine.generateFutureStates(state, true, null);
        assertEquals(16, state.getChildrenStates().size());
    }

    @Test
    public void testGetValidPieceMoves_InitialBlackMoves() throws Exception {
        char charBoard[][] = new char[][] {
                {'.', '.', '.', '.', '.', '.', '.', '.'},
                {'p', 'p', 'p', 'p', 'p', 'p', 'p', 'p'},
                {'.', '.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.', '.'},
                {'P', 'P', 'P', 'P', 'P', 'P', 'P', 'P'},
                {'.', '.', '.', '.', '.', '.', '.', '.'}
        };
        ChessBoard board = BoardConverter.convertCharBoardToByteBoard(charBoard);
        StateNode state = StateEngine.convertBoardToState(board, null);
        StateEngine.generateFutureStates(state, false, null);
        assertEquals(16, state.getChildrenStates().size());
    }

    @Test
    public void testGetValidPieceMoves_MiddleCaptureMoves_ForWhite() throws Exception {
        char charBoard[][] = new char[][] {
                {'.', '.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', 'p', '.', '.', '.'},
                {'.', '.', '.', 'P', 'P', 'P', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.', '.'}
        };
        ChessBoard board = BoardConverter.convertCharBoardToByteBoard(charBoard);
        StateNode state = StateEngine.convertBoardToState(board, null);
        StateEngine.generateFutureStates(state, true, null);
        assertEquals(6, state.getChildrenStates().size());
    }

    @Test
    public void testGetValidPieceMoves_MiddleCaptureMoves_ForBlack() throws Exception {
        char charBoard[][] = new char[][] {
                {'.', '.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', 'p', '.', '.', '.'},
                {'.', '.', '.', 'P', 'P', 'P', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.', '.'}
        };
        ChessBoard board = BoardConverter.convertCharBoardToByteBoard(charBoard);
        StateNode state = StateEngine.convertBoardToState(board, null);
        StateEngine.generateFutureStates(state, false, null);
        assertEquals(2, state.getChildrenStates().size());
    }

    @Test
    public void testGetValidPieceMoves_Promotion_ForWhite() throws Exception {
        char charBoard[][] = new char[][] {
                {'.', '.', '.', 'q', '.', '.', '.', '.'},
                {'.', '.', '.', '.', 'P', '.', 'P', '.'},
                {'.', '.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.', '.'}
        };
        ChessBoard board = BoardConverter.convertCharBoardToByteBoard(charBoard);
        StateNode state = StateEngine.convertBoardToState(board, null);
        StateEngine.generateFutureStates(state, true, null);
        assertEquals(12, state.getChildrenStates().size());
    }

    @Test
    public void testGetValidPieceMoves_Promotion_ForBlack() throws Exception {
        char charBoard[][] = new char[][] {
                {'.', '.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.', '.'},
                {'p', '.', '.', 'p', '.', 'p', '.', '.'},
                {'.', '.', '.', '.', '.', '.', 'Q', '.'}
        };
        ChessBoard board = BoardConverter.convertCharBoardToByteBoard(charBoard);
        StateNode state = StateEngine.convertBoardToState(board, null);
        StateEngine.generateFutureStates(state, false, null);
        assertEquals(16, state.getChildrenStates().size());
    }

    @Test
    public void testGetValidPieceMoves_EnPassant_ForWhite() throws Exception {
        char charBoard[][] = new char[][] {
                {'.', '.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', 'P', 'p', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.', '.'}
        };
        ChessBoard board = BoardConverter.convertCharBoardToByteBoard(charBoard);
        ChessMove chessMove = new ChessMove();
        chessMove.setFromFile('f');
        chessMove.setFromRank((byte) 6);
        chessMove.setToFile('f');
        chessMove.setToRank((byte) 4);
        StateNode state = StateEngine.convertBoardToState(board, null);
        StateEngine.generateFutureStates(state, true, chessMove);
        assertEquals(2, state.getChildrenStates().size());
    }

    @Test
    public void testGetValidPieceMoves_EnPassant_ForBlack() throws Exception {
        char charBoard[][] = new char[][] {
                {'.', '.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', 'p', 'P', 'p', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.', '.'}
        };
        ChessBoard board = BoardConverter.convertCharBoardToByteBoard(charBoard);
        ChessMove chessMove = new ChessMove();
        chessMove.setFromFile('e');
        chessMove.setFromRank((byte) 1);
        chessMove.setToFile('e');
        chessMove.setToRank((byte) 3);
        StateNode state = StateEngine.convertBoardToState(board, null);
        StateEngine.generateFutureStates(state, false, chessMove);
        assertEquals(4, state.getChildrenStates().size());
    }

    @Test
    public void testGetValidPieceMoves_EnPassant_ForWhite_DifferentPiece() throws Exception {
        char charBoard[][] = new char[][] {
                {'.', '.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', 'P', 'q', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.', '.'}
        };
        ChessBoard board = BoardConverter.convertCharBoardToByteBoard(charBoard);
        ChessMove chessMove = new ChessMove();
        chessMove.setFromFile('f');
        chessMove.setFromRank((byte) 6);
        chessMove.setToFile('f');
        chessMove.setToRank((byte) 4);
        StateNode state = StateEngine.convertBoardToState(board, null);
        StateEngine.generateFutureStates(state, true, chessMove);
        assertEquals(1, state.getChildrenStates().size());
    }

    @Test
    public void testGetValidPieceMoves_EnPassant_ForBlack_DifferentPiece() throws Exception {
        char charBoard[][] = new char[][] {
                {'.', '.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', 'p', 'R', 'p', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.', '.'}
        };
        ChessBoard board = BoardConverter.convertCharBoardToByteBoard(charBoard);
        ChessMove chessMove = new ChessMove();
        chessMove.setFromFile('e');
        chessMove.setFromRank((byte) 1);
        chessMove.setToFile('e');
        chessMove.setToRank((byte) 3);
        StateNode state = StateEngine.convertBoardToState(board, null);
        StateEngine.generateFutureStates(state, false, chessMove);
        assertEquals(2, state.getChildrenStates().size());
    }

    @Test
    public void testTwoStateHashCodes() {
        char charBoard[][] = new char[][] {
                {'.', '.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', 'p', 'R', 'p', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.', '.'}
        };
        ChessBoard board = BoardConverter.convertCharBoardToByteBoard(charBoard);
        StateNode state = StateEngine.convertBoardToState(board, null);

        char charBoard2[][] = new char[][] {
                {'.', '.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', 'p', 'R', 'p', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.', '.'}
        };
        ChessBoard board2 = BoardConverter.convertCharBoardToByteBoard(charBoard2);
        StateNode state2 = StateEngine.convertBoardToState(board2, null);

        assertEquals(state.hashCode(), state2.hashCode());
        assertEquals(board.hashCode(), board2.hashCode());
    }

    @Test
    public void testTwoStateHashCodes_WithMoves() {
        char charBoard[][] = new char[][] {
                {'.', '.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', 'p', 'R', 'p', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.', '.'}
        };
        ChessBoard board = BoardConverter.convertCharBoardToByteBoard(charBoard);
        ChessMove move = new ChessMove();
        move.setFromFile('a');
        move.setToFile('b');
        move.setFromRank((byte)0);
        move.setToRank((byte)1);
        StateNode state = StateEngine.convertBoardToState(board, move);

        char charBoard2[][] = new char[][] {
                {'.', '.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', 'p', 'R', 'p', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.', '.'}
        };
        ChessBoard board2 = BoardConverter.convertCharBoardToByteBoard(charBoard2);
        ChessMove move2 = new ChessMove();
        move2.setFromFile('a');
        move2.setToFile('b');
        move2.setFromRank((byte)0);
        move2.setToRank((byte)1);
        StateNode state2 = StateEngine.convertBoardToState(board2, move2);

        assertEquals(state.hashCode(), state2.hashCode());
        assertEquals(board.hashCode(), board2.hashCode());
        assertEquals(move.hashCode(), move2.hashCode());
    }
    @Test
    public void testTwoStateHashCodes_WithMoves_HTTest() {
        char charBoard[][] = new char[][] {
                {'.', '.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', 'p', 'R', 'p', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.', '.'}
        };
        ChessBoard board = BoardConverter.convertCharBoardToByteBoard(charBoard);
        ChessMove move = new ChessMove();
        move.setFromFile('a');
        move.setToFile('b');
        move.setFromRank((byte) 0);
        move.setToRank((byte) 1);
        StateNode state = StateEngine.convertBoardToState(board, move);

        char charBoard2[][] = new char[][] {
                {'.', '.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', 'p', 'R', 'p', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.', '.'}
        };
        ChessBoard board2 = BoardConverter.convertCharBoardToByteBoard(charBoard2);
        ChessMove move2 = new ChessMove();
        move2.setFromFile('a');
        move2.setToFile('b');
        move2.setFromRank((byte)0);
        move2.setToRank((byte)1);
        StateNode state2 = StateEngine.convertBoardToState(board2, move2);

        assertEquals(state.hashCode(), state2.hashCode());
        assertEquals(state.getState().hashCode(), state2.getState().hashCode());
        assertEquals(board.hashCode(), board2.hashCode());
        assertEquals(move.hashCode(), move2.hashCode());




        Map<State, Integer> historyTable = new HashMap<State, Integer>();
        historyTable.put(state.getState(), 1);
//        historyTable.remove(state2);
//        Integer integer = historyTable.get(state2);
        historyTable.put(state2.getState(), 2);

        System.out.println();
    }
}
