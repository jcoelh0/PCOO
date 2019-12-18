package gboard.games;

//import static java.lang.System.*;
import pt.ua.gboard.*;
import pt.ua.gboard.basic.*;
import pt.ua.gboard.games.*;

public class TestCard
{
   public static void main(String[] args)
   {
      int numberOfLines = 4;
      int numberOfColumns = 13;

      GBoard gboard = new GBoard("Test Card", numberOfLines, numberOfColumns, 80, 110, 1);
      DeckOfCards deckOfCards = new DeckOfCards(gboard);

      for(Suits s: Suits.values())
         for(Rank r: Rank.values())
            gboard.draw(deckOfCards.cardGelem(s, r), s.ordinal(), r.ordinal(), 0);
      gboard.pushInputHandler(new InputHandling(deckOfCards));
   }
}

class InputHandling extends GBoardInputHandler
{
   public InputHandling(DeckOfCards deckOfCards)
   {
      super(mousePressedMask);
      this.deckOfCards = deckOfCards;
   }

   final DeckOfCards deckOfCards;

   public void run(GBoard gboard, int line, int column, int layer, int type, int code, Gelem gelem)
   {
      if (gelem != null)
      {
         gboard.erase(gelem, line, column, layer);
         gboard.draw(deckOfCards.getBackBlueGelem(), line, column, layer);
      }
   }
}

