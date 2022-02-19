package main.analysis;

import main.classes.AdjacentList;
import main.classes.AdjacentMatrix;
import main.classes.Edge;
import main.classes.GraphData;
import main.constants.GraphFiles;
import main.enums.GraphsRepresentations;
import main.interfaces.IGraphRepresentation;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Array;
import java.io.FileWriter;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Scanner;

public class GraphAnalysis {
    // Lê o tipo de representação do grafo (matriz ou lista de adjacência) e o nome do arquivo do grafo da linha de comando
    public static HashMap<String, String> GetInitialInfoFromUser() {
        Scanner consoleScanner = new Scanner(System.in);
        String inputFile = GetFileFromUser(consoleScanner);
        System.out.println("Grafo a ser analisado: " + inputFile);
        String representationType = GetFileRepresentation(consoleScanner);
        System.out.println("Analisando " + inputFile + " como " + representationType + "......");
        
        HashMap<String, String> initialInfo = new HashMap<>();
        initialInfo.put("RepresentationType", representationType);
        initialInfo.put("InputFile", inputFile);
        consoleScanner.close();
        return initialInfo;
    }

    //Cria um arquivo de saída com o nome especificado, usada para salvar dados de buscas e análises dos grafos
    public static String CreateOutputFile(String filename) {
        String filePath = Paths.get("").toAbsolutePath().toString() + "/output_files/" + filename + ".txt";
        try {
            
            File outputFile = new File(Paths.get("").toAbsolutePath().toString() + "/output.txt");
            if(outputFile.createNewFile()){
                System.out.println("Arquivo de saída criado");
            } else {
                System.out.println("Atualizando arquivo de saída");
                outputFile.delete();
                outputFile.createNewFile();
            }
        } catch (IOException e){
            System.out.println("Erro.");
            e.printStackTrace();
        }

        return filePath;
    }

    //Salva os dados de uma matriz de adjacência no arquivo criado através de CreateOutputFile
    public static void SaveAdjMatrixOutput(AdjacentMatrix graph,String outputFilePath) {
        try {
            FileWriter fileWriter = new FileWriter(outputFilePath);
            fileWriter.write("Grau máximo:" + graph.maxDegree + "\n");
            fileWriter.write("Grau mínimo:" + graph.minDegree + "\n");
            fileWriter.write("Mediana de Grau:" + graph.medianDegree + "\n");
            fileWriter.write("Número de vértices:" + graph.numberOfVertices + "\n");
            fileWriter.write("Número de arestas:" + graph.numberOfEdges + "\n");
            fileWriter.close();
        } catch (IOException e) {
            System.out.println("Erro.");
            e.printStackTrace();
        }
    }

    //Salva os dados de uma lista de adjacência no arquivo criado através de CreateOutputFile
    public static void SaveAdjListOutput(AdjacentList graph, String outputFilePath) {
        try {
            FileWriter fileWriter = new FileWriter(outputFilePath);
            fileWriter.write("Grau máximo:" + graph.maxDegree + "\n");
            fileWriter.write("Grau mínimo:" + graph.minDegree + "\n");
            fileWriter.write("Mediana de Grau:" + graph.medianDegree + "\n");
            fileWriter.write("Número de vértices:" + graph.numberOfVertices + "\n");
            fileWriter.write("Número de arestas:" + graph.numberOfEdges + "\n");
            fileWriter.close();
        } catch (IOException e) {
            System.out.println("Erro.");
            e.printStackTrace();
        }
    }

    //imprime no consoe os uma lista de adjacência informada
    public static void PrintAdjList(AdjacentList graph) {
        for (int i = 0; i < graph.Graph.length ; i++) {
            if(graph.Graph[i] != null){
                System.out.print( i + " -> ");
                for (var next : graph.Graph[i]) System.out.print(" " + next);
            }
            System.out.println("");
        }

        System.out.println("Grau máximo:" + graph.maxDegree);
        System.out.println("Grau mínimo:" + graph.minDegree);
        System.out.println("Mediana de Grau:" + graph.medianDegree);
        System.out.println("Número de vértices:" + graph.numberOfVertices);
        System.out.println("Número de arestas:" + graph.numberOfEdges);
    };

