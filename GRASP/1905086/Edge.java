public class Edge {
    int u;
    int v;
    int weight;

    Edge(int u, int v, int weight) {
        this.u = u;
        this.v = v;
        this.weight = weight;
    }
    Edge(int endVertex, int weight) {
        this.v = endVertex;
        this.weight = weight;
    }
}