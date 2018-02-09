package A1.test;

import java.util.Arrays;
import java.util.ArrayList;
import java.lang.StringBuilder;
import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Before;

import A1.src.ChessBoard;
import A1.src.Knight;
import A1.src.Color;
import A1.src.IllegalPositionException;

public class KnightTest{
	private ChessBoard chessBoard;
	private Knight piece;

	@Before
	public void init(){
		chessBoard = new ChessBoard();
		chessBoard.initialize();
	}

	@Test
	public void ChessPieceTest(){
		// Test Constructor
		// a1 is initial position for all
		piece = new Knight(chessBoard, Color.WHITE);
		assertTrue(piece.getColor() == Color.WHITE);
		assertEquals(piece.getPosition(), "a1");
		piece = new Knight(chessBoard, Color.BLACK);
		assertTrue(piece.getColor() == Color.BLACK);
		assertEquals(piece.getPosition(), "a1");
	}

	@Test
	public void getColorTest(){
		// Test getColor method
		// Simple test
		piece = new Knight(chessBoard, Color.WHITE);
		assertTrue(piece.getColor() == Color.WHITE);
		piece = new Knight(chessBoard, Color.BLACK);
		assertTrue(piece.getColor() == Color.BLACK);
	}

	@Test
	public void getPositionTest(){
		// Test getPosition method
		// Initially always return "a1"
		// Test more in setPositionTest
		piece = new Knight(chessBoard, Color.WHITE);
		assertEquals(piece.getPosition(), "a1");
		piece = new Knight(chessBoard, Color.BLACK);
		assertEquals(piece.getPosition(), "a1");
	}
	
	@Test
	public void setPositionTest(){
		piece = new Knight(chessBoard, Color.BLACK);
		try{
			// Test legal position
			piece.setPosition("a1");
			assertEquals(piece.getPosition(), "a1");
			piece.setPosition("h2");
			assertEquals(piece.getPosition(), "h2");
			piece.setPosition("f5");
			assertEquals(piece.getPosition(), "f5");
		} catch (IllegalPositionException ie){
			fail("IllegalPositionException");
		}

		try{
			// Test illegal position : Occupied by same type
			piece.setPosition("a7");
			fail("IllegalPositionException");
		} catch (IllegalPositionException ie){
			assertTrue(true);
		}
		try{
			// Test illegal position : Occupied by same type
			piece.setPosition("g8");
			fail("IllegalPositionException");
		} catch (IllegalPositionException ie){
			assertTrue(true);
		}

		try{
			// Test illegal position : Illegal Position
			piece.setPosition("a0");
			fail("IllegalPositionException");
		} catch (IllegalPositionException ie){
			assertTrue(true);
		}
		try{
			// Test illegal position : Illegal Position
			piece.setPosition("k10");
			fail("IllegalPositionException");
		} catch (IllegalPositionException ie){
			assertTrue(true);
		}
		try{
			// Test illegal position : Illegal Position
			piece.setPosition("23");
			fail("IllegalPositionException");
		} catch (IllegalPositionException ie){
			assertTrue(true);
		}
	}

	@Test
	public void toStringTest(){
		// Test WHITE color piece
		piece = new Knight(chessBoard, Color.WHITE);
		assertTrue(piece.toString() == "\u2658");
		assertFalse(piece.toString() == "\u265E");

		// Test BLACK color piece
		piece = new Knight(chessBoard, Color.BLACK);
		assertFalse(piece.toString() == "\u2658");
		assertTrue(piece.toString() == "\u265E");
	}

	@Test
	public void legalMovesTest(){
		// There is no need for this test
		piece = new Knight(chessBoard, Color.BLACK);
		assertTrue(piece.legalMoves().isEmpty());
	}
}