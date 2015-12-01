package pegPuz;

import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public abstract class GameAbstractPanel extends JPanel{

	protected static final String STR_PLAYER_LBL = "";
	protected static final String STR_COUNTER_LBL = "";
	
	protected JLabel updatableLabel; 
	
	public GameAbstractPanel(){
		super.setLayout( new FlowLayout() );
	}
	
	public void updateGUI() {
		
	}

}
