package com.proyecto.avl;

public class NodoAVL {
    public int clave;
    public String valor;
    public int altura;
    public NodoAVL izquierdo;
    public NodoAVL derecho;

    public NodoAVL(int clave, String valor) {
        this.clave = clave;
        this.valor = valor;
        this.altura = 1;
        this.izquierdo = null;
        this.derecho = null;
    }
}
