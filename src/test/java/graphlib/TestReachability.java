package graphlib;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.Set;
import java.util.HashSet;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestReachability {

    private Graph graph;

    @BeforeEach
    public void setUp() {
        graph = new Graph();
    }

    @Test
    public void testSimpleGraph() {
        graph.getOrCreateNode("1").addUnweightedDirectedEdge(graph.getOrCreateNode("2"));
        graph.getOrCreateNode("1").addUnweightedDirectedEdge(graph.getOrCreateNode("3"));
        graph.getOrCreateNode("2").addUnweightedDirectedEdge(graph.getOrCreateNode("3"));
        graph.getOrCreateNode("3").addUnweightedDirectedEdge(graph.getOrCreateNode("4"));

        Map<String, Set<String>> expected = new HashMap<>();
        expected.put("1", Set.of("2", "3", "4"));
        expected.put("2", Set.of("3", "4"));
        expected.put("3", Set.of("4"));
        expected.put("4", Set.of());

        Map<String, Set<String>> result = graph.getReachableNodes();
        assertEquals(expected, result);
    }

    @Test
    public void testGraphWithCycle() {
        graph.getOrCreateNode("1").addUnweightedDirectedEdge(graph.getOrCreateNode("2"));
        graph.getOrCreateNode("2").addUnweightedDirectedEdge(graph.getOrCreateNode("3"));
        graph.getOrCreateNode("3").addUnweightedDirectedEdge(graph.getOrCreateNode("4"));
        graph.getOrCreateNode("4").addUnweightedDirectedEdge(graph.getOrCreateNode("1"));

        Map<String, Set<String>> expected = new HashMap<>();
        expected.put("1", Set.of("2", "3", "4"));
        expected.put("2", Set.of("3", "4", "1"));
        expected.put("3", Set.of("4", "1", "2"));
        expected.put("4", Set.of("1", "2", "3"));

        Map<String, Set<String>> result = graph.getReachableNodes();
        assertEquals(expected, result);
    }

    @Test
    public void testDisconnectedGraph() {
        graph.getOrCreateNode("1").addUnweightedDirectedEdge(graph.getOrCreateNode("2"));
        graph.getOrCreateNode("3").addUnweightedDirectedEdge(graph.getOrCreateNode("4"));

        Map<String, Set<String>> expected = new HashMap<>();
        expected.put("1", Set.of("2"));
        expected.put("2", Set.of());
        expected.put("3", Set.of("4"));
        expected.put("4", Set.of());

        Map<String, Set<String>> result = graph.getReachableNodes();
        assertEquals(expected, result);
    }

    @Test
    public void testSelfLoop() {
        graph.getOrCreateNode("1").addUnweightedDirectedEdge(graph.getOrCreateNode("1"));

        Map<String, Set<String>> expected = new HashMap<>();
        expected.put("1", Set.of());

        Map<String, Set<String>> result = graph.getReachableNodes();
        assertEquals(expected, result);
    }
}
