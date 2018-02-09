package A1.test;

import java.util.Arrays;
import java.util.ArrayList;
import java.lang.StringBuilder;
import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Before;

import A1.src.ChessBoard;
import A1.src.Rook;
import A1.src.Color;
import A1.src.IllegalPositionException;

public class RookTest{
	private ChessBoard chessBoard;
	private Rook piece;

	@Before
	public void init(){
		chessBoard = new ChessBoard();
		chessBoard.initialize();
	}

	@Test
	public void ChessPieceTest(){
		// Test Constructor
		// a1 is initial position for all
		piece = new Rook(chessBoard, Color.WHITE);
		assertTrue(piece.getColor() == Color.WHITE);
		assertEquals(piece.getPosition(), "a1");
		piece = new Rook(chessBoard, Color.BLACK);
		assertTrue(piece.getColor() == Color.BLACK);
		assertEquals(piece.getPosition(), "a1");
	}

	@Test
	public void getColorTest(){
		// Test getColor method
		// Simple test
		piece = new Rook(chessBoard, Color.WHITE);
		assertTrue(piece.getColor() == Color.WHITE);
		piece = new Rook(chessBoard, Color.BLACK);
		assertTrue(piece.getColor() == Color.BLACK);
	}

	@Test
	public void getPositionTest(){
		// Test getPosition method
		// Initially always return "a1"
		// Test more in setPositionTest
		piece = new Rook(chessBoard, Color.WHITE);
		assertEquals(piece.getPosition(), "a1");
		piece = new Rook(chessBoard, Color.BLACK);
		assertEquals(piece.getPosition(), "a1");
	}
	
	@Test
	public void setPositionTest(){
		piece = new Rook(chessBoard, Color.WHITE);
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
		piece = new Rook(chessBoard, Color.WHITE);
		assertTrue(piece.toString() == "\u2656");
		assertFalse(piece.toString() == "\u265C");

		// Test BLACK color piece
		piece = new Rook(chessBoard, Color.BLACK);
		assertFalse(piece.toString() == "\u2656");
		assertTrue(piece.toString() == "\u265C");
	}

	private String arrayList2String(ArrayList<String> alist){
		StringBuilder sb = new StringBuilder();
		for (String s : alist){
		    sb.append(s);
		    sb.append(";");
		}
		return sb.toString();
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
		chessBoard.initialize();
		piece = new Rook(chessBoard, Color.WHITE);
		assertTrue(piece.legalMoves().isEmpty());
		// assertTrue(arrayList2String(piece.legalMoves()) == "");

		try{
			piece.setPosition("a4");
			ArrayList<String> a4 = new ArrayList<>(Arrays.asList("a3", "a5", "a6", "a7", "b4", "c4", "d4", "e4", "f4", "g4", "h4"));
			assertTrue(isTwoArrayListsWithSameValues(a4, piece.legalMoves()));
		} catch (IllegalPositionException ie){
			fail("IllegalPositionException");
		}

		try{
			piece.setPosition("e5");
			ArrayList<String> e5 = new ArrayList<>(Arrays.asList("a5", "b5", "c5", "d5", "f5", "g5", "h5", "e4", "e3", "e6", "e7"));
			assertTrue(isTwoArrayListsWithSameValues(e5, piece.legalMoves()));
		} catch (IllegalPositionException ie){
			fail("IllegalPositionException");
		}

		try{
			piece.setPosition("h8");
			ArrayList<String> h8 = new ArrayList<>(Arrays.asList("g8", "h7"));
			assertTrue(isTwoArrayListsWithSameValues(h8, piece.legalMoves()));
		} catch (IllegalPositionException ie){
			fail("IllegalPositionException");
		}
	}
}