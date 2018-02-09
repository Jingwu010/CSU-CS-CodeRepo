package A1.test;

import java.util.Arrays;
import java.util.ArrayList;
import java.lang.StringBuilder;
import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Before;

import A1.src.ChessBoard;
import A1.src.Bishop;
import A1.src.Color;
import A1.src.IllegalPositionException;

public class BishopTest{
	private ChessBoard chessBoard;
	private Bishop piece;

	@Before
	public void init(){
		chessBoard = new ChessBoard();
		chessBoard.initialize();
	}

	@Test
	public void ChessPieceTest(){
		// Test Constructor
		// a1 is initial position for all
		piece = new Bishop(chessBoard, Color.WHITE);
		assertTrue(piece.getColor() == Color.WHITE);
		assertEquals(piece.getPosition(), "a1");
		piece = new Bishop(chessBoard, Color.BLACK);
		assertTrue(piece.getColor() == Color.BLACK);
		assertEquals(piece.getPosition(), "a1");
	}

	@Test
	public void getColorTest(){
		// Test getColor method
		// Simple test
		piece = new Bishop(chessBoard, Color.WHITE);
		assertTrue(piece.getColor() == Color.WHITE);
		piece = new Bishop(chessBoard, Color.BLACK);
		assertTrue(piece.getColor() == Color.BLACK);
	}

	@Test
	public void getPositionTest(){
		// Test getPosition method
		// Initially always return "a1"
		// Test more in setPositionTest
		piece = new Bishop(chessBoard, Color.WHITE);
		assertEquals(piece.getPosition(), "a1");
		piece = new Bishop(chessBoard, Color.BLACK);
		assertEquals(piece.getPosition(), "a1");
	}
	
	@Test
	public void setPositionTest(){
		piece = new Bishop(chessBoard, Color.WHITE);
		try{
			// Test legal position
			piece.setPosition("a8");
			assertEquals(piece.getPosition(), "a8");
			piece.setPosition("b7");
			assertEquals(piece.getPosition(), "b7");
			piece.setPosition("g3");
			assertEquals(piece.getPosition(), "g3");
		} catch (IllegalPositionException ie){
			fail("IllegalPositionException");
		}

		try{
			// Test illegal position : Occupied by same type
			piece.setPosition("a2");
			fail("IllegalPositionException");
		} catch (IllegalPositionException ie){
			assertTrue(true);
		}
		try{
			// Test illegal position : Occupied by same type
			piece.setPosition("g1");
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
		piece = new Bishop(chessBoard, Color.WHITE);
		assertTrue(piece.toString() == "\u2657");
		assertFalse(piece.toString() == "\u265D");

		// Test BLACK color piece
		piece = new Bishop(chessBoard, Color.BLACK);
		assertFalse(piece.toString() == "\u2657");
		assertTrue(piece.toString() == "\u265D");
	}

	// https://stackoverflow.com/questions/2989987/how-can-i-check-if-two-arraylist-differ-i-dont-care-whats-changed
	private boolean isTwoArrayListsWithSameValues(ArrayList<String> list1, ArrayList<String> list2){
        //null checking
        if(list1==null && list2==null)
            return true;
        if((list1 == null && list2 != null) || (list1 != null && list2 == null))
            return false;

        if(list1.size()!=list2.size())
            return false;
        for(String itemList1: list1){
            if(!list2.contains(itemList1))
                return false;
        }
        return true;
    }

	@Test
	public void legalMovesTest(){
		// Restore Initial State
		// Test WHITE Color
		chessBoard.initialize();
		piece = new Bishop(chessBoard, Color.WHITE);
		assertTrue(piece.legalMoves().isEmpty());
		// assertTrue(arrayList2String(piece.legalMoves()) == "");

		try{
			piece.setPosition("b5");
			ArrayList<String> b5 = new ArrayList<>(Arrays.asList("a4", "c4", "a6", "c6", "d7", "d3"));
			assertTrue(isTwoArrayListsWithSameValues(b5, piece.legalMoves()));
		} catch (IllegalPositionException ie){
			fail("IllegalPositionException");
		}

		try{
			piece.setPosition("h7");
			ArrayList<String> h7 = new ArrayList<>(Arrays.asList("g8", "g6", "f5", "e4", "d3"));
			assertTrue(isTwoArrayListsWithSameValues(h7, piece.legalMoves()));
		} catch (IllegalPositionException ie){
			fail("IllegalPositionException");
		}

		try{
			piece.setPosition("d8");
			ArrayList<String> d8 = new ArrayList<>(Arrays.asList("c7", "e7"));
			assertTrue(isTwoArrayListsWithSameValues(d8, piece.legalMoves()));
		} catch (IllegalPositionException ie){
			fail("IllegalPositionException");
		}

		// Test BLACK Color
		chessBoard.initialize();
		piece = new Bishop(chessBoard, Color.BLACK);
		ArrayList<String> a1 = new ArrayList<>(Arrays.asList("b2"));
		assertTrue(isTwoArrayListsWithSameValues(a1, piece.legalMoves()));

		try{
			piece.setPosition("g1");
			ArrayList<String> g1 = new ArrayList<>(Arrays.asList("h2", "f2"));
			assertTrue(isTwoArrayListsWithSameValues(g1, piece.legalMoves()));
		} catch (IllegalPositionException ie){
			fail("IllegalPositionException");
		}
		try{
			piece.setPosition("a6");
			ArrayList<String> a6 = new ArrayList<>(Arrays.asList("b5", "c4", "d3", "e2"));
			assertTrue(isTwoArrayListsWithSameValues(a6, piece.legalMoves()));
		} catch (IllegalPositionException ie){
			fail("IllegalPositionException");
		}
		try{
			piece.setPosition("d4");
			ArrayList<String> d4 = new ArrayList<>(Arrays.asList("c3", "b2", "e3", "f2", "c5", "b6", "e5", "f6"));
			assertTrue(isTwoArrayListsWithSameValues(d4, piece.legalMoves()));
		} catch (IllegalPositionException ie){
			fail("IllegalPositionException");
		}
	}
}