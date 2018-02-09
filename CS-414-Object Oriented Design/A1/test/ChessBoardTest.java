package A1.test;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Before;

import A1.src.ChessBoard;
import A1.src.Color;
import A1.src.IllegalPositionException;
import A1.src.IllegalMoveException;
import A1.src.Bishop;
import A1.src.Rook;
import A1.src.Queen;
import A1.src.Pawn;
import A1.src.King;

public class ChessBoardTest{
	private ChessBoard chessBoard;

	@Before 
	public void initialize(){
		this.chessBoard = new ChessBoard();
	}

	@Test
	public void initializeTest(){
		chessBoard.initialize();
		// More intuitive way of viewing initilization
		// System.out.println(chessBoard.toString());
		try{
			// Selective Test
			assertEquals(chessBoard.getPiece("a1").getColor(), Color.WHITE);
			assertEquals(chessBoard.getPiece("f2").getColor(), Color.WHITE);
			assertEquals(chessBoard.getPiece("h8").getColor(), Color.BLACK);
			assertEquals(chessBoard.getPiece("b7").getColor(), Color.BLACK);

			assertEquals(chessBoard.getPiece("a2").toString(), "\u2659");
			assertEquals(chessBoard.getPiece("f7").toString(), "\u265F");
			assertEquals(chessBoard.getPiece("d8").toString(), "\u265B");
			assertEquals(chessBoard.getPiece("h1").toString(), "\u2656");

			assertNull(chessBoard.getPiece("a4"));
			assertNull(chessBoard.getPiece("e3"));
			assertNull(chessBoard.getPiece("g6"));
		}catch (IllegalPositionException ie){
			fail("IllegalPositionException");
		}
	}

	@Test
	public void getPieceTest(){
		chessBoard.initialize();
		try{
			// Selective JUnit Test
			// Test White Piece
			assertEquals(chessBoard.getPiece("a1").toString(), "\u2656");
			assertEquals(chessBoard.getPiece("f1").toString(), "\u2657");
			assertEquals(chessBoard.getPiece("d1").toString(), "\u2655");
			assertEquals(chessBoard.getPiece("e2").toString(), "\u2659");

			// Test Black Piece
			assertEquals(chessBoard.getPiece("b8").toString(), "\u265E");
			assertEquals(chessBoard.getPiece("c8").toString(), "\u265D");
			assertEquals(chessBoard.getPiece("e8").toString(), "\u265A");
			assertEquals(chessBoard.getPiece("h7").toString(), "\u265F");

			// Test Null piece
			assertNull(chessBoard.getPiece("f6"));
			assertNull(chessBoard.getPiece("e3"));
			assertNull(chessBoard.getPiece("c5"));
		} catch (IllegalPositionException ie){
			fail("IllegalPositionException");
		}

		try{
			// Selective JUnit Test
			// Test Illegal Case
			chessBoard.getPiece("a0");
			fail("IllegalPositionException");
		}catch (IllegalPositionException ie){
			assertTrue(true);
		}
		try{
			// Selective JUnit Test
			// Test Illegal Case
			chessBoard.getPiece("o2");
			fail("IllegalPositionException");
		}catch (IllegalPositionException ie){
			assertTrue(true);
		}
		try{
			// Selective JUnit Test
			// Test Illegal Case
			chessBoard.getPiece("a10");
			fail("IllegalPositionException");
		}catch (IllegalPositionException ie){
			assertTrue(true);
		}
		try{
			// Selective JUnit Test
			// Test Illegal Case
			chessBoard.getPiece("aa");
			fail("IllegalPositionException");
		}catch (IllegalPositionException ie){
			assertTrue(true);
		}
	}

	@Test
	public void placePieceTest(){
		// Test with initial board
		chessBoard.initialize();
		// Replace
		assertTrue(chessBoard.placePiece(new Bishop(chessBoard, Color.BLACK), "a2"));
		// Fill Blank
		assertTrue(chessBoard.placePiece(new Bishop(chessBoard, Color.BLACK), "f4"));
		// Illegal
		// Same piece
		assertFalse(chessBoard.placePiece(new Bishop(chessBoard, Color.BLACK), "a8"));
		// Illegal Pos
		assertFalse(chessBoard.placePiece(new Bishop(chessBoard, Color.BLACK), "g9"));

		// Test with new Board
		this.chessBoard = new ChessBoard();
		assertTrue(chessBoard.placePiece(new Bishop(chessBoard, Color.BLACK), "a8"));
		assertTrue(chessBoard.placePiece(new Bishop(chessBoard, Color.WHITE), "h1"));
	}

