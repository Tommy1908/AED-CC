package aed;

public class Agenda {
    private ArregloRedimensionableDeRecordatorios _recordatorios;
    private Fecha _fecha;

    public Agenda(Fecha fechaActual) {
        _fecha = new Fecha(fechaActual.dia(), fechaActual.mes());
        _recordatorios = new ArregloRedimensionableDeRecordatorios();
    }

    public void agregarRecordatorio(Recordatorio recordatorio) {
        _recordatorios.agregarAtras(recordatorio);
    }

    @Override
    public String toString() {
        String res = _fecha.toString() + "\n=====\n";
        for (int i = 0; i < _recordatorios.longitud(); i++){
            if(_recordatorios.obtener(i).fecha().equals(_fecha)){
                res += _recordatorios.obtener(i).toString() + "\n";
            }
        } 
        return res;
    }

    public void incrementarDia() {
        _fecha.incrementarDia();
    }

    public Fecha fechaActual() {
        Fecha res = new Fecha(_fecha.dia(), _fecha.mes());
        return res;
    }

}
