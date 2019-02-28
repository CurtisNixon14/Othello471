
class Game{
	//Game setup 1= black 2 = white 0 = unused square
	private int [][] game_board ={	
				{0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0}};
	public Game(){
		setupGame();
	}
	public Game(int [][] test_game){
		game_board = test_game;
	}
	public int returnValue(int x, int y){
		return game_board[x][y];
	}
	public void makeMove(int x, int y , int value){
		
		if(value<3 && value >1){
			game_board[x][y] = value;
			flipChips(x,y);
		}else{
			System.out.println("Illegal move");
		}
	}
	private void flipChips(int x, int y){
	}
	//returns a String of the state of the adjacent squares of the parameter
	//String Format -> Up,Down,Left,Right
	//String Inputs -> E= Empty, B = Black, W= White, C = Corner/Edge
	public String checkAdSquares(int x, int y){
		String state = "";
		//if x == 0 then the spot is along the top row, else add the state of the square to the string
		if(x==0){
			state += "C";
		}else{
			state += returnValue(x-1, y);
		}
		//if x==7 then the spot is along the bottom row, 
		if(x==7){
			state += "C";
		}else{
			state += returnValue(x+1, y);
		}
		//if y==0 then the spot in alng the left edge
		if(x==0){
			state += "C";
		}else{
			state += returnValue(x, y-1);
		}
		//if y == 7 then the spot is along the right edge
		if(x==7){
			state += "C";
		}else{
			state += returnValue(x, y+1);
		}
		return state;
	}
	private void changeSquare(int x, int y, int value){
		game_board[x][y] = value;
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
		for(int i = 0; i < 8; i++){
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
			test_game.printGameState();
			System.out.println(test_game.checkAdSquares(4,3));
		}		
}