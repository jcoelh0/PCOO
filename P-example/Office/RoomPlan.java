package Office;

import static java.lang.System.*;
import pt.ua.gboard.*;
import pt.ua.gboard.basic.*;
import pt.ua.gboard.games.*;
import pt.ua.concurrent.*;

/*
 * Instances of this class are immutable after being registered in FloorPlan (registerRoom)
 */
public class RoomPlan implements IRoomPlan
{
   public RoomPlan(String description, char roomIDSymbol, char entrySymbol, char exitSymbol)
   {
      assert description != null;
      assert roomIDSymbol != ' ' && roomIDSymbol != '\u0000';
      assert entrySymbol != ' ' && entrySymbol != '\u0000';
      assert exitSymbol != ' ' && exitSymbol != '\u0000';
      assert exitSymbol != roomIDSymbol;
      assert exitSymbol != entrySymbol;

      registeredToFloor = false;
      this.description = description;
      this.IDSymbol = roomIDSymbol;
      this.entrySymbol = entrySymbol;
      this.exitSymbol = exitSymbol;
   }

   public boolean registeredToFloor()
   {
      return registeredToFloor;
   }

   public void addToFloor(FloorPlan floorPlan)
   {
      assert floorPlan != null;
      assert !registeredToFloor();

      floorPlan.registerRoom(this);
      floorPlan.registerRoadSymbol(IDSymbol);
      floorPlan.registerRoadSymbol(entrySymbol);
      floorPlan.registerRoadSymbol(exitSymbol);
      registeredToFloor = true;
   }

   public String description()
   {
      return description;
   }

   public char IDSymbol()
   {
      return IDSymbol;
   }

   public char entrySymbol()
   {
      return entrySymbol;
   }

   public char exitSymbol()
   {
      return exitSymbol;
   }

   public boolean isRoad(char symbol)
   {
      return symbol == ' ' || symbol == IDSymbol || symbol == entrySymbol || symbol == exitSymbol;
   }

   public void registerResourcePosition(char symbol, int line, int column)
   {
      /* do nothing by default */
   }

   public void setBoundingBox(int minLin, int maxLin, int minCol, int maxCol)
   {
      assert minLin >= 0 && maxLin >= minLin;
      assert minCol >= 0 && maxCol >= minCol;

      this.minLin = minLin;
      this.maxLin = maxLin;
      this.minCol = minCol;
      this.maxCol = maxCol;
      numLins = maxLin-minLin+1;
      numCols = maxCol-minCol+1;
   }

   public int minLin()
   {
      return minLin;
   }

   public int maxLin()
   {
      return maxLin;
   }

   public int minCol()
   {
      return minCol;
   }

   public int maxCol()
   {
      return maxCol;
   }

   public int numLins()
   {
      return numLins;
   }

   public int numCols()
   {
      return numCols;
   }

   public boolean inside(Position pos)
   {
      assert pos != null;

      return inside(pos.line(), pos.column());
   }

   public boolean inside(int line, int column)
   {
      return line >= minLin && line <= maxLin &&
             column >= minCol && column <= maxCol;
   }

   public void setEntryPositions(Position[] pos)
   {
      assert pos != null;
      assert pos.length > 0;

      this.entryPositions = pos;
   }

   public void setExitPositions(Position[] pos)
   {
      assert pos != null;
      assert pos.length > 0;

      this.exitPositions = pos;
   }

   /*
    * A randomly choosen entry location
    */
   public Position randomEntryPosition()
   {
      assert registeredToFloor();

      return entryPositions[Utils.randomInteger(entryPositions.length)];
   }

   /*
    * A randomly choosen exit location
    */
   public Position randomExitPosition()
   {
      assert registeredToFloor();

      return exitPositions[Utils.randomInteger(exitPositions.length)];
   }

   protected boolean registeredToFloor;
   protected final String description;
   protected final char IDSymbol;
   protected final char entrySymbol;
   protected final char exitSymbol;
   protected Position[] entryPositions;
   protected Position[] exitPositions;
   protected int minLin, maxLin;
   protected int minCol, maxCol;
   protected int numLins, numCols;
}

