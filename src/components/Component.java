package components;

import entity.Entity;

public abstract class Component {
	
	protected ComponentID id; // id of component
	protected Entity attachedTo; // to which game entity the component is attached to 
	
	public boolean toRemove;
	
	public Component(ComponentID id, Entity attachedTo) {
		this.id = id;
		this.attachedTo = attachedTo;
		this.toRemove = false;
	}

	public void update() {
		//optional. can be implemented in child classes
		return;
	}
	public void render() {
		//optional. can be implemented in child classes
		return;
	}
	
	public ComponentID getId() {
		return id;
	}

	public Entity getAttachedTo() {
		return attachedTo;
	}
}
