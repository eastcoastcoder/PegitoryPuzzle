package pegPuz;

import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JLabel;

@SuppressWarnings("serial")
public class PegitoryRightPanel extends GameAbstractPanel {
	
	protected static final String STR_PLAYER_LBL = "Red: ";
	
	private IPegitoryModel mdl;
	
	public PegitoryRightPanel(IPegitoryModel m){

		super();
		updatableLabel = new JLabel( STR_COUNTER_LBL );
		super.add(updatableLabel);
		mdl = m;
	
	}
	
	@Override
	public void updateGUI(){
		if(mdl.getPlayersTurn()==2){
			setBackground(Color.RED);
		}
		updatableLabel.setText( STR_PLAYER_LBL + "\n" + Integer.toString( mdl.getPlayerTwoScore() ));
	}

}
