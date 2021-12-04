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
import java.util.ArrayList;
import java.util.Scanner;

public class GraphAnalysis {
    public static void main(String[] args) {
        Scanner consoleScanner = new Scanner(System.in);

        String file = GetFileFromUser(consoleScanner);

        System.out.println("Grafo a ser analisado: " + file);

        String representationType = GetFileRepresentation(consoleScanner);

        System.out.println("Analisando " + file + " como " + representationType + "......");

        GraphData graphData = SetGraphData(file);

        IGraphRepresentation graph;

        switch (representationType) {
            case "AdjacentMatrix":
                graph = new AdjacentMatrix(graphData);
                break;
            case "AdjacentList":
                graph = new AdjacentList(graphData);
                break;
        }
    }

    private static String GetFileFromUser(Scanner consoleScanner) {
        System.out.println("Escolha o numero do grafo a ser analisado:");
        for (int i = 0; i < 6; i++) System.out.println(" " + i + " para " + GraphFiles.fileNames[i]);
        return GraphFiles.fileNames[consoleScanner.nextInt()];
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
        try {
            File file = new File("src/graph_repo/" + graphFile);
            Scanner fileScanner = new Scanner(file);
            graphData.NumberOfVertices = fileScanner.nextInt();
            graphData.Edges = new ArrayList<>();
            graphData.Vertices = new ArrayList<>();
            graphData.MaxVertex = 0;
            while (fileScanner.hasNextInt()) {
                int Node1 = fileScanner.nextInt();
                int Node2 = fileScanner.nextInt();
                Edge edge = new Edge();
                edge.firstNode = Node1;
                edge.secondNode = Node2;
                graphData.Edges.add(edge);
                if(!graphData.Vertices.contains(Node1)) graphData.Vertices.add(Node1);
                if(!graphData.Vertices.contains(Node2)) graphData.Vertices.add(Node2);
                if(Node1 > graphData.MaxVertex) graphData.MaxVertex = Node1;
                if(Node2 > graphData.MaxVertex) graphData.MaxVertex = Node2;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return graphData;
    }
}
