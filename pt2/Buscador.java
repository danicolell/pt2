package pt2;

import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

public class Buscador {
    public static void main(String[] args) {
        // Solicito al usuario el tamaño del vector
        int tamañoVector = pedirEntero("Ingrese el tamaño del vector: ");

        // Solicito al usuario el número a buscar
        int númeroABuscar = pedirEntero("Ingrese el número a buscar: ");

        // Solicito al usuario el número de hilos
        int númeroDeHilos = pedirEntero("Ingrese el número de hilos: ");

        // Valido límites concretos para los valores de los argumentos
        if (tamañoVector <= 0 || númeroDeHilos <= 0) {
            System.out.println("Error: El tamaño del vector y el número de hilos deben ser mayores que cero.");
            return;
        }

        // Creo e inicializo el vector de forma aleatoria
        int[] vector = new int[tamañoVector];
        Random random = new Random();
        for (int i = 0; i < tamañoVector; i++) {
            vector[i] = random.nextInt(1000); // Puedes ajustar el rango según tus necesidades
        }

        // Variable para indicar si el número ha sido encontrado
        AtomicBoolean numeroEncontrado = new AtomicBoolean(false);

        // Creo hilos para realizar la búsqueda
        Thread[] hilos = new Thread[númeroDeHilos];
        int posicionesPorHilo = tamañoVector / númeroDeHilos;

        for (int i = 0; i < númeroDeHilos; i++) {
            int inicio = i * posicionesPorHilo;
            int fin = (i == númeroDeHilos - 1) ? tamañoVector - 1 : (i + 1) * posicionesPorHilo - 1;
            hilos[i] = new Thread(new BusquedaThread(vector, númeroABuscar, inicio, fin, i + 1, numeroEncontrado));
            hilos[i].start();
        }

        // Espero a que todos los hilos terminen antes de continuar
        for (Thread hilo : hilos) {
            try {
                hilo.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Mensaje indicando que el número no se encontró si no se ha encontrado
        if (!numeroEncontrado.get()) {
            System.out.println("El número " + númeroABuscar + " no se encontró en el vector.");
        }
    }

    // Método para realizar la búsqueda en un rango del vector
    private static void buscarEnRango(int[] vector, int númeroABuscar, int inicio, int fin, int identificador, AtomicBoolean numeroEncontrado) {
        for (int i = inicio; i <= fin; i++) {
            if (vector[i] == númeroABuscar) {
                System.out.println("Hilo/Proceso " + identificador + " encontró el número " + númeroABuscar + " en la posición " + i);
                numeroEncontrado.set(true);
                return;
            }
        }
    }

    // Método para solicitar al usuario un entero
    private static int pedirEntero(String mensaje) {
        Scanner scanner = new Scanner(System.in);
        int entero = 0;
        boolean entradaValida = false;

        do {
            try {
                System.out.print(mensaje);
                entero = Integer.parseInt(scanner.nextLine());
                entradaValida = true;
            } catch (NumberFormatException e) {
                System.out.println("Error: Ingrese un número válido.");
            }
        } while (!entradaValida);

        return entero;
    }
}

class BusquedaThread implements Runnable {
    private int[] vector;
    private int númeroABuscar;
    private int inicio;
    private int fin;
    private int identificador;
    private AtomicBoolean numeroEncontrado;

    public BusquedaThread(int[] vector, int númeroABuscar, int inicio, int fin, int identificador, AtomicBoolean numeroEncontrado) {
        this.vector = vector;
        this.númeroABuscar = númeroABuscar;
        this.inicio = inicio;
        this.fin = fin;
        this.identificador = identificador;
        this.numeroEncontrado = numeroEncontrado;
    }

    @Override
    public void run() {
        buscarEnRango(vector, númeroABuscar, inicio, fin, identificador, numeroEncontrado);
    }

    private void buscarEnRango(int[] vector, int númeroABuscar, int inicio, int fin, int identificador, AtomicBoolean numeroEncontrado) {
        for (int i = inicio; i <= fin; i++) {
            if (vector[i] == númeroABuscar) {
                System.out.println("Hilo " + identificador + " encontró el número " + númeroABuscar + " en la posición " + i);
                numeroEncontrado.set(true);
                return;
            }
        }
    }
}
