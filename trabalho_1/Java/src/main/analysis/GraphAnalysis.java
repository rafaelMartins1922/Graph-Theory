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
import java.util.Scanner;

public class GraphAnalysis {
    public static void main(String[] args) {
       AnalyseGraph(null, null);
    }

    private static HashMap<String, String> GetInitialInfoFromUser() {
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

    private static void AnalyseGraph(String inputFile, String representationType) {
        if(inputFile == null && representationType == null) {
            HashMap<String, String> initialInfo = GetInitialInfoFromUser();
            representationType = initialInfo.get("RepresetationType");
            inputFile = initialInfo.get("InputFile");
        }
        
        String outputFilePath = CreateOutputFile("output");

        AdjacentMatrix graphAdjMatrix = null;
        AdjacentList graphAdjList = null;
        switch (representationType) {
            case "AdjacentMatrix":
                graphAdjMatrix = new AdjacentMatrix(SetGraphData(inputFile));
                // PrintAdjMatrix(graphAdjMatrix);
                //SaveAdjMatrixOutput(graphAdjMatrix, outputFilePath);
                // BuildSearchTree(null, graphAdjMatrix, 1, "DFS");
                break;
            case "AdjacentList":
                graphAdjList = new AdjacentList(SetGraphData(inputFile));
                //PrintAdjList(graphAdjList);
                //SaveAdjListOutput(graphAdjList, outputFilePath);
                // BuildSearchTree(graphAdjList, null, 1, "DFS");
                //DFS(graphAdjList, null, 1);
                break;
         }
        
    }

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

    private static void SaveAdjMatrixOutput(AdjacentMatrix graph,String outputFilePath) {
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

    private static void SaveAdjListOutput(AdjacentList graph, String outputFilePath) {
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

    private static String GetFileFromUser(Scanner consoleScanner) {
        System.out.println("Escolha o numero do grafo a ser analisado:");
        for (int i = 0; i < 3; i++) System.out.println(" " + (i +1) + " para " + GraphFiles.fileNames[i]);
        return GraphFiles.fileNames[consoleScanner.nextInt() - 1];
    }

    private static String GetFileRepresentation(Scanner consoleScanner) {
        System.out.println("Escolha o tipo de representação do grafo :");
        for (int i = 0; i < GraphsRepresentations.values().length; i++) {
            System.out.println(" " + i + " para " + GraphsRepresentations.values()[i]);
        }
        return GraphsRepresentations.values()[consoleScanner.nextInt()].toString();
    }

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
                int i = 0;
                while (fileScanner.hasNextInt()) {
                    i++;
                    int Node1 = fileScanner.nextInt();
                    int Node2 = fileScanner.nextInt();
                    Edge edge = new Edge();
                    edge.firstNode = Node1;
                    edge.secondNode = Node2;
                    graphData.Edges.add(edge);

                    // erradin
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
                    //-----------
                    if(Node1 > graphData.MaxVertex) graphData.MaxVertex = Node1;
                    if(Node2 > graphData.MaxVertex) graphData.MaxVertex = Node2;  
                }

                graphData.AverageDegree = 2*graphData.Edges.size()/graphData.Vertices.size();

                ArrayList<Integer> graphDegreesList = new ArrayList<Integer>(graphDegreesHash.values());
                Collections.sort(graphDegreesList);
                //System.out.println("Graus do grafo:");

                // int countDeg = 0;
                
                // //imprime lista ordenada de graus do grafo
                // // for(int deg : graphDegreesList) {
                // //     countDeg++;
                // //     System.out.println(countDeg + "->" + deg);
                // // }

                // countDeg = 0;

                // for(int vert : graphDegreesHash.keySet()) {
                //     countDeg++;
                //     System.out.println(countDeg + "-> vértice " + vert + ": "+ graphDegreesHash.get(vert));
                // }


                if(graphDegreesList.size() % 2 == 0) {
                    graphData.MedianDegree = (graphDegreesList.get(graphDegreesList.size()/2) + graphDegreesList.get(graphDegreesList.size()/2 - 1))/2;
                } else {
                    graphData.MedianDegree = graphDegreesList.get(graphDegreesList.size()/2);
                }

                graphData.MaxDegree = graphDegreesList.get(graphDegreesList.size() - 1 );
                graphData.MinDegree = graphDegreesList.get(0);
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
                Comparator<Integer> order = Integer::compare;
                int v = queue.poll();

                explored.add(v);
                graphAdjList.Graph[v].sort(order);

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

        // System.out.println("Vértices descobertos:");
        // for(int vertex : discovered) {
        //     System.out.print(vertex + " ");
        // }

        // System.out.println("\nVértices explorados:\n");
        // for(int vertex : explored) {
        //     System.out.print(vertex + " ");
        // }

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
        

        // System.out.println("Vértices na pilha:");
        // for(int vertex : stacked) {
        //     System.out.print(vertex + " ");
        // }

        // System.out.println("\nVértices marcados:");
        // for(int vertex : marked) {
        //     System.out.print(vertex + " ");
        // }

        HashMap<String, int[]> treeInfo = new HashMap<String, int[]>();
        treeInfo.put("parents", parents);
        treeInfo.put("levels", levels);
        treeInfo.put("markedVertices", markedVertices);
        return treeInfo;
    }

    private static void BuildSearchTree(AdjacentList graphAdjList, AdjacentMatrix graphAdjMatrix, int root, String typeOfSearch) {
        HashMap<String, int[]> treeInfo = new HashMap<String, int[]>();
        if(typeOfSearch == "BFS"){
            treeInfo = BFS(graphAdjList, graphAdjMatrix, root);
        } else {
            treeInfo = DFS(graphAdjList, graphAdjMatrix, root);
        }

        String filePath = CreateOutputFile("SearchTreeInfo");

        SaveSearchTreeOutput(treeInfo, filePath);
    }

    private static void SaveSearchTreeOutput(HashMap<String, int[]> treeInfo, String outputFilePath) {
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
}

// ArrayList<Integer> aaa = new ArrayList<>();
//        aaa.add(0);
//        aaa.add(12);
//        System.out.println(aaa);
//        aaa.remove(Integer.valueOf(12));
//        System.out.println(aaa);