    //imprime no consoe os uma matriz de adjacência informada
    public static void PrintAdjMatrix(AdjacentMatrix graph) {
        for (int i = 0; i < graph.Graph.length; i++) {
            System.out.print( i + " -> ");
            for (int j = 0; j < graph.Graph.length; j++) {
                if(graph.Graph[i][j] == 1) System.out.print(" " + j);
            }
            System.out.println();
        }

        System.out.println("Grau máximo:" + graph.maxDegree);
        System.out.println("Grau mínimo:" + graph.minDegree);
        System.out.println("Mediana de Grau:" + graph.medianDegree);
        System.out.println("Número de vértices:" + graph.numberOfVertices);
        System.out.println("Número de arestas:" + graph.numberOfEdges);

    };

    //Imprime no console as opções de grafos a serem escolhidas por um usuário
    public static String GetFileFromUser(Scanner consoleScanner) {
        System.out.println("Escolha o numero do grafo a ser analisado:");
        for (int i = 0; i < 3; i++) System.out.println(" " + (i +1) + " para " + GraphFiles.fileNames[i]);
        return GraphFiles.fileNames[consoleScanner.nextInt() - 1];
    }

    //Imprime no console as opççõs de representação (lista ou matriz de adjacência) de um grafo a ser lido
    public static String GetFileRepresentation(Scanner consoleScanner) {
        System.out.println("Escolha o tipo de representação do grafo :");
        for (int i = 0; i < GraphsRepresentations.values().length; i++) {
            System.out.println(" " + i + " para " + GraphsRepresentations.values()[i]);
        }
        return GraphsRepresentations.values()[consoleScanner.nextInt()].toString();
    }

