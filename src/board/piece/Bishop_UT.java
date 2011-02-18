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
 * Time: 4:39:51 PM
 */
public class Bishop_UT {
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
                {'.', '.', 'B', '.', '.', 'B', '.', '.'}
        };
        ChessBoard board = BoardConverter.convertCharBoardToByteBoard(charBoard);
        StateNode state = StateEngine.convertBoardToState(board, null);
        StateEngine.generateFutureStates(state, true, null);
        assertEquals(16, state.getChildrenStates().size());
    }

    @Test
    public void testGetValidPieceMoves_LineOfPawns_ForBlack() throws Exception {
        char charBoard[][] = new char[][] {
                {'.', '.', 'b', '.', '.', 'b', '.', '.'},
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
        assertEquals(16, state.getChildrenStates().size());
    }

    @Test
    public void testGetValidPieceMoves_LineOfPawns_WithGap_ForWhite() throws Exception {
        char charBoard[][] = new char[][] {
                {'.', '.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.', '.'},
                {'P', '.', 'P', 'P', 'P', 'P', '.', 'P'},
                {'.', '.', 'B', '.', '.', 'B', '.', '.'}
        };
        ChessBoard board = BoardConverter.convertCharBoardToByteBoard(charBoard);
        StateNode state = StateEngine.convertBoardToState(board, null);
        StateEngine.generateFutureStates(state, true, null);
        assertEquals(16, state.getChildrenStates().size());
    }

    @Test
    public void testGetValidPieceMoves_LineOfPawns_WithGap_ForBlack() throws Exception {
        char charBoard[][] = new char[][] {
                {'.', '.', 'b', '.', '.', 'b', '.', '.'},
                {'p', '.', 'p', 'p', 'p', 'p', '.', 'p'},
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
        assertEquals(16, state.getChildrenStates().size());
    }

    @Test
    public void testGetValidPieceMoves_BishopInCenter_ForWhite() throws Exception {
        char charBoard[][] = new char[][] {
                {'.', '.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', 'B', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.', '.'}
        };
        ChessBoard board = BoardConverter.convertCharBoardToByteBoard(charBoard);
        StateNode state = StateEngine.convertBoardToState(board, null);
        StateEngine.generateFutureStates(state, true, null);
        assertEquals(13, state.getChildrenStates().size());
    }

    @Test
    public void testGetValidPieceMoves_LineOfPawns_BishopInCenter_ForBlack() throws Exception {
        char charBoard[][] = new char[][] {
                {'.', '.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', 'b', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.', '.'}
        };
        ChessBoard board = BoardConverter.convertCharBoardToByteBoard(charBoard);
        StateNode state = StateEngine.convertBoardToState(board, null);
        StateEngine.generateFutureStates(state, false, null);
        assertEquals(13, state.getChildrenStates().size());
    }

    @Test
    public void testGetValidPieceMoves_BishopInCenter_CaptureAndBlocking_ForWhite() throws Exception {
        char charBoard[][] = new char[][] {
                {'.', '.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', 'p', '.', '.', '.'},
                {'.', '.', '.', 'B', '.', '.', '.', '.'},
                {'.', '.', 'p', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', 'P', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.', '.'}
        };
        ChessBoard board = BoardConverter.convertCharBoardToByteBoard(charBoard);
        StateNode state = StateEngine.convertBoardToState(board, null);
        StateEngine.generateFutureStates(state, true, null);
        assertEquals(7, state.getChildrenStates().size());
    }

    @Test
    public void testGetValidPieceMoves_LineOfPawns_BishopInCenter_CaptureAndBlocking_ForBlack() throws Exception {
        char charBoard[][] = new char[][] {
                {'.', '.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', 'p', '.', 'P', '.', '.', '.'},
                {'.', '.', '.', 'b', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', 'p', '.', '.'},
                {'P', '.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.', '.'}
        };
        ChessBoard board = BoardConverter.convertCharBoardToByteBoard(charBoard);
        StateNode state = StateEngine.convertBoardToState(board, null);
        StateEngine.generateFutureStates(state, false, null);
        assertEquals(7, state.getChildrenStates().size());
    }
}
