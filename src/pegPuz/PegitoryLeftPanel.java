package pegPuz;

import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JLabel;

@SuppressWarnings("serial")
public class PegitoryLeftPanel extends GameAbstractPanel {

	protected static final String STR_PLAYER_LBL = "Blue: ";
	
	private IPegitoryModel mdl;
	
	public PegitoryLeftPanel(IPegitoryModel m){

		super();		
		updatableLabel = new JLabel( STR_COUNTER_LBL );
		super.add(updatableLabel);
		mdl = m;

	}
	
	public void updateGUI(){
		if(mdl.getPlayersTurn()==1){
			Color BLUE_TERRITORY_COLOUR = new Color( 5, 165, 255 );
			setBackground(BLUE_TERRITORY_COLOUR);
		}
		updatableLabel.setText( STR_PLAYER_LBL + "\n" + Integer.toString( mdl.getPlayerOneScore() ));
		
	}

}