    //Lê o grafo a partir de um arquivo .txt
    public static GraphData SetGraphData(String graphFile) {
        GraphData graphData = new GraphData();
        HashMap<Integer, Integer> graphDegreesHash = new HashMap<Integer, Integer>();

            try {
                File file = new File(Paths.get("").toAbsolutePath().toString() + "/input_files/" + graphFile);
                Scanner fileScanner = new Scanner(file);
                
                graphData.NumberOfVertices = fileScanner.nextInt();
                graphData.Edges = new ArrayList<>();
                graphData.Vertices = new ArrayList<>();
                graphData.MaxVertex = 0;
                fileScanner.nextLine();
                int i = 0;
                while (fileScanner.hasNextLine()) {
                    i++;
                    
                    String line = fileScanner.nextLine();
                    String[] splitLine = line.split(" ");

                    int Node1 = Integer.parseInt(splitLine[0]);
                    int Node2 = Integer.parseInt(splitLine[1]);
                    float Weight;

                    if(splitLine.length == 2) {
                        graphData.hasWeights = false;
                        Weight = 1;
                    } else {
                        graphData.hasWeights = true;
                        Weight = Float.parseFloat(splitLine[2]);
                    }

                    if(Weight < 0) {
                        graphData.hasNegativeWeights = true;
                    }
                    Edge edge = new Edge();
                    edge.firstNode = Node1;
                    edge.secondNode = Node2;
                    edge.weight = Weight;
                    graphData.Edges.add(edge);

                    if(!graphData.Vertices.contains(Node1)){
                        graphData.Vertices.add(Node1);
                        graphDegreesHash.put(Node1, 1);
                    }else {
                        graphDegreesHash.put(Node1, graphDegreesHash.get(Node1)+1);
                    }

                    if(!graphData.Vertices.contains(Node2)) {
                        graphData.Vertices.add(Node2);
                        graphDegreesHash.put(Node2, 1);
                    } else{
                        graphDegreesHash.put(Node2, graphDegreesHash.get(Node2)+1);
                    }
                    if(Node1 > graphData.MaxVertex) graphData.MaxVertex = Node1;
                    if(Node2 > graphData.MaxVertex) graphData.MaxVertex = Node2;  
                }

                fileScanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return graphData;
    }

    public static HashMap<String, int[]> BFS(AdjacentList graphAdjList, AdjacentMatrix graphAdjMatrix, int s) {
        int markedVertices[];
        int[] parents;
        int[] levels;
        LinkedList<Integer> queue = new LinkedList<Integer>();
        ArrayList<Integer> discovered = new ArrayList<Integer>();
        ArrayList<Integer> explored = new ArrayList<Integer>();

        queue.add(s);
        discovered.add(s);

        if (graphAdjMatrix != null) {
            markedVertices = new int[graphAdjMatrix.Graph.length];
            markedVertices[s] = 1;
            parents = new int[graphAdjMatrix.Graph.length];
            levels = new int[graphAdjMatrix.Graph.length];

            while(!queue.isEmpty()) {
                int v = queue.poll();
                explored.add(v);
                for (int w = 0; w < graphAdjMatrix.Graph[v].length; w++) {
                    if (!(markedVertices[w] == 1) && graphAdjMatrix.Graph[v][w] == 1) {
                        markedVertices[w] = 1;
                        queue.add(w);
                        discovered.add(w);
                        parents[w] = v;
                        levels[w] = levels[v] + 1;
                        parents[w] = v;
                    }
                }
            }
        } else {
            markedVertices = new int[graphAdjList.Graph.length+1];
            markedVertices[s] = 1;
            parents = new int[graphAdjList.Graph.length];
            levels = new int[graphAdjList.Graph.length];
            
            while(!queue.isEmpty()) {
                int v = queue.poll();

                explored.add(v);

                for (Integer w : graphAdjList.Graph[v]) {
                    if (!(markedVertices[w] == 1)) {
                        markedVertices[w] = 1;
                        queue.add(w);
                        discovered.add(w);
                        parents[w] = v;
                        levels[w] = levels[v] + 1;
                        parents[w] = v;
                    }
                }
            }
        }

        HashMap<String, int[]> treeInfo = new HashMap<String, int[]>();
        treeInfo.put("parents", parents);
        treeInfo.put("levels", levels);
        treeInfo.put("markedVertices", markedVertices);
        return treeInfo;
    }

    public static HashMap<String, int[]> DFS(AdjacentList graphAdjList, AdjacentMatrix graphAdjMatrix, int s) {
        int markedVertices[];
        int parents[];
        int levels[];
        ArrayList<Integer> stack = new ArrayList<Integer>();
        ArrayList<Integer> stacked = new ArrayList<Integer>();
        ArrayList<Integer> marked = new ArrayList<Integer>();

        stack.add(s);
        stacked.add(s);

        if(graphAdjMatrix != null) {
           markedVertices = new int[graphAdjMatrix.Graph.length];
           parents = new int[graphAdjMatrix.Graph.length];
           levels = new int[graphAdjMatrix.Graph.length];
            
            while(!stack.isEmpty()) {
                int u = stack.get(stack.size() - 1);
                stack.remove(stack.size() - 1);

                if(!(markedVertices[u] == 1)) {
                    markedVertices[u] = 1;
                    marked.add(u);
                    for (int v = graphAdjMatrix.Graph[u].length - 1; v >= 0; v--) {
                        if(graphAdjMatrix.Graph[u][v] == 1){
                            stack.add(v);
                            stacked.add(v);
                            if(!(markedVertices[v] == 1)) {
                                levels[v] = levels[u] + 1;
                                parents[v] = u;
                            };
                        }
                    }
                }
            }
        } else {
            markedVertices = new int[graphAdjList.Graph.length];
            parents = new int[graphAdjList.Graph.length];
            levels = new int[graphAdjList.Graph.length];

            while(!stack.isEmpty()) {
                int u = stack.get(stack.size() - 1);
                stack.remove(stack.size() - 1);

                if(!(markedVertices[u] == 1)) {
                    markedVertices[u] = 1;
                    marked.add(u);
                    Comparator<Integer> order = Integer::compare;
                    graphAdjList.Graph[u].sort(order.reversed());
                    for (Integer v : graphAdjList.Graph[u]) {
                        stack.add(v);
                        stacked.add(v);
                        if(!(markedVertices[v] == 1)) {
                            levels[v] = levels[u] + 1;
                            parents[v] = u;
                        };
                    }
                }
            }
        }

        HashMap<String, int[]> treeInfo = new HashMap<String, int[]>();
        treeInfo.put("parents", parents);
        treeInfo.put("levels", levels);
        treeInfo.put("markedVertices", markedVertices);
        return treeInfo;
    }

    //Constrói uma árvore de busca, isto é, captura informações de níveis e pais de nós para árvores de busca geradas pela BFS e DFS
    public static void BuildSearchTree(AdjacentList graphAdjList, AdjacentMatrix graphAdjMatrix, int root, String typeOfSearch) {
        HashMap<String, int[]> treeInfo = new HashMap<String, int[]>();
        if(typeOfSearch == "BFS"){
            treeInfo = BFS(graphAdjList, graphAdjMatrix, root);
        } else {
            treeInfo = DFS(graphAdjList, graphAdjMatrix, root);
        }

        String filePath = CreateOutputFile("SearchTreeInfo");

        SaveSearchTreeOutput(treeInfo, filePath);
    }

    //Salva informações de árvores de busca em um arquivo .txt
    public static void SaveSearchTreeOutput(HashMap<String, int[]> treeInfo, String outputFilePath) {
        try {
            FileWriter fileWriter = new FileWriter(outputFilePath);
            System.out.println(treeInfo.get("parents").length);
            for (int i = 0; i < treeInfo.get("parents").length; i++) {
                fileWriter.write("Vértice: " + i + ", pai: " + treeInfo.get("parents")[i] + ", nível na árvore de busca: " + treeInfo.get("levels")[i] + "\n");
            }
            fileWriter.close();
        } catch (IOException e) {
            System.out.println("Erro.");
            e.printStackTrace();
        }
    }

    //Retorna a lista de componentes conexas de um grafo
    public static ArrayList<ArrayList<Integer>> GetConnectedComponents(AdjacentList graphAdjList, AdjacentMatrix graphAdjMatrix) {
        ArrayList<ArrayList<Integer>> connectedComponents = new ArrayList<ArrayList<Integer>>();
        ArrayList<Integer> unexploredVertices;

        if(graphAdjMatrix != null) {
            unexploredVertices = new ArrayList<>(graphAdjMatrix.vertices);
        } else {
            unexploredVertices = new ArrayList<>(graphAdjList.vertices);
        }

        while(unexploredVertices.size() != 0) {
            int root = unexploredVertices.get(0);
            HashMap<String, int[]> bfsData = BFS(graphAdjList, graphAdjMatrix, root);
            int[] markedVertices = bfsData.get("markedVertices");
            ArrayList<Integer> newConnectedComponent = new ArrayList<Integer>();


            for(int i = 0; i < markedVertices.length; i++) {
                if(markedVertices[i] == 1) {
                    unexploredVertices.remove(Integer.valueOf(i));
                    newConnectedComponent.add(i);
                }
            }
            connectedComponents.add(newConnectedComponent);
        }

        return connectedComponents;
    }

    //Pega o diâmetro do grafo
    public static int GetGraphDiameter(IGraphRepresentation graph){
        int graphDiameter = 0;
        if(graph.getClass().toString() == GraphsRepresentations.AdjacentList.toString()){
            for (var vertex : graph.GetVertices()) {
                var searchResult =  BFS((AdjacentList) graph,null,vertex);
                int levelsTotal = searchResult.get("levels").length;
                if (levelsTotal > graphDiameter) graphDiameter = levelsTotal;
            }
        }
        if(graph.getClass().toString() == GraphsRepresentations.AdjacentMatrix.toString()){
            for (var vertex : graph.GetVertices()) {
                var searchResult =  BFS(null,(AdjacentMatrix) graph,vertex);
                int levelsTotal = searchResult.get("levels").length;
                if (levelsTotal > graphDiameter) graphDiameter = levelsTotal;
            }
        }
        return graphDiameter;
    }

    //Salva informações de componentes conexas de um grafo em um arquivo .txt
    public static void SaveConnectedComponentInfo(AdjacentList graphAdjList, AdjacentMatrix graphAdjMatrix, String outputFileName) {
        ArrayList<ArrayList<Integer>> connectedComponents = GetConnectedComponents(graphAdjList, graphAdjMatrix);
        String outputFilePath = CreateOutputFile(outputFileName);
        
        Collections.sort(connectedComponents, new Comparator<ArrayList<Integer>>(){
            public int compare(ArrayList<Integer> a1, ArrayList<Integer> a2) {
                return a2.size() - a1.size();
            }
        });

        try {
            FileWriter fileWriter = new FileWriter(outputFilePath);
            fileWriter.write("Número de componentes conexas do grafo: " + connectedComponents.size()+ "\n");
            fileWriter.write("Dados das componentes (em ordem decrescente de tamanho): \n");
            fileWriter.write("Tamanho da maior componente conexa:" + connectedComponents.get(0).size() +"\n");
            fileWriter.write("Tamanho da menor componente conexa:" + connectedComponents.get(connectedComponents.size() - 1).size() +"\n");
            int i = 1;
            for(ArrayList<Integer> component : connectedComponents) {
                fileWriter.write("Componente "+ i + ":\n");
                fileWriter.write("  Tamanho: " + component.size() + "\n");
                fileWriter.write("  Vértices: \n");
                for(int vertex : component) {
                    fileWriter.write("      " + vertex + "\n");
                }
                i++;
            }

            fileWriter.close();
        } catch (IOException e) {
            System.out.println("Erro.");
            e.printStackTrace();
        }
    }

    //Pega o peso de uma aresta do grafo
    public static float getWeight(AdjacentList graphAdjList, AdjacentMatrix graphAdjMatrix, Integer i, Integer j) {
        if(graphAdjMatrix != null) {
            float weight =  graphAdjMatrix.Weights[i][j];
            if(weight == 0) return Float.MAX_VALUE/2 -10;
            else return weight;
        } else {
            if(graphAdjList.Weights.get(i).get(j) == null) return Float.MAX_VALUE/2 -10;
            else return graphAdjList.Weights.get(i).get(j);
        }
    }

    //Roda o algoritmo de Dijkstra sobre um grafo
    public static HashMap<String, float[]> Dijkstra(AdjacentList graphAdjList, AdjacentMatrix graphAdjMatrix, Integer s){
        float infinite = Float.MAX_VALUE;
        
        if(graphAdjMatrix != null){
            float dist[] = new float[graphAdjMatrix.Graph.length];
            boolean[] isInS = new boolean[graphAdjMatrix.Graph.length];
            float parents[] = new float[graphAdjList.Graph.length];

            Comparator<Integer> comparator = new Comparator<Integer>() {
                public int compare(Integer v1, Integer v2) {
                    if(dist[v1] > dist[v2])
                        return 1;
                    else if(dist[v1] < dist[v2])
                        return -1;
                    return 0;
                }
            };
            
            PriorityQueue<Integer> heap = new PriorityQueue<Integer>(comparator);

            for (int v = 1; v < graphAdjMatrix.Graph.length; v++) {
                dist[v] = infinite;
                heap.add(v);
            }

            dist[s] = 0;
            parents[s] = 0;

            heap.remove(s);
            heap.add(s);

            while(heap.size() != 0) {
                Integer u = heap.poll();
                
                isInS[u] = true;
                for (int v = 1; v < graphAdjMatrix.Graph.length; v++){
                    if( graphAdjMatrix.Graph[u][v] == 1 && !isInS[v]){
                        float weight = getWeight(null, graphAdjMatrix, u, v);
                        if(dist[v] > dist[u] + weight) {
                            dist[v] = dist[u] + weight;
                            heap.remove(v);
                            heap.add(v);
                            parents[v] = u;
                        }
                    }
                }
            }
            
            HashMap<String, float[]> dijkstraInfo = new HashMap<String, float[]>();
            dijkstraInfo.put("distances", dist);
            dijkstraInfo.put("parents", parents);
            return dijkstraInfo;
        } else {
            float dist[] = new float[graphAdjList.Graph.length];
            boolean[] isInS = new boolean[graphAdjList.Graph.length];
            float parents[] = new float[graphAdjList.Graph.length];
            float parent;

            Comparator<Integer> comparator = new Comparator<Integer>() {
                public int compare(Integer v1, Integer v2) {
                    if(dist[v1] > dist[v2])
                        return 1;
                    else if(dist[v1] < dist[v2])
                        return -1;
                    return 0;
                }
            };
            
            PriorityQueue<Integer> heap = new PriorityQueue<Integer>(comparator);

            for (int v = 1; v < graphAdjList.Graph.length; v++) {
                dist[v] = infinite;
                heap.add(v);
            }

            dist[s] = 0;
            parents[s] = 0;

            heap.remove(s);
            heap.add(s);

            while(heap.size() != 0) {
                Integer u = heap.poll();

                isInS[u] = true;
                for (Integer v : graphAdjList.Graph[u]){
                    if(!isInS[v]) {
                        float weight = getWeight(graphAdjList, null, u, v);
                        if(dist[v] > dist[u] + weight) {
                            dist[v] = dist[u] + weight;
                            heap.remove(v);
                            heap.add(v);
                            parents[v] = u;
                        }
                    }
                }
            }
            
            HashMap<String, float[]> dijkstraInfo = new HashMap<String, float[]>();
            dijkstraInfo.put("distances", dist);
            dijkstraInfo.put("parents", parents);
            return dijkstraInfo;
        }
    }
    //Roda o algoritmo de Floyd-Warshal sobre o grafo
    public static float[][] FloydWarshall(AdjacentList graphAdjList, AdjacentMatrix graphAdjMatrix) {
        if(graphAdjMatrix != null) {
            int n = graphAdjMatrix.Graph.length;
            float[][] d = new float[n][n];
            int [][] next = new int[n][n];
                        
            for (int i = 1; i < n; i++) {
                for (int j = 1; j < n; j++) {
                    if(j == i){
                        d[i][j] = 0;   
                    } else {
                        d[i][j] = getWeight(null, graphAdjMatrix, i, j);
                    }

                    if(d[i][j] < (Float.MAX_VALUE/2 -10)) next[i][j] = j;
                    else next[i][j] = -1;
                }
            }

            for (int k = 1; k < n; k++) {
                for (int i = 1; i < n; i++) {
                    for (int j = 1; j < n; j++) {
                        if(d[i][j] > d[i][k] + d[k][j]) {
                                d[i][j] = d[i][k] + d[k][j];
                                next[i][j] = next[i][k];
                        }
                    }
                }
            }
            return d;
        } else {
            int n = graphAdjList.Graph.length;
            float[][] d = new float[n][n];
                        
            for (int i = 1; i < n; i++) {
                for (int j = 1; j < n; j++) {
                    if(j == i){
                        d[i][j] = 0;   
                    } else {
                        d[i][j] = getWeight(graphAdjList, null, i, j);
                    }
                }
            }

            for (int k = 1; k < n; k++) {
                for (int i = 1; i < n; i++) {
                    for (int j = 1; j < n; j++) {
                        if(d[i][j] > d[i][k] + d[k][j]) {
                                d[i][j] = d[i][k] + d[k][j];
                            }
                    }
                }
            }
            return d;
        }
    }
    //Roda o algoritmo de Kruskal para gerar a MST de um grafo
    public static AdjacentList Kruskal(AdjacentList graphAdjList, AdjacentMatrix graphAdjMatrix) {
        if(graphAdjMatrix != null) {
        GraphData graphData = new GraphData();
        graphData.Edges = new ArrayList<>();
        graphData.Vertices = new ArrayList<>();
        graphData.NumberOfVertices = graphAdjMatrix.Graph.length - 1;

        Comparator<Edge> comparator = new Comparator<Edge>() {
            public int compare(Edge e1, Edge e2) {
                if(e1.weight > e2.weight)
                    return 1;
                else if(e1.weight < e2.weight)
                    return -1;
                return 0;
            }
        };

        graphAdjMatrix.Edges.sort(comparator);

        for (Edge e : graphAdjMatrix.Edges) {
            graphData.Edges.add(e);

            if(!graphData.Vertices.contains(e.firstNode)){
                graphData.Vertices.add(e.firstNode);
            }

            if(!graphData.Vertices.contains(e.secondNode)){
                graphData.Vertices.add(e.secondNode);
            }

            if(graphData.Vertices.size() == graphAdjMatrix.vertices.size()) break;
        }

        AdjacentList mst = new AdjacentList(graphData);
        return mst;
        } else {
            GraphData graphData = new GraphData();
            graphData.Edges = new ArrayList<>();
            graphData.Vertices = new ArrayList<>();
            graphData.NumberOfVertices = graphAdjList.Graph.length - 1;

        Comparator<Edge> comparator = new Comparator<Edge>() {
            public int compare(Edge e1, Edge e2) {
                if(e1.weight > e2.weight)
                    return 1;
                else if(e1.weight < e2.weight)
                    return -1;
                return 0;
            }
        };

        graphAdjList.Edges.sort(comparator);

        for (Edge e : graphAdjList.Edges) {
            graphData.Edges.add(e);

            if(!graphData.Vertices.contains(e.firstNode)){
                graphData.Vertices.add(e.firstNode);
            }

            if(!graphData.Vertices.contains(e.secondNode)){
                graphData.Vertices.add(e.secondNode);
            }

            if(graphData.Vertices.size() == graphAdjList.vertices.size()) break;
        }

        AdjacentList mst = new AdjacentList(graphData);
        return mst;
        }
    } 

}
