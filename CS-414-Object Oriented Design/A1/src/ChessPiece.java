package A1.src;

import java.lang.StringBuilder;
import java.util.ArrayList;

public abstract class ChessPiece{
			
	protected ChessBoard chessBoard; // the board it belongs to, default null
	protected int row; // the index of the horizontal rows 0..7
	protected int column; // the index of the vertical column 0..7
	protected Color color; // the color of the piece

	public ChessPiece(ChessBoard chessBoard, Color color){
		// This constructor sets the board and color attributes.
		this.chessBoard = chessBoard;
		this.color = color;
	}

	public Color getColor(){
		// This method returns the color of the piece. There is no need for a setColor method because a piece cannot change color.
		return this.color;
	}

	public String getPosition(){
		// This method returns the position of the piece in the format single letter (a..h) followed by a single digit (1..8).
		StringBuilder sb = new StringBuilder();
		sb.append((char)('a'+this.column));
		sb.append((char)('1'+this.row));
		return sb.toString();
	}

	public void setPosition(String position) throws IllegalPositionException{
		// This method sets the position of the piece to the appropriate row and column based on the argument, which in the format single letter (a..h) followed by a single digit (1..8). If the position is illegal for any of the two reasons mentioned earlier, throw the stated exception.
		// only change piece infos
	    int p_col = position.charAt(0) - 'a';
		int p_row = position.charAt(1) - '1';
		try{
			
			ChessPiece tmpChess = chessBoard.getPiece(position);
			if (tmpChess != null && tmpChess.getColor() == this.color){
				throw new IllegalPositionException();
			}
			this.row = p_row;
			this.column = p_col;
			// board.placePiece(board.getPosition(position), position);
		}catch (IllegalPositionException ie){
			throw new IllegalPositionException();
		}
	}

	abstract public String toString();
	// This method will be implemented in the concrete subclasses corresponding to each chess piece. This method returns a String composed of a single character that corresponds to which piece it is. In the unicode character encoding scheme there are characters that represent each chess piece. Use one of the following characters:

	abstract public ArrayList<String> legalMoves();
	// This method will be implemented in the concrete subclasses corresponding to each chess piece. This method returns all the legal moves that piece can make based on the rules described above in the assignment.
}