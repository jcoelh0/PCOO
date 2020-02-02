package Office;

import static java.lang.System.*;
import pt.ua.gboard.*;
import pt.ua.gboard.basic.*;
import pt.ua.gboard.games.*;
import pt.ua.concurrent.*;

/*
 * The only mutable part is related with RoomResource object chairs
 */
abstract public class ChairArea extends RoomPlan implements IChairArea
{
   public ChairArea(String description, char roomIDSymbol)
   {
      super(description, roomIDSymbol, Global.ROOM_INP, Global.ROOM_OUT);
   }

   @Override
   public void addToFloor(FloorPlan floorPlan)
   {
      super.addToFloor(floorPlan);

      floorPlan.registerRoadSymbol(Global.CHAIR);
      if (tmp.length == 0)
      {
         err.println("ERROR: missing chairs in map file");
         System.exit(1);
      }
      chairs = new RoomResource(tmp, 1);
   }

   @Override
   public boolean isRoad(char symbol)
   {
      //err.println("[ChairArea.isRoad]");
      return super.isRoad(symbol) || symbol == Global.CHAIR;
   }

   @Override
   public void registerResourcePosition(char symbol, int line, int column)
   {
      //err.println("[ChairArea.registerResourcePosition] line: "+line+", column: "+column);
      assert symbol == Global.CHAIR; 

      if (!positionExists(line, column))
      {
         Position[] t = new Position[tmp.length+1];
         arraycopy(tmp, 0, t, 0, tmp.length);
         t[tmp.length] = new Position(line, column);
         tmp = t;
      }
   }

   public boolean chairAvaliable()
   {
      return chairs.existsPositionWith(0);
   }

   public boolean chairInUse(Position pos)
   {
      assert chairs.exists(pos);

      return chairs.inUse(pos);
   }

   public Position reserveChair()
   {
      assert chairAvaliable();

      Position result = chairs.randomPositionWith(0);
      //out.println("[ChairArea] chair: "+result+", use: "+chairs.usage(result));
      chairs.increment(result);
      return result;
   }

   public void releaseChair(Position pos)
   {
      assert chairs.exists(pos);
      assert chairInUse(pos);

      chairs.reset(pos);
   }

   protected boolean positionExists(int line, int column)
   {
      boolean result = false;
      for (int i = 0; !result && i < tmp.length; i++)
         result = (line == tmp[i].line() && column == tmp[i].column());
      return result;
   }

   protected RoomResource chairs;
   protected Position[] tmp = new Position[0];
}

