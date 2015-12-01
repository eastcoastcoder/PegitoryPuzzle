package pegPuz;

public abstract class GameAbstractModel implements IGameModel {
	
	private static final String STR_PEG_START = "Moves must start on a peg";
	protected static final String STR_PEG_MIDDLE = "Moves must have a peg in the middle";
	private static final String STR_TWO_AWAY = "End location must be 2-away from Start";
	protected static final String STR_END_EMPTY = "Moves must end on an empty location";
	
	protected EModelStates curState;
	protected int firstMoveLoc;
	protected String failedMoveString = "";
	private String onGoingMessage; 
	private int curMoveCount;
	private int curLoc = 0;
	private int prevLoc = 0;
	private int errorPegLoc;
	protected int initialHoleLoc;

	protected GamePlayableBoard gameBoard;
	private GameViewer gameView;
	
	//INTERFACE
	//////////////////////////////////////////////////////////////////////////////////////////////////
	
	private final IPlayableBoard.Piece InitialPiece = IPlayableBoard.Piece.PEG;
	
	/**
	 * Default Constructor
	 */ 
	public GameAbstractModel( GamePlayableBoard board, int initialHole ) {
		initialHoleLoc = initialHole;
		gameBoard = board;
		gameBoard.initBoard();
		
	}
	
	/* (non-Javadoc)
	 * @see pegPuz.IGameMeol#getGameBoard()
	 */
	@Override
	public GamePlayableBoard getGameBoard() {
		
		return gameBoard;
		
	}	
	
	/* (non-Javadoc)
	 * @see pegPuz.IGameMeol#getMoveCount()
	 */
	@Override
	public int getMoveCount() {
		
		return curMoveCount;
		
	}
	
	/* (non-Javadoc)
	 * @see pegPuz.IGameMeol#getRemainingPegsCount()
	 */
	@Override
	public int getRemainingPegsCount(){
		
		return gameBoard.getNumLocs() - getMoveCount() - 1;
		
	}
	
	/* (non-Javadoc)
	 * @see pegPuz.IGameMeol#getState()
	 */
	@Override
	public EModelStates getState(){
		return curState;
	}
	
	/* (non-Javadoc)
	 * @see pegPuz.IGameMeol#newGame()
	 */
	@Override
	public void newGame() {
		
		gameBoard.fillWith( InitialPiece );
		gameBoard.setWhatIsAtLoc( initialHoleLoc, IPlayableBoard.Piece.HOLE );
		
		if( gameView != null )
			gameView.resetMoveText();
		
		curState = EModelStates.READY;
		firstMoveLoc = -1;
		
		setMoveCount( 0 );
		
	}
	
	/* (non-Javadoc)
	 * @see pegPuz.IGameMeol#getNumBoardLocs()
	 */
	@Override
	public int getNumBoardLocs() {
		
		return gameBoard.getNumLocs();
		
	}
	
	/* (non-Javadoc)
	 * @see pegPuz.IGameMeol#handleMoveAtLoc(int)
	 */
	@Override
	public void handleMoveAtLoc( int loc ) {
		if(curState == EModelStates.ERROR){
			curState = EModelStates.READY;
			errorPegLoc=-1;
		}
		if( curState != EModelStates.GAMEOVER ) {
			
			boolean failedMove = false;
			
			if( curState == EModelStates.READY ){
	
				failedMove = !makeFirstMove( loc );
				if(!failedMove){
					onGoingMessage = "Ongoing move started at location: " + loc;
				}
			} else if( curState == EModelStates.INPROG ) {
				
				failedMove = !makeSecondMove( loc );
				
				if( !failedMove ) {
					
					if( prevLoc != loc ) {
						
						prevLoc = curLoc;
						
					}
					
					curLoc = loc;
				
					onGoingMessage = "Moved from location " + firstMoveLoc + " to location " + loc;
								
					firstMoveLoc = -1;
					checkGameOver();
					
				}
				
			}
			
			if( curState == EModelStates.GAMEOVER ) {
				
				int numRemainingPegs = getRemainingPegsCount();
				int lastPegLoc = -1;
				
				if( numRemainingPegs == 1 ) {
				
					for( int i = 0; i < getNumBoardLocs(); i++ ) {
						
						if( isPegAtLoc( i + 1 ) ) {
							
							lastPegLoc = i + 1;
							break;
							
						}
						
					}
					
				}
				
			}
			
		}
			
	}
	
	/* (non-Javadoc)
	 * @see pegPuz.IGameMeol#getFirstLoc()
	 */

	@Override
	public int getFirstLoc() {
		
		return firstMoveLoc;
		
	}
	

	/* (non-Javadoc)
	 * @see pegPuz.IGameMeol#isPegAtLoc(int)
	 */
	@Override
	public boolean isPegAtLoc( int peg ) {
		
		return ( gameBoard.getWhatIsAtLoc( peg ) == IPlayableBoard.Piece.PEG );
		
	}
	
	//HELPER FUNCTIONS
    //////////////////////////////////////////////////////////////////////////////////////////////////

