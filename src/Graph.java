// Java implementation of Kosaraju's algorithm to print all SCCs

import com.oracle.tools.packager.Log;

import java.util.*;
import java.util.LinkedList;

// This class represents a directed graph using adjacency list
// representation
class Graph {
    private int V;   // No. of vertices
    private LinkedList<Integer> adj[]; //Adjacency List
    private List<LinkedList<Integer>> SCCs = new ArrayList<LinkedList<Integer>>();
    private int[] numberOfNeighbours;

    //Constructor
    Graph(int v) {
        V = v;
        adj = new LinkedList[v + 1];
        for (int i = 1; i <= v; ++i)
            adj[i] = new LinkedList();
    }

    //Function to add an edge into the graph
    public void addEdge(int v, int w) {
        adj[v].add(w);
    }

    // A recursive function to print DFS starting from v
    private void DFSUtil(int v, boolean visited[], int sccIndex) {
        // Mark the current node as visited and print it
        visited[v] = true;
        SCCs.get(sccIndex).add(v);
        System.out.print(v + " ");

        int n;

        // Recur for all the vertices adjacent to this vertex
        Iterator<Integer> i = adj[v].iterator();
        while (i.hasNext()) {
            n = i.next();
            if (!visited[n])
                DFSUtil(n, visited, sccIndex);
        }
    }

    // Function that returns reverse (or transpose) of this graph
    private Graph getTranspose() {
        Graph g = new Graph(V);
        for (int v = 1; v <= V; v++) {
            // Recur for all the vertices adjacent to this vertex
            final int index = v;
            adj[v].forEach(integer -> g.adj[integer].add(index));

        }
        return g;
    }

    private void fillOrder(int v, boolean visited[], Stack stack) {
        // Mark the current node as visited and print it
        visited[v] = true;

        // Recur for all the vertices adjacent to this vertex
        adj[v].forEach(n -> {
                    if (!visited[n])
                        fillOrder(n, visited, stack);
                }
        );

        // All vertices reachable from v are processed by now,
        // push v to Stack
        stack.push(new Integer(v));
    }

    // The main function that finds and prints all strongly
    // connected components
    void printSCCs() {
        Stack stack = new Stack();

        // Mark all the vertices as not visited (For first DFS)
        boolean visited[] = new boolean[V + 1];
        for (int i = 1; i <= V; i++)
            visited[i] = false;

        // Fill vertices in stack according to their finishing
        // times
        for (int i = 1; i <= V; i++)
            if (!visited[i])
                fillOrder(i, visited, stack);

        // Create a reversed graph
        Graph gr = getTranspose();

        // Mark all the vertices as not visited (For second DFS)
        for (int i = 1; i <= V; i++)
            visited[i] = false;

        // Now process all vertices in order defined by Stack
        int sccIndex = 0;
        while (!stack.empty()) {
            // Pop a vertex from stack
            int v = (int) stack.pop();
            // Print Strongly connected component of the popped vertex
            if (!visited[v]) {
                gr.SCCs.add(sccIndex, new LinkedList());
                gr.DFSUtil(v, visited, sccIndex);
                System.out.println();
                sccIndex++;
            }
        }
        Log.debug("Stop");

        SCCs = gr.SCCs;
        numberOfNeighbours = new int[SCCs.size()];
        for (int number : numberOfNeighbours) {
            number = 0;
        }
        findNumberOfNeghbours();
    }

    private void findNumberOfNeghbours() {
        int counter = 0;
        for (LinkedList linkedList : SCCs) {
            for (LinkedList linkedList2 : SCCs) {
                if (!linkedList.equals(linkedList2)) {
                    boolean added = false;
                    for (Object aLinkedList : linkedList) {
                        for (Object aLinkedList2 : linkedList2) {
                            Integer i1 = (Integer) aLinkedList;
                            Integer i2 = (Integer) aLinkedList2;
                            for (Integer adj : adj[i1]) {
                                if (adj.equals(i2) && !added) {
                                    numberOfNeighbours[counter]++;
                                    added = true;
                                }
                            }
                            for (Integer adj : adj[i2]) {
                                if (adj.equals(i1) && !added) {
                                    numberOfNeighbours[counter]++;
                                    added = true;
                                }
                            }
                        }
                    }
                }
            }
            counter++;
        }

        int SCCIndex = 0, maxValue = numberOfNeighbours[0];

        for (int i = 0; i < numberOfNeighbours.length; i++) {
            int value = numberOfNeighbours[i];
            if (value > maxValue) {
                maxValue = value;
                SCCIndex = i;
            }
        }

        for (Integer vertex : SCCs.get(SCCIndex)) {
            System.out.print(vertex + " ");
        }
    }
}