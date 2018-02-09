package A1.src;

import java.util.ArrayList;

public class Knight extends ChessPiece{
		
	public Knight(ChessBoard chessBoard, Color color){
		super(chessBoard, color);
	}

	public String toString(){
		if (this.getColor() == Color.WHITE){
			return "\u2658";
		} else{
			return "\u265E";
		}
	}

	public ArrayList<String> legalMoves(){
		return new ArrayList<String>();
	}
}