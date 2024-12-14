import java.util.HashSet;
import java.util.List;
import java.util.Random;

public class RandomizedAlgorithm implements Algorithm{
    private Graph graph;
    private HashSet<Integer> setX;
    private HashSet<Integer> setY;
    private HashSet<Integer> remVertex;
    private int[] sigmaX;
    private int[] sigmaY;
    RandomizedAlgorithm(Graph graph){
        this.graph = graph;
        this.sigmaX = new int[10000];
        this.sigmaY = new int[10000];
        this.setX = new HashSet<>();
        this.setY = new HashSet<>();
        this.remVertex = new HashSet<>();
    }
    private int generateRandomNo(int idx){
        Random random = new Random();
        return random.nextInt(idx);
    }

    public void algorithm(){
        for (int v: graph.getVertex()) {
            remVertex.add(v);
        }
        for(int v: graph.getVertex()){
            int ran = generateRandomNo(50);
            if((ran%2)==0){
                setX.add(v);
                List<Edge> adjX1 = graph.getAdjacencyList()[v];
                for(Edge x: adjX1){
                    sigmaY[x.v]+= x.weight;
                }
            }
            else{
                setY.add(v);
                List<Edge> adjY1 = graph.getAdjacencyList()[v];
                for(Edge y: adjY1){
                    sigmaX[y.v]+= y.weight;
                }
            }
            remVertex.remove(v);
        }
    }
    public void reset(){
        setX.clear();
        setY.clear();
        remVertex.clear();
        for(int i = 0; i < sigmaX.length; i++){
            sigmaX[i] = 0;
        }
        for(int i = 0; i < sigmaY.length; i++){
            sigmaY[i] = 0;
        }
    }
    public int getMaxCut(){
        int sum = 0;
        for (int n: setX){
            sum += sigmaX[n];
        }
        return sum;
    }

    public HashSet<Integer> getSetX() {
        return setX;
    }

    public HashSet<Integer> getSetY() {
        return setY;
    }

    public int[] getSigmaX() {
        return sigmaX;
    }

    public int[] getSigmaY() {
        return sigmaY;
    }
}
