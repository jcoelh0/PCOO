package city;

import java.awt.Color;
import java.awt.Image;
import java.awt.Point;
import java.io.IOException;
import static java.lang.System.err;
import static java.lang.System.exit;
import static java.lang.System.out;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import pt.ua.gboard.GBoard;
import pt.ua.gboard.Gelem;
import pt.ua.gboard.basic.ImageGelem;
import pt.ua.gboard.basic.StringGelem;
import pt.ua.gboard.games.Labyrinth;
import pt.ua.gboard.games.LabyrinthGelem;

/**
 *
 * @author João Coelho
 */
public final class Map {
	
	protected static int N;
	protected static GBoard board;
	protected static Labyrinth labyrinth;
	private List<int[]> blocksArray;
	private Point prisonSymbol, thiefHome, store1, store2;
	
	public Map(char[] symbols, Gelem[] gelems){
		blocksArray = new ArrayList<>();
		createMap(symbols, gelems);
		
	}
	
	public void createMap(char[] symbols, Gelem[] gelems){
		String map = "map1.txt";
		
		out.println("Usage: CityRun <map-file>");
		out.println();
		out.println("Using \"" + map + "\" as default");
		
		

		if (!Labyrinth.validMapFile(map)) {
			err.println("ERROR: invalid map file \"" + map + "\"");
			exit(1);
		}
		
		LabyrinthGelem.setShowRoadBoundaries();
		
		
		N = 1;
		labyrinth = new Labyrinth(map, symbols, N);
		
		board = labyrinth.board;
		
		prisonSymbol = new Point(17,21);
		thiefHome = new Point(17,1);
		store1 = new Point(4,22);
		store2 = new Point(9,19);
		
		TargaReader tga = new TargaReader();
		
		Gelem roadHorizontal = null,
			roadBaixoDireita = null, 
			roadVertical = null,
			roadCimaDireita = null, 
			roadDireitaBaixo = null,
			roadBaixoEsquerda = null,
			roadTudo = null,
			roadTCima = null,
			roadTBaixo = null,
			roadTEsquerda = null,
			roadTDireita = null;
		
		Gelem store = new ImageGelem("src\\city\\store.jpg", board, 100);
		
		Gelem buildings[] = {new ImageGelem("src\\city\\buildings\\building1.png", board, 100),
		new ImageGelem("src\\city\\buildings\\building2.png", board, 100),
		new ImageGelem("src\\city\\buildings\\building4.jpg", board, 100)};
		
		
		try {
			roadHorizontal = new ImageGelem(tga.getImage("src\\city\\roads\\roadEW.tga"), board, 100);
			roadBaixoDireita = new ImageGelem(tga.getImage("src\\city\\roads\\roadSE.tga"), board, 100);
			roadVertical = new ImageGelem(tga.getImage("src\\city\\roads\\roadNS.tga"), board, 100);
			roadCimaDireita = new ImageGelem(tga.getImage("src\\city\\roads\\roadNE.tga"), board, 100);
			roadBaixoEsquerda = new ImageGelem(tga.getImage("src\\city\\roads\\roadSW.tga"), board, 100);
			roadDireitaBaixo = new ImageGelem(tga.getImage("src\\city\\roads\\roadNW.tga"), board, 100);
			roadTudo = new ImageGelem(tga.getImage("src\\city\\roads\\roadNEWS.tga"), board, 100);
			//roads T
			roadTCima = new ImageGelem(tga.getImage("src\\city\\roads\\roadTS.tga"), board, 100);
			roadTBaixo = new ImageGelem(tga.getImage("src\\city\\roads\\roadTN.tga"), board, 100);
			roadTEsquerda = new ImageGelem(tga.getImage("src\\city\\roads\\roadTW.tga"), board, 100);
			roadTDireita = new ImageGelem(tga.getImage("src\\city\\roads\\roadTE.tga"), board, 100);
		} catch (IOException ex) {
			Logger.getLogger(Map.class.getName()).log(Level.SEVERE, null, ex);
		}
		
		
		
		
		
		for (int i = 0; i < gelems.length; i++) {
			labyrinth.attachGelemToRoadSymbol(symbols[i], gelems[i]);
		}
		
		//prisonSymbol
		labyrinth.attachGelemToRoadSymbol(symbols[0], gelems[0]);
		
		//thiefHome
		labyrinth.attachGelemToRoadSymbol(symbols[1], gelems[1]);
		
		//store1
		labyrinth.attachGelemToRoadSymbol(symbols[2], gelems[2]);
		
		//store2
		labyrinth.attachGelemToRoadSymbol(symbols[3], gelems[3]);
		
		int[][] mapLab = new int[labyrinth.numberOfLines][labyrinth.numberOfColumns];
		int l = 0, c = 0;
		boolean isRoad = false;
		for (l = 0; !isRoad && l < labyrinth.numberOfLines; l++) {
			for (c = 0; !isRoad && c < labyrinth.numberOfColumns; c++) {
				isRoad = labyrinth.isRoad(l, c);
				
			}
		}
		for (int le = 0;  le < labyrinth.numberOfLines; le++) {
			for (int co = 0;  co < labyrinth.numberOfColumns; co++) {
				isRoad = labyrinth.isRoad(le, co);
				//System.out.println(le+","+co+"="+isRoad);
				if(isRoad)
					mapLab[le][co] = 0;
				else{
					blocksArray.add(new int[]{le,co});
					mapLab[le][co] = 1;
				}
			}
		}
		/*for (l = 0; l < labyrinth.numberOfLines; l++) {
			for (c = 0; c < labyrinth.numberOfColumns; c++) {
				System.out.print(mapLab[l][c]+",");
			}
		}*/
		
		
		//board.draw(store, 0, 0, 1);
		//board.draw(building1, 0, 1, 1);
		//board.draw(building2, 0, 2, 1);
		//board.draw(building3, 0, 3, 1);
		
		///board.draw(roadHorizontal, 3, 2, 1);
		//board.draw(roadBaixoDireita, 3, 1, 0);
		//board.draw(roadVertical, 2, 1, 1);
		//board.draw(roadCimaDireita, 1, 1, 0);
		
		Gelem temp = null;
		
		for (int i = 0; i < board.cellHeight()-1; i++) {
			for (int j = 0; j < board.cellWidth()-1; j++) {
				if(mapLab[i][j] == 0){
					//baixo                 cima             esquerda                 direita
					//if(mapLab[i+1][j]==0 && mapLab[i-1][j]==0 && mapLab[i][j-1]==0 && mapLab[i][j+1]==0))
					if(mapLab[i+1][j]+mapLab[i-1][j]+mapLab[i][j-1]+mapLab[i][j+1]>2)
						temp = roadTudo;
					else if(mapLab[i+1][j]==1 && mapLab[i-1][j]==0 && mapLab[i][j-1]==0 && mapLab[i][j+1]==0){
						temp = roadTCima;
					}else if(mapLab[i+1][j]==0 && mapLab[i-1][j]==1 && mapLab[i][j-1]==0 && mapLab[i][j+1]==0){
						temp = roadTBaixo;
					}else if(mapLab[i+1][j]==0 && mapLab[i-1][j]==0 && mapLab[i][j-1]==0 && mapLab[i][j+1]==1){
						temp = roadTEsquerda;
					}else if(mapLab[i+1][j]==0 && mapLab[i-1][j]==0 && mapLab[i][j-1]==1 && mapLab[i][j+1]==0){
						temp = roadTDireita;
					}else if(mapLab[i+1][j]==0 && mapLab[i-1][j]==0 && mapLab[i][j-1]==0 && mapLab[i][j+1]==0)
						temp = roadTudo;
					else if(mapLab[i+1][j]==0 && mapLab[i][j+1]==0)
						temp = roadCimaDireita;
					else if(mapLab[i][j-1]==0 && mapLab[i+1][j]==0)
						temp = roadDireitaBaixo;
					else if(mapLab[i-1][j]==0 && mapLab[i][j+1]==0)
						temp = roadBaixoDireita;
					else if(mapLab[i-1][j]==0 && mapLab[i][j-1]==0)
						temp = roadBaixoEsquerda;
					
					else if(mapLab[i][j+1]==0 && mapLab[i][j-1]==0)
						temp = roadHorizontal;
					else if(mapLab[i+1][j]==0)
						temp = roadVertical;
					
					
					board.draw(temp, i, j, 0);
				}else{
					board.draw(buildings[new Random().nextInt(buildings.length)], i, j, 0);
				}
			}
		}
		
		l--;
		c--;
		labyrinth.putRoadSymbol(prisonSymbol.x, prisonSymbol.y, symbols[0]);
		
		labyrinth.putRoadSymbol(thiefHome.x, thiefHome.y, symbols[1]);
		
		labyrinth.putRoadSymbol(store1.x, store1.y, symbols[2]);
		
		labyrinth.putRoadSymbol(store2.x, store2.y, symbols[3]);
		
		pause(1000);
		
		
	}
	
	public Labyrinth getLabyrinth(){
		return labyrinth;
	}
	
	public Point[] getStorePositions(){
		return new Point[]{store1,store2};
	}
	
	public Point getPrisonPosition(){
		return prisonSymbol;
	}
	
	public List<int[]> getBlocksArray(){
		return blocksArray;
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
	
	public void terminate(){
		board.terminate();
	}
	
}
