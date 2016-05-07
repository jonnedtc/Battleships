import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.ArrayList;
@SuppressWarnings("serial")
public class PositionBattleView extends BattleView implements MouseListener {
	private BattleModel currentModel;
	private Ship currentShip;
	private boolean placeShip = true;
	private List<Ship> shipList = new ArrayList<Ship>();
	public PositionBattleView(BattleModel model, Ship firstShip) {
		super(model);
		currentModel = model;
		currentShip = firstShip;
		addMouseListener(this);
	}
	public void setCurrentShip(Ship ship) {
		currentShip = ship;
	}
	public void setPlaceShip(boolean b) {
		placeShip = b;
	}
	public boolean checkShip(int dx, int dy, int dw, int dh) {   
		if(placeShip) {
			//check if the ship is within bounds
			if(dx>=0 && dx<=9 && dy>=0 && dy<=9) {
				if(dx+dw <= 10 && dy+dh <= 10) {
					for(Ship ship : shipList) {
						//check if the ship to place is colliding with an already existing ship
						if((dx<ship.getX()+ship.getWidth()) && (dx+dw>ship.getX()) && (dy<ship.getY()+ship.getHeight()) && (dy+dh>ship.getY())) { 
							return false;
						}
					}
					return true;
				}
			}
		}
		return false;
	}
	public void mouseClicked(MouseEvent e) {
	
	}
	public void mousePressed(MouseEvent e) {
		//check if click is within bounds of grid
		if(e.getX()>0&&e.getX()<400&&e.getY()>0&&e.getY()<400){
			int dx = (e.getX())/40;
			int dy = (e.getY())/40;
			//check if a ship can be placed at that position
			if(checkShip(dx,dy,currentShip.getWidth(),currentShip.getHeight())){
				//place ship
				shipList.add(new Ship(dx,dy,currentShip.getWidth(),currentShip.getHeight()));
				currentModel.setShipList(shipList);
				currentModel.nudge();
			}
		}
	}
	public void mouseReleased(MouseEvent e) {

	}
	public void mouseEntered(MouseEvent e) {
	
	}
	public void mouseExited(MouseEvent e) {
	
	}
	public void reset() {
		shipList.clear();
		currentModel.setShipList(shipList);
		currentModel.nudge();
	}
	public List<Ship> getShipList() { return shipList; }
}	