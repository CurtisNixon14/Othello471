package Othello471;

public class Main {

    public static void main(String[] args){

        runGame();
    }


    private static void runGame() {

        int p1 = 1;
        int p2 = 2;
        int turncount = 0;
        int turns_with_no_moves = 0;
        Board board = new Board(p1, p2);
        Player player = new Player(p1, p2);
        Player opponent = new Player(p2, p1);

        while(turns_with_no_moves < 2){
            turncount++;
            board.printBoard();
            try{
                if (turncount % 2 == 1){
                    System.out.println("\nPlayer 1's turn.");
                    board = player.makeMove(board);
                }
                else{
                    System.out.println("\nPlayer 2's turn.");
                    board = opponent.nextMove(board);
                }

                turns_with_no_moves = 0;
            }
            catch (Exception no_moves){
                turns_with_no_moves++;  // Game ends if player and opponent both cannot move.
                if (turncount % 2 == 1){
                    System.out.println("Player 1 cannot move.");
                }
                else{
                    System.out.println("Player 2 cannot move.");
                }

            }
        }

        System.out.println("\nGame is over.");
        int p1score = board.score(p1);
        int p2score = board.score(p2);
        System.out.println("Player 1 scored: " + p1score);
        System.out.println("Player 2 scored: " + p2score);
        if (p1score > p2score){
            System.out.println("Player 1 wins.");
        }
        else{
            System.out.println("Player 2 wins.");
        }
    }

}
