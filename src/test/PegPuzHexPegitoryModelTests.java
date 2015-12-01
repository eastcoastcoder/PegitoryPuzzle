package test;

import pegPuz.*;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class PegPuzHexPegitoryModelTests {

	PegitoryHexagonModel model;
	GameHexagonPlayableBoard board;
	
	@Before
	public void setUp() throws Exception{

		board = new GameHexagonPlayableBoard();

		model = new PegitoryHexagonModel(board);

		model.newGame();
	}
	
	@Test
	public void getPlayerOneScoreTests(){
		int test = model.getPlayerOneScore();
		assertEquals("The constructor should make the initial score 0 for player 1", 0, test);
		model.handleMoveAtLoc(12);
		model.handleMoveAtLoc(10);
		assertEquals("Playing peg 12->10 should increase the score for player 1 to 2", 0, model.getPlayerOneScore());
		model.handleMoveAtLoc(9);
		model.handleMoveAtLoc(11);
		assertEquals("Playing peg 9->11 should decrease the score for player 1 to 1", 1, model.getPlayerOneScore());
	}
	
	@Test
	public void getPLayerTwoScoreTests(){
		assertEquals("The constructor should make the initial score 0 for player 2", 0, model.getPlayerTwoScore());
		model.handleMoveAtLoc(12);
		model.handleMoveAtLoc(10);
		assertEquals("Playing peg 12->10 should not change the score for player 2", 2, model.getPlayerTwoScore());
		model.handleMoveAtLoc(9);
		model.handleMoveAtLoc(11);
		assertEquals("Playing peg 9->11 should increase the score for player 2 to 1", 1, model.getPlayerTwoScore());
	}

	@Test
	public void getTerritoryTests(){
		//tests for inputs into getTerritory that are less than 1 and therefore not holes on the board
		for(int i=-10; i<=0; i++){
			assertEquals("The getTerritory method should return -1 (invalid holeLoc) for the input" + i, -1, model.getTerritory(i));
		}
		
		//tests for inputs into getTerritory that should return 1 for player one's territory
		for(int i=1; i<=5; i++){
			int test = model.getTerritory(i);
			assertEquals("The getTerritory method should return 1 (player 1) for the hole location " + i, 1, test);
		}
		for(int i=8; i<=9; i++){
			assertEquals("The getTerritory method should return 1 (player 1) for the hole location " + i, 1, model.getTerritory(i));
		}
		for(int i=13; i<=14; i++){
			assertEquals("The getTerritory method should return 1 (player 1) for the hole location " + i, 1, model.getTerritory(i));
		}
		
		//test the neutral location that should return 0
		assertEquals("The getTerritory method should return 0 (neutral) for the hole location 10", 0, model.getTerritory(10));
		
		for(int i=6; i<=7; i++){
			assertEquals("The getTerritory method should return 2 (player 2) for the hole location" + i, 2, model.getTerritory(i));
		}
		for(int i=11; i<=12; i++){
			assertEquals("The getTerritory method should return 2 (player 2) for the hole location" + i, 2, model.getTerritory(i));
		}
		for(int i=15; i<=19; i++){
			assertEquals("The getTerritory method should return 2 (player 2) for the hole location" + i, 2, model.getTerritory(i));
		}
		
		//tests for inputs into getTerritory that are greater that 19 and therefore not holes on the board
		for(int i=20; i<=30; i++){
			assertEquals("The getTerritory method should return -1 (invalid holeLoc) for the input" + i, -1, model.getTerritory(i));
		}
	}
	
	@Test
	public void getPlayersTurnTests(){
		//initially it should be player one's turn
		assertEquals("The getPlayersTurn method should return 1 for the initial board.", 1, model.getPlayersTurn());
		
		model.handleMoveAtLoc(1);
		model.handleMoveAtLoc(10);
		assertEquals("After move, it should be player 2 turn", 2, model.getPlayersTurn());
		
		
		model.handleMoveAtLoc(15);
		model.handleMoveAtLoc(5);
		assertEquals("After move, players turn should change back to 1", 1, model.getPlayersTurn());
		
		
		model.handleMoveAtLoc(1);
		model.handleMoveAtLoc(16);
		assertEquals("After an invalid move, the players turn should not change back to player 2", 1, model.getPlayersTurn());
	}
	
	@Test
	public void gameOverByTargetValueTests(){
		
		
		//set the win method to WIN_BY_TARGET_VALUE
		model.setWinMethod(WinMethods.WIN_BY_TARGET_VALUE);
		
		//try to set the value to 0, the method should set the target to -1 and return false
		assertFalse("The setWinMethod did not return false when 0 was input", model.setWinTarget(0));
		assertEquals("The setWinMethod did not set the target to -1 when 0 was input", -1, model.getWinTarget());
		
		
		model.setWinTarget(2);
		
		//make move so that blue player gets a score of 2
		model.handleMoveAtLoc(12);
		model.handleMoveAtLoc(10);
		
		assertEquals("The game did not end when blue player got a score of two at the start of the game", EModelStates.GAMEOVER, model.getState());

		
		
		//make a new game
		model.newGame();
		
		//set the winTarget to 3
		model.setWinTarget(3);
		
		//make moves so that blue player gets a score of 3
		model.handleMoveAtLoc(12);
		model.handleMoveAtLoc(10);
		model.handleMoveAtLoc(2);
		model.handleMoveAtLoc(11);
		model.handleMoveAtLoc(9);
		model.handleMoveAtLoc(2);
		model.handleMoveAtLoc(11);
		model.handleMoveAtLoc(9);
		
		assertEquals("The game did not end when the blue player got a score of three", EModelStates.GAMEOVER, model.getState());
	
	
		
		//game a new game
		model.newGame();
		
		//set the winTarget to 9
		model.setWinTarget(9);
		
		//make moves so that the blue red player gets a score of 9
		//
		//
		//
		//
		//
		//
		//
		//
		//
		//
		//
	}
}
