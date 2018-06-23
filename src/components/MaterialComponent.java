package components;

import engine.Game;
import engine.Material;
import entity.Entity;

public class MaterialComponent extends Component{

	private Material material;
	private float sx;
	private float sy;
	public MaterialComponent(Entity attachedTo, Material material, float sx, float sy) {
		super(ComponentID.material, attachedTo);
		this.material = material;
		this.sx = sx;
		this.sy = sy;
		Game.renderComponents.add(this);
	}
	
	public Material getMaterial() {
		return this.material;
	}
	
	public void setMaterial(Material material) {
		this.material = material;
	}
	
	public float getSx() {
		return this.sx;
	}
	
	public float getSy() {
		return this.sy;
	}
	
}
