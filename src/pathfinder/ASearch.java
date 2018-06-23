package pathfinder;
/*
 * Author and Maintainer : SATYAJIT
 * A* Algorithm Implementation in JAVA
 */

import java.util.Stack;
import java.util.TreeSet;

import engine.EntitiesMap;

@SuppressWarnings("unused")
public class ASearch {
	// to check if the destination has been reached or not
	// return a boolean true or false
	public static boolean isDestination(int row, int col, Pair <Integer, Integer> dest) {
		if (row == dest.first && col == dest.second) return true;
		else return false;
	}
	
	// function to calculate 'h' heuristics.
	public static double calculateHValue(int row, int column, Pair <Integer, Integer> dest) {
		return (double) (Math.sqrt((row - dest.first) * (row - dest.second)
									+ (column - dest.second) * (column - dest.second) ) );
	}
	
	public static Stack <Pair <Integer, Integer>> path = new Stack <Pair <Integer, Integer>>();
	
	// To trace the path obtained
	public static void tracePath(Cell cellDetails[][], Pair <Integer, Integer> dest) {
		int row = dest.first;
		int column = dest.second;

		//Stack <Pair <Integer, Integer>> path = new Stack <Pair <Integer, Integer>>();
		path = new Stack <Pair <Integer, Integer>>();
		while ( !(cellDetails[row][column].parent_i == row
					&& cellDetails[row][column].parent_j == column) ) {
			path.push(new Pair <Integer, Integer>(row, column) );
			int temp_row = cellDetails[row][column].parent_i;
			int temp_column = cellDetails[row][column].parent_j;
			row = temp_row;
			column = temp_column;
		}
		path.push(new Pair <Integer, Integer>(row, column));

		return;
	}

	// get the cell details
	// takes the source and destination as a Pair, and returns a stack that contains the path from source to destination
	public static Stack < Pair <Integer, Integer>> getPath(Pair <Integer, Integer> src, Pair <Integer, Integer> dest) {
		AStarSearch(src, dest);
		return path;
	}
	
	//The A-Star Search Algorithm

