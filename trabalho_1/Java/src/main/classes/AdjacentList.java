package main.classes;

import main.interfaces.IGraphRepresentation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

public class AdjacentList implements IGraphRepresentation {
    public LinkedList<Integer>[] Graph;
    public HashMap<Integer,HashMap<Integer, Float>> Weights;
    public ArrayList<Integer>vertices;
    public ArrayList<Edge> Edges;
    public int numberOfEdges;
    public int numberOfVertices;
    public int maxDegree;
    public int minDegree;
    public int medianDegree;
    public boolean hasNegativeWeights = false;
    public boolean hasWeights = false;

    public AdjacentList(GraphData graphData) {
            numberOfEdges = graphData.Edges.size();
            numberOfVertices = graphData.Vertices.size();
            vertices = graphData.Vertices;
            maxDegree = graphData.MaxDegree;
            minDegree = graphData.MinDegree;
            medianDegree = graphData.MedianDegree;
            hasNegativeWeights = graphData.hasNegativeWeights;
            hasWeights = graphData.hasWeights;
            Edges = graphData.Edges;
            CreateGraph(graphData);
    }

    private void CreateGraph(GraphData graphData) {
        this.Graph = new LinkedList[graphData.NumberOfVertices + 1];
        this.Weights = new HashMap<Integer,HashMap<Integer,Float>>();

        for (var vertex: graphData.Vertices){
            Graph[vertex] = new LinkedList<>();
            Weights.put(vertex, new HashMap<Integer, Float>());
        }

        for (var edge : graphData.Edges) {
            Graph[edge.firstNode].add(edge.secondNode);
            Graph[edge.secondNode].add(edge.firstNode);
            Weights.get(edge.firstNode).put(edge.secondNode, edge.weight);
            Weights.get(edge.secondNode).put(edge.firstNode, edge.weight);
        }
    }

    @Override
    public ArrayList<Integer> GetVertices() {
        return vertices;
    }
}
