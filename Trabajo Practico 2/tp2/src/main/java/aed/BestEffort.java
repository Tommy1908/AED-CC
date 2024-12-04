package aed;

import java.util.ArrayList;

// Las complejidades estan explicadas en el archivo con nombre: EXPLICACION_COMPLEJIDADES.txt

public class BestEffort {
    private Heap<Traslado> h_redituable, h_antiguedad;
    private Estadisticas stats;

    final private int I_REDITUABLE = 0;
    final private int I_ANTIGUEDAD = 1;

    public BestEffort(int cantCiudades, Traslado[] traslados) {
        ArrayList<Tuple<Traslado, Handle>> trasladosList = new ArrayList<Tuple<Traslado, Handle>>();

        // Cambio de variable de Traslado [] a un ArrayList<Traslado, Handle>
        for (int i = 0; i < traslados.length; i++) {
            Tuple<Traslado, Handle> traslado = new Tuple<Traslado, Handle>(traslados[i], new Handle(2));
            trasladosList.add(traslado);
        }

        h_redituable = new Heap<>(new GananciaComparator(), I_REDITUABLE, trasladosList);
        h_antiguedad = new Heap<>(new AntiguedadComparator(), I_ANTIGUEDAD, trasladosList);

        stats = new Estadisticas(cantCiudades);
    }

    public void registrarTraslados(Traslado[] traslados) {
        for (int i = 0; i < traslados.length; i++) {
            Tuple<Traslado, Handle> traslado = new Tuple<Traslado, Handle>(traslados[i], new Handle(2));
            h_redituable.encolar(traslado);
            h_antiguedad.encolar(traslado);
        }
    }

    public int[] despacharMasRedituables(int n) {
        int despachos = n;
        if (h_redituable.size() <= 0) {
            return new int[0];
        }
        if (despachos > h_redituable.size()) {
            despachos = h_redituable.size();
        }
        // Lo guardamos todo en un array list y despues hacemos la conversion a una
        // lista
        ArrayList<Integer> reslist = new ArrayList<Integer>(despachos);
        int[] res = new int[despachos];
        while (despachos > 0) {
            Tuple<Traslado, Handle> tupla_traslado = h_redituable.desencolarMax();
            Traslado t = tupla_traslado.first;
            Handle handle = tupla_traslado.second;
            h_antiguedad.eliminar(handle.handles.get(I_ANTIGUEDAD));

            stats.actualizarEstadisticas(t);

            reslist.add(t.id);
            despachos--;
        }
        for (int i = 0; i < n && h_redituable.size() != 0; i++) {
            res[i] = reslist.get(i);
        }
        stats.actualizarPromedio();
        return res;
    }

    public int[] despacharMasAntiguos(int n) {
        int despachos = n;
        if (h_antiguedad.size() <= 0) {
            return new int[0];
        }
        if (despachos > h_antiguedad.size()) {
            despachos = h_antiguedad.size();
        }
        // Lo guardamos todo en un array list y despues hacemos la conversion a una
        // lista
        ArrayList<Integer> reslist = new ArrayList<Integer>(despachos);
        int[] res = new int[despachos];
        while (despachos > 0) {
            Tuple<Traslado, Handle> tupla_traslado = h_antiguedad.desencolarMax();
            Traslado t = tupla_traslado.first;
            Handle handle = tupla_traslado.second;
            h_redituable.eliminar(handle.handles.get(I_REDITUABLE));

            stats.actualizarEstadisticas(t);

            reslist.add(t.id);
            despachos--;
        }
        for (int i = 0; i < n && h_antiguedad.size() != 0; i++) {
            res[i] = reslist.get(i);
        }
        stats.actualizarPromedio();
        return res;
    }

    public int ciudadConMayorSuperavit() {
        return stats.ciudadMayorSuperavit;
    }

    public ArrayList<Integer> ciudadesConMayorGanancia() {
        return stats.ganancias;
    }

    public ArrayList<Integer> ciudadesConMayorPerdida() {
        return stats.perdidas;
    }

    public int gananciaPromedioPorTraslado() {
        return stats.promedio;
    }
}