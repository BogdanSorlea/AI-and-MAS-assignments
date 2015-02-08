package sc;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public abstract class Heuristic implements Comparator< Node > {

	public Node initialState;
	public Heuristic(Node initialState) {
		this.initialState = initialState;
	}

	public int compare( Node n1, Node n2 ) {
		return f( n1 ) - f( n2 );
	}
	
	private int maxManhattanHeuristic(Node n) {
		HashMap<Character, ArrayList<Integer>> goals = new HashMap<Character, ArrayList<Integer>>();
		HashMap<Character, ArrayList<Integer>> boxes = new HashMap<Character, ArrayList<Integer>>();
		
		for (int i=0; i<NodeStatics.goals.size(); i++) {
			for (int j=0; j<NodeStatics.goals.get(i).size(); j++) {
				Character itemName = NodeStatics.goals.get(i).get(j);
				if ( 'a' <= itemName && itemName <= 'z' ) {
					ArrayList<Integer> coordinates = new ArrayList<Integer>();
					coordinates.add(i);
					coordinates.add(j);
					goals.put(Character.toUpperCase(itemName), coordinates);
				}
				
				itemName = n.boxes.get(i).get(j);
				if ( 'A' <= itemName && itemName <= 'Z' ) {
					ArrayList<Integer> coordinates = new ArrayList<Integer>();
					coordinates.add(i);
					coordinates.add(j);
					boxes.put(Character.toUpperCase(itemName), coordinates);
				}
			}
		}
		
		int maxH = 0;
		for (Character itemName : goals.keySet()) {
			int newH = Math.abs(n.agentRow - goals.get(itemName).get(0)) 
						+ Math.abs(n.agentCol - goals.get(itemName).get(1)) - 1
						+ Math.abs(goals.get(itemName).get(0) - boxes.get(itemName).get(0))
						+ Math.abs(goals.get(itemName).get(1) - boxes.get(itemName).get(1));
			if ( newH > maxH ) {
				maxH = newH;
			}
		}
		
		return maxH;
	}
	
	private int sumManhattanHeuristic(Node n) {
		HashMap<Character, ArrayList<Integer>> goals = new HashMap<Character, ArrayList<Integer>>();
		HashMap<Character, ArrayList<Integer>> boxes = new HashMap<Character, ArrayList<Integer>>();
		
		for (int i=0; i<NodeStatics.goals.size(); i++) {
			for (int j=0; j<NodeStatics.goals.get(i).size(); j++) {
				Character itemName = NodeStatics.goals.get(i).get(j);
				if ( 'a' <= itemName && itemName <= 'z' ) {
					ArrayList<Integer> coordinates = new ArrayList<Integer>();
					coordinates.add(i);
					coordinates.add(j);
					goals.put(Character.toUpperCase(itemName), coordinates);
				}
				
				itemName = n.boxes.get(i).get(j);
				if ( 'A' <= itemName && itemName <= 'Z' ) {
					ArrayList<Integer> coordinates = new ArrayList<Integer>();
					coordinates.add(i);
					coordinates.add(j);
					boxes.put(Character.toUpperCase(itemName), coordinates);
				}
			}
		}
		
		int sumH = 0;
		for (Character itemName : goals.keySet()) {
			int newH = Math.abs(n.agentRow - goals.get(itemName).get(0)) 
						+ Math.abs(n.agentCol - goals.get(itemName).get(1)) - 1
						+ Math.abs(goals.get(itemName).get(0) - boxes.get(itemName).get(0))
						+ Math.abs(goals.get(itemName).get(1) - boxes.get(itemName).get(1));
			sumH += newH;
		}
		
		return sumH;
	}

	public int h( Node n ) {
		return maxManhattanHeuristic(n);
		//return sumManhattanHeuristic(n);
	}

	public abstract int f( Node n );

	public static class AStar extends Heuristic {
		public AStar(Node initialState) {
			super( initialState );
		}

		public int f( Node n ) {
			return n.g() + h( n );
		}

		public String toString() {
			return "A* evaluation";
		}
	}

	public static class WeightedAStar extends Heuristic {
		private int W;

		public WeightedAStar(Node initialState) {
			super( initialState );
			W = 5; // You're welcome to test this out with different values, but for the reporting part you must at least indicate benchmarks for W = 5
		}

		public int f( Node n ) {
			return n.g() + W * h( n );
		}

		public String toString() {
			return String.format( "WA*(%d) evaluation", W );
		}
	}

	public static class Greedy extends Heuristic {

		public Greedy(Node initialState) {
			super( initialState );
		}

		public int f( Node n ) {
			return h( n );
		}

		public String toString() {
			return "Greedy evaluation";
		}
	}
}
