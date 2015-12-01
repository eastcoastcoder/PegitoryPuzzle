package pegPuz;

public class PegPuzTriangleDrawableBoard extends GameAbstractDrawableBoard {

	public void initBoard( IPlayableBoard board ) {
		
		super.initBoard( board );
		
		ShapeCornerCount = 3;
		ShapeCorners = new int[ShapeCornerCount];
		
		ShapeCorners[0] = 0;
		ShapeCorners[1] = 10;
		ShapeCorners[2] = 14;
		
		createShapeBackground();
		
	}

	
}
