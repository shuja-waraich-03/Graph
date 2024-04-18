package graphlib;

import java.io.InputStream;
import java.util.Map;
import java.util.Scanner;

public class Graph
{
    private Map<String, Node> nodes;

    public Graph()
    {
        nodes = new java.util.HashMap<>();
    }

    public Node getOrCreateNode(String name)
    {
        Node node = nodes.get(name);
        if (node == null)
        {
            node = new Node(name);
            nodes.put(name, node);
        }
        return node;
    }

    public boolean containsNode(String name)
    {
        return nodes.containsKey(name);
    }

    /**
     * Returns a string representation of the graph in GraphViz format.
     * 
     * This is for an <b>undirected</b>, <b>weighted</b> graph
     * @return
     */
    public String toUndirectedWeightedGraphViz()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("graph G {\n");
        for (Node node : nodes.values())
        {
            for (Node neighbor : node.getNeighbors())
            {
                // make sure we only add each edge once
                if (node.getName().compareTo(neighbor.getName()) < 0)
                {
                    sb.append(String.format("  %s -- %s [label=\"%.1f\"];\n", node.getName(), neighbor.getName(), node.getWeight(neighbor)));
                }
            }
        }
        sb.append("}\n");
        return sb.toString();
    }
    /**
     * Returns a string representation of the graph in GraphViz format.
     * 
     * This is for an <b>directed</b>, <b>weighted</b> graph
     * @return
     */
    public String toDirectedWeightedGraphViz()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("digraph G {\n");
        for (Node node : nodes.values())
        {
            for (Node neighbor : node.getNeighbors())
            {
                // append using String.format
                sb.append(String.format("  %s -> %s [label=\"%.1f\"];\n", node.getName(), neighbor.getName(), node.getWeight(neighbor)));
            }
        }
        sb.append("}\n");
        return sb.toString();
    }

    /**
     * Returns a string representation of the graph in GraphViz format.
     * 
     * This is for an <b>directed</b>, <b>unweighted</b> graph
     * @return
     */
    public String toDirectedUnweightedGraphViz()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("digraph G {\n");
        for (Node node : nodes.values())
        {
            for (Node neighbor : node.getNeighbors())
            {
                // append using String.format
                sb.append(String.format("  %s -> %s;\n", node.getName(), neighbor.getName()));
            }
        }
        sb.append("}\n");
        return sb.toString();
    }

    /**
     * Returns a string representation of the graph in GraphViz format.
     * 
     * This is for an <b>undirected</b>, <b>unweighted</b> graph
     * @return
     */
    public String toUndirectedUnweightedGraphViz()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("graph G {\n");
        for (Node node : nodes.values())
        {
            for (Node neighbor : node.getNeighbors())
            {
                // make sure we only add each edge once
                if (node.getName().compareTo(neighbor.getName()) < 0)
                {
                    sb.append("  " + node.getName() + " -- " + neighbor.getName() + ";\n");
                }
            }
        }
        sb.append("}\n");
        return sb.toString();
    }
    
    public static Graph readUndirectedUnweightedGraph(InputStream in)
    {
        Graph graph = new Graph();
        Scanner scanner = new Scanner(in);
        while (scanner.hasNext())
        {
            String nameA = scanner.next();
            String nameB = scanner.next();
            Node nodeA = graph.getOrCreateNode(nameA);
            Node nodeB = graph.getOrCreateNode(nameB);
            nodeA.addUnweightedUndirectedEdge(nodeB);
        }
        scanner.close();
        return graph;
    }

    public static Graph readDirectedUnweightedGraph(InputStream in)
    {
        Graph graph = new Graph();
        Scanner scanner = new Scanner(in);
        while (scanner.hasNext())
        {
            String nameA = scanner.next();
            String nameB = scanner.next();
            Node nodeA = graph.getOrCreateNode(nameA);
            Node nodeB = graph.getOrCreateNode(nameB);
            nodeA.addUnweightedDirectedEdge(nodeB);
        }
        scanner.close();
        return graph;
    }

    public static Graph readUndirectedWeightedGraph(InputStream in)
    {
        Graph graph = new Graph();
        Scanner scanner = new Scanner(in);
        while (scanner.hasNext())
        {
            String nameA = scanner.next();
            String nameB = scanner.next();
            double weight = scanner.nextDouble();
            Node nodeA = graph.getOrCreateNode(nameA);
            Node nodeB = graph.getOrCreateNode(nameB);
            nodeA.addUndirectedEdge(nodeB, weight);
        }
        scanner.close();
        return graph;
    }

    public static Graph readDirectedWeightedGraph(InputStream in)
    {
        Graph graph = new Graph();
        Scanner scanner = new Scanner(in);
        while (scanner.hasNext())
        {
            String nameA = scanner.next();
            String nameB = scanner.next();
            double weight = scanner.nextDouble();
            Node nodeA = graph.getOrCreateNode(nameA);
            Node nodeB = graph.getOrCreateNode(nameB);
            nodeA.addDirectedEdge(nodeB, weight);
        }
        scanner.close();
        return graph;
    }

}
