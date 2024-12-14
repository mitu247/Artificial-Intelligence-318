import org.w3c.dom.Attr;

import java.util.ArrayList;
import java.util.List;

public class Attribute {
    private int attrIdx;
    private List<Attribute> childList;
    private List<List<String>> examples;
    private String result;
    private String selectedBranch;

    public Attribute(int attrIdx) {
        this.attrIdx = attrIdx;
        this.childList = new ArrayList<Attribute>();
        this.examples = new ArrayList<>();
        this.result = "";
        this.selectedBranch = "";
    }

    public void addChild(Attribute child) {
        this.childList.add(child);
    }

    public int getAttrIdx() {
        return this.attrIdx;
    }

    public void setExamples(List<List<String>> examples) {
//        for (List<String> exc : examples) {
//            List<String> line = new ArrayList<>(exc);
//            this.examples.add(line);
//        }
        this.examples = examples;
    }

    public List<List<String>> getExamples() {
        return this.examples;
    }

    public void setAttrIdx(int attrIdx) {
        this.attrIdx = attrIdx;
    }

    public List<Attribute> getChildList() {
        return this.childList;
    }

    public String getResult() {
        return this.result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getSelectedBranch() {
        return selectedBranch;
    }

    public void setSelectedBranch(String selectedBranch) {
        this.selectedBranch = selectedBranch;
    }
}
