package graphlib;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import assignment.TestIslands;

import static org.junit.jupiter.api.Assertions.*;


import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;



import graphlib.Graph;
import graphlib.Node;

public class TestIslandsJuit
{
    private TestIslands testIslands;

    @Before
    public void setUp() throws Exception
    {
        testIslands = new TestIslands();
    }

    @Test
    public void testReadIslands() {
        String input = "3 3\n110\n010\n001";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        Graph g = TestIslands.readIslands(in);

        assertEquals(4, g.getAllNodes().size());
    }

    @Test
    public void testReadIslandsFromFile() throws FileNotFoundException
    {
        Graph g = TestIslands.readIslandsFromFile("datafiles/islands2.txt");

        assertNotNull(g);
        assertEquals(12, g.getAllNodes().size());
    }


    @Test
    public void testIslandWithNoLand()throws FileNotFoundException{
        Graph g = TestIslands.readIslandsFromFile("datafiles/nolandIsland.txt");
        assertNotNull(g);
        assertEquals(0, g.getMaxIslandSize());
    }

    @Test
    public void testSizeOneIsland()throws FileNotFoundException{
        Graph g = TestIslands.readIslandsFromFile("datafiles/singlesizeisland.txt");
        assertNotNull(g);
        assertEquals(1, g.getMaxIslandSize());
    }

    @Test
    public void testTwoIslands()throws FileNotFoundException{
        Graph g = TestIslands.readIslandsFromFile("datafiles/twoislands.txt");
        assertEquals(6, g.getMaxIslandSize());
    }


}
