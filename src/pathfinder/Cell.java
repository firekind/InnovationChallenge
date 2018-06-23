package pathfinder;

class Cell {
	public int parent_i, parent_j;
	public double f, g, h;

	public void setValue(int i, int j, double f, double g, double h) {
		this.parent_i = i;
		this.parent_j = j;
		this.f = f;
		this.g = g;
		this.h = h;
	}
	
	//Parameterized constructor
	public Cell(int i, int j, double f, double g, double h) {
		this.parent_i = i;
		this.parent_j = j;
		this.f = f;
		this.g = g;
		this.h = j;
	}
	
	//Default constructor
	public Cell() {
	
	}
}