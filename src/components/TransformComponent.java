package components;


import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;

import engine.Game;
import entity.Entity;
import toolbox.Maths;
public class TransformComponent extends Component{
	
	public Vector2f pos;
	public float rot;
	public float scale;
	public float speed;
	
	public TransformComponent(Entity attachedTo, Vector2f pos, float rot, float scale, float speed) {
		super(ComponentID.transform, attachedTo);
		
		this.pos = pos;
		this.rot = rot;
		this.scale = scale;
		this.speed = speed;
		
		Game.updateComponents.add(this);
	}
	
	public void move(float magX, float magY) {
		if(attachedTo.isFrozen()) return;
		this.pos.x += magX * speed;
		this.pos.y += magY * speed;
	}
	
	public void rotate(float rot) {
		if(attachedTo.isFrozen()) return;
		this.rot = rot;
	}
	
	public Matrix4f getTransformationMatrix() {
		return Maths.createTransformationMatrix(pos, rot, scale);
	}

}
