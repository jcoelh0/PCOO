package Office;

import static java.lang.System.*;
import pt.ua.gboard.*;
import pt.ua.gboard.basic.*;
import pt.ua.gboard.games.*;
import pt.ua.concurrent.*;

public class Employee
{
   public Employee(int id, Office office)
   {
      assert office != null;

      this.id = id;

      Position chair;
      Position vendingMachine;

      this.office = office;
      this.officeMap = office.officeMap();
      person = new ImageGelem("image/person.png", officeMap.board, 80, Global.N, Global.N);
      redefineSpeed();

      // show a possible employee agenda:
 
      out.println("Employee #"+id+" started its day work!");
 
      // employee day's work beginning:
      pos = office.hallwayArea().randomEntryPosition();
      draw(pos);

      // to work area and do some work (seated!):
      go(office.hallwayArea(), office.workArea().randomEntryPosition());
      chair = office.workArea().reserveChair();
      go(office.workArea(), chair);
      Utils.randomPause();
      office.workArea().releaseChair(chair);

      // leave work area and buy something to eat:
      go(office.workArea(), office.workArea().randomExitPosition());
      go(office.hallwayArea(), office.vendingMachineArea().randomEntryPosition());
      vendingMachine = office.vendingMachineArea().selectMachine();
      go(office.vendingMachineArea(), vendingMachine);
      Utils.randomPause();
      // office.vendingMachineArea().buy(vendingMachine); // commented for unlimited capacity simulation!

      // leave vending area and go eat (seated!) at eating area:
      go(office.vendingMachineArea(), office.vendingMachineArea().randomExitPosition());
      go(office.hallwayArea(), office.eatingArea().randomEntryPosition());
      chair = office.eatingArea().reserveChair();
      go(office.eatingArea(), chair);
      Utils.randomPause();
      office.eatingArea().releaseChair(chair);

      // leave eating area and leave office:
      go(office.eatingArea(), office.eatingArea().randomExitPosition());
      go(office.hallwayArea(), office.hallwayArea().randomExitPosition());

      // that's it for this employee:
      erase(pos);
      out.println("Employee #"+id+" done for today!");
   }

   protected void go(IRoomPlan room, Position dest)
   {
      assert dest != null;

      Position[] path;
      if (room == null)
         path = office.floorPlan.randomShortestPath(pos, dest);
      else
         path = office.floorPlan.randomShortestPath(room, pos, dest);
      /*
      out.print("[go] Path:");
      for(int p = 0; p < path.length; p++)
         out.print(" "+path[p]);
      out.println();
      */
      out.println("Employee #"+id+" from "+pos+" to "+dest);
      for(int p = 0; p < path.length; p++)
      {
         int l = path[p].line()-pos.line();
         int c = path[p].column()-pos.column();
         for(int i = 0; i < Global.N; i++)
         {
            sync();
            officeMap.board.move(person, Global.N*pos.line()+i*l, Global.N*pos.column()+i*c, 2, Global.N*pos.line()+(i+1)*l, Global.N*pos.column()+(i+1)*c, 2);
         }
         pos = path[p];
      }
   }

   protected void redefineSpeed()
   {
      speed = Utils.randomInteger(Global.MIN_SPEED, Global.MAX_SPEED);
   }

   protected void sync()
   {
      for(int i = 0; i < speed; i++)
         Global.metronome.sync();
   }

   protected final int id;
   protected final Office office;
   protected final Labyrinth officeMap;
   protected final Gelem person;
   protected Position pos;
   protected int speed = 1;


   /* No need to understand the following code */

   protected void draw(Position pos)
   {
      assert pos != null;

      officeMap.board.draw(person, Global.N*pos.line(), Global.N*pos.column(), 2);
   }

   protected void erase(Position pos)
   {
      assert pos != null;

      officeMap.board.erase(person, Global.N*pos.line(), Global.N*pos.column(), 2);
   }
}

