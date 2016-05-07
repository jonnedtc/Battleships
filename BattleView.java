import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.util.Observable;
import java.util.Observer;
@SuppressWarnings("serial")
public class BattleView extends JPanel implements Observer {
	private BattleModel currentModel;
	public BattleView(BattleModel model) {
		currentModel = model;
		currentModel.addObserver(this);
	}
	public void update(Observable obs, Object arg) {
		repaint();
	}
	public void paintComponent(Graphics g) {
		//draw background
		g.setColor(Color.white);
		g.fillRect(0,0,400,400);
		
		//draw all ships
		for(Ship ship : currentModel.getShipList()) {
			g.setColor(Color.black);
			g.fillRect(ship.getX()*40, ship.getY()*40, ship.getWidth()*40, ship.getHeight()*40);
		}
		//show shots
		for(Shot shot : currentModel.getShotList()) {
			if(shot.gotHit()){
				//draw hit shot
				g.setColor(Color.red);
				g.fillRect(shot.getX()*40+5, shot.getY()*40+5, 30, 30);
			} else {
				//draw missed shot
				g.setColor(Color.blue);
				g.fillRect(shot.getX()*40+5, shot.getY()*40+5, 30, 30);
			}
		}
		
		//draw grid
		g.setColor(Color.black);
		for(int i = 0; i<=400; i+=40){
			g.drawLine(0,i,400,i);
			g.drawLine(i,0,i,400);
		}
	}
}	