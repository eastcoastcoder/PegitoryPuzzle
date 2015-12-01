package pegPuz;

import javax.swing.JPanel;

public interface IPlayableGame {
	public void initView( GameAbstractDrawableBoard drawable, IUpdateable u, boolean pegitory );
	
	public JPanel getViewPanel();
	
	public IGameModel getGameModel();
	
	public GameViewer getGameView();
}
