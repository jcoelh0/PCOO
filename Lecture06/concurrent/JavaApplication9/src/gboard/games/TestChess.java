package gboard.games;

import pt.ua.gboard.*;
import pt.ua.gboard.basic.*;
import pt.ua.gboard.games.*;

public class TestChess
{
   static public void main(String[] args)
   {
      ChessBoard cboard = new ChessBoard(8);
      cboard.initializeChessGame();
      cboard.board.pushInputHandler(new InputHandling(cboard));
      //cboard.move(0, 0, 5, 0);
   }
}

class InputHandling extends GBoardInputHandler
{
   public InputHandling(ChessBoard cboard)
   {
      super(mousePressedMask);
      this.cboard = cboard;
   }

   final ChessBoard cboard;

   public void run(GBoard gboard, int line, int column, int layer, int type, int code, Gelem gelem)
   {
      if (line >= 1 && line <= cboard.N && column >= 1 && column <= cboard.N)
      {
         if (cboard.positionSelected(line-1, column-1))
            cboard.unselectPosition(line-1, column-1);
         else
            cboard.selectPosition(line-1, column-1);
      }
   }
}

