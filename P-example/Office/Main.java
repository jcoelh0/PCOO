package Office;

import static java.lang.System.*;
import java.awt.Point;
import pt.ua.gboard.*;
import pt.ua.gboard.games.*;
import pt.ua.concurrent.*;

public class Main
{
   public static void main(String[] args)
   {
      String map=null;
      int period = Global.DEFAULT_PERIOD;
      for(int i = 0; i < args.length; i++)
      {
         switch(args[i])
         {
            case "-p":
               if (i+1 == args.length)
                  error();
               period = stringToInt(args[i+1]);
               i++;
               break;
            default:
               if (map != null)
                  error();
               map = args[i];
               break;
         }
      }
      if (map == null)
         map=Global.MAP;

      Global.metronome = new Metronome(period);

      if (!Labyrinth.validMapFile(map))
      {
         err.println("ERROR: invalid map file \""+map+"\"");
         exit(1);
      }
      Office office = new Office(map);

      int n = Utils.randomInteger(Global.MIN_NUM_EMPLOYEES, Global.MAX_NUM_EMPLOYEES);
      out.println("# Employees: "+n);
      // dummy code (just as an example)
      for(int e = 1; e <= n; e++)
      {
         Employee w = new Employee(e, office);
      }

      exit(0);
   }

   private static void error()
   {
      err.println("ERROR: invalid arguments. Usage Main [<mapfile>] [-p simulation-period]");
      exit(1);
   }

   private static int stringToInt(String text)
   {
      int res = 0;
      try
      {
         res = Integer.parseInt(text);
         if (res <= 0)
            throw new NumberFormatException();
      }
      catch(NumberFormatException e)
      {
         err.println("ERROR: invalid simulation period ("+text+")");
         exit(2);
      }
      return res;
   }
}

