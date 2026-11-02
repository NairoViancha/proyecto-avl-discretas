package com.proyecto.avl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class Main {

    private static final int TOTAL_ELEMENTOS = 1000;
    private static final int TOTAL_BUSQUEDAS = 1000;
    private static final int TOTAL_ELIMINACIONES = 100;
    private static final int RANGO_CLAVES = 1_000_000;
    private static final long SEMILLA = 42L;

    public static void main(String[] args) {
        ArbolAVL arbol = new ArbolAVL();
        Random random = new Random(SEMILLA);

        // 1) Generar 1,000 claves aleatorias únicas
        Set<Integer> claves = generarClavesUnicas(random, TOTAL_ELEMENTOS);
        List<Integer> listaClaves = new ArrayList<>(claves);

        // 2) Inserción de los 1,000 elementos
        long inicioInsercion = System.nanoTime();
        for (Integer clave : listaClaves) {
            arbol.insertar(clave, "Valor-" + clave);
        }
        long finInsercion = System.nanoTime();
        double msInsercion = (finInsercion - inicioInsercion) / 1_000_000.0;

        System.out.println("=== EXPERIMENTO ARBOL AVL ===");
        System.out.println("Elementos insertados : " + TOTAL_ELEMENTOS);
        System.out.println("Tiempo de insercion  : " + msInsercion + " ms");
        System.out.println("Altura tras insertar : " + arbol.altura());

        // 3) Búsquedas: la mitad de claves existentes y la mitad inexistentes
        int existentesBuscadas = TOTAL_BUSQUEDAS / 2;
        int inexistentesBuscadas = TOTAL_BUSQUEDAS - existentesBuscadas;
        int encontradas = 0;

        long inicioBusqueda = System.nanoTime();
        for (int i = 0; i < existentesBuscadas; i++) {
            int clave = listaClaves.get(random.nextInt(listaClaves.size()));
            if (arbol.buscar(clave) != null) {
                encontradas++;
            }
        }
        for (int i = 0; i < inexistentesBuscadas; i++) {
            int clave = RANGO_CLAVES + 1 + i; // fuera del rango generado: no deben existir
            if (arbol.buscar(clave) != null) {
                encontradas++;
            }
        }
        long finBusqueda = System.nanoTime();
        double msBusqueda = (finBusqueda - inicioBusqueda) / 1_000_000.0;

        System.out.println();
        System.out.println("Busquedas realizadas : " + TOTAL_BUSQUEDAS
                + " (" + existentesBuscadas + " existentes, " + inexistentesBuscadas + " inexistentes)");
        System.out.println("Claves encontradas   : " + encontradas + " (esperado: " + existentesBuscadas + ")");
        System.out.println("Tiempo de busqueda   : " + msBusqueda + " ms");

        // 4) Eliminar 100 nodos para comprobar el rebalanceo
        List<Integer> aEliminar = new ArrayList<>();
        Set<Integer> yaElegidas = new HashSet<>();
        while (aEliminar.size() < TOTAL_ELIMINACIONES) {
            int clave = listaClaves.get(random.nextInt(listaClaves.size()));
            if (yaElegidas.add(clave)) {
                aEliminar.add(clave);
            }
        }

        long inicioEliminacion = System.nanoTime();
        for (Integer clave : aEliminar) {
            arbol.eliminar(clave);
        }
        long finEliminacion = System.nanoTime();
        double msEliminacion = (finEliminacion - inicioEliminacion) / 1_000_000.0;

        int nodosRestantes = TOTAL_ELEMENTOS - TOTAL_ELIMINACIONES;
        int alturaFinal = arbol.altura();
        int alturaOptima = (int) Math.ceil(Math.log(nodosRestantes + 1) / Math.log(2));
        double limiteAVL = 1.4405 * (Math.log(nodosRestantes + 2) / Math.log(2)) - 0.3277;
        boolean rebalanceado = alturaFinal <= limiteAVL;

        System.out.println();
        System.out.println("Nodos eliminados     : " + TOTAL_ELIMINACIONES);
        System.out.println("Tiempo de eliminacion: " + msEliminacion + " ms");
        System.out.println("Nodos restantes      : " + nodosRestantes);
        System.out.println("Altura final         : " + alturaFinal);
        System.out.println("Altura optima (log2) : " + alturaOptima);
        System.out.println("Limite teorico AVL   : " + String.format("%.2f", limiteAVL));
        System.out.println("Rebalanceo correcto  : " + (rebalanceado ? "SI" : "NO"));

        // 5) Comprobar que las claves eliminadas ya no existen
        int eliminadasEncontradas = 0;
        for (Integer clave : aEliminar) {
            if (arbol.buscar(clave) != null) {
                eliminadasEncontradas++;
            }
        }
        System.out.println("Claves eliminadas aun presentes: " + eliminadasEncontradas + " (esperado: 0)");

        double msTotal = msInsercion + msBusqueda + msEliminacion;
        System.out.println();
        System.out.println(String.format("Tiempo total ejecutado: %.3f ms", msTotal));
    }

    private static Set<Integer> generarClavesUnicas(Random random, int cantidad) {
        Set<Integer> claves = new HashSet<>();
        while (claves.size() < cantidad) {
            claves.add(random.nextInt(RANGO_CLAVES) + 1);
        }
        return claves;
    }
}
