import com.oracle.tools.packager.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String args[]) {
        // Create a graph given in the above diagram
        Scanner scan = new Scanner(System.in);
        System.out.println("Wprowadz liczbę wierzchołków:");
        /** number of vertices **/
        int V = scan.nextInt();
        Graph g = new Graph(V);
        System.out.println("Wprowadź dane wierzchołków:");

        Scanner scan2 = new Scanner(System.in);
        for (int i = 0; i < V; i++) {
            String line = scan2.nextLine();
            String[] splitStringArray = line.split("\\s+");
            int[] splitIntArray = new int[splitStringArray.length];
            for (int j = 1; j < splitStringArray.length; j++) {
                splitIntArray[j] = Integer.parseInt(splitStringArray[j]);
                g.addEdge(i + 1, splitIntArray[j]);
            }
        }

        System.out.println("Following are strongly connected components in given graph ");
        g.printSCCs();
    }

}
