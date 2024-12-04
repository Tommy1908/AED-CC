package aed;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

public class HeapTest {

    @Test
    void init(){
        IntComparator comp = new IntComparator();
        Heap<Integer> newHeap = new Heap<Integer>(comp, 0);
        assertTrue(newHeap.size() == 0);
    }

    @Test
    void encolar(){
        IntComparator comp = new IntComparator();
        Heap<Integer> heap = new Heap<Integer>(comp, 0);

        //Encolar 1 elemento
        Handle a = heap.encolar(new Tuple<Integer,Handle>(30,new Handle(1)));
        assertEquals("[30]", heap.toString());
        assertEquals( 0, a.handles.get(0));

        //Encolar un elemento menor
        heap.encolar(new Tuple<Integer,Handle>(20,new Handle(1)));
        assertEquals("[30, 20]", heap.toString());

        //Encolar un elemento mayor
        heap.encolar(new Tuple<Integer,Handle>(50,new Handle(1)));
        assertEquals("[50, 20, 30]", heap.toString());

        Handle b = heap.encolar(new Tuple<Integer,Handle>(40,new Handle(1)));
        assertEquals("[50, 40, 30, 20]", heap.toString());
        assertEquals( 2, a.handles.get(0));
        assertEquals( 1, b.handles.get(0));


        heap.encolar(new Tuple<Integer,Handle>(70,new Handle(1)));
        assertEquals( "[70, 50, 30, 20, 40]", heap.toString());

        Handle c = heap.encolar(new Tuple<Integer,Handle>(10,new Handle(1)));
        assertEquals("[70, 50, 30, 20, 40, 10]" ,heap.toString());
        assertEquals(2, a.handles.get(0) );
        assertEquals(4 , b.handles.get(0));
        assertEquals(5, c.handles.get(0));

        //Elementos repetidos
        heap.encolar(new Tuple<Integer,Handle>(60,new Handle(1)));
        assertEquals("[70, 50, 60, 20, 40, 10, 30]" , heap.toString() );

        heap.encolar(new Tuple<Integer,Handle>(60,new Handle(1)));
        assertEquals("[70, 60, 60, 50, 40, 10, 30, 20]" , heap.toString() );

        heap.encolar(new Tuple<Integer,Handle>(60,new Handle(1)));
        assertEquals("[70, 60, 60, 60, 40, 10, 30, 20, 50]" , heap.toString());
    }

    @Test
    void desencolar(){
        IntComparator comp = new IntComparator();
        Heap<Integer> heap = new Heap<Integer>(comp, 0);

        //Casos regulares
        heap.encolar(new Tuple<Integer,Handle>(7,new Handle(1)));
        heap.encolar(new Tuple<Integer,Handle>(6,new Handle(1)));
        heap.encolar(new Tuple<Integer,Handle>(6,new Handle(1)));
        heap.encolar(new Tuple<Integer,Handle>(4,new Handle(1)));
        heap.encolar(new Tuple<Integer,Handle>(5,new Handle(1)));
        heap.encolar(new Tuple<Integer,Handle>(-3,new Handle(1)));
        heap.encolar(new Tuple<Integer,Handle>(4,new Handle(1)));
        heap.encolar(new Tuple<Integer,Handle>(2,new Handle(1)));
        heap.encolar(new Tuple<Integer,Handle>(3,new Handle(1)));
        heap.encolar(new Tuple<Integer,Handle>(0,new Handle(1)));
        assertEquals("[7, 6, 6, 4, 5, -3, 4, 2, 3, 0]" , heap.toString());

        Tuple<Integer,Handle> a = heap.desencolarMax();
        assertEquals(7,a.first);
        assertEquals("[6, 5, 6, 4, 0, -3, 4, 2, 3]", heap.toString());

        Tuple<Integer,Handle> b = heap.desencolarMax();
        assertEquals(6,b.first);
        assertEquals("[6, 5, 4, 4, 0, -3, 3, 2]", heap.toString());
        
        heap.desencolarMax();
        heap.desencolarMax();
        heap.desencolarMax();
        heap.desencolarMax();
        heap.desencolarMax();
        heap.desencolarMax();
        assertEquals("[0, -3]", heap.toString());

        //2 Elementos
        heap.desencolarMax();
        assertEquals("[-3]", heap.toString());

        //Caso 1 Elemento
        Tuple<Integer,Handle> c = heap.desencolarMax();
        assertEquals(-3,c.first);
        assertEquals("[]", heap.toString());
        
        //Caso 0 Elementos
        Tuple<Integer,Handle> d = heap.desencolarMax();
        assertTrue(d == null);
        assertEquals("[]", heap.toString());
    }

