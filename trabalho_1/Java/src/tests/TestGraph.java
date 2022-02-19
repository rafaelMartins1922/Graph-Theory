package tests;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.Scanner;

import main.analysis.GraphAnalysis;
import main.classes.AdjacentList;
import main.classes.AdjacentMatrix;
import main.classes.Edge;

import java.io.FileWriter;
import java.io.IOException;
public class TestGraph {
    public static void main(String[] args) {
        tp2_caseStudy1();
    }
    

    public static void tp2_caseStudy1() {
        String outputFilePath = GraphAnalysis.CreateOutputFile("tp2_caseStudy1");
        try {
            FileWriter fileWriter = new FileWriter(outputFilePath);
            fileWriter.write("Caso de Estudo 1:\n\n");
            fileWriter.write("Distâncias pela BFS (sem considerar os pesos)\n");

            for(int i = 1; i <= 1; i++){
                fileWriter.write("  Grafo " + i + '\n');
                AdjacentList graphAdjList = new AdjacentList(GraphAnalysis.SetGraphData("grafo_W_"+i+"_1.txt"));
                HashMap<String,int[]> bfsData = GraphAnalysis.BFS(graphAdjList, null, 1);
                fileWriter.write("  Entre os vértices 1 e 10: " + bfsData.get("levels")[10] + '\n');
                fileWriter.write("  Entre os vértices 1 e 20: " + bfsData.get("levels")[20] + '\n');
                fileWriter.write("  Entre os vértices 1 e 30: " + bfsData.get("levels")[30] + '\n');
                fileWriter.write("  Entre os vértices 1 e 40: " + bfsData.get("levels")[40] + '\n');
                fileWriter.write("  Entre os vértices 1 e 50: " + bfsData.get("levels")[50] + '\n');
                fileWriter.write("\n");
            }

            fileWriter.write("\nDistâncias pelo algoritmo de Dijkstra\n");

            for(int i = 1; i <= 1; i++){
                fileWriter.write("  Grafo " + i + '\n');
                AdjacentList graphAdjList = new AdjacentList(GraphAnalysis.SetGraphData("grafo_W_"+i+"_1.txt"));
                HashMap<String,float[]> dijkstraData = GraphAnalysis.Dijkstra(graphAdjList, null, 1);
                fileWriter.write("  Entre os vértices 1 e 10: " + dijkstraData.get("distances")[10] + '\n');
                fileWriter.write("  Entre os vértices 1 e 20: " + dijkstraData.get("distances")[20] + '\n');
                fileWriter.write("  Entre os vértices 1 e 30: " + dijkstraData.get("distances")[30] + '\n');
                fileWriter.write("  Entre os vértices 1 e 40: " + dijkstraData.get("distances")[40] + '\n');
                fileWriter.write("  Entre os vértices 1 e 50: " + dijkstraData.get("distances")[50] + '\n');
                fileWriter.write("\n");
            }

            fileWriter.close();
        } catch (IOException e) {
            System.out.println("Erro.");
            e.printStackTrace();
        }
    }

