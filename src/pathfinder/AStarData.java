package pathfinder;

class AStarData {
	public Cell newCell;
	public Cell[][] cellDetails;
	public boolean destFound;
	public boolean addToSet;
	public AStarData(Cell newCell, Cell[][] cellDetails) {
		this.newCell = newCell;
		this.cellDetails = cellDetails;
	}
}
