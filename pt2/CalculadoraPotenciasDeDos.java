package pt2;

import java.util.Scanner;

public class CalculadoraPotenciasDeDos {
    public static void main(String[] args) {
        // Solicito al usuario el número hasta el cual se calcularán las potencias de 2
        int limiteSuperior = pedirEntero("Ingrese el número hasta el cual se calcularán las potencias de 2: ");

        // Creo un arreglo para almacenar los resultados de las potencias
        long[] resultados = new long[limiteSuperior];

        // Creo un arreglo para almacenar los hilos
        Thread[] hilos = new Thread[limiteSuperior];

        // Imprimo un mensaje sobre la creación de hilos
        System.out.println("Creando hilos para calcular potencias de 2 hasta " + (limiteSuperior - 1));

        // Itero a través de los hilos para calcular las potencias de 2
        for (int i = 0; i < limiteSuperior; i++) {
            hilos[i] = new Thread(new HiloPotenciaDeDos(i, resultados));
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

        // Imprimo los resultados en orden
        for (int i = 0; i < limiteSuperior; i++) {
            System.out.println("2^" + i + " = " + resultados[i]);
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

class HiloPotenciaDeDos implements Runnable {
    private int exponente;
    private long[] resultados;

    public HiloPotenciaDeDos(int exponente, long[] resultados) {
        this.exponente = exponente;
        this.resultados = resultados;
    }

    @Override
    public void run() {
        // Calculo la potencia de 2 y la almaceno en el array de resultados
        resultados[exponente] = (long) Math.pow(2, exponente);
    }
}
