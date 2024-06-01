package graphlib;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Test.*;

import graphlib.Graph;
import graphlib.Node;

import graphlib.Graph;
import graphlib.Node;

public class TestInverseGraph {

    @Test
    public void testEmptyGraph() {
        Graph g = new Graph();
        Graph invertedGraph = g.inverseGraph();
        String expectedOutput = "graph G {\n}\n";
        assertEquals(expectedOutput, invertedGraph.toUndirectedUnweightedGraphViz());
    }

    @Test
    public void testSingleNodeGraph() {
        Graph g = new Graph();
        g.getOrCreateNode("A");
        Graph invertedGraph = g.inverseGraph();
        String expectedOutput = "graph G {\n}\n";
        assertEquals(expectedOutput, invertedGraph.toUndirectedUnweightedGraphViz());
    }

    @Test
    public void testGraphWithNoEdges() {
        Graph g = new Graph();
        g.getOrCreateNode("A");
        g.getOrCreateNode("B");
        g.getOrCreateNode("C");
        Graph invertedGraph = g.inverseGraph();
        String expectedOutput = "graph G {\n  A -- B;\n  A -- C;\n  B -- C;\n}\n";
        assertEquals(expectedOutput, invertedGraph.toUndirectedUnweightedGraphViz());
    }

    @Test
    public void testGraphWithSingleEdge() {
        Graph g = new Graph();
        Node a = g.getOrCreateNode("A");
        Node b = g.getOrCreateNode("B");
        a.addUnweightedUndirectedEdge(b);
        Graph invertedGraph = g.inverseGraph();
        String expectedOutput = "graph G {\n}\n";
        assertEquals(expectedOutput, invertedGraph.toUndirectedUnweightedGraphViz());
    }

    @Test
    public void testFullyConnectedGraph() {
        Graph g = new Graph();
        Node a = g.getOrCreateNode("A");
        Node b = g.getOrCreateNode("B");
        Node c = g.getOrCreateNode("C");
        Node d = g.getOrCreateNode("D");
        a.addUnweightedUndirectedEdge(b);
        a.addUnweightedUndirectedEdge(c);
        a.addUnweightedUndirectedEdge(d);
        b.addUnweightedUndirectedEdge(c);
        b.addUnweightedUndirectedEdge(d);
        c.addUnweightedUndirectedEdge(d);
        Graph invertedGraph = g.inverseGraph();
        String expectedOutput = "graph G {\n}\n";
        assertEquals(expectedOutput, invertedGraph.toUndirectedUnweightedGraphViz());
    }

    @Test
    public void testTypicalGraph() {
        Graph g = new Graph();
        Node a = g.getOrCreateNode("A");
        Node b = g.getOrCreateNode("B");
        Node c = g.getOrCreateNode("C");
        Node d = g.getOrCreateNode("D");
        a.addUnweightedUndirectedEdge(c);
        b.addUnweightedUndirectedEdge(d);
        Graph invertedGraph = g.inverseGraph();
        String expectedOutput = "graph G {\n  A -- D;\n  A -- B;\n  B -- C;\n  C -- D;\n}\n";
        assertEquals(expectedOutput, invertedGraph.toUndirectedUnweightedGraphViz());
    }
}