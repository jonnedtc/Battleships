import java.util.Observable;
public class PanelChange extends Observable {
	private boolean resume = false;
	public void change() {
		setChanged();
		notifyObservers();
	}
	public void setResume(boolean b) { resume = b; }
	public boolean getResume() { return resume; }
}