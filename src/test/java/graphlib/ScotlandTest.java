package graphlib;

import org.junit.jupiter.api.Test;
import java.io.FileInputStream;

public class ScotlandTest
{
    @Test
    public void testGraphViz() throws Exception
    {
        Graph g = Graph.readUndirectedWeightedGraph(new FileInputStream("datafiles/scotlandc.txt"));
        String s = g.toUndirectedWeightedGraphViz();
        System.out.println(s);
    }

    public static void main(String[] args) throws Exception
    {
        // I can't figure out how to see System.out when I run JUnit through VS Code
        ScotlandTest test = new ScotlandTest();
        test.testGraphViz();
    }

}
