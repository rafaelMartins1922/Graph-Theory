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
import java.io.FileWriter;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class GraphAnalysis {
    public static void main(String[] args) {
        AnalyseGraph(null, null);
    }

    private static void AnalyseGraph(String inputFile, String representationType) {
        if(inputFile == null && representationType == null) {
            Scanner consoleScanner = new Scanner(System.in);
            inputFile = GetFileFromUser(consoleScanner);
            System.out.println("Grafo a ser analisado: " + inputFile);
            representationType = GetFileRepresentation(consoleScanner);
            System.out.println("Analisando " + inputFile + " como " + representationType + "......");
        }
        
        GraphData graphData = SetGraphData(inputFile);
        String outputFilePath = CreateOutputFile();

        switch (representationType) {
            case "AdjacentMatrix":
                AdjacentMatrix graphAdjMatrix = new AdjacentMatrix(graphData);
               // PrintAdjMatrix(graphAdjMatrix);
                SaveAdjMatrixOutput(graphAdjMatrix, outputFilePath);
                break;
            case "AdjacentList":
                AdjacentList graphAdjList = new AdjacentList(graphData);
                //PrintAdjList(graphAdjList);
                SaveAdjListOutput(graphAdjList, outputFilePath);
                break;
        }
        
    }

    private static String CreateOutputFile() {
        String filePath = Paths.get("").toAbsolutePath().toString() + "/output_files/output.txt";
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

    private static void PrintAdjList(AdjacentList graph) {
        for (int i = 0; i < graph.Graph.length ; i++) {
            if(graph.Graph[i] != null){
                System.out.print( i + " -> ");
                for (var next : graph.Graph[i]) System.out.print(" " + next);
                System.out.println();
            }
        }

        System.out.println("Grau máximo:" + graph.maxDegree);
        System.out.println("Grau mínimo:" + graph.minDegree);
        System.out.println("Mediana de Grau:" + graph.medianDegree);
        System.out.println("Número de vértices:" + graph.numberOfVertices);
        System.out.println("Número de arestas:" + graph.numberOfEdges);
    };

    private static void PrintAdjMatrix(AdjacentMatrix graph) {
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
        for (int i = 0; i < 2; i++) System.out.println(" " + (i +1) + " para " + GraphFiles.fileNames[i]);
        return GraphFiles.fileNames[consoleScanner.nextInt() - 1];
    }

    private static String GetFileRepresentation(Scanner consoleScanner) {
        System.out.println("Escolha o tipo de representação do grafo :");
        for (int i = 0; i < GraphsRepresentations.values().length; i++) {
            System.out.println(" " + i + " para " + GraphsRepresentations.values()[i]);
        }
        return GraphsRepresentations.values()[consoleScanner.nextInt()].toString();
    }

    private static GraphData SetGraphData(String graphFile) {
        GraphData graphData = new GraphData();
        HashMap<Integer, Integer> graphDegreesHash = new HashMap<Integer, Integer>();

            try {
                File file = new File(Paths.get("").toAbsolutePath().toString() + "/input_files/" + graphFile);
                Scanner fileScanner = new Scanner(file);

                graphData.NumberOfVertices = fileScanner.nextInt();
                graphData.Edges = new ArrayList<>();
                graphData.Vertices = new ArrayList<>();
                graphData.MaxVertex = 0;
                while (fileScanner.hasNextInt()) {
                    int Node1 = fileScanner.nextInt();
                    int Node2 = fileScanner.nextInt();
                    System.out.println("Node1:" + Node1 + ", Node2:" + Node2);
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

                List<Integer> graphDegreesList = new ArrayList<Integer>(graphDegreesHash.values());
                Collections.sort(graphDegreesList);
                System.out.println("Graus do grafo:");

                int countDeg = 0;
                for(int deg : graphDegreesList) {
                    countDeg++;
                    System.out.println(countDeg + "->" + deg);
                }

                countDeg = 0;

                for(int vert : graphDegreesHash.keySet()) {
                    countDeg++;
                    System.out.println(countDeg + "-> vértice " + vert + ": "+ graphDegreesHash.get(vert));
                }


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

}
