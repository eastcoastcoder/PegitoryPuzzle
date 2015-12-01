package test;

import pegPuz.*;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class PegPuzGameModelTest {

	PegPuzModel logic;
	GamePlayableBoard baseBoard;
	int locID;
	
	@Before
	public void setUp() throws Exception{
		baseBoard = new GamePlayableBoard();
		logic = new PegPuzModel(baseBoard);		
	}
	
	@Test
	public void testNewGame() {
		logic.newGame();
		
		assertEquals("Upon newGame, state is READY", EModelStates.READY, logic.getState());
	}
	
	@Test
	public void testGamePlay() {
		logic.newGame();
		logic.makeFirstMove(6);
		assertTrue("Move should be possible", logic.canMoveToPeg( 6, 3, 1 ));
		
		assertTrue("movePeg returns true if move successful", logic.movePeg( 6, 1 ));
		assertTrue("Removed Peg is HOLE", logic.isHoleAtLoc(3));
		assertTrue("Ending location is PEG", logic.isPegAtLoc(1));
		
		logic.makeFirstMove(6);
		assertFalse("Move should NOT be possible", logic.canMoveToPeg( 6, 2, 5 ));

		assertFalse("movePeg returns false if move unsuccessful", logic.movePeg( 6, 5 ));
	}
	
	@Test
	public void testMoveAndRemainingCount(){
	
		logic.setMoveCount(3);
		assertEquals("Upon directly setting move count, moveCount should be 3", 3 ,logic.getMoveCount());
		
		logic.newGame();
		assertEquals("Upon newGame, moveCount should be reset to 0", 0 ,logic.getMoveCount());
		assertEquals("Upon newGame, remainingPegsCount should be 14", 14 ,logic.getRemainingPegsCount());
		
		logic.makeFirstMove(6);
		logic.makeSecondMove(1);
		assertEquals("Upon successful move, moveCount should be 1", 1 ,logic.getMoveCount());
		assertEquals("Upon newGame, remainingPegsCount should be 14", 13 ,logic.getRemainingPegsCount());
		
		logic.makeFirstMove(8);
		logic.makeSecondMove(3);
		assertEquals("Upon successful move, moveCount should be 2", 2 ,logic.getMoveCount());
		assertEquals("Upon newGame, remainingPegsCount should be 14", 12 ,logic.getRemainingPegsCount());
		
	}
	
}
