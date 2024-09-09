
import java.util.Arrays;

public class Search {
    public static int menorDistancia(int[] distancia, boolean[] verticeVisitado, int quantVertice) {
        int menor = Integer.MAX_VALUE, indiceVMenorDist = -1;
        for (int i = 0; i < quantVertice; i++) {
            if (!verticeVisitado[i] && distancia[i] <= menor) {
                menor = distancia[i];
                indiceVMenorDist = i;
            }
        }
        return indiceVMenorDist;
    }
    
    public static void dijkstra(int[][] grafo, int origem, int quantVertice) {
        int[] distancia = new int[quantVertice];
        boolean[] verticeVisitado = new boolean[quantVertice]; 
        int[] anterior = new int[quantVertice]; 
        Arrays.fill(distancia, Integer.MAX_VALUE); 
        Arrays.fill(verticeVisitado, false);
        distancia[origem] = 0; 
        anterior[origem] = -1; 

        System.out.println("Iniciando o calculo do menor caminho a partir do periodo " + (origem + 1) + "...\n");

        
        for (int i = 0; i < quantVertice - 1; i++) {
            int verticeY = menorDistancia(distancia, verticeVisitado, quantVertice); 
            if (verticeY == -1) break; 
            verticeVisitado[verticeY] = true; 
            System.out.println("Vertice " + (verticeY + 1) + " foi rotulado com a distanciaancia minima: " + distancia[verticeY]);
            
            for (int verticeX = 0; verticeX < quantVertice; verticeX++) {
                if (!verticeVisitado[verticeX] && grafo[verticeY][verticeX] != Integer.MAX_VALUE 
                        && distancia[verticeY] != Integer.MAX_VALUE && distancia[verticeY] + grafo[verticeY][verticeX] < distancia[verticeX]) {
                    System.out.println("Atualizando distanciaancia do periodo " + (verticeX + 1) + ": " +
                            "antes = " + distancia[verticeX] + ", depois = " + (distancia[verticeY] + grafo[verticeY][verticeX]));
                    distancia[verticeX] = distancia[verticeY] + grafo[verticeY][verticeX];
                    anterior[verticeX] = verticeY; 
                }
            }
            System.out.println();
        }
        printResultado(distancia, anterior, origem, quantVertice);
    }
    
    public static void printResultado(int[] distancia, int[] anterior, int origem, int quantVertice) {
        System.out.println("\nResultado final:");
        System.out.println("Distancias a partir do periodo " + (origem + 1) + ":");
        for (int i = 0; i < quantVertice; i++) {
            if (distancia[i] == Integer.MAX_VALUE) {
                System.out.println("Período " + (i + 1) + " nao eh acessível a partir do periodo " + (origem + 1));
            } else {
                System.out.print("Menor custo para o periodo " + (i + 1) + " = " + distancia[i] + " | Caminho: ");
                printCaminho(i, anterior); 
                System.out.println();
            }
        }
        System.out.print("\nCaminho mais curto ate o ultimo periodo (6): ");
        printCaminho(quantVertice - 1, anterior);
        System.out.println("\nCusto total: " + distancia[quantVertice - 1]);
    }
    public static void printCaminho(int atual, int[] anterior) {
        if (anterior[atual] == -1) {
            System.out.print((atual + 1));
            return;
        }
        printCaminho(anterior[atual], anterior);
        System.out.print(" -> " + (atual + 1));
    }

    public static void main(String[] args) {
        int n = 6;
        
        /* Exemplo do artigo (matriz de adjacência representando os custos)*/
        int[][] grafo = {
                {0, 10, 11, 6, Integer.MAX_VALUE, Integer.MAX_VALUE},
                {Integer.MAX_VALUE, 0, Integer.MAX_VALUE, 2, 6, Integer.MAX_VALUE},
                {Integer.MAX_VALUE, Integer.MAX_VALUE, 0, Integer.MAX_VALUE, 2, 4},
                {Integer.MAX_VALUE, Integer.MAX_VALUE, 3, 0, 3, Integer.MAX_VALUE},
                {Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, 0, 3},
                {Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, 0}
        }; /*Integer.MAX_VALUE é o maior valor que um inteiro em Java pode armazenar (2^31 - 1)
             Ele é usado aqui para representar distancias "infinitas"*/

        /* Executa o algoritmo de Dijkstra a partir do período 1 (índice 0)*/
        dijkstra(grafo, 0, n);
    }
}
