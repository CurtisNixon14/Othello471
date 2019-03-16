package Othello471;

public class Board {

    private int[][] board;
    private int score;

    public Board(){
        score = 0;
        board = new int[8][8];
    }

    public Board(int player1, int player2){
        score = 0;
        board = new int[8][8];
        board[3][4] = player1;
        board[4][3] = player1;
        board[3][3] = player2;
        board[4][4] = player2;
    }

    // For deepCopy() method only.
    public Board(Board source){
        score = 0;
        board = new int[8][8];
        for (int r = 0; r < board.length; r++){
            for (int c = 0; c < board[0].length; c++){
                board[r][c] = source.at(r, c);
            }
        }
    }

    public int[] dimen(){
        return new int[] {board.length, board[0].length};
    }

    public int getScore(){
        return score;
    }

    public int at(int row, int column){
        return board[row][column];
    }

    public void mark(int player, int row, int column){
        board[row][column] = player;
    }

    // Determines how many of a player's discs are on the board.
    public int score(int player){
        for (int i = 0; i < board.length; i++){
            for (int j = 0; j < board[0].length; j++){
                if (board[i][j] == player) {
                    score+=1;
                }
            }
        }
        return score;
    }

    public void printBoard(){
        for (int r = 0; r < board.length; r++){
            StringBuilder row = new StringBuilder();
            for (int c = 0; c < board[0].length; c++){
                switch(board[r][c]){
                    case 0: row.append('_'); break;
                    case 1: row.append('B'); break;
                    case 2: row.append('W'); break;
                }
            }
            System.out.println(row.toString());
        }
    }
}
