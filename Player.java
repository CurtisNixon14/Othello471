import java.util.ArrayList;

public class Player {

    private int turn; // Black goes first.

    public Player(int turn){
        this.turn = turn;
    }

    public Board nextMove(Board currentstate){
        // Getting a list of all legal moves.
        ArrayList<int[]> legalmoves = getLegalMoves(currentstate);
        // Getting the best move.
        int[] bestmove = getBestMove(currentstate, legalmoves);
        // Making a move.
        currentstate.board[bestmove[0]][bestmove[1]] = turn;
        return currentstate;
    }

    private ArrayList<int[]> getLegalMoves(Board board){
        ArrayList<int[]> list_legalmoves = new ArrayList<int[]>();

        for (int row = 0; row < board.board.length; row++) {
            for (int col = 0; col < board.board[0].length; col++) {
                if (this_move_captures(row, col)) {
                    list_legalmoves.add(new int[]{row, col});
                }
            }
        }
        return list_legalmoves;
    }

    private boolean this_move_captures(int row, int col){
        


        return false;
    }

    private int[] getBestMove(Board board, ArrayList<int[]> legalmoves){
        int[] bestmove = legalmoves.get(0);



        return bestmove;
    }

}
