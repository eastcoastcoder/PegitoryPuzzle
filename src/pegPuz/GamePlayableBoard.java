package pegPuz;

public class GamePlayableBoard implements IPlayableBoard {

	protected Piece boardPieces[]; // Contains the entire board (all pieces and all non-piece spaces), eases traversal of the board
	protected int boardLocation[]; // Array from 1-NUM_BOARD_LOC.  Each location references an index of the boardPieces array (where it is on the board)
	
	
	// 6 allowable directions; one or two letter int constants
	public static final int N = 0;
	public static final int NE = 1;
	public static final int E = 2;
	public static final int SE = 3;
	public static final int S = 4;
	public static final int SW = 5;
	public static final int W = 6;
	public static final int NW = 7;

	protected double X_TRAVERSAL = 1;
	protected double Y_TRAVERSAL = 1;
	
	protected int BOARD_WIDTH = 0;
	protected int BOARD_HEIGHT = 0; 
	protected int NUM_BOARD_LOC = 0; 
	protected int NUM_BOARD_DIR = 8; 

	/**
	 * Default Constructor
	 */ 
	public GamePlayableBoard() {
		
		//Allocate our board arrays
		boardLocation = new int[NUM_BOARD_LOC];	
		boardPieces = new GamePlayableBoard.Piece[BOARD_WIDTH * BOARD_HEIGHT];

	}
	
	
	//HELPER FUNCTIONS
    //////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Initializes all board pieces
	 */
	public void initBoard() {
		
		//This gets overridden in derived shape classes.
	
	}
	
	/**
	 * Given a boardPieces index, return the board location that is there (1-NUM_BOARD_LOC)
	 * @param bp boardPieces array index number
	 * @return the board location at specified index
	 */
	public int getLocationFromBoardPiece( int bp ) {
		
		for( int j = 0; j < NUM_BOARD_LOC; j++ ) {
			
			if( boardLocation[j] == bp ) return j + 1;
			
		}
		
		return -1;
		
	}
	
	public int getBoardWidth() {
		
		return BOARD_WIDTH;
		
	}
	
	public int getBoardHeight() {
		
		return BOARD_HEIGHT;
	
	}
	
	/**
	 * Given a board location (1-NUM_BOARD_LOC), return its boardPieces index
	 * @param loc 1-based location index
	 * @return boardLocation index at specified board location
	 */
	public int getBoardPieceFromLocation( int loc ) {
		
		if( isLocValid( loc ) ) {

			return boardLocation[loc - 1];
			
		}
		
		return -1;
		
	}
	
	/**
	 * Returns board piece index at a direction relative to another board piece index; checks for out of bounds
	 * @param bp boardPieces array index number
	 * @param dir the direction of target board piece
	 * @return board piece index at specified direction; relative to another board piece index
	 */
	public int getBoardPieceInDir( int bp, int dir ) {		

		int y = bp / BOARD_WIDTH;
		int x = bp - y * BOARD_WIDTH;

		double xmove = 0.0;
		double ymove = 0.0;

		if( dir > S )
			xmove = -X_TRAVERSAL;
		else if( dir < S && dir > N )
			xmove = X_TRAVERSAL;
		
		if( dir == W || dir == E )
			xmove *= 2;

		if( dir == N || dir == NW || dir == NE ) 
			ymove = -Y_TRAVERSAL;
		else if( dir == SE || dir == S || dir == SW )
			ymove = Y_TRAVERSAL;
		
		x += xmove;
		y += ymove;
	
		if( x >= 0 && x < BOARD_WIDTH )
			if( y >= 0 && y < BOARD_HEIGHT )
				return x + y * BOARD_WIDTH;
		
		return -1;
		
		
	}
	//////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	
	//PUBLIC FUNCTIONS
	//////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * Count all the pieces of a particular type that are on the board
	 * @param peg the type of piece to count
	 * @return the total number of pieces of the specified type on the board
	 */
	public int countOnBoard( Piece peg ) {
		
		int count = 0;
		
		for( int i = 0; i < NUM_BOARD_LOC; i++ )
			if( getWhatIsAtLoc( i + 1 ) == peg ) count++;
		
		return count;
	}
	
