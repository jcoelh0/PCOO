package concurrent;

import static java.lang.System.*;

import pt.ua.concurrent.*;

public class TestSignals
{
   public static void main(String[] args)
   {
      Signal sig = new PersistentSignal();
      Sender sender = new Sender(10, sig);
      Receiver receiver = new Receiver(sig);
      sender.start();
      receiver.start();
      sender.ajoin();
      sig.interruptWaitingThreads();
   }

   static class Sender extends CThread
   {
      public Sender(int counter, Signal sig)
      {
         assert counter >= 0;
         assert sig != null;

         this.counter = counter;
         this.sig = sig;
      }

      public void arun()
      {
         for(int i = 1; i <= counter; i++)
         {
            out.println("[Sender] signal #"+i);
            sig.send();
         }
      }

      private int counter;
      private Signal sig;
   }

   static class Receiver extends CThread
   {
      public Receiver(Signal sig)
      {
         assert sig != null;

         counter = 0;
         this.sig = sig;
      }

      public void arun()
      {
         boolean finished = false;
         while(!finished)
         {
            try
            {
               sig.await();
               counter++;
               out.println("[Receiver] signal #"+counter);
            }
            catch(ThreadInterruptedException e)
            {
               finished = true;
            }
         }
      }

      private int counter;
      private Signal sig;
   }

}
