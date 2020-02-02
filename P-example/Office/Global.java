package Office;

import pt.ua.concurrent.Metronome;
import pt.ua.concurrent.CThread;
/*
 * Problem parameter definition singleton
 */

public class Global
{
   /*
    * Common metronome
    */
   public static int DEFAULT_PERIOD = 10; // 10 ms
   public static Metronome metronome;

   public static int MIN_RANDOM_PAUSE = 20; // # syncs
   public static int MAX_RANDOM_PAUSE = 40; // # syncs

   public static int MIN_SPEED = 1;
   public static int MAX_SPEED = 3;

   public static int MIN_NUM_EMPLOYEES = 3;
   public static int MAX_NUM_EMPLOYEES = 40;

   /* No need to understand the following code */

   /*
    * square dimension (related with movement smoothness)
    */
   public static final int N = 4;

   /*
    * default map
    */
   public static final String MAP="office.txt";

   /*
    * Office entry symbol
    */
   public static final char OFFICE_INP = 'I';

   /*
    * Office exit symbol
    */
   public static final char OFFICE_OUT = 'O';

   /*
    * Room entry symbol
    */
   public static final char ROOM_INP = 'i';

   /*
    * Room exit symbol
    */
   public static final char ROOM_OUT = 'o';

   /*
    * Chair symbol
    */
   public static final char CHAIR = '-';

   /*
    * Vending machine buy symbol
    */
   public static final char VEND_BUY = 'b';

   /*
    * Vending machine amount of meals
    */
   public static final int VEND_CAPACITY = 5;

   /*
    * Vending machine symbol
    */
   public static final char VEND_SYMBOL = 'F';

}

