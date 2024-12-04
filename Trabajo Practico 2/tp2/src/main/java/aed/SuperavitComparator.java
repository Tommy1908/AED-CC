package aed;

public class SuperavitComparator implements Comparator<Tuple<Integer, Integer>> {
    @Override
    public int compare(Tuple<Integer, Integer> a, Tuple<Integer, Integer> b) {
        int diff = a.second - b.second;
        if (diff == 0) {
            diff = b.first - a.first;
        }
        return diff;
    }
}
