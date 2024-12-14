import java.util.HashSet;

public interface Algorithm {
    public int getMaxCut();
    public void reset();
    public void algorithm();
    public HashSet<Integer> getSetX();
    public HashSet<Integer> getSetY();
    public int[] getSigmaX();
    public int[] getSigmaY();
}
