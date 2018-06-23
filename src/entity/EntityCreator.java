package entity;

import java.util.HashMap;
import java.util.LinkedList;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import abilities.TimeFreeze;
import components.AbilityComponent;
import components.CollisionComponent;
import components.HealthComponent;
import components.MaterialComponent;
import components.MessageListenerComponent;
import components.MiscComponent;
import components.PathfinderComponent;
import components.StateMachineComponent;
import components.TransformComponent;
import components.WindowExitTriggerComponent;
import engine.Game;
import engine.Loader;
import engine.Main;
import engine.Material;
import fsm.EnemyAttackState;
import fsm.EnemyIdleState;
import fsm.StateMachine;
import gui.GUI;
import gui.GuiID;

public class EntityCreator {
	public static final int player_size = 80;
	public static final float player_speed = 4;
	
	private Entity player;
	private GUI abilityBar;
	private Material bg;
	private Light light;
	private Loader loader;
	
	private HashMap<EntityID, LinkedList<Entity>> entities;
	
	public EntityCreator(Loader loader) {
		this.loader = loader;
		float vertices[] = {-1, 1, -1, -1, 1, -1, 1, 1};
		this.bg = loader.loadToVAO(vertices, "bg");
		
		entities = new HashMap<EntityID, LinkedList<Entity>>();
		
	}
	
	public Entity createPlayer(float x, float y, float rot) {
		this.player = new Entity(EntityID.player);
		Vector2f playerPos = new Vector2f(x, y);
		GUI healthbar = new GUI(GuiID.healthbar, new Vector2f(-0.84f,0.95f), new Vector2f(0.15f,0.02f), player);
		abilityBar = new GUI(GuiID.timeFreezeBar, new Vector2f(+0.84f, 0.95f), new Vector2f(0.15f, 0.02f));
		Game.guis.add(abilityBar);
		
		player.addComponent(new TransformComponent(player, playerPos, rot, 1, player_speed));
		player.addComponent(new MaterialComponent(player, loader.loadToVAO(player_size, player_size, "player"), player_size, player_size));
		player.addComponent(new HealthComponent(player, 100, healthbar, true, 1/60f));
		player.addComponent(new MiscComponent(player, Main.callbacks.followRotation(), player, "Mouse"));
		player.addComponent(new WindowExitTriggerComponent(player, 0, Main.callbacks.inWindow(), player));
		player.addComponent(new CollisionComponent(player, player_size/2, player_size/2, Main.callbacks.playerCollision(), player));
		player.addComponent(new AbilityComponent(player));
		
		TimeFreeze timeFreeze = new TimeFreeze(100, 1f, 1/30f, abilityBar);
		player.abilityComponent.setCurrentAbility(timeFreeze);
		
		this.light = new Light(playerPos, new Vector3f(1,0.8f,0.6f), new Vector3f(1,1,1), 250f);
		
		addEntity(player);
		return player;
		
	}
	
	public void createBullet(Vector2f pos, Vector2f dir, float rot, EntityID type) {
		Entity bullet = new Entity(type);
		
		bullet.addComponent(new TransformComponent(bullet, pos, rot, 1, 10));
		bullet.addComponent(new MaterialComponent(bullet, loader.loadToVAO(8, 8, "bullet"), 8, 8));
		bullet.addComponent(new MiscComponent(bullet, Main.callbacks.propel(), bullet, dir));
		bullet.addComponent(new WindowExitTriggerComponent(bullet, 50, Main.callbacks.windowExitRemove(), bullet));
		bullet.addComponent(new CollisionComponent(bullet, 8, 8, Main.callbacks.bulletCollision(), bullet));
		bullet.addComponent(new MessageListenerComponent(bullet, Main.callbacks.messageHandle(), bullet));
		
		addEntity(bullet);
	}
	
