package pegPuz;

import java.awt.*;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;

import javax.swing.*;
import java.util.Date;

@SuppressWarnings("serial")
public class GameViewer extends JPanel {

	private Color bgColor = Color.WHITE;
	
	private IDrawableBoard drawableBoard;
	
	private static final String STR_END_ONE = "Genius!";
	private static final String STR_END_ONE_ALT = "Excellent!";
	private static final String STR_END_TWO = "Great Job!";
	private static final String STR_END_THREE = "OK but you can do better!";
	private static final String STR_END_FOUR = "You need more practice!";
	private static final String STR_END_DEFAULT = "Were you even trying?";
	
	private static final int PREFER_WID = 550;
	private static final int PREFER_HT = 500;
	
	private final int BOARD_OFFSET_X = 120;
	private final int BOARD_OFFSET_Y = 110;

	protected IGameModel gameModel;
	private IUpdateable appFrame;
	
	private Dimension preferredSize = new Dimension(PREFER_WID, PREFER_HT);
	
	protected String gameOverText = "";
	private String previousMoveText = "";
	private String ongoingMoveText = "";
	private String badMoveText = "";
	
	
	/**
	 * Default Constructor
	 */ 
	public GameViewer(GameAbstractDrawableBoard d, IGameModel m){
		
		drawableBoard = d;
		gameModel = m;
	
	}
	
	//HELPER FUNCTIONS
    //////////////////////////////////////////////////////////////////////////////////////////////////
	
	private void drawString( Graphics2D g2, String s, int x, int y, int size, Color clr, boolean centered ) {
		
		g2.setColor( clr );
		g2.setFont(new Font("default", Font.PLAIN, size ));
        
		int stringWidth = (int)g2.getFontMetrics().getStringBounds( s, g2).getWidth();  
		int offset = ( centered )? -stringWidth / 2 : 0;
		
		g2.drawString( s, x + offset, y ); 
	
	}
	
	private void drawString( Graphics2D g2, String s, int x, int y, int size, Color clr ) {
		
		drawString( g2, s, x, y, size, clr, false );
		
	}


	//////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	
	//PUBLIC FUNCTIONS
	//////////////////////////////////////////////////////////////////////////////////////////////////
	
	public void setGameBoard( IPlayableBoard b ) {
		
		drawableBoard.setGameModel( gameModel );
		drawableBoard.initBoard( b );
		
	}

	@Override
	public void paintComponent(Graphics g){
		
		super.paintComponent(g);
		
		Graphics2D g2D = (Graphics2D)g;

		drawString( g2D, ongoingMoveText, 10, 60, 16, Color.BLACK );
		drawString( g2D, previousMoveText, 10, 78, 16, Color.BLACK );
		drawString( g2D, badMoveText, 10, 30, 24, Color.RED );
		
		drawString( g2D, gameOverText, PREFER_WID / 2, PREFER_HT - 30, 24, Color.BLACK, true );
		
		if( gameModel.getState() == EModelStates.GAMEOVER ) {
			
			drawString( g2D, "GAME OVER", PREFER_WID / 2, PREFER_HT - 60, 42, Color.RED, true );
			
		}
		
		g2D.translate( BOARD_OFFSET_X, BOARD_OFFSET_Y);
		
		if( drawableBoard.drawBoard( g2D ) )
			repaint();
	
	}
	
	public void resetMoveText() {
		
		previousMoveText = "";
		ongoingMoveText = "";
		badMoveText = "";
		gameOverText = "";
		
	}
	
	public void doBadMoveFlash( int loc ) {
		
		drawableBoard.doBadMoveFlash( loc );
		
		repaint();
		
	}
	
	public void setPreviousMoveText( String s ) {
		
		previousMoveText = s;
		
	}
	
	public void setOngoingMoveText( String s ) {
		
		ongoingMoveText = s;
		
	}
	
	public void showFailedMoveMessage( String s ) {
		
		badMoveText = s;
		
	}
		
	public int getSpotAtXY( int x, int y ) {
		
		return drawableBoard.getSpotAtXY( x - BOARD_OFFSET_X, y - BOARD_OFFSET_Y );
		
	}
	
	public void showGameOverMessage( int remaining, int lastpeg ) {
		
		switch( remaining ) {
			
			case 1:	
				if( lastpeg == 1)
					gameOverText = STR_END_ONE;
				else
					gameOverText = STR_END_ONE_ALT;
				break;
			
			case 2: 
				gameOverText = STR_END_TWO;
				break;
				
			case 3: 
				gameOverText = STR_END_THREE;
				break;
				
			case 4:
				gameOverText = STR_END_FOUR;
				break;
				
			default:
				gameOverText = STR_END_DEFAULT;
				break;
				
		}
		

		repaint();
		
	}

	public Dimension getPreferredSize(){
		return preferredSize;
	}
	
	public void buildGUI( IUpdateable u ){

		appFrame = u;
		
		GameController controller = new GameController(this, gameModel);
		this.addMouseListener(controller);
		
		setOpaque(true);
		setBackground(bgColor);
	}
	
	public void updateGUI(){
		repaint();
		appFrame.updateGUI();

	}
	
	public IGameModel getModel(){
		return gameModel;
	}

}

/* Unused Vars
private static final int TOP_CENTER_X = 165; //The top of the triangle
private static final int TOP_CENTER_Y = 70; 
private static final int DIST_BETWEEN_SPOTS_X = 90; //The X/Y distance between each spot
private static final int DIST_BETWEEN_SPOTS_Y = 65;
private static final int SPOT_WIDTH = 50; //Size of the circle at each spot
private static final int SPOT_HEIGHT = 50;
private int badMoveEndTime = 0;
private static final Color BOARD_COLOR = new Color( 180, 180, 255 );
private static final Color BOARD_LINE_COLOR = new Color( 70, 70, 180 );
private static final Color SPOT_COLOR_PEG = new Color( 255, 10, 10 );
private static final Color SPOT_COLOR_EMPTY = new Color( 220, 220, 220 );
private static final Color SPOT_COLOR_PICKED = new Color( 255, 100, 100 );
private static final int ROWS = 5;
 */