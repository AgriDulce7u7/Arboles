public class ArbolBinario {
    private NodoArbol raiz;

    public ArbolBinario() {
        this.raiz = null;
    }

    // Verificar si el árbol está vacío
    public boolean estaVacio() {
        return raiz == null;
    }

    // Agregar dato al árbol (ordenado)
    public void agregarDato(int dato) {
        raiz = agregarRecursivo(raiz, dato);
    }

    private NodoArbol agregarRecursivo(NodoArbol nodo, int dato) {
        if (nodo == null) {
            return new NodoArbol(dato);
        }

        if (dato < nodo.dato) {
            nodo.izquierdo = agregarRecursivo(nodo.izquierdo, dato);
        } else if (dato > nodo.dato) {
            nodo.derecho = agregarRecursivo(nodo.derecho, dato);
        }

        return nodo;
    }

    // Recorrer árbol en Inorden (Izquierda - Raíz - Derecha)
    public String recorrerInorden() {
        StringBuilder resultado = new StringBuilder();
        inordenRecursivo(raiz, resultado);
        return resultado.toString().trim();
    }

    private void inordenRecursivo(NodoArbol nodo, StringBuilder resultado) {
        if (nodo != null) {
            inordenRecursivo(nodo.izquierdo, resultado);
            resultado.append(nodo.dato).append(" ");
            inordenRecursivo(nodo.derecho, resultado);
        }
    }

    // Recorrer árbol en Postorden (Izquierda - Derecha - Raíz)
    public String recorrerPostorden() {
        StringBuilder resultado = new StringBuilder();
        postordenRecursivo(raiz, resultado);
        return resultado.toString().trim();
    }

    private void postordenRecursivo(NodoArbol nodo, StringBuilder resultado) {
        if (nodo != null) {
            postordenRecursivo(nodo.izquierdo, resultado);
            postordenRecursivo(nodo.derecho, resultado);
            resultado.append(nodo.dato).append(" ");
        }
    }

    // Recorrer árbol en Preorden (Raíz - Izquierda - Derecha)
    public String recorrerPreorden() {
        StringBuilder resultado = new StringBuilder();
        preordenRecursivo(raiz, resultado);
        return resultado.toString().trim();
    }

    private void preordenRecursivo(NodoArbol nodo, StringBuilder resultado) {
        if (nodo != null) {
            resultado.append(nodo.dato).append(" ");
            preordenRecursivo(nodo.izquierdo, resultado);
            preordenRecursivo(nodo.derecho, resultado);
        }
    }

    // Verificar si existe un dato
    public boolean existeDato(int dato) {
        return existeRecursivo(raiz, dato);
    }

    private boolean existeRecursivo(NodoArbol nodo, int dato) {
        if (nodo == null) {
            return false;
        }
        if (nodo.dato == dato) {
            return true;
        }
        return dato < nodo.dato 
            ? existeRecursivo(nodo.izquierdo, dato)
            : existeRecursivo(nodo.derecho, dato);
    }

    // Obtener peso (cantidad total de nodos)
    public int obtenerPeso() {
        return pesoRecursivo(raiz);
    }

    private int pesoRecursivo(NodoArbol nodo) {
        if (nodo == null) {
            return 0;
        }
        return 1 + pesoRecursivo(nodo.izquierdo) + pesoRecursivo(nodo.derecho);
    }

    // Obtener altura del árbol
    public int obtenerAltura() {
        return alturaRecursivo(raiz);
    }

    private int alturaRecursivo(NodoArbol nodo) {
        if (nodo == null) {
            return 0;
        }
        int alturaIzq = alturaRecursivo(nodo.izquierdo);
        int alturaDer = alturaRecursivo(nodo.derecho);
        return 1 + Math.max(alturaIzq, alturaDer);
    }

    // Obtener nivel de un nodo
    public int obtenerNivel(int dato) {
        return nivelRecursivo(raiz, dato, 1);
    }

    private int nivelRecursivo(NodoArbol nodo, int dato, int nivel) {
        if (nodo == null) {
            return -1;
        }
        if (nodo.dato == dato) {
            return nivel;
        }
        if (dato < nodo.dato) {
            return nivelRecursivo(nodo.izquierdo, dato, nivel + 1);
        } else {
            return nivelRecursivo(nodo.derecho, dato, nivel + 1);
        }
    }

    // Contar hojas
    public int contarHojas() {
        return contarHojasRecursivo(raiz);
    }

    private int contarHojasRecursivo(NodoArbol nodo) {
        if (nodo == null) {
            return 0;
        }
        if (nodo.izquierdo == null && nodo.derecho == null) {
            return 1;
        }
        return contarHojasRecursivo(nodo.izquierdo) + contarHojasRecursivo(nodo.derecho);
    }

    // Obtener menor valor
    public int obtenerMenor() {
        if (estaVacio()) {
            throw new IllegalStateException("El árbol está vacío");
        }
        NodoArbol actual = raiz;
        while (actual.izquierdo != null) {
            actual = actual.izquierdo;
        }
        return actual.dato;
    }

    // Imprimir amplitud (por niveles)
    public String imprimirAmplitud() {
        if (raiz == null) {
            return "";
        }

        StringBuilder resultado = new StringBuilder();
        java.util.Queue<NodoArbol> cola = new java.util.LinkedList<>();
        cola.add(raiz);

        while (!cola.isEmpty()) {
            NodoArbol actual = cola.poll();
            resultado.append(actual.dato).append(" ");

            if (actual.izquierdo != null) {
                cola.add(actual.izquierdo);
            }
            if (actual.derecho != null) {
                cola.add(actual.derecho);
            }
        }

        return resultado.toString().trim();
    }

    // Eliminar dato
    public void eliminarDato(int dato) {
        raiz = eliminarRecursivo(raiz, dato);
    }

    private NodoArbol eliminarRecursivo(NodoArbol nodo, int dato) {
        if (nodo == null) {
            return null;
        }

        if (dato < nodo.dato) {
            nodo.izquierdo = eliminarRecursivo(nodo.izquierdo, dato);
        } else if (dato > nodo.dato) {
            nodo.derecho = eliminarRecursivo(nodo.derecho, dato);
        } else {
            // Nodo con un solo hijo o sin hijos
            if (nodo.izquierdo == null) {
                return nodo.derecho;
            } else if (nodo.derecho == null) {
                return nodo.izquierdo;
            }

            // Nodo con dos hijos: obtener el sucesor inorden (menor del subárbol derecho)
            nodo.dato = obtenerMenorValor(nodo.derecho);
            nodo.derecho = eliminarRecursivo(nodo.derecho, nodo.dato);
        }

        return nodo;
    }

    private int obtenerMenorValor(NodoArbol nodo) {
        int min = nodo.dato;
        while (nodo.izquierdo != null) {
            min = nodo.izquierdo.dato;
            nodo = nodo.izquierdo;
        }
        return min;
    }

    // Obtener nodo mayor
    public int obtenerNodoMayor() {
        if (estaVacio()) {
            throw new IllegalStateException("El árbol está vacío");
        }
        NodoArbol actual = raiz;
        while (actual.derecho != null) {
            actual = actual.derecho;
        }
        return actual.dato;
    }

    // Obtener nodo menor (alias de obtenerMenor)
    public int obtenerNodoMenor() {
        return obtenerMenor();
    }

    // Borrar el árbol completo
    public void borrarArbol() {
        raiz = null;
    }

    // Método para obtener la raíz (útil para dibujar)
    public NodoArbol getRaiz() {
        return raiz;
    }
}