	//public static void AStarSearch(boolean grid[][], Pair <Integer, Integer> src, Pair <Integer, Integer> dest) {
	public static void AStarSearch(Pair <Integer, Integer> src, Pair <Integer, Integer> dest) {	
		Pair <Integer, Integer> size = EntitiesMap.getRowsAndCols();
//		System.out.println("size x: "+size.first+" size y: "+size.second);

		if (EntitiesMap.validateCell(src.first, src.second) == false) {
//			System.out.println("Source is invalid");
			return;
		}

		if (EntitiesMap.validateCell(dest.first, dest.second) == false) {
//			System.out.println("Destination is invalid");
			return;
		}
		
		if (isDestination(src.first, src.second, dest) == true) {
//			System.out.println("We are already at the destination");
			return;
		}

		if (EntitiesMap.cellIsUnblocked(src.first, src.second) == false ||
			EntitiesMap.cellIsUnblocked(dest.first, dest.second) == false) {
			//System.out.println("Source or the Destination is blocked");
			return;
		}
		

		boolean[][] closedList = new boolean[size.first][size.second];
		Cell[][] cellDetails = new Cell[size.first][size.second];
		int i, j;
		for (i = 0 ; i < cellDetails.length ; i++) {
			for (j = 0 ; j < cellDetails[0].length ; j++) {
				cellDetails[i][j] = new Cell(-1, -1, Float.MAX_VALUE, Float.MAX_VALUE, Float.MAX_VALUE);
			}
		}
		i = src.first; j = src.second;
		cellDetails[i][j].setValue(i, j, 0.0, 0.0, 0.0);

		TreeSet <Pair <Double, Pair <Integer, Integer>>> openList = new TreeSet <Pair <Double, Pair <Integer, Integer>>>();
		openList.add(new Pair<Double, Pair<Integer, Integer>>(0.0, new Pair<Integer, Integer>(i, j)));
		boolean foundDest = false;
		while(! openList.isEmpty()) {
		Pair < Double, Pair <Integer, Integer>> p = openList.first();
		openList.remove(openList.first());

		i = p.second.first; j = p.second.second;
		closedList[i][j] = true;

		/*	Generating all the 8 successor of this cell
			N.W		N	  N.E
			  \		|	  /
			   \	|	 /
			 W----Cell----E
			 	/	|	\
			   /	|	 \
			S.W		S	 S.E

			Cell 	--> Popped Cell	(i, j)
			N 		--> North		(i-1, j)
			S		--> South		(i+1, j)
			E		--> East		(i, j+1)
			W		--> West		(i, j-1)
			N.E		--> North-East	(i-1, j+1)
			N.W		--> North-West	(i-1, j-1)
			S.E		--> South-East	(i+1, j+1)
			S.W		--> South-West	(i+1, j-1)
		*/

		AStarData successor;

		// 1st Successor - North
		successor = successorProcess(new AStarData(new Cell(i-1, j, 0.0, 0.0, 0.0), cellDetails), dest, closedList, i, j, false);
		if (successor.destFound) {
//			System.out.println("The Destination Cell is found");
			tracePath(successor.cellDetails, dest);
			foundDest = true;
			return;
		}

		if (successor.addToSet) {
			cellDetails[i-1][j] = successor.newCell;
			openList.add(new Pair<Double, Pair<Integer, Integer>>(successor.newCell.f, new Pair<Integer, Integer>(i-1, j)));
		}

		// 2nd Successor - South
		successor = successorProcess(new AStarData(new Cell(i+1, j, 0.0, 0.0, 0.0), cellDetails), dest, closedList, i, j, false);
		if (successor.destFound) {
//			System.out.println("The Destination Cell is found");
			tracePath(successor.cellDetails, dest);
			foundDest = true;
			return;
		}

		if (successor.addToSet) {
			cellDetails[i+1][j] = successor.newCell;
			openList.add(new Pair<Double, Pair<Integer, Integer>>(successor.newCell.f, new Pair<Integer, Integer>(i+1, j)));
		}

		// 3rd Successor - East
		successor = successorProcess(new AStarData(new Cell(i, j+1, 0.0, 0.0, 0.0), cellDetails), dest, closedList, i, j, false);
		if (successor.destFound) {
//			System.out.println("The Destination Cell is found");
			tracePath(successor.cellDetails, dest);
			foundDest = true;
			return;
		}

		if (successor.addToSet) {
			cellDetails[i][j+1] = successor.newCell;
			openList.add(new Pair<Double, Pair<Integer, Integer>>(successor.newCell.f, new Pair<Integer, Integer>(i, j+1)));
		}

		// 4th Successor - West
		successor = successorProcess(new AStarData(new Cell(i, j-1, 0.0, 0.0, 0.0), cellDetails), dest, closedList, i, j, false);
		if (successor.destFound) {
//			System.out.println("The Destination Cell is found");
			tracePath(successor.cellDetails, dest);
			foundDest = true;
			return;
		}

		if (successor.addToSet) {
			cellDetails[i][j-1] = successor.newCell;
			openList.add(new Pair<Double, Pair<Integer, Integer>>(successor.newCell.f, new Pair<Integer, Integer>(i, j-1)));
		}

		// 5th Successor - North-East
		successor = successorProcess(new AStarData(new Cell(i-1, j+1, 0.0, 0.0, 0.0), cellDetails), dest, closedList, i, j, true);
		if (successor.destFound) {
//			System.out.println("The Destination Cell is found");
			tracePath(successor.cellDetails, dest);
			foundDest = true;
			return;
		}

		if (successor.addToSet) {
			cellDetails[i-1][j+1] = successor.newCell;
			openList.add(new Pair<Double, Pair<Integer, Integer>>(successor.newCell.f, new Pair<Integer, Integer>(i-1, j+1)));
		}

		// 6th Successor - North-West
		successor = successorProcess(new AStarData(new Cell(i-1, j-1, 0.0, 0.0, 0.0), cellDetails), dest, closedList, i, j, true);
		if (successor.destFound) {
//			System.out.println("The Destination Cell is found");
			tracePath(successor.cellDetails, dest);
			foundDest = true;
			return;
		}

		if (successor.addToSet) {
			cellDetails[i-1][j-1] = successor.newCell;
			openList.add(new Pair<Double, Pair<Integer, Integer>>(successor.newCell.f, new Pair<Integer, Integer>(i-1, j-1)));
		}

		// 7th Successor - South-East
		successor = successorProcess(new AStarData(new Cell(i+1, j+1, 0.0, 0.0, 0.0), cellDetails), dest, closedList, i, j, true);
		if (successor.destFound) {
//			System.out.println("The Destination Cell is found");
			tracePath(successor.cellDetails, dest);
			foundDest = true;
			return;
		}

		if (successor.addToSet) {
			cellDetails[i+1][j+1] = successor.newCell;
			openList.add(new Pair<Double, Pair<Integer, Integer>>(successor.newCell.f, new Pair<Integer, Integer>(i+1, j+1)));
		}

		// 8th Successor - South-West
		successor = successorProcess(new AStarData(new Cell(i+1, j-1, 0.0, 0.0, 0.0), cellDetails), dest, closedList, i, j, true);
		if (successor.destFound) {
//			System.out.println("The Destination Cell is found");
			tracePath(successor.cellDetails, dest);
			foundDest = true;
			return;
		}

		if (successor.addToSet) {
			cellDetails[i+1][j-1] = successor.newCell;
			openList.add(new Pair<Double, Pair<Integer, Integer>>(successor.newCell.f, new Pair<Integer, Integer>(i+1, j-1)));
		}

	} // end of the while the set is not empty loop
		
	} // end of a-start function
	
	public static AStarData successorProcess(AStarData currSuccessor, Pair <Integer, Integer> dest, boolean[][] closedList, int parent_i, int parent_j, boolean isDiagonal) {
		int i = currSuccessor.newCell.parent_i;
		int j = currSuccessor.newCell.parent_j;
		Cell[][] cellDetails = currSuccessor.cellDetails;
		if (EntitiesMap.validateCell(i, j) == true) {
			if (isDestination(i, j, dest)) {
				cellDetails[i][j].parent_i = parent_i;
				cellDetails[i][j].parent_j = parent_j;
				currSuccessor.cellDetails = cellDetails;
				currSuccessor.destFound = true;
				return currSuccessor;
			}

			else if(closedList[i][j] == false && EntitiesMap.cellIsUnblocked(i, j) == true) {
				currSuccessor.newCell.g = cellDetails[parent_i][parent_j].g;
				if (isDiagonal) currSuccessor.newCell.g += 1.414;
				else currSuccessor.newCell.g += 1.0;
				currSuccessor.newCell.h = calculateHValue(i, j , dest);
				currSuccessor.newCell.f = currSuccessor.newCell.g + currSuccessor.newCell.h;
	
				if (cellDetails[i][j].f == Float.MAX_VALUE || cellDetails[i][j].f > currSuccessor.newCell.f) {
					currSuccessor.newCell.parent_i = parent_i;
					currSuccessor.newCell.parent_j = parent_j;
					currSuccessor.addToSet = true;
					return currSuccessor;
				}
			}
		}
		return currSuccessor;
	}
}
