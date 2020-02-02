package Office;

import pt.ua.gboard.*;
import pt.ua.gboard.basic.*;
import pt.ua.gboard.games.*;
import pt.ua.concurrent.*;

/*
 * The only mutable part is related with RoomResource object buyPos
 */
public interface IVendingMachineArea extends IRoomPlan
{
   public boolean emptyVendingMachine(Position pos);
   public void refill(Position pos);
   public void buy(Position pos);
   public boolean allEmpty();
   public Position selectMachine();
   public int numMachines();
   public Position getMachine(int idx);
   public void init(Labyrinth officeMap);
}

