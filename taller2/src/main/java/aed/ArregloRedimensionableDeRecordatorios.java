package aed;

class ArregloRedimensionableDeRecordatorios {
    
    private Recordatorio[] _recordatorios;

    public ArregloRedimensionableDeRecordatorios() {
        _recordatorios = new Recordatorio[0];
    }

    public int longitud() {
        return _recordatorios.length;
    }

    public void agregarAtras(Recordatorio i) {
        Recordatorio[] nuevo = new Recordatorio[_recordatorios.length + 1];

        for (int j = 0; j < _recordatorios.length; j++){
            nuevo[j] = _recordatorios[j];
        }
        nuevo[nuevo.length - 1] = i;

        _recordatorios = nuevo;
    }

    public Recordatorio obtener(int i) {
        return _recordatorios[i];
    }

    public void quitarAtras() {
        Recordatorio[] nuevo = new Recordatorio[_recordatorios.length -1];

        for (int i = 0; i < _recordatorios.length - 1; i++){
            nuevo[i] = _recordatorios[i];
        }

        _recordatorios = nuevo;
    }

    public void modificarPosicion(int indice, Recordatorio valor) {
        _recordatorios[indice] = valor;
    }

    public ArregloRedimensionableDeRecordatorios(ArregloRedimensionableDeRecordatorios vector) {
        Recordatorio[] copia = new Recordatorio[vector.longitud()];
        
        for (int i = 0; i < vector.longitud(); i++){
            Recordatorio r = new Recordatorio(vector.obtener(i).mensaje(),vector.obtener(i).fecha(),vector.obtener(i).horario()); //aca el puntero esta hacia un nuevo recordatorio y no al de vector
            //Recordatorio r = vector.obtener(i); con eso da aliasing
            copia[i] = r;
        }
        _recordatorios = copia;
    }

    public ArregloRedimensionableDeRecordatorios copiar() {
        ArregloRedimensionableDeRecordatorios res = new ArregloRedimensionableDeRecordatorios();

        for (int i = 0; i < longitud(); i++){
            Recordatorio r = new Recordatorio(_recordatorios[i].mensaje(),_recordatorios[i].fecha(),_recordatorios[i].horario());
            res.agregarAtras(r);
        }
        return res;
    }
}
