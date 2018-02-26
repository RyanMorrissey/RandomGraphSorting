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

 	For use in MST.java
*/

import java.util.*;

class Edge
{
	int leftNode;
	int rightNode;
	int weight;
	public Edge(int leftNode, int rightNode, int weight)
	{
		this.leftNode = leftNode;
		this.rightNode = rightNode;
		this.weight = weight;
	}
	
	// My glorious kruskal function.  Really standard when it comes to Kruskal implementation
	public static int kruskal(ArrayList<Edge> edgeList, int nodes)
	{
		int components = nodes;
		int cost = 0;
		int counter = 0;
		
		DisjointSets sets = new DisjointSets(nodes);
		
		while(components > 1)
		{
			Edge e = new Edge(edgeList.get(counter).leftNode, edgeList.get(counter).rightNode, edgeList.get(counter).weight);
			counter++;
			if(sets.find(e.leftNode) == sets.find(e.rightNode))
				continue;
			if(nodes <= 9)
				System.out.println(toString(e));
			sets.union(e.leftNode, e.rightNode);
			components--;
			cost += e.weight;
		}
		return cost;
	}
	
	public static void primMatrix(int[][] matrix, int n)
	{
		int weight = 0;
		Long startTime = System.currentTimeMillis();
		MinHeap prioQ = new MinHeap(n);
		boolean visited[] = new boolean[n]; 
		Edge nodeArray[] = new Edge[n];
		visited[0] = true; // mark node 0 as visited
		int counter = 1;
		
		// First, to add the edges attached to 0 as a starting point
		for(int i = 0; i < n; i++)
		{
			if(matrix[0][i] != 0)
				prioQ.insert(new Edge(0, i, matrix[0][i]));
		}
		while(counter < n) // Where the magic of Prim is located
		{
			Edge edge = prioQ.remove(); // Remove the top
			int rNode = edge.rightNode;
			if(visited[rNode] == false)
			{
				visited[rNode] = true;
				weight += edge.weight;
				nodeArray[rNode] = edge; // Add it to nodeArray to finalize
				for(int i = 0; i < n; i++) // Now to add more edges to the heap
				{
					if(visited[i] == false && matrix[rNode][i] != 0) 
					{
						prioQ.insert(new Edge(rNode, i, matrix[rNode][i]));
					}
				}
		
				prioQ.minHeap();
				counter++;
			}
		}
		Long endTime = System.currentTimeMillis();
		Long finalTime = endTime - startTime;
		System.out.println("\n===================================");
		System.out.println("PRIM WITH ADJACENCY MATRIX\n");
		if(n <= 9){
		for(int i = 1; i < n; i++)
		{
			if(nodeArray[i] != null)
				System.out.println(toString(nodeArray[i]));
			else
				System.out.println("Null... something went wrong D:");
		}}
		System.out.println("Total weight of MST using Prim: " + weight);
		System.out.println("Runtime: " + finalTime + " milliseconds");
	}
	
	public static void primAdj(Edge[][] adjList, int n)
	{
		int weight = 0;
		Long startTime = System.currentTimeMillis();
		MinHeap prioQ = new MinHeap(n);
		boolean visited[] = new boolean[n]; 
		Edge nodeArray[] = new Edge[n];
		visited[0] = true; // mark node 0 as visited
		int counter = 1;
		
		// First, to add the edges attached to 0 as a starting point
		for(int i = 0; i < adjList[0].length; i++)
		{
			prioQ.insert(adjList[0][i]);
		}
		while(counter < n) // Where the magic of Prim is located
		{
			Edge edge = prioQ.remove(); // Remove the top
			int rNode = edge.rightNode;
			if(visited[rNode] == false)
			{
				visited[rNode] = true;
				weight += edge.weight;
				nodeArray[rNode] = edge; // Add it to nodeArray to finalize
				for(int i = 0; i < adjList[rNode].length; i++) // Now to add more edges to the heap
				{
					if(visited[adjList[rNode][i].rightNode] == false) 
					{
						prioQ.insert(adjList[rNode][i]);
					}
				}
		
				prioQ.minHeap();
				counter++;
			}
		}
		Long endTime = System.currentTimeMillis();
		Long finalTime = endTime - startTime;
		System.out.println("\n===================================");
		System.out.println("PRIM WITH ADJACENCY LIST\n");
		if(n <= 9){
		for(int i = 1; i < n; i++)
		{
			if(nodeArray[i] != null)
				System.out.println(toString(nodeArray[i]));
			else
				System.out.println("Null... something went wrong D:");
		}}
		System.out.println("Total weight of MST using Prim: " + weight);
		System.out.print("Runtime: " + finalTime + " milliseconds");
	}
	
