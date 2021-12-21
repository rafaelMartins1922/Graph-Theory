package tests;

import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

import org.junit.Test;

import main.analysis.GraphAnalysis;
import main.classes.AdjacentList;
import main.classes.AdjacentMatrix;

public class TestGraph {

    public static void main(String[] args) {
        testGraph();
    }

    @Test
    static void testGraph(){
        DFS1000Matrix();
    }

    static void caseStudy_1() {
        Scanner consoleScanner = new Scanner(System.in);
        for (int i = 1; i <=3; i++) {
            readAdjMatrix(i);
        }

        for (int i = 1; i <=3; i++) {
            readAdjList(i);
        }
    }

    static void caseStudy_2() {
    }

    static AdjacentList readAdjList(int number) {
        
        String inputFile = "grafo_" + number + ".txt";
        AdjacentList graphAdjList = new AdjacentList(GraphAnalysis.SetGraphData(inputFile));
        Runtime.getRuntime().gc();
        System.out.println("Pausando");
        try {
            Thread.sleep(30 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return graphAdjList;
    }

    static AdjacentMatrix readAdjMatrix(int number) {
        String inputFile = "grafo_" + number + ".txt";
        AdjacentMatrix graphAdjMatrix = new AdjacentMatrix(GraphAnalysis.SetGraphData(inputFile));
        Runtime.getRuntime().gc();
        System.out.println("Pausando");
        try {
            Thread.sleep(30 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return graphAdjMatrix;
    }

    static void BFS1000List() {
        String inputFile = "grafo_1.txt";
        AdjacentList graphAdjList = new AdjacentList(GraphAnalysis.SetGraphData(inputFile));
        
        long pastTime = System.currentTimeMillis();

        for (int j = 1; j <= 100; j++){
            for (int k = 1; k <= 10; k++) {
                GraphAnalysis.BFS(graphAdjList, null, j);
            }
        }

        long timeElapsed = System.currentTimeMillis() - pastTime;
        System.out.println("1000 buscas no grafo 1 " + timeElapsed + "ms");

        inputFile = "grafo_2.txt";
        graphAdjList = new AdjacentList(GraphAnalysis.SetGraphData(inputFile));
        Runtime.getRuntime().gc();
        pastTime = System.currentTimeMillis();

        for (int i = 1; i <= 968; i++){
            GraphAnalysis.BFS(graphAdjList, null, i);
        }

        for (int i = 1; i <= 32; i++){
            GraphAnalysis.BFS(graphAdjList, null, i);
        }
        
        timeElapsed = System.currentTimeMillis() - pastTime;
        System.out.println("1000 buscas no grafo 2 " + timeElapsed + "ms");

        
        inputFile = "grafo_3.txt";
        graphAdjList = new AdjacentList(GraphAnalysis.SetGraphData(inputFile));
        Runtime.getRuntime().gc();
        pastTime = System.currentTimeMillis();

        for (int i = 1; i <= 1000; i++){
            GraphAnalysis.BFS(graphAdjList, null, i);
        }

        timeElapsed = System.currentTimeMillis() - pastTime;
        System.out.println("1000 buscas no grafo 3: " + timeElapsed + "ms");

        System.out.println("Lendo grafo 4");
        inputFile = "grafo_4.txt";
        graphAdjList = new AdjacentList(GraphAnalysis.SetGraphData(inputFile));
        Runtime.getRuntime().gc();
        pastTime = System.currentTimeMillis();

        for (int i = 1; i <= 1000; i++){
            GraphAnalysis.BFS(graphAdjList, null, i);
        }

        timeElapsed = System.currentTimeMillis() - pastTime;
        System.out.println("1000 buscas no grafo 4: " + timeElapsed + "ms");
    }

    static void BFS1000Matrix() {
        String inputFile = "grafo_1.txt";
        AdjacentMatrix graphAdjMatrix = new AdjacentMatrix(GraphAnalysis.SetGraphData(inputFile));
        
        long pastTime = System.currentTimeMillis();

        for (int j = 1; j <= 100; j++){
            for (int k = 1; k <= 10; k++) {
                GraphAnalysis.BFS(null, graphAdjMatrix, j);
            }
        }

        long timeElapsed = System.currentTimeMillis() - pastTime;
        System.out.println("1000 buscas no grafo 1 " + timeElapsed + "ms");

        inputFile = "grafo_2.txt";
        graphAdjMatrix = new AdjacentMatrix(GraphAnalysis.SetGraphData(inputFile));
        Runtime.getRuntime().gc();
        pastTime = System.currentTimeMillis();

        for (int i = 1; i <= 968; i++){
            GraphAnalysis.BFS(null, graphAdjMatrix, i);
        }

        for (int i = 1; i <= 32; i++){
            GraphAnalysis.BFS(null, graphAdjMatrix, i);
        }
        
        timeElapsed = System.currentTimeMillis() - pastTime;
        System.out.println("1000 buscas no grafo 2 " + timeElapsed + "ms");

        
        inputFile = "grafo_3.txt";
        graphAdjMatrix = new AdjacentMatrix(GraphAnalysis.SetGraphData(inputFile));
        Runtime.getRuntime().gc();
        pastTime = System.currentTimeMillis();

        for (int i = 1; i <= 1000; i++){
            GraphAnalysis.BFS(null, graphAdjMatrix, i);
        }

        timeElapsed = System.currentTimeMillis() - pastTime;
        System.out.println("1000 buscas no grafo 3: " + timeElapsed + "ms");

    //     System.out.println("Lendo grafo 4");
    //     inputFile = "grafo_4.txt";
    //     graphAdjMatrix = new AdjacentMatrix(GraphAnalysis.SetGraphData(inputFile));
    //     Runtime.getRuntime().gc();
    //     pastTime = System.currentTimeMillis();

    //     for (int i = 1; i <= 1000; i++){
    //         GraphAnalysis.BFS(null, graphAdjMatrix, i);
    //     }

    //     timeElapsed = System.currentTimeMillis() - pastTime;
    //     System.out.println("1000 buscas no grafo 4: " + timeElapsed + "ms");
    }

    static void DFS1000List() {
        String inputFile = "grafo_1.txt";
        AdjacentList graphAdjList = new AdjacentList(GraphAnalysis.SetGraphData(inputFile));
        
        long pastTime = System.currentTimeMillis();

        for (int j = 1; j <= 100; j++){
            for (int k = 1; k <= 10; k++) {
                GraphAnalysis.DFS(graphAdjList, null, j);
            }
        }

        long timeElapsed = System.currentTimeMillis() - pastTime;
        System.out.println("1000 buscas no grafo 1 " + timeElapsed + "ms");

        inputFile = "grafo_2.txt";
        graphAdjList = new AdjacentList(GraphAnalysis.SetGraphData(inputFile));
        Runtime.getRuntime().gc();
        pastTime = System.currentTimeMillis();

        for (int i = 1; i <= 968; i++){
            GraphAnalysis.DFS(graphAdjList, null, i);
        }

        for (int i = 1; i <= 32; i++){
            GraphAnalysis.DFS(graphAdjList, null, i);
        }
        
        timeElapsed = System.currentTimeMillis() - pastTime;
        System.out.println("1000 buscas no grafo 2 " + timeElapsed + "ms");

        
        inputFile = "grafo_3.txt";
        graphAdjList = new AdjacentList(GraphAnalysis.SetGraphData(inputFile));
        Runtime.getRuntime().gc();
        pastTime = System.currentTimeMillis();

        for (int i = 1; i <= 1000; i++){
            GraphAnalysis.DFS(graphAdjList, null, i);
        }

        timeElapsed = System.currentTimeMillis() - pastTime;
        System.out.println("1000 buscas no grafo 3: " + timeElapsed + "ms");

        // System.out.println("Lendo grafo 4");
        // inputFile = "grafo_4.txt";
        // graphAdjList = new AdjacentList(GraphAnalysis.SetGraphData(inputFile));
        // Runtime.getRuntime().gc();
        // pastTime = System.currentTimeMillis();

        // for (int i = 1; i <= 1000; i++){
        //     GraphAnalysis.DFS(graphAdjList, null, i);
        // }

        // timeElapsed = System.currentTimeMillis() - pastTime;
        // System.out.println("1000 buscas no grafo 4: " + timeElapsed + "ms");
    }

    static void DFS1000Matrix() {
        String inputFile = "grafo_1.txt";
        AdjacentMatrix graphAdjMatrix = new AdjacentMatrix(GraphAnalysis.SetGraphData(inputFile));
        
        long pastTime = System.currentTimeMillis();

        for (int j = 1; j <= 100; j++){
            for (int k = 1; k <= 10; k++) {
                GraphAnalysis.DFS(null, graphAdjMatrix, j);
            }
        }

        long timeElapsed = System.currentTimeMillis() - pastTime;
        System.out.println("1000 buscas no grafo 1 " + timeElapsed + "ms");

        inputFile = "grafo_2.txt";
        graphAdjMatrix = new AdjacentMatrix(GraphAnalysis.SetGraphData(inputFile));
        Runtime.getRuntime().gc();
        pastTime = System.currentTimeMillis();

        for (int i = 1; i <= 968; i++){
            GraphAnalysis.DFS(null, graphAdjMatrix, i);
        }

        for (int i = 1; i <= 32; i++){
            GraphAnalysis.DFS(null, graphAdjMatrix, i);
        }
        
        timeElapsed = System.currentTimeMillis() - pastTime;
        System.out.println("1000 buscas no grafo 2 " + timeElapsed + "ms");

        
        inputFile = "grafo_3.txt";
        graphAdjMatrix = new AdjacentMatrix(GraphAnalysis.SetGraphData(inputFile));
        Runtime.getRuntime().gc();
        pastTime = System.currentTimeMillis();

        for (int i = 1; i <= 1000; i++){
            GraphAnalysis.DFS(null, graphAdjMatrix, i);
        }

        timeElapsed = System.currentTimeMillis() - pastTime;
        System.out.println("1000 buscas no grafo 3: " + timeElapsed + "ms");

        // System.out.println("Lendo grafo 4");
        // inputFile = "grafo_4.txt";
        // graphAdjMatrix = new AdjacentMatrix(GraphAnalysis.SetGraphData(inputFile));
        // Runtime.getRuntime().gc();
        // pastTime = System.currentTimeMillis();

        // for (int i = 1; i <= 1000; i++){
        //     GraphAnalysis.DFS(null, graphAdjMatrix, i);
        // }

        // timeElapsed = System.currentTimeMillis() - pastTime;
        // System.out.println("1000 buscas no grafo 4: " + timeElapsed + "ms");
    }
}
