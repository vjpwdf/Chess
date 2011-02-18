package board.piece;

import board.BoardConverter;
import board.ChessBoard;
import org.junit.Test;
import state.StateEngine;
import state.StateNode;

import static junit.framework.Assert.assertEquals;

/**
 * Created by IntelliJ IDEA.
 * User: vincent
 * Date: 18-Feb-2011
 * Time: 4:05:45 PM
 */
public class Knight_UT {
    @Test
    public void testGetValidPieceMoves_LineOfPawns_ForWhite() throws Exception {
        char charBoard[][] = new char[][] {
                {'.', '.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.', '.'},
                {'P', 'P', 'P', 'P', 'P', 'P', 'P', 'P'},
                {'.', 'N', '.', '.', '.', '.', 'N', '.'}
        };
        ChessBoard board = BoardConverter.convertCharBoardToByteBoard(charBoard);
        StateNode state = StateEngine.convertBoardToState(board, null);
        StateEngine.generateFutureStates(state, true, null);
        assertEquals(20, state.getChildrenStates().size());
    }

    @Test
    public void testGetValidPieceMoves_LineOfPawns_ForBlack() throws Exception {
        char charBoard[][] = new char[][] {
                {'.', 'n', '.', '.', '.', '.', 'n', '.'},
                {'p', 'p', 'p', 'p', 'p', 'p', 'p', 'p'},
                {'.', '.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.', '.'}
        };
        ChessBoard board = BoardConverter.convertCharBoardToByteBoard(charBoard);
        StateNode state = StateEngine.convertBoardToState(board, null);
        StateEngine.generateFutureStates(state, false, null);
        assertEquals(20, state.getChildrenStates().size());
    }

    @Test
    public void testGetValidPieceMoves_MiddleOfBoard_ForWhite() throws Exception {
        char charBoard[][] = new char[][] {
                {'.', '.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', 'N', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.', '.'}
        };
        ChessBoard board = BoardConverter.convertCharBoardToByteBoard(charBoard);
        StateNode state = StateEngine.convertBoardToState(board, null);
        StateEngine.generateFutureStates(state, true, null);
        assertEquals(8, state.getChildrenStates().size());
    }

    @Test
    public void testGetValidPieceMoves_MiddleOfBoard_ForBlack() throws Exception {
        char charBoard[][] = new char[][] {
                {'.', '.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', 'n', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.', '.'}
        };
        ChessBoard board = BoardConverter.convertCharBoardToByteBoard(charBoard);
        StateNode state = StateEngine.convertBoardToState(board, null);
        StateEngine.generateFutureStates(state, false, null);
        assertEquals(8, state.getChildrenStates().size());
    }

    @Test
    public void testGetValidPieceMoves_MiddleOfBoard_CanCaptureSeveral_ForWhite() throws Exception {
        char charBoard[][] = new char[][] {
                {'.', '.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', 'p', '.', '.', '.'},
                {'.', '.', '.', '.', '.', 'p', '.', '.'},
                {'.', '.', '.', 'N', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', 'p', '.', '.'},
                {'.', '.', 'P', '.', 'p', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.', '.'}
        };
        ChessBoard board = BoardConverter.convertCharBoardToByteBoard(charBoard);
        StateNode state = StateEngine.convertBoardToState(board, null);
        StateEngine.generateFutureStates(state, true, null);
        assertEquals(8, state.getChildrenStates().size());
    }

    @Test
    public void testGetValidPieceMoves_MiddleOfBoard_CanCaptureSeveral_ForBlack() throws Exception {
        char charBoard[][] = new char[][] {
                {'.', '.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', 'p', '.', 'P', '.', '.', '.'},
                {'.', 'p', '.', '.', '.', 'P', '.', '.'},
                {'.', '.', '.', 'n', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.', '.'}
        };
        ChessBoard board = BoardConverter.convertCharBoardToByteBoard(charBoard);
        StateNode state = StateEngine.convertBoardToState(board, null);
        StateEngine.generateFutureStates(state, false, null);
        assertEquals(9, state.getChildrenStates().size());
    }
}