	public static void matrixEdgeSort(int[][] matrix, int n)
	{
		long startTime = System.currentTimeMillis();
		// First, to generate the edgeList we will be using for all 3 sorts
		ArrayList<Edge> edgeList = new ArrayList<Edge>();
		int count = 0;
		for(int i = 0; i < (n - 1); i++) // Start filling in edgeList
		{
			for(int x = (1 + count); x < n; x++)
			{
				if(matrix[i][x] != 0)
				{
					Edge edge = new Edge(i, x, matrix[i][x]);
					edgeList.add(edge);
				}
			}
			count++;
		}
		long endTime = System.currentTimeMillis();
		long creationTime = endTime - startTime;
		
		// Time to call our sort functions!
		System.out.println("===================================");
		System.out.println("KRUSKAL WITH MATRIX USING INSERTION SORT");
		insertionSort(edgeList, creationTime, n);
		
		System.out.println("===================================");
		System.out.println("KRUSKAL WITH MATRIX USING COUNT SORT");
		countSort(edgeList, creationTime, n);
		
		System.out.println("===================================");
		System.out.println("KRUSKAL WITH MATRIX USING QUICKSORT");
		startTime = System.currentTimeMillis();
		quickSort(edgeList, 0, edgeList.size() - 1);
		System.out.println("\nTotal weight of MST using Kruskal: " + kruskal(edgeList, n));
		endTime = System.currentTimeMillis();
		System.out.println("Runtime: " + (creationTime + endTime - startTime) + " milliseconds\n");
	}
	
	public static void adjListEdgeSort(Edge[][] adjList, int n)
	{
		long startTime = System.currentTimeMillis();
		// First, to generate the edgeList we will be using for all 3 sorts
		ArrayList<Edge> edgeList = new ArrayList<Edge>();
		for(int i = 0; i < (n - 1); i++) // Start filling in edgeList
		{
			for(int x = 0; x < adjList[i].length; x++)
			{
				if(adjList[i][x].leftNode < adjList[i][x].rightNode)
				{
					edgeList.add(adjList[i][x]);
				}
			}
		}
		long endTime = System.currentTimeMillis();
		long creationTime = endTime - startTime;
		
		// Time to call our sort functions!
		System.out.println("===================================");
		System.out.println("KRUSKAL WITH LIST USING INSERTION SORT");
		insertionSort(edgeList, creationTime, n);
		
		System.out.println("===================================");
		System.out.println("KRUSKAL WITH LIST USING COUNT SORT");
		countSort(edgeList, creationTime, n);
		
		System.out.println("===================================");
		System.out.println("KRUSKAL WITH LIST USING QUICKSORT");
		startTime = System.currentTimeMillis();
		quickSort(edgeList, 0, edgeList.size() - 1);
		System.out.println("\nTotal weight of MST using Kruskal: " + kruskal(edgeList, n));
		endTime = System.currentTimeMillis();
		System.out.println("Runtime: " + (creationTime + endTime - startTime) + " milliseconds");
	}
	
	public static String toString(Edge edge)
	{
		return edge.leftNode + " " + edge.rightNode + " weight = " + edge.weight;
	}

