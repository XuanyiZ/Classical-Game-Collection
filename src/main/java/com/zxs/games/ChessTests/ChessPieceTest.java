package com.zxs.games.ChessTests;

import com.zxs.games.ChessProgram.*;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ChessPieceTest {
    public ChessBoard chessBoard = new ChessBoard(); // initial board, don't change it
    @Test
    public void canMovePawnStartTest(){
        assertTrue(chessBoard.canMove(1,7,2,7));
        assertTrue(chessBoard.canMove(1,7,3,7));
        assertFalse(chessBoard.canMove(1,7,4,7));
        ChessBoard board = new ChessBoard(1);
        assertTrue(board.canMove(6,0,5,0));
        assertTrue(board.canMove(6,0,4,0));
        assertFalse(board.canMove(6,0,3,0));
        assertFalse(board.canMove(6,0,5,1));

    }
    @Test
    public void canMovePawnMiddleTest(){
        ChessBoard board = new ChessBoard();
        board.chessBoard[4][0] = new Pawn(0);
        assertTrue(board.canMove(4,0,5,0));
        assertFalse(board.canMove(4,0,6,0));
        assertFalse(board.canMove(4,0,5,1));
        assertFalse(board.canMove(4,0,4,1));
    }
    @Test
    public void canMovePawnEatTest(){
        ChessBoard board = new ChessBoard();
        board.chessBoard[5][0] = new Pawn(0);
        assertTrue(board.canMove(5,0,6,1));
        assertFalse(board.canMove(5,0,6,0));
        assertFalse(board.canMove(5,0,6,2));
        board = new ChessBoard(1);
        board.chessBoard[2][1] = new Pawn(1);
        assertTrue(board.canMove(2,1,1,0));
        assertTrue(board.canMove(2,1,1,2));
        assertFalse(board.canMove(2,1,1,1));
        assertFalse(board.canMove(2,1,1,4));
    }
    @Test
    public void canMovePawnInvalidTest(){
        assertFalse(chessBoard.canMove(1,7,4,7));
    }

    @Test
    public void canMoveKnightTest(){
        assertTrue(chessBoard.canMove(0,1,2,0));
        assertFalse(chessBoard.canMove(0,1,2,1));
        assertFalse(chessBoard.canMove(0,1,1,3));
    }

    @Test
    public void canMoveKingTest(){
        ChessBoard board = new ChessBoard();
        assertFalse(board.canMove(0,4,1,4));
        board.chessBoard[1][4] = null;
        assertTrue(board.canMove(0,4,1,4));
        assertFalse(board.canMove(0,4,2,4));
    }
    @Test
    public void canMoveRookVerticalTest(){
        ChessBoard board = new ChessBoard();
        assertFalse(board.canMove(0,0,3,0));
        board.chessBoard[1][0] = null;
        assertTrue(board.canMove(0,0,6,0));
        assertFalse(board.canMove(0,0,7,0));
    }
    @Test
    public void canMoveRookHorizontalTest(){
        ChessBoard board = new ChessBoard();
        board.chessBoard[0][1] = null;
        assertTrue(board.canMove(0,0,0,1));
        assertFalse(board.canMove(0,0,0,2));
        board.chessBoard[0][3] = null;
        assertFalse(board.canMove(0,0,0,3));
    }
    @Test
    public void canMoveBishopTest(){
        ChessBoard board = new ChessBoard();
        assertFalse(board.canMove(0,2,1,3));
        board.chessBoard[1][3] = null;
        assertTrue(board.canMove(0,2,5,7));
        assertFalse(board.canMove(0,0,0,0));
    }
    @Test
    public void canMoveQueenTest() {
        ChessBoard board = new ChessBoard();
        assertFalse(board.canMove(0, 3, 1, 3));
        board.chessBoard[1][3] = null;
        assertTrue(board.canMove(0, 3, 6, 3));
        assertFalse(board.canMove(0, 3, 7, 3));
        assertFalse(board.canMove(0, 3, 3, 0));
        board.chessBoard[1][2] = null;
        assertTrue(board.canMove(0, 3, 3, 0));
    }
    @Test
    public void canMoveRookKnightTest(){
        ChessBoard board = new ChessBoard();
        board.chessBoard[1][0] = new RookKnight(0);
        assertTrue(board.canMove(1,0,6,0));
        assertTrue(board.canMove(1,0,2,2));
    }
    @Test
    public void canMoveBishopKnightTest(){
        ChessBoard board = new ChessBoard();
        board.chessBoard[1][0] = new BishopKnight(0);
        assertTrue(board.canMove(1,0,6,5));
        assertTrue(board.canMove(1,0,2,2));
    }

    @Test
    public void filePathTest(){
        ChessBoard board = new ChessBoard(0,false);
        assertEquals(board.chessBoard[0][0].getIconFileName(),"BlackRook.png");
        assertEquals(board.chessBoard[0][1].getIconFileName(),"BlackKnight.png");
        assertEquals(board.chessBoard[1][0].getIconFileName(),"BlackPawn.png");
        assertEquals(board.chessBoard[0][2].getIconFileName(),"BlackBishopKnight.png");
        assertEquals(board.chessBoard[0][3].getIconFileName(),"BlackQueen.png");
        assertEquals(board.chessBoard[0][4].getIconFileName(),"BlackKing.png");
        assertEquals(board.chessBoard[0][5].getIconFileName(),"BlackBishop.png");
        assertEquals(board.chessBoard[0][7].getIconFileName(),"BlackRookKnight.png");
        assertEquals(board.chessBoard[7][0].getIconFileName(),"WhiteRook.png");
        assertEquals(board.chessBoard[7][1].getIconFileName(),"WhiteKnight.png");
        assertEquals(board.chessBoard[6][0].getIconFileName(),"WhitePawn.png");
        assertEquals(board.chessBoard[7][2].getIconFileName(),"WhiteBishopKnight.png");
        assertEquals(board.chessBoard[7][3].getIconFileName(),"WhiteQueen.png");
        assertEquals(board.chessBoard[7][4].getIconFileName(),"WhiteKing.png");
        assertEquals(board.chessBoard[7][5].getIconFileName(),"WhiteBishop.png");
        assertEquals(board.chessBoard[7][7].getIconFileName(),"WhiteRookKnight.png");
    }
}
