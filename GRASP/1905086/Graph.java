import java.util.*;

public class Graph {
    private int V;
    private int E;
    private List<Edge> edges;
    private Set<Integer> vertex;
    private HashMap<String,Integer> edgeMap;
    private List<Edge>[] adjacencyList;

    public Graph(int V,int E) {
        this.V = V;
        this.E = E;
        adjacencyList = new ArrayList[V + 1];
        edges = new ArrayList<>();
        vertex = new HashSet<>();
        edgeMap = new HashMap<>();
        for (int i = 0; i <= V; i++) {
            adjacencyList[i] = new ArrayList<>();
        }
    }

    public void addEdge(int u, int v, int weight) {
        edges.add(new Edge(u, v, weight));
        vertex.add(u);
        vertex.add(v);
        adjacencyList[u].add(new Edge(v, weight));
        adjacencyList[v].add(new Edge(u, weight));
    }

    public void printGraph() {
        for (Edge edge : edges) {
            System.out.println("Edge (" + edge.u + " - " + edge.v + ") Weight: " + edge.weight);
        }
    }
    public Edge getMaxWeightedEdge() {
        Edge maxWeightedEdge = null;
        int maxWeight = Integer.MIN_VALUE;

        for (Edge edge : edges) {
            if (edge.weight > maxWeight) {
                maxWeight = edge.weight;
                maxWeightedEdge = edge;
            }
        }
        return maxWeightedEdge;
    }
    public Edge getMinWeightedEdge() {
        Edge minWeightedEdge = null;
        int minWeight = Integer.MAX_VALUE;

        for (Edge edge : edges) {
            if (edge.weight < minWeight) {
                minWeight = edge.weight;
                minWeightedEdge = edge;
            }
        }
        return minWeightedEdge;
    }

    public List<Edge> getEdges() {
        return edges;
    }

    public List<Edge>[] getAdjacencyList() {
        return adjacencyList;
    }

    public Set<Integer> getVertex() {
        return vertex;
    }
//    public int getWeight(String obj){
//        if(edgeMap.containsKey(obj)){
//            return edgeMap.get(obj);
//        }
//        return -1;
//    }
}