    public static void tp2_caseStudy2(){
        String outputFilePath = GraphAnalysis.CreateOutputFile("tp2_caseStudy2");
        Random rand = new Random();
        try{
            FileWriter fileWriter = new FileWriter(outputFilePath);
            fileWriter.write("Caso de Estudo 2:\n\n");
            for(int i = 1; i<=3; i++){
                fileWriter.write("  Grafo " + i + '\n');

                AdjacentList graphAdjList = new AdjacentList(GraphAnalysis.SetGraphData("grafo_W_"+i+"_1.txt"));
                long timeBeforeBFS = System.currentTimeMillis();
                for(int j = 0; j < 100; j++){
                    GraphAnalysis.BFS(graphAdjList, null, rand.nextInt(graphAdjList.Graph.length-1)+1);
                }
                long timeAfterBFS = System.currentTimeMillis();
                fileWriter.write("  Tempo médio de 100 buscas BFS para achar distância de um vértice a todos os outros do grafo: " + (timeAfterBFS-timeBeforeBFS)/100 + "ms\n");

                if(i !=3) {
                    for(int j = 0; j < 100; j++){
                        GraphAnalysis.Dijkstra(graphAdjList, null, rand.nextInt(graphAdjList.Graph.length-1)+1);
                    }
    
                    long timeAfterDijsktra = System.currentTimeMillis();
                    fileWriter.write("  Tempo médio de 100 buscas com o algoritmo de Dijsktra para achar distância de um vértice a todos os outros do grafo: " + (timeAfterDijsktra-timeAfterBFS)/100 + "ms\n");
    
                } else {
                    for (int j = 0; j < 1; j++){
                        GraphAnalysis.Dijkstra(graphAdjList, null, rand.nextInt(graphAdjList.Graph.length-1)+1);
                    }
                    long timeAfterDijsktra = System.currentTimeMillis();
                    fileWriter.write("  Tempo de dez buscas com o algoritmo de Dijsktra para achar distância de um vértice a todos os outros do grafo: " + (timeAfterDijsktra-timeAfterBFS) + "ms\n");
    
                }
            }
            fileWriter.close();
        } catch (IOException e) {
            System.out.println("Erro.");
            e.printStackTrace();
        }
    }

    public static void tp2_caseStudy3(){
        try{
            for(int i = 1; i<=3; i++){
                String outputFilePath = GraphAnalysis.CreateOutputFile("mst_grafo_W_" + i + "_1");
                FileWriter fileWriter = new FileWriter(outputFilePath);

                AdjacentList graphAdjList = new AdjacentList(GraphAnalysis.SetGraphData("grafo_W_"+i+"_1.txt"));
                
                AdjacentList mst = GraphAnalysis.Kruskal(graphAdjList,null);
                float mstWeight = 0;
                for(Edge e : mst.Edges) {
                    mstWeight+=e.weight;
                    fileWriter.write(e.firstNode + " " + e.secondNode + '\n');
                }
                fileWriter.close();
                System.out.println("Peso da MST de grafo_W_" + i + "_1: " + mstWeight);
            }
            
        } catch (IOException e) {
            System.out.println("Erro.");
            e.printStackTrace();
        }
    }
   
    public static void tp2_caseStudy4(){
        try{
            for(int i = 1; i<=3; i++){
                String outputFilePath = GraphAnalysis.CreateOutputFile("tp2_caseStudy4.txt");
                FileWriter fileWriter = new FileWriter(outputFilePath);
                AdjacentList graph = new AdjacentList(GraphAnalysis.SetGraphData("rede_colaboracao.txt"));
                HashMap<String, float[]> dijkstraData = GraphAnalysis.Dijkstra(graph, null, 2722);
                fileWriter.write("Distância entre Edsger W. Dijkstra e Alan M. Turing: " + dijkstraData.get("distances")[11365] + '\n');
                fileWriter.write("Distância entre Edsger W. Dijkstra e J. B. Kruskal: " + dijkstraData.get("distances")[471365] + '\n');
                fileWriter.write("Distância entre Edsger W. Dijkstra e Jon M. Kleinberg: " + dijkstraData.get("distances")[5709] + '\n');
                fileWriter.write("Distância entre Edsger W. Dijkstra e Éva Tardos: " + dijkstraData.get("distances")[11386] + '\n');
                fileWriter.write("Distância entre Edsger W. Dijkstra e Daniel R. Figueiredo: " + dijkstraData.get("distances")[343930] + "\n\n");

                int target = 11365;
                float parent = dijkstraData.get("parents")[target];
                fileWriter.write("Caminho mínimo entre Dijkstra e Turing: \n");
                while(parent != 2722) {
                    fileWriter.write((int) parent + '\n');
                    parent = dijkstraData.get("parents")[(int) parent];
                }
                fileWriter.write("\n");

                target = 471365;
                parent = dijkstraData.get("parents")[target];
                fileWriter.write("Caminho mínimo entre Dijkstra e Kruskal: \n");
                while(parent != 2722) {
                    fileWriter.write((int) parent + '\n');
                    parent = dijkstraData.get("parents")[(int) parent];
                }
                fileWriter.write("\n");

                target = 5709;
                parent = dijkstraData.get("parents")[target];
                fileWriter.write("Caminho mínimo entre Dijkstra e Kleinberg: \n");
                while(parent != 2722) {
                    fileWriter.write((int) parent + '\n');
                    parent = dijkstraData.get("parents")[(int) parent];
                }
                fileWriter.write("\n");

                target = 11386;
                parent = dijkstraData.get("parents")[target];
                fileWriter.write("Caminho mínimo entre Dijkstra e Tardos: \n");
                while(parent != 2722) {
                    fileWriter.write((int) parent + '\n');
                    parent = dijkstraData.get("parents")[(int) parent];
                }
                fileWriter.write("\n");

                target = 343930;
                parent = dijkstraData.get("parents")[target];
                fileWriter.write("Caminho mínimo entre Dijkstra e Figueiredo: \n");
                while(parent != 2722) {
                    fileWriter.write((int) parent + '\n');
                    parent = dijkstraData.get("parents")[(int) parent];
                }
                fileWriter.write("\n");
                fileWriter.close();
            }
            
        } catch (IOException e) {
            System.out.println("Erro.");
            e.printStackTrace();
        }
    }
    
