package games.sokoban.sokoban2;

public interface IUpdateable {
	public void updateState(boolean fullUpdate);
	public void updateState(int x1, int y1, int x2, int y2);
	public void updateState(String text);
}
