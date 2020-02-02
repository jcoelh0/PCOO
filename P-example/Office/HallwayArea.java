package Office;

import static java.lang.System.*;
import pt.ua.gboard.*;
import pt.ua.gboard.basic.*;
import pt.ua.gboard.games.*;
import pt.ua.concurrent.*;

/*
 * Instances of this class are immutable after creation
 */
public class HallwayArea extends RoomPlan
{
   public HallwayArea()
   {
      super("Hallway Area", '1', Global.OFFICE_INP, Global.OFFICE_OUT);
   }
}

