package A1.src;

import java.util.ArrayList;

public class Pawn extends ChessPiece{

	public Pawn(ChessBoard chessBoard, Color color){
		super(chessBoard, color);
	}

	public String toString(){
		if (this.getColor() == Color.WHITE){
			return "\u2659";
		} else{
			return "\u265F";
		}
	}

	// A pawn in the initial position may move one or two squares vertically forward to an empty square but cannot leap over any piece. 
	// Subsequently it can move only one square vertically forward to an empty square. 
	// A pawn may also capture (replace) an opponent's piece diagonally one square in front of it. 
	// Pawns can never move backwards. These are the only moves; 
	// we will not implement the En passant rule and will also not allow promotion to another piece if the pawn reaches the end of the column. 
	// If you don't know what these rules are, don't worry. We won't use them.
	public ArrayList<String> legalMoves(){
		// TODO
		ArrayList<String> results = new ArrayList<String>();

		int[] dir_row = {1, -1};
		int[] dir_col = {0, 0};
		int[] init_row = {1, 6};
		int dirs;
		if (this.getColor() == Color.WHITE){
			dirs = 0;
		} else {dirs = 1;}

		int k = -1;
		for (int i = 0; i < 2; i++){
			StringBuilder sb = new StringBuilder();
			sb.append((char)('a'+this.column+k*dir_row[dirs]));
			sb.append((char)('1'+this.row+dir_row[dirs]));
			String position = sb.toString();
			k = 1;
			try{
				ChessPiece piece = this.chessBoard.getPiece(position);
				if (piece != null && piece.getColor() != this.getColor()){
					results.add(position);
				} else continue;
			} catch (IllegalPositionException ie){ continue; }
		}

		while (true){
			StringBuilder sb = new StringBuilder();
			sb.append((char)('a'+this.column+k*dir_col[dirs]));
			sb.append((char)('1'+this.row+k*dir_row[dirs]));
			String position = sb.toString();
			try{
				ChessPiece piece = this.chessBoard.getPiece(position);	
				if (piece != null){
					return results;
				} else{
					results.add(position);
					if (k == 2) return results;
					if (this.row != init_row[dirs]) return results;
					k += 1;
				}
			} catch (IllegalPositionException ie){ return results; }
			if (k > 2) return results;
		}
	}
}