    @Test
    void array2heap(){
        IntComparator comp = new IntComparator();
        
        ArrayList<Tuple<Integer,Handle>> array = new ArrayList<Tuple<Integer,Handle>>();
        array.add(new Tuple<Integer,Handle>(4, new Handle(1)));
        array.add(new Tuple<Integer,Handle>(2, new Handle(1)));
        array.add(new Tuple<Integer,Handle>(7, new Handle(1)));
        array.add(new Tuple<Integer,Handle>(9, new Handle(1)));
        array.add(new Tuple<Integer,Handle>(4, new Handle(1)));
        array.add(new Tuple<Integer,Handle>(3, new Handle(1)));
        array.add(new Tuple<Integer,Handle>(8, new Handle(1)));
        array.add(new Tuple<Integer,Handle>(5, new Handle(1)));


        Heap<Integer> heap = new Heap<Integer>(comp, 0, array);

        assertEquals("[9, 5, 8, 4, 4, 3, 7, 2]", heap.toString());

    }
    
    @Test
    void modificar(){
        IntComparator comp = new IntComparator();
        Heap<Integer> heap = new Heap<Integer>(comp, 0);
        

        heap.encolar(new Tuple<Integer,Handle>(25,new Handle(1)));
        heap.encolar(new Tuple<Integer,Handle>(20,new Handle(1)));
        heap.encolar(new Tuple<Integer,Handle>(15,new Handle(1)));
        heap.encolar(new Tuple<Integer,Handle>(7,new Handle(1)));
        heap.encolar(new Tuple<Integer,Handle>(5,new Handle(1)));
        heap.encolar(new Tuple<Integer,Handle>(3,new Handle(1)));
        heap.encolar(new Tuple<Integer,Handle>(1,new Handle(1)));
                
        assertEquals("[25, 20, 15, 7, 5, 3, 1]", heap.toString());
        
        //Sin alteraciones de poscion
        heap.modificar(4, 4);
        assertEquals("[25, 20, 15, 7, 4, 3, 1]", heap.toString());

        //Usando upHeap
        heap.modificar(6,17);
        assertEquals("[25, 20, 17, 7, 4, 3, 15]", heap.toString());

        //Usando donwHeap
        heap.modificar(0, -5);
        assertEquals("[20, 7, 17, -5, 4, 3, 15]", heap.toString());

    }

    @Test
    void eliminar(){
        IntComparator comp = new IntComparator();
        Heap<Integer> heap = new Heap<Integer>(comp, 0);
        

        heap.encolar(new Tuple<Integer,Handle>(25,new Handle(1)));
        heap.encolar(new Tuple<Integer,Handle>(20,new Handle(1)));
        heap.encolar(new Tuple<Integer,Handle>(15,new Handle(1)));
        heap.encolar(new Tuple<Integer,Handle>(7,new Handle(1)));
        heap.encolar(new Tuple<Integer,Handle>(5,new Handle(1)));
        heap.encolar(new Tuple<Integer,Handle>(3,new Handle(1)));
        heap.encolar(new Tuple<Integer,Handle>(1,new Handle(1)));
                
        assertEquals("[25, 20, 15, 7, 5, 3, 1]", heap.toString());

        heap.eliminar(-3);
        assertEquals("[25, 20, 15, 7, 5, 3, 1]", heap.toString());
        
        //Eliminar hoja
        heap.eliminar(3);
        assertEquals("[25, 20, 15, 1, 5, 3]", heap.toString());


        //Eliminar Padre
        heap.eliminar(1);
        assertEquals("[25, 5, 15, 1, 3]", heap.toString());

        //Eliminar Raiz
        heap.eliminar(0);
        assertEquals("[15, 5, 3, 1]", heap.toString());

        //Eliminar ultimo
        heap.eliminar(3);
        assertEquals("[15, 5, 3]", heap.toString());

        heap.eliminar(0);
        assertEquals("[5, 3]", heap.toString());

        heap.eliminar(0);
        assertEquals("[3]", heap.toString());

        //eliminar con un solo elemento 
        heap.eliminar(0);
        assertEquals("[]", heap.toString());
        assertEquals(0, heap.size());
        
        //elminiar sin elementos
        heap.eliminar(0);
        assertEquals("[]", heap.toString());
        assertEquals(0, heap.size());
        
    }

