package components;

import org.lwjgl.util.vector.Vector2f;

import callback.Callback;
import engine.Game;
import entity.Entity;
import entity.EntityID;
import gui.GUI;
import rooms.RoomMap;

public class HealthComponent extends Component{

	private int maxHealth;
	private boolean regen;
	private float currentHealth;
	private float regenRate;
	private float scaleReductionFactor;
	
	private GUI healthbar;
	private Callback onDeath;
	private Object[] data;
	
	
	public HealthComponent(Entity attachedTo, int maxHealth, GUI healthbar,  boolean regen, float regenRate) {
		this(attachedTo, maxHealth, healthbar);
		this.regen = regen;
		this.regenRate = regenRate;
	}
	
	public HealthComponent(Entity attachedTo, int maxHealth, GUI healthbar) {
		super(ComponentID.health, attachedTo);
		this.maxHealth = maxHealth;
		this.currentHealth  = maxHealth;
		this.healthbar = healthbar;
		
		this.scaleReductionFactor = healthbar.getScale().x/maxHealth;
		
		Game.guis.add(healthbar);
		Game.updateComponents.add(this);
		
	}
	
	public HealthComponent(Entity attachedTo, int maxHealth) {
		super(ComponentID.health, attachedTo);
		this.maxHealth = maxHealth;
		this.currentHealth = maxHealth;
		
		Game.updateComponents.add(this);
	}
	
	@Override
	public void update() {	
		if(currentHealth<=0){
			if(onDeath == null)
				attachedTo.destroy();
			else
				onDeath.execute(data);
			
			if(healthbar != null) 
				Game.guis.remove(healthbar);
			if(attachedTo.id == EntityID.enemy)
				RoomMap.currentRoom.enemyCount-=1;
		}
		
		if(regen && currentHealth<maxHealth) {
			increaseHealth(regenRate);
		}
	}
	
	public void reduceHealth(float damage) {
		this.currentHealth-=damage;
		
		if(healthbar!=null) {
			Vector2f scale = healthbar.getScale();
			healthbar.setScale(scale.x - (scaleReductionFactor*damage), scale.y);
		}
	}
	
	public void increaseHealth(float increase) {
		this.currentHealth+=increase;
		if(healthbar!=null) {
			Vector2f scale = healthbar.getScale();
			healthbar.setScale(scale.x + (scaleReductionFactor*increase), scale.y);
		}
	}
	
	public float getCurrentHealth() {
		return this.currentHealth;
	}
	
	public int getMaxHealth() {
		return this.maxHealth;
	}
	
	public GUI getHealthbar() {
		return healthbar;
	}
	
	public void setRegen(boolean regen) {
		this.regen = regen;
		this.regen = true;
	}
	
	public void onDeath(Callback onDeath, Object...data) {
		this.onDeath = onDeath;
		this.data = data;
	}

}
