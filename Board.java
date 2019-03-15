package Othello471;

public class Board {

    private int[][] board;
    private boolean visited;

    public Board(){
        visited = false;
        board = new int[8][8];
    }

    public Board(int player1, int player2){
        visited = false;
        board = new int[8][8];
        board[3][4] = player1;
        board[4][3] = player1;
        board[3][3] = player2;
        board[4][4] = player2;
    }

    // For deepCopy() method only.
    public Board(int[][] board, boolean visited){
        this.visited = visited;
        this.board = new int[8][8];
        for (int r = 0; r < board.length; r++){
            for (int c = 0; c < board[0].length; c++){
                this.board[r][c] = board[r][c];
            }
        }
    }

    public int[] dimen(){
        return new int[] {board.length, board[0].length};
    }

    public boolean already_visited(){
        return visited;
    }

    public void mark_visited(){
        visited = true;
    }

    public int at(int row, int column){
        return board[row][column];
    }

    public void mark(int player, int row, int column){
        board[row][column] = player;
    }

    public Board deepCopy(){
        return new Board(board, false);
    }

    // Determines how many of a player's discs are on the board.
    public int findTotalDiscs(int player){
        int count = 0;
        for (int i = 0; i < board.length; i++){
            for (int j = 0; j < board[0].length; j++){
                if (board[i][j] == player) {
                    count+=1;
                }
            }
        }
        return count;
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
