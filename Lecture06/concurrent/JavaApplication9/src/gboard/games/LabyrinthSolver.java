package gboard.games;

import static java.lang.System.*;
import java.awt.Color;
import pt.ua.gboard.*;
import pt.ua.gboard.basic.*;
import pt.ua.gboard.games.*;

public class LabyrinthSolver
{
   static Labyrinth maze = null;
   public static final int pause = 100; // waiting time in each step [ms]
   static final char startSymbol = 'S';
   static final char markedStartSymbol = 's';
   static final char endSymbol = 'X';
   static final char markedPositionSymbol = '.';
   static final char actualPositionSymbol = 'o';

   public static void main(String[] args)
   {
      String map="map1.txt";
      if (args.length != 1)
      {
         err.println("Usage: java -ea LabyrinthSolver <text-map>");
         out.println();
         out.println("Using \""+map+"\" as default");
      }
      else
         map=args[0];

      LabyrinthGelem.setShowRoadBoundaries();

      char[] extraSymbols =
      {
         startSymbol,
         markedStartSymbol,
         endSymbol,
         markedPositionSymbol,
         actualPositionSymbol
      };
      maze = new Labyrinth(map, extraSymbols, 1, true);
      //maze = new Labyrinth(map, extraSymbols);
      Gelem[] gelems = {
         new StringGelem(""+startSymbol, Color.red),
         new StringGelem(""+markedStartSymbol, Color.red),
         new StringGelem(""+endSymbol, Color.red),
         new CircleGelem(Color.green, 20),
         new CircleGelem(Color.green, 60)
      };
      for(int i = 0; i < extraSymbols.length; i++)
         maze.attachGelemToRoadSymbol(extraSymbols[i], gelems[i]);

      Position[] startPositions = maze.roadSymbolPositions(startSymbol);
      if (startPositions.length != 1)
      {
         err.println("ERROR: one, and only one, start point required!");
         exit(2);
      }
      Position[] endPositions = maze.roadSymbolPositions(endSymbol);
      if (endPositions.length != 1)
      {
         err.println("ERROR: one, and only one, end point required!");
         exit(3);
      }

      if (!searchPath(0, startPositions[0].line(), startPositions[0].column()))
         out.println("No solution!");
   }

   /**
    * Backtracking path search algorithm
    */
   public static boolean searchPath (int distance, int lin, int col)
   {
      boolean result = false;

      if (maze.validPosition(lin, col) && maze.isRoad(lin, col))
      {
         if (maze.roadSymbol(lin, col) == endSymbol)
         {
            out.println("Destination found at " + distance + " steps from start position.");
            out.println();
            result = true;
         }
         else if (freePosition(lin, col))
         { 
            markPosition(lin, col);
            if (searchPath(distance+1, lin-1, col))       // North
               result = true;
            else if (searchPath(distance+1, lin, col+1))  // East
               result = true;
            else if (searchPath(distance+1, lin, col-1))  // West
               result = true;
            else if (searchPath(distance+1, lin+1, col))  // South
               result = true;
            else {
               unmarkPosition(lin, col);
               //clearPosition(lin, col);
            }
         }
      }
      return result;
   }

   static boolean isStartPosition(int lin, int col)
   {
      assert maze.isRoad(lin, col);

      return maze.roadSymbol(lin, col) == startSymbol ||
             maze.roadSymbol(lin, col) == markedStartSymbol;
   }

   static boolean freePosition(int lin, int col)
   {
      assert maze.isRoad(lin, col);

      return maze.roadSymbol(lin, col) == ' ' ||
             maze.roadSymbol(lin, col) == startSymbol;
   }

   static void markPosition(int lin, int col)
   {
      assert maze.isRoad(lin, col);

      if (isStartPosition(lin, col))
         maze.putRoadSymbol(lin, col, markedStartSymbol);
      else
         maze.putRoadSymbol(lin, col, actualPositionSymbol);
      GBoard.sleep(pause);
   }

   static void clearPosition(int lin, int col)
   {
      assert maze.isRoad(lin, col);

      if (isStartPosition(lin, col))
         maze.putRoadSymbol(lin, col, startSymbol);
      else
         maze.putRoadSymbol(lin, col, ' ');
      GBoard.sleep(pause);
   }

   static void unmarkPosition(int lin, int col)
   {
      assert maze.isRoad(lin, col);

      if (!isStartPosition(lin, col))
         maze.putRoadSymbol(lin, col, markedPositionSymbol);
      GBoard.sleep(pause);
   }
}

