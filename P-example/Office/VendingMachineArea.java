package Office;

import static java.lang.System.*;
import pt.ua.gboard.*;
import pt.ua.gboard.basic.*;
import pt.ua.gboard.games.*;
import pt.ua.concurrent.*;

/*
 * The only mutable part is related with RoomResource object buyPos
 */
public class VendingMachineArea extends RoomPlan implements IVendingMachineArea
{
   public VendingMachineArea()
   {
      super("VendingMachine Area", '4', Global.ROOM_INP, Global.ROOM_OUT);
   }

   @Override
   public void addToFloor(FloorPlan floorPlan)
   {
      super.addToFloor(floorPlan);

      if (tmp.length == 0)
      {
         err.println("ERROR: missing vending machine purchase locations in map file");
         System.exit(1);
      }
      buyPos = new RoomResource(tmp, Global.VEND_CAPACITY);
      buyPos.setAll(Global.VEND_CAPACITY);
      floorPlan.registerRoadSymbol(Global.VEND_BUY);
   }

   @Override
   public boolean isRoad(char symbol)
   {
      return super.isRoad(symbol) || symbol == Global.VEND_BUY;
   }

   @Override
   public void registerResourcePosition(char symbol, int line, int column)
   {
      assert symbol == Global.VEND_BUY; 

      if (!positionExists(line, column))
      {
         Position[] t = new Position[tmp.length+1];
         arraycopy(tmp, 0, t, 0, tmp.length);
         t[tmp.length] = new Position(line, column);
         tmp = t;
      }
   }


   public boolean emptyVendingMachine(Position pos)
   {
      assert buyPos.exists(pos);

      return buyPos.usage(pos) == 0;
   }

   public void refill(Position pos)
   {
      assert buyPos.exists(pos);
      assert emptyVendingMachine(pos);

      buyPos.set(pos, Global.VEND_CAPACITY);
   }

   public void buy(Position pos)
   {
      assert buyPos.exists(pos);
      assert !emptyVendingMachine(pos);

      buyPos.decrement(pos);
   }

   /*
    * Are all vending machines empty?
    */
   public boolean allEmpty()
   {
      return !buyPos.existsPositionWithout(0);
   }

   /*
    * A randomly choosen non empty vending machine buy position
    */
   public Position selectMachine()
   {
      assert !allEmpty();

      Position result = buyPos.randomPositionWithout(0);

      return result;
   }

   public int numMachines()
   {
      return buyPos.size();
   }

   public Position getMachine(int idx)
   {
      assert idx >= 0 && idx < numMachines();

      return buyPos.get(idx);
   }

   /* No need to understand the following code */

   public void init(Labyrinth officeMap)
   {
      Position[] vendingPos = officeMap.symbolPositions(Global.VEND_SYMBOL);
      /*
      for(int i = 0; i < vendingPos.length; i++)
         out.println("p["+i+"]=("+vendingPos[i].line()+","+vendingPos[i].column()+")");
      */
      if (vendingPos.length == 0)
      {
         err.println("ERROR: missing vending machine in map file");
         System.exit(1);
      }
      Gelem vendingMachineGelem = new ImageGelem("image/vending-machine.png", officeMap.board, 90, 2*Global.N, 2*Global.N);
      for(int i = 0; i < vendingPos.length; i++)
         officeMap.board.draw(vendingMachineGelem, Global.N*vendingPos[i].line(), Global.N*vendingPos[i].column(), 1);
   }

   protected boolean positionExists(int line, int column)
   {
      boolean result = false;
      for (int i = 0; !result && i < tmp.length; i++)
         result = (line == tmp[i].line() && column == tmp[i].column());
      return result;
   }

   protected RoomResource buyPos;
   protected Position[] tmp = new Position[0];
}

