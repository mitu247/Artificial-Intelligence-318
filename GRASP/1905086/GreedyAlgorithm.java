import java.util.*;

public class GreedyAlgorithm implements Algorithm{
private Graph graph;
private List<Edge> RCLedgeList;
private List<Integer> RCLnodeList;
private HashSet<Integer> setX;
private HashSet<Integer> setY;
private HashSet<Integer> remVertex;
private int[] sigmaX;
private int[] sigmaY;
private double alpha;
private String algorithm;

public GreedyAlgorithm(Graph graph, String algorithm){
    this.graph = graph;
    this.algorithm = algorithm;
    this.RCLedgeList = new ArrayList<>();
    this.RCLnodeList = new ArrayList<>();
    this.sigmaX = new int[10000];
    this.sigmaY = new int[10000];
    this.setX = new HashSet<>();
    this.setY = new HashSet<>();
    this.remVertex = new HashSet<>();
}
private double getAlpha(){
    Random random = new Random();
    return random.nextDouble();
}
private int calculateMiu(double alpha){
    double val = graph.getMinWeightedEdge().weight + alpha*(graph.getMaxWeightedEdge().weight-graph.getMinWeightedEdge().weight);
    return (int) Math.round(val);
}
private void RCLedgeList(){
    int miu = calculateMiu(this.alpha);
    List<Edge> edges = graph.getEdges();
    for (Edge edge : edges) {
            if (edge.weight >= miu) {
                RCLedgeList.add(edge);
            }
        }
    }
    private int generateRandomIdx(int idx){
        Random random = new Random();
        return random.nextInt(idx);
    }

    public void algorithm(){
        if (this.algorithm.equalsIgnoreCase("simple-greedy")) this.alpha = 1;
        else if (this.algorithm.equalsIgnoreCase("semi-greedy")) this.alpha = getAlpha();
        RCLedgeList();
        int ran = generateRandomIdx(RCLedgeList.size());
        Edge sel_edge = RCLedgeList.get(ran);
        setX.add(sel_edge.u);
        List<Edge> adjX = graph.getAdjacencyList()[sel_edge.u];
        for(Edge x: adjX){
            sigmaY[x.v]+= x.weight;
        }
        setY.add(sel_edge.v);
        List<Edge> adjY = graph.getAdjacencyList()[sel_edge.v];
        for(Edge y: adjY){
            sigmaX[y.v]+= y.weight;
        }
        for (int v: graph.getVertex()) {
            remVertex.add(v);
        }
        remVertex.remove(sel_edge.u);
        remVertex.remove(sel_edge.v);
        int maxSigmaX,maxSigmaY,minSigmaX,minSigmaY;
        while(setX.size()+setY.size() != graph.getVertex().size()){
            maxSigmaX = maxSigmaY = Integer.MIN_VALUE;
            minSigmaX = minSigmaY = Integer.MAX_VALUE;
            for(int v: remVertex){
                maxSigmaX = Math.max(maxSigmaX,sigmaX[v]);
                maxSigmaY = Math.max(maxSigmaY,sigmaY[v]);
                minSigmaX = Math.min(minSigmaX,sigmaX[v]);
                minSigmaY = Math.min(minSigmaY,sigmaY[v]);
            }
            int w_max = Math.max(maxSigmaX,maxSigmaY);
            int w_min = Math.min(minSigmaX,minSigmaY);
            double miu = (w_min + alpha*(w_max-w_min));
            RCLnodeList.clear();
            for(int v: remVertex){
                if(Math.max(sigmaX[v],sigmaY[v])>=miu){
                    RCLnodeList.add(v);
                }
            }
            int ran_idx = generateRandomIdx(RCLnodeList.size());
            ran = RCLnodeList.get(ran_idx);
            if(sigmaX[ran] > sigmaY[ran]){
                setX.add(ran);
                List<Edge> adjX1 = graph.getAdjacencyList()[ran];
                for(Edge x: adjX1){
                    sigmaY[x.v]+= x.weight;
                }
            }
            else {
                setY.add(ran);
                List<Edge> adjY1 = graph.getAdjacencyList()[ran];
                for(Edge y: adjY1){
                    sigmaX[y.v]+= y.weight;
                }
            }
            remVertex.remove(ran);
        }

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

        public int getMaxCut() {
        int sum = 0;
        for (int n: setX){
            sum += sigmaX[n];
        }
        return sum;
    }

    public void reset(){
        setX.clear();
        setY.clear();
        remVertex.clear();
        RCLnodeList.clear();
        RCLedgeList.clear();
        for(int i = 0; i < sigmaX.length; i++){
            sigmaX[i] = 0;
        }
        for(int i = 0; i < sigmaY.length; i++){
            sigmaY[i] = 0;
        }
    }
}
