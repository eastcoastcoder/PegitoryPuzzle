package pegPuz;

import java.awt.BorderLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JCheckBox;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class GamePrefsPanel extends JPanel {
	private static final String LOC_TOGGLE = "Toggle Locations";

	private JCheckBox locToggle;
	
	public GamePrefsPanel(){
		
		super.setLayout( new BorderLayout() );
		locToggle = new JCheckBox(LOC_TOGGLE);
		checkBox();
		
		super.add(locToggle);
	}
	
	private void checkBox(){
		locToggle.addItemListener(new ItemListener() {
	         public void itemStateChanged(ItemEvent e) {         
	            if(e.getStateChange()==1)
	            	System.out.println("Checked");
	         }           
	      });
	}
	
	public void updateGUI(IGameModel mdl, String curGame){
		
		switch(curGame){
		case "credits":
			
			return;
			
		case "triPegPuz":
		case "hexPegPuz":
		
			break;
			
		case "triPegitory":
		case "hexPegitory":
		
			break;

		}
		
		
	}
}
