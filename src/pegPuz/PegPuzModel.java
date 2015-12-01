package pegPuz;

public class PegPuzModel extends GameAbstractModel {

	private static final int PEG_PUZZLE_INITIAL_HOLE_LOC = 1;

	public PegPuzModel(GamePlayableBoard board) {
		super(board, PEG_PUZZLE_INITIAL_HOLE_LOC);
	}

}