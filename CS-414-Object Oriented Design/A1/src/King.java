package A1.src;

import java.util.ArrayList;

public class King extends ChessPiece{

	public King(ChessBoard chessBoard, Color color){
		super(chessBoard, color);
	}

	public String toString(){
		if (this.getColor() == Color.WHITE){
			return "\u2654";
		} else{
			return "\u265A";
		}
	}

	// The king can only move one square horizontally, vertically, or diagonally.
	// Can king capture other pieces?
	public ArrayList<String> legalMoves(){
		ArrayList<String> results = new ArrayList<String>();
		int[] dir_row = {1, 1, 1, 0, 0, -1, -1, -1};
		int[] dir_col = {-1, 0, 1, -1, 1, -1, 0, 1};
		for (int i = 0; i < dir_row.length; i++){
			StringBuilder sb = new StringBuilder();
			sb.append((char)('a'+this.column+dir_col[i]));
			sb.append((char)('1'+this.row+dir_row[i]));
			String position = sb.toString();
			try{
				ChessPiece piece = this.chessBoard.getPiece(position);	
				if (piece != null)
					if (piece.getColor() != this.getColor())
						results.add(position);
					else continue;
				else results.add(position);
			} catch (IllegalPositionException ie){ continue;}
		}
		return results;
	}
}