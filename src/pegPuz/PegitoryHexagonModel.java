package pegPuz;

public class PegitoryHexagonModel extends PegitoryAbstractModel implements IPegitoryModel {
	private static final int HEXAGON_PUZZLE_INITIAL_HOLE = 10;
	private int[] player1Territory = {1, 2, 3, 4, 5, 8, 9, 13, 14};
	private int[] player2Territory = {6, 7, 11, 12, 15, 16, 17, 18, 19};

	
	public PegitoryHexagonModel(GamePlayableBoard playable){
		super(playable, HEXAGON_PUZZLE_INITIAL_HOLE);
		
		player1territory = new int[9];
		player2territory = new int[9];
		for(int i = 0; i<player1Territory.length; i++){
			player1territory[i]=player1Territory[i];
			player2territory[i]=player2Territory[i];
		}
	}
}
