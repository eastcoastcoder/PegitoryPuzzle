package pegPuz;

import java.awt.FlowLayout;

import javax.swing.JLabel;

@SuppressWarnings("serial")
public class PegPuzLeftPanel extends GameAbstractPanel {
	
	protected static final String STR_COUNTER_LBL = "Moves: ";
	
	private PegPuzModel mdl;
	
	public PegPuzLeftPanel(PegPuzModel m){
		
		super();
		updatableLabel = new JLabel( STR_COUNTER_LBL );
		super.add(updatableLabel);
		mdl =  m;
		
	}
	
	@Override
	public void updateGUI(){
		
		updatableLabel.setText( STR_COUNTER_LBL + "\n" + Integer.toString( mdl.getMoveCount()) );
		
	}

}
