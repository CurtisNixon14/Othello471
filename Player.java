import java.util.ArrayList;

public class Player {

    private int turn; // Black goes first.
    private ArrayList<int[]> previousMoves;

    public Player(int turn){
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
            throw new Exception();  // If no legal moves are available, throws an Exception to warn Game.
        }
        // Getting the best move.
        int[] bestMove = getBestMove(currentState, legalMoves);
        previousMoves.add(bestMove);
        // Making a move.
        return updateBoard(currentState, bestMove);
    }

    // Places a disc onto the board and flips any captured discs.
    private Board updateBoard(Board currentState, int[] bestMove){


        return currentState;
    }

    // Get a list of all legal moves.
    private ArrayList<int[]> getLegalMoves(Board board){
        ArrayList<int[]> list_legalmoves = new ArrayList<int[]>();

        // Loop through every single empty square on the board and record any legal moves.
        for (int row = 0; row < board.board.length; row++) {
            for (int col = 0; col < board.board[0].length; col++) {
                if (board.board[row][col] == 0 && confirmLegalMove(board, row, col)) {
                    list_legalmoves.add(new int[]{row, col});
                }
            }
        }
        return list_legalmoves;
    }

    // Checks if a move A) captures an opponent disc and B) is placed contiguously with other discs.
    private boolean confirmLegalMove(Board board, int row, int col){
        int[] on_same_line = {0, 0};
        int orientation = 0; // 1 is horizontal, 2 is vertical, 3 is diagonal
        // Checking that this move is on a horizontal/vertical/diagonal line with another disc of the same color.
        for (int i = 0; i < previousMoves.size(); i++) {
            if (row == previousMoves.get(i)[0]){
                on_same_line = previousMoves.get(i);
                orientation = 1;
                break;
            }
            else if (col == previousMoves.get(i)[1]) {
                on_same_line = previousMoves.get(i);
                orientation = 2;
                break;
            }
            else if (previousMoves.get(i)[1] - col == previousMoves.get(i)[0] - row) {
                on_same_line = previousMoves.get(i);
                orientation = 3;
                break;
            }
        }
        if (orientation == 0){
            return false;
        }
        // Checking that this move captures at least one opponent disc and is contiguous with the other discs.
        else{
            return board.capturesDisc(turn, orientation, row, col, on_same_line);
        }
    }

    private int[] getBestMove(Board board, ArrayList<int[]> legalmoves){
        int[] bestMove = legalmoves.get(0);



        return bestMove;
    }

}
