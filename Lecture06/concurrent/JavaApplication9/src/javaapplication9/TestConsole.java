package concurrent;

import static pt.ua.concurrent.Console.*;

public class TestConsole
{
   public static void main(String[] args)
   {
      for(int c = 0; c < colors.length; c++)
         println(colors[c], "["+c+"] Colorful text!");
   }
}

