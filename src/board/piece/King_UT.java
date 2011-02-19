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
 * Time: 7:19:08 PM
 */
public class King_UT {
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
                {'.', '.', 'B', '.', 'K', 'B', '.', '.'}
        };
        ChessBoard board = BoardConverter.convertCharBoardToByteBoard(charBoard);
        StateNode state = StateEngine.convertBoardToState(board, null);
        StateEngine.generateFutureStates(state, true, null);
        assertEquals(17, state.getChildrenStates().size());
    }

    @Test
    public void testGetValidPieceMoves_LineOfPawns_ForBlack() throws Exception {
        char charBoard[][] = new char[][] {
                {'.', '.', 'b', 'k', '.', 'b', '.', '.'},
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
        assertEquals(17, state.getChildrenStates().size());
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
                {'P', '.', 'P', '.', 'P', 'P', '.', 'P'},
                {'.', '.', 'K', '.', '.', '.', '.', '.'}
        };
        ChessBoard board = BoardConverter.convertCharBoardToByteBoard(charBoard);
        StateNode state = StateEngine.convertBoardToState(board, null);
        StateEngine.generateFutureStates(state, true, null);
        assertEquals(14, state.getChildrenStates().size());
    }

    @Test
    public void testGetValidPieceMoves_LineOfPawns_WithGap_ForBlack() throws Exception {
        char charBoard[][] = new char[][] {
                {'.', '.', 'k', '.', '.', '.', '.', '.'},
                {'p', '.', 'p', '.', 'p', 'p', '.', 'p'},
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
        assertEquals(14, state.getChildrenStates().size());
    }

    @Test
    public void testGetValidPieceMoves_KingInCenter_ForWhite() throws Exception {
        char charBoard[][] = new char[][] {
                {'.', '.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', 'K', '.', '.', '.', '.'},
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
    public void testGetValidPieceMoves_LineOfPawns_KingInCenter_ForBlack() throws Exception {
        char charBoard[][] = new char[][] {
                {'.', '.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', 'k', '.', '.', '.', '.'},
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
    public void testGetValidPieceMoves_KingInCenter_CaptureAndBlocking_ForWhite() throws Exception {
        char charBoard[][] = new char[][] {
                {'.', '.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', 'p', '.', '.', '.'},
                {'.', '.', '.', 'K', '.', '.', '.', '.'},
                {'.', '.', 'p', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', 'P', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.', '.'}
        };
        ChessBoard board = BoardConverter.convertCharBoardToByteBoard(charBoard);
        StateNode state = StateEngine.convertBoardToState(board, null);
        StateEngine.generateFutureStates(state, true, null);
        assertEquals(9, state.getChildrenStates().size());
    }

    @Test
    public void testGetValidPieceMoves_LineOfPawns_KingInCenter_CaptureAndBlocking_ForBlack() throws Exception {
        char charBoard[][] = new char[][] {
                {'.', '.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', 'p', '.', 'P', '.', '.', '.'},
                {'.', '.', '.', 'k', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', 'p', '.', '.'},
                {'P', '.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.', '.'}
        };
        ChessBoard board = BoardConverter.convertCharBoardToByteBoard(charBoard);
        StateNode state = StateEngine.convertBoardToState(board, null);
        StateEngine.generateFutureStates(state, false, null);
        assertEquals(9, state.getChildrenStates().size());
    }
}
