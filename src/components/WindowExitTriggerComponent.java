package components;

import org.lwjgl.util.vector.Vector2f;

import callback.Callback;
import engine.DisplayManager;
import engine.Game;
import entity.Entity;
import entity.EntityCreator;

public class WindowExitTriggerComponent extends Component{

	private Callback c;
	private Object[] data;
	private float offset;
	
	public WindowExitTriggerComponent(Entity attachedTo, float offset, Callback c, Object... data) {
		super(ComponentID.windowTrigger, attachedTo);
		
		this.c = c;
		this.attachedTo = attachedTo;
		this.data = data;
		this.offset = offset;
		
		Game.updateComponents.add(this);

	}
	
	@Override
	public void update() {
		Vector2f pos = attachedTo.transform.pos;

		if (pos.x < (EntityCreator.player_size/4) - offset){ //if player goes out of the screen from the left
			c.execute(data, 0, EntityCreator.player_size/4); 
		}
		if(pos.x > DisplayManager.WIDTH-(EntityCreator.player_size/4) + offset){ //if player goes out of the screen from the right
			c.execute(data, 1, EntityCreator.player_size/4); 
		}
		if(pos.y < (EntityCreator.player_size/4) - offset){ //if player goes out of the screen from the bottom
			c.execute(data, 2, EntityCreator.player_size/4); 
		}
		if(pos.y > DisplayManager.HEIGHT-(EntityCreator.player_size/4) + offset){//if player goes out of the screen from the top
			c.execute(data, 3, EntityCreator.player_size/4); 
		}
		
	}

}
