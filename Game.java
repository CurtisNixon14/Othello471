

import java.awt.dnd.*;
import javax.naming.directory.*;
import java.awt.*;
import java.util.Scanner;

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
		int posX = y;
		int posY = x;
		legal = legalMove(posX, posY, value, false);
		if(legal){
			legalMove(posX, posY, value, true);
			changeSquare(posX, posY, value);
			System.out.println("X: " + posX + " Y: " + posY);
		}
		//Comment out to no prinr gamestate every tunr
		printGameState();
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
		for(int i = 1; i < 9; i++){
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
		System.out.println("Y X 1 2 3 4 5 6 7 8");
		for(int i = 1; i < 9; i++){
			System.out.println((i) + " [ "+ printRow(i) + "]");	
		}
	}
	private void setupGame(){
		for(int i = 0; i < 10;i++){
			changeSquare(0, i, -1);
			changeSquare(i, 0, -1);
			changeSquare(9, i, -1);
			changeSquare(i, 9, -1);
		}
		changeSquare(4, 5, 1);
		changeSquare(5, 4, 1);
		changeSquare(4, 4, 2);
		changeSquare(5, 5, 2);
	}
	public void simGame(){
		private Scanner input = new Scanner(System.in);
		int in;
		int x;
		int y;
		boolean turn = true;
		while(true){
			if(turn){
				System.out.println("Enter play for black team");
				x = input.nextInt();
				y = input.nextInt();
				makeMove(x,y , 1);
				turn = false;
			}else{
				System.out.println("Enter play for white team");
				x = input.nextInt();
				y = input.nextInt();
				makeMove(x,y, 2);
				turn = true;
			}
		}
	}
		public static void main(String [] args){
			Game sim_game = new Game();
			sim_game.printGameState();
			sim_game.simGame();
		}
}