	private int getPegInBetween( int srcPeg, int dstPeg ) {
		
		int possibleDir[] = { 
				gameBoard.NE, gameBoard.E, gameBoard.SE, 
				gameBoard.SW, gameBoard.W, gameBoard.NW 
		};
		
		int reverseDir[] = { 
				gameBoard.SW, gameBoard.W, gameBoard.NW, 
				gameBoard.NE, gameBoard.E, gameBoard.SE 
		};		
		
		for( int j = 0; j < 6; j++ ) {
			
			int srcPegNeighbor = gameBoard.getLocInDir( srcPeg, possibleDir[j] );
			int dstPegNeighbor = gameBoard.getLocInDir( dstPeg, reverseDir[j] );
			
			if( gameBoard.isLocValid( srcPegNeighbor ) &&
				srcPegNeighbor == dstPegNeighbor ) {	
				
				return srcPegNeighbor;
				
			}
			
		}	
		
		return -1;
		
	}	/* (non-Javadoc)
	 * @see pegPuz.IGameMeol#makeFirstMove(int)
	 */
	@Override
	public boolean makeFirstMove( int loc ){
	
		if( curState == EModelStates.READY && isPegAtLoc( loc ) ) {		
			firstMoveLoc = loc;	
			curState = EModelStates.INPROG;
			return true;
			
		}
		
		failedMoveString = STR_PEG_START;
		curState = EModelStates.ERROR;
		return false;
	
	}
	
	/* (non-Javadoc)
	 * @see pegPuz.IGameMeol#makeSecondMove(int)
	 */
	@Override
	public boolean makeSecondMove( int loc ) {
		
		if( getState() == EModelStates.INPROG ) {

			int src = firstMoveLoc;

			curState = EModelStates.READY;
			
			if( isHoleAtLoc( loc ) ){
				if( movePeg( src, loc ) ){
					return true;
				}else{
					errorPegLoc = src;
					curState = EModelStates.ERROR;
					failedMoveString = STR_PEG_MIDDLE; //"You cannot move a peg there!"
				}
			}else{
				errorPegLoc = src;
				curState = EModelStates.ERROR;
				failedMoveString = STR_END_EMPTY; //"You didn't click on a hole"
			}
				
		} 
		
		return false;
		
	}
	
	protected boolean pegCanMove( int srcPeg ) {
		
		int possibleDir[] = { 
				gameBoard.NE, gameBoard.E, gameBoard.SE, 
				gameBoard.SW, gameBoard.W, gameBoard.NW 
		};
		
		for( int j = 0; j < 6; j++ ) {
			
			int midPoint = gameBoard.getLocInDir( srcPeg, possibleDir[j] );
			if( gameBoard.isLocValid( midPoint ) && isPegAtLoc( midPoint ) ) {
				
				int endPoint = gameBoard.getLocInDir( midPoint, possibleDir[j] );
				if( gameBoard.isLocValid( endPoint ) && isHoleAtLoc( endPoint ) ) {

					return true;
				}
			}

		}
		
		return false;
		
	}
	
	private String getLastMoveFailureString() {
		
		return failedMoveString;
		
	}
	
	protected void checkGameOver() {

		for( int i = 0; i < gameBoard.getNumLocs(); i++ ) {

			if( isPegAtLoc( i + 1 ) && pegCanMove( i + 1 ) )
				return;
			
		}

		curState = EModelStates.GAMEOVER;
		
	}	
	
	private void setPegAtLoc( int loc ) {
		
		gameBoard.setWhatIsAtLoc( loc, IPlayableBoard.Piece.PEG );
		
	}
	
	private void removePegAtLoc( int loc ) {
		
		gameBoard.setWhatIsAtLoc( loc, IPlayableBoard.Piece.HOLE );
		
	}	
	
	/* (non-Javadoc)
	 * @see pegPuz.IGameMeol#isHoleAtLoc(int)
	 */
	@Override
	public boolean isHoleAtLoc( int loc ) {
		
		return ( gameBoard.getWhatIsAtLoc( loc ) == IPlayableBoard.Piece.HOLE );
		
		
	}
	
	/* (non-Javadoc)
	 * @see pegPuz.IGameMeol#canMoveToPeg(int, int, int)
	 */
	@Override
	public boolean canMoveToPeg( int srcPeg, int midPeg, int dstPeg ) {
		
		if( isPegAtLoc( midPeg ) && isHoleAtLoc( dstPeg ) )
				return true;
			
		return false;
		
	}
	
	/* (non-Javadoc)
	 * @see pegPuz.IGameMeol#movePeg(int, int)
	 */
	@Override
	public boolean movePeg( int srcPeg, int dstPeg ) {
	
		int midPeg = getPegInBetween( srcPeg, dstPeg );
	
		if( canMoveToPeg( srcPeg, midPeg, dstPeg ) ) {

			removePegAtLoc( srcPeg );
			removePegAtLoc( midPeg );
			setPegAtLoc( dstPeg );
			setMoveCount( getMoveCount() + 1 );
			
			return true;
		} 
	

		return false;
	}
	
	/* (non-Javadoc)
	 * @see pegPuz.IGameMeol#setMoveCount(int)
	 */
	@Override
	public void setMoveCount( int moveCount ) {
		
		this.curMoveCount = moveCount;
		
	}
	
	/* (non-Javadoc)
	 * @see pegPuz.IGameMeol#getErrorPegLoc()
	 */
	@Override
	public int getErrorPegLoc(){
		return errorPegLoc;
	}
	
	/* (non-Javadoc)
	 * @see pegPuz.IGameMeol#getOnGoingMessage()
	 */
	@Override
	public String getOnGoingMessage(){
		return onGoingMessage;
	}
	
	@Override
	public String getFailedMoveString(){
		return failedMoveString;
	}
	
	
	//////////////////////////////////////////////////////////////////////////////////////////////////
}
