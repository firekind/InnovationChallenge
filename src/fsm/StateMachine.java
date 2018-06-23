package fsm;

public class StateMachine {
	protected State currentState;
	protected State previousState;
	protected State defaultState;
		
	public void update() {
		if(currentState.getAttachedTo().isFrozen()) return;
		currentState.update();
	}
	
	public void changeState(State newState) {
		currentState.exit();
		previousState = currentState;
		currentState = newState;
		currentState.enter();
	}
	
	public void toPreviousState() {
		changeState(previousState);
	}
	
	public void toDefaultState() {
		changeState(defaultState);
	}
	
	public void setDefaultState(State defaultState) {
		this.defaultState = defaultState;
	}
	
	public void setCurrentState(State state) {
		this.currentState = state;
	}
	
	public State getCurrentState() {
		return currentState;
	}
}
