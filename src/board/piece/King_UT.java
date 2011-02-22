package board.piece;

import board.BoardConverter;
import board.ChessBoard;
import board.move.ChessMove;
import board.move.ChessMoveBuilder;
import board.move.MoveTracker;
import org.junit.Before;
import org.junit.Test;
import state.StateEngine;
import state.StateNode;
import state.chooser.RandomStateChooser;

import static junit.framework.Assert.assertEquals;

/**
 * Created by IntelliJ IDEA.
 * User: vincent
 * Date: 18-Feb-2011
 * Time: 7:19:08 PM
 */
public class King_UT {
    @Before
    public void setUp() {
        MoveTracker.allMoves.clear();
    }

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

    @Test
    public void testGetValidPieceMoves_TestProblemForBlack() throws Exception {
        char charBoard[][] = new char[][] {
                {'.', '.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', 'b', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.', '.'},
                {'.', 'p', '.', '.', 'Q', 'k', '.', '.'},
                {'.', 'p', '.', '.', 'p', '.', '.', '.'},
                {'.', 'P', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.', 'r', '.'},
                {'.', '.', 'K', '.', '.', '.', 'n', '.'}
        };
        ChessBoard board = BoardConverter.convertCharBoardToByteBoard(charBoard);
        StateNode state = StateEngine.convertBoardToState(board, null);
        ChessMove move = new RandomStateChooser().chooseNextStateBasedOnCurrentState(state, false).getMove();
        assertEquals(4, state.getChildrenStates().size());
    }

    @Test
    public void testGetValidPieceMoves_TestProblemForWhite() throws Exception {
        char charBoard[][] = new char[][] {
                {'.', '.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', 'k', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.', 'n', '.'},
                {'p', '.', '.', '.', '.', 'p', '.', 'p'},
                {'.', '.', '.', '.', '.', '.', '.', '.'},
                {'.', 'K', 'b', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', 'p', '.', '.', '.'},
                {'.', 'b', '.', '.', '.', '.', '.', '.'}
        };
        ChessBoard board = BoardConverter.convertCharBoardToByteBoard(charBoard);
        StateNode state = StateEngine.convertBoardToState(board, null);
        ChessMove move = new RandomStateChooser().chooseNextStateBasedOnCurrentState(state, true).getMove();
        assertEquals(4, state.getChildrenStates().size());
    }

    @Test
    public void testGetValidPieceMoves_TestProblemForWhite2() throws Exception {
        char charBoard[][] = new char[][] {
                {'.', 'b', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', 'p', '.', '.'},
                {'.', '.', 'N', 'k', '.', '.', '.', '.'},
                {'.', '.', '.', 'Q', 'b', '.', '.', '.'},
                {'P', '.', '.', 'B', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', 'K', '.', '.', '.', '.', '.'}
        };
        ChessBoard board = BoardConverter.convertCharBoardToByteBoard(charBoard);
        StateNode state = StateEngine.convertBoardToState(board, null);
        ChessMove move = new RandomStateChooser().chooseNextStateBasedOnCurrentState(state, false).getMove();
        assertEquals(2, state.getChildrenStates().size());
    }

    @Test
    public void testGetValidPieceMoves_TestCastelingForWhite_KingSide() throws Exception {
        char charBoard[][] = new char[][] {
                {'.', '.', '.', '.', 'k', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', 'K', '.', '.', 'R'}
        };
        MoveTracker.allMoves.add(ChessMoveBuilder.buildChessMoveFromPositions(new PiecePosition(0, 0), new PiecePosition(0, 1)));
        ChessBoard board = BoardConverter.convertCharBoardToByteBoard(charBoard);
        StateNode state = StateEngine.convertBoardToState(board, null);
        ChessMove move = new RandomStateChooser().chooseNextStateBasedOnCurrentState(state, true).getMove();
        assertEquals(15, state.getChildrenStates().size());
    }

    @Test
    public void testGetValidPieceMoves_TestCastelingForWhite_QueenSide() throws Exception {
        char charBoard[][] = new char[][] {
                {'.', '.', '.', '.', 'k', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.', '.'},
                {'R', '.', '.', '.', 'K', '.', '.', '.'}
        };
        MoveTracker.allMoves.add(ChessMoveBuilder.buildChessMoveFromPositions(new PiecePosition(7, 0), new PiecePosition(7, 1)));
        ChessBoard board = BoardConverter.convertCharBoardToByteBoard(charBoard);
        StateNode state = StateEngine.convertBoardToState(board, null);
        ChessMove move = new RandomStateChooser().chooseNextStateBasedOnCurrentState(state, true).getMove();
        assertEquals(16, state.getChildrenStates().size());
    }

    @Test
    public void testGetValidPieceMoves_TestCastelingForWhite_KingSide_KingPassesThroughCheck() throws Exception {
        char charBoard[][] = new char[][] {
                {'.', '.', '.', '.', 'k', 'q', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', 'K', '.', '.', 'R'}
        };
        MoveTracker.allMoves.add(ChessMoveBuilder.buildChessMoveFromPositions(new PiecePosition(0, 0), new PiecePosition(0, 1)));
        ChessBoard board = BoardConverter.convertCharBoardToByteBoard(charBoard);
        StateNode state = StateEngine.convertBoardToState(board, null);
        ChessMove move = new RandomStateChooser().chooseNextStateBasedOnCurrentState(state, true).getMove();
        assertEquals(12, state.getChildrenStates().size());
    }

    @Test
    public void testGetValidPieceMoves_TestCastelingForWhite_QueenSide_KingPassesThroughCheck() throws Exception {
        char charBoard[][] = new char[][] {
                {'.', '.', '.', '.', 'k', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', 'b', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.', '.'},
                {'R', '.', '.', '.', 'K', '.', '.', '.'}
        };
        MoveTracker.allMoves.add(ChessMoveBuilder.buildChessMoveFromPositions(new PiecePosition(7, 0), new PiecePosition(7, 1)));
        ChessBoard board = BoardConverter.convertCharBoardToByteBoard(charBoard);
        StateNode state = StateEngine.convertBoardToState(board, null);
        ChessMove move = new RandomStateChooser().chooseNextStateBasedOnCurrentState(state, true).getMove();
        assertEquals(13, state.getChildrenStates().size());
    }
    @Test
    public void testGetValidPieceMoves_TestCastelingForBlack_KingSide() throws Exception {
        char charBoard[][] = new char[][] {
                {'.', '.', '.', '.', 'k', '.', '.', 'r'},
                {'.', '.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', 'K', '.', '.', '.'}
        };
        MoveTracker.allMoves.add(ChessMoveBuilder.buildChessMoveFromPositions(new PiecePosition(0, 7), new PiecePosition(0, 6)));
        ChessBoard board = BoardConverter.convertCharBoardToByteBoard(charBoard);
        StateNode state = StateEngine.convertBoardToState(board, null);
        ChessMove move = new RandomStateChooser().chooseNextStateBasedOnCurrentState(state, false).getMove();
        assertEquals(15, state.getChildrenStates().size());
    }

    @Test
    public void testGetValidPieceMoves_TestCastelingForBlack_QueenSide() throws Exception {
        char charBoard[][] = new char[][] {
                {'r', '.', '.', '.', 'k', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', 'K', '.', '.', '.'}
        };
        MoveTracker.allMoves.add(ChessMoveBuilder.buildChessMoveFromPositions(new PiecePosition(7, 7), new PiecePosition(7, 6)));
        ChessBoard board = BoardConverter.convertCharBoardToByteBoard(charBoard);
        StateNode state = StateEngine.convertBoardToState(board, null);
        ChessMove move = new RandomStateChooser().chooseNextStateBasedOnCurrentState(state, false).getMove();
        assertEquals(16, state.getChildrenStates().size());
    }

    @Test
    public void testGetValidPieceMoves_TestCastelingForBlack_KingSide_KingPassesThroughCheck() throws Exception {
        char charBoard[][] = new char[][] {
                {'.', '.', '.', '.', 'k', '.', '.', 'r'},
                {'.', '.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', 'K', 'Q', '.', '.'}
        };
        MoveTracker.allMoves.add(ChessMoveBuilder.buildChessMoveFromPositions(new PiecePosition(0, 7), new PiecePosition(0, 6)));
        ChessBoard board = BoardConverter.convertCharBoardToByteBoard(charBoard);
        StateNode state = StateEngine.convertBoardToState(board, null);
        ChessMove move = new RandomStateChooser().chooseNextStateBasedOnCurrentState(state, false).getMove();
        assertEquals(12, state.getChildrenStates().size());
    }

    @Test
    public void testGetValidPieceMoves_TestCastelingForBlack_QueenSide_KingPassesThroughCheck() throws Exception {
        char charBoard[][] = new char[][] {
                {'r', '.', '.', '.', 'k', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', 'B', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', 'K', '.', '.', '.'}
        };
        MoveTracker.allMoves.add(ChessMoveBuilder.buildChessMoveFromPositions(new PiecePosition(7, 7), new PiecePosition(7, 6)));
        ChessBoard board = BoardConverter.convertCharBoardToByteBoard(charBoard);
        StateNode state = StateEngine.convertBoardToState(board, null);
        ChessMove move = new RandomStateChooser().chooseNextStateBasedOnCurrentState(state, false).getMove();
        assertEquals(13, state.getChildrenStates().size());
    }
}
