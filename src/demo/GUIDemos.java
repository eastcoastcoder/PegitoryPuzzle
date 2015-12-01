package demo;

import org.junit.Before;
import org.junit.runner.RunWith;

import de.jdemo.annotation.Demo;
import de.jdemo.junit.DemoAsTestRunner;
import pegPuz.*;

@RunWith (DemoAsTestRunner.class)
public class GUIDemos extends de.jdemo.extensions.SwingDemoCase implements IUpdateable {

	GameApp app;
	PegPuzModel m;
	
	@Before
	public void setUp() throws Exception {
		
		app = new GameApp( GameApp.STR_WINDOW_TITLE );
		//m = app.getGameModel();
		app.buildGUI();
	}
	
	@Demo
	public void Base() {
		
		app.buildGUI();
		app.updateGUI();
		show(app);
		
	}

	/**
	 * 125: Illegal Move Feedback (A)
	 */ 
	
	@Demo
	public void StartOnPeg() {
		
		int[] moves = {1,1};
		
		playMoves( m, moves );
		app.updateGUI();
		show( app );
		
	}	
	
	@Demo
	public void EndOnEmpty() {
		
		int[] moves = {3,6};
		
		playMoves( m, moves );
		app.updateGUI();
		show( app );
		
	}	
	

	/**
	 * 110: Game Over (A)
	 */ 
	
	@Demo
	public void OnePegLeft() {
		
		int[] moves = {4,1,6,4,15,6,3,10,13,6,11,13,14,12,12,5,10,3,7,2,1,4,4,6};
		
		playMoves( m, moves );
		app.updateGUI();
		show( app );
		
	}	
	@Demo
	public void TwoPegsLeft() {
		
		int[] moves = {6,1,8,3,1,6,10,3,14,5,3,8,12,14,15,0,15,13,7,9,2,7,11,4,13};

		playMoves( m, moves );
		app.updateGUI();
		show( app );
	}
	
	@Demo
	public void ThreePegsLeft() {
		
		int[] moves = {4,1,9,2,11,4,2,7,12,5,3,8,7,9,10,8,14,12,12,5,6};
		
		playMoves( m, moves );
		app.updateGUI();
		show( app );
		
	}
	
	@Demo
	public void FourPegsLeft() {
		
		int[] moves = {4,1,9,2,12,5,3,8,11,4,14,12,12,5,2,9,6,13,15};
		
		playMoves( m, moves );
		app.updateGUI();
		show( app );
		
	}	
	
	@Demo
	public void FivePegsLeft() {
		
		int[] moves = {6,1,8,3,13,6,15,13,6,0,6,15,1,6,4,1,11,4};
	
		playMoves( m, moves );
		app.updateGUI();
		show( app );
		
	}
		
	
	@Demo
	public void MoreThanFivePegsLeft() {
		
		int[] moves = {4,1,11,4,9,2,13,11,2,7,11,4,4,13,14};
		
		playMoves( m, moves );
		app.updateGUI();
		show( app );
	
	}
	
	
	private void playMoves ( PegPuzModel model, int[] loc ) {
		int i = 0;
		System.out.print("{");
		//controller should call model, play loc
		while ( i < loc.length-1 ) {

			model.makeFirstMove( loc[i] );
			System.out.print( loc[i] + "," );
			i++;
			
			model.makeSecondMove( loc[i] );
			System.out.print( loc[i] + "," );
			i++;
			
		}
		System.out.print("}");
	}
	
	@Override
	public void updateGUI() {
		// TODO Auto-generated method stub
		
	}
	
}