	@Test
	public void moveTest(){
		// TODO

		// 1. fromPosition should be null
		// 2. toPosition should be from piece
		// 3. other positions don't change

		// Test with initial board
		// Test Illegal Move 
		chessBoard.initialize();
		try{
			// Selective JUnit Test
			// Test Illegal Case
			chessBoard.move("a2", "b3");
			fail("IllegalMoveException");
		}catch (IllegalMoveException ie){
			assertTrue(true);
		}

		try{
			// Selective JUnit Test
			// Test Illegal Case
			chessBoard.move("e1", "e2");
			fail("IllegalMoveException");
		}catch (IllegalMoveException ie){
			assertTrue(true);
		}

		try{
			// Selective JUnit Test
			// Test Illegal Case
			chessBoard.move("c7", "c4");
			fail("IllegalMoveException");
		}catch (IllegalMoveException ie){
			assertTrue(true);
		}

		try{
			// Selective JUnit Test
			// Test Move f2 -> f4
			chessBoard.move("f2", "f4");
			assertNull(chessBoard.getPiece("f2"));
			assertEquals(chessBoard.getPiece("f4").toString(), "\u2659");

			// Test Move d7 -> d6
			chessBoard.move("d7", "d6");
			assertNull(chessBoard.getPiece("d7"));
			assertEquals(chessBoard.getPiece("d6").toString(), "\u265F");

		} catch (IllegalPositionException ie){
			fail("IllegalPositionException");
		} catch (IllegalMoveException ie){
			fail("IllegalPositionException");
		}

		// Test with new Board
		this.chessBoard = new ChessBoard();
		chessBoard.placePiece(new Bishop(chessBoard, Color.BLACK), "a8");
		chessBoard.placePiece(new Pawn(chessBoard, Color.BLACK), "c5");
		chessBoard.placePiece(new King(chessBoard, Color.BLACK), "f6");

		chessBoard.placePiece(new Pawn(chessBoard, Color.WHITE), "b4");
		chessBoard.placePiece(new Rook(chessBoard, Color.WHITE), "h6");
		chessBoard.placePiece(new Queen(chessBoard, Color.WHITE), "g2");


		try{
			// Selective JUnit Test
			// Test Illegal Case
			chessBoard.move("b4", "b6");
			fail("IllegalMoveException");
		}catch (IllegalMoveException ie){
			assertTrue(true);
		}

		try{
			// Selective JUnit Test
			// Test Illegal Case
			chessBoard.move("h6", "c6");
			fail("IllegalMoveException");
		}catch (IllegalMoveException ie){
			assertTrue(true);
		}

		try{
			// Selective JUnit Test
			// Test Illegal Case
			chessBoard.move("c5", "d4");
			fail("IllegalMoveException");
		}catch (IllegalMoveException ie){
			assertTrue(true);
		}

		try{
			// Selective JUnit Test
			// Test Move b4 -> c5
			chessBoard.move("b4", "c5");
			assertNull(chessBoard.getPiece("b4"));
			assertEquals(chessBoard.getPiece("c5").toString(), "\u2659");

			// Test Move a8 -> g2
			chessBoard.move("a8", "g2");
			assertNull(chessBoard.getPiece("a8"));
			assertEquals(chessBoard.getPiece("g2").toString(), "\u265D");

			// Test Move h6 -> f6
			chessBoard.move("h6", "f6");
			assertNull(chessBoard.getPiece("h6"));
			assertEquals(chessBoard.getPiece("f6").toString(), "\u2656");

		} catch (IllegalPositionException ie){
			fail("IllegalPositionException");
		} catch (IllegalMoveException ie){
			fail("IllegalPositionException");
		}
	}
}

/*
 character     piece
----------   -----------
  "\u2654"   white king
  "\u2655"   white queen
  "\u2656"   white rook
  "\u2657"   white bishop
  "\u2658"   white knight
  "\u2659"   white pawn
  "\u265A"   black king
  "\u265B"   black queen
  "\u265C"   black rook
  "\u265D"   black bishop
  "\u265E"   black knight
  "\u265F"   black pawn

*/