    public static void floydWarshallTest() {
        AdjacentList graphAdjList = new AdjacentList(GraphAnalysis.SetGraphData("grafo_W_1_1.txt"));
        float[][] distances = GraphAnalysis.FloydWarshall(graphAdjList, null);
        System.out.println("Distância entre os vértices 1 e 10: " + distances[1][10]);
        System.out.println("Distância entre os vértices 1 e 20: " + distances[1][20]);
        System.out.println("Distância entre os vértices 1 e 30: " + distances[1][30]);
        System.out.println("Distância entre os vértices 1 e 40: " + distances[1][40]);
        System.out.println("Distância entre os vértices 1 e 50: " + distances[1][50]);
    }
    public static void testDijkstra(){
        long firstTime = System.currentTimeMillis();
        AdjacentList graphAdjList = new AdjacentList(GraphAnalysis.SetGraphData("grafo_W_2_1.txt"));
        long secondTime = System.currentTimeMillis();
        HashMap<String,float[]> dijkstraData = GraphAnalysis.Dijkstra(graphAdjList, null, 1);
        long thirdTime = System.currentTimeMillis();

        long readingTime = secondTime-firstTime;
        long operationTime = thirdTime-secondTime;
        long totaltime= readingTime+operationTime;

        System.out.println("Tempo de leitura: " + readingTime);
        System.out.println("Tempo de operação: " + operationTime);
        System.out.println("Tempo total: " + totaltime);
    }

    public static void testBFS(){
        long firstTime = System.currentTimeMillis();
        AdjacentList graphAdjList = new AdjacentList(GraphAnalysis.SetGraphData("grafo_W_2_1.txt"));
        long secondTime = System.currentTimeMillis();
        HashMap<String,int[]> bfsData = GraphAnalysis.BFS(graphAdjList, null, 1);
        long thirdTime = System.currentTimeMillis();

        long readingTime = secondTime-firstTime;
        long operationTime = thirdTime-secondTime;
        long totaltime= readingTime+operationTime;

        System.out.println("Tempo de leitura: " + readingTime);
        System.out.println("Tempo de operação: " + operationTime);
        System.out.println("Tempo total: " + totaltime);
    }

