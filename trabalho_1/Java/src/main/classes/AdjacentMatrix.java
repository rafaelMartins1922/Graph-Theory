package main.classes;

import main.interfaces.IGraphRepresentation;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class AdjacentMatrix implements IGraphRepresentation {
    private int[][] Graph;
    private ArrayList<Integer> Vertices;
    private int NumberOfEdges;
    private int NumberOfVertices;

    public AdjacentMatrix(@NotNull GraphData graphData) {
        NumberOfEdges = graphData.Edges.size();
        NumberOfVertices = graphData.Vertices.size();
        Vertices = graphData.Vertices;
        CreateGraph(graphData);
    }

    private void CreateGraph(GraphData graphData){
        Graph = new int[graphData.MaxVertex+1][graphData.MaxVertex+1];
        for (var edge: graphData.Edges){
            Graph[edge.firstNode][edge.secondNode] = 1;
            Graph[edge.secondNode][edge.firstNode] = 1;
        }
    }
}
