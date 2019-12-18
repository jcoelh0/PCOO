
import static java.lang.System.*;
import java.util.Scanner;
import java.awt.*;
import javax.swing.*;
import figures.*;

public class TestFigures {

	static public void main(String[] args) {
		FigureBoard board = FigureBoard.init("Test", 400, 300);
		Figure triangle = new Triangle();
		Figure square = new Square();
		Figure f = new DummyFigure();
		
		board.draw(f);
		board.draw(triangle);
		board.draw(square);
		
		/* Draw figures with:
       *
       * board.draw(...);
       *
       * and erase them with:
       *
       * board.erase(...);
       *
		 */
	}
}
