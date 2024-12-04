package aed;

import java.util.ArrayList;

public class Estadisticas {
    private ArrayList<Tuple<Integer, Integer>> infoCiudades; // Ganancia total, Perdida total
    public ArrayList<Integer> ganancias, perdidas; // Ciudades con Mayores Ganancias y Mayores Perdidas

    private Heap<Tuple<Integer, Integer>> h_superavit; // Tupla ciudad, superavit
    public ArrayList<Handle> handleSuperavit; // Handle en el superavit de las ciudades

    private int totalDespachados, gananciaMax, perdidaMax; // Variables Auxiliares
    public int total, promedio, ciudadMayorSuperavit;

    public Estadisticas(int cantCiudades) {
        infoCiudades = new ArrayList<Tuple<Integer, Integer>>(cantCiudades);
        ganancias = new ArrayList<Integer>(0);
        perdidas = new ArrayList<Integer>(0);

        handleSuperavit = new ArrayList<Handle>(cantCiudades);
        ArrayList<Tuple<Tuple<Integer, Integer>, Handle>> superavitList = new ArrayList<Tuple<Tuple<Integer, Integer>, Handle>>(
                cantCiudades);

        // Crea la lista de Ciudades con todo (0,0) de ganancia y perdida.
        for (int i = 0; i < cantCiudades; i++) {
            infoCiudades.add(new Tuple<Integer, Integer>(0, 0));
        }

        promedio = 0;
        totalDespachados = 0;
        gananciaMax = 0;
        perdidaMax = 0;
        total = 0;

        // Crea una lista de Tuplas (Id de Ciudad, valor inicial de superavit) y su
        // Handle
        // Luego se crea el Heap con dicha lista
        for (int i = 0; i < cantCiudades; i++) {
            handleSuperavit.add(new Handle(1));
            superavitList.add(new Tuple<Tuple<Integer, Integer>, Handle>(new Tuple<Integer, Integer>(i, 0),
                    handleSuperavit.get(i)));
        }
        h_superavit = new Heap<>(new SuperavitComparator(), 0, superavitList);
    }

    public void actualizarPromedio() {
        if (totalDespachados != 0) {
            promedio = total / totalDespachados;
        }
    }

    public void actualizarEstadisticas(Traslado t) {
        infoCiudades.get(t.origen).first += t.gananciaNeta;
        infoCiudades.get(t.destino).second += t.gananciaNeta;
        actualizarGanancia(t);
        actualizarPerdida(t);
        actualizarSuperavit(t.origen, t.destino);
        total += t.gananciaNeta;
        totalDespachados += 1;
    }

    // Compara la mayor ganancia registrada, si la suma de la ganancia de la ciudad recientemente despachada es mayor se borra la lista actual y se le agrega ese valor
    // Si es igual a la mayor ganancia, se le agrega a la lista
    private void actualizarGanancia(Traslado t) {
        int ganancia_origen = infoCiudades.get(t.origen).first;
        if (gananciaMax < ganancia_origen) {
            ganancias.clear();
            ganancias.add(t.origen);
            gananciaMax = ganancia_origen;
        } 
        else if (gananciaMax == ganancia_origen) {
            ganancias.add(t.origen);
        }
    }

    // Compara la mayor perdida registrada, si la suma de la perdida de la ciudad recientemente despachada es mayor se borra la lista actual y se le agrega ese valor
    // Si es igual a la mayor perdida, se le agrega a la lista
    private void actualizarPerdida(Traslado t) {
        int perdida_destino = infoCiudades.get(t.destino).second;
        if (perdidaMax < perdida_destino) {
            perdidas.clear();
            perdidas.add(t.destino);
            perdidaMax = perdida_destino;
        } 
        else if (perdidaMax == perdida_destino) {
            perdidas.add(t.destino);
        }
    }

    private void actualizarSuperavit(int origen, int destino) {
        int superavit_origen = infoCiudades.get(origen).first - infoCiudades.get(origen).second;
        int superavit_destino = infoCiudades.get(destino).first - infoCiudades.get(destino).second;
        int i_origen = handleSuperavit.get(origen).handles.get(0);
        int i_destino = handleSuperavit.get(destino).handles.get(0);

        h_superavit.modificar(i_origen, new Tuple<Integer, Integer>(origen, superavit_origen));
        h_superavit.modificar(i_destino, new Tuple<Integer, Integer>(destino, superavit_destino));

        ciudadMayorSuperavit = h_superavit.verMax().first.first;
    }

}
