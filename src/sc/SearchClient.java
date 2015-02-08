package sc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import sc.Heuristic.*;
import sc.Strategy.*;

public class SearchClient {
	
	// Auxiliary static classes
	public static void error( String msg ) throws Exception {
		throw new Exception( "GSCError: " + msg );
	}

	public static class Memory {
		public static Runtime runtime = Runtime.getRuntime();
		public static final float mb = 1024 * 1024;
		public static final float limitRatio = .9f;
		public static final int timeLimit = 180;

		public static float used() {
			return ( runtime.totalMemory() - runtime.freeMemory() ) / mb;
		}

		public static float free() {
			return runtime.freeMemory() / mb;
		}

		public static float total() {
			return runtime.totalMemory() / mb;
		}

		public static float max() {
			return runtime.maxMemory() / mb;
		}

		public static boolean shouldEnd() {
			return ( used() / max() > limitRatio );
		}

		public static String stringRep() {
			return String.format( "[Used: %.2f MB, Free: %.2f MB, Alloc: %.2f MB, MaxAlloc: %.2f MB]", used(), free(), total(), max() );
		}
	}

	public Node initialState = null;

	public SearchClient( BufferedReader serverMessages ) throws Exception {
		Map< Character, String > colors = new HashMap< Character, String >();
		String line, color;

		int agentCol = -1, agentRow = -1;
		int colorLines = 0, levelLines = 0;

		// Read lines specifying colors
		while ( ( line = serverMessages.readLine() ).matches( "^[a-z]+:\\s*[0-9A-Z](,\\s*[0-9A-Z])*\\s*$" ) ) {
			line = line.replaceAll( "\\s", "" );
			String[] colonSplit = line.split( ":" );
			color = colonSplit[0].trim();

			for ( String id : colonSplit[1].split( "," ) ) {
				colors.put( id.trim().charAt( 0 ), color );
			}
			colorLines++;
		}
		
		if ( colorLines > 0 ) {
			error( "Box colors not supported" );
		}
		
		initialState = new Node( null );
		
		//int max_columns = 0;
		
		while ( !line.equals( "" ) ) {
			/*
			if (line.length() > max_columns) {
				max_columns = line.length();
			}
			*/
			ArrayList<Boolean> wallsLine = new ArrayList<Boolean>();
			ArrayList<Character> goalsLine = new ArrayList<Character>();
			ArrayList<Character> boxesLine = new ArrayList<Character>();
			
			for ( int i = 0; i < line.length(); i++ ) {
				char chr = line.charAt( i );
				boolean wallItem = false;
				char goalItem = (char) 0;
				char boxItem = (char) 0;
				
				if ( '+' == chr ) { // Walls
					wallItem = true;
					//initialState.statics.walls[levelLines][i] = true;
				} else if ( '0' <= chr && chr <= '9' ) { // Agents
					if ( agentCol != -1 || agentRow != -1 ) {
						error( "Not a single agent level" );
					}
					initialState.agentRow = levelLines;
					initialState.agentCol = i;
				} else if ( 'A' <= chr && chr <= 'Z' ) { // Boxes
					boxItem = chr;
					//initialState.boxes[levelLines][i] = chr;
				} else if ( 'a' <= chr && chr <= 'z' ) { // Goal cells
					goalItem = chr;
					//initialState.statics.goals[levelLines][i] = chr;
				}
				
				wallsLine.add(wallItem);
				goalsLine.add(goalItem);
				boxesLine.add(boxItem);
			}
			
			NodeStatics.walls.add(wallsLine);
			NodeStatics.goals.add(goalsLine);
			initialState.boxes.add(boxesLine);
			
			line = serverMessages.readLine();
			levelLines++;
		}
		/*
		for (int i=0; i<levelLines; i++) {
			for (int j=0; j<max_columns - NodeStatics.walls.get(i).size(); j++) {
				NodeStatics.walls.get(i).add(false);
			}
			
			for (int j=0; j<max_columns - NodeStatics.goals.get(i).size(); j++) {
				NodeStatics.goals.get(i).add((char) 0);
			}
			
			for (int j=0; j<max_columns - initialState.boxes.get(i).size(); j++) {
				initialState.boxes.get(i).add((char) 0);
			}
		}
		*/
	}

	public LinkedList< Node > Search( Strategy strategy ) throws IOException {
		System.err.format( "Search starting with strategy %s\n", strategy );
		strategy.addToFrontier( this.initialState );

		int iterations = 0;
		while ( true ) {
			if ( iterations % 200 == 0 ) {
				System.err.println( strategy.searchStatus() );
			}
			if ( Memory.shouldEnd() ) {
				System.err.format( "Memory limit almost reached, terminating search %s\n", Memory.stringRep() );
				return null;
			}
			if ( strategy.timeSpent() > 300 ) { // Minutes timeout
				System.err.format( "Time limit reached, terminating search %s\n", Memory.stringRep() );
				return null;
			}

			if ( strategy.frontierIsEmpty() ) {
				return null;
			}

			Node leafNode = strategy.getAndRemoveLeaf();

			if ( leafNode.isGoalState() ) {
				return leafNode.extractPlan();
			}

			strategy.addToExplored( leafNode );
			for ( Node n : leafNode.getExpandedNodes() ) {
				if ( !strategy.isExplored( n ) && !strategy.inFrontier( n ) ) {
					strategy.addToFrontier( n );
				}
			}
			iterations++;
		}
	}

	public static void main( String[] args ) throws Exception {
		BufferedReader serverMessages = new BufferedReader( new InputStreamReader( System.in ) );
		
		// Use stderr to print to console
		System.err.println( "SearchClient initializing. I am sending this using the error output stream." );

		// Read level and create the initial state of the problem
		SearchClient client = new SearchClient( serverMessages );

		Strategy strategy = null;
		strategy = new StrategyBFS();
		// Ex 1:
		//strategy = new StrategyDFS();
		
		// Ex 3:
		//strategy = new StrategyBestFirst( new AStar( client.initialState ) );
		//strategy = new StrategyBestFirst( new WeightedAStar( client.initialState ) );
		//strategy = new StrategyBestFirst( new Greedy( client.initialState ) );

		LinkedList< Node > solution = client.Search( strategy );

		if ( solution == null ) {
			System.err.println( "Unable to solve level" );
			System.exit( 0 );
		} else {
			System.err.println( "\nSummary for " + strategy );
			System.err.println( "Found solution of length " + solution.size() );
			System.err.println( strategy.searchStatus() );

			for ( Node n : solution ) {
				String act = n.action.toActionString();
				System.out.println( act );
				String response = serverMessages.readLine();
				if ( response.contains( "false" ) ) {
					System.err.format( "Server responsed with %s to the inapplicable action: %s\n", response, act );
					System.err.format( "%s was attempted in \n%s\n", act, n );
					break;
				}
			}
		}
	}
}
