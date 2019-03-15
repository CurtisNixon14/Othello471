package Othello471;

import java.util.ArrayList;
import java.util.Stack;

public class PlayerAI {

    private int turn; // Black goes first.
    private ArrayList<int[]> previousMoves;

    public PlayerAI(int turn){
        this.turn = turn;
        previousMoves = new ArrayList<int[]>();
        //Adding starting moves to previousMoves.
        if (turn == 1){
            previousMoves.add(new int[] {3, 4});
            previousMoves.add(new int[] {4, 3});
        }
        else if (turn == 2){
            previousMoves.add(new int[] {3, 3});
            previousMoves.add(new int[] {4, 4});
        }
    }

    public Board nextMove(Board currentState) throws Exception {
        // Getting a list of all legal moves.
        ArrayList<int[]> legalMoves = getLegalMoves(currentState);
        if (legalMoves.size() == 0) {
            throw new Exception();  // If no legal moves are available, throws an Exception to warn Othello471.Game.
        }
        // Getting the best move.
        int[] bestMove = getBestMove(currentState, legalMoves);
        previousMoves.add(bestMove);
        // Making a move.
        return updateBoard(currentState, bestMove);
    }

    // Places a disc onto the board and flips any captured discs.
    private Board updateBoard(Board prevState, int[] thisMove){
        Board currentState = prevState.deepCopy();

        // Need to know where the "anchoring" disc is to know which opponent discs to flip.
        int[] prev = previousMoves.get(thisMove[2]);
        int start;
        int limit;
        // Horizontal
        if (thisMove[0] == prev[0]){
            if (thisMove[1] < prev[1]){ start = thisMove[1]; limit = prev[1]; }
            else{ start = prev[1]; limit = thisMove[1]; }
            for (int i = start; i < limit; i++){
                currentState.mark(turn,thisMove[0],i);
            }
        }

        // Vertical
        else if (thisMove[1] == prev[1]) {
            if (thisMove[0] < prev[0]) { start = thisMove[0]; limit = prev[0]; }
            else { start = prev[0]; limit = thisMove[0]; }
            for (int i = start; i < limit; i++){
                currentState.mark(turn,i,thisMove[1]);
            }
        }

        // Diagonal
        else if (prev[1] - thisMove[1] == prev[0] - thisMove[0]) {
            int start2;
            int limit2;
            if (thisMove[0] < prev[0]){ start = thisMove[0]; limit = prev[0]; }
            else{ start = prev[0]; limit = thisMove[0]; }
            if (thisMove[1] < prev[1]){ start2 = thisMove[1]; limit2 = prev[1]; }
            else { start2 = prev[1]; limit2 = thisMove[1]; }
            for (int r = start; r < limit; r++){
                for (int c = start2; c < limit2; c++){
                    currentState.mark(turn,r,c);
                }
            }
        }

        return currentState;
    }

    // Get a list of all legal moves.
    private ArrayList<int[]> getLegalMoves(Board board){
        ArrayList<int[]> list_legalmoves = new ArrayList<int[]>();
        int on_same_line;

        // Loop through every single empty square on the board and record any legal moves.
        for (int row = 0; row < board.dimen()[0]; row++) {
            for (int col = 0; col < board.dimen()[1]; col++) {
                if (board.at(row,col) == 0) {
                    on_same_line = confirmLegalMove(board, row, col);
                    if (on_same_line >= 0){ list_legalmoves.add(new int[]{row, col, on_same_line}); }
                }
            }
        }
        return list_legalmoves;
    }

    // Checks if a move A) captures an opponent disc and B) is placed contiguously with other discs.
    private int confirmLegalMove(Board board, int row, int column){
        int[] on_same_line = new int[] {0, 0};
        int orientation = 0; // 1 is horizontal, 2 is vertical, 3 is diagonal
        // Checking that this move is on a horizontal/vertical/diagonal line with another disc of the same color.
        int start, limit;
        int i;
        int capture_count = 0;
        for (i = 0; i < previousMoves.size(); i++) {
            if (row == previousMoves.get(i)[0]){
                on_same_line = previousMoves.get(i);
                if (column < on_same_line[1]){ start = column; limit = on_same_line[1]; }
                else{ start = on_same_line[1]; limit = column; }
                for (int c = start; c < limit; c++){
                    if (board.at(row, c) == 0) {capture_count = 0; break;}
                    else if (board.at(row, c) != turn) {capture_count++;}
                }
                break;
            }
            else if (column == previousMoves.get(i)[1]) {
                on_same_line = previousMoves.get(i);
                if (row < on_same_line[0]){ start = row; limit = on_same_line[0]; }
                else{ start = on_same_line[0]; limit = row; }
                for (int r = start; r < limit; r++){
                    if (board.at(r, column) == 0) {capture_count = 0; break;}
                    else if (board.at(row, column) != turn) {capture_count++;}
                }
                break;
            }
            else if (previousMoves.get(i)[1] - column == previousMoves.get(i)[0] - row) {
                on_same_line = previousMoves.get(i);
                int start2, limit2;
                if (row < on_same_line[0]){ start = row; limit = on_same_line[0]; }
                else{ start = on_same_line[0]; limit = row; }
                if (column < on_same_line[1]){ start2 = column; limit2 = on_same_line[1]; }
                else { start2 = on_same_line[1]; limit2 = column; }
                for (int r = start; r < limit; r++){
                    for (int c = start2; c < limit2; c++){
                        if (board.at(r,c) == 0) {capture_count = 0; break;}
                        else if (board.at(r,c) != turn) {capture_count++;}
                    }
                }
                break;
            }
        }
        // Checking that this move captures at least one opponent disc and is contiguous with the other discs.
        if (capture_count > 0) {
            return i;
        }
        else{
            return -1;
        }
    }

    private int[] getBestMove(Board current_board, ArrayList<int[]> legalmoves){
        int[] bestMove = legalmoves.get(0);

        Stack<Board> dfs_tree = new Stack<>();
        current_board.mark_visited();
        dfs_tree.push(current_board);

        for (int[] move : legalmoves){
            
        }





        return bestMove;
    }

}
