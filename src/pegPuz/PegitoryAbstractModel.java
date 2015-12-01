package pegPuz;

public abstract class PegitoryAbstractModel extends GameAbstractModel implements IPegitoryModel {

	protected int[] player1territory;
	protected int[] player2territory;
	protected int totalHoleLocations;
	protected int currentPlayer;
	private WinMethods winMethod;
	private int winTarget;
	
	public PegitoryAbstractModel(GamePlayableBoard board, int initialHole) {
		super(board, initialHole);
		currentPlayer=1;
		winMethod = WinMethods.WIN_BY_HIGH_SCORE;
	}
	
	@Override
	public int getTerritory(int holeLoc) {
		int result = 0;
		
		if ( holeLoc <= 0 || holeLoc >=20){
					result = -1;
		}
		else {
			for (int i = 0; i<player1territory.length; i++) {
				if (holeLoc == player1territory[i]){
					result = 1;
				}
			
			else {
			for (int j = 0; j <player2territory.length; j++){
				if (holeLoc == player2territory[i]){
					result = 2;	
						}
					}
				}
			}
		}
		return result;
	}

	@Override
	public int getPlayerOneScore() {
		int count=0;
		for(int i = 0; i<player1territory.length;i++){
			if(!isPegAtLoc(player1territory[i])){
				count++;
			}
		}
		return count;
	}

	@Override
	public int getPlayerTwoScore() {
		int count=0;
		for(int i = 0; i<player2territory.length;i++){
			if(!isPegAtLoc(player2territory[i])){
				count++;
			}
		}
		return count;
	}

	public int getPlayersTurn() {
		return currentPlayer;
	}
	
	public void setWinMethod(WinMethods method){
		winMethod = method;
	}
	
	public boolean setWinTarget(int target){
		if(gameBoard.getNumLocs()>(target/2)){
			winTarget = target;
			return true;
		} else return false;
	}
	
	public int getWinTarget() {
		return winTarget;
	}
	
	@Override
	protected void checkGameOver() {
		if(winMethod == WinMethods.WIN_BY_TARGET_VALUE){
			if(getPlayerOneScore()>=winTarget || getPlayerTwoScore()>=winTarget){
				curState = EModelStates.GAMEOVER;
			}
		}
		for( int i = 0; i < gameBoard.getNumLocs(); i++ ) {
			if( isPegAtLoc( i + 1 ) && pegCanMove( i + 1 ) ){
				return;
			}
		}
		curState = EModelStates.GAMEOVER;
	}
	
	@Override
	public boolean makeSecondMove( int loc ) {
		super.makeSecondMove(loc);

		if( getState() != EModelStates.ERROR){
			switchPlayerTurn();
		}
		return true;
	}

	private void switchPlayerTurn() {
		if(currentPlayer == 1){
			currentPlayer = 2;
		}else{
			currentPlayer = 1;
		}
	}
}
