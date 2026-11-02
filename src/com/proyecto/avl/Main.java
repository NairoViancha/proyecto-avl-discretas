package com.proyecto.avl;

public class Main {
    public static void main(String[] args) {
        ArbolAVL arbol = new ArbolAVL();

        System.out.println("Insertando elementos en el Arbol AVL...");
        arbol.insertar(10, "Diez");
        arbol.insertar(20, "Veinte");
        arbol.insertar(30, "Treinta");
        arbol.insertar(40, "Cuarenta");
        arbol.insertar(50, "Cincuenta");

        System.out.println("Recorrido Inorden (Ordenado):");
        arbol.inorden();
    }
}
