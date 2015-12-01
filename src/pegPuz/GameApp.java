package pegPuz;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.*;
import java.awt.event.*;

@SuppressWarnings("serial")
public class GameApp extends JFrame implements ActionListener, IUpdateable {
	
	public static final String STR_WINDOW_TITLE = "PegPuz";
	private static final String STR_NEWGAME_BTN = "New Game";
	private static final String STR_UNDO_BTN = "<-";
	
	private JFrame  gameFrame;
	private JButton newGameBut, 
					backBut;
	private JPanel  newGamePane, 
					buttonPane; 
	private JTabbedPane jtp;
	private Container cont;
	private String curGame;
	private GamePlayableGame hexPegPuz,
							 triPegPuz,
							 hexPegitory,
							 triPegitory;
	
	private GameAbstractPanel leftPane,
							  rightPane;
	
	private GamePrefsPanel prefsPane;
	
	public GameApp( String winTitle ){
		super(winTitle);
		
		hexPegPuz = new GamePlayableGame( new GameHexagonPlayableBoard(), false);
		triPegPuz = new GamePlayableGame( new GameTrianglePlayableBoard(), false );
		triPegitory = new GamePlayableGame( new GameTrianglePlayableBoard(), true );
		hexPegitory = new GamePlayableGame( new GameHexagonPlayableBoard(), true );
	}
	
	public static void main( String[] args ){
		final GameApp app = new GameApp( STR_WINDOW_TITLE );
		
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				app.createAndShowGUI();
			}
		});
		
	}
	
	public void createAndShowGUI(){
		setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		
		buildGUI();
		pack();
		setVisible( true );
	}
	
	public void buildGUI(){
		cont = getContentPane();
		cont.setLayout( new BorderLayout() );
		initViews();
		addTabs();
		switchSubPanels(null);
		createButtons();
		createAndLoadPanes();
		addPanes( newGamePane, buttonPane, prefsPane );
	
		gameFrame = new JFrame();
		
		ChangeListener tabChange = new ChangeListener() {
			public void stateChanged(ChangeEvent changeEvent) {
				updateGUI();
			}
		};
	    
	    jtp.addChangeListener( tabChange );

		updateGUI();
	}
	
	private void initViews(){	
		hexPegPuz.initView( new PegPuzHexagonDrawableBoard(),  this, false );
		triPegPuz.initView( new PegPuzTriangleDrawableBoard(),  this, false );
		hexPegitory.initView( new PegitoryHexagonDrawableBoard(), this, true);
		triPegitory.initView( new PegitoryTriangleDrawableBoard(), this, true);
	}
	
	//adds tabs to JTabPane
	private void addTabs(){
		jtp = new JTabbedPane();
		jtp.addTab("Tri", triPegPuz.getViewPanel() );
		jtp.addTab("Hex", hexPegPuz.getViewPanel() );
		jtp.addTab("Tri-Pegitory", triPegitory.getGameView() );
	    jtp.addTab("Hex-Pegitory", hexPegitory.getViewPanel() );
	    jtp.addTab("Credits", new GameAboutPanel());
	}
	
	public GameViewer getGameView(){
		return GetCurrentGame().getGameView();
	}
	
	private GamePlayableGame GetCurrentGame() {
		int index = jtp.getSelectedIndex();
		switch(index){
		case 0:
			return triPegPuz;
		case 1:
			return hexPegPuz;
		case 2:
			return triPegitory;
		case 3:
			return hexPegitory;
		case 4:
			//credits
		}
		return triPegPuz;
	}
	
	private void switchSubPanels(IGameModel model){
		int index = jtp.getSelectedIndex();
		switch(index){
			case 0:
			case 1:
				curGame = "PegPuz";
				leftPane = new PegPuzLeftPanel((PegPuzModel) model);
				rightPane = new PegPuzRightPanel((PegPuzModel) model);
				break;
			case 2:
			case 3:
				curGame = "Pegitory";
				leftPane = new PegitoryLeftPanel((IPegitoryModel) model);
				rightPane = new PegitoryRightPanel((IPegitoryModel) model);
				break;
			case 4:
				curGame = "credits";
				//need to clear panes away
		}

	}
	
	//creates buttons, adds listeners
	private void createButtons(){
		newGameBut = new JButton( STR_NEWGAME_BTN );
		backBut = new JButton( STR_UNDO_BTN );
		newGameBut.addActionListener( this );
		backBut.addActionListener( this );
		backBut.setEnabled( false );
	}
	
	//creates panes, 
	//adds buttons to panes,
	//adds subpanes
	private void createAndLoadPanes(){
		newGamePane = new JPanel( new BorderLayout() );
		buttonPane = new JPanel( new BorderLayout() );
		prefsPane = new GamePrefsPanel();
		
		newGamePane.add( newGameBut );
		buttonPane.add( backBut );
		prefsPane.add( rightPane, BorderLayout.SOUTH );
		newGamePane.add( leftPane, BorderLayout.SOUTH );	
	}
		
	//adds topPanel to ContentPane, adds panes to topPanel
	private void addPanes(JPanel newGamePane, JPanel buttonPane, JPanel prefsPane){
		JPanel topPanel = new JPanel( new BorderLayout() );
		cont.add( topPanel, BorderLayout.NORTH );
		topPanel.add( jtp, BorderLayout.CENTER );
		topPanel.add( newGamePane, BorderLayout.WEST );
		topPanel.add( buttonPane, BorderLayout.SOUTH );
		topPanel.add( prefsPane, BorderLayout.EAST );	
	}
		
	public void updateGUI(){
		IGameModel mdl = getGameModel();

		switchSubPanels(mdl);
		prefsPane.add( rightPane, BorderLayout.SOUTH );
		newGamePane.add( leftPane, BorderLayout.SOUTH );
		leftPane.updateGUI();
		rightPane.updateGUI();
		
		gameFrame.repaint();
	}
	
	public IGameModel getGameModel(){
		return GetCurrentGame().getGameModel();
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		JButton buttonContents = (JButton) ae.getSource();

		if ( buttonContents.getText() == STR_NEWGAME_BTN ){
			IGameModel mdl = getGameModel();
			GameViewer view = getGameView();
			view.resetMoveText();
			mdl.newGame();
			view.updateGUI();
		}
		
		//To Be Implemented
		if ( buttonContents.getText() == STR_UNDO_BTN );
		
	}
}