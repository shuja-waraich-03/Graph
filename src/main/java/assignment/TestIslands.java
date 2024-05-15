package assignment;

import java.io.InputStream;
import java.util.Scanner;

import graphlib.Graph;
import graphlib.Node;
import graphlib.NodeVisitor;

public class TestIslands
{
    private static String makeName(int r, int c)
    {
        return r + "-" + c;
    }

    public static class CountingVisitor implements NodeVisitor
    {
        private int count = 0;

        @Override
        public void visit(Node n)
        {
            count++;
        }

        public int getCount()
        {
            return count;
        }
    }

    public static void main(String[] args)
    {
        String s = 
"4 4\n" + 
"1100\n" + 
"0010\n" + 
"1000\n" +
"1111\n";

        Graph g = readIslands(new java.io.ByteArrayInputStream(s.getBytes()));

        int max = 0;
        for (Node n : g.getAllNodes())
        {
            CountingVisitor counting = new CountingVisitor();
            g.bfs(n.getName(), counting);
            if (counting.getCount() > max)
            {
                max = counting.getCount();
            }
        }

        System.out.println("Max island size: " + max);
    }

    public static Graph readIslands(InputStream in)
    {
        Scanner sc = new Scanner(in);
        int rows = sc.nextInt();
        int cols = sc.nextInt();
        sc.nextLine();
        int[][] grid = new int[rows][cols];
        for (int i = 0; i < rows; i++)
        {
            String line = sc.nextLine();
            for (int j = 0; j < cols; j++)
            {
                grid[i][j] = line.charAt(j) - '0';
            }
        }
        sc.close();

        Graph g = new Graph();

        for (int r=0; r<rows; r++)
        {
            for (int c=0; c<cols; c++)
            {
                if (grid[r][c] == 1)
                {
                    String name = makeName(r, c);
                    Node n = g.getOrCreateNode(name);
                    
                    // up right
                    if (r > 0 && c < cols-1 && grid[r-1][c+1] == 1)
                    {
                        String upRight = makeName(r-1, c+1);
                        Node upRightNode = g.getOrCreateNode(upRight);
                        n.addUnweightedUndirectedEdge(upRightNode);
                    }
                    
                    // right
                    if (c < cols-1 && grid[r][c+1] == 1)
                    {
                        String right = makeName(r, c+1);
                        Node rightNode = g.getOrCreateNode(right);
                        n.addUnweightedUndirectedEdge(rightNode);
                    }

                    // down right
                    if (r < rows-1 && c < cols-1 && grid[r+1][c+1] == 1)
                    {
                        String downRight = makeName(r+1, c+1);
                        Node downRightNode = g.getOrCreateNode(downRight);
                        n.addUnweightedUndirectedEdge(downRightNode);
                    }

                    // down
                    if (r < rows-1 && grid[r+1][c] == 1)
                    {
                        String down = makeName(r+1, c);
                        Node downNode = g.getOrCreateNode(down);
                        n.addUnweightedUndirectedEdge(downNode);
                    }
                }
            }
        }
        return g;
    }

}
