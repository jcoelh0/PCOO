package gboard.basic;

import static java.lang.System.*;

import java.awt.*;
import pt.ua.gboard.*;
import pt.ua.gboard.basic.*;

public class TestMutable
{
   public static void main(String[] args)
   {
      int numIter = 100;
      int sleep = 500;
      if (args.length != 2)
      {
         err.println("Usage: TestMutable <numIter> <ms-sleep-time>");
         out.println();
         out.println("Using: numIter="+numIter+" and sleep="+sleep+"ms as default");
      }
      else
      {
         numIter = Integer.decode(args[0]);
         sleep = Integer.decode(args[1]);
      }

      GBoard board = new GBoard("Test Mutable", 8, 8, 60, 60, 2);
      GBoard board2 = new GBoard("Test Mutable 2", 3, 3, 60, 60, 2);
      String text = "A";
      MutableStringGelem mstr = new MutableStringGelem(text, Color.green);
      board.draw(mstr, 1, 1, 1);
      board.draw(mstr, 0, 0, 1);
      board.draw(mstr, 4, 4, 1);
      board.draw(mstr, 7, 2, 1);
      if (ImageGelem.isImage("Guernica.jpg"))
         board.draw(new ImageGelem("Guernica.jpg", board, 90, 4, 6), 0, 0, 0);
      board2.draw(mstr, 1, 1, 0);
      for(int i = 1; i <= numIter; i++)
      {
         board.sleep(sleep);
         text = ""+(char)((int)'A' + (i%((int)'Z'-(int)'A'+1)));
         out.printf("[%05d] Text: \"%s\"\n", i, text);
         mstr.setText(text);
      }
   }
}

