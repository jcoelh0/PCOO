package gboard.basic;

import static java.lang.System.*;
import java.awt.*;
import pt.ua.gboard.*;
import pt.ua.gboard.basic.*;

public class TestPolygonComposedGelem
{
   protected static final GBoard board = new GBoard("Polygon", 3, 3, 15*12, 15*12, 2);

   static public void main(String[] args)
   {
      Gelem pcg = new PolygonComposedGelem(new Color[]{Color.blue,Color.yellow}, new double[][][]{{{0.25,0.25},{0.75,0.25},{0.75,0.75},{0.25,0.75}},{{0.5,0.375},{0.625,0.5},{0.5,0.625},{0.375,0.5}}});
      board.draw(pcg, 0, 0, 0);
      board.draw(pcg, 1, 0, 0);
      board.draw(pcg, 0, 1, 0);
      board.draw(pcg, 1, 1, 0);
   }
}

