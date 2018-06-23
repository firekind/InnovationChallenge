package callback;

import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector2f;

import engine.Collision;
import engine.DisplayManager;
import engine.Game;
import engine.Message;
import entity.Entity;
import entity.EntityID;
import rooms.RoomMap;

public class Callbacks {
	
	
	public Callback inWindow() {
		 return (Object[] data, Object... extra)-> {
			
			int dir = (int)extra[0];
			int offset = (int) extra[1];
				
			Entity entity = (Entity)data[0];
			
			switch(dir) {
			case 0:
				entity.transform.pos.x = offset;
				break;
			case 1:
				entity.transform.pos.x = DisplayManager.WIDTH-offset;
				break;
			case 2:
				entity.transform.pos.y = offset;
				break;
			case 3:
				entity.transform.pos.y = DisplayManager.HEIGHT-offset;
			}
			
		};
	}
	
	public Callback windowExitRemove() {
		return (Object[] data, Object... extra)->{
			((Entity)data[0]).destroy();
		};
	}
	
	public Callback followRotation() {
		return (Object[] data, Object... extra)-> {
			
			Entity source = (Entity)data[0];
			
			Vector2f pos = source.transform.pos;
			Vector2f target_pos;
			if(data[1].equals("Mouse"))
				target_pos = new Vector2f(Mouse.getX(), Mouse.getY());	
			else {
				Entity target = (Entity) data[1];
				target_pos = new Vector2f(target.transform.pos.x, target.transform.pos.y);
			}
			
			float alpha = (float) Math.toDegrees(Math.atan2(target_pos.y - pos.y, target_pos.x - pos.x));
			source.transform.rotate(alpha - 90);
		};
	}
	
	public Callback propel() {
		return (Object[] data, Object... extra)->{
			Entity bullet = (Entity) data[0];
			Vector2f dir = (Vector2f) data[1];
			
			bullet.transform.move(dir.x, dir.y);
		};
	}
	
	public Callback playerCollision() {
		return (Object[] data, Object... extra)-> {
			Entity player = (Entity) data[0];
			for(int i=0;i<Game.updateComponents.size();i++) {
				Entity e = Game.updateComponents.get(i).getAttachedTo();
				if(e.collisionComponent == null) continue;
				
				if(e.id == EntityID.wall) {
					Collision c = player.collisionComponent.getAABB().getCollision(e.collisionComponent.getAABB());
					if(c.isIntersecting) {
						player.collisionComponent.getAABB().correctPosition(e.collisionComponent.getAABB(), c);
					}
				}
				
				if(e.id == EntityID.enemyBullet) {
					Collision c = player.collisionComponent.getAABB().getCollision(e.collisionComponent.getAABB());
					if(c.isIntersecting) {
						e.destroy();
						player.health.reduceHealth(0.5f);
					}
				}
				
			}
		};
	}
	
	public Callback bulletCollision() {
		return (Object[] data, Object... extra)-> {
			Entity bullet = (Entity) data[0];
			for(int i=0;i<Game.updateComponents.size();i++) {
				Entity e = Game.updateComponents.get(i).getAttachedTo();
				if(e.collisionComponent == null) continue;
				
				if(e.id == EntityID.wall) {
					Collision c = bullet.collisionComponent.getAABB().getCollision(e.collisionComponent.getAABB());
					if(c.isIntersecting) {
						bullet.destroy();
					}
				}
				
			}
		};
	}
	
	public Callback enemyCollision() {
		return (Object[] data, Object... extra)-> {
			Entity enemy = (Entity) data[0];
			for(int i=0;i<Game.updateComponents.size();i++) {
				Entity e = Game.updateComponents.get(i).getAttachedTo();
				if(e.collisionComponent == null) continue;
				
				if(e.id == EntityID.wall) {
					Collision c = enemy.collisionComponent.getAABB().getCollision(e.collisionComponent.getAABB());
					if(c.isIntersecting) {
						enemy.collisionComponent.getAABB().correctPosition(e.collisionComponent.getAABB(), c);
					}
				}
				else if(e.id == EntityID.bullet) {
					Collision c = enemy.collisionComponent.getAABB().getCollision(e.collisionComponent.getAABB());
					if(c.isIntersecting) {
						e.destroy();
						enemy.health.reduceHealth(1.42f);
					}
				}
				
			}
		};
	}
	
	public Callback enemyExploCollision() {
		return (Object[] data, Object... extra)->{
			Entity enemy = (Entity) data[0];
			float damage = (float) data[1];
			for(int i=0;i<Game.updateComponents.size();i++) {
				Entity e = Game.updateComponents.get(i).getAttachedTo();
				if(e.collisionComponent == null) continue;
				
				if(e.id == EntityID.wall) {
					Collision c = enemy.collisionComponent.getAABB().getCollision(e.collisionComponent.getAABB());
					if(c.isIntersecting) {
						enemy.collisionComponent.getAABB().correctPosition(e.collisionComponent.getAABB(), c);
					}
				}
				else if(e.id == EntityID.bullet) {
					Collision c = enemy.collisionComponent.getAABB().getCollision(e.collisionComponent.getAABB());
					if(c.isIntersecting) {
						e.destroy();
						enemy.health.reduceHealth(1.42f);
					}
				}
				else if(e.id == EntityID.player) {
					Collision c = enemy.collisionComponent.getAABB().getCollision(e.collisionComponent.getAABB());
					if(c.isIntersecting) {
						enemy.destroy();
						e.health.reduceHealth(damage);
					}
				}
				
			}
		};
	}
	
	public Callback gateCollision() {
		return (Object[] data, Object... extra)-> {
			if(!RoomMap.currentRoom.gateEnabled) return;
			
			int targetRoom = (int) data[1];
			Entity gate = (Entity) data[0];
			for(int i=0;i<Game.updateComponents.size();i++) {
				Entity e = Game.updateComponents.get(i).getAttachedTo();
				if(e.collisionComponent == null) continue;
				
				if(e.id == EntityID.player) {
					Collision c = gate.collisionComponent.getAABB().getCollision(e.collisionComponent.getAABB());
					if(c.isIntersecting) {
						RoomMap.currentRoom.destory();
						RoomMap.visit(RoomMap.currentRoom.room_id,targetRoom);
					}
				}
				
			}
			
		};
	}
	
	public Callback messageHandle() {
		return (Object[] data, Object...extra)->{
			Message message = (Message) extra[0];
			Entity entity = (Entity) data[0];
			boolean freeze = (message.getMessage().equals("freeze")) ? true : false;
			entity.setFrozen(freeze);
		};
	}
	
	public Callback unrealize() {
		return (Object[] data, Object...extra)->{
			Entity entity = (Entity)data[0];
			entity.realize(false);
		};
	}
}
