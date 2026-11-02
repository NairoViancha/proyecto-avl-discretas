package com.proyecto.avl;

public class ArbolAVL {
    private NodoAVL raiz;

    public int obtenerAltura(NodoAVL nodo) {
        return nodo == null ? 0 : nodo.altura;
    }

    public int altura() {
        return obtenerAltura(raiz);
    }

    public int obtenerBalance(NodoAVL nodo) {
        return nodo == null ? 0 : obtenerAltura(nodo.izquierdo) - obtenerAltura(nodo.derecho);
    }

    private NodoAVL rotacionDerecha(NodoAVL y) {
        NodoAVL x = y.izquierdo;
        NodoAVL T2 = x.derecho;

        x.derecho = y;
        y.izquierdo = T2;

        y.altura = Math.max(obtenerAltura(y.izquierdo), obtenerAltura(y.derecho)) + 1;
        x.altura = Math.max(obtenerAltura(x.izquierdo), obtenerAltura(x.derecho)) + 1;

        return x;
    }

    private NodoAVL rotacionIzquierda(NodoAVL x) {
        NodoAVL y = x.derecho;
        NodoAVL T2 = y.izquierdo;

        y.izquierdo = x;
        x.derecho = T2;

        x.altura = Math.max(obtenerAltura(x.izquierdo), obtenerAltura(x.derecho)) + 1;
        y.altura = Math.max(obtenerAltura(y.izquierdo), obtenerAltura(y.derecho)) + 1;

        return y;
    }

    public void insertar(int clave, String valor) {
        raiz = insertarRec(raiz, clave, valor);
    }

    private NodoAVL insertarRec(NodoAVL nodo, int clave, String valor) {
        if (nodo == null) {
            return new NodoAVL(clave, valor);
        }

        if (clave < nodo.clave) {
            nodo.izquierdo = insertarRec(nodo.izquierdo, clave, valor);
        } else if (clave > nodo.clave) {
            nodo.derecho = insertarRec(nodo.derecho, clave, valor);
        } else {
            nodo.valor = valor;
            return nodo;
        }

        nodo.altura = 1 + Math.max(obtenerAltura(nodo.izquierdo), obtenerAltura(nodo.derecho));
        int balance = obtenerBalance(nodo);

        if (balance > 1 && clave < nodo.izquierdo.clave) {
            return rotacionDerecha(nodo);
        }
        if (balance < -1 && clave > nodo.derecho.clave) {
            return rotacionIzquierda(nodo);
        }
        if (balance > 1 && clave > nodo.izquierdo.clave) {
            nodo.izquierdo = rotacionIzquierda(nodo.izquierdo);
            return rotacionDerecha(nodo);
        }
        if (balance < -1 && clave < nodo.derecho.clave) {
            nodo.derecho = rotacionDerecha(nodo.derecho);
            return rotacionIzquierda(nodo);
        }

        return nodo;
    }

    public void inorden() {
        inordenRec(raiz);
        System.out.println();
    }

    private void inordenRec(NodoAVL nodo) {
        if (nodo != null) {
            inordenRec(nodo.izquierdo);
            System.out.print("[" + nodo.clave + ": " + nodo.valor + "] ");
            inordenRec(nodo.derecho);
        }
    }

    /**
     * Busca un nodo por su clave.
     *
     * @return el valor asociado a la clave, o null si la clave no existe.
     */
    public String buscar(int clave) {
        NodoAVL actual = raiz;

        while (actual != null) {
            if (clave == actual.clave) {
                return actual.valor;
            } else if (clave < actual.clave) {
                actual = actual.izquierdo;
            } else {
                actual = actual.derecho;
            }
        }

        return null;
    }

    public void eliminar(int clave) {
        raiz = eliminarRec(raiz, clave);
    }

    private NodoAVL eliminarRec(NodoAVL nodo, int clave) {
        if (nodo == null) {
            return null;
        }

        if (clave < nodo.clave) {
            nodo.izquierdo = eliminarRec(nodo.izquierdo, clave);
        } else if (clave > nodo.clave) {
            nodo.derecho = eliminarRec(nodo.derecho, clave);
        } else {
            // Nodo encontrado
            if (nodo.izquierdo == null || nodo.derecho == null) {
                // Caso con cero o un hijo
                NodoAVL hijo = (nodo.izquierdo != null) ? nodo.izquierdo : nodo.derecho;
                nodo = hijo;
            } else {
                // Caso con dos hijos: se reemplaza por el menor del subárbol derecho
                NodoAVL minimo = obtenerMinimo(nodo.derecho);
                nodo.clave = minimo.clave;
                nodo.valor = minimo.valor;
                nodo.derecho = eliminarRec(nodo.derecho, minimo.clave);
            }
        }

        if (nodo == null) {
            return null;
        }

        nodo.altura = 1 + Math.max(obtenerAltura(nodo.izquierdo), obtenerAltura(nodo.derecho));
        int balance = obtenerBalance(nodo);

        // Izquierda Izquierda
        if (balance > 1 && obtenerBalance(nodo.izquierdo) >= 0) {
            return rotacionDerecha(nodo);
        }
        // Izquierda Derecha
        if (balance > 1 && obtenerBalance(nodo.izquierdo) < 0) {
            nodo.izquierdo = rotacionIzquierda(nodo.izquierdo);
            return rotacionDerecha(nodo);
        }
        // Derecha Derecha
        if (balance < -1 && obtenerBalance(nodo.derecho) <= 0) {
            return rotacionIzquierda(nodo);
        }
        // Derecha Izquierda
        if (balance < -1 && obtenerBalance(nodo.derecho) > 0) {
            nodo.derecho = rotacionDerecha(nodo.derecho);
            return rotacionIzquierda(nodo);
        }

        return nodo;
    }

    private NodoAVL obtenerMinimo(NodoAVL nodo) {
        NodoAVL actual = nodo;
        while (actual.izquierdo != null) {
            actual = actual.izquierdo;
        }
        return actual;
    }
}