    @Test
    void simultaneo(){
        AntiguedadComparator antiguedadComparator = new AntiguedadComparator();
        GananciaComparator gananciaComparator = new GananciaComparator();



        Traslado[] listaTraslados = new Traslado[] {
            new Traslado(1, 0, 1, 100, 10),
            new Traslado(2, 0, 1, 400, 20),
            new Traslado(3, 3, 4, 500, 50),
            new Traslado(4, 4, 3, 500, 11),
            new Traslado(5, 1, 0, 1000, 40),
            new Traslado(6, 1, 0, 1000, 41),
            new Traslado(7, 6, 3, 2000, 42)
        };

        ArrayList<Tuple<Traslado,Handle>> copy = new ArrayList<Tuple<Traslado,Handle>>();
        for(int j = 0; j < listaTraslados.length; j++){
            copy.add(new Tuple<Traslado,Handle>(listaTraslados[j], new Handle(2)));
        }

        Heap<Traslado> heapAntiguedad = new Heap<Traslado>(antiguedadComparator, 0, copy);
        Heap<Traslado> heapGanancia = new Heap<Traslado>(gananciaComparator, 1, copy);
        
        assertEquals("[g:100 id:1 ts:10, g:500 id:4 ts:11, g:1000 id:6 ts:41, g:400 id:2 ts:20, g:1000 id:5 ts:40, g:500 id:3 ts:50, g:2000 id:7 ts:42]",heapAntiguedad.toString());
        assertEquals("[g:2000 id:7 ts:42, g:1000 id:5 ts:40, g:1000 id:6 ts:41, g:500 id:4 ts:11, g:400 id:2 ts:20, g:100 id:1 ts:10, g:500 id:3 ts:50]",heapGanancia.toString());
        
        //desencolar en ganancia y eliminar en otro heap

        Tuple<Traslado,Handle> a = heapGanancia.desencolarMax();
        heapAntiguedad.eliminar(a.second.handles.get(1));
        assertEquals("[g:1000 id:5 ts:40, g:500 id:3 ts:50, g:1000 id:6 ts:41, g:500 id:4 ts:11, g:400 id:2 ts:20, g:100 id:1 ts:10]",heapGanancia.toString());
        assertEquals("[g:100 id:1 ts:10, g:500 id:4 ts:11, g:1000 id:6 ts:41, g:400 id:2 ts:20, g:1000 id:5 ts:40, g:500 id:3 ts:50]",heapAntiguedad.toString());
        
        //desencolar en antiguedad y eliminar en otro heap
        
        Tuple<Traslado,Handle> b = heapAntiguedad.desencolarMax();
        heapGanancia.eliminar(b.second.handles.get(0));
        assertEquals("[g:500 id:4 ts:11, g:400 id:2 ts:20, g:1000 id:6 ts:41, g:500 id:3 ts:50, g:1000 id:5 ts:40]",heapAntiguedad.toString());
        assertEquals("[g:1000 id:5 ts:40, g:500 id:3 ts:50, g:1000 id:6 ts:41, g:500 id:4 ts:11, g:400 id:2 ts:20]",heapGanancia.toString());
    }
}