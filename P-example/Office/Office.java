package Office;

import static java.lang.System.*;
import pt.ua.gboard.*;
import pt.ua.gboard.basic.*;
import pt.ua.gboard.games.*;
import pt.ua.concurrent.*;

public class Office
{
   public Office(String pathToMap)
   {
      assert Labyrinth.validMapFile(pathToMap): "ERROR: invalid map file \""+pathToMap+"\"";

      floorPlan = new FloorPlan(pathToMap);
      out.println(floorPlan);
      hallwayArea = Factory.newHallwayArea();
      hallwayArea.addToFloor(floorPlan);
      out.println(floorPlan);
      eatingArea = Factory.newEatingArea();
      eatingArea.addToFloor(floorPlan);
      out.println(floorPlan);
      workArea = Factory.newWorkArea();
      workArea.addToFloor(floorPlan);
      out.println(floorPlan);
      vendingMachineArea = Factory.newVendingMachineArea();
      vendingMachineArea.addToFloor(floorPlan);
      out.println(floorPlan);

      LabyrinthGelem.setShowRoadBoundaries();
      Labyrinth.setNumberOfLayers(3);
      Labyrinth.setWindowName("Office");
      officeMap = new Labyrinth(floorPlan.exportMap(), floorPlan.roadSymbols(), Global.N, true);
      officeMap.attachGelemToRoadSymbol(floorPlan.northChar, new ImageGelem("image/sign-up-icon.png", officeMap.board, 90, Global.N, Global.N));
      officeMap.attachGelemToRoadSymbol(floorPlan.eastChar, new ImageGelem("image/sign-right-icon.png", officeMap.board, 90, Global.N, Global.N));
      officeMap.attachGelemToRoadSymbol(floorPlan.westChar, new ImageGelem("image/sign-left-icon.png", officeMap.board, 90, Global.N, Global.N));
      officeMap.attachGelemToRoadSymbol(floorPlan.southChar, new ImageGelem("image/sign-down-icon.png", officeMap.board, 90, Global.N, Global.N));
      officeMap.attachGelemToRoadSymbol(Global.CHAIR, new ImageGelem("image/chair.png", officeMap.board, 90, Global.N, Global.N));
      vendingMachineArea.init(officeMap);
   }

   public FloorPlan floorPlan()
   {
      return floorPlan;
   }

   public Labyrinth officeMap()
   {
      return officeMap;
   }

   public IRoomPlan hallwayArea()
   {
      return hallwayArea;
   }

   public IChairArea eatingArea()
   {
      return eatingArea;
   }

   public IChairArea workArea()
   {
      return workArea;
   }

   public IVendingMachineArea vendingMachineArea()
   {
      return vendingMachineArea;
   }

   protected final FloorPlan floorPlan;
   protected final Labyrinth officeMap;
   protected final IRoomPlan hallwayArea;
   protected final IChairArea eatingArea;
   protected final IChairArea workArea;
   protected final IVendingMachineArea vendingMachineArea;
}

