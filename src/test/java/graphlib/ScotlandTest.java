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
        ScotlandTest test = new ScotlandTest();
        test.testGraphViz();
    }

}
