package A1.src;

import java.util.ArrayList;

public class Rook extends ChessPiece{

	public Rook(ChessBoard chessBoard, Color color){
		super(chessBoard, color);
	}

	public String toString(){
		if (this.getColor() == Color.WHITE){
			return "\u2656";
		} else{
			return "\u265C";
		}
	}

	// A rook can move any number of squares horizontally or vertically, forward or backward, 
	// as long as it does not have to leap over other pieces. At the end of the move, 
	// it can occupy a previously empty square or capture (replace) an opponent's piece but it cannot replace another piece of the same player.
	// Checked OK
	public ArrayList<String> legalMoves(){
		ArrayList<String> results = new ArrayList<String>();
		int[] dir_row = {1, -1, 0, 0};
		int[] dir_col = {0, 0, 1, -1};
		for (int i = 0; i < dir_row.length; i++){
			int k = 1;
			while (true){
				StringBuilder sb = new StringBuilder();
				sb.append((char)('a'+this.column+k*dir_col[i]));
				sb.append((char)('1'+this.row+k*dir_row[i]));
				String position = sb.toString();
				try{
					ChessPiece piece = this.chessBoard.getPiece(position);
					if (piece != null){
						if (piece.getColor() == this.getColor()) break;
						else{
							results.add(position);
							break;
						}
					} else{
						results.add(position);
						k ++;
					}
				} catch (IllegalPositionException ie){ break; }
			}
		}
		return results;
	}
}