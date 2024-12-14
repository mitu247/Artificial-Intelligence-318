public class Board {
    private Player player1;
    private Player player2;
    private int[] board;
    private Player currentPlayer;

    private int playerNo, value;
    public Board(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;
        this.value = 0;
    }
    public Player getCurrentPlayer(){
        if(playerNo == 1){
            return player1;
        }
        else {
            return player2;
        }
    }
    public void setPlayerNo(int playerNo) {
        this.playerNo = playerNo;
    }

    public int getValue() {
        return this.value;
    }

    public void setBoard(int[] board) {
        this.board = board;
    }
    
    public int[] getBoard() {
        return this.board;
    }
    
    public void printBoard() {
        int slot = 6;
        int st2 = slot+1;
        System.out.print("\t\t");
        for(int i=st2+slot-1; i>=st2; i--) System.out.print(board[i]+" ");
        System.out.println("");

        System.out.println(board[13]+"\t\t\t\t\t\t\t"+board[6]);

        int st1 = 0;
        System.out.print("\t\t");
        for(int i=st1; i<st1+slot; i++) System.out.print(board[i]+" ");
        System.out.println("");
    }

    public Player getPlayer1() {
        return this.player1;
    }

    public Player getPlayer2() {
        return this.player2;
    }
}