    public static void testHeap() {
        double[] dist = new double[4];

        Comparator<Integer> comparator = new Comparator<Integer>() {
            public int compare(Integer v1, Integer v2) {
                if(dist[v1] > dist[v2])
                    return 1;
                else if(dist[v1] < dist[v2])
                    return -1;
                return 0;
            }
        };
        
        dist[0] = 1;
        dist[1] = 2;
        dist[2] = 3;
        dist[3] = 4;

        PriorityQueue<Integer> q = new PriorityQueue<Integer>(comparator);
        q.add(0);
        q.add(1);
        q.add(2);
        q.add(3);
        
        dist[0] = 10;
        dist[1] = 9;
        dist[2] = 8;
        dist[3] = 7;

        q.remove(0);
        q.add(0);
        q.remove(1);
        q.add(1);
        q.remove(2);
        q.add(2);
        q.remove(3);
        q.add(3);
        
        for (Integer w : q){
            System.out.println(w);
        }
        System.out.println("");
        while (!q.isEmpty()){
            System.out.println(q.poll());
        }

    }

    public static void testGraph(){
        analyseGraphFromCommandLine();//chame outras funções aqui
    }

    //Teste para ler grafo selecionado pela linha de comando e imprimir algumas informações dele em um arquivo
    public static void analyseGraphFromCommandLine() {
       HashMap<String, String> initialInfo = GraphAnalysis.GetInitialInfoFromUser();
       String inputFileName = initialInfo.get("InputFile");
       String representationType = initialInfo.get("RepresentationType");
       String outputFilePath = GraphAnalysis.CreateOutputFile("dados_grafo_cmd");

       AdjacentList graphAdjList = null; 
       AdjacentMatrix graphAdjMatrix = null; 
       if(representationType == "AdjacentList") {
        graphAdjList = new AdjacentList(GraphAnalysis.SetGraphData(inputFileName+".txt"));
        // GraphAnalysis.SaveAdjListOutput(graphAdjList, outputFilePath);
        // GraphAnalysis.PrintAdjList(graphAdjList);

        HashMap<String, float[]> dijkstraInfo = GraphAnalysis.Dijkstra(graphAdjList, graphAdjMatrix, 1);

        for(float d : dijkstraInfo.get("distances")){
            System.out.println(d);
        }    
       } else {
        graphAdjMatrix = new AdjacentMatrix(GraphAnalysis.SetGraphData(inputFileName+".txt"));
        // GraphAnalysis.SaveAdjMatrixOutput(graphAdjMatrix, outputFilePath);
        // GraphAnalysis.PrintAdjMatrix(graphAdjMatrix);


        // float dist[] = GraphAnalysis.Dijkstra(graphAdjList, graphAdjMatrix, 1);

        // for(float d : dist){
        //     System.out.println(d);
        // }
        // for(int i = 1; i < graphAdjMatrix.Graph.length; i++){
        //     for(int j = 1; j < graphAdjMatrix.Graph.length; j++){
        //         System.out.println("i = " + i + ", j = " + j + ", weight = " + GraphAnalysis.getWeight(graphAdjList, graphAdjMatrix, i, j));
        //     }
        // }

        // float[][] d = GraphAnalysis.FloydWarshall(graphAdjMatrix);

        // for(int i = 1; i < graphAdjMatrix.Graph.length; i++ ){
        //     System.out.println(d[1][i]);
        // }

        AdjacentList mst = GraphAnalysis.Kruskal(null, graphAdjMatrix);

        GraphAnalysis.PrintAdjList(mst);
    }

    }

    //------------- FUNÇÕES DOS ESTUDOS DE CASO - TP1 ----------------

    public static void caseStudy_1() {
        Scanner consoleScanner = new Scanner(System.in);
        for (int i = 1; i <=3; i++) {
            readAdjMatrix(i);
        }

        for (int i = 1; i <=3; i++) {
            readAdjList(i);
        }
    }

    public static void caseStudy_2() {
        String outputFilePath = GraphAnalysis.CreateOutputFile("caseStudy_2_list");
        BFS1000List(outputFilePath);
        outputFilePath = GraphAnalysis.CreateOutputFile("caseStudy_2_Matrix");
        BFS1000Matrix(outputFilePath);
    }

