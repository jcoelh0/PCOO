package concurrent;

import static java.lang.System.*;

import pt.ua.concurrent.*;

public class TestDynamicBarrier
{
   public static void main(String[] args)
   {
      String[] defaultArgs = {"10"};
      if (args.length > 1)
      {
         err.println("Usage: TestDynamicBarrier [<num-threads>]");
         exit(1);
      }
      else if (args.length == 0)
      {
         args=defaultArgs;
         out.print("Using \"TestDynamicBarrier");
         for(int i = 0; i < args.length; i++)
            out.print(" "+args[i]);
         out.println("\" as default");
         out.println("");
      }

      int numThreads = 0;
      try
      {
         numThreads = Integer.parseInt(args[0]);
      }
      catch(NumberFormatException e)
      {
         err.println("ERROR: invalid number of thread \""+args[0]+"\"");
         exit(2);
      }
      if (numThreads < 0)
      {
         err.println("ERROR: invalid number of thread \""+args[0]+"\"");
         exit(2);
      }
      DynamicBarrier barrier = new DynamicBarrier();
      barrier.signIn();
      for(int i = 0; i < numThreads; i++)
         new AThread(barrier).start();
      out.println(""+CThread.currentThread()+" waiting on barrier #1...");
      barrier.await();
      out.println(""+CThread.currentThread()+" released from barrier #1...");
      out.println(""+CThread.currentThread()+" waiting on barrier #2...");
      barrier.await();
      out.println(""+CThread.currentThread()+" released from barrier #2...");
   }
}

class AThread extends CThread
{
   public AThread(DynamicBarrier barrier)
   {
      assert barrier != null;

      this.barrier = barrier;
   }

   public void arun()
   {
      barrier.signIn();
      pause((int)(100+Math.random()*500));
      out.println(""+currentThread()+" waiting on barrier #1...");
      barrier.await();
      out.println(""+currentThread()+" released from barrier #1...");
      pause((int)(100+Math.random()*500));
      out.println(""+currentThread()+" waiting on barrier #2...");
      barrier.await();
      out.println(""+currentThread()+" released from barrier #2...");
   }

   protected final DynamicBarrier barrier;
}

