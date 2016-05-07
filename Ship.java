public class Ship {
	private int x;
	private int y;
	private int width;
	private int height;
	private boolean down = false;
	public Ship(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	public void isDown() {
		down = true;
	}
	public void getUp() {
		down = false;
	}
	public int getX() { return x; }
	public int getY() { return y; }
	public int getWidth() { return width; }
	public int getHeight() { return height; }
	public boolean wentDown() { return down; }
	public int getLength() { return (getWidth()>getHeight()) ? getWidth() : getHeight(); }
}	