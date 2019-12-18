
import static java.lang.System.*;
import java.awt.*;
import pt.ua.gboard.*;
import pt.ua.gboard.basic.*;
import pt.ua.gboard.games.*;

public class TestLabyrinth {

	static public void main(String[] args) {
		String map = "map1.txt";
		if (args.length != 1) {
			out.println("Usage: TestLabyrinth <map-file>");
			out.println();
			out.println("Using \"" + map + "\" as default");
		} else {
			map = args[0];
		}

		if (!Labyrinth.validMapFile(map)) {
			err.println("ERROR: invalid map file \"" + map + "\"");
			exit(1);
		}
		LabyrinthGelem.setShowRoadBoundaries();
		char[] roadSymbols = {'S', 'X'};
		N = 4;
		Labyrinth labyrinth = new Labyrinth(map, roadSymbols, N);
		labyrinth.attachGelemToRoadSymbol(roadSymbols[0], new StringGelem("" + roadSymbols[0], Color.green, N, N));
		labyrinth.attachGelemToRoadSymbol(roadSymbols[1], new StringGelem("" + roadSymbols[1], Color.red, N, N));
		board = labyrinth.board;
		board.pushInputHandler(new TestInputHandler());

		int l = 0, c = 0;
		boolean isRoad = false;
		for (l = 0; !isRoad && l < labyrinth.numberOfLines; l++) {
			for (c = 0; !isRoad && c < labyrinth.numberOfColumns; c++) {
				isRoad = labyrinth.isRoad(l, c);
			}
		}
		l--;
		c--;

		pause(2000);
		labyrinth.putRoadSymbol(l, c, 'X');
		pause(2000);
		labyrinth.putRoadSymbol(l, c, 'S');
		pause(2000);
		labyrinth.putRoadSymbol(l, c, ' ');

		Thread t1 = new Thread(new Runnable() {
			public void run() {
				Gelem ge = new PacmanGelem(Color.red, 90, N, N);
				int l = board.numberOfLines() / 2;
				int c = board.numberOfColumns() / 2;
				int dir = (int) (Math.random() * (double) 4); // [0;3]
				int incCol = (dir % 2 == 1 ? 0 : dir - 1);
				int incLin = (dir % 2 == 0 ? 0 : 2 - dir);
				int steps = 1 + (int) (Math.random() * (double) 10); // [1;10]
				int wait = 100 + (int) (Math.random() * (double) 101); // [100;200]
				board.draw(ge, l, c, 1);

				while (true) {
					assert steps > 0;
					int lastCol = c;
					int lastLin = l;
					if (board.validPosition(l + incLin, c + incCol)
						&& board.gelemFitsInside(ge, l + incLin, c + incCol)) {
						c += incCol;
						l += incLin;
						board.move(ge, lastLin, lastCol, 1, l, c, 1);
					}
					GBoard.sleep(wait);
					steps--;
					if (steps == 0) {
						dir = (int) (Math.random() * (double) 4); // [0;3]
						incCol = (dir % 2 == 1 ? 0 : dir - 1);
						incLin = (dir % 2 == 0 ? 0 : 2 - dir);
						steps = 1 + (int) (Math.random() * (double) 10); // [1;10]
						wait = 100 + (int) (Math.random() * (double) 101); // [100;200]
					}
				}
			}
		});

		Thread t2 = new Thread(new Runnable() {
			public void run() {
				Gelem ge = new FilledGelem(Color.magenta, 90, N, N);
				int l = N;
				int c = 10 * N;
				int incCol = -1;
				board.draw(ge, l, c, 1);

				while (true) {
					int lastCol = c;
					c += incCol;
					if (c == N * 10 || c == N * 1) {
						incCol *= -1;
					}
					board.move(ge, l, lastCol, 1, l, c, 1);
					GBoard.sleep(100);
				}
			}
		});
		t1.start();
		t2.start();
	}

	static void pause(int ms) {
		assert ms >= 0;

		try {
			Thread.sleep(ms);
		} catch (InterruptedException e) {
			err.println("Thread interrupted!");
			exit(1);
		}
	}

	protected static int N;
	protected static GBoard board;
}
