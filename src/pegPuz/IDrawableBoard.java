package pegPuz;

import java.awt.Graphics2D;

import pegPuz.IPlayableBoard.Piece;

public interface IDrawableBoard {
	
	void setGameModel( IGameModel gameModel );
	boolean drawBoard( Graphics2D g2D );
	int getSpotAtXY( int x, int y );
	void doBadMoveFlash( int loc );
	void initBoard( IPlayableBoard board );
}
