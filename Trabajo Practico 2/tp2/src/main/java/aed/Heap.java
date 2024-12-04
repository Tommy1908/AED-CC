package aed;

import java.util.ArrayList;

public class Heap<T>{

    private ArrayList<Tuple<T, Handle>> heap;
    private int size, indice;
    private Comparator<T> comparator;

    public Heap(Comparator<T> comp, int i){
        heap = new ArrayList<Tuple<T, Handle>>();
        comparator = comp;
        indice = i;
        size = 0;
    }
    //O(1) ya que crear un arraylist es O(1)

    public Heap(Comparator<T> comp, int i, ArrayList<Tuple<T, Handle>> array){
        heap = new ArrayList<Tuple<T, Handle>>();
        comparator = comp;
        indice = i;
        size = 0;

        for (int j = 0; j < array.size(); j++){
            heap.add(array.get(j));
            heap.get(j).second.handles.set(indice, j);
            size++;
        }

        int ultimoPadre = (size / 2) - 1;

        for (int j = ultimoPadre; j >= 0; j--){
            downHeap(j);
        }
    }
    //O(n) porque se recorre todo el array 1 vez para pasarlo a array list, y O(n) para hacer downHeap. Queda el maximo que es el primer O(n)
    //Crear los handles en nuestra aplicacion es O(1) porque solo vamos a usar 1 o 2 indices y nunca mas que eso.
    

    public Tuple<T, Handle> verMax(){
        return heap.get(0);
    }
    //O(1) solo accedemos a una posicion del array

    public int size(){
        return size;
    }
    //O(1) devolver una variable

    public Handle encolar(Tuple<T, Handle> valor){
        Handle handleDeEncolado = null;
        if (size == 0) {
            heap.add(valor);
            handleDeEncolado = heap.get(0).second;
            handleDeEncolado.cambiarIndice(indice, 0);
        } else {
            heap.add(valor);
            handleDeEncolado = heap.get(size).second;
            handleDeEncolado.cambiarIndice(indice, size);
            upHeap(size);
            // Acceder a size es correcto porque aun el tamaño no se actualizo por lo tanto
            // la ultima pos es size
        }
        size++;
        return handleDeEncolado;
    }
    //O(log n) por el upHeap

    public Tuple<T, Handle> desencolarMax(){
        if (size == 0){
            return null;
        }
        Tuple<T, Handle> max = heap.get(0);
        if (size == 1){
            heap.clear();
            size--;
        }

        if (size > 1){
            swap(0, size - 1);
            heap.remove(size - 1); // O(1) cuando es el ultimo elemento
            size--;
            downHeap(0);
        }

        return max;
    }
    //O(log n) por el downHeap, remove no suma mas complejidad en el caso de ser el ultimo elemento.

    public void modificar(int i, T valor){
        if (i >= size || i < 0){
            System.out.println("Fuera de rango");
            return;
        }

        heap.set(i, new Tuple<T, Handle>(valor, heap.get(i).second));
        upHeap(i);
        downHeap(i);
    }
    //O(log n) por ambos upHeap y downHeap, 

    public void eliminar(int i){
        if (i >= size || i < 0){
            System.out.println("Fuera de rango");
            return;
        }
        if (i == size - 1){
            heap.remove(size - 1);
            size--;
        } else{
            swap(i, size - 1);
            heap.remove(size - 1);
            size--;

            upHeap(i);
            downHeap(i);
        }
    }
    //O(log n) por ambos upHeap y downHeap, 

    //////////////////// Algoritmos para mantener el Heap ////////////////////
    private void upHeap(int i){
        int padre = (i - 1) / 2;

        if (i != padre && comparator.compare(heap.get(i).first, heap.get(padre).first) > 0){
            swap(i, padre);
            upHeap(padre);
        }
    }
    //O(log n) algoritmo conocido

    private void downHeap(int i){
        int left = 2 * i + 1;
        int right = 2 * i + 2;

        // Si tiene hijos y es mas grande que ambos
        if (left < size && right < size && comparator.compare(heap.get(i).first, heap.get(left).first) > 0
                && comparator.compare(heap.get(i).first, heap.get(right).first) > 0){
            return;
        }
        // Si es mas chico que los 2 hijos
        if (left < size && right < size && comparator.compare(heap.get(i).first, heap.get(left).first) < 0
                && comparator.compare(heap.get(i).first, heap.get(right).first) < 0){
            int mayor;

            if (comparator.compare(heap.get(left).first, heap.get(right).first) >= 0){
                mayor = left;
            } else{
                mayor = right;
            }
            swap(i, mayor);
            downHeap(mayor); // Es el 0 en la poscion del que era el mayor
        }
        else{
            // Mas grande que izquierdo menor que derecho
            if (left < size && comparator.compare(heap.get(left).first, heap.get(i).first) > 0) {
                swap(i, left);
                downHeap(left);
            } else if (right < size) {
                swap(i, right);
                downHeap(right);
            }
        }

    }
    //O(log n) algoritmo conocido

    private void swap(int i, int j){
        Tuple<T, Handle> holder = heap.get(i);
        // modificar posiciones
        heap.set(i, heap.get(j));
        heap.set(j, holder);
        // modificar handles
        heap.get(j).second.handles.set(indice, j); // El que era i, ahora en j
        heap.get(i).second.handles.set(indice, i);
    }
    //O(1) solo intercambia posiciones de arrays

    ////////////////////////////////////////
    @Override
    public String toString(){ // Uso para test O(Size)
        if (size == 0) {
            return "[]";
        }
        String array = "[";

        for (int i = 0; i < heap.size() - 1; i++) {
            // System.out.println("Posición " + i + ": " + heap.get(i).first.toString());
            array += heap.get(i).first.toString() + ", ";
        }
        array += heap.get(heap.size() - 1).first.toString() + ']';
        System.out.println(array);
        return array;
    }
}
// [1,2,3,4,5,]
