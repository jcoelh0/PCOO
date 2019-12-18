package figures;

import static java.lang.System.*;
import java.awt.*;
import javax.swing.*;
import java.util.List;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentLinkedQueue;

public class FigureBoard extends JComponent
{
   public static FigureBoard init(String name, int boardWidth, int boardHeight)
   {
      assert name != null;
      assert boardWidth > 0;
      assert boardHeight > 0;

      board = new FigureBoard(boardWidth, boardHeight);
      FigureBoard.name = name;
      try
      {
         javax.swing.SwingUtilities.invokeAndWait(new Runnable()
         {
            public void run()
            {
               JFrame.setDefaultLookAndFeelDecorated(true);
               JFrame frame = new JFrame(FigureBoard.name);
               frame.setBackground(Color.white);
               frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
               Container pane = frame.getContentPane();
               pane.add(board);
               frame.pack();
               frame.setVisible(true);
            }
         });
      }
      catch(Exception e)
      {
         err.println("ERROR: unable to launch figure board!");
         exit(1);
      }

      return board;
   }

   protected FigureBoard(int width,int height)
   {
      this.width = width;
      this.height = height;
      setDoubleBuffered(true);
      setOpaque(true);
      setBackground(Color.white);
      figures = new LinkedList<Figure>();
   }

   public synchronized boolean isInside(int x, int y)
   {
      return x >= 0 && x < width && y >= 0 && y < height;
   }

   public void draw(Figure f)
   {
      assert f != null;

      Runnable runDraw;

      synchronized(this)
      {
         final Figure fig = f;

         runDraw = new Runnable()
         {
            public void run()
            {
               figures.add(fig);
               fig.draw(getGraphics());
            }
         };

         if(javax.swing.SwingUtilities.isEventDispatchThread())
         {
            runDraw.run();
         }
      }
      if (!javax.swing.SwingUtilities.isEventDispatchThread())
      {
         try
         {
            javax.swing.SwingUtilities.invokeAndWait(runDraw);
         }
         catch(Exception e)
         {
            err.println("ERROR: unable to draw figure!");
            exit(1);
         }
      }
   }

   public void erase(Figure f)
   {
      assert f != null;

      Runnable runErase;

      synchronized(this)
      {
         final Figure fig = f;

         runErase = new Runnable()
         {
            public void run()
            {
               figures.remove(fig);
               Graphics g = getGraphics();
               g.setColor(getBackground());
               Rectangle bb = fig.boundingBox();
               g.fillRect(bb.x, bb.y, bb.width, bb.height);
               g.setColor(getForeground());
               for(int i = 0; i < figures.size(); i++)
               {
                  Figure f2 = figures.get(i);
                  if (intersect(fig, f2))
                     f2.draw(getGraphics());
               }
            }
         };

         if(javax.swing.SwingUtilities.isEventDispatchThread())
         {
            runErase.run();
         }
      }
      if(!javax.swing.SwingUtilities.isEventDispatchThread())
      {
         try
         {
            javax.swing.SwingUtilities.invokeAndWait(runErase);
         }
         catch(Exception e)
         {
            err.println("ERROR: unable to erase figure!");
            exit(1);
         }
      }
   }

   protected boolean intersect(Figure f1, Figure f2)
   {
      int f1x = f1.boundingBox.x;
      int f1y = f1.boundingBox.y;
      int f1w = f1.boundingBox.width;
      int f1h = f1.boundingBox.height;
      int f2x = f2.boundingBox.x;
      int f2y = f2.boundingBox.y;
      int f2w = f2.boundingBox.width;
      int f2h = f2.boundingBox.height;
      boolean result =  (f1x <= f2x && f1x+f1w-1 >= f2x ||
            f2x < f1x && f2x+f2w-1 >= f1x) &&
         (f1y <= f2y && f1y+f1h-1 >= f2y ||
          f2y < f1y && f2y+f2h-1 >= f1y);
      //out.print("f1 = ("+f1x+","+f1y+","+f1w+","+f1h+")");
      //out.print(" - f2 = ("+f2x+","+f2y+","+f2w+","+f2h+")");
      //out.println(" = "+result);
      return result;
   }

   protected synchronized void paintComponent(Graphics g)
   {
      super.paintComponent(g);
      for(int i = 0; i < figures.size(); i++)
         figures.get(i).draw(g);
   }

   public synchronized Dimension getPreferredSize()
   {
      return new Dimension(width,height);
   }

   protected static FigureBoard board;
   protected static String name;
   protected List<Figure> figures;
   protected int width,height;
}
