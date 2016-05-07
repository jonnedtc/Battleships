import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
@SuppressWarnings("serial")
public class MenuPanel extends JPanel {
	private PanelChange pc;
	private JButton resumeButton;
	public MenuPanel(PanelChange pc) {
		this.pc = pc;
		setBounds(0,0,900,560); 
		setLayout(null); //to add panels, textfields and/or buttons to absolute positions
		
		//startButton
		JButton startButton = new JButton("START NEW GAME");
		startButton.setBounds(330,40,240,40);
		startButton.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
				start();
			}
		});  
		add(startButton);
		
		//startButton
		resumeButton = new JButton("RESUME PREVIOUS GAME");
		resumeButton.setBounds(330,120,240,40);
		resumeButton.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
				resume();
			}
		});  
		resumeButton.setEnabled(false);
		add(resumeButton);
	}
	public void start() {
		pc.setResume(false);
		pc.change();
	}
	public void resume() {
		pc.setResume(true);
		pc.change();
	}
	public void unlockResume() {
		resumeButton.setEnabled(true);
	}
}