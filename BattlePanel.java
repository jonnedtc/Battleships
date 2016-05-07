import javax.swing.JPanel;
import java.util.List;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import javax.swing.Timer;
import java.awt.Color; 
import java.awt.Cursor;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.Random;
@SuppressWarnings("serial")
public class BattlePanel extends JPanel implements Observer {
	private PanelChange pc;
	private List<Ship> shipList;
	private BattleModel playerModel;
	private BattleModel AIModel;
	private JButton menuButton;
	private JButton restartButton;
	private JTextField textField;
	private AI ai;
	private boolean playerTurn = true;
	private boolean restart = false;
	public BattlePanel(PanelChange pc, List<Ship> newShipList) {
		this.pc = pc;
		this.shipList = newShipList;
		setBounds(0,0,900,560); 
		setLayout(null); //to add panels, textfields and/or buttons to absolute positions
		
		//playerModel
		playerModel = new BattleModel(shipList);
		playerModel.addObserver(this);
		BattleView bv = new BattleView(playerModel);
		bv.setVisible(true);
		bv.setBounds(20,20,401,401);
		add(bv);
		
		//AIModel
		AIModel = new BattleModel(randomShipList());
		AIModel.addObserver(this);
		PlayerBattleView pbv = new PlayerBattleView(AIModel);
		pbv.setVisible(true);
		pbv.setBounds(460,20,401,401);
		add(pbv);
		
		//menuButton
		menuButton = new JButton("MENU");
		menuButton.setBounds(20,460,240,40);
		menuButton.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
				menu();
			}
		});  
		add(menuButton);
		
		//restartButton
		restartButton = new JButton("RESTART");
		restartButton.setBounds(300,460,240,40);
		restartButton.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
				restart();
			}
		});  
		add(restartButton);
		
		//textfield
		textField = new JTextField("   YOUR TURN");
		textField.setBounds(580,460,280,40);
		textField.setEditable(false);
		textField.setForeground(Color.black);
		textField.setBackground(Color.white);
		add(textField);
		
		//enable player to shoot at AI
		ai = new AI(playerModel);
		AIModel.enable();
		setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		
		//make it visible
		setVisible(true);
	}
	public List<Ship> randomShipList() {
		List<Ship> aiShipList = new ArrayList<Ship>();
		int dx,dy,dw,dh;
		Random generator = new Random();
		boolean possible;
		//place the six ships
		for(int i = 6; i>=2; i--) {
			do {
				//50% horizontal and 50% vertical
				possible = false;
				if(generator.nextInt(2)>0) {
					dx = generator.nextInt( 9-i );
					dy = generator.nextInt( 9 );
					dw = i;
					dh = 1;
				} else {
					dx = generator.nextInt( 9 );
					dy = generator.nextInt( 9-i );
					dw = 1;
					dh = i;
				}
				possible = checkShip(aiShipList,dx,dy,dw,dh);
			} while(!possible);
			aiShipList.add(new Ship(dx,dy,dw,dh));
		}
		return aiShipList;
	}
	private boolean checkShip(List<Ship> aiShipList,int dx,int dy,int dw,int dh) {
		for(Ship ship : aiShipList) {
			//check if the ship to place is colliding with an already existing ship
			if((dx<ship.getX()+ship.getWidth()) && (dx+dw>ship.getX()) && (dy<ship.getY()+ship.getHeight()) && (dy+dh>ship.getY())) { 
				return false;
			}
		}
		return true;
	}
	public void update(Observable obs, Object arg) {
		if(!restart){
			playerTurn = !playerTurn;
			if(playerTurn){
				//check if game is lost by player
				if(playerModel.gameLost()) {
					textField.setText("   COMPUTER WINS");
					setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				} else {
					//let the player shoot at AI
					textField.setText("   YOUR TURN");
					AIModel.enable();
					setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				}
			} else {
				//check if game is lost by AI
				if(AIModel.gameLost()) {
					textField.setText("   YOU WIN!");
				} else {
					//let the AI shoot at the player
					textField.setText("   WAITING FOR COMPUTER...");
					setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
					Timer timer = new Timer(500, ai);
					timer.setRepeats(false);
					timer.start(); 
				}
			}
		}
	}
	public void menu() {
		pc.change();
	}
	public void restart() {
		restart = true;
		for(Ship ship : shipList) {
			ship.getUp();
		}
		AIModel.setShipList(randomShipList());
		AIModel.setShotList(new ArrayList<Shot>());
		playerModel.setShipList(shipList);
		playerModel.setShotList(new ArrayList<Shot>());
		AIModel.enable();
		playerTurn = true;
		AIModel.nudge();
		playerModel.nudge();
		restart = false;
	}
}	