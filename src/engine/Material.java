package engine;

public class Material {
	
	private int vao_id;
	private int vertex_count;
	private int texID;
	
	public Material(int vao_id, int vertex_count, int texID) {
		this.vao_id = vao_id;
		this.vertex_count = vertex_count;
		this.texID = texID;
	}
		
	public Material(int vao_id, int vertex_count) {
		this.vao_id = vao_id;
		this.vertex_count = vertex_count;
	}

	public int getVaoID() {
		return this.vao_id;
	}
	
	public int getVertexCount() {
		return this.vertex_count;
	}
	
	public int getTextureID() {
		return this.texID;
	}

}
