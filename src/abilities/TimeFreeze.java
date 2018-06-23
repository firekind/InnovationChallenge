package abilities;

import org.lwjgl.util.vector.Vector2f;

import engine.Main;
import engine.Message;
import entity.EntityID;
import gui.GUI;
import toolbox.Maths;

public class TimeFreeze extends Ability{

	private float depletionRate;
	private float regenRate;
	private float maxAmount;
	private float currentAmount;
	private float scaleFactor;
	private GUI gui;
	
	public TimeFreeze(float maxAmount, float depletionRate, float regenRate, GUI gui) {
		super(AbilityID.timeFreeze);
		this.maxAmount = maxAmount;
		this.currentAmount = maxAmount;
		this.depletionRate = depletionRate;
		this.regenRate = regenRate;
		this.scaleFactor = gui.getScale().x/maxAmount;
		this.gui = gui;
	}

	@Override
	public void execute() {	
		if(currentAmount<=0) {
			deactivate();
			return;
		}
		
		Vector2f scale = gui.getScale();
		currentAmount -= depletionRate;
		gui.setScale(scale.x - (scaleFactor*depletionRate), scale.y);
		
		currentAmount = Maths.clamp(currentAmount, 0, maxAmount);
	}
	
	public void regenerate() {
		if(active || currentAmount>=maxAmount) return;
		currentAmount = Maths.clamp(currentAmount, 0, maxAmount);
		if(currentAmount>=maxAmount) return;
		
		Vector2f scale = gui.getScale();
		currentAmount += regenRate;
		gui.setScale(scale.x + (scaleFactor*regenRate), scale.y);
		
		
	}
	
	@Override
	public void activate() {
		if(active || currentAmount<maxAmount) return;
		Message message = new Message(Main.creator.getEntitiesExcept(EntityID.player), "freeze");
		this.active = true;
		Main.messageHandler.broadcast(message);
	}
	
	@Override
	public void deactivate() {
		if(!active) return;
		Message message = new Message(Main.creator.getEntitiesExcept(EntityID.player), "unfreeze");
		this.active = false;
		Main.messageHandler.broadcast(message);
	}

	public float getDepletionRate() {
		return depletionRate;
	}

	public float getRegenRate() {
		return regenRate;
	}

	public float getMaxAmount() {
		return maxAmount;
	}

	public float getCurrentAmount() {
		return currentAmount;
	}
	
	public GUI getGui() {
		return gui;
	}
	
	

}
