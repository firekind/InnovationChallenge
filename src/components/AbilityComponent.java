package components;

import abilities.Ability;
import engine.Game;
import entity.Entity;

public class AbilityComponent extends Component{

	private Ability currentAbility;
	public AbilityComponent(Entity attachedTo) {
		super(ComponentID.ability, attachedTo);
		this.currentAbility = null;
		Game.updateComponents.add(this);
	}
	
	@Override
	public void update() {
		if(currentAbility.isActive()) {
			currentAbility.execute();
		}
		else
			currentAbility.regenerate();
	}
	
	public Ability getCurrentAbility() {
		return currentAbility;
	}
	
	public void setCurrentAbility(Ability ability) {
		this.currentAbility = ability;
	}
	
	

}
