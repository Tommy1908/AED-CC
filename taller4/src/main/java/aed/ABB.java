package aed;

import java.lang.annotation.ElementType;
import java.util.*;

// Todos los tipos de datos "Comparables" tienen el método compareTo()
// elem1.compareTo(elem2) devuelve un entero. Si es mayor a 0, entonces elem1 > elem2
public class ABB<T extends Comparable<T>> implements Conjunto<T> {
    // Agregar atributos privados del Conjunto
    private Nodo _raiz;
    //private int _altura;
    private int _cardinal;

    private class Nodo {
        // Agregar atributos privados del Nodo
        private T _valor;
        private Nodo _padre;
        private Nodo _izq;
        private Nodo _der;
        // Crear Constructor del nodo
        public Nodo(T valor){
            _valor = valor;
            _padre = null;
            _izq = null;
            _der = null;
        }

    }

    public ABB() {
        _raiz = null;
        _cardinal = 0;
        //_altura = 0;

    }

    public int cardinal() {
        return _cardinal;
    }

    public T minimo(){
        Nodo n = _raiz;
        while(n._izq != null){
            n = n._izq;
        }
        return n._valor;
    }

    public T maximo(){
        Nodo n = _raiz;
        while(n._der != null){
            n = n._der;
        }
        return n._valor;
    }

    public void insertar(T elem){
        if(_cardinal == 0){
            _raiz = new Nodo(elem);
            _cardinal++;
        }
        else{
            insertarAux(_raiz,elem);
        }
    }
    private void insertarAux(Nodo raiz, T elem){
        if(raiz._valor.compareTo(elem) == 0){
            return;
        }
        else if(raiz._valor.compareTo(elem) > 0 && raiz._izq != null){ //si la raiz es mas grande que el elemento, va para la izquierda
            insertarAux(raiz._izq,elem);
        }
        else if(raiz._valor.compareTo(elem) < 0 && raiz._der != null){
            insertarAux(raiz._der,elem);
        }
        else{
            Nodo nuevo = new Nodo(elem);
            if(raiz._valor.compareTo(elem) > 0){
                raiz._izq = nuevo;
            }
            else{
                raiz._der = nuevo;
            }
            nuevo._padre = raiz;
            _cardinal++;
        }
    }

    public boolean pertenece(T elem){
        Nodo encontrado = buscar(_raiz, elem);
        if(encontrado == null){
            return false;
        }
        return true;
    }

    public void eliminar(T elem){
        Nodo objetivo = buscar(_raiz,elem);
        Nodo padre = objetivo._padre;

        if(objetivo != null){ //Si el objeto pertenece
            if(objetivo._izq == null && objetivo._der == null){ //Sin hijos
                eliminarHoja(objetivo, padre);
            }
            else if(objetivo._izq != null && objetivo._der != null){ //2 hijos (voy a buscar inmediato sucesor)
                Nodo inmediato = inmediatoSucesor(elem);
                
                eliminarRamaDoble(objetivo, padre, inmediato);
            }
            else{
                eliminarRamaSimple(objetivo, padre);
            }

            objetivo = null;
            _cardinal--;
        }
    }

    public String toString(){
        String res = "{";
        if(_cardinal != 0){
            Iterador<T> it = iterador();
            if(it.haySiguiente()){
                res += it.siguiente().toString();
            }
            while(it.haySiguiente()){
                res += ',' + it.siguiente().toString();
            }
        }
        res += '}';

        return res;
    }

    private class ABB_Iterador implements Iterador<T> {
        private Nodo _actual;
        private int _count;
        
        public ABB_Iterador(){
            _actual = buscar(_raiz, minimo());
        }

        public boolean haySiguiente() {
            if(_count < _cardinal){
                _count++;
                return true;
            }
            return false;
        }
    
        public T siguiente() {
            T res = _actual._valor;

            if(_actual._der != null){
                _actual = inmediatoSucesor(res);
            }
            else if(_actual._valor.compareTo(maximo()) != 0){
                _actual = subirHastaSiguiente(_actual,res);
            }
            return res;
        }
    }

    public Iterador<T> iterador() {
        return new ABB_Iterador();
    }



    /////Custom/////
    private Nodo buscar(Nodo raiz,T elem){ //Dada la raiz o nueva raiz del arbol busca si esta elem, null si no esta
        if(raiz == null){
            return null;
        }
        int compare = raiz._valor.compareTo(elem);
        if(compare == 0){
            return raiz;
        }
        else if(compare > 0){
            return buscar(raiz._izq, elem);
        }
        else{
            return buscar(raiz._der, elem);
        }
    }
    private Nodo inmediatoSucesor(T elem){
        Nodo n = buscar(_raiz, elem);
        
        if (n == null){
            return null;
        } 
        if (n._der != null) {
            // Encontrar el mínimo en el subárbol derecho
            n = n._der;
            while (n._izq != null) {
                n = n._izq;
            }
            return n;
        }
        return null; // No hay sucesor inmediato
    }
    private Nodo subirHastaSiguiente(Nodo actual,T elem){
        actual = actual._padre;
        if(actual._valor.compareTo(elem) <= 0){
            actual = subirHastaSiguiente(actual,elem);
        }
        return actual;
    }
    
    private void eliminarHoja(Nodo objetivo, Nodo padre){
        if(padre != null){ //caso con mas elementos
            if(padre._valor.compareTo(objetivo._valor) > 0){
                padre._izq = null;
            }
            else{
                padre._der = null;
            }
        }
        else{ //caso 1 solo elemento
            _raiz = null;
        }
    }
    private void eliminarRamaSimple(Nodo objetivo, Nodo padre){
        if(objetivo._izq == null && objetivo._der != null){ //Solo con rama derecha
            if(objetivo == _raiz){
                _raiz = objetivo._der;
                _raiz._padre = null;
            }
            else{ //Veo posicion relativa al padre
                if(padre._valor.compareTo(objetivo._valor) > 0){
                    padre._izq = objetivo._der;
                    objetivo._der._padre = padre;
                }
                else{
                    padre._der = objetivo._der;
                    objetivo._der._padre = padre;
                }
            }
        }
        else{ //Solo con rama izquierda
            if(objetivo == _raiz){
                _raiz = objetivo._izq;
                _raiz._padre = null;
            }
            else{ //Veo posicion relativa al padre
                if(padre._valor.compareTo(objetivo._valor) > 0){
                    padre._izq = objetivo._izq;
                    objetivo._izq._padre = padre;
                }
                else{
                    padre._der = objetivo._izq;
                    objetivo._izq._padre = padre;
                }
            }
        }

    }
    private void eliminarRamaDoble(Nodo objetivo, Nodo padre, Nodo inmediato){
        objetivo._valor = inmediato._valor;
        //Borrar el inmediato
        if(inmediato._izq == null && inmediato._der == null){ //Sin hijos
            eliminarHoja(inmediato, inmediato._padre);
        }
        else{
            eliminarRamaSimple(inmediato, inmediato._padre);
        }
    }
}
