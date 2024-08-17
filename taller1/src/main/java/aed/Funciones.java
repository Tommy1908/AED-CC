package aed;

class Funciones {
    int cuadrado(int x) {
        return x * x;
    }

    double distancia(double x, double y) {
        double res;
        res = Math.sqrt(x * x + y * y);
        return res;
    }

    boolean esPar(int n) {
        return n % 2 == 0;
    }

    boolean esBisiesto(int n) {
        boolean res = false;

        //con if
        /*if (n % 4 == 0 && n % 100 != 0){
            res = true;
        }
        else if (n % 400 == 0){
            res = true;
        }*/

        //sin if
        res = (n % 4 == 0 && n % 100 != 0) || (n % 400 == 0);
        
        return res;
    }

    int factorialIterativo(int n) {
        int res = 1;
        
        for (int i = 1; i <= n; i++) {
            res *= i;
        }

        return res;
    }

    int factorialRecursivo(int n) {
        if (n == 0) {
            return 1;
        }
        int res = n * factorialRecursivo(n - 1);
        return res;
    }

    boolean esPrimo(int n) {
        int res = 0;
        for (int i = 1; i <= n; i++) {
            if (n % i == 0) {
                res++;
            }
        }

        return res == 2;
    }

    int sumatoria(int[] numeros) {
        int res = 0;
        for (int i = 0; i < numeros.length; i++) {
            res += numeros[i];
        }
        return res;
    }

    int busqueda(int[] numeros, int buscado) {
        int res = -1;

        for (int i = 0; i < numeros.length; i++) {
            if (numeros[i] == buscado) {
                res = i;
            }
        }

        return res;
    }

    boolean tienePrimo(int[] numeros) {
        boolean res = false;
        for (int i = 0; i < numeros.length; i++) {
            if(!res) {
                res = esPrimo(numeros[i]);
            }
        }
        return res;
    }

    boolean todosPares(int[] numeros) {
        boolean res = true;
        for (int i = 0; i < numeros.length; i++) {
            if(res) {
                res = esPar(numeros[i]);
            }
        }
        return res;
    }

    boolean esPrefijo(String s1, String s2) {
        if (s1.length() > s2.length()) {
            return false;
        }
        for (int i = 0; i < s1.length(); i++) {
            if(s1.charAt(i) != s2.charAt(i)) {
                return false;
            }
        }
        return true;
    }

    boolean esSufijo(String s1, String s2) {
        /*
        int dif = s2.length() - s1.length();
        if (dif < 0) {
            return false;
        }
        for (int i = 0; i < s1.length(); i++) {
            if(s1.charAt(i) != s2.charAt(i + dif)) {
                return false;
            }
        }
        return true; */
        
        //Version usando esPrefijo
        int dif = s2.length() - s1.length();
        if (dif < 0) {
            return false;
        }
        String s2Cut = "";
        for (int i = dif; i < s2.length(); i++) {
            s2Cut += s2.charAt(i);
        }

        return esPrefijo(s1,s2Cut);
    }
}
