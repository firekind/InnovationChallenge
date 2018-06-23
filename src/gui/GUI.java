package gui;

import org.lwjgl.util.vector.Vector2f;

import entity.Entity;

public class GUI {
	
	private int texID;
	private GuiID id;
	private Vector2f pos;
	private Vector2f scale;
	private Entity attachedTo;
	
	public GUI(GuiID id, Vector2f pos, Vector2f scale, int texID) {
		this.pos = pos;
		this.scale = scale;
		this.texID = texID;
		this.id = id;

	}
	
	public GUI(GuiID id, Vector2f pos, Vector2f scale, Entity attachedTo) {
		this(id, pos, scale);
		this.attachedTo = attachedTo;
	}
	
	public GUI(GuiID id, Vector2f pos, Vector2f scale) {
		this.pos = pos;
		this.scale = scale;
		this.texID = 0;
		this.id = id;
	}

	public int getTexID() {
		return texID;
	}

	public Vector2f getPos() {
		return pos;
	}

	public Vector2f getScale() {
		return scale;
	}
	
	public Entity getAttachedTo() {
		return this.attachedTo;
	}
	
	public GuiID getID() {
		return this.id;
	}
	
	public void setScale(float sx, float sy) {
		this.scale.x = sx;
		this.scale.y = sy;
	}
	
	public boolean hasTexture() {
		return (texID == 0) ? false : true;
	}
}
