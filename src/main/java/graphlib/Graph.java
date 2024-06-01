package graphlib;

import java.io.InputStream;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Queue;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

import assignment.TestIslands.CountingVisitor;

import java.util.Collection;
import java.util.HashMap;

public class Graph
{
    private Map<String, Node> nodes;

    public Graph()
    {
        nodes = new HashMap<>();
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

    public Collection<Node> getAllNodes()
    {
        return nodes.values();
    }

    public void bfs(String startNodeName, NodeVisitor visitor)
    {
        Queue<Node> queue = new LinkedList<>();
        Set<Node> visited = new HashSet<>();
        Node start = nodes.get(startNodeName);
        if (start == null)
        {
            throw new IllegalArgumentException("Node " + startNodeName + " not found");
        }
        queue.add(start);
        while (!queue.isEmpty())
        {
            Node node = queue.remove();
            if (visited.contains(node))
            {
                // skip nodes we have already visited
                continue;
            }
            // visit the node, and mark it as visited
            visitor.visit(node);
            visited.add(node);
            for (Node neighbor : node.getNeighbors())
            {
                if (!visited.contains(neighbor))
                {
                    queue.add(neighbor);
                }
            }
        }
    }

    public void dfs(String startNodeName, NodeVisitor visitor)
    {
        
        Node startNode = nodes.get(startNodeName);
        if (startNode == null)
        {
            throw new IllegalArgumentException("Node " + startNodeName + " not found");
        }
        Set<Node> visited = new HashSet<>();
        Stack<Node> stack = new Stack<>();
        stack.push(startNode);
        while (!stack.isEmpty())
        {
            Node node = stack.pop();
            if (visited.contains(node))
            {
                // skip nodes we have already visited
                continue;
            }
            // visit the node, and mark it as visited
            visitor.visit(node);
            visited.add(node);
            for (Node neighbor : node.getNeighbors())
            {
                if (!visited.contains(neighbor))
                {
                    stack.push(neighbor);
                }
            }
        }
    }

    public int getMaxIslandSize()
    {
        int max = 0;
        for (Node n : getAllNodes())
        {
            CountingVisitor counting = new CountingVisitor();
            bfs(n.getName(), counting);
            if (counting.getCount() > max)
            {
                max = counting.getCount();
            }
        }
        return max;
    }

    private static class Path implements Comparable<Path>
    {
        private Node node;
        private double weight;
        // TODO: include the path
        //private List<Node> path;

        public Path(Node node, double weight)
        {
            this.node = node;
            this.weight = weight;
        }

        public Node getNode()
        {
            return node;
        }

        public double getWeight()
        {
            return weight;
        }

        public int compareTo(Path other)
        {
            return Double.compare(weight, other.weight);
        }
    }

    public Map<Node, Double> dijkstra(String startNodeName)
    {
        Map<Node, Double> distances = new HashMap<>();
        
        Node start = nodes.get(startNodeName);
        PriorityQueue<Path> pq = new PriorityQueue<>();

        pq.add(new Path(start, 0.0));

        while (!pq.isEmpty() && distances.size() < nodes.size())
        {
            Path edge = pq.remove();
            Node node = edge.getNode();
            if (distances.containsKey(node)) continue;

            double distance = edge.getWeight();

            distances.put(node, distance);
            
            for (Node neighbor : node.getNeighbors())
            {
                if (!distances.containsKey(neighbor))
                {
                    double newDistance = distance + node.getWeight(neighbor);
                    pq.add(new Path(neighbor, newDistance));
                }
            }
        }
        
        return distances;
    }

    private static interface MyQueue
    {
        void add(Node node);
        Node remove();
        boolean isEmpty();
    }

    private void xfs(String startNodeName, NodeVisitor visitor, MyQueue queue)
    {
        Node startNode = nodes.get(startNodeName);
        if (startNode == null)
        {
            throw new IllegalArgumentException("Node " + startNodeName + " not found");
        }
        Set<Node> visited = new HashSet<>();
        queue.add(startNode);
        while (!queue.isEmpty())
        {
            Node node = queue.remove();
            if (visited.contains(node))
            {
                // skip nodes we have already visited
                continue;
            }
            // visit the node, and mark it as visited
            visitor.visit(node);
            visited.add(node);
            for (Node neighbor : node.getNeighbors())
            {
                if (!visited.contains(neighbor))
                {
                    queue.add(neighbor);
                }
            }
        }
    }

    public void bfs2(String startNodeName, NodeVisitor visitor)
    {
        xfs(startNodeName, visitor, new MyQueue()
        {
            private Queue<Node> queue = new LinkedList<>();

            public void add(Node node)
            {
                queue.add(node);
            }

            public Node remove()
            {
                return queue.remove();
            }

            public boolean isEmpty()
            {
                return queue.isEmpty();
            }
        });
    }

    public void dfs2(String startNodeName, NodeVisitor visitor)
    {
        xfs(startNodeName, visitor, new MyQueue()
        {
            private Stack<Node> stack = new Stack<>();

            public void add(Node node)
            {
                stack.push(node);
            }

            public Node remove()
            {
                return stack.pop();
            }

            public boolean isEmpty()
            {
                return stack.isEmpty();
            }
        });
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

    public int getNumComponents()
    {
        Set<Node> visited = new HashSet<>();
        int numComponents = 0;
        for (Node node : nodes.values())
        {
            if (!visited.contains(node))
            {
                numComponents++;
                dfs(node.getName(), new NodeVisitor()
                {
                    public void visit(Node node)
                    {
                        visited.add(node);
                    }
                });
            }
        }
        return numComponents;
    }

    public static Graph readIslandFile(InputStream in) 
    {
        Graph graph = new Graph();
        Scanner scanner = new Scanner(in);
        int numRows = scanner.nextInt();
        int numCols = scanner.nextInt();
        // TODO: pick up here
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




    public Graph inverseGraph() {
        Graph newGraph = new Graph();
    
        // Get all nodes from the original graph
        Collection<Node> allNodes = getAllNodes();
        
        // Create all nodes in the new graph
        for (Node n1 : allNodes) {
            newGraph.getOrCreateNode(n1.getName());
        }
    
        // Add inverse edges to the new graph
        for (Node n1 : allNodes) {
            for (Node n2 : allNodes) {
                if (!n1.equals(n2) && !n1.hasEdge(n2)) {
                    Node newN1 = newGraph.getOrCreateNode(n1.getName());
                    Node newN2 = newGraph.getOrCreateNode(n2.getName());
                    if (!newN1.hasEdge(newN2)) {
                        newN1.addUnweightedUndirectedEdge(newN2);
                    }
                }
            }
        }
    
        return newGraph;
    }
    
    public Map<String, Set<String>> getReachableNodes() {
        Map<String, Set<String>> reachableNodes = new HashMap<>();
        for (String nodeName : nodes.keySet()) {
            reachableNodes.put(nodeName, new HashSet<>());
            dfsForReachability(nodeName, nodeName, reachableNodes, new HashSet<>());
        }
        return reachableNodes;
    }

    private void dfsForReachability(String startNodeName, String currentNodeName, Map<String, Set<String>> reachableNodes, Set<String> visited) {
        Node currentNode = nodes.get(currentNodeName);
        if (currentNode == null) {
            return;
        }
        visited.add(currentNodeName);
        for (Node neighbor : currentNode.getNeighbors()) {
            if (!visited.contains(neighbor.getName())) {
                reachableNodes.get(startNodeName).add(neighbor.getName());
                dfsForReachability(startNodeName, neighbor.getName(), reachableNodes, visited);
            }
        }
    }

    public static void main(String[] args) {
        // Sample graph creation
        Graph graph = new Graph();
        graph.getOrCreateNode("1").addUnweightedDirectedEdge(graph.getOrCreateNode("2"));
        graph.getOrCreateNode("7").addUnweightedDirectedEdge(graph.getOrCreateNode("8"));
        graph.getOrCreateNode("2").addUnweightedDirectedEdge(graph.getOrCreateNode("3"));
        graph.getOrCreateNode("3").addUnweightedDirectedEdge(graph.getOrCreateNode("4"));
        graph.getOrCreateNode("4").addUnweightedDirectedEdge(graph.getOrCreateNode("1"));

        // Compute reachable nodes
        Map<String, Set<String>> result = graph.getReachableNodes();

        // Print the result
        for (Map.Entry<String, Set<String>> entry : result.entrySet()) {
            System.out.println("From node " + entry.getKey() + " reachable nodes are " + entry.getValue());
        }
    }
}


