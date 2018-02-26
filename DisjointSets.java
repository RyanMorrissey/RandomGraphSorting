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
 	For use in Edge.java
*/

import java.util.*;

// 	Disjoint set which will be used in Kruskal implementation
class DisjointSets
{
	private int[] parent;
	public DisjointSets(int size)
	{
		parent = new int[size];
		for(int i = 0; i < size; i++)
		{
			parent[i] = i;
		}
	}
	
	public int find(int x)
	{
		if(parent[x] == x)
			return x;
		parent[x] = find(parent[x]);
		return parent[x];
	}
	
	public void union(int x, int y)
	{
		parent[find(x)] = find(y);
	}
}