package sc;

import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Map;
import java.util.Stack;
import java.util.TreeMap;

import sc.SearchClient.Memory;

public abstract class Strategy {

	public HashSet< Node > explored;
	public long startTime = System.currentTimeMillis();
	
	public Strategy() {
		explored = new HashSet< Node >();
	}

	public void addToExplored( Node n ) {
		explored.add( n );
	}

	public boolean isExplored( Node n ) {
		return explored.contains( n );
	}

	public int countExplored() {
		return explored.size();
	}

	public String searchStatus() {
		return String.format( "#Explored: %4d, #Frontier: %3d, Time: %3.2f s \t%s", countExplored(), countFrontier(), timeSpent(), Memory.stringRep() );
	}
	
	public float timeSpent() {
		return ( System.currentTimeMillis() - startTime ) / 1000f;
	}

	public abstract Node getAndRemoveLeaf();

	public abstract void addToFrontier( Node n );

	public abstract boolean inFrontier( Node n );

	public abstract int countFrontier();

	public abstract boolean frontierIsEmpty();
	
	public abstract String toString();

	public static class StrategyBFS extends Strategy {

		private ArrayDeque< Node > frontier;

		public StrategyBFS() {
			super();
			frontier = new ArrayDeque< Node >();
		}

		public Node getAndRemoveLeaf() {
			return frontier.pollFirst();
		}

		public void addToFrontier( Node n ) {
			frontier.addLast( n );
		}

		public int countFrontier() {
			return frontier.size();
		}

		public boolean frontierIsEmpty() {
			return frontier.isEmpty();
		}

		public boolean inFrontier( Node n ) {
			return frontier.contains( n );
		}

		public String toString() {
			return "Breadth-first Search";
		}
	}

	public static class StrategyDFS extends Strategy {
		
		//private ArrayList<Node> frontier;
		private Stack<Node> frontier;
		
		public StrategyDFS() {
			super();
			//frontier = new ArrayList<Node>();		
			frontier = new Stack<Node>();
		}

		public Node getAndRemoveLeaf() {
			Node leaf = null;
			/*
			if(!frontier.isEmpty()){
				leaf = frontier.get(frontier.size()-1);
				frontier.remove(frontier.size()-1);
			}
			*/
			leaf = frontier.size() > 0 ? frontier.pop() : null;
			return leaf;
		}

		public void addToFrontier( Node n ) {
			//frontier.add(n);
			frontier.push(n);
			return;
		}

		public int countFrontier() {
			return frontier.size();
		}

		public boolean frontierIsEmpty() {
			return frontier.isEmpty();
		}

		public boolean inFrontier( Node n ) {
			return frontier.contains(n);
		}

		public String toString() {
			return "Depth-first Search";
		}
	}

	// Ex 3: Best-first Search uses a priority queue (Java contains no implementation of a Heap data structure)
	public static class StrategyBestFirst extends Strategy {
		private Heuristic heuristic;
		private TreeMap<Integer, ArrayDeque<Node>> frontier;
		public StrategyBestFirst( Heuristic h ) {
			super();
			heuristic = h;
			frontier = new TreeMap<Integer, ArrayDeque<Node>>();
		}
		public Node getAndRemoveLeaf() {
			Node leaf = null;
			leaf = frontier.firstEntry().getValue().pollFirst();
			if ( frontier.firstEntry().getValue().size() == 0 ) {
				frontier.remove(frontier.firstKey());
			}
			return leaf;
		}

		public void addToFrontier( Node n ) {
			int key = heuristic.f(n);
			if ( frontier.get(key) == null ) {
				frontier.put(key, new ArrayDeque<Node>());
			}
			frontier.get(key).add(n);
		}

		public int countFrontier() {
			int count = 0;
			for (Map.Entry<Integer, ArrayDeque<Node>> nodeList : frontier.entrySet()) {
				count += nodeList.getValue().size();
			}
			return count;
		}

		public boolean frontierIsEmpty() {
			boolean isEmpty = true;
			for (Map.Entry<Integer, ArrayDeque<Node>> nodeList : frontier.entrySet()) {
				if ( nodeList.getValue() != null ) {
					isEmpty = false;
				}
			}
			return isEmpty;
		}

		public boolean inFrontier( Node n ) {
			boolean inFrontier = false;
			for (Map.Entry<Integer, ArrayDeque<Node>> nodeList : frontier.entrySet()) {
				if ( nodeList.getValue().contains(n) ) {
					inFrontier = true;
				}
			}
			return inFrontier;
		}

		public String toString() {
			return "Best-first Search (PriorityQueue) using " + heuristic.toString();
		}
	}
}
