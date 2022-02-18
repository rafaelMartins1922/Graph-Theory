package main.classes;

import main.interfaces.IGraphRepresentation;

import java.util.ArrayList;

public class AdjacentMatrix implements IGraphRepresentation {
    public int[][] Graph;
    public float[][] Weights;
    public ArrayList<Integer>vertices;
    public ArrayList<Edge> Edges;
    public int numberOfEdges;
    public int numberOfVertices;
    public int maxDegree;
    public int minDegree;
    public int medianDegree;
    public boolean hasNegativeWeights = false;
    public boolean hasWeights = false;

    public AdjacentMatrix(GraphData graphData) {
            numberOfEdges = graphData.Edges.size();
            numberOfVertices = graphData.Vertices.size();
            maxDegree = graphData.MaxDegree;
            minDegree = graphData.MinDegree;
            medianDegree = graphData.MedianDegree;
            vertices = graphData.Vertices;
            hasNegativeWeights = graphData.hasNegativeWeights;
            hasWeights = graphData.hasWeights;
            Edges = graphData.Edges;
            CreateGraph(graphData);
    }

    private void CreateGraph(GraphData graphData){
        this.Graph = new int[graphData.MaxVertex+1][graphData.MaxVertex+1];
        this.Weights = new float[graphData.MaxVertex+1][graphData.MaxVertex+1];
        for (var edge: graphData.Edges){
            Graph[edge.firstNode][edge.secondNode] = 1;
            Graph[edge.secondNode][edge.firstNode] = 1;
            Weights[edge.firstNode][edge.secondNode] = edge.weight;
            Weights[edge.secondNode][edge.firstNode] = edge.weight;
        }
    }

    @Override
    public ArrayList<Integer> GetVertices() {
        return vertices;
    }
}
