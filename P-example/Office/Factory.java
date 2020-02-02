package Office;

import pt.ua.gboard.*;
import pt.ua.gboard.basic.*;
import pt.ua.gboard.games.*;
import pt.ua.concurrent.*;

public class Factory
{
   public static IRoomPlan newHallwayArea()
   {
      IRoomPlan result = new HallwayArea();
      return result;
   }

   public static IChairArea newEatingArea()
   {
      IChairArea result = new EatingArea();
      return result;
   }

   public static IChairArea newWorkArea()
   {
      IChairArea result = new WorkArea();
      return result;
   }

   public static IVendingMachineArea newVendingMachineArea()
   {
      IVendingMachineArea result = new VendingMachineArea();
      return result;
   }

}

