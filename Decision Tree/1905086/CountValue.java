import java.util.HashMap;
import java.util.List;

public class CountValue {
    private HashMap<String,Integer> attrValCount;
    private HashMap<String,HashMap<String,Integer>> classValueCount;
     CountValue(){
        this.attrValCount = new HashMap<>();
        this.classValueCount = new HashMap<>();
    }

    public HashMap<String, Integer> getAttrValCount() {
        return attrValCount;
    }

    public HashMap<String, HashMap<String, Integer>> getClassValueCount() {
        return classValueCount;
    }

    public void setAttrValCount(int attrIdx,List<List<String>>examples) {
        attrValCount.clear();
        classValueCount.clear();
        for (int i = 0; i < examples.size(); i++) {
            String value = examples.get(i).get(attrIdx);
            if (attrValCount.containsKey(value) == false) {
                attrValCount.put(value, 1);
                classValueCount.put(value, new HashMap<>());
                String className = examples.get(i).get(6);
                if (classValueCount.get(value).containsKey(className) == false) {
                    classValueCount.get(value).put(className, 1);
                }
            } else {
                int cnt = attrValCount.get(value) + 1;
                attrValCount.put(examples.get(i).get(attrIdx), cnt);
                String className = examples.get(i).get(6);
                if (classValueCount.get(value).containsKey(className) == false) {
                    classValueCount.get(value).put(className, 1);
                } else {
                    int cnt1 = classValueCount.get(value).get(className) + 1;
                    classValueCount.get(value).put(className, cnt1);
                }
            }
        }
    }
}
