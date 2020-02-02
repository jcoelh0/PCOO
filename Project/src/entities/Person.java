package entities;

import java.awt.Point;
import java.util.List;
import path.Node;
import pt.ua.gboard.GBoard;

/**
 *
 * @author João Coelho
 */
public interface Person {
	
	public Point goToPosition(int currentLine, int currentColumn, List<Node> positions);
	public boolean moveToPosition(int currentLine, int currentColumn, int line, int column);
	
}
