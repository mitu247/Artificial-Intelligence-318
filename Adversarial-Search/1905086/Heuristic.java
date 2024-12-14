import java.util.List;

public class Heuristic {
    private Player player1;
    private Player player2;
    private List<Integer> weights;

    public Heuristic(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;
    }
    public int heuristic1(){
        int w1 = this.weights.get(0);
        return w1*(player1.getStorageStone()-player2.getStorageStone());
    }
    public int heuristic2(){
        int w2 = this.weights.get(1);
        return this.heuristic1() + w2*(player1.getNoStones()-player2.getNoStones());
    }
    public int heuristic3(){
        int w3 = this.weights.get(2);
        return this.heuristic2() + w3*(player1.getAdditionalMoves()-player2.getAdditionalMoves());
    }
    public int heuristic4(){
        int w4 = this.weights.get(3);
        //System.out.println(player1.getNoCaptured()+" "+player2.getNoCaptured());
        //System.out.println(player1.getAdditionalMoves()+" "+player2.getAdditionalMoves());
        return this.heuristic3() + w4*(player1.getNoCaptured()-player2.getNoCaptured());
    }
    public int getHeuristicValue(int hNo, List<Integer> weights) {
        this.weights = weights;

        if (hNo == 1) return heuristic1();
        else if (hNo == 2) return heuristic2();
        else if (hNo == 3) return heuristic3();
        else return heuristic4();

//        return 0;
    }
}
