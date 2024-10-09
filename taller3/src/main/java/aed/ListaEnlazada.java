package aed;

import java.util.*;

public class ListaEnlazada<T> implements Secuencia<T> {
    // Completar atributos privados
    private Nodo primero;
    private Nodo ultimo;
    private int size;

    private class Nodo {
        T valor;
        Nodo next;
        Nodo prev;

        Nodo(T v){
            valor = v;
        }
    }

    public ListaEnlazada() {
        primero = null;
        ultimo = null;
        size = 0;
    }

    public int longitud() {
        return size;
    }

    public void agregarAdelante(T elem) {
        if (primero == null){
            primero = new Nodo(elem);
            ultimo = primero;
        }
        else{
            if(ultimo == primero){
                Nodo nuevo = new Nodo(elem);
                nuevo.next = primero; //referencia a que el ahora primero viene despues del nuevo
                primero.prev = nuevo; //referencia a que el ahora primero tiene atras el nuevo
                ultimo = primero; //el primero ahora es el ultimo
                primero = nuevo; //el nuevo es ahora el primero
            }
            else{
                Nodo nuevo = new Nodo(elem);
                nuevo.next = primero; //referencia a que el ahora primero viene despues del nuevo
                primero.prev = nuevo; //referencia a que el ahora primero tiene atras el nuevo
                primero = nuevo; //el primero es el nuevo
            }
        }
        size ++;
    }

    public void agregarAtras(T elem) {
        if (ultimo == null){
            ultimo = new Nodo(elem);
            primero = ultimo;
        }
        else{
            if(primero == ultimo){
                Nodo nuevo = new Nodo(elem);
                nuevo.prev = ultimo;
                ultimo.next = nuevo;
                primero = ultimo;
                ultimo = nuevo; 
            }
            else{
                Nodo nuevo = new Nodo(elem);
                nuevo.prev = ultimo;
                ultimo.next = nuevo;
                ultimo = nuevo;
            }
        }
        size ++;
    }

    public T obtener(int i) {
        //asumo que el elemento pertenece a la lista//
        // --> de izquierda a derecha
        Nodo holder = primero;
        for(int j = 0 ; j < i; j++){
            holder = holder.next;
        }
        return holder.valor;
    }

    public Nodo obtenerNodo(int i) {
        //asumo que el elemento pertenece a la lista//
        // --> de izquierda a derecha
        Nodo holder = primero;
        for(int j = 0 ; j < i; j++){
            holder = holder.next;
        }
        return holder;
    }

    public void eliminar(int i) {
        if(i != size-1 && i != 0){
            Nodo holder = primero;
            for(int j = 0 ; j < i; j++){
                holder = holder.next;
            }
            Nodo siguiente = holder.next;
            Nodo anterior = holder.prev;
    
            siguiente.prev = anterior;
            anterior.next = siguiente;
        }
        else{
            if(i==0){
                primero = primero.next;
            }
            else{
                ultimo = ultimo.prev;
            }
        }
        size --;
    }

    public void modificarPosicion(int indice, T elem) {
        Nodo holder = primero;
        for(int i = 0; i < indice; i++){
            holder = holder.next;
        }
        holder.valor = elem;
    }

    public ListaEnlazada(ListaEnlazada<T> lista) {
        primero = new Nodo(lista.obtener(0));

        for(int i = 0; i < lista.size; i++){
            if(i==0){
                primero = new Nodo(lista.obtener(i));
            }
            else{
                Nodo nuevo = new Nodo(lista.obtener(i));
                nuevo.prev = obtenerNodo(i-1); //que el nuevo apunte al anterior
                nuevo.prev.next = nuevo; //que el anterior apunte al nuevo
            }
            size++;
        }
        ultimo = obtenerNodo(size-1);
    }
    
    @Override
    public String toString() {

        String res = "[";
        for(int i = 0; i < size-1; i++){
            res += (obtener(i)).toString() + ", ";
        }
        res += obtener(size-1).toString() + ']';
        return res;
    }

    private class ListaIterador implements Iterador<T> {
    	private int arrow;

        public ListaIterador(){
            arrow = 0;
        }

        public boolean haySiguiente() {
	        return arrow < size;
        }
        
        public boolean hayAnterior() {
	        return arrow > 0;
        }

        public T siguiente() {
            int i = arrow;
            arrow++;
	        return obtener(i);
        }
        

        public T anterior() {
            int i = arrow-1;
            arrow--;
            return obtener(i);
        }
    }

    public Iterador<T> iterador() {
	    ListaIterador it = new ListaIterador();
        return it;
    }

}
