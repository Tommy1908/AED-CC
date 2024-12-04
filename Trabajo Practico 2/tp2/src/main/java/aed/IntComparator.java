package aed;

public class IntComparator implements Comparator<Integer> {

    @Override
    public int compare(Integer a, Integer b) {
        return a-b;
    }
}
