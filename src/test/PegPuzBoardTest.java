package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import pegPuz.GamePlayableBoard;
import pegPuz.IPlayableBoard.Piece;

public class PegPuzBoardTest {

	GamePlayableBoard board;
	int locID;
	
	@Before
	public void setUp() throws Exception{
		board = new GamePlayableBoard();

	}
	
	@Test
	public void testCountOnBoard(){
		assertEquals("Should contain 0 PEGS at start", 0, board.countOnBoard(Piece.PEG));
		
		// Set piece at position 1 (1-based)
		board.setWhatIsAtLoc(1, Piece.PEG);
		assertEquals("Should contain 1 PEG after set", 1, board.countOnBoard(Piece.PEG));
		board.setWhatIsAtLoc(15,Piece.PEG);
		assertEquals("Should contain 2 PEGs after set", 2, board.countOnBoard(Piece.PEG));
	}
	
	// fillWith is void, so test against isBoardEmpty
	@Test
	public void testFillWith(){
		board.fillWith(Piece.PEG);
		assertFalse("Board is not empty", board.isBoardEmpty());
	}
	
	@Test
	public void testGetLocInDir(){
		int loc = 5;
		int neighborLoc = 9;
		assertEquals("SE of " + loc + " is: " + neighborLoc, neighborLoc, board.getLocInDir(loc, GamePlayableBoard.SE));	

		loc = 7;
		neighborLoc = -1;
		assertEquals("NW of " + loc + " is: " + neighborLoc, neighborLoc, board.getLocInDir(loc, GamePlayableBoard.NW));	
		
		loc = 10;
		neighborLoc = -1;
		assertEquals("E of " + loc + " is: " + neighborLoc, neighborLoc, board.getLocInDir(loc, GamePlayableBoard.E));
		
	}
	
	
	@Test
	public void testGetNeighborDir(){
		int full[] = new int[]{GamePlayableBoard.NE, GamePlayableBoard.E, GamePlayableBoard.SE, GamePlayableBoard.SW, GamePlayableBoard.W, GamePlayableBoard.NW};
		locID = 5;
		assertArrayEquals("Get neighbors of locID: " + locID, full , board.getNeighborDir(locID));
		
		int notSoFull[] = new int[]{GamePlayableBoard.NE, GamePlayableBoard.E};
		locID = 11;
		assertArrayEquals("Get neighbors of locID: " + locID, notSoFull , board.getNeighborDir(locID));
		
	}
	
	//Tests both setWhatIsAtLoc and getWhatIsAtLoc
	@Test
	public void testSetAndGetWhatIsLoc(){
		locID = 4;
		board.setWhatIsAtLoc(locID, Piece.PEG);
		assertEquals("Get state of locID: " + locID + "(PEG?)", Piece.PEG, board.getWhatIsAtLoc(locID) );
		assertNotEquals("Get state of locID: " + locID + "(HOLE?)", Piece.HOLE, board.getWhatIsAtLoc(locID) );
		
		locID = 1;
		board.setWhatIsAtLoc(locID, Piece.PEG);
		assertNotEquals("Get state of locID: " + locID + "(HOLE?)", Piece.HOLE, board.getWhatIsAtLoc(locID) );
		
	}
		
	@Test
	public void testIsBoardEmpty(){
		board.fillWith(Piece.HOLE);
		assertTrue( "Should be Empty upon fill", board.isBoardEmpty());
	}
	
	
}
