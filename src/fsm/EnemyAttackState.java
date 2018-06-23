package fsm;

import java.util.Random;

import org.lwjgl.util.vector.Vector2f;

import components.ComponentID;
import components.MiscComponent;
import engine.Main;
import entity.Entity;
import entity.EntityID;

public class EnemyAttackState extends State{

	private Entity target;
	private Random random;
	public EnemyAttackState(StateMachine machine, Entity attachedTo, Entity target) {
		super(StateID.enemyAttackState, machine, attachedTo);
		this.target = target;
		this.random = new Random();
	}

	@Override
	public void enter() {
		attachedTo.addComponent(new MiscComponent(attachedTo, Main.callbacks.followRotation(), attachedTo, target));

	}

	@Override
	public void update() {
		int prob = random.nextInt(20);
		
		if(prob == 0) {
			float x = (float)(attachedTo.transform.pos.x + (attachedTo.material.getSx()/2)*Math.cos(Math.toRadians(attachedTo.transform.rot+90)));
			float y = (float)(attachedTo.transform.pos.y + (attachedTo.material.getSy()/2)*Math.sin(Math.toRadians(attachedTo.transform.rot+90)));
			Vector2f pos = new Vector2f(x, y);
			Vector2f dir = new Vector2f(target.transform.pos.x - attachedTo.transform.pos.x, target.transform.pos.y - attachedTo.transform.pos.y);
			dir.normalise();
			Main.creator.createBullet(pos, dir, attachedTo.transform.rot, EntityID.enemyBullet);
		}
		
		if(Main.creator.getPlayer().health.getCurrentHealth()<=0) {
			machine.toDefaultState();
		}
	}

	@Override
	public void exit() {
		attachedTo.removeComponent(ComponentID.misc);
	}

}
