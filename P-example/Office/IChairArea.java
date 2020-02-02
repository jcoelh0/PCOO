package Office;

import pt.ua.gboard.*;
import pt.ua.gboard.basic.*;
import pt.ua.gboard.games.*;
import pt.ua.concurrent.*;

/*
 * The only mutable part is related with RoomResource object chairs
 */
public interface IChairArea extends IRoomPlan
{
   public boolean chairAvaliable();
   public boolean chairInUse(Position pos);
   public Position reserveChair();
   public void releaseChair(Position pos);
}

