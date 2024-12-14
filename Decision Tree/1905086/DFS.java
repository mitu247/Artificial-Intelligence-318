import java.util.List;

public class DFS {
    DFS(){

    }
    public String traverseDecisionTree(Attribute root,List<String> testSet){
            if (!root.getResult().isEmpty()) {
                return root.getResult();
            }
            int selAttr = root.getAttrIdx();
            String testValueOfAttr = testSet.get(selAttr);

            for (Attribute attr : root.getChildList()){
                if (attr.getSelectedBranch().equalsIgnoreCase(testValueOfAttr)) {
                    return traverseDecisionTree(attr,testSet);
                }
            }
            return root.getResult();

    }
}
