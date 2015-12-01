package pegPuz;

public interface IGameModel {

	public abstract GamePlayableBoard getGameBoard();

	public abstract int getMoveCount();

	public abstract int getRemainingPegsCount();

	public abstract EModelStates getState();

	public abstract void newGame();

	public abstract int getNumBoardLocs();

	public abstract void handleMoveAtLoc(int loc);

	/**
	 * The first move (player picks a peg)
	 * @param loc 1-based location index
	 * @return true or false depending upon successful/unsuccessful move
	 */

	public abstract int getFirstLoc();

	/**
	 * Returns whether a location is a peg or not
	 * @param peg 1-based location index to check if peg
	 * @return true or false depending upon if peg or other
	 */
	public abstract boolean isPegAtLoc(int peg);

	public abstract boolean makeFirstMove(int loc);

	/**
	 * The second move (player chooses a destination hole)
	 * @param loc 1-based location index
	 * @return true or false depending upon successful/unsuccessful move
	 */
	public abstract boolean makeSecondMove(int loc);

	/**
	 * Returns whether a location is a hole or not
	 * @param peg 1-based location index to check if hole
	 * @return true or false depending upon if hole or other
	 */
	public abstract boolean isHoleAtLoc(int loc);

	public abstract boolean canMoveToPeg(int srcPeg, int midPeg, int dstPeg);

	public abstract boolean movePeg(int srcPeg, int dstPeg);

	public abstract void setMoveCount(int moveCount);

	public abstract int getErrorPegLoc();

	public abstract String getOnGoingMessage();

	public String getFailedMoveString();

}