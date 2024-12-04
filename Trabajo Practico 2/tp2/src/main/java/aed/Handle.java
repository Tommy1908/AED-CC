package aed;

import java.util.ArrayList;

public class Handle{
        public ArrayList<Integer> handles;

        public Handle(int indices){
            handles = new ArrayList<Integer>();
            for (int i = 0; i < indices; i++) {
                handles.add(null); // Añadir elementos null según la cantidad de índices
            }
        }
        //O(n) en general O(1) en el tp ya que solamente usamos con tamaño 1 y 2. 
        
        //Crear el handle recibe cuantos indices va a guardar, esto por si se tiene mas de 1 heap y se quiere llevar registro de donde esta cada elemento


        public void cambiarIndice(int indice, int valor){
            if (indice >= handles.size()) {
                return;
            }
            handles.set(indice, valor);
        }
        //O(1)
}
