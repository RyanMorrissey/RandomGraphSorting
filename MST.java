/*
	Author: Ryan Morrissey
	Course: CSCI.261.02 - Analysis of Algorithms
	Date: 3/13/2016
	
	This program generates graphs!  It will call a generate graph
	function that will keep generating random graphs.  If the graph
	passes a DFS test to prove that it is connected, it will get passed
	back to main where it will print out all of its information, 
	including the DFS results.  The program will then do 3 different sorts
	on both the matrix and list and print out the results.
	
	Includes main
*/

import java.io.*;
import java.util.*;
import java.lang.*;

public class MST
{	
	public static void main(String[] args) throws IOException
	{
		long startTime = System.currentTimeMillis();
		File file = null;
		// First to make sure args is 1
		if (1 == args.length) 
		{
			file = new File(args[0]);
			if(file.exists() == false) // To check if the file exists
			{
				System.out.println("Input file not found");
				System.exit(1);
			}
		} 
		else 
		{
			System.err.println("Usage: java MST filename.txt waka waka");
			System.exit(1);
		}
		FileReader fr = new FileReader(file);
		BufferedReader br = new BufferedReader(fr);
		String line;
		int n = 0;
		int seed = 0;
		double p = 0.0;
		int valueCollect = 1;
		while((line = br.readLine()) != null)
		{

			if(valueCollect == 1) // set n
			{
				if(isNumeric(line) == true)
				{
					try
					{
						n = Integer.parseInt(line);
					}
					catch(NumberFormatException nfe)
					{
						System.out.println(line + " is not a valid number");
					}
					// Now to make sure n is valid
					if (n < 2)
					{
						System.out.println("n must be greater than 1");
						br.close();
						fr.close();
						System.exit(1);
					}
				}
				else // line is not a number
				{
					System.out.println("n and seed must be integers");
					br.close();
					fr.close();
					System.exit(1);
				}
			}
			else if(valueCollect == 2) // set seed
			{
				if(isNumeric(line) == true)
				{
					try
					{
						seed = Integer.parseInt(line);
					}
					catch(NumberFormatException nfe)
					{
						System.out.println(line + " is not a valid number");
					}
				}
				else // line is not a number
				{
					System.out.println("n and seed must be integers");
					br.close();
					fr.close();
					System.exit(1);
				}
			}
			else if(valueCollect == 3) // set p
			{
				if(isNumeric(line) == true)
				{
					try
					{
						p = Double.parseDouble(line);
						if (p <= 0 || p >= 1)
						{
							System.out.println("p must be a value between 0 and 1");
							br.close();
							fr.close();
							System.exit(1);
						}
					}
					catch(NumberFormatException nfe)
					{
						System.out.println("p must be a real number");
					}
				}
				else // line is not a number
				{
					System.out.println("p must be a real number");
					br.close();
					fr.close();
					System.exit(1);
				}
			}
			else // file check
			{
				System.out.println(args[0] + " is not of the correct format.");
				br.close();
				fr.close();
				System.exit(1);
			}
			valueCollect++;
        }
		br.close();
		fr.close();
		System.out.println("TEST: n=" + n + ", seed=" + seed + ", p=" + p);
		
		// Time for fun stuff!
		int[][] matrix = new int[n][n];
		matrix = createMatrix(n, seed, p); // This will only return a fully connected graph
		// First, to print how long it took to create this graph.
		long endTime = System.currentTimeMillis();
        System.out.println("Time to generate the graph: " + (endTime - startTime) + " milliseconds");
		// Now to print the graph as an adjacency matrix
		if(n < 10) // Only print all of this information when n < 10
		{
			System.out.println("\nThe graph as an adjacency matrix:\n");
			for(int i = 0; i < n; i++)
			{
				for(int j = 0; j < n; j++)
				{
					System.out.print(" " + matrix[i][j] + "  ");
				}
				System.out.println("\n");
			}
			System.out.println("The graph as an adjacency list:");
			for(int i = 0; i < n; i++)
			{
				System.out.print(i + "-> ");
				for(int j = 0; j < n; j++)
				{
					if (matrix[i][j] > 0)
					{
						System.out.print(j +"(" + matrix[i][j] + ") ");
					}
				}
				System.out.println("");
			}
			int[] vis = new int[n];
			for (int i = 0; i < n; i++)  // Needed to set all values to -2 (the value for false)
			{
				vis[i] = -2;
			}
			boolean runDFS = DFS(matrix, vis, n, 0, -1);
			System.out.println("\nDepth-First Search:\nVertices:");
			for(int i = 0; i < n; i++)
			{
				System.out.print(" " + i);
			}
			System.out.print("\nPredecessors:\n");
			for(int i = 0; i < n; i++)
			{
				System.out.print(vis[i] + " ");
			}
			System.out.println();
		}
		
		// Now to make the actual Adjacency List data structure
		// It will be a sort of jagged array.
		// It will be full of Edges, since there really isnt a reason to create a new object
		Edge[][] adjList = new Edge[n][1]; // The 1 is a placeholder, the size will change
		for(int i = 0; i < n; i++)
		{
			int count = 0;
			// First to iterate once through the matrix row to get the size of the list
			for(int j = 0; j < n; j++)
			{
				if(matrix[i][j] != 0)
					count++;
			}
			// Now to fill up the adjList
			adjList[i] = new Edge[count];
			count = 0; // Reset count to be used again
			for(int j = 0; j < n; j++)
			{
				if(matrix[i][j] != 0)
				{
					adjList[i][count] = new Edge(i, j, matrix[i][j]);
					count++;
				}
			}
		}
		
		// Now for some edge calls!
		Edge.matrixEdgeSort(matrix, n);
		Edge.adjListEdgeSort(adjList, n);
		
		//Now for the prim function calls
		Edge edgeArrayMatrix[];
		Edge edgeArrayAdj[];
		Edge.primMatrix(matrix, n);
		Edge.primAdj(adjList, n);
    }
   
