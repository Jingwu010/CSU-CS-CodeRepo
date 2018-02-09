package A1.src;

import java.util.Random;
import java.util.ArrayList;
import A1.src.Color;

public class ChessProgram{

	private static String int2Position(int x, int y){
		StringBuilder sb = new StringBuilder();
		sb.append((char)('a'+x));
		sb.append((char)('1'+y));
		return sb.toString();
	}

	public static void main(String[] args) throws IllegalPositionException, IllegalMoveException, InterruptedException{
		ChessBoard chessboard = new ChessBoard();
		chessboard.initialize();

		Random rand = new Random();
		int player = rand.nextInt(2);
		Color cur;
		if (player == 0) cur = Color.WHITE;
		else cur = Color.BLACK;

		while(true){
			int x = rand.nextInt(8);
			int y = rand.nextInt(8);
			String fromPosition = int2Position(x, y);
			ChessPiece fromPiece;
			try{
				fromPiece = chessboard.getPiece(fromPosition);
			} catch (IllegalPositionException ie) {
				continue;
			}
			if (fromPiece == null) continue;
			if (fromPiece.getColor() != cur) continue;
			ArrayList<String> legalMoves = fromPiece.legalMoves();
			if (legalMoves.size() == 0) continue;

			String toPosition = legalMoves.get(rand.nextInt(legalMoves.size()));
			ChessPiece toPiece = chessboard.getPiece(toPosition);
			
			Thread.sleep(2000);

			chessboard.move(fromPosition, toPosition);
			System.out.println("\nMove Piece from " + fromPosition + " to " + toPosition);
	    	System.out.println(chessboard);

	    	if (toPiece != null)
				if (toPiece.toString() == "\u2654" || toPiece.toString() == "\u265A"){
					cur = fromPiece.getColor();
					break;
				}

			cur = (cur == Color.WHITE) ? Color.BLACK : Color.WHITE;
		}

		System.out.println("Game Over " + cur + " wins");
	}
}