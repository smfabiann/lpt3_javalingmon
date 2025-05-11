import java.util.Random;

public class Utilidades {
    // se reutilizara en utilidades y solo en utilidades
    private static final Random random = new Random();

    // printea lento
    public static void slowPrint(String texto) {
        for (char c: texto.toCharArray()) {
            System.out.print(c);

            // colocamos un delay random entre cada letra, para que se sienta mas natural
            // int delay = random.nextInt(10);
            int delay = random.nextInt(1);
            // se añade el delay, try porque sino no me deja usar el sleep
            try {
                Thread.sleep(delay);
            } catch (InterruptedException exception) {
                Thread.currentThread().interrupt();
            }
        }

        return;
    }
    // sobrecarga de la funcion pero añadimos la capasidad de aceptar un color
    public static void slowPrint(String texto, String color) {
        String ansiColor = " ";

        switch (color.toLowerCase()) {
            case "rojo":
                ansiColor = "\u001B[31m"; break;
            case "verde":
                ansiColor = "\u001B[32m"; break;
            case "amarillo":
                ansiColor = "\u001B[33m"; break;
            case "azul":
                ansiColor = "\u001B[34m"; break;
            case "magenta":
                ansiColor = "\u001B[35m"; break;
            case "cyan":
                ansiColor = "\u001B[36m"; break;
            case "blanco":
                ansiColor = "\u001B[37m"; break;
            case "naranjo":
            case "naranja":
                ansiColor = "\u001B[38;5;208m"; break;  // color extendido
            default:
                ansiColor = "\u001B[0m"; break; // reset
        }
        

        // cambiamos el color del texto
        System.out.print(ansiColor);

        // copiado y pegado de la funcion anterior
        for (char c: texto.toCharArray()) {
            System.out.print(c);

            // colocamos un delay random entre cada letra, para que se sienta mas natural
            int delay = random.nextInt(1);
            // int delay = random.nextInt(20);
            // se añade el delay, try porque sino no me deja usar el sleep
            try {
                Thread.sleep(delay);
            } catch (InterruptedException exception) {
                Thread.currentThread().interrupt();
            }
        }

        System.out.print("\u001B[0m");

        return;
    }

    // cambia el color del print manuealmente
    public static void setPrintColor(String color) {
        String ansiColor = "";
        switch (color.toLowerCase()) {
            case "rojo":
                ansiColor = "\u001B[31m"; break;
            case "verde":
                ansiColor = "\u001B[32m"; break;
            case "amarillo":
                ansiColor = "\u001B[33m"; break;
            case "azul":
                ansiColor = "\u001B[34m"; break;
            case "magenta":
                ansiColor = "\u001B[35m"; break;
            case "cyan":
                ansiColor = "\u001B[36m"; break;
            case "blanco":
                ansiColor = "\u001B[37m"; break;
            case "naranjo":
            case "naranja":
                ansiColor = "\u001B[38;5;208m"; break;  // color extendido
            default:
                ansiColor = "\u001B[0m"; break; // reset
        }
        // se cambia el color
        System.out.print(ansiColor);
    }

    // pausa el programa
    public static void sleep(int milisegundos) {
        try {
            Thread.sleep(milisegundos);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    
    // numero random entre intervalos
    public static int randInt(int min, int max) {
        return random.nextInt((max - min) + 1) + min;
    }

    // boleano random
    public static boolean randomNextBool() {
        return random.nextBoolean();
    }
}
