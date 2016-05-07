import java.util.List;
import java.util.ArrayList;
import java.util.Observable;
public class BattleModel extends Observable {
	private boolean disabled = true;
	private List<Ship> shipList = new ArrayList<Ship>();
	private List<Shot> shotList = new ArrayList<Shot>();
	public BattleModel(List<Ship> shipList) {
		this.shipList = shipList;
	}
	public boolean shot(int x, int y) {
		//A shot is fired at position (x,y)
		Shot shot = new Shot(x,y);
		//check if shot is legit and if this model is enabled for shooting
		if(checkShot(shot) && !disabled) {
			//add to the array
			shotList.add(shot);
			//loop through list of ships
			for(Ship ship : shipList) {
				//check if ship contains this position
				if(ship.getX() <= x && ship.getX()+ship.getWidth() > x) {
					if(ship.getY() <= y && ship.getY()+ship.getHeight() > y) {
						//shot hit this ship
						shot.isHit();
						checkShip(ship);
					}
				}
			}
			disabled = true;
			setChanged();
			notifyObservers();
			return true;
		}
		return false;
	}
	private boolean checkShot(Shot shot) {
		if(shot.getX()<0||shot.getX()>9||shot.getY()<0||shot.getY()>9) {
			return false;
		}
		for(Shot s : shotList) {
			if(s.equals(shot)){
				return false; //stop function
			}
		}
		return true;
	}
	private void checkShip(Ship ship) {
		boolean allHit = true;
		int x,y;
		for(x = ship.getX(); x<ship.getX()+ship.getWidth(); x++) {
			for(y = ship.getY(); y<ship.getY()+ship.getHeight(); y++) {
				boolean hit = false;
				for(Shot shot : shotList) {
					if(shot.getX() == x && shot.getY() == y) {
						hit = true;
					}
				}
				if(!hit){
					allHit = false;
				}
			}
		}
		if(allHit) {
			//sink the ship
			ship.isDown();
			//sink the shots
			for(x = ship.getX(); x<ship.getX()+ship.getWidth(); x++) {
				for(y = ship.getY(); y<ship.getY()+ship.getHeight(); y++) {
					for(Shot shot : shotList) {
						if(shot.getX() == x && shot.getY() == y) {
							shot.isDown();
						}
					}
				}
			}
		}
	}
	public boolean gameLost() {
		for(Ship ship : shipList) {
			if(!ship.wentDown()) {
				return false;
			}
		}
		return true;
	}
	public void enable() { disabled = false; }
	public void setShipList(List<Ship> newShipList) {
		shipList = newShipList;
	}
	public void setShotList(List<Shot> newShotList) {
		shotList = newShotList;
	}
	public void nudge() {
		setChanged();
		notifyObservers();
	}
	public List<Ship> getShipList() { return shipList; }
	public List<Shot> getShotList() { return shotList; }
}	