	/**
	 * Fills all board locations with the piece type specified
	 * @param peg the type of piece to fill
	 */
	public void fillWith( Piece peg ) {
		
		for( int i = 0; i < NUM_BOARD_LOC; i++ )
			setWhatIsAtLoc( i + 1, peg );
		
	}
	
	/**
	 * Given a location and a direction; returns the neighbor location in that direction
	 * @param loc 1-based location index
	 * @param dir the direction in which to traverse
	 * @return neighbor location in specified direction
	 */
	public int getLocInDir( int loc, int dir ) {
		
		// Traverse the puzzle board to the destination location
		int boardPieceIndex = getBoardPieceInDir(  getBoardPieceFromLocation( loc ), dir );
		
		// If this index is valid and there is a piece there (aka, not undefined)
		if( boardPieceIndex > -1 && boardPieces[boardPieceIndex] != Piece.UNDEFINED) {
			
			return getLocationFromBoardPiece( boardPieceIndex );
			
		}
		
		return -1;
		
	}
	
	/**
	 * Returns array of exact length containing dir of every neighboring location
	 * @param loc 1-based location index
	 * @return dir of every neighboring location
	 */
	public int[] getNeighborDir( int loc ) {
		
		int numNeighbors = 0;
		int neighborDirs[] = new int[NUM_BOARD_DIR];
		
		for( int i = 0; i < NUM_BOARD_DIR; i++ ) {
			
			int neighborLoc = getLocInDir( loc, i );
			
			if( neighborLoc > -1)
				numNeighbors++;
			
			neighborDirs[i] = neighborLoc;
			
		}
		
		int result[] = new int[numNeighbors];
		int index = 0;
		
		for( int i = 0; i < NUM_BOARD_DIR; i++ )
			if( neighborDirs[i] > -1 )
				result[index++] = neighborDirs[i];
		
		return result;
		
	}
	
	/**
	 * Obtains number of locations on the board
	 * @return the total number of board locations
	 */
	public int getNumLocs() {
		return NUM_BOARD_LOC;
	}
	
	/**
	 * Checks if every valid board location is empty
	 * @return true or false depending upon vacancy of entire board
	 */
	public boolean isBoardEmpty() {
		
		for( int i = 0; i < NUM_BOARD_LOC; i++ )
			if( getWhatIsAtLoc( i + 1 ) != Piece.HOLE )
					return false;
		
		return true;
	}
	
	/**
	 * Checks whether location is legal piece on the board
	 * @param locID 1-based location index
	 * @return true or false depending upon validity of specified location
	 */
	public boolean isLocValid( int locID ) {
		
		return ( locID >= 1 && locID <= NUM_BOARD_LOC )? true : false;	
		
	}
	
	/**
	 * Obtains piece at a certain location
	 * @param locID 1-based location index
	 * @return the type of piece at a specified location on the board
	 */
	public GamePlayableBoard.Piece getWhatIsAtLoc( int locID ) {
		
		if( isLocValid( locID ) )
			return boardPieces[boardLocation[locID-1]];
		
		return GamePlayableBoard.Piece.UNDEFINED;
		
	}
	

	/**
	 * Puts a given piece on the board the specified location
	 * @param locID 1-based location index
	 * @param peg the type of piece to be set
	 */
	public void setWhatIsAtLoc( int locID, Piece peg) {
		
		if( isLocValid( locID ) ) {
		
			int boardPieceIndex = getBoardPieceFromLocation( locID );
			boardPieces[boardPieceIndex] = peg;
					
		}
		
	}
	//////////////////////////////////////////////////////////////////////////////////////////////////
	
	
}
