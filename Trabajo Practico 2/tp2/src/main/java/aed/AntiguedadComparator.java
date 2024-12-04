package aed;

public class AntiguedadComparator implements Comparator<Traslado> {

    @Override
    public int compare(Traslado t1, Traslado t2) {
        int diff = t2.timestamp - t1.timestamp;
        return diff;

    }
}