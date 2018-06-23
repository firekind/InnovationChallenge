package components;

import callback.Callback;
import engine.Game;
import entity.Entity;

public class MiscComponent extends Component{

	private Callback c;
	private Object[] data;
	public MiscComponent(Entity attachedTo, Callback c, Object...data) {
		super(ComponentID.misc, attachedTo);
		this.c = c;
		this.data = data;
		Game.updateComponents.add(this);
	}
	
	@Override
	public void update() {
		c.execute(data);
	}
}
