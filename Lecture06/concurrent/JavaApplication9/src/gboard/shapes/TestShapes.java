package gboard.shapes;

import static java.lang.System.*;
import java.awt.*;
import pt.ua.gboard.*;
import pt.ua.gboard.shapes.*;
import static pt.ua.gboard.shapes.Shapes.*;

public class TestShapes
{
   public static void main(String[] args)
   {
      GBoard board = new GBoard("Test ShapeGelem", 3, 3, 100, 100, 2);

      Object[] c1 = {
                       "arc", 0.1, 0.1, 0.8, 0.8, 0, 400,
                       "color", Color.red,
                       "fill-arc", 0.3, 0.3, 0.4, 0.4, 45, 90,
                       "line", 0.25, 0.25, 0.75, 0.25,
                       "color", Color.green,
                       "line-width", 0.1,
                       "line", 0.75, 0.25, 0.75, 0.75,
                       "line-width", 0,
                       "color", Color.blue,
                       "line", 0.75, 0.75, 0.25, 0.75,
                       "color", Color.yellow,
                       "line", 0.25, 0.75, 0.25, 0.25,
                       "color", Color.red,
                       "circle", 0.5, 0.5, 0.2,
                       "color", Color.green,
                       "fill-circle", 0.2, 0.2, 0.1,
                       "color", Color.blue,
                       "fill-oval", 0.7, 0.7, 0.3, 0.2,
                       "color", Color.red,
                       "polygon", 3, 0.1, 0.9, 0.5, 0.8, 0.7, 0.95,
                       "color", Color.gray,
                       "fill-polygon", 5, 0.1, 0.1, 0.3, 0.1, 0.4, 0.2, 0.3, 0.3, 0.2, 0.2,
                       "color", Color.black,
                       "line-dash", 1, 0.05, 0.05,
                       "line-path", 4, 0.05, 0.05, 0.05, 0.95, 0.95, 0.95, 0.95, 0.05,
                       "line-dash", 0,
                       "rectangle", 0.1,0.1,0.2,0.2,
                       "color", Color.green,
                       "fill-rectangle", 0.6,0.6,0.2,0.2,
                    };
      Object[] c2 = {
                       "line-curve-path", 0.1, 0.1, "line", 0.9, 0.1, "curve", 0.7, 0.3, 0.7, 0.7, 0.9, 0.9,
                                                    "line", 0.1, 0.9, "curve", 0.3, 0.7, 0.3, 0.3, 0.1, 0.1, "end",
                       "color", Color.blue,
                       "closed-line-curve-path", 0.8, 0.2, "curve", 0.6, 0.4, 0.6, 0.6, 0.8, 0.8,
                                                           "line", 0.2, 0.8, "curve", 0.4, 0.6, 0.4, 0.4, 0.2, 0.2, "end",
                       "color", Color.red,
                       "fill-line-curve-path", 0.6, 0.6, "curve", 0.35, 0.35, 0.35, 0.85, 0.35, 0.35,
                                                         "line", 0.35, 0.85, "end",
                    };

      board.draw(new ShapeGelem(c2), 0, 0, 0);
      board.draw(new ShapeGelem(c1, true), 1, 1, 0);
      board.draw(new ShapeGelem(c1, false), 2, 2, 0);
   }
}

