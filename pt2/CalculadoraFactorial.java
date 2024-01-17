package pt2;

import java.util.Scanner;

public class CalculadoraFactorial {
    public static void main(String[] args) {
        // Solicito al usuario que ingrese el número de hilos
        int numeroDeHilos = pedirEntero("Ingrese el número de hilos: ");

        // Creo un arreglo para almacenar los hilos
        Thread[] hilos = new Thread[numeroDeHilos];

        // Creo un solo Scanner para la entrada de usuario
        Scanner scanner = new Scanner(System.in);

        // Itero a través de los hilos para calcular factoriales
        for (int i = 0; i < numeroDeHilos; i++) {
            try {
                // Solicito al usuario un número para calcular el factorial
                System.out.print("Ingrese el número para calcular el factorial (hilo " + (i + 1) + "): ");
                int num = scanner.nextInt();

                // Imprimo un mensaje sobre la creación del hilo
                System.out.println("Creando hilo para el factorial de " + num);

                // Creo un nuevo hilo y lo ejecuto
                hilos[i] = new Thread(new HiloFactorial(num));
                hilos[i].start();

                // Espero a que el hilo termine antes de continuar con el siguiente
                hilos[i].join();
            } catch (InterruptedException e) {
                // Capturo errores si hay un problema con el hilo
                System.out.println("Error: Problema con el hilo.");
            }
        }

        // Cierro el Scanner al final para evitar posibles fugas de recursos
        scanner.close();
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

class HiloFactorial implements Runnable {
    private int num;

    public HiloFactorial(int num) {
        this.num = num;
    }

    @Override
    public void run() {
        // Calculo el factorial
        long factorial = calcularFactorial(num);

        // Imprimo el resultado
        System.out.println("Factorial de " + num + ": " + factorial);
    }

    private long calcularFactorial(int num) {
        // Función para calcular el factorial de un número
        if (num <= 1) {
            return 1;
        } else {
            return num * calcularFactorial(num - 1);
        }
    }
}
