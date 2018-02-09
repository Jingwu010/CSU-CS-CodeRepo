package A1.test;

import java.util.Arrays;
import java.util.ArrayList;
import java.lang.StringBuilder;
import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Before;

import A1.src.ChessBoard;
import A1.src.King;
import A1.src.Color;
import A1.src.IllegalPositionException;

public class KingTest{
	private ChessBoard chessBoard;
	private King piece;

	@Before
	public void init(){
		chessBoard = new ChessBoard();
		chessBoard.initialize();
	}

	@Test
	public void ChessPieceTest(){
		// Test Constructor
		// a1 is initial position for all
		piece = new King(chessBoard, Color.WHITE);
		assertTrue(piece.getColor() == Color.WHITE);
		assertEquals(piece.getPosition(), "a1");
		piece = new King(chessBoard, Color.BLACK);
		assertTrue(piece.getColor() == Color.BLACK);
		assertEquals(piece.getPosition(), "a1");
	}

	@Test
	public void getColorTest(){
		// Test getColor method
		// Simple test
		piece = new King(chessBoard, Color.WHITE);
		assertTrue(piece.getColor() == Color.WHITE);
		piece = new King(chessBoard, Color.BLACK);
		assertTrue(piece.getColor() == Color.BLACK);
	}

	@Test
	public void getPositionTest(){
		// Test getPosition method
		// Initially always return "a1"
		// Test more in setPositionTest
		piece = new King(chessBoard, Color.WHITE);
		assertEquals(piece.getPosition(), "a1");
		piece = new King(chessBoard, Color.BLACK);
		assertEquals(piece.getPosition(), "a1");
	}
	
	@Test
	public void setPositionTest(){
		piece = new King(chessBoard, Color.WHITE);
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
		piece = new King(chessBoard, Color.WHITE);
		assertTrue(piece.toString() == "\u2654");
		assertFalse(piece.toString() == "\u265A");

		// Test BLACK color piece
		piece = new King(chessBoard, Color.BLACK);
		assertFalse(piece.toString() == "\u2654");
		assertTrue(piece.toString() == "\u265A");
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
		piece = new King(chessBoard, Color.WHITE);
		assertTrue(piece.legalMoves().isEmpty());
		// assertTrue(arrayList2String(piece.legalMoves()) == "");

		try{
			piece.setPosition("a3");
			ArrayList<String> a3 = new ArrayList<>(Arrays.asList("a4", "b4", "b3"));
			assertTrue(isTwoArrayListsWithSameValues(a3, piece.legalMoves()));
		} catch (IllegalPositionException ie){
			fail("IllegalPositionException");
		}

		try{
			piece.setPosition("h6");
			ArrayList<String> h6 = new ArrayList<>(Arrays.asList("h5", "g5", "g6", "g7", "h7"));
			// ArrayList<String> h6 = new ArrayList<>(Arrays.asList("h5", "g5", "g6"));
			assertTrue(isTwoArrayListsWithSameValues(h6, piece.legalMoves()));
		} catch (IllegalPositionException ie){
			fail("IllegalPositionException");
		}

		try{
			piece.setPosition("d4");
			ArrayList<String> d4 = new ArrayList<>(Arrays.asList("d3", "c3", "c4", "c5", "d5", "e5", "e4", "e3"));
			assertTrue(isTwoArrayListsWithSameValues(d4, piece.legalMoves()));
		} catch (IllegalPositionException ie){
			fail("IllegalPositionException");
		}

		// Test BLACK Color
		chessBoard.initialize();
		piece = new King(chessBoard, Color.BLACK);


		try{
			piece.setPosition("c6");
			ArrayList<String> c6 = new ArrayList<>(Arrays.asList("b6", "b5", "c5", "d5", "d6"));
			assertTrue(isTwoArrayListsWithSameValues(c6, piece.legalMoves()));
		} catch (IllegalPositionException ie){
			fail("IllegalPositionException");
		}
		try{
			piece.setPosition("e1");
			ArrayList<String> e1 = new ArrayList<>(Arrays.asList("d1", "d2", "e2", "f2", "f1"));
			// ArrayList<String> e1 = new ArrayList<>(Arrays.asList());
			assertTrue(isTwoArrayListsWithSameValues(e1, piece.legalMoves()));
		} catch (IllegalPositionException ie){
			fail("IllegalPositionException");
		}
		try{
			piece.setPosition("f3");
			ArrayList<String> f3 = new ArrayList<>(Arrays.asList("e2", "e3", "e4", "f4", "g4", "g3", "g2", "f2"));
			assertTrue(isTwoArrayListsWithSameValues(f3, piece.legalMoves()));
		} catch (IllegalPositionException ie){
			fail("IllegalPositionException");
		}
	}
}