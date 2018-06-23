package fsm;

import java.util.Random;

import engine.Main;
import entity.Entity;

public class EnemyIdleState extends State{

	private State transitionState;
	private int dir;
	
	public EnemyIdleState(StateMachine machine, Entity attachedTo, State transitionState) {
		this(machine, attachedTo);
		this.transitionState = transitionState;
		
	}
	
	public EnemyIdleState(StateMachine machine, Entity attachedTo) {
		super(StateID.enemyIdleState, machine, attachedTo);
		this.transitionState = null;
		this.dir = -1;
		if(new Random().nextInt(2) == 0) {
			this.dir = 1;
		}
	}

	@Override
	public void enter() {}

	@Override
	public void update() {
		attachedTo.transform.rotate(attachedTo.transform.rot+(2*dir));
		
		if(transitionState != null && Main.creator.getPlayer().health.getCurrentHealth() > 0) {
			machine.changeState(transitionState);
		}
	}

	@Override
	public void exit() {}
}
