package com.zxs.games.ChessTests;

import com.zxs.games.ChessProgram.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ChessBoardTest {
    public ChessBoard chessBoard = new ChessBoard(); // initial board, don't change it

    @Test
    public void constructorTest(){
        String answer = "RNBQKBNR\nPPPPPPPP\n........\n........\n........\n........\npppppppp\nrnbqkbnr\n";
        String board = chessBoard.toString();
        assertEquals(board, answer);
    }
    @Test
    public void constructorTest2(){
        String answer = "ANBQKBNR\nCPPPPPPP\n........\n........\n........\n........\ncppppppp\nanbqkbnr\n";
        ChessBoard board = new ChessBoard();
        board.chessBoard[0][0] = new RookKnight(0);
        board.chessBoard[1][0] = new BishopKnight(0);
        board.chessBoard[7][0] = new RookKnight(1);
        board.chessBoard[6][0] = new BishopKnight(1);
        String boardString = board.toString();
        assertEquals(boardString, answer);
    }
    @Test
    public void inBoundaryTest(){
        assertTrue(chessBoard.inBoundary(0,0,7,7));

    }

    @Test
    public void notInBoundaryTest(){
        assertFalse(chessBoard.inBoundary(0,0,8,7));
        assertFalse(chessBoard.inBoundary(-1,0,7,7));
        assertFalse(chessBoard.inBoundary(0,8,7,7));
        assertFalse(chessBoard.inBoundary(0,0,7,8));
        assertFalse(chessBoard.canMove(0,0,8,7));
    }

    @Test
    public void canMoveNoPieceTest(){
        assertFalse(chessBoard.canMove(3,0,2,0));
    }
    @Test
    public void canMoveOtherPieceTest(){
        assertFalse(chessBoard.canMove(6,0,0,0));
    }
    @Test
    public void canMoveCheckedTest(){
        ChessBoard board = new ChessBoard();
        assertTrue(board.canMove(1,3,2,3));
        board.chessBoard[3][1] = new Bishop(1);
        assertFalse(board.canMove(1,3,2,3));
    }

    @Test
    public void isNotCheckedTest(){
        assertFalse(chessBoard.isChecked(0));
        assertFalse(chessBoard.isChecked(1));
    }
    @Test
    public void isCheckedTest(){
        ChessBoard board = new ChessBoard();
        board.chessBoard[1][4] = new Queen(1);
        assertTrue(board.isChecked(0));
    }

    @Test
    public void isEndingCheckMateTest(){
        ChessBoard board= new ChessBoard();
        board.emptyChessBoard();
        board.chessBoard[3][5] = new King(1);
        board.chessBoard[3][7] = new King(0);
        board.chessBoard[7][7] = new Rook(1);
        assertTrue(board.isEnding() == 3);
    }
    //Famous Fool's Mate
    @Test
    public void isEndingCheckMateTest2(){
        ChessBoard board= new ChessBoard();
        board.turn = 1;
        board.chessBoard[0][3] = null;
        board.chessBoard[1][4] = null;
        board.chessBoard[6][5] = null;
        board.chessBoard[6][6] = null;
        board.chessBoard[3][4] = new Pawn(0);
        board.chessBoard[4][7] = new Queen(0);
        board.chessBoard[5][5] = new Pawn(1);
        board.chessBoard[4][6] = new Pawn(1);
        assertTrue(board.isEnding() == 2);
    }
    @Test
    public void isEndingStaleMateTest(){
        ChessBoard board= new ChessBoard();
        board.emptyChessBoard();
        board.chessBoard[5][2] = new King(1);
        board.chessBoard[6][1] = new Rook(1);
        board.chessBoard[7][0] = new King(0);
        assertTrue(board.isEnding() == 4);
    }

    @Test
    public void isEndingNotEndingTest(){
        assertTrue(chessBoard.isEnding() == 1);
    }

    @Test
    public void movePieceInvalidTest(){
        assertTrue(chessBoard.movePiece(0,0,0,1) == 0);
    }

    @Test
    public void movePieceValidTest(){
        ChessBoard board= new ChessBoard();
        assertTrue( board.movePiece(1,0,3,0)== 1);

    }

    @Test
    public void movePieceEndingTest(){
        ChessBoard board= new ChessBoard();
        board.emptyChessBoard();
        board.turn = 1;
        board.chessBoard[5][2] = new King(1);
        board.chessBoard[5][1] = new Rook(1);
        board.chessBoard[7][0] = new King(0);
        assertTrue( board.movePiece(5,1,6,1) == 4);
    }

    @Test
    public void undoTest(){
        ChessBoard board = new ChessBoard();
        assertTrue(board.undo() == null);
        board.movePiece(1,0,2,0);
        assertTrue(board.chessBoard[1][0] == null);
        moveStats lastMove = board.undo();
        assertTrue(lastMove.startRow ==1);
        assertTrue(lastMove.startPiece instanceof  Pawn);
        assertTrue(board.chessBoard[2][0] == null);
    }

    @Test
    public void undoTest2(){
        ChessBoard board = new ChessBoard();
        board.chessBoard[1][7] = null;
        board.movePiece(0,7,6,7);
        board.undo();
        assertTrue(board.chessBoard[0][7] instanceof  Rook);
        assertTrue(board.chessBoard[6][7] instanceof  Pawn);
    }

    @Test
    public void isPlayerPiece(){
        assertTrue(chessBoard.isCurrentPlayerPiece(1,0));
        assertFalse(chessBoard.isCurrentPlayerPiece(2,0));
        assertFalse(chessBoard.isCurrentPlayerPiece(6,0));
    }
}