    public static void caseStudy_3() {
        String outputFilePath = GraphAnalysis.CreateOutputFile("caseStudy_3_list");
        DFS1000List(outputFilePath);
        outputFilePath = GraphAnalysis.CreateOutputFile("caseStudy_3_Matrix");
        DFS1000Matrix(outputFilePath);
    }

    public static void caseStudy_4() {
        try {
            String outputFilePath = GraphAnalysis.CreateOutputFile("caseStudy_4");
            FileWriter fileWriter = new FileWriter(outputFilePath);
            for (int graphNumber = 1; graphNumber <= 3; graphNumber++) {
                String inputFile = "grafo_" + graphNumber + ".txt";
                AdjacentList graphAdjList = new AdjacentList(GraphAnalysis.SetGraphData(inputFile));
               
                for(int searchRoot = 1; searchRoot <= 3; searchRoot++) {
                    HashMap<String, int[]> BFSListData = GraphAnalysis.BFS(graphAdjList, null, searchRoot);
                    int[] nodeChilds = {10, 20, 30};
    
                    for(int node : nodeChilds) {
                        int parent = BFSListData.get("parents")[node];
                        fileWriter.write("Pai do nó " + node + " do grafo " + graphNumber + " na BFS iniciada pelo nó " + searchRoot + ": " + parent);
                        if(parent == 0) {
                            fileWriter.write(" (Vértice sem pai - parte de outra componente conexa)\n");
                        } else {
                            fileWriter.write("\n");
                        }
                        
                    }
                }
            }
            fileWriter.write("\n");
            for (int graphNumber = 1; graphNumber <= 3; graphNumber++) {
                String inputFile = "grafo_" + graphNumber + ".txt";
                AdjacentList graphAdjList = new AdjacentList(GraphAnalysis.SetGraphData(inputFile));
                  
                for(int searchRoot = 1; searchRoot <= 3; searchRoot++) {
                    HashMap<String, int[]> DFSListData = GraphAnalysis.DFS(graphAdjList, null, searchRoot);
                    int[] nodeChilds = {10, 20, 30};
    
                    for(int node : nodeChilds) {
                        int parent = DFSListData.get("parents")[node];
                        fileWriter.write("Pai do nó " + node + " do grafo " + graphNumber + " na DFS iniciada pelo nó " + searchRoot + ": " + parent);
                        if(parent == 0) {
                            fileWriter.write(" (Vértice sem pai - parte de outra componente conexa)\n");
                        } else {
                            fileWriter.write("\n");
                        }
                        
                    }
                }
            }
            fileWriter.close();
        } catch (IOException e) {
            System.out.println("Erro.");
            e.printStackTrace();
        }

        
    }   

    public static void caseStudy_5() {
        try {
            String outputFilePath = GraphAnalysis.CreateOutputFile("caseStudy_5");
            FileWriter fileWriter = new FileWriter(outputFilePath);
            fileWriter.write("Obs: Distancias marcadas como 0 indicam que os vértices pertencem a diferentes componentes conexas do grafo.\n\n");
            for (int graphNumber = 1; graphNumber <= 3; graphNumber++) {
                String inputFile = "grafo_" + graphNumber + ".txt";
                AdjacentList graphAdjList = new AdjacentList(GraphAnalysis.SetGraphData(inputFile));
                HashMap<String, int[]> BFSListData1 = GraphAnalysis.BFS(graphAdjList, null, 10);
                HashMap<String, int[]> BFSListData2 = GraphAnalysis.BFS(graphAdjList, null, 20);
                
                fileWriter.write("Distância entre os vértices 10 e 20 no grafo: "+ graphNumber + ": " + BFSListData1.get("levels")[20]+ "\n");
                fileWriter.write("Distância entre os vértices 10 e 30 no grafo: "+ graphNumber + ": " + BFSListData1.get("levels")[30]+ "\n");
                fileWriter.write("Distância entre os vértices 20 e 30 no grafo: "+ graphNumber + ": " + BFSListData2.get("levels")[30]+ "\n");
                fileWriter.write("\n");
            }
            fileWriter.close();
        } catch (IOException e) {
            System.out.println("Erro.");
            e.printStackTrace();
        }
    }

