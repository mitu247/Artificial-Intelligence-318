public class Grasp {
    private Algorithm gg;
    private LocalSearch ls;
    private Graph graph;
    private int totalAvgLocalSearches;
    private int sum;
    Grasp(Graph graph, Algorithm gg){
        this.gg = gg;
        this.graph = graph;
        this.ls = new LocalSearch(graph,gg.getSetX(),gg.getSetY(),gg.getSigmaX(),gg.getSigmaY());
    }

    int grasp(int maxItr){
     int maxValue = Integer.MIN_VALUE,val;
     this.totalAvgLocalSearches = 0;
     this.sum = 0;
     for(int i = 0; i<maxItr; i++){
         gg.algorithm();
         val = this.ls.localSearch();
         totalAvgLocalSearches += this.ls.getCount();
         sum+=val;
         maxValue = Math.max(maxValue,val);
         gg.reset();
     }
     this.totalAvgLocalSearches = this.totalAvgLocalSearches/maxItr;
     this.sum = this.sum/maxItr;
     return maxValue;
    }
    public int getAvgLocalSearches(){
        return this.totalAvgLocalSearches;
    }
    public int getBestVal(){
        return this.sum;
    }
}
