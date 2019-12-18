package figures;

import static java.lang.System.*;
import java.awt.Graphics;

public class DummyFigure extends Figure {

	public DummyFigure() {
		super(10, 30, 70, 75);
	}

	public void internalDraw(Graphics g) {
		g.drawOval(50, 50, 20, 20);
		g.drawRect(10, 30, 60, 45);
	}
}
