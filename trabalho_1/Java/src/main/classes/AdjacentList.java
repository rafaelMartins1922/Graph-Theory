package main.classes;

import main.interfaces.IGraphRepresentation;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.LinkedList;

public class AdjacentList implements IGraphRepresentation {
    public LinkedList<Integer>[] Graph;
    public ArrayList<Integer>vertices;
    public int numberOfEdges;
    public int numberOfVertices;
    public int maxDegree;
    public int minDegree;
    public int medianDegree;

    public AdjacentList(@NotNull GraphData graphData) {
        numberOfEdges = graphData.Edges.size();
        numberOfVertices = graphData.Vertices.size();
        vertices = graphData.Vertices;
        maxDegree = graphData.MaxDegree;
        minDegree = graphData.MinDegree;
        medianDegree = graphData.MedianDegree;

        CreateGraph(graphData);
    }

    private void CreateGraph(@NotNull GraphData graphData) {
        this.Graph = new LinkedList[graphData.MaxVertex + 1];

        for (var vertex: graphData.Vertices)
            Graph[vertex] = new LinkedList<>();

        for (var edge : graphData.Edges) {
            Graph[edge.firstNode].add(edge.secondNode);
            Graph[edge.secondNode].add(edge.firstNode);
        }
    }
}
