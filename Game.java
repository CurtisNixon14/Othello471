import java.awt.dnd.*;
import javax.naming.directory.*;
import java.awt.*;

class Game{
	//
	//Game setup 1= black 2 = white 0 = unused square
	//COORDINATES ENTERED GO Y ACCROSS AND X DOWN (THE OPPOSITE OF A REGULAR X/Y PLANE)
	private int [][] game_board = new int[10][10];
	private final String illegal = "Illegal move";
	public Game(){
		setupGame();
	}
	public Game(int [][] test_game){
		game_board = test_game;
	}
	public int returnValue(int x, int y){
		return game_board[x][y];
	}
	private boolean checkInput(int value){
		if( value > 0 && value < 3){
			return true;
		}else{
			return false;
		}
	}
	private void changeSquare(int x, int y ,int value){
		game_board[x][y] = value;
	}
	/**
		* method to check if a move is illlegal
		* if legal, the function will return with no move made
		*
		* @param x the x coordinate of the square to be changed, 0-7
		*
		*/
	public void makeMove(int x, int y , int value){
		boolean legal;
		String ad  = checkAdSquares(x, y);
		if(checkInput(value)){
			if(game_board[x][y] == 0){
				if(ad.equalsIgnoreCase("00000000") || ad.equalsIgnoreCase("CC000CCC") || ad.equalsIgnoreCase("0CCCCC00") || ad.equalsIgnoreCase("000CCCCC") || ad.equalsIgnoreCase("CCCC000C")){
					System.out.println(illegal);
				}else{ 
					if(value == 1){
						if(ad.equalsIgnoreCase("11111111") || ad.equalsIgnoreCase("CC111CCC") || ad.equalsIgnoreCase("1CCCCC11") || ad.equalsIgnoreCase("111CCCCC") || ad.equalsIgnoreCase("CCCC111C")){
							System.out.println(illegal);
						}
					}else if(value == 2){
						if(ad.equalsIgnoreCase("22222222") || ad.equalsIgnoreCase("CC222CCC") || ad.equalsIgnoreCase("2CCCCC22") || ad.equalsIgnoreCase("222CCCCC") || ad.equalsIgnoreCase("CCCC222C")){
							System.out.println(illegal);
							}
					}else if(returnValue(x, y) != 0){
						System.out.println(illegal);
					}
					/**
					changeSquare(x, y, value);
					for(int i = 1; i < 9; i ++){
						legalMove = checkLegalMove(x,y,value,i);
						if(legalMove){
							flipChips(x, y, value, i);	
						}
					}
					*/
					legal = legalMove(x, y, value, true);
					System.out.println(legal);
				}
			}
		}
	}
	public boolean legalMove(int x, int y, int color, boolean flip){
		boolean legal = false;
		
		
		if(game_board[x][y] == 0){
			int pX, pY, current;
			boolean found;
			
			for(int i = -1; i <= 1; i++){
				for(int j = -1; j <= 1; j++){
					pX = y + i;
					pY = x + j;
					found = false;
					current = game_board[pY][pX];
					
					if(current == -1 || current == 0 || current == color){
						continue;
					}
					while(!found){
						pX += i;
						pY += j;
						current = game_board[pY][pX];
						
						if(current == color){
							found = true;
							legal = true;
							
							if(flip){
								pX -=i;
								pY -=j;
								current = game_board[pY][pX];
								
								while(current != 0){
									game_board[pY][pX] = color;
									pX -= i;
									pY -= j;
									current = game_board[pY][pX];
								}
							}
						}else if(current == -1 || current ==0){
							found = true;
						}
					}
				}
			}
		}
		return legal;
	}
		//returns a String of the state of the adjacent squares of the parameter
	//String Format(8 digits, one for each direction in clockwise direction -> Up,Upright, right, downright, down, downleft, left, upleft
	//String Inputs -> E= Empty, B = Black, W= White, C = Corner/Edge
	public String checkAdSquares(int x, int y){
		String state = "";
		//up coordinate
		if(x == 0){
			state += "C";
		}else{
			state+= returnValue(x-1, y);
		}
		//up right coordinate
		if(x == 0 || y == 7){
			state += "C";
		}else{
			state += returnValue(x-1, y+1);
		}
		//right coordinate
		if(y==7){
			state += "C";
		}else{
			state += returnValue(x, y+1);
		}
		//right down coordinate
		if(y==7 || x==7){
			state += "C";
		}else{
			state += returnValue(x+1, y+1);
		}
		//down coordinate
		if(x==7){
			state += "C";
		}else{
			state += returnValue(x+1, y);
		}
		//down left coordinate
		if(x==7 || y==0){
			state += "C";
		}else{
			state += returnValue(x+1, y-1);
		}
		//left coordinate
		if(y==0){
			state += "C";
		}else{
			state += returnValue(x, y-1);
		}
		//left up coordinate
		if(x==0 || y==0){
			state += "C";
		}else{
			state += returnValue(x-1, y-1);
		}
		return state;
	}
	private String printRow(int x){
		String row = "";
		for(int i = 0; i < 8; i++){
			switch(returnValue(x,i)){
				case 0: row += "E";
						break;
				case 1:	row += "B";
						break;
				case 2: row += "W";
						break;
				default: 
						break;
			}
			row+= " ";
		}
		return row;
	}
	public void printGameState(){
		System.out.println("    1 2 3 4 5 6 7 8");
		for(int i = 0; i < 9; i++){
			System.out.println((i+1) + " [ "+ printRow(i) + "]");	
		}
	}
	private void setupGame(){
		changeSquare(3, 4, 1);
		changeSquare(4, 3, 1);
		changeSquare(3, 3, 2);
		changeSquare(4, 4, 2);
	}			
	public static void main(String[] args){
			Game test_game = new Game();
			boolean done = true;
			for(int i = 1; i < 9; i++){
				for(int j = 1; j < 9; j++){
					if((test_game.legalMove(i,j,1,false)) || (test_game.legalMove(i,j,2,false))){
						done = false;
					}
				}
			}
			test_game.printGameState();
		}		
}
