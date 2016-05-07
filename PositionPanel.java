import javax.swing.JPanel;
import java.util.List;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.util.Observable;
import java.util.Observer;
import java.awt.Color; 
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
@SuppressWarnings("serial")
public class PositionPanel extends JPanel implements Observer {
	private PanelChange pc;
	private BattleModel positionModel;
	private PositionBattleView bv;
	private List<Ship> shipList = new ArrayList<Ship>();
	private JTextField textField;
	private JButton startButton;
	private JButton turnButton;
	private int shipSize = 6;
	private boolean shipHor = true;
	public PositionPanel(PanelChange pc) {
		this.pc = pc;
		setBounds(0,0,900,560);
		setLayout(null); //to add panels, textfields and/or buttons to absolute positions
		
		//positionModel
		positionModel = new BattleModel(shipList);
		positionModel.addObserver(this);
		bv = new PositionBattleView(positionModel, new Ship(0,0,6,1));
		bv.setVisible(true);
		bv.setBounds(20,20,401,401);
		add(bv);
		
		//textfield
		String text2 = "      SELECT THE SQUARE ON THE GRID WHERE YOU WANT";
		JTextField textField2 = new JTextField(text2);
		textField2.setBounds(440,20,400,40);
		textField2.setEditable(false);
		textField2.setForeground(Color.black);
		textField2.setBackground(Color.white);
		add(textField2);
		String text3 = "      THE LEFT UPPER CORNER OF THE SHIP TO APPEAR";
		JTextField textField3 = new JTextField(text3);
		textField3.setBounds(440,60,400,40);
		textField3.setEditable(false);
		textField3.setForeground(Color.black);
		textField3.setBackground(Color.white);
		add(textField3);
		
		//textfield
		String text = "   SHIP TO PLACE      WIDTH: "+6+"      HEIGHT: "+1;
		textField = new JTextField(text);
		textField.setBounds(440,140,400,40);
		textField.setEditable(false);
		textField.setForeground(Color.black);
		textField.setBackground(Color.white);
		add(textField);
		
		//turnButton
		turnButton = new JButton("HORIZONTAL");
		turnButton.setBounds(440,220,180,40);
		turnButton.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
				turnShips();
			}
		});  
		add(turnButton);
		
		//startButton
		startButton = new JButton("START GAME");
		startButton.setBounds(660,220,180,40);
		startButton.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
				menu();
			}
		});
		startButton.setEnabled(false);
		add(startButton);
		
		//make it visible
		setVisible(true);
	}
	public void menu() {
		pc.change();
	}
	public void turnShips() {
		shipHor = !shipHor;
		if(shipHor) {
			turnButton.setText("HORIZONTAL");
		} else {
			turnButton.setText("VERTICAL");
		}
		String text;
		if(shipHor) {
			text = "   SHIP TO PLACE      WIDTH: "+shipSize+"      HEIGHT: "+1;
			bv.setCurrentShip(new Ship(0,0,shipSize,1));
		} else {
			text = "   SHIP TO PLACE      WIDTH: "+1+"      HEIGHT: "+shipSize;
			bv.setCurrentShip(new Ship(0,0,1,shipSize));
		}
		textField.setText(text);
	}
	public void update(Observable obs, Object arg) {
		if(shipSize>2) {
			shipSize--;
			String text;
			if(shipHor) {
				text = "   SHIP TO PLACE      WIDTH: "+shipSize+"      HEIGHT: "+1;
				bv.setCurrentShip(new Ship(0,0,shipSize,1));
			} else {
				text = "   SHIP TO PLACE      WIDTH: "+1+"      HEIGHT: "+shipSize;
				bv.setCurrentShip(new Ship(0,0,1,shipSize));
			}
			textField.setText(text);
		} else {
			textField.setText("   PRESS START TO PLAY");
			startButton.setEnabled(true);
			turnButton.setEnabled(false);
			bv.setPlaceShip(false);
		}
	}
	public void reset() {
		bv.reset();
		shipSize = 6;
		shipHor = true;
		bv.setPlaceShip(true);
		turnButton.setEnabled(true);
		startButton.setEnabled(false);
		bv.setCurrentShip(new Ship(0,0,shipSize,1));
		textField.setText("   SHIP TO PLACE      WIDTH: "+6+"      HEIGHT: "+1);
	}
	public List<Ship> getShipList() { return bv.getShipList(); }
}