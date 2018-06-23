package engine;

import java.util.ArrayList;
import java.util.HashMap;

import org.lwjgl.util.vector.Vector2f;

import components.Component;
import components.ComponentID;
import entity.EntityID;
import pathfinder.Pair;

public class EntitiesMap {
	
	private static final int CELL_SIZE = 20;
	
	private static HashMap<String, ArrayList<Component>> map;
	
	private static int rows;
	private static int cols;
	
	public EntitiesMap() {
		map = new HashMap<String, ArrayList<Component>>();
		rows = (DisplayManager.HEIGHT/CELL_SIZE) - 1;
		cols = (DisplayManager.WIDTH/CELL_SIZE) - 1;
//		System.out.println("rows: "+rows+" cols: "+cols);
		update();
	}
	
	public void update() {
		initMap();
		populateMap();
	}
	
	private void populateMap() {
		// maps entities to cell number 
			ArrayList<Component> components = Game.updateComponents;
			for(int i=0;i<components.size();i++) {
				Component c = components.get(i);
				if(c.getId() != ComponentID.collision) continue;
				
				for(String j: getCellsForObj(c)) {
					ArrayList<Component> cs = map.get(j);
					if(cs != null) {
						cs.add(c);
						map.put(j, cs);
					}	
				}
			}
	}
	
	private ArrayList<String> getCellsForObj(Component c) {
		ArrayList<String> cells = new ArrayList<String>();
		
		float pos_x = c.getAttachedTo().transform.pos.x;
		float pos_y = c.getAttachedTo().transform.pos.y;
		float sx = c.getAttachedTo().material.getSx();
		float sy = c.getAttachedTo().material.getSy();
		
		if(c.getAttachedTo().id == EntityID.player || c.getAttachedTo().id == EntityID.enemy) {
			sx /= 2;
			sy /= 2;
		}
		
		Vector2f topRight = getCellVec(pos_x+(sx/2), pos_y+(sy/2));
		Vector2f bottomLeft = getCellVec(pos_x-(sx/2), pos_y-(sy/2));
		
		for(float i = bottomLeft.x; i>=topRight.x;i--) {
			for(float j = bottomLeft.y; j<=topRight.y;j++) {
				cells.add(coordToString(i, j));
			}
		}
		
		return cells;
	}
	
	//AI FUNCTIONS
	////////////////////////////////////////////////////////////////////////
	public static boolean validateCell(int x, int y) {
		boolean value = (x>=0 && x<=rows) && (y>=0 && y<=cols);
		//System.out.println("x: "+x+" rows: "+rows+"\ny: "+y+" cols: "+cols+"\n");
		return value;
	}
	
	public static boolean cellIsUnblocked(int x, int y) {
//		System.out.println("\'"+coordToString(x, y)+"\'");
//		System.out.println("x: "+x+" y: "+y);
		if(validateCell(x, y)) {
			try {
				ArrayList<Component> cs  = map.get(coordToString(x, y));
				if(cs.size() == 1 && (cs.get(0).getAttachedTo().id == EntityID.player || cs.get(0).getAttachedTo().id == EntityID.enemy)) {
					return true;
				}
				
				
				
				boolean flag = false;
				for(Component c: cs) {
					if(c.getAttachedTo().id != EntityID.enemy)
						flag = true;
				}
				if(!flag) return true;
				
//				if(map.get(coordToString(x, y)).size() != 0)
//				System.out.println("stuff: "+map.get(coordToString(x, y)).get(0).getAttachedTo().id);

				return map.get(coordToString(x, y)).isEmpty();
			}catch(NullPointerException e) {
				System.out.println("fdsf");
				return false;
			}
		}
		return false;
	}
	
	public static Pair<Integer, Integer> getRowsAndCols(){
		return new Pair<Integer, Integer>(rows+2, cols+2);
	}
	////////////////////////////////////////////////////////////////////////
	
	public static ArrayList<Component> getEntitiesInCell(Vector2f pos){
		return map.get(getCellS(pos.x, pos.y));
	}
	
	public static String getCellS(float x, float y) {
		return Integer.toString(rows - (int)(y/CELL_SIZE))+","+Integer.toString((int)(x/CELL_SIZE));
	}
	
	public static Vector2f getCellVec(float x, float y) {
		return new Vector2f(rows - (int)(y/CELL_SIZE), (int)(x/CELL_SIZE));
	}
	
	public static String coordToString(float x, float y) {
		return Integer.toString((int) x)+","+Integer.toString((int) y);
	}
	
	public static Vector2f getCellVecNormalCoordinateSystem(float x, float y) {
		return new Vector2f((int)(x/CELL_SIZE), (int)(y/CELL_SIZE));
	}
	
	public static int getRows() {
		return rows;
	}
	
	public static int getCols() {
		return cols;
	}
	
	private void initMap() {
		//clears the hashMap 
		for(int i=0;i<=rows;i++) {
			for(int j=0;j<=cols;j++) {
				String s = coordToString(i, j);
				map.put(s, new ArrayList<Component>());
			}
		}
	}

}
