package fsm;

import entity.Entity;

public abstract class State {

	protected StateID id;
	protected Entity attachedTo;
	protected StateMachine machine;
	
	public State(StateID id, StateMachine machine, Entity attachedTo) {
		this.id = id;
		this.attachedTo = attachedTo;
		this.machine = machine;
	}
	
	public abstract void enter();
	public abstract void update();
	public abstract void exit();
	
	public StateID getId() {
		return id;
	}

	public Entity getAttachedTo() {
		return attachedTo;
	}
}
