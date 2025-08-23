
import java.util.*;

public class Simulacion {
    private static int aux = 0;
    private static Random rand = new Random();
        public static void main(String[] args) {
        System.out.println("Inicio de la simulación...");
        try {
            while(aux<1000){
                if(rand.nextBoolean()) {
                    Thread.sleep(6000);
                    continue;
                }
                Paciente p = new Paciente("Paciente" + aux, 30 + aux % 10, "ID" + aux, "Dirección" + aux, 1 + aux % 5);
                System.out.println("Insertando: " + p);
                ABinBusPrioridad arbol = new ABinBusPrioridad();
                arbol.insert(p);
                System.out.println("Paciente insertado: " + p);
                aux++;
                System.out.println("Esperando 6 segundos antes de insertar el siguiente paciente...");
                System.out.println("Pacientes en espera hasta ahora: " + aux);
                System.out.println(arbol.printTree()+"\n");

                if(aux % 5 == 0) {
                    System.out.println("Atendiendo pacientes...");
                    Paciente atendido = arbol.pollNext();
                    if (atendido != null) {
                        System.out.println("Paciente atendido: " + atendido);
                    } else {
                        System.out.println("No hay pacientes para atender.");
                    }
                }

            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Fin de la simulación.");
    }
}
