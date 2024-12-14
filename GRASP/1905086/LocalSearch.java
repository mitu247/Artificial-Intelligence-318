import java.util.HashSet;
import java.util.List;

public class LocalSearch{
private Graph graph;
private HashSet<Integer> setX;
private HashSet<Integer> setY;
private HashSet<Integer> vertex;
private int[] sigmaX;
private int[] sigmaY;
private int count;
LocalSearch(Graph graph,HashSet<Integer>setX,HashSet<Integer>setY,int[] sigmaX, int[] sigmaY){
    this.graph = graph;
    this.setX = setX;
    this.setY = setY;
    this.vertex = new HashSet<>();
    for (int v: graph.getVertex()) {
        vertex.add(v);
    }
    this.sigmaX = sigmaX;
    this.sigmaY = sigmaY;
}
int localSearch(){
    boolean change = true;
    count = 0;

    while(change){
        this.count++;
        change = false;
        for(int v: vertex){
           if(setX.contains(v) && sigmaY[v]>sigmaX[v]){
               change = true;
               setX.remove(v);
               List<Edge> adjX = graph.getAdjacencyList()[v];
               for(Edge x: adjX){
                   sigmaY[x.v] -= x.weight;
                   sigmaX[x.v] += x.weight;
               }
               setY.add(v);
           }
           else if(setY.contains(v) && sigmaX[v]>sigmaY[v]){
               change = true;
               setY.remove(v);
               List<Edge> adjY = graph.getAdjacencyList()[v];
               for(Edge y: adjY){
                   sigmaX[y.v] -= y.weight;
                   sigmaY[y.v] += y.weight;
               }
               setX.add(v);
           }
        }
    }
    int sum = 0;
    for (int n: setX){
        sum += sigmaX[n];
    }
    return sum;
}
public int getCount(){
    return count;
}
}
