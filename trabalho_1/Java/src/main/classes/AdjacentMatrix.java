package main.classes;

import main.interfaces.IGraphRepresentation;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class AdjacentMatrix implements IGraphRepresentation {
    public int[][] Graph;
    public int numberOfEdges;
    public int numberOfVertices;
    public int maxDegree;
    public int minDegree;
    public int medianDegree;

    public AdjacentMatrix(@NotNull GraphData graphData) {
        numberOfEdges = graphData.Edges.size();
        numberOfVertices = graphData.Vertices.size();
        maxDegree = graphData.MaxDegree;
        minDegree = graphData.MinDegree;
        medianDegree = graphData.MedianDegree;
        CreateGraph(graphData);
    }

    private void CreateGraph(GraphData graphData){
        this.Graph = new int[graphData.MaxVertex+1][graphData.MaxVertex+1];
        for (var edge: graphData.Edges){
            Graph[edge.firstNode][edge.secondNode] = 1;
            Graph[edge.secondNode][edge.firstNode] = 1;
        }
    }
}
