package pegPuz;

@SuppressWarnings("serial")
public class PegitoryViewer extends GameViewer  {

	private IPegitoryModel gameModel;
	
	public PegitoryViewer(GameAbstractDrawableBoard d, IGameModel m) {
		super(d, m);
		gameModel = (IPegitoryModel) m;
	}

	private static final String STR_END_ONE = "Player 1 wins!";
	private static final String STR_END_TWO = "Player 2 wins!";
	private static final String STR_END_THREE = "It's a tie.";
		
	@Override
	public void showGameOverMessage( int remaining, int lastpeg ) {


		if (gameModel.getPlayerOneScore() > gameModel.getPlayerTwoScore()){
			gameOverText = STR_END_ONE;
		}else if (gameModel.getPlayerOneScore() < gameModel.getPlayerTwoScore()){
			gameOverText = STR_END_TWO;
		}else if (gameModel.getPlayerOneScore() == gameModel.getPlayerTwoScore()){
			gameOverText = STR_END_THREE;
		}
		
		repaint();
		
	}	
}
