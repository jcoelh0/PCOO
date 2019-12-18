package gboard;

import pt.ua.gboard.*;

public class TestInputHandler extends GBoardInputHandler
{
   public TestInputHandler()
   {
      super(mousePressedMask | keyPressedMask);
   }

   public void run(GBoard board, int line, int column, int layer, int type, int code, Gelem gelem)
   {
      System.out.println("TestInputHandler: line "+line+", column="+column+", layer="+layer+", type="+type+", code="+code+", gelem="+gelem);
   }
}
