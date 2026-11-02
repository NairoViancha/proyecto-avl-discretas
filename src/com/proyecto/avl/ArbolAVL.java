package com.proyecto.avl;

public class ArbolAVL {
    private NodoAVL raiz;

    public int obtenerAltura(NodoAVL nodo) {
        return nodo == null ? 0 : nodo.altura;
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
}
