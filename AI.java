import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import java.util.List;
public class AI implements ActionListener {
	BattleModel currentModel;
	int[] missedTiles;
	int[] shootTiles;
	public AI(BattleModel model) {
		currentModel = model;
	}
	public void actionPerformed(ActionEvent e) {
		currentModel.enable();
		shoot();
	}
	private void shoot() {
		/*  
			The AI can only use the functions getLength() and getDown() for the shipList,
			since these are these function do not reveal any information that the
			AI can't know in a fair game.
		*/
		Random generator = new Random();
		List<Ship> shipList = currentModel.getShipList();
		List<Shot> shotList = currentModel.getShotList();
		missedTiles = new int[100];
		shootTiles = new int[100];
		int x, y, i, best, numberOfBest, rand;
		boolean missedShip = false;
		boolean goodShot = false;
		boolean hitShots = false;
		
		//array missedTiles is filled with 0, set all hit shots to 1 and set all missed shots to -1.
		for(Shot shot : shotList) {
			if(!shot.gotHit()) {
				missedTiles[shot.getX()+shot.getY()*10] = -1;
			} else {
				missedTiles[shot.getX()+shot.getY()*10] = 1;
				if(!shot.wentDown()){
					hitShots = true;
				}
			}
		}
		
		//fill array shootTiles for each possibility that a ship may lie under it
		if(hitShots) {
			for(Shot shot : shotList) {
				if(shot.gotHit() && !shot.wentDown()) {
					//for all shots that hit, but didn't sink a ship yet
					for(Ship ship : shipList) {
						if(!ship.wentDown()) {
							//for all ships that aren't down yet
							for(i=0;i<ship.getLength();i++) {
								//if the ship is within bounds
								if(shot.getX()+ship.getLength()-i<=10 && shot.getX()-i>=0) {
									missedShip = true;
									//check if there are no missed shots on the position of the ship
									for(x=shot.getX()-i;x<shot.getX()+ship.getLength()-i;x++) {
										if(missedTiles[x+shot.getY()*10] < 0) {
											missedShip = false;
										}
									}
									if(missedShip) {
										for(x=shot.getX()-i;x<shot.getX()+ship.getLength()-i;x++) {
											shootTiles[x+shot.getY()*10]++;
										}
									}
								}
								//if the ship is within bounds
								if(shot.getY()+ship.getLength()-i<=10 && shot.getY()-i>=0) {
									missedShip = true;
									//check if there are no missed shots on the position of the ship
									for(y=shot.getY()-i;y<shot.getY()+ship.getLength()-i;y++) {
										if(missedTiles[shot.getX()+y*10] < 0) {
											missedShip = false;
										}
									}
									if(missedShip) {
										for(y=shot.getY()-i;y<shot.getY()+ship.getLength()-i;y++) {
											shootTiles[shot.getX()+y*10]++;
										}
									}
								}
							}
						}
					}
				}
			}
			
			//check what the highest score of a tile is and how many there are of it
			best = 0;
			numberOfBest = 1;
			for(i=0; i<100;i++) {
				if(missedTiles[i]<1) {
					if(shootTiles[i]==best) {
						numberOfBest++;
					} else if(shootTiles[i]>best) {
						best = shootTiles[i];
						numberOfBest = 1;
					}
				}
			}
			
			//pick a random tile from the tiles with the highest score
			rand = generator.nextInt(numberOfBest);
			numberOfBest = 0;
			for(i=0; i<100;i++) {
				if(missedTiles[i]<1) {
					if(shootTiles[i]==best) {
						if(numberOfBest==rand) {
							//shoot
							x = i;
							y = 0;
							while(x>9) {
								x-=10;
								y++;
							}
							goodShot = currentModel.shot(x,y);
							System.out.println(x+" "+y);
							break;
						} else {
							numberOfBest++;
						}
					}
				}
			}
		}
		
		//shoot random if goodShot is false, meaning no shot has succesfully been placed
		x = generator.nextInt( 10 );
		y = generator.nextInt( 10 );
		while(!goodShot) {
			goodShot = currentModel.shot(x,y);
			if(x<9) {
				x++;
			} else if(y<9) {
				x = 0;
				y++;
			} else {
				x = 0;
				y = 0;
			}
		}
	}
}