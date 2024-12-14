import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Map<String, Integer> bestValue = new HashMap<>();
        bestValue.put("G1", 12078);
        bestValue.put("G2", 12084);
        bestValue.put("G3", 12077);
        bestValue.put("G11", 627);
        bestValue.put("G12", 621);
        bestValue.put("G13", 645);
        bestValue.put("G14", 3187);
        bestValue.put("G15", 3169);
        bestValue.put("G16", 3171);
        bestValue.put("G22", 14123);
        bestValue.put("G23", 14129);
        bestValue.put("G24", 14131);
        bestValue.put("G32", 1560);
        bestValue.put("G33", 1537);
        bestValue.put("G34", 1541);
        bestValue.put("G35", 8000);
        bestValue.put("G36", 7996);
        bestValue.put("G37", 8009);
        bestValue.put("G43", 7027);
        bestValue.put("G44", 7022);
        bestValue.put("G45", 7020);
        bestValue.put("G48", 6000);
        bestValue.put("G49", 6000);
        bestValue.put("G50", 5988);
        try {
            File file = new File("src/Test-Cases/");
            File[] files = file.listFiles();
            FileWriter fileOut = new FileWriter("src/report.csv", true);

            for (File testCase: files) {
                Scanner fileScanner = new Scanner(new File(testCase.getAbsolutePath()));
                int V = fileScanner.nextInt();
                int E = fileScanner.nextInt();
                Graph graph = new Graph(V,E);

                for (int i = 0; i < E; i++) {
                    int u = fileScanner.nextInt();
                    int v = fileScanner.nextInt();
                    int weight = fileScanner.nextInt();
                    graph.addEdge(u, v, weight);
                }

                RandomizedAlgorithm gg = new RandomizedAlgorithm(graph);
                int count = 20, maxCutSum = 0;
                for (int i=1; i<=count; i++) {
                    gg.algorithm();
                    maxCutSum += gg.getMaxCut();
                    gg.reset();
                }

                String fileName = testCase.getName().substring(0, testCase.getName().indexOf(".rud")).toUpperCase();
                System.out.println("Running test case: " + fileName);

                fileOut.write(fileName + "," + V + "," + E + "," + (maxCutSum/count) + ",");

                GreedyAlgorithm greedy = new GreedyAlgorithm(graph, "simple-greedy");
                greedy.algorithm();
                fileOut.write(greedy.getMaxCut() + ",");
                greedy.reset();

                maxCutSum = 0;
                Algorithm semiGreedy = new GreedyAlgorithm(graph, "semi-greedy");
                for (int i=1; i<=count; i++) {
                    semiGreedy.algorithm();
                    maxCutSum += semiGreedy.getMaxCut();
                    semiGreedy.reset();
                }
                fileOut.write((maxCutSum/count)+",");

                Grasp grasp = new Grasp(graph,semiGreedy);
                int maxItr = 120;
                int maxValSemi = grasp.grasp(maxItr);

                fileOut.write(grasp.getAvgLocalSearches()+","+grasp.getBestVal()+",");

                Algorithm randomized = new RandomizedAlgorithm(graph);
                Grasp grasp_n = new Grasp(graph,randomized);
                int maxValRandomized = grasp_n.grasp(maxItr);

                fileOut.write(grasp_n.getAvgLocalSearches()+","+grasp_n.getBestVal()+",");
                fileOut.write(maxItr+","+maxValSemi+","+maxItr+","+maxValRandomized+",");

                if(bestValue.containsKey(fileName)){
                    fileOut.write(bestValue.get(fileName)+"\n");
                }
                else{
                    fileOut.write("Not Given\n");
                }
                fileScanner.close();
            }
            fileOut.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        } catch (IOException ioException) {
            ioException.printStackTrace();
            System.out.println("File writer could not open file");
        }
    }
}