package myothello;

import java.awt.dnd.*;
import javax.naming.directory.*;
import java.awt.*;
import java.util.Scanner;
import java.util.List;
import java.util.*;

class Game{
	//
	//Game setup 1= black 2 = white 0 = unused square
	//I used 10X10 because its easier to check border moves with room in the array on each side
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
	//makes sure the input to go into the array is either 1 or 2
	private boolean checkInput(int value){
		if( value > 0 && value < 3){
			return true;
		}else{
			return false;
		}
	}
        public int getScore(){
            int score = 0;
            for(int i = 0; i < 10; i++){
                for(int j = 0; j < 10; j++){
                    if(game_board[i][j]==1){
                        score--;
                    }
                    if(game_board[i][j]==2){
                        score++;
                    }
                }
            }
            return score;
        }
	//return values: -1 border, 0 empty, 1 black, 2 white
	public int[][] getGameState(){
		return game_board;
	}
        public void setGameState(int[][]g){
            game_board = g;
        }
        public int[][] copyGameState(){
		int [][] nb = new int[10][10];
		for(int i = 0; i < 10; i++){
			for(int j = 0; j < 10; j++){
				nb[i][j] = game_board[i][j];
			}
		}
		return nb;
	}
	public void changeSquare(int x, int y ,int value){
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
		}
	}
	//Returns true if the proposed move is legal 
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
        public void newGame(){
            game_board = new int[10][10];
            setupGame();
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
        public void showScore(){
            int black = 0;
            int white = 0;
            for (int i = 1; i < 10 ; i++){
                for(int j = 0; j < 10; j++){
                    if(game_board[i][j] == 1){
                        black++;
                    }
                    if(game_board[i][j] == 2){
                        white++;
                    }
                }   
            }
            System.out.println("Black: " + black + " White: " + white);
        }
	public void endGame(){
		int black = 0;
		int white = 0;
		for(int i = 1; i <9; i ++){
			for(int j = 1; j <9; j++){
				if(game_board[i][j] == 1){
					black++;
				}else{
					white++;
				}
			}
		}
		if(black > white){
			System.out.println("Game over: Black wins");
		}else if(white > black){
			System.out.println("Game over: White wins");
		}else{
			System.out.println("No winner: DeadLock");
		}
	}
	public Point getInputFromUser(){
		Scanner input = new Scanner(System.in);
		int x = 0;
		int y =0;
		boolean validInput = false;
		String invalid = "The entered number is invalid, please try again?";
		System.out.println("Please enter the x coordinate of your next move");
		while(!validInput){
			try {
				x = input.nextInt();
			} catch (Exception e) {
				System.out.println(invalid);
			}
			if(x < 9 && x > 0){
				validInput = true;
			}
		}
		validInput = false;
		System.out.println("Please enter the y coordinate of your next move");
		while(!validInput){
			try {
				y = input.nextInt();
			} catch (Exception e) {
				System.out.println(invalid);
			}
			if(y < 9 && y > 0){
				validInput = true;
			}
		}
		return new Point(x,y);
	}
	public List<Point> getMoves(int color){
		List<Point> list = new ArrayList<Point>();
		Point p;
		for(int i = 1; i<9;i++){
			for(int j = 1; j<9;j++){
				if(legalMove(i,j, color, false)){
					p = new Point(j,i);
					list.add(p);
				}
			}
		}
		return list;
	}
}
class Point{
	private final int x;
	private final int y;
	
	public Point(){
		this.x = 0;
		this.y = 0;
	}
	public Point(int x, int y){
		this.x = x;
		this.y = y;
	}
	public int getX(){
		return this.x;
	}
	public int getY(){
		return this.y;
	}
	@Override
	public String toString(){
		return ("("+x+","+y+")");
	}
}
