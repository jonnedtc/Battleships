public class Shot {
	private int x;
	private int y;
	private boolean hit = false;
	private boolean down = false;
	public Shot(int x, int y) {
		this.x = x;
		this.y = y;
	}
	public void isHit() {
		hit = true;
	}
	public void isDown() {
		down = true;
	}
	public boolean equals(Shot s) {
		if(s.getX() == x && s.getY() == y) {
			return true;
		}
		return false;
	}
	public int getX() { return x; }
	public int getY() { return y; }
	public boolean gotHit() { return hit; }
	public boolean wentDown() { return down; }
}	