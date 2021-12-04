package main.classes;

import main.interfaces.IGraphRepresentation;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

public class AdjacentList implements IGraphRepresentation {
    private ArrayList<LinkedList<Integer>> Graph;
    private int numberOfEdges;
    private int numberOfVertex;

    public AdjacentList(String path){
        this.CreateGraph(path);
    }

    private void CreateGraph(String path) {
        this.Graph = new ArrayList<LinkedList<Integer>>();
        File graphFilePath = new File(path);

    }
}
