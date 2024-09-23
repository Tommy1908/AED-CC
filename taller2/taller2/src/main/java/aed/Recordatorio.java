package aed;

public class Recordatorio {
    private String _mensaje;
    private Fecha _fecha;
    private Horario _horario;

    public Recordatorio(String mensaje, Fecha fecha, Horario horario) {
        _mensaje = mensaje;
        _fecha = new Fecha(fecha.dia(),fecha.mes());
        _horario = new Horario(horario.hora(),horario.minutos());
    }

    public Horario horario() {
        return (new Horario(_horario.hora(),_horario.minutos()));
    }

    public Fecha fecha() {
        return (new Fecha(_fecha.dia(),_fecha.mes()));
    }

    public String mensaje() {
        return _mensaje;
    }

    @Override
    public String toString() {
        return _mensaje + " @ " + _fecha.toString() + ' ' + horario().toString();
    }

    @Override
    public boolean equals(Object otro) {
        if(otro == null || otro.getClass() != getClass()){
            return false;
        }
        Recordatorio otroRecordatorio = (Recordatorio) otro;
        //cambiar por la func
        boolean fechaIgual = _fecha.equals(otroRecordatorio._fecha);
        boolean horaIgual = _horario.equals(otroRecordatorio._horario);
        boolean mensajeIgual = _mensaje == otroRecordatorio._mensaje;

        return fechaIgual && horaIgual && mensajeIgual;
    }
}
