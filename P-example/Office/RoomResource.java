package Office;

import static java.lang.System.*;
import pt.ua.gboard.*;
import pt.ua.gboard.basic.*;
import pt.ua.gboard.games.*;
import pt.ua.concurrent.*;

public class RoomResource
{
   public RoomResource(Position[] positions)
   {
      assert positions != null;
      assert positions.length > 0;

      this.positions = new Position[positions.length]; // alias problems prevention!
      arraycopy(positions, 0, this.positions, 0, positions.length);
      usage = new int[positions.length];
      this.limit = 0;
   }

   public RoomResource(Position[] positions, int limit)
   {
      assert positions != null;
      assert positions.length > 0;
      assert limit > 0;

      this.positions = new Position[positions.length]; // alias problems prevention!
      arraycopy(positions, 0, this.positions, 0, positions.length);
      usage = new int[positions.length];
      this.limit = limit;
   }

   public boolean unlimited()
   {
      return limit == 0;
   }

   public int limit()
   {
      assert !unlimited();

      return limit;
   }

   public int size()
   {
      return positions.length;
   }

   public Position get(int idx)
   {
      assert idx >= 0 && idx < size();

      return positions[idx];
   }

   public boolean exists(Position pos)
   {
      return search(pos) < positions.length;
   }

   public boolean inUse(Position pos)
   {
      assert pos != null;
      assert exists(pos);

      return usage[search(pos)] > 0;
   }

   public int usage(Position pos)
   {
      assert pos != null;
      assert exists(pos);

      return usage[search(pos)];
   }

   public void set(Position pos, int val)
   {
      assert pos != null;
      assert exists(pos);
      assert val >= 0 && (unlimited() || val <= limit());

      usage[search(pos)] = val;
   }

   public void reset(Position pos)
   {
      assert pos != null;
      assert exists(pos);

      usage[search(pos)] = 0;
   }

   public void setAll(int val)
   {
      assert val >= 0 && (unlimited() || val <= limit()): "invalid val ("+val+") value: ("+val+" >= 0 && ("+unlimited()+" || "+val+" <= "+limit()+")";

      for(int i = 0; i < usage.length; i++)
         usage[i] = val;
   }

   public void resetAll()
   {
      for(int i = 0; i < usage.length; i++)
         usage[i] = 0;
   }

   public void increment(Position pos)
   {
      assert pos != null;
      assert exists(pos);
      assert unlimited() || usage(pos) < limit();

      usage[search(pos)]++;
   }

   public void decrement(Position pos)
   {
      assert pos != null;
      assert exists(pos);
      assert inUse(pos);

      usage[search(pos)]--;
   }

   public boolean existsPositionWith(int val)
   {
      assert val >= 0 && (unlimited() || val <= limit());

      boolean result = false;
      for(int i = 0; !result && i < usage.length; i++)
         result = (usage[i] == val);
      return result;
   }

   public boolean existsPositionWithout(int val)
   {
      assert val >= 0 && (unlimited() || val <= limit());

      boolean result = false;
      for(int i = 0; !result && i < usage.length; i++)
         result = (usage[i] != val);
      return result;
   }

   public Position randomPosition()
   {
      return positions[Utils.randomInteger(positions.length)];
   }

   public Position randomPositionWith(int val)
   {
      assert val >= 0 && (unlimited() || val <= limit());
      assert existsPositionWith(val);

      int idx;
      do
      {
         idx = Utils.randomInteger(usage.length);
      }
      while(usage[idx] != val);

      return positions[idx];
   }

   public Position randomPositionWithout(int val)
   {
      assert val >= 0 && (unlimited() || val <= limit());
      assert existsPositionWithout(val);

      int idx;
      do
      {
         idx = Utils.randomInteger(usage.length);
      }
      while(usage[idx] == val);

      return positions[idx];
   }

   @Override
   public String toString()
   {
      String result = "";
      for(int i = 0; i < positions.length; i++)
      {
         if (i > 0)
            result += " ";
         result += ""+i+": "+positions[i]+","+usage[i];
      }
      return result;
   }

   protected int search(Position pos)
   {
      int result;
      for(result = 0; result < positions.length && !pos.isEqual(positions[result]); result++)
         ;
      return result;
   }

   protected final int limit;
   protected final Position[] positions;
   protected final int[] usage;
}

