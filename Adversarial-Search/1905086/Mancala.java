import java.util.Arrays;

public class Mancala {
    public Board reallocateStones(Board board, int pitNo){
        int[] playerBoard = Arrays.copyOf(board.getBoard(), board.getBoard().length);
        int storage, opstorage, stones;

        Player player = board.getCurrentPlayer();

        storage = player.ispMode() ? 6 : 13;
        opstorage = player.ispMode() ? 13 : 6;

        stones = playerBoard[pitNo];

        playerBoard[pitNo] = 0;
        int x = pitNo;
        Player player1 = null,player2 = null;

        int nextPlayerNo = -1;

        while(stones>0) {
            ++pitNo;
            pitNo %= 14;
            if (pitNo != opstorage) {
                playerBoard[pitNo] += 1;
                stones -= 1;
            }
        }

        boolean playerSide = pitNo < 6;
        boolean check = player.ispMode() == playerSide;

        if (player.ispMode()) nextPlayerNo = 2;
        else nextPlayerNo = 1;
        
        player1 = new Player(board.getPlayer1());
        player2 = new Player(board.getPlayer2());

        if(pitNo == storage){
            if (player.ispMode()) {
                player1.setAdditionalMoves(player.getAdditionalMoves() + 1);
                nextPlayerNo = 1;
            }
            else {
                player2.setAdditionalMoves(player.getAdditionalMoves() + 1);
                nextPlayerNo = 2;
            }
        }
        else if(pitNo!= x && check && playerBoard[pitNo] == 1){
            int collected = 0;
            if (playerBoard[12-pitNo] != 0) { // opponent player's pit is non-zero
                collected = playerBoard[pitNo] + playerBoard[12-pitNo];
                playerBoard[storage] += collected;
                playerBoard[12-pitNo] = 0;
                playerBoard[pitNo] = 0;
            }

            if (player.ispMode()) {
                player1.setNoCaptured(player.getNoCaptured() + collected);
            }
            else {
                player2.setNoCaptured(player.getNoCaptured() + collected);
            }
        }
        player1.setState(Arrays.copyOfRange(playerBoard, 0, 7));
        player2.setState(Arrays.copyOfRange(playerBoard,7,14));

        Board newBoard = new Board(player1,player2);
        newBoard.setPlayerNo(nextPlayerNo);
        newBoard.setBoard(playerBoard);

        return newBoard;
    }
}
