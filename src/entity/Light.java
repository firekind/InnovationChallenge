package entity;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

public class Light {
	
	private Vector2f pos;
	private Vector3f color;
	private Vector3f intensity;
	private float radius;
	
	public Light(Vector2f position, Vector3f color, Vector3f intensity, float radius) {
		this.pos = position;
		this.color = color;
		this.intensity = intensity;
		this.radius = radius;
	}

	public Vector2f getPos() {
		return pos;
	}

	public Vector3f getColor() {
		return color;
	}
	
	public Vector3f getIntensity() {
		return intensity;
	}
	
	public float getRadius() {
		return radius;
	}
	
	public void setPos(Vector2f pos) {
		this.pos.x = pos.x;
		this.pos.y = pos.y;
	}
	
}
