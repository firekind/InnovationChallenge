package components;

import java.util.Stack;

import org.lwjgl.util.vector.Vector2f;

import engine.EntitiesMap;
import engine.Game;
import entity.Entity;
import pathfinder.ASearch;
import pathfinder.Pair;

public class PathfinderComponent extends Component{
	
	private Stack <Pair <Integer, Integer>> path;
	private Entity target;
	
	public PathfinderComponent(Entity attachedTo, Entity target) {
		super(ComponentID.pathfinder, attachedTo);
		this.target = target;
		this.path = new Stack<Pair <Integer, Integer>>();
		
		Game.updateComponents.add(this);
		
	}
	
	@Override
	public void update() {
		if(attachedTo.isFrozen()) return;
		
		Vector2f sourceVec = EntitiesMap.getCellVec(attachedTo.transform.pos.x, attachedTo.transform.pos.y);
		Vector2f destVec = EntitiesMap.getCellVec(target.transform.pos.x, target.transform.pos.y);
		path = ASearch.getPath(new Pair<Integer, Integer>((int)sourceVec.x, (int)sourceVec.y), new Pair<Integer, Integer>((int)destVec.x, (int)destVec.y));
		if(!path.isEmpty()) {
			path.pop();
			if(!path.isEmpty()) {
				Pair<Integer, Integer> coords = path.pop();
			 	Vector2f dir = getDir(coords); //gets direction from current position to popped value
			 	this.attachedTo.transform.move(dir.x, dir.y);
			}
		}
			
//		System.out.println(path);
//		System.out.println("target cell x: "+destVec.x + " target y: "+destVec.y);
//		System.out.println("source cell x: "+sourceVec.x+" source cell y: "+sourceVec.y);
//		System.out.println(path+"\n");
		
		
	}
	
	private Vector2f getDir(Pair<Integer, Integer> coords) {
		Vector2f dir = new Vector2f();
		
		Vector2f targetPos = new Vector2f(coords.second, -coords.first+EntitiesMap.getRows());
		Vector2f pos = EntitiesMap.getCellVecNormalCoordinateSystem(attachedTo.transform.pos.x, attachedTo.transform.pos.y);
		
		if(targetPos.x == pos.x && targetPos.y == pos.y) {
			return new Vector2f(0,0);
		}
		
		Vector2f.sub(targetPos, pos, dir);
		dir.normalise();
		return dir;
	}
}
