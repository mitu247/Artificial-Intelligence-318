import java.util.*;

public class GameSimulation {
    private Mancala mancala;
    private boolean print_game;
    private boolean humanMode;
    private int hNo, hno1, hno2;
    private int winningPlayerNo;
    List<Integer> weights;

    public GameSimulation(boolean print_game) {
        this.print_game = print_game;
        this.mancala = new Mancala();
        this.humanMode = false;
        this.hNo = 1; // default
        this.hno1 = this.hno2 = 1;
        this.winningPlayerNo = -1;
        this. weights = new ArrayList<>();
        weights.add(1);
        weights.add(2);
        weights.add(3);
        weights.add(4);
    }

    private Helper MiniMaxDFS(int alpha, int beta, Board board, int depth) {
        if ((depth == 0) || (board.getCurrentPlayer().getNoStones() == 0)) {
            int value = new Heuristic(board.getPlayer1(), board.getPlayer2()).getHeuristicValue(this.hNo, this.weights);
            return new Helper(-1, value);
        }

        List<Integer> moves = new ArrayList<>();
        int[] boardArr = board.getBoard();
        if (board.getCurrentPlayer().ispMode()) {
            for (int i=0; i<=5; i++) {
                if (boardArr[i] != 0) moves.add(i); // finding the valid moves
            }
        }
        else {
            for (int i=7; i<=12; i++) {
                if (boardArr[i] != 0) moves.add(i);
            }
        }
        Collections.shuffle(moves);  // Changing move ordering

        if (board.getCurrentPlayer().ispMode()) {
            Helper bestH = new Helper(-1, Integer.MIN_VALUE);
            for (int pitNo: moves) {
                Board newBoard = mancala.reallocateStones(board, pitNo);

                Helper retH = MiniMaxDFS(alpha, beta, newBoard, depth - 1);

                if (retH.getValue() > bestH.getValue()) {
                    bestH.setValue(newBoard.getValue());
                    bestH.setMove(pitNo);
                }

                alpha = Math.max(alpha, retH.getValue());

                if (beta <= alpha)
                    break;
            }
            return bestH;
        }
        else {
            Helper bestH = new Helper(-1, Integer.MAX_VALUE);
            for (int pitNo: moves) {
                Board newBoard = mancala.reallocateStones(board, pitNo);

                Helper retH = MiniMaxDFS(alpha, beta, newBoard, depth - 1);

                if (retH.getValue() < bestH.getValue()) {
                    bestH.setValue(newBoard.getValue());
                    bestH.setMove(pitNo);
                }

                beta = Math.min(beta, retH.getValue());

                if (beta <= alpha)
                    break;
            }
            return bestH;
        }
    }

    public void Play(Board board, int depth) {
        int negInf = Integer.MIN_VALUE, posInf = Integer.MAX_VALUE;

        if(board.getCurrentPlayer().ispMode()){
            this.hNo = this.hno1;
        }
        else{
            this.hNo = this.hno2;
        }
        Helper retH = MiniMaxDFS(negInf, posInf, board, depth);

        if (this.print_game) {
            System.out.println("---------------------------------");
            board.printBoard();
            System.out.println("Current Player: " + board.getCurrentPlayer().getpType() + "\nMove Idx\t  : " + retH.getMove() + "\n\n");
        }

        if (this.print_game && this.humanMode && board.getCurrentPlayer().ispMode()) {
            System.out.println("Suggested move: " + retH.getMove());
            System.out.print("Your move: ");
            Scanner scanner = new Scanner(System.in);
            retH.setMove(scanner.nextInt());
        }
        Board nextBoard = mancala.reallocateStones(board, retH.getMove());

        // Game Over
        if (nextBoard.getPlayer1().getNoStones()==0 || nextBoard.getPlayer2().getNoStones()==0) {
            int[] nextBoardArr = nextBoard.getBoard();

            if (nextBoard.getPlayer2().getNoStones() == 0) {
                for (int i=0; i<=5; i++) {
                    nextBoardArr[6] += nextBoardArr[i];
                    nextBoardArr[i] = 0;
                }

                nextBoard.getPlayer1().setState(Arrays.copyOfRange(nextBoardArr, 0, 7));
            }
            else if (nextBoard.getPlayer1().getNoStones() == 0) {
                for (int i = 7; i <= 12; i++) {
                    nextBoardArr[13] += nextBoardArr[i];
                    nextBoardArr[i] = 0;
                }

                nextBoard.getPlayer2().setState(Arrays.copyOfRange(nextBoardArr, 7, 14));
            }

            if (this.print_game) {
                System.out.println("\n\n|||||| Game simulation complete |||||||");
                nextBoard.printBoard();
            }
            if (nextBoard.getPlayer1().getStorageStone() > nextBoard.getPlayer2().getStorageStone()) {
                if (this.print_game) {
                    System.out.println("Player wins the game, score: " + nextBoardArr[6]);
                }
                this.winningPlayerNo = 1;
            }
            else if (nextBoard.getPlayer1().getStorageStone() < nextBoard.getPlayer2().getStorageStone()) {
                if (this.print_game) {
                    System.out.println("Computer wins the game, score: " + nextBoardArr[13]);
                }
                this.winningPlayerNo = 2;
            }
            else {
                if (this.print_game) {
                    System.out.println("Game Draw!!");
                }
                this.winningPlayerNo = -1;
            }
            return;
        }


        Play(nextBoard, depth);
    }

    public void start(Player player1, Player player2, String game, int depth, int heuristic1, int heuristic2) {
        Board initBoard = new Board(player1, player2);
        initBoard.setPlayerNo(1);  // setting current player, first to move
        initBoard.setBoard(new int[] {4, 4, 4, 4, 4, 4, 0, 4, 4, 4, 4, 4, 4, 0});

        this.hno1 = heuristic1;
        this.hno2 = heuristic2;

        if (game.equalsIgnoreCase("1")) {
            this.humanMode = false;
            this.Play(initBoard, depth);
        }
        else if (game.equalsIgnoreCase("2")) {
            this.humanMode = true;
            this.Play(initBoard, depth);
        }
        else {
            this.humanMode = false;
            this.print_game = false;
            int p1WinCount = 0, p2WinCount = 0, drawCount = 0;

            for (int i=0; i<100; i++) {
                this.Play(initBoard, depth);

                if (this.winningPlayerNo == 1) p1WinCount++;
                else if (this.winningPlayerNo == 2) p2WinCount++;
                else drawCount++;
            }

            System.out.println("Player winning count: " + p1WinCount);
            System.out.println("Computer winning count: " + p2WinCount);
            System.out.println("Draw Count: " + drawCount);
        }

    }
}
