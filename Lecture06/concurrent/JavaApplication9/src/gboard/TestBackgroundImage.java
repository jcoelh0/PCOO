package gboard;

import static java.lang.System.*;
import java.util.Scanner;
import java.awt.*;
import pt.ua.gboard.*;
import pt.ua.gboard.basic.*;

public class TestBackgroundImage
{
   static final public Scanner in = new Scanner(System.in);

   protected static final GBoard board = new GBoard("Test", 8, 8, 60, 60, 2);

   static public void main(String[] args)
   {
      board.pushInputHandler(new TestInputHandler());

      if (ImageGelem.isImage("Guernica.jpg"))
      {
         Gelem image = new ImageGelem("Guernica.jpg", board, 100, board.numberOfLines(), board.numberOfColumns());
         board.draw(image, 0, 0, 0); // layer zero!
      }
      else
         out.println("WARNING: image file \"Guernica.jpg\" not found!");

      Gelem letters[] = new Gelem[26];
      for(int i = 0; i < letters.length; i++)
      {
         //out.println(""+(char)((int)'A'+i));
         letters[i] = new CharGelem((char)((int)'A'+i), Color.green);
      }
      Gelem bigLetter = new CharGelem('X', Color.blue, 3, 3);

      int i = 0;
      for(int l = 0; l < board.numberOfLines(); l++)
         for(int c = 0; c < board.numberOfColumns(); c++)
         {
            board.draw(letters[i], l, c, 1);
            i = (i+1) % letters.length;
         }

      Gelem stringGelem = new StringGelem("TEXT", Color.red, 2, 2);
      board.draw(stringGelem, 6, 6, 1);

      Gelem stringGelem2 = new StringGelem("TEXT", Color.green, 1, 1);
      board.draw(stringGelem2, 5, 7, 1);

      board.draw(bigLetter, 4, 4, 1);

      Gelem bigBlue = new FilledGelem(Color.blue, 90, 2, 2);
      board.draw(bigBlue, 4, 0, 1);

      out.print("<Press RETURN>");
      in.nextLine();
      board.move(letters[0], 0, 0, 1, 3, 3, 1);
      board.erase(letters[2], 0, 2, 1);
      /*
      for(int l = 0; l < 8; l++)
         for(int c = 0; c < 8; c++)
            if (((l+c) % 2) == 0)
               ;
            else
            {
               board.erase(x, l, c, 0);
            }
      */
   }
}
