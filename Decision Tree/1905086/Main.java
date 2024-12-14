import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

public class Main {
    public static final int CLASS_VAL = 6;

    private static double standardDeviation(double mean, List<Double> testDataList) {
        double sdev = 0.0;
        for (double data: testDataList) {
            sdev += (data - mean)*(data - mean);
        }
        sdev = sdev / testDataList.size();
        sdev = Math.sqrt(sdev);
        return sdev;
    }

    public static void main(String[] args) {
        String filePath = "src/car.data";
        List<String> allExamples = new ArrayList<>();
        List<List<String>> dataSet = new ArrayList<>();
        List<List<String>> testSet = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                allExamples.add(line);
            }
            double total = 0.0;
            List<Double> testDataList = new ArrayList<>();
            for (int itr=0; itr<20; itr++) {
                Collections.shuffle(allExamples);
                dataSet.clear();
                testSet.clear();
                int dataSetSize = (int) (allExamples.size() * 0.8);
                int i = 0;
                for (String item : allExamples) {
                    String[] parts = item.split(",");
                    List<String> dataList = new ArrayList<>(Arrays.asList(parts));
                    if (i < dataSetSize) {
                        dataSet.add(dataList);
                    } else {
                        testSet.add(dataList);
                    }
                    i++;
                }
                Set<Integer> attributes = new HashSet<>();
                for (int j = 0; j <= 5; j++) {
                    attributes.add(j);
                }
                Attribute head = new Attribute(-1);
                head.setExamples(dataSet);
                DecisionTreeLearning dtl = new DecisionTreeLearning(head);
                head = dtl.decisionTreeLearning(attributes, head, null, "");
                System.out.println("Current root: " + head.getAttrIdx());
                DFS dfs = new DFS();
                int accuracy = 0;
                for (List<String> testData : testSet) {
                    if (testData.get(CLASS_VAL).equalsIgnoreCase(dfs.traverseDecisionTree(head, testData))) {
                        accuracy++;
                    }
                }
                System.out.println("Tests: " + testSet.size());
                System.out.println("Accurate Results: " + accuracy);
                System.out.println("Accuracy Percentage: " + (accuracy * 100.0) / testSet.size());
                total += ((accuracy * 100.0) / testSet.size());
                testDataList.add((accuracy * 100.0) / testSet.size());
            }
            System.out.println("\nMean accuracy: " + (double)(total / 20));
            System.out.println("Standard Deviation: " + standardDeviation((double)total/20, testDataList));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
