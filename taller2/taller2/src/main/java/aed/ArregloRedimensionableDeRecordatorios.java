package aed;

class ArregloRedimensionableDeRecordatorios {
    
    private Recordatorio[] _Recordatorios;

    public ArregloRedimensionableDeRecordatorios() {
        _Recordatorios = new Recordatorio[0];
    }

    public int longitud() {
        return _Recordatorios.length;
    }

    public void agregarAtras(Recordatorio i) {
        Recordatorio[] nuevo = new Recordatorio[_Recordatorios.length + 1];

        for (int j = 0; j < _Recordatorios.length; j++){
            nuevo[j] = _Recordatorios[j];
        }
        nuevo[nuevo.length - 1] = i;

        _Recordatorios = nuevo;
    }

    public Recordatorio obtener(int i) {
        return _Recordatorios[i];
    }

    public void quitarAtras() {
        Recordatorio[] nuevo = new Recordatorio[_Recordatorios.length -1];

        for (int i = 0; i < _Recordatorios.length - 1; i++){
            nuevo[i] = _Recordatorios[i];
        }

        _Recordatorios = nuevo;
    }

    public void modificarPosicion(int indice, Recordatorio valor) {
        _Recordatorios[indice] = valor;
    }

    public ArregloRedimensionableDeRecordatorios(ArregloRedimensionableDeRecordatorios vector) {
        Recordatorio[] copia = new Recordatorio[vector.longitud()];
        
        for (int i = 0; i < vector.longitud(); i++){
            Recordatorio r = new Recordatorio(vector.obtener(i).mensaje(),vector.obtener(i).fecha(),vector.obtener(i).horario()); //aca el puntero esta hacia un nuevo recordatorio y no al de vector
            //Recordatorio r = vector.obtener(i); con eso da aliasing
            copia[i] = r;
        }
        _Recordatorios = copia;
    }

    public ArregloRedimensionableDeRecordatorios copiar() {
        ArregloRedimensionableDeRecordatorios res = new ArregloRedimensionableDeRecordatorios();

        for (int i = 0; i < longitud(); i++){
            Recordatorio r = new Recordatorio(_Recordatorios[i].mensaje(),_Recordatorios[i].fecha(),_Recordatorios[i].horario());
            res.agregarAtras(r);
        }
        return res;
    }
}
