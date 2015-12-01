package pegPuz;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;
import java.awt.Shape;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;
import java.util.Date;

public abstract class GameAbstractDrawableBoard implements IDrawableBoard {
	
	protected IPlayableBoard gameBoard;
	protected IGameModel gameModel;
	
	protected int TOP_CENTER_X = 165; //The top of the triangle
	protected int TOP_CENTER_Y = 70; 
	
	protected int DIST_BETWEEN_SPOTS_X = 45; //The X/Y distance between each spot
	protected int DIST_BETWEEN_SPOTS_Y = 65;
	
	protected int SPOT_WIDTH = 50; //Size of the circle at each spot
	protected int SPOT_HEIGHT = 50;
	
	protected Color BOARD_COLOR = new Color( 180, 180, 255 );
	protected Color BOARD_LINE_COLOR = new Color( 70, 70, 180 );
	protected Color SPOT_COLOR_PEG = new Color( 255, 10, 10 );
	protected Color SPOT_COLOR_EMPTY = new Color( 220, 220, 220);
	protected Color SPOT_COLOR_PICKED = new Color( 255, 100, 100 );
	
	protected static final int BAD_MOVE_FLASH_DURATION = 1000;
	protected static final int BAD_MOVE_FLASH_FRAMETIME = 100;
	protected static final Color BAD_MOVE_FLASH_COLOR = new Color( 180, 10, 10 );

	protected boolean flashBadMove = false;
	protected boolean flashBadMoveBrightFrame = false;
	protected int flashBadMoveLoc = 0;
	protected long flashBadMoveNextTime = 0;
	protected long flashBadMoveEndTime = 0;
	
	protected List< Double[][] > DrawableLines;
	
	protected GeneralPath ShapeBackground;
	protected int ShapeCornerCount = 0;
	protected int ShapeCorners[];
	
	protected double LocXY[][];
	
	public void initBoard( IPlayableBoard board ) {
		
		gameBoard = board;
		createBoard();
		
	}
	
	public void setGameModel( IGameModel model ) {
		
		gameModel = model;
		
	}
	
	public void createBoard() {

		int boardLocs = gameBoard.getNumLocs();
		int boardWidth = gameBoard.getBoardWidth();
		//int boardHeight = gameBoard.getBoardHeight(); 	//not used
		//int boardIndices = boardWidth * boardHeight;		//not used
		
		LocXY = new double[boardLocs][2];
		
		//Generate visual board information based on the playable board set-up.
		for( int j = 0; j < boardLocs; j++ ) {
			
			int bpIndex = gameBoard.getBoardPieceFromLocation( j + 1 );
			int bpByWidth = ( bpIndex / boardWidth );
			
			double bpY = ( bpByWidth * DIST_BETWEEN_SPOTS_Y - ( .5 * ( DIST_BETWEEN_SPOTS_Y - SPOT_HEIGHT ) ) );
			double bpX = ( ( bpIndex - ( bpByWidth * boardWidth ) ) * DIST_BETWEEN_SPOTS_X - ( .5 * ( DIST_BETWEEN_SPOTS_X - SPOT_WIDTH ) ) );
			
			LocXY[j][0] = bpX;
			LocXY[j][1] = bpY;
			
		}
		
		createShapeLines();
		
	}
	
	public void createShapeLines() {
		
		DrawableLines = new ArrayList< Double[][] >();
		
		//Loop through every location on the board, and generate information about a line from the loc to each of its neighbors
		//We then store the line info in an array so we can draw all the lines later on
		for( int i = 0; i < gameBoard.getNumLocs(); i++ ) {
			
			int bpIndex = gameBoard.getBoardPieceFromLocation( i + 1 );
			
			for( int j = 0; j < 8; j++ ) {
			
				int bpNeighbor = gameBoard.getBoardPieceInDir( bpIndex, j );
				int srcLoc = gameBoard.getLocationFromBoardPiece( bpIndex );
				int dstLoc = gameBoard.getLocationFromBoardPiece( bpNeighbor );
				
				if( srcLoc > -1 && dstLoc > -1 ) {
			
					Double lineXY[][] = new Double[2][2];
					
					for( int u = 0; u < 2; u++ )
						lineXY[0][u] = LocXY[srcLoc - 1][u];
					
					for( int u = 0; u < 2; u++ )
						lineXY[1][u] = LocXY[dstLoc - 1][u];
					
					DrawableLines.add( lineXY );
					
				}
				
			}
			
		}
		
	}
	
