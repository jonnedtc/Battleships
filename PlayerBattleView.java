import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
@SuppressWarnings("serial")
public class PlayerBattleView extends BattleView implements MouseListener {
	private BattleModel currentModel;
	public PlayerBattleView(BattleModel model) {
		super(model);
		currentModel = model;
		addMouseListener(this);
	}

	public void paintComponent(Graphics g) {
		//draw background
		g.setColor(Color.white);
		g.fillRect(0,0,400,400);
		
		for(Ship ship : currentModel.getShipList()) {
			//draw ship only when the whole ship is down
			if(ship.wentDown()) {
				g.setColor(Color.black);
				g.fillRect(ship.getX()*40, ship.getY()*40, ship.getWidth()*40, ship.getHeight()*40);
			}
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
	public void mouseClicked(MouseEvent e) {
	
	}
	public void mousePressed(MouseEvent e) {
		//check if the click was within the bounds of this view
		if(e.getX()>0&&e.getX()<400&&e.getY()>0&&e.getY()<400){
			int dx = (e.getX())/40;
			int dy = (e.getY())/40;
			currentModel.shot(dx,dy);
		}
	}
	public void mouseReleased(MouseEvent e) {

	}
	public void mouseEntered(MouseEvent e) {
	
	}
	public void mouseExited(MouseEvent e) {
	
	}
}	