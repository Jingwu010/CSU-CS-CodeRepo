package A1.src;

import java.util.ArrayList;

public class Queen extends ChessPiece{

	public Queen(ChessBoard chessBoard, Color color){
		super(chessBoard, color);
	}

	public String toString(){
		if (this.getColor() == Color.WHITE){
			return "\u2655";
		} else{
			return "\u265B";
		}
	}

	public ArrayList<String> legalMoves(){
		return new ArrayList<String>();
	}
}