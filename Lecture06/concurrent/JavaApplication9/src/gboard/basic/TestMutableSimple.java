package gboard.basic;

import static java.lang.System.*;

import java.awt.*;
import pt.ua.gboard.*;
import pt.ua.gboard.basic.*;

public class TestMutableSimple
{
   public static void main(String[] args)
   {
      GBoard board = new GBoard("Test Mutable Simple", 8, 8, 60, 60, 2);
      String text = "A";
      MutableStringGelem mstr = new MutableStringGelem(text, Color.green);
      board.draw(mstr, 1, 1, 0);
      //board.draw(mstr, 3, 3, 0);
      for(int i = 1; i <= 4; i++)
      {
         board.sleep(1000);
         text = ""+(char)((int)'A' + (i%((int)'Z'-(int)'A'+1)));
         out.printf("[%05d] Text: \"%s\"\n", i, text);
         mstr.setText(text);
      }
   }
}

