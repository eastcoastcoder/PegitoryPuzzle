package pegPuz;

public class PegPuzHexagonDrawableBoard extends GameAbstractDrawableBoard {
	
	public void initBoard( IPlayableBoard board ) {
		
		super.initBoard( board );
		
		ShapeCornerCount = 6;
		ShapeCorners = new int[ShapeCornerCount];
		
		ShapeCorners[0] = 0;
		ShapeCorners[1] = 2;
		ShapeCorners[2] = 11;
		ShapeCorners[3] = 18;
		ShapeCorners[4] = 16;
		ShapeCorners[5] = 7;
		
		createShapeBackground();
		
	}

	

}
