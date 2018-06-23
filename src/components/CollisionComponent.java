package components;

import callback.Callback;
import engine.AABB;
import engine.Game;
import entity.Entity;

public class CollisionComponent extends Component{

	private AABB bounds;
	private Callback c;
	private Object[] data;
	
	public CollisionComponent(Entity attachedTo, float sx, float sy, Callback c, Object...data) {
		super(ComponentID.collision, attachedTo);
		Game.updateComponents.add(this);
		
		bounds = new AABB(this.attachedTo, sx, sy);
		this.c = c;
		this.data = data;
	}
	
	@Override
	public void update() {
		if(c!=null)
			c.execute(data);
	}
	
	public AABB getAABB() {
		return this.bounds;
	}
}
