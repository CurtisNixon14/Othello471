import java.util.ArrayList;
import java.util.Scanner;
import java.lang.Character;

public class Player {

    private static int[][] adjacent = new int[][] {{1,0}, {1,1}, {0,1}, {-1,1}, {-1,0}, {-1,-1}, {0,-1}, {1,-1}};

    private int turn; // Black goes first.
    private int opponent;

    public Player(int turn, int opp){
        this.turn = turn;
        opponent = opp;
    }

    public Board makeMove(Board currentState) throws Exception {
        // Getting a list of all legal moves.
        ArrayList<String> legalMoves = getLegalMoves(currentState);
        if (legalMoves.size() == 0) {
            throw new Exception();  // If no legal moves are available, throws an Exception to warn Othello471.Game.
        }

        System.out.print("Legal moves available: ");
        for (String m : legalMoves){
            System.out.print("[" + m.charAt(m.length()-2) + "," + m.charAt(m.length()-1) + "] ");
        }

        Scanner player_input = new Scanner(System.in);
        int[] move;

        while(true) {
            System.out.print("\nEnter your next move: ");
            String move_string = player_input.next();
            move = new int[] {Character.getNumericValue(move_string.charAt(0)),
                    Character.getNumericValue(move_string.charAt(1)), 0};
            for (String m : legalMoves){
                if (Character.getNumericValue(m.charAt(m.length()-2)) == move[0]
                        && Character.getNumericValue(m.charAt(m.length()-1)) == move[1]){
                    return updateBoard(currentState, m, legalMoves);
                }
            }
        }
    }

    public Board nextMove(Board currentState) throws Exception {
        // Getting a list of all legal moves.
        ArrayList<String> legalMoves = getLegalMoves(currentState);
        if (legalMoves.size() == 0) {
            throw new Exception();  // If no legal moves are available, throws an Exception to warn Othello471.Game.
        }
        // Getting the best move.
        String bestMove = getBestMove(currentState, legalMoves);
        System.out.println("AI: [" + bestMove.charAt(bestMove.length()-2) + ","
                + bestMove.charAt(bestMove.length()-1) + "] ");
        // Making a move.
        return updateBoard(currentState, bestMove, legalMoves);
    }

    // Places a disc onto the board and flips any captured discs.
    private Board updateBoard(Board prevState, String move_string, ArrayList<String> legalMoves){
        Board currentState = new Board(prevState);
        int row;
        int column;
        String position = move_string.substring(move_string.length()-2);

        for (String thisMove : legalMoves){
            // Find any move-strings with the same endpoint as specified in String position.
            if (position.charAt(0) == thisMove.charAt(thisMove.length()-2)
                && position.charAt(1) == thisMove.charAt(thisMove.length()-1)) {
                // If any such move-strings exist, execute them.
                for (int i = 0; i < thisMove.length(); i+=2){
                    row = Character.getNumericValue(thisMove.charAt(i));
                    column = Character.getNumericValue(thisMove.charAt(i+1));
                    currentState.mark(turn, row, column);
                }
            }
        }

        return currentState;
    }

    // Get a list of all legal moves.
    private ArrayList<String> getLegalMoves(Board board){
        ArrayList<String> list_legalmoves = new ArrayList<>();
        ArrayList<int[]> all_your_discs = board.findPositions(turn);
        StringBuilder move;

        // Using each of the player's current discs' positions, find all possible moves.
        for (int[] possible : all_your_discs){
            int row = possible[0];
            int column = possible[1];

            // Checking all adjacent positions for an opponent disc.
            for (int[] adj : adjacent){
                move = new StringBuilder(); //Initializing the move-string.
                int mod_row = row + adj[0];
                int mod_col = column + adj[1];

                // If there is an adjacent opponent disc, add its position to the move-string.
                if (board.inbounds(mod_row, mod_col)
                        && board.at(mod_row, mod_col) == opponent){
                    move.append(mod_row);
                    move.append(mod_col);

                    // Continue in the direction of the adjacent opponent disc.
                    while (board.inbounds(mod_row+adj[0], mod_col+adj[1])){
                        mod_row += adj[0];
                        mod_col += adj[1];
                        // Keep appending moves to the move-string as long as opponent discs are encountered.
                        if (board.at(mod_row, mod_col) == opponent){
                            move.append(mod_row);
                            move.append(mod_col);
                        }
                        // If an empty square is reached, terminate the move-string.
                        else if (board.at(mod_row, mod_col) == 0){
                            move.append(mod_row);
                            move.append(mod_col);
                            // Ignore copies of move-strings that are already in list_legalmoves.
                            if (!list_legalmoves.contains(move.toString())){
                                list_legalmoves.add(move.toString());
                            }
                            break;
                        }
                        else{
                            break;
                        }
                    }
                }
            }
        }

        return list_legalmoves;
    }

    // Conducts 3-ply alpha-beta pruning to get the next best move.
    private String getBestMove(Board current_board, ArrayList<String> legalmoves){
        String bestMove = legalmoves.get(0);
        int best_max = -1;  // One below the lowest possible value for MAX.
        int MAX = turn;  // Score will be determined by number of white discs on the board, AI goes second by default.

        // Checking MIN level nodes. Want to get the highest possible MAX here.
        for (String move1 : legalmoves){
            Board new_board = updateBoard(current_board, move1, legalmoves);
            ArrayList<String> legalmoves2 = getLegalMoves(new_board);
            int best_min2 = 65; // One above the highest possible value for MAX.

            // Checking MAX level nodes. Want to get the lowest possible MAX here.
            for (String move2 : legalmoves2){
                Board new_board2 = updateBoard(new_board, move2, legalmoves2);
                ArrayList<String> legalmoves3 = getLegalMoves(new_board2);
                int best_max3 = -1; // One below the lowest possible value for MAX.

                // Checking MIN level nodes. Want to get the highest possible MAX here.
                for (String move3 : legalmoves3){
                    Board new_board3 = updateBoard(new_board2, move3, legalmoves3);
                    new_board3.score(MAX);
                    // Pruning
                    if (new_board3.getScore() > best_min2){
                        break;
                    }
                    // Check if any of these moves offered a higher MAX than the current best MAX.
                    if (new_board3.getScore() > best_max3){
                        best_max3 = new_board3.getScore();
                    }
                }
                // Pruning
                if (best_min2 < best_max){
                    break;
                }
                // Check if any moves by MIN offered a lower MAX than the current best MIN.
                if (best_max3 < best_min2){
                    best_min2 = best_max3;
                }
            }

            // Check if any moves by MAX offered a higher MAX than the current best MAX.
            if (best_min2 > best_max){
                best_max = best_min2;
                bestMove = move1;
            }
        }
        return bestMove;
    }
}
