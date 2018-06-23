package components;

import engine.Game;
import entity.Entity;
import fsm.StateMachine;

public class StateMachineComponent extends Component{

	private StateMachine machine;
	public StateMachineComponent(Entity attachedTo, StateMachine machine) {
		super(ComponentID.stateMachine, attachedTo);
		this.machine = machine;
		Game.updateComponents.add(this);
	}
	
	@Override
	public void update() {
		machine.update();
	}
	
	public StateMachine getStateMachine() {
		return this.machine;
	}

}