	public void createShapeBackground() {
		
		//Create a GeneralPath shape from our ShapeCorners array (our shape background)
		ShapeBackground = new GeneralPath();
		
		ShapeBackground.moveTo( LocXY[ShapeCorners[0]][0], LocXY[ShapeCorners[0]][1] );
		
		for( int j = 1; j < ShapeCornerCount; j++ ) {
			
			ShapeBackground.lineTo( LocXY[ShapeCorners[j]][0], LocXY[ShapeCorners[j]][1] );
		
		}
		
		ShapeBackground.closePath();		
		
	}
	
	public void drawBoardBackground( Graphics2D g2D ) {

		g2D.setColor(BOARD_COLOR);
		g2D.fill( (Shape)ShapeBackground );
		
	}
	
	//We're going to return true to tell the game view to repaint (to show the flashing)
	public boolean drawBoard( Graphics2D g2D ) {

		boolean returnValue = false;
		
		drawBoardBackground( g2D );
		
		drawLines(g2D);
		
		returnValue = paintPegs(g2D, returnValue);
		
		return returnValue;
		
		
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

	protected Color selectHoleLocationColor(int i, Color c) {
		if( gameModel.isPegAtLoc( i + 1 ) ) {
			
			if( gameModel.getFirstLoc() == i + 1 )
				c = SPOT_COLOR_PICKED;
			else
				c = SPOT_COLOR_PEG;
		}
		return c;
	}

	protected void drawLines(Graphics2D g2D) {	
		g2D.setColor(BOARD_LINE_COLOR);
		g2D.setStroke(new BasicStroke(2));
		for( int i = 0; i < DrawableLines.size(); i++ ) {
			
			Double line[][] = DrawableLines.get( i );
			
			g2D.draw(new Line2D.Double( line[0][0], line[0][1], line[1][0], line[1][1] ));

		}
	}

	protected void paintCircleInsidePegs(Graphics2D g2D, int i, Color c) {
		int r = ( c.getRed() > 40 )? c.getRed() - 40 : 0;
		int g = ( c.getGreen() > 40 )? c.getGreen() - 40 : 0;
		int b = ( c.getBlue() > 40 )? c.getBlue() - 40 : 0;
		
		Color c2 = new Color( r, g, b );
		g2D.setColor( c2 );
		g2D.fillOval( ( int )( LocXY[i][0] - SPOT_WIDTH * .5 ) + 10, ( int )( LocXY[i][1] - SPOT_HEIGHT * .5 ) + 10, SPOT_WIDTH - 20, SPOT_HEIGHT - 20 );
	}
	
	
	public void doBadMoveFlash( int loc ) {
		
		long curTime = new Date().getTime();
		
		flashBadMoveLoc = loc;
		flashBadMove = true;
		flashBadMoveBrightFrame = true;
		flashBadMoveEndTime = curTime + BAD_MOVE_FLASH_DURATION;
		flashBadMoveNextTime = curTime + BAD_MOVE_FLASH_FRAMETIME;
		
		
	}
	
	public int getSpotAtXY( int x, int y ) {
		
		for( int i = 0; i < gameBoard.getNumLocs(); i++ ) {
			
			int x1 = ( int )( LocXY[i][0] - SPOT_WIDTH * .5 );
			int y1 = ( int )( LocXY[i][1] - SPOT_HEIGHT * .5 );
			int x2 = x1 + SPOT_WIDTH;
			int y2 = y1 + SPOT_HEIGHT;
			
			if( y > y1 && y < y2 ) {
				
				if( x > x1 && x < x2 ) {
					
					return i;
					
				}
				
			}
			
		}
		
		return -1;
		
	}

}
