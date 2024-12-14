import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

public class DecisionTreeLearning {
    public static final int CLASS_VAL = 6;

    private String finalResult;
    private DecisionTreeHelper decisionTreeHelper;

    public DecisionTreeLearning(Attribute head) {
        decisionTreeHelper = new DecisionTreeHelper();
    }

    private String pluralityValue(Attribute attribute) {
        int unacc = 0, acc = 0, good = 0, vgood = 0;

        String result = "";
        int maxVal = Integer.MIN_VALUE;
        for (List<String> list: attribute.getExamples()) {
            String resultTemp = list.get(CLASS_VAL);

            unacc = (resultTemp.equalsIgnoreCase("unacc")) ? unacc + 1 : unacc;
            if (maxVal < unacc) {
                result = "unacc";
                maxVal = unacc;
            }
            acc = (resultTemp.equalsIgnoreCase("acc")) ? acc + 1 : acc;
            if (maxVal < acc) {
                result = "acc";
                maxVal = acc;
            }
            good = (resultTemp.equalsIgnoreCase("good")) ? good + 1 : good;
            if (maxVal < good) {
                result = "good";
                maxVal = good;
            }
            vgood = (resultTemp.equalsIgnoreCase("vgood")) ? vgood + 1 : vgood;
            if (maxVal < vgood) {
                result = "vgood";
                maxVal = vgood;
            }
        }
        // system.out.println("Result: " + result);

        return result;
    }

    boolean isClassified(Attribute attribute) {
        String result = attribute.getExamples().get(0).get(CLASS_VAL);
        for (List<String> list: attribute.getExamples()) {
            if (!list.get(CLASS_VAL).equalsIgnoreCase(result))
                return false;
        }
        this.finalResult = result;
        return true;
    }

    public Attribute decisionTreeLearning(Set<Integer> attributes, Attribute currentAttr, Attribute parentAttr, String branch) {
        if (currentAttr.getExamples().size() == 0) {
            // system.out.println("No examples remaining");
            Attribute newAttr = new Attribute(-1);
            newAttr.setResult(pluralityValue(parentAttr));
            newAttr.setSelectedBranch(branch);
            return newAttr;
        }
        else if (isClassified(currentAttr)) {
            // system.out.println("Fully classified");
            Attribute newAttr = new Attribute(-1);
            newAttr.setResult(this.finalResult);
            this.finalResult = "";
            newAttr.setSelectedBranch(branch);
            return newAttr;
        }
        else if (attributes.size() == 0) {
            // system.out.println("No attributes remaining");
            Attribute newAttr = new Attribute(-1);
            newAttr.setResult(pluralityValue(currentAttr));
            newAttr.setSelectedBranch(branch);
            return newAttr;
        }
        else {
            Attribute nextAttribute = decisionTreeHelper.getImportantAttr(attributes, currentAttr.getExamples(), CLASS_VAL);
            nextAttribute.setSelectedBranch(branch);

            Set<Integer> newAttributeList = new HashSet<>(attributes);
            newAttributeList.remove(nextAttribute.getAttrIdx());

            for (String nextBranch: decisionTreeHelper.getMapForValues().get(nextAttribute.getAttrIdx())) {
                int attrIdx = nextAttribute.getAttrIdx();

                List<List<String>> truncatedExamples = new ArrayList<>();
                for (List<String> list: currentAttr.getExamples()) {
                    String attrVal = list.get(attrIdx);
                    if (attrVal.equalsIgnoreCase(nextBranch)) {
                        truncatedExamples.add(new ArrayList<>(list));
                    }
                }
                // system.out.println("Examples used in branch: " + nextBranch + " | " + truncatedExamples.size());
                nextAttribute.setExamples(truncatedExamples);
                Attribute childAttr = decisionTreeLearning(newAttributeList, nextAttribute, currentAttr, nextBranch);
                nextAttribute.addChild(childAttr);
            }
            return nextAttribute;
        }
    }
}
