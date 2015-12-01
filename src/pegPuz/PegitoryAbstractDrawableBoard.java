package pegPuz;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.util.Date;

public class PegitoryAbstractDrawableBoard extends GameAbstractDrawableBoard {
	
	protected Color SPOT_COLOR_PEG = new Color( 255, 195, 50 );
	protected Color SPOT_COLOR_PICKED = new Color( 255, 225, 100 );
	protected Color RED_TERRITORY = new Color( 255, 10, 10 );
	protected Color BLUE_TERRITORY = new Color( 5, 165, 255 );
	protected Color NO_TERRITORY = new Color( 220, 220, 220 );
	protected Color SPOT_COLOR_EMPTY = new Color( 220, 220, 220, 1);	
	
	
	protected Color BOARD_LINE_COLOR = Color.BLACK;
	
	private final int topMidY = 0, botMidY = 87, 
					  left = -DIST_BETWEEN_SPOTS_X , right = DIST_BETWEEN_SPOTS_X, midX = 0,
					  topSideY = DIST_BETWEEN_SPOTS_X/2, botSideY = DIST_BETWEEN_SPOTS_Y;

	
	private int boardLocs;
	private IPegitoryModel gameModel;
	protected Shape hexAtOrigin;
	private Shape[] hexBackgrounds;
	protected AffineTransform toPos;
	protected BasicStroke outline;
	
	public void createBoard() {

		boardLocs = gameBoard.getNumLocs();
		
		createShapeBackground();
		hexAtOrigin = ShapeBackground;
		toPos = new AffineTransform();
	
		int boardWidth = gameBoard.getBoardWidth();
		
		LocXY = new double[boardLocs][2];
		hexBackgrounds = new Shape[boardLocs];
		
		//Generate visual board information based on the playable board set-up.
		for( int j = 0; j < boardLocs; j++ ) {
			
			int bpIndex = gameBoard.getBoardPieceFromLocation( j + 1 );
			int bpByWidth = ( bpIndex / boardWidth );
			
			double bpY = ( bpByWidth * DIST_BETWEEN_SPOTS_Y - ( .5 * ( DIST_BETWEEN_SPOTS_Y - SPOT_HEIGHT ) ) );
			double bpX = ( ( bpIndex - ( bpByWidth * boardWidth ) ) * DIST_BETWEEN_SPOTS_X - ( .5 * ( DIST_BETWEEN_SPOTS_X - SPOT_WIDTH ) ) );
			
			LocXY[j][0] = bpX;
			LocXY[j][1] = bpY;
			
			toPos.setToIdentity();
			toPos.translate(LocXY[j][0],LocXY[j][1]-DIST_BETWEEN_SPOTS_Y*.68);
			
			hexBackgrounds[j] = toPos.createTransformedShape(hexAtOrigin);
			
		}
		
	}	
	public void setGameModel( IGameModel model ) {
		
		gameModel = (IPegitoryModel)model;
		
	}
		
	@Override
	public void drawBoardBackground( Graphics2D g2D ) {
		for(int i = 1; i<=gameModel.getNumBoardLocs(); i++){
			if(gameModel.getTerritory(i)==0){
				g2D.setColor(NO_TERRITORY);
				g2D.fill( hexBackgrounds[i-1] );
			}else if(gameModel.getTerritory(i)==1){
				g2D.setColor(BLUE_TERRITORY);
				g2D.fill( hexBackgrounds[i-1] );
			}else if(gameModel.getTerritory(i)==2){
				g2D.setColor(RED_TERRITORY);
				g2D.fill( hexBackgrounds[i-1] );
			}
		}
	}
	
	@Override
	public void createShapeBackground(){
		ShapeBackground = new GeneralPath();
		ShapeBackground.moveTo( midX, topMidY );
		ShapeBackground.lineTo(left, topSideY);
		ShapeBackground.lineTo(left, botSideY);
		ShapeBackground.lineTo(midX, botMidY);
		ShapeBackground.lineTo(right, botSideY);
		ShapeBackground.lineTo(right, topSideY);
		ShapeBackground.closePath();
	}
		
	protected void drawLines(Graphics2D g2D) {	
		outline = new BasicStroke();
		g2D.setColor(BOARD_LINE_COLOR);
		for( int id = 0; id < boardLocs; id++ ) {
			outline.createStrokedShape( hexBackgrounds[id] );
			g2D.draw( hexBackgrounds[id] );
		}
	}
	
	//override recolors pegs to yellow
	@Override
	protected Color selectHoleLocationColor(int i, Color c) {
		if( gameModel.isPegAtLoc( i + 1 ) ) {
			
			if( gameModel.getFirstLoc() == i + 1 )
				c = SPOT_COLOR_PICKED;
			else
				c = SPOT_COLOR_PEG;
		}
		return c;
	}
	
	public boolean drawBoard( Graphics2D g2D ) {
		boolean returnValue = false;
		drawBoardBackground( g2D );
		drawLines(g2D);
		returnValue = paintPegs(g2D, returnValue);
		return returnValue;
		
	}
	
	protected void paintCircleInsidePegs(Graphics2D g2D, int i, Color c){
		if(gameModel.getPlayersTurn()==2){
			g2D.setColor( RED_TERRITORY );
		} else 
			g2D.setColor( BLUE_TERRITORY );			
		g2D.fillOval( ( int )( LocXY[i][0] - SPOT_WIDTH * .5 ) + 10, ( int )( LocXY[i][1] - SPOT_HEIGHT * .5 ) + 10, SPOT_WIDTH - 20, SPOT_HEIGHT - 20 );
	}
	protected boolean paintPegs(Graphics2D g2D, boolean returnValue) {
		for( int i = 0; i < gameBoard.getNumLocs(); i++ ) {
			
			Color c = SPOT_COLOR_EMPTY;
			
			//If we're doing the "bad move flash" on this location, do special coloring logic
			if( flashBadMove && flashBadMoveLoc == i + 1 ) {
				
				long curTime = new Date().getTime();
				
				if( flashBadMoveBrightFrame )
					c = SPOT_COLOR_PICKED;
				else
					c = BAD_MOVE_FLASH_COLOR;
	
				if( curTime < flashBadMoveEndTime ) {
					
					if( curTime > flashBadMoveNextTime ) {
						
						flashBadMoveBrightFrame = !flashBadMoveBrightFrame;
						flashBadMoveNextTime = curTime + BAD_MOVE_FLASH_FRAMETIME;
						
					}
					
				} else
					flashBadMove = false;
				
				returnValue = true;
			
			//Otherwise, this is a normal loc, either a peg or a hole.
			} else {
				c = selectHoleLocationColor(i, c);
			}
	
			g2D.setColor( c );
			g2D.fillOval( ( int )( LocXY[i][0] - SPOT_WIDTH * .5 ), ( int )( LocXY[i][1] - SPOT_HEIGHT * .5 ), SPOT_WIDTH, SPOT_HEIGHT );
			
		
			//Inner circle drawing (for aesthetic)
			if( c != SPOT_COLOR_EMPTY ) {
				paintCircleInsidePegs(g2D, i, c);
			}	
		}
		return returnValue;
	}

	
}