	// Function to check if a string is actually a number
	public static boolean isNumeric(String str)  
	{  
		try  
		{  
			double d = Double.parseDouble(str);  
		}  
		catch(NumberFormatException nfe)  
		{  
			return false;  
		}  
		return true;  
	}
	
	// This will return a fully connected graph
	public static int[][] createMatrix(int n, int seed, double p)
	{
		Random random = new Random(seed);
		Random random2 = new Random(seed * 2);
		int[][] graphCheck = new int[n][n];
		boolean graphTest = false;
		while(graphTest == false) // This is meant to keep looping until a good graph is produced.
		{
			int count = 0;
			graphCheck = new int[n][n]; // reset the weights
			for(int i = 0; i < (n - 1); i++) // Create a random graph
			{
				for(int x = (1 + count); x < n; x++)
				{
					double randomNum = random.nextDouble(); // p test
					int weight = 0;//random2.nextInt(n) + 1; // potential weight
					if(randomNum <= p)
					{
						weight = random2.nextInt(n) + 1; // potential weight
						graphCheck[i][x] = weight;
						graphCheck[x][i] = weight;
					}
				}
				count++;
			}
			// Now to generate a boolean array to be used.
			int[] vis = new int[n];
			for (int i = 0; i < n; i++)  // Needed to set all values to false
			{
				vis[i] = -2;
			}
			graphTest = DFS(graphCheck, vis, n, 0, -1); // test to see if the graph is completely connected.
		}
		return graphCheck;
	}
	
	// n is the number of nodes, i is the vertex, and x is the predecessor
	public static boolean DFS(int[][] adjMatrix, int[] visited, int n, int i, int x)
	{
		if(visited[i] == -2)
			visited[i] = x;
		for (int j = 0; j < n; j++)
			{
			if(visited[j] == -2 && adjMatrix[i][j]!=0){
				boolean notImportant = DFS(adjMatrix, visited, n, j, i);
			}
	
		}
		for (int z = 1; z < n; z++)
		{
			if (visited[z] == -2)
			{
				return false;
			}
		}
		return true; // meaning nothing was false.
	}
}
