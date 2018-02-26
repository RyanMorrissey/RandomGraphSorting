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

// 	A MinHeap will be used in the priority queue in Edge.java
class MinHeap
{
	private Edge[] Heap;
    private int size;
    private int maxsize;
 
    private static final int FRONT = 1;
 
    public MinHeap(int maxsize)
    {
        this.maxsize = maxsize;
        this.size = 0;
        Heap = new Edge[(maxsize * maxsize) + 4];
        Heap[0] = new Edge(Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE);
    }
 
    private int parent(int pos)
    {
        return pos / 2;
    }
 
    private int leftChild(int pos)
    {
        return (2 * pos);
    }
 
    private int rightChild(int pos)
    {
        return (2 * pos) + 1;
    }
 
    private boolean isLeaf(int pos)
    {
        if (pos >=  (size / 2)  &&  pos <= size)
        { 
            return true;
        }
        return false;
    }
 
    private void swap(int fpos, int spos)
    {
        Edge tmp;
        tmp = Heap[fpos];
        Heap[fpos] = Heap[spos];
        Heap[spos] = tmp;
    }
 
    private void minHeapify(int pos)
    {
        if (!isLeaf(pos))
        { 
            if ( Heap[pos].weight > Heap[leftChild(pos)].weight  || Heap[pos].weight > Heap[rightChild(pos)].weight)
            {
                if (Heap[leftChild(pos)].weight < Heap[rightChild(pos)].weight)
                {
                    swap(pos, leftChild(pos));
                    minHeapify(leftChild(pos));
                }else
                {
                    swap(pos, rightChild(pos));
                    minHeapify(rightChild(pos));
                }
            }
        }
    }
 
    public void insert(Edge element)
    {
        Heap[++size] = element;
        int current = size;
 
        while (Heap[current].weight < Heap[parent(current)].weight)
        {
            swap(current,parent(current));
            current = parent(current);
        }	
    }
 
    public void print()
    {
        for (int i = 1; i < size + 1; i++ )
        {
            System.out.println(Edge.toString(Heap[i]));
        } 
    }
 
    public void minHeap()
    {
        for (int pos = (size / 2); pos >= 1 ; pos--)
        {
            minHeapify(pos);
        }
    }
 
    public Edge remove()
    {
        Edge popped = Heap[FRONT];
        Heap[FRONT] = Heap[size--]; 
        minHeapify(FRONT);
        return popped;
    }
}