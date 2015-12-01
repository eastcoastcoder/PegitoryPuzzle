package pegPuz;

import pegPuz.IPlayableBoard.Piece;

public class GameHexagonPlayableBoard extends GamePlayableBoard {
	

	public GameHexagonPlayableBoard() {
		
		BOARD_WIDTH = 9; 
		BOARD_HEIGHT = 5; 
		NUM_BOARD_LOC = 19; 
		
	}

	/**
	 * Initializes all board pieces
	 */
	public void initBoard() {
		
		boardLocation = new int[NUM_BOARD_LOC];	
		boardPieces = new IPlayableBoard.Piece[BOARD_WIDTH * BOARD_HEIGHT];
		
		// Find the center piece of each row, we start setting pieces based off of this
		int centerX = ( int )Math.ceil( BOARD_WIDTH / 2.0 ) - 1;
		
		// Use this to populate our boardLocation array as we fill out the board
		int curLoc = 0;

		// Set empty board
		for( int y = 0; y < BOARD_HEIGHT; y++ ) {
			
			// The index range per row where we start populating pieces
			int minPiece = centerX - 2 - y;
			int maxPiece = centerX + 2 + y;
			
			if( y > 2 ) {
				
				minPiece += y - ( 4 - y );
				maxPiece -= y - ( 4 - y );
				
			}
			
			for( int x = 0; x < BOARD_WIDTH; x++ ) {
				
				// The board piece index
				int i = x + y * BOARD_WIDTH;
				
				// It is a piece location IF it fits in the range, and then check if the offset is an even number (used to alternate between piece and non-piece)
				if( x >= minPiece && x <= maxPiece && ( x - minPiece ) % 2 == 0 ) {
					
					boardPieces[i] = Piece.HOLE;
					boardLocation[curLoc++] = i;
					
				} else // Otherwise, it is just an undefined location on our board that contains no piece
					boardPieces[i] = Piece.UNDEFINED;
				
			}

		}
				
	}

}
