package pegPuz;

import java.awt.FlowLayout;

import javax.swing.JLabel;

@SuppressWarnings("serial")
public class PegPuzRightPanel extends GameAbstractPanel {
	
	protected static final String STR_COUNTER_LBL = "Remaining: ";
	
	private PegPuzModel mdl;
	
	public PegPuzRightPanel(PegPuzModel m){
		
		super();		
		updatableLabel = new JLabel( STR_COUNTER_LBL );
		super.add(updatableLabel);
		mdl = m;
		
	}
	
	@Override
	public void updateGUI(){
		updatableLabel.setText( STR_COUNTER_LBL + "\n" + Integer.toString( mdl.getRemainingPegsCount()) );

	}

}
