package assignment;

import graphlib.Graph;
import graphlib.Node;

public class TestInverse
{
    public static void main(String[] args)
    {
        Graph g = new Graph();
        Node a = g.getOrCreateNode("A");
        Node b = g.getOrCreateNode("B");
        Node c = g.getOrCreateNode("C");
        Node d = g.getOrCreateNode("D");

        a.addUnweightedUndirectedEdge(c);
        b.addUnweightedUndirectedEdge(d);

        System.out.println(g.toUndirectedUnweightedGraphViz());

        Graph newGraph = g.inverseGraph();

        System.out.println(newGraph.toUndirectedUnweightedGraphViz());

    }

}
