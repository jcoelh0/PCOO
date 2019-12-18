package gboard;

import static java.lang.System.*;
import java.awt.*;
import pt.ua.gboard.*;
import pt.ua.gboard.basic.*;

public class Mines
{
   static public void main(String[] args)
   {
      int numLines=10;
      int numColumns=10;
      int numMines=10;
      if (args.length != 3)
      {
         err.println("Usage: Mines <numLines> <numColumns> <numMines>");
         out.println();
         out.println("Using: numLines="+numLines+", numColumns="+numColumns+", numMines="+numMines+" as default");
      }
      else
      {
         numLines = Integer.decode(args[0]);
         numColumns = Integer.decode(args[1]);
         numMines = Integer.decode(args[2]);
      }

      if (!MinesGame.valid(numLines, numColumns, numMines))
      {
         err.println("ERROR: invalid value(s)!");
         exit(2);
      }
      new MinesGame(numLines, numColumns, numMines);
   }
}

class MinesGame extends GBoardInputHandler
{
   public static boolean valid(int numLines, int numColumns, int numMines)
   {
      return numLines > 0 && numColumns > 0 && numMines >= 0 && numMines <= numLines*numColumns;
   }

   public MinesGame(int numLines, int numColumns, int numMines)
   {
      super(mousePressedMask);

      assert valid(numLines, numColumns, numMines);

      this.numLines = numLines;
      this.numColumns = numColumns;
      this.numMines = numMines;
      contents = new int[numLines][numColumns];
      for(int l = 0; l < numLines; l++)
         for(int c = 0; c < numColumns; c++)
            contents[l][c] = 0;

      for(int m = 0; m < numMines; m++)
      {
         int l, c;
         do
         {
            l = (int)(Math.random()*(double)numLines);
            c = (int)(Math.random()*(double)numColumns);
            assert l >= 0 && l < numLines && c >= 0 && c < numColumns;
         }
         while(contents[l][c] == -1); // mine
         contents[l][c] = -1; // mine
         for(int dl = -1;dl <= 1; dl++)
            for(int dc = -1;dc <= 1; dc++)
               if ((dl != 0 || dc != 0) &&
                   l+dl >= 0 && l+dl < numLines &&
                   c+dc >= 0 && c+dc < numColumns &&
                   contents[l+dl][c+dc] != -1)
                  contents[l+dl][c+dc]++;
      }

      board = new GBoard("Mines", numLines, numColumns, 60, 60, 2);

      hidden = new FilledGelem(Color.yellow, 90);
      mine = new CharGelem('*', Color.red);
      digits = new Gelem[9]; // 0-8
      for(int i = 0; i < digits.length; i++)
         digits[i] = new CharGelem((char)((int)'0'+i), Color.green);

      for(int l = 0; l < numLines; l++)
         for(int c = 0; c < numColumns; c++)
            board.draw(hidden, l, c, 0);

      board.pushInputHandler(this);
   }

   public int get(int line, int column)
   {
      assert line >= 0 && line < numLines;
      assert column >= 0 && column < numColumns;

      return contents[line][column];
   }

   public void run(GBoard board, int line, int column, int layer, int type, int code, Gelem gelem)
   {
      if (gelem == hidden)
      {
         int value = get(line, column);
         board.draw(value == -1 ? mine : digits[value], line, column, layer);
      }
   }

   protected int numLines;
   protected int numColumns;
   protected int numMines;
   protected int[][] contents;
   protected GBoard board;
   protected Gelem hidden;
   protected Gelem mine;
   protected Gelem[] digits;
}

