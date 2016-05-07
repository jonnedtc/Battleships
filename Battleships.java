import javax.swing.JFrame;
import javax.swing.JPanel;
import java.util.Observable;
import java.util.Observer;
@SuppressWarnings("serial")
public class Battleships extends JFrame implements Observer {
	private JPanel currentPanel;
	private String currentState;
	private BattlePanel bp;
	private PositionPanel pp;
	private MenuPanel mp;
	private PanelChange pc;
	
	public Battleships() {
		pc = new PanelChange();
		pc.addObserver(this);
		
		mp = new MenuPanel(pc);
		pp = new PositionPanel(pc);
		
		//start with menupanel
		currentState = "menu";
		currentPanel = mp;
		add(mp);
		
		//some properties
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(900,560);
		setTitle("Epic Battleships");
		setVisible(true);
	}
	
	public void update(Observable obs, Object arg) {
		remove(currentPanel);
		if(currentState == "menu") {
			//check which button was pressed
			if(pc.getResume()) {
				currentState = "battle";
				currentPanel = bp;
			} else {
				pp.reset();
				currentState = "position";
				currentPanel = pp;
			}
		} else if(currentState == "position") {
			bp = new BattlePanel(pc,pp.getShipList());
			currentState = "battle";
			currentPanel = bp;
		} else if(currentState == "battle") {
			mp.unlockResume();
			currentState = "menu";
			currentPanel = mp;
		}
		add(currentPanel);
		repaint();
	}
	
	public static void main(String[] args) {
		new Battleships();
	}
}