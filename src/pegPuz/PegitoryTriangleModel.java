package pegPuz;

public class PegitoryTriangleModel extends PegitoryAbstractModel implements IPegitoryModel {

	private static final int TRIANGLE_PEGITORY_INITIAL_HOLE_LOC = 1;
		private int[] player1Territory = {4, 6, 7, 10, 11, 13, 15};
		private int[] player2Territory = {2, 3, 5, 8, 9, 12, 14};

	public PegitoryTriangleModel(GamePlayableBoard board) {
		super(board, TRIANGLE_PEGITORY_INITIAL_HOLE_LOC);
		player1territory = new int[7];
		player2territory = new int[7];
		for(int i = 0; i<player1Territory.length; i++){
			player1territory[i]=player1Territory[i];
			player2territory[i]=player2Territory[i];
		}
	}
}
