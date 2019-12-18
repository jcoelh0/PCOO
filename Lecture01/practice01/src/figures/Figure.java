package figures;

import static java.lang.System.*;
import java.awt.Graphics;
import java.awt.Rectangle;

abstract public class Figure
{
   public Figure(int x, int y, int width, int height)
   {
      assert width >= 0 && height >= 0;

      boundingBox = new Rectangle(x, y, width, height);
   }

   public int x()
   {
      return boundingBox.x;
   }

   public int y()
   {
      return boundingBox.y;
   }

   public int width()
   {
      return boundingBox.width;
   }

   public int height()
   {
      return boundingBox.height;
   }

   public void setPosition(int x, int y)
   {
      boundingBox.setLocation(x, y);
   }

   public Rectangle boundingBox()
   {
      assert boundingBox != null; // invariant!

      return boundingBox;
   }

   public String name()
   {
      return getClass().getSimpleName();
   }

   public void draw(Graphics g)
   {
      assert g != null;

      out.println("Drawing "+name()+" at position ("+x()+","+y()+")");
      internalDraw(g);
   }

   protected abstract void internalDraw(Graphics g);

   public String toString()
   {
      return name() + " at position ["+x()+", "+y()+"]";
   }

   protected Rectangle boundingBox;
}

