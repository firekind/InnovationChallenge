package abilities;

import entity.Entity;

public abstract class Ability {
	
	private AbilityID id;
	protected boolean active;
	protected Entity entity;
	
	public Ability(AbilityID id) {
		this.id = id;
		this.active = false;
		this.entity = null;
	}
	
	public Ability(AbilityID id, Entity entity) {
		this(id);
		this.entity = entity;
	}
	
	public abstract void execute();
	public abstract void regenerate();
	
	public void activate() {
		if(active) return;
		this.active = true;
		
	}
	
	public void deactivate() {
		if (!active) return;
		this.active = false;
	}
	
	public AbilityID getId() {
		return this.id;
		
	}
	
	public boolean isActive() {
		return this.active;
	}
	
	public Entity getEntity() {
		return this.entity;
	}

}