    public static void caseStudy_6() {
        for (int i = 1; i <= 3; i++) {
            String inputFileName = "grafo_" + i + ".txt";
            AdjacentList graph = new AdjacentList(GraphAnalysis.SetGraphData(inputFileName));

            GraphAnalysis.SaveConnectedComponentInfo(graph, null, "caseStudy_6_"+ i);
        }
    }

    public static void caseStudy_7() {

    }

    // -----------------------------------------------------------------

    //Cria uma lista de adjacência a partir de um arquivo .txt, com base na função SetGraphData da classe GraphAnalysis
    public static AdjacentList readAdjList(int number) {
        
        String inputFile = "grafo_" + number + ".txt";
        AdjacentList graphAdjList = new AdjacentList(GraphAnalysis.SetGraphData(inputFile));
        return graphAdjList;
    }

    //Cria uma matriz de adjacência a partir de um arquivo .txt, com base na função SetGraphData da classe GraphAnalysis
    public static AdjacentMatrix readAdjMatrix(int number) {
        String inputFile = "grafo_" + number + ".txt";
        AdjacentMatrix graphAdjMatrix = new AdjacentMatrix(GraphAnalysis.SetGraphData(inputFile));
        Runtime.getRuntime().gc();
        return graphAdjMatrix;
    }

    // Executa 1000 buscas BFS em listas de adjacência dos grafos 1, 2 e 3
    public static void BFS1000List(String outputFilePath) {
        try {
            FileWriter fileWriter = new FileWriter(outputFilePath);
            String inputFile = "grafo_1.txt";
            AdjacentList graphAdjList = new AdjacentList(GraphAnalysis.SetGraphData(inputFile));
            
            long pastTime = System.currentTimeMillis();
    
            for (int j = 1; j <= 100; j++){
                for (int k = 1; k <= 10; k++) {
                    GraphAnalysis.BFS(graphAdjList, null, j);
                }
            }
    
            long timeElapsed = System.currentTimeMillis() - pastTime;
            fileWriter.write("1000 buscas no grafo 1: " + timeElapsed + "ms\n");
    
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
            fileWriter.write("1000 buscas no grafo 2: " + timeElapsed + "ms\n");
    
            
            inputFile = "grafo_3.txt";
            graphAdjList = new AdjacentList(GraphAnalysis.SetGraphData(inputFile));
            Runtime.getRuntime().gc();
            pastTime = System.currentTimeMillis();
    
            for (int i = 1; i <= 1000; i++){
                GraphAnalysis.BFS(graphAdjList, null, i);
            }
    
            timeElapsed = System.currentTimeMillis() - pastTime;
            fileWriter.write("1000 buscas no grafo 3: " + timeElapsed + "ms\n");
    
            fileWriter.close();
        } catch (IOException e) {
            System.out.println("Erro.");
            e.printStackTrace();
        }
    }

