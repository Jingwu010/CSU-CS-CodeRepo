package A1.src;

import java.util.ArrayList;
import java.lang.StringBuilder;

public class ChessBoard{
	private ChessPiece[][] board;
	public ChessBoard(){
		// The no-arg constructor ChessBoard() initializes the board to an 8X8 array with all empty squares. An empty square is null.
		board = new ChessPiece[8][8];
		for (int i = 0; i < 8; i++){
			for (int j = 0; j < 8; j ++){
				board[i][j] = null;
			}
		}
	}

	public void initialize(){
		// This method initializes the board to the standard chess opening state with indexing as shown in the figure. This method should use the constructors of the appropriate pieces, and call placePiece below to place the newly constructed pieces in the right position.
		
		for (int j = 0; j < 8; j ++){
			this.placePiece(new Pawn(this, Color.WHITE), (char)('a' + j) + "2");
			this.placePiece(new Pawn(this, Color.BLACK), (char)('a' + j) + "7");
		}

		this.placePiece(new Rook(this, Color.WHITE), "a1");
		this.placePiece(new Rook(this, Color.WHITE), "h1");
		this.placePiece(new Rook(this, Color.BLACK), "a8");
		this.placePiece(new Rook(this, Color.BLACK), "h8");
		
		this.placePiece(new Knight(this, Color.WHITE), "b1");
		this.placePiece(new Knight(this, Color.WHITE), "g1");
		this.placePiece(new Knight(this, Color.BLACK), "b8");
		this.placePiece(new Knight(this, Color.BLACK), "g8");

		this.placePiece(new Bishop(this, Color.WHITE), "c1");
		this.placePiece(new Bishop(this, Color.WHITE), "f1");
		this.placePiece(new Bishop(this, Color.BLACK), "c8");
		this.placePiece(new Bishop(this, Color.BLACK), "f8");

		this.placePiece(new Queen(this, Color.WHITE), "d1");
		this.placePiece(new Queen(this, Color.BLACK), "d8");

		this.placePiece(new King(this, Color.WHITE), "e1");
		this.placePiece(new King(this, Color.BLACK), "e8");

	}

	private boolean checkPosition(String position){
		// If the position is illegal because the string contains illegal characters or represents a position outside the board, return false
		if (position.length() > 2){
			return false;
		}
		int p_col = position.charAt(0) - 'a';
		int p_row = position.charAt(1) - '1';
		if (p_row < 0 || p_row > 7){
			return false;
		}
		if (p_col < 0 || p_col > 7){
			return false;
		}
		return true;
	}

	public ChessPiece getPiece(String position) throws IllegalPositionException{
		// This method returns the chess piece at a given position. The position is represented as a two-character string (e.g., e8) as described above. The first letter is in lowercase (a..h) and the second letter is a digit (1..8). If the position is illegal because the string contains illegal characters or represents a position outside the board, the exception is thrown.
		// return a real piece or null
		int p_col = position.charAt(0) - 'a';
		int p_row = position.charAt(1) - '1';
		if (this.checkPosition(position)){
			return board[p_row][p_col];
		} else{
			throw new IllegalPositionException();
		}
	}

	public boolean placePiece(ChessPiece piece, String position){
		// This method tries to place the given piece at a given position, and returns true if successful, and false if there is already a piece of the same player in the given position or the position was illegal for any of the two reasons mentioned in the description of getPiece. If an opponent's piece exists, that piece is captured. If successful, this method should call an appropriate method in the ChessPiece class (i.e., setPosition) to set the piece's position.
		// if fail in checkPiecePos
		// if piece of same player in the given position
		if (piece == null) {
			board[position.charAt(1) - '1'][position.charAt(0) - 'a'] = piece;
			return true;
		}
		try{
			ChessPiece tmpChess = this.getPiece(position);
			if (tmpChess != null && tmpChess.getColor() == piece.getColor()){
				return false;
			}
			
			piece.setPosition(position);
			board[position.charAt(1) - '1'][position.charAt(0) - 'a'] = piece;
			return true;
		}catch (IllegalPositionException ie){
			return false;
		}
	}

	private boolean isStringInArrayList(String string, ArrayList<String> alist){
		if (alist == null || alist.isEmpty()) return false;
		if (string == null || string.isEmpty()) return false;
		if (alist.contains(string))
            return true;
        return false;
	}

	public void move(String fromPosition, String toPosition) throws IllegalMoveException{
		// This method checks if moving the piece from the fromPosition to toPosition is a legal move. Legality is defined based on the rules described above in Section 2.1. If the move is legal, it executes the move, changing the value of the board as needed. Otherwise, the stated exception is thrown.
		// it will not detect leaps
		// it will check for from status and to status
		try{
			ChessPiece fromChess = this.getPiece(fromPosition);
			ChessPiece toChess = this.getPiece(toPosition);

			ArrayList<String> legalMoves = fromChess.legalMoves();

			if (!isStringInArrayList(toPosition, legalMoves))
				throw new IllegalMoveException();
			if (toChess != null && fromChess.getColor() == toChess.getColor())
				throw new IllegalMoveException();
			if (!this.placePiece(fromChess, toPosition))
				throw new IllegalMoveException();
			if (!this.placePiece(null, fromPosition))
				throw new IllegalMoveException();
		}catch (IllegalPositionException ie){}
	}

	public String toString(){
	    String chess="";
	    String upperLeft = "\u250C";
	    String upperRight = "\u2510";
	    String horizontalLine = "\u2500";
	    String horizontal3 = horizontalLine + "\u3000" + horizontalLine;
	    String verticalLine = "\u2502";
	    String upperT = "\u252C";
	    String bottomLeft = "\u2514";
	    String bottomRight = "\u2518";
	    String bottomT = "\u2534";
	    String plus = "\u253C";
	    String leftT = "\u251C";
	    String rightT = "\u2524";

	    String topLine = upperLeft;
	    for (int i = 0; i<7; i++){
	        topLine += horizontal3 + upperT;
	    }
	    topLine += horizontal3 + upperRight;

	    String bottomLine = bottomLeft;
	    for (int i = 0; i<7; i++){
	        bottomLine += horizontal3 + bottomT;
	    }
	    bottomLine += horizontal3 + bottomRight;
	    chess+=topLine + "\n";

	    for (int row = 7; row >=0; row--){
	        String midLine = "";
	        for (int col = 0; col < 8; col++){
	            if(board[row][col]==null) {
	                midLine += verticalLine + " \u3000 ";
	            } else {midLine += verticalLine + " "+board[row][col]+"  ";}
	        }
	        midLine += verticalLine;
	        String midLine2 = leftT;
	        for (int i = 0; i<7; i++){
	            midLine2 += horizontal3 + plus;
	        }
	        midLine2 += horizontal3 + rightT;
	        chess+=midLine+ "\n";
	        if(row>=1)
	            chess+=midLine2+ "\n";
	    }

	    chess+=bottomLine;
	    return chess;
	}

	public static void main(String[] args) {
	    ChessBoard board = new ChessBoard();
	    board.initialize();
	    System.out.println(board);
	    try{
	    	board.move("c2", "c4");
	    } catch (IllegalMoveException ie){}	
	    System.out.println(board);
	}

}