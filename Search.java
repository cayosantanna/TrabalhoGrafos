/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */

import java.util.Arrays;

public class Search {
    /* encontra o vértice com a menor distancia que não foi verticeVisitado*/
    public static int menorDistancia(int[] distancia, boolean[] verticeVisitado, int quantVertice) {
        int menor = Integer.MAX_VALUE, indiceVMenorDist = -1;
        /*Inicializa menor como o maior valor de um int já que estamos buscando o menor valor.*/
        for (int i = 0; i < quantVertice; i++) {
            if (!verticeVisitado[i] && distancia[i] <= menor) {
                menor = distancia[i];
                indiceVMenorDist = i;
            }
        }
        return indiceVMenorDist;
        /*Retorna o índice do vértice com a menor distância não processada.*/
    }

    
    public static void dijkstra(int[][] grafo, int origem, int quantVertice) {
        int[] distancia = new int[quantVertice];
        /* Cria um array para armazenar as distâncias mínimas da origem para os outros vértices.*/
        boolean[] verticeVisitado = new boolean[quantVertice]; 
        /*Array para verificar quais períodos já foram verticeVisitados*/
        int[] anterior = new int[quantVertice]; 
        /*Armazena o vértice anterior no caminho mais curto para reconstruir o caminho depois.*/

        /* Inicializa as distâncias como infinitas e verticeVisitados como false*/
        Arrays.fill(distancia, Integer.MAX_VALUE); 
        Arrays.fill(verticeVisitado, false);
        distancia[origem] = 0; /* A distância da origem para si mesma é 0*/
        anterior[origem] = -1; /* A origem não tem um vértice anterior*/

        System.out.println("Iniciando o calculo do menor caminho a partir do periodo " + (origem + 1) + "...\n");

        /* Processa todos os vértices*/
        for (int i = 0; i < quantVertice - 1; i++) {
            /* Encontra o vértice com a menor distância*/
            int verticeY = menorDistancia(distancia, verticeVisitado, quantVertice); 
            /*chamada de função para encontrar o próximo vértice com a menor distanciaância não viitado*/
            if (verticeY == -1) break; /* Para se não houver mais vértices acessíveis */ 
            verticeVisitado[verticeY] = true; /* Marca o vértice como verticeVisitado */

            System.out.println("Vertice " + (verticeY + 1) + " foi rotulado com a distanciaancia minima: " + distancia[verticeY]);

            /* Atualiza os valores das distanciaâncias dos vértices adjacentes do vértice escolhido*/
            for (int verticeX = 0; verticeX < quantVertice; verticeX++) {
                if (!verticeVisitado[verticeX] && grafo[verticeY][verticeX] != Integer.MAX_VALUE 
                        && distancia[verticeY] != Integer.MAX_VALUE && distancia[verticeY] + grafo[verticeY][verticeX] < distancia[verticeX]) {
                    System.out.println("Atualizando distanciaancia do periodo " + (verticeX + 1) + ": " +
                            "antes = " + distancia[verticeX] + ", depois = " + (distancia[verticeY] + grafo[verticeY][verticeX]));
                    distancia[verticeX] = distancia[verticeY] + grafo[verticeY][verticeX];
                    anterior[verticeX] = verticeY; /* Atualiza o caminho */
                }
            }
            System.out.println();
            /*Exibe qual vértice foi rotulado com a menor distância.*/
        }

        /* Exibe o caminho e as distâncias encontradas */
        printResultado(distancia, anterior, origem, quantVertice);
    }

    /* Função para imprimir o caminho e as distâncias mínimas*/
    public static void printResultado(int[] distancia, int[] anterior, int origem, int quantVertice) {
        System.out.println("\nResultado final:");
        System.out.println("Distancias a partir do periodo " + (origem + 1) + ":");
        for (int i = 0; i < quantVertice; i++) {
            if (distancia[i] == Integer.MAX_VALUE) {
                System.out.println("Período " + (i + 1) + " nao eh acessível a partir do periodo " + (origem + 1));
            } else {
                System.out.print("Menor custo para o periodo " + (i + 1) + " = " + distancia[i] + " | Caminho: ");
                printCaminho(i, anterior);  /*Chamada de função para exibir o caminho mais curto até o vértice i*/
                System.out.println();
            }
        }

        /*Exibindo o caminho mais curto final para o último vértice*/
        System.out.print("\nCaminho mais curto ate o ultimo periodo (6): ");
        printCaminho(quantVertice - 1, anterior);
        System.out.println("\nCusto total: " + distancia[quantVertice - 1]);
    }

    /* Função recursiva para imprimir o caminho*/
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
