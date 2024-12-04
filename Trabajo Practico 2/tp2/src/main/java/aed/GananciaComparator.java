package aed;

public class GananciaComparator implements Comparator<Traslado> {

    @Override
    public int compare(Traslado t1, Traslado t2){
        int diff = t1.gananciaNeta - t2.gananciaNeta;
        if (diff == 0) {
            diff = t2.id - t1.id;
        }
        return diff;
    }
}
