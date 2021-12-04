package main.classes;

import main.interfaces.IGraphRepresentation;

import java.util.ArrayList;
import java.util.LinkedList;

public class AdjacentList implements IGraphRepresentation {
    private LinkedList<Integer>[] Graph;
    private ArrayList<Integer>vertices;
    private int numberOfEdges;
    private int numberOfVertices;

    public AdjacentList(GraphData graphData) {
        numberOfEdges = graphData.Edges.size();
        numberOfVertices = graphData.Vertices.size();
        vertices = graphData.Vertices;
        CreateGraph(graphData);
    }

    private void CreateGraph(GraphData graphData) {
        this.Graph = new LinkedList[graphData.MaxVertex + 1];

        for (var vertex: graphData.Vertices)
            Graph[vertex] = new LinkedList<>();

        for (var edge : graphData.Edges) {
            Graph[edge.firstNode].add(edge.secondNode);
            Graph[edge.secondNode].add(edge.firstNode);
        }
        int i =0;
        for(int vertex : vertices){
            i++;
            System.out.print( i+ " "+ vertex + " -> ");
            for (var next : Graph[vertex]) System.out.print(" " + next);
            System.out.println();
        }
    }
}