	public Entity createEnemy(float x, float y, float rot, float sx, float sy) {
		Entity enemy = createBaseEnemy(x, y, rot, sx, sy, 1, 100, "enemy");
		
		StateMachine enemyStateMachine = new StateMachine();
		EnemyAttackState attack = new EnemyAttackState(enemyStateMachine, enemy, player);
		EnemyIdleState idle = new EnemyIdleState(enemyStateMachine, enemy, attack);
		enemyStateMachine.setCurrentState(idle);
		enemyStateMachine.setDefaultState(idle);
		
		enemy.addComponent(new CollisionComponent(enemy, sx/2, sy/2, Main.callbacks.enemyCollision(), enemy));
		enemy.addComponent(new StateMachineComponent(enemy, enemyStateMachine));

		addEntity(enemy);
		return enemy;
		
	}
	
	public Entity createExploEnemy(float x, float y, float rot, float sx, float sy) {
		Entity enemy = createBaseEnemy(x, y, rot, sx, sy, 3, 50, "exploEnemy");
	
		enemy.addComponent(new CollisionComponent(enemy, sx/2, sy/2, Main.callbacks.enemyExploCollision(), enemy, 10f));
		enemy.addComponent(new MiscComponent(enemy, Main.callbacks.followRotation(), enemy, player));
		
		addEntity(enemy);
		return enemy;
	}
	
	public Entity createWall(float x, float y, float rot, float sx, float sy) {
		Entity wall = new Entity(EntityID.wall);
		
		wall.addComponent(new TransformComponent(wall, new Vector2f(x, y), rot, 1, 0));
		wall.addComponent(new MaterialComponent(wall, loader.loadToVAO(sx, sy, "wall"), sx, sy));
		wall.addComponent(new CollisionComponent(wall, sx, sy, null));
		
		return wall;
	}
	public Entity createGate(float x, float y, float rot, float sx, float sy, int targetRoom) {
		Entity gate = new Entity(EntityID.gate);
		
		gate.addComponent(new TransformComponent(gate, new Vector2f(x, y), rot, 1, 0));
		gate.addComponent(new MaterialComponent(gate, loader.loadToVAO(sx, sy, "gateDisabled"), sx, sy));
		gate.addComponent(new CollisionComponent(gate, sx, sy, Main.callbacks.gateCollision(), gate, targetRoom));
		
		return gate;
	}
	
	private Entity createBaseEnemy(float x, float y, float rot, float sx, float sy, float speed, int health, String texture) {
		Entity enemy = new Entity(EntityID.enemy);
		
		enemy.addComponent(new TransformComponent(enemy, new Vector2f(x, y), rot, 1, speed));
		enemy.addComponent(new MaterialComponent(enemy, loader.loadToVAO(sx, sy, texture), sx, sy));
		enemy.addComponent(new WindowExitTriggerComponent(enemy, 0, Main.callbacks.inWindow(), enemy));
		enemy.addComponent(new HealthComponent(enemy, health));
		enemy.addComponent(new PathfinderComponent(enemy, player));
		enemy.addComponent(new MessageListenerComponent(enemy, Main.callbacks.messageHandle(), enemy));
		
		return enemy;
	}
	
	private void addEntity(Entity entity) {
		LinkedList<Entity> l;
		
		if(entities.get(entity.id) == null) 
			l = new LinkedList<Entity>();
		else 
			l = entities.get(entity.id);
		
		l.add(entity);
		entities.put(entity.id, l);
	}
	
	public void removeEntity(Entity entity) {
		LinkedList<Entity> l = entities.get(entity.id);
		l.remove(entity);
		entities.put(entity.id, l);
	}
	
	public void removeEntites(EntityID id) {
		entities.put(id, new LinkedList<Entity>());
	}
	
	public LinkedList<Entity> getEntitiesWithId(EntityID id){
		return entities.get(id);
	}
	
	public LinkedList<Entity> getEntitiesExcept(EntityID id){
		LinkedList<Entity> e = new LinkedList<Entity>();
		for(EntityID key: entities.keySet()) {
			if(key!=id) {
				e.addAll(entities.get(key));
			}
		}
		return e;
	}
		
	public Entity getPlayer() {
		return this.player;
	}
	
	public Light getLight() {
		return light;
	}
	
	public Material getBg() {
		return this.bg;
	}
	
	public GUI getTimeFreezeBar() {
		return this.abilityBar;
	}
}