	// Good ol' terrible insertion sort.  Pretty self explanatory
	public static void insertionSort(ArrayList<Edge> edgeList, long time, int n)
	{
		long startTime = System.currentTimeMillis();
		// First, we need to create a copy of the given list to prevent any changes to the original
		ArrayList<Edge> list = new ArrayList<Edge>();
		list.addAll(edgeList);
		
		Edge temp;
		int i, j;
		for(i = 1; i < list.size(); i++)
		{
			temp = list.get(i);
			j = i;
			while(j > 0 && list.get(j - 1).weight > temp.weight)
			{
				list.set(j, list.get(j - 1));
				j--;
			}
			list.set(j, temp);
		}
		
		System.out.println("\nTotal weight of MST using Kruskal: " + kruskal(list, n));
		long endTime = System.currentTimeMillis();
		System.out.println("Runtime: " + (endTime - startTime + time) + " milliseconds\n");
	}
	
	// Count sort is a sorting technique based on keys between a specific range. 
	// It works by counting the number of objects having distinct key values. 
	// Then it does some arithmetic to calculate the position of each object in the output sequence.
	public static void countSort(ArrayList<Edge> edgeList, long time, int z)
	{
		long startTime = System.currentTimeMillis();
		//int totalWeight = 0;
		ArrayList<Edge> list = new ArrayList<Edge>();
		list.addAll(edgeList);
		
		int n = list.size();
		if (n == 0)
			return;
		
		// Find the  min and max values
		int max = list.get(0).weight;
		int min = list.get(0).weight;
		for(int i = 1; i < n; i++)
		{
			if(list.get(i).weight > max)
				max = list.get(i).weight;
			if(list.get(i).weight < min)
				min = list.get(i).weight;
		}
		int range = max - min + 1;
		
		int[] count = new int[range];
		for (int i = 0; i < n; i++)
			count[list.get(i).weight - min]++;
		for (int i = 1; i < range; i++)
			count[i] += count[i - 1];
		int j = 0;
		int[] sortedWeights = new int[list.size()];
		for(int i = 0; i < range; i++)
			while(j < count[i])
				sortedWeights[j++] = i + min;
		long endTime = System.currentTimeMillis();
		
		// We have a sorted list of weights, now to make the edges line up
		ArrayList<Edge> list2 = new ArrayList<Edge>();
		for(int i = 0; i < sortedWeights.length; i++)
		{
			boolean check = false;
			int x = 0;
			while(check == false)
			{
				if(sortedWeights[i] == list.get(x).weight)
				{
					list2.add(list.get(x));
					list.get(x).weight = 0;
					check = true;
				}
				else
					x++;
			}
		}
		
		// Now to print and some some other necessary changes at the same time)
		for(int i = 0; i < list.size(); i++) // no point in making 2 loops
		{
			list2.get(i).weight = sortedWeights[i];
		}
		long time1 = System.currentTimeMillis();
		System.out.println("\nTotal weight of MST using Kruskal: " + kruskal(list2, z));
		long time2 = System.currentTimeMillis();
		System.out.println("Runtime: " + ((endTime - startTime + time) + (time2 - time1)) + " milliseconds\n");
	}
	
	// Pretty basic quicksort implementation.  
	public static void quickSort(ArrayList<Edge> list, int start, int end)
	{
		int i = start;
		int k = end;
		
		if(end - start >= 1)
		{
			int pivot = list.get(start).weight;
			while(k > i)
			{
				while(list.get(i).weight <= pivot && i <= end && k > i)
					i++;
				while(list.get(k).weight > pivot && k >= start && k >= i)
					k--;
				if(k > i)
						swap(list, i, k);

			}
			//if (lessThan(list, i, k) == true)
			swap(list, start, k);
			
			quickSort(list, start, k - 1);
			quickSort(list, k + 1, end);
		}
		else
		{
			return;
		}
	}
	
	// Swap function only used in quicksort, since it is called multiple times in 
	// one recursive call
	public static void swap(ArrayList<Edge> list, int index1, int index2)
	{
		Edge temp = list.get(index1);
		list.set(index1, list.get(index2));
		list.set(index2, temp);
	}
	
	// Basic comparator for two edges
	public static boolean lessThan(ArrayList<Edge> list, int index1, int index2)
	{
		if(list.get(index1).weight <= list.get(index2).weight)
			if(list.get(index1).leftNode <= list.get(index2).leftNode)
				if(list.get(index1).rightNode <= list.get(index2).rightNode)
					return true;
				else	
					return false;
			else
					return false;
		else
			return false;
	}
}