package gboard.games;

import static java.lang.System.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import pt.ua.gboard.*;
import pt.ua.gboard.basic.*;
import pt.ua.gboard.games.*;

public class TestPacman
{
   static public void main(String[] args)
   {
      String map="map1.txt";
      if (args.length != 1)
      {
         out.println("Usage: TestPacman <map-file>");
         out.println();
         out.println("Using \""+map+"\" as default");
      }
      else
         map=args[0];

      new TestPacman(map);
   }

   public TestPacman(String map)
   {
      LabyrinthGelem.setShowRoadBoundaries();
      char[] roadSymbols = {'S', 'X'};
      N=4;
      labyrinth = new Labyrinth(map, roadSymbols, N);

      Position[] start = labyrinth.roadSymbolPositions('S');
      for(int i = 0; i < start.length; i++)
         out.println("Start: "+start[i]);
      Position[] finish = labyrinth.roadSymbolPositions('X');
      for(int i = 0; i < finish.length; i++)
         out.println("Finish: "+finish[i]);

      labyrinth.board.pushInputHandler(new PacmanInput());
      pacman = new PacmanGelem(Color.red, 90, N, N);
      boolean found = false;
      for(line = 0; !found && line < labyrinth.numberOfLines; line++)
         for(column = 0; !found && column < labyrinth.numberOfColumns; column++)
            found = labyrinth.isRoad(line, column);
      line = (line-1)*N;
      column = (column-1)*N;
      labyrinth.board.draw(pacman, line, column, 1);
   }

   protected int N;
   protected Labyrinth labyrinth;
   protected Gelem pacman;
   protected int line;
   protected int column;

   protected class PacmanInput extends GBoardInputHandler
   {
      public PacmanInput()
      {
         super(keyPressedMask);
      }

      protected int lastCode = -1;
      protected int remN = 0;

      public void move(int code)
      {
         boolean testWall = ((line % N) == 0) && ((column % N) == 0);
         int dLine = 0;
         int dColumn = 0;
         switch(code)
         {
            case KeyEvent.VK_DOWN:
               dLine = +1;
               break;
            case KeyEvent.VK_UP:
               dLine = -1;
               break;
            case KeyEvent.VK_RIGHT:
               dColumn = +1;
               break;
            case KeyEvent.VK_LEFT:
               dColumn = -1;
               break;
         }
         if ((dLine != 0 || dColumn != 0) &&
             (!testWall || labyrinth.validPosition(line/N+dLine, column/N+dColumn) &&
                           labyrinth.isRoad(line/N+dLine, column/N+dColumn)))
         {
            labyrinth.board.move(pacman, line, column, 1, line+dLine, column+dColumn, 1);
            line += dLine;
            column += dColumn;
            if (code == KeyEvent.VK_DOWN || code == KeyEvent.VK_RIGHT)
               remN = (remN+1)%N;
            else
               remN = (N+remN-1)%N;
            lastCode = code;
         }
      }

      protected boolean perpendicularChange(int code, int lastCode)
      {
         return ((code == KeyEvent.VK_DOWN || code == KeyEvent.VK_UP) &&
                 (lastCode == KeyEvent.VK_RIGHT || lastCode == KeyEvent.VK_LEFT)) ||
                ((code == KeyEvent.VK_RIGHT || code == KeyEvent.VK_LEFT) &&
                 (lastCode == KeyEvent.VK_DOWN || lastCode == KeyEvent.VK_UP));
      }

      public void run(GBoard board, int l, int c, int layer, int type, int code, Gelem gelem)
      {
         if (remN != 0 && perpendicularChange(code, lastCode))
            move(lastCode);
         else
            move(code);
      }
   }
}
