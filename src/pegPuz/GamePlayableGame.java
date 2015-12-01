package pegPuz;

import java.awt.BorderLayout;

import javax.swing.JPanel;

public class GamePlayableGame implements IPlayableGame {

	protected GameAbstractDrawableBoard drawableBoard;
	protected GameViewer gameView;
	protected IGameModel gameModel;
	protected JPanel gamePanel;
	
	GamePlayableGame( GamePlayableBoard playable, boolean pegitory ) {
		if(pegitory){
			if(playable.getNumLocs()==19){
				gameModel = new PegitoryHexagonModel( playable );
				gameModel.newGame();
				return;
			} else{
				gameModel = new PegitoryTriangleModel( playable );
				gameModel.newGame();
				return;
			}
		}else{
			gameModel = new PegPuzModel( playable );
			gameModel.newGame();
		}
	}
	
	public void initView( GameAbstractDrawableBoard drawable, IUpdateable u, boolean pegitory ) {
		
		gamePanel = new JPanel();
		gamePanel.setLayout( new BorderLayout() ); 

		if(pegitory){
			gameView = new PegitoryViewer( drawable, gameModel);
		}else
			gameView = new GameViewer( drawable, gameModel );
		gameView.buildGUI( u );

		gameView.setGameBoard( gameModel.getGameBoard() );
	
		gamePanel.add( gameView, BorderLayout.CENTER );
		
	}
	
	public GameViewer getGameView() { 
		
		return gameView;
		
	}
	
	public IGameModel getGameModel() { 
		
		return gameModel;
		
	}
	
	public JPanel getViewPanel() {
		
		return gamePanel;
	
	}
}
