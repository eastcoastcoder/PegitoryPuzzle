package pegPuz;

public interface IPlayableBoard {


	public enum Piece { PEG, HOLE, UNDEFINED } // Enum detailing the current state of a slot on the puzzle board (whether it is a peg piece, hole, or nothing at all)
	
	int getBoardWidth();
	int getBoardHeight();
	int getBoardPieceFromLocation( int loc );
	int getBoardPieceInDir( int bp, int dir );
	int getLocationFromBoardPiece( int bp );
	
	Piece getWhatIsAtLoc( int loc ); 
	int getNumLocs();
	
	void initBoard();
	
}
