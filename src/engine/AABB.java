package engine;



import org.lwjgl.util.vector.Vector2f;

import entity.Entity;

public class AABB {
	private Vector2f center;
	private Vector2f half_extent;
	
	public AABB(Entity entity, float sx, float sy) {
		this.center = entity.transform.pos;
		this.half_extent = new Vector2f(sx/2, sy/2);
	}

	public Collision getCollision(AABB bounds) {		
		//Vector2f distance = center.sub(bounds.center, new Vector2f());
		Vector2f distance = new Vector2f();
		Vector2f.sub(center, bounds.getCenter(), distance);
		distance.x = Math.abs(distance.x);
		distance.y = Math.abs(distance.y);
		
		Vector2f sumOfhalfExtents = new Vector2f();
		Vector2f.add(half_extent, bounds.half_extent, sumOfhalfExtents);
		Vector2f.sub(distance, sumOfhalfExtents, distance);

		return new Collision(distance, distance.x<=0 && distance.y<=0);
	}
	
	public void correctPosition(AABB bounds, Collision data) {
		Vector2f correction = new Vector2f();
		Vector2f.sub(bounds.center, center, correction);
		
		if(data.distance.x > data.distance.y) {
			if(correction.x<0) {
				center.x += -data.distance.x;
			}else if(correction.x>0) {
				center.x += data.distance.x;
			}
		}else {
			if(correction.y<0) {
				center.y += -data.distance.y;
			}else if(correction.y>0) {
				center.y += data.distance.y;
			}
		}
	}
	
	public Vector2f getCenter() {
		return center;
	}

	public Vector2f getHalf_extent() {
		return half_extent;
	}
	
	
}
