public class Board {

    int[][] board;

    public Board(int player1, int player2){
        board = new int[8][8];
        board[3][4] = player1;
        board[4][3] = player1;
        board[3][3] = player2;
        board[4][4] = player2;
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

    public boolean inbounds(int row, int column){
        // If the move is within the boundaries of the board, it is valid.
        return (row >= 0 && row < 8 && column >= 0 && column < 8);
    }

}
