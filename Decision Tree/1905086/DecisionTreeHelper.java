import java.util.*;

public class DecisionTreeHelper {
    private Attribute importantAttr;
    private HashMap<Integer, List<String>> mapForValues;
    DecisionTreeHelper(){
        this.mapForValues = new HashMap<>();
        mapForValues.put(0, new ArrayList<>(Arrays.asList("vhigh", "high", "med", "low")));
        mapForValues.put(1, new ArrayList<>(Arrays.asList("vhigh", "high", "med", "low")));
        mapForValues.put(2, new ArrayList<>(Arrays.asList("2", "3", "4", "5more")));
        mapForValues.put(3, new ArrayList<>(Arrays.asList("2", "4", "more")));
        mapForValues.put(4, new ArrayList<>(Arrays.asList("small", "med", "big")));
        mapForValues.put(5, new ArrayList<>(Arrays.asList("low", "med", "high")));
    }
    public double logAny(double a) {
        return Math.log(a) / Math.log(4);
    }
    public double getEntropy(List<List<String>> examples, int classIdx){
        int unacc = 0, acc = 0, good = 0, vgood = 0;
        double entropy = 0.0;
        double denominator = examples.size();
        String classValue;
        for (List<String> v : examples) {
            classValue = v.get(classIdx);
            if (classValue.equals("unacc")) unacc++;
            else if (classValue.equals("acc")) acc++;
            else if (classValue.equals("good")) good++;
            else if (classValue.equals("vgood")) vgood++;
        }
        if (unacc > 0) entropy += (1.0*unacc / denominator) * logAny(1.0*denominator / unacc);
        if (acc > 0) entropy += (1.0*acc / denominator) * logAny(1.0*denominator / acc);
        if (good > 0) entropy += (1.0*good / denominator) * logAny(1.0*denominator / good);
        if (vgood > 0) entropy += (1.0*vgood / denominator) * logAny(1.0*denominator / vgood);
      return entropy;
    }
    public double getRemainder(List<List<String>> examples, int attrIdx) {
        int unacc = 0, acc = 0, good = 0, vgood = 0;
        double entropy = 0.0;
        double remainder = 0.0;
        double denominator = examples.size();

        CountValue cc = new CountValue();
        cc.setAttrValCount(attrIdx,examples);
        HashMap<String,Integer> attrValCount = cc.getAttrValCount();
        HashMap<String,HashMap<String,Integer>> classCount = cc.getClassValueCount();

        for (Map.Entry<String, HashMap<String, Integer>> entry : classCount.entrySet()) {
            String className = entry.getKey();
            HashMap<String, Integer> innerMap = entry.getValue();

            double wt = 1.0 * attrValCount.get(className) / denominator;

            for (Map.Entry<String, Integer> innerEntry : innerMap.entrySet()) {
                String innerKey = innerEntry.getKey();
                Integer value = innerEntry.getValue();
                if(innerKey.equalsIgnoreCase("acc")){
                    acc = value;
                }
                else if(innerKey.equalsIgnoreCase("unacc")){
                    unacc = value;
                }
                else if(innerKey.equalsIgnoreCase("good")){
                    good = value;
                }
                else if(innerKey.equalsIgnoreCase("vgood")){
                    vgood = value;
                }
            }
            int totalClassCount = unacc + acc + good + vgood;
            if (unacc > 0) entropy += (1.0*unacc / totalClassCount)* logAny(1.0*totalClassCount / unacc);
            if (acc > 0) entropy += (1.0*acc / totalClassCount) * logAny(1.0*totalClassCount / acc);
            if (good > 0) entropy += (1.0*good / totalClassCount) * logAny(1.0*totalClassCount / good);
            if (vgood > 0) entropy += (1.0*vgood / totalClassCount) * logAny(1.0*totalClassCount / vgood);
            remainder += wt*entropy;
            acc = unacc = vgood = good = 0;
            entropy = 0.0;
        }
        return remainder;
    }

    public Attribute getImportantAttr(Set<Integer>attributes,List<List<String>> examples,int classIdx){
        int maxGainAttr = -1;
        double maxGain = Double.NEGATIVE_INFINITY;
        double parentEntropy = getEntropy(examples, classIdx);

        for (int attr : attributes) {
            double nodeRemainder = getRemainder(examples, attr);
            double gain = parentEntropy - nodeRemainder;

            if (gain > maxGain) {
                maxGain = gain;
                maxGainAttr = attr;
            }
        }

        if (maxGainAttr != -1) {
            this.importantAttr = new Attribute(maxGainAttr);
        } else {
            this.importantAttr = null;
        }
        return this.importantAttr;
    }

    public HashMap<Integer, List<String>> getMapForValues() {
        return this.mapForValues;
    }
}