    // Executa 1000 buscas BFS em matrizes de adjacência dos grafos 1, 2 e 3
    public static void BFS1000Matrix(String outputFilePath) {

        try {
            FileWriter fileWriter = new FileWriter(outputFilePath);
            String inputFile = "grafo_1.txt";
            AdjacentMatrix graphAdjMatrix = new AdjacentMatrix(GraphAnalysis.SetGraphData(inputFile));
            
            long pastTime = System.currentTimeMillis();
    
            for (int j = 1; j <= 100; j++){
                for (int k = 1; k <= 10; k++) {
                    GraphAnalysis.BFS(null, graphAdjMatrix, j);
                }
            }
    
            long timeElapsed = System.currentTimeMillis() - pastTime;
            fileWriter.write("1000 buscas no grafo 1: " + timeElapsed + "ms\n");
    
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
            fileWriter.write("1000 buscas no grafo 2: " + timeElapsed + "ms\n");
    
            
            inputFile = "grafo_3.txt";
            graphAdjMatrix = new AdjacentMatrix(GraphAnalysis.SetGraphData(inputFile));
            Runtime.getRuntime().gc();
            pastTime = System.currentTimeMillis();
    
            for (int i = 1; i <= 1000; i++){
                GraphAnalysis.BFS(null, graphAdjMatrix, i);
            }
    
            timeElapsed = System.currentTimeMillis() - pastTime;
            fileWriter.write("1000 buscas no grafo 3: " + timeElapsed + "ms\n");
            fileWriter.close();
        } catch (IOException e) {
            System.out.println("Erro.");
            e.printStackTrace();
        }

    }

    // Executa 1000 buscas DFS em listas de adjacência dos grafos 1, 2 e 3
    public static void DFS1000List(String outputFilePath) {

        try {
            FileWriter fileWriter = new FileWriter(outputFilePath);
            String inputFile = "grafo_1.txt";
            AdjacentList graphAdjList = new AdjacentList(GraphAnalysis.SetGraphData(inputFile));
            
            long pastTime = System.currentTimeMillis();

            for (int j = 1; j <= 100; j++){
                for (int k = 1; k <= 10; k++) {
                    GraphAnalysis.DFS(graphAdjList, null, j);
                }
            }

            long timeElapsed = System.currentTimeMillis() - pastTime;
            fileWriter.write("1000 buscas no grafo 1: " + timeElapsed + "ms\n");

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
            fileWriter.write("1000 buscas no grafo 2: " + timeElapsed + "ms\n");

            
            inputFile = "grafo_3.txt";
            graphAdjList = new AdjacentList(GraphAnalysis.SetGraphData(inputFile));
            Runtime.getRuntime().gc();
            pastTime = System.currentTimeMillis();

            for (int i = 1; i <= 1000; i++){
                GraphAnalysis.DFS(graphAdjList, null, i);
            }

            timeElapsed = System.currentTimeMillis() - pastTime;
            fileWriter.write("1000 buscas no grafo 3: " + timeElapsed + "ms\n");

            fileWriter.close();
        } catch (IOException e) {
            System.out.println("Erro.");
            e.printStackTrace();
        }

    }

    // Executa 1000 buscas DFS em matrizes de adjacência dos grafos 1, 2 e 3
    public static void DFS1000Matrix(String outputFilePath) {
        try {
            FileWriter fileWriter = new FileWriter(outputFilePath);
            String inputFile = "grafo_1.txt";
            AdjacentMatrix graphAdjMatrix = new AdjacentMatrix(GraphAnalysis.SetGraphData(inputFile));
            
            long pastTime = System.currentTimeMillis();

            for (int j = 1; j <= 100; j++){
                for (int k = 1; k <= 10; k++) {
                    GraphAnalysis.DFS(null, graphAdjMatrix, j);
                }
            }

            long timeElapsed = System.currentTimeMillis() - pastTime;
            fileWriter.write("1000 buscas no grafo 1: " + timeElapsed + "ms\n");

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
            fileWriter.write("1000 buscas no grafo 2: " + timeElapsed + "ms\n");

            
            inputFile = "grafo_3.txt";
            graphAdjMatrix = new AdjacentMatrix(GraphAnalysis.SetGraphData(inputFile));
            Runtime.getRuntime().gc();
            pastTime = System.currentTimeMillis();

            for (int i = 1; i <= 1000; i++){
                GraphAnalysis.DFS(null, graphAdjMatrix, i);
            }

            timeElapsed = System.currentTimeMillis() - pastTime;
            fileWriter.write("1000 buscas no grafo 3: " + timeElapsed + "ms\n");
            fileWriter.close();
        } catch (IOException e) {
            System.out.println("Erro.");
            e.printStackTrace();
        }

    }
}
