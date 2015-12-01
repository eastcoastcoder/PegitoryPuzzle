package pegPuz;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GameController extends MouseAdapter {
	
	GameViewer gameView;
	IGameModel gameModel;


	GameController( GameViewer view, IGameModel model ) {
		
		gameView = view;
		gameModel = model;
		
	}
	
	public void mouseReleased(MouseEvent e) {

		int mx = e.getX();
		int my = e.getY();

		int loc = gameView.getSpotAtXY( mx, my ) + 1;
		gameView.resetMoveText();
		gameModel.handleMoveAtLoc( loc );
		
		if(gameModel.getState() == EModelStates.GAMEOVER){
			
			gameView.showGameOverMessage( gameModel.getRemainingPegsCount(), 0 );
			
		} else if(gameModel.getState() == EModelStates.ERROR){
			
			gameView.showFailedMoveMessage(gameModel.getFailedMoveString());
			gameView.doBadMoveFlash(gameModel.getErrorPegLoc());
			
		} else if(gameModel.getState() == EModelStates.READY){
			
			gameView.setOngoingMoveText(gameModel.getOnGoingMessage());
			
		} else if(gameModel.getState() == EModelStates.INPROG){
			
			gameView.setOngoingMoveText(gameModel.getOnGoingMessage());
		}
		
		gameView.updateGUI();
	}

}

