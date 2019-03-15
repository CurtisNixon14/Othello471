package Othello471;

import java.lang.String;

public class Board {

    int[][] board;

    public Board(){
        board = new int[8][8];
    }

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

    // Determining whether this move is contiguous with other pieces and captures at least one enemy piece.
    public boolean capturesDisc(int player, int orientation, int row, int column, int[] on_same_line){
        int i;
        int capture_count = 0;

        // Horizontal
        if (orientation == 1){
            if (column < on_same_line[1]){
                for (i = column; i < on_same_line[1]; i++){
                    if (board[row][i] == 0) {return false;}
                    else if (board[row][i] != player) {capture_count++;}
                }
            }
            else{
                for (i = on_same_line[1]; i < column; i++){
                    if (board[row][i] == 0) {return false;}
                    else if (board[row][i] != player) {capture_count++;}
                }
            }
        }

        // Vertical
        else if (orientation == 2){
            if (row < on_same_line[0]){
                for (i = row; i < on_same_line[0]; i++){
                    if (board[i][column] == 0) {return false;}
                    else if (board[i][column] != player) {capture_count++;}
                }
            }
            else{
                for (i = on_same_line[0]; i < row; i++){
                    if (board[i][column] == 0) {return false;}
                    else if (board[i][column] != player) {capture_count++;}
                }
            }
        }

        // Diagonal
        else if (orientation == 3){
            int limit;
            if (row < on_same_line[0]){
                i = row;
                limit = on_same_line[0];
            }
            else{
                i = on_same_line[0];
                limit = row;
            }
            if (column < on_same_line[1]){
                for (int r = i; r < limit; r++){
                    for (int c = column; c < on_same_line[1]; c++){
                        if (board[r][c] == 0) {return false;}
                        else if (board[r][c] != player) {capture_count++;}
                    }
                }
            }
            else {
                for (int r = i; r < limit; r++){
                    for (int c = on_same_line[1]; c < column; c++){
                        if (board[r][c] == 0) {return false;}
                        else if (board[r][c] != player) {capture_count++;}
                    }
                }
            }
        }

        // If this move captures opponent discs and is contiguous with other discs, return true.
        if (capture_count > 0){
            return true;
        }
        else{
            return false;
        }
    }

}
