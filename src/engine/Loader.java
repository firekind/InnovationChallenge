package engine;

import static org.lwjgl.opengl.GL11.GL_LINEAR;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MAG_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MIN_FILTER;
import static org.lwjgl.opengl.GL11.glTexParameterf;
import static org.lwjgl.opengl.GL11.glTexParameteri;
import static org.lwjgl.opengl.GL14.GL_TEXTURE_LOD_BIAS;

import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

public class Loader {
	
	private static ArrayList<Integer> vaos = new ArrayList<Integer>();
	private static ArrayList<Integer> vbos = new ArrayList<Integer>();
	private static ArrayList<Integer> textures = new ArrayList<Integer>();
	
	private int[] indices = {
			0,1,3,
			3,1,2
	};
	
	private float[] texCoords = {
			0,0,
			0,1,
			1,1,
			1,0
	};
	
	public Material loadToVAO(float sx, float sy, String texture) {
		float[] vertices = {-sx/2, sy/2,
				-sx/2, -sy/2,
				sx/2, -sy/2,
				sx/2, sy/2
		};
		int vaoID = createVAO();
		bindIndicesBuffer(indices);
		storeDataInAttributeList(0, 2, vertices);
		storeDataInAttributeList(1, 2, texCoords);
		unbindVAO();
		int tex = loadTexture(texture);
		return new Material(vaoID, indices.length, tex);
	}
	
	public Material loadToVAO(float[] vertices) {
		int vaoID = createVAO();
		storeDataInAttributeList(0, 2, vertices);
		unbindVAO();
		return new Material(vaoID, vertices.length/2);
	}
	
	public Material loadToVAO(float[] vertices, String texture) {
		int vaoID = createVAO();
		bindIndicesBuffer(indices);
		storeDataInAttributeList(0, 2, vertices);
		storeDataInAttributeList(1, 2, texCoords);
		unbindVAO();
		int tex = loadTexture(texture);
		return new Material(vaoID, indices.length, tex);
	}
	
	public int loadTexture(String fileName) {
		Texture tex = null;
		try {
			tex = TextureLoader.getTexture("PNG", Loader.class.getResourceAsStream(("/images/"+fileName+".png")));
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
			glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_LOD_BIAS, -1);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		int texID = tex.getTextureID();
		textures.add(texID);
		
		return texID;
	}
	
	private int createVAO() {
		int vaoID = GL30.glGenVertexArrays();
		vaos.add(vaoID);
		GL30.glBindVertexArray(vaoID);
		return vaoID;
	}
	
	private void storeDataInAttributeList(int attributeNumber, int coordSize, float[] data) {
		int vbo = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, storeDataInFloatBuffer(data), GL15.GL_STATIC_DRAW);
		GL20.glVertexAttribPointer(attributeNumber, coordSize, GL11.GL_FLOAT, false, 0, 0);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		vbos.add(vbo);
	}
	
	private void unbindVAO() {
		GL30.glBindVertexArray(0);
	}
	
	private void bindIndicesBuffer(int[] indices) {
		int vbo = GL15.glGenBuffers();
		vbos.add(vbo);
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vbo);
		GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, storeDataInIntBuffer(indices), GL15.GL_STATIC_DRAW);
	}
	
	private IntBuffer storeDataInIntBuffer(int[] data) {
		IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		return buffer;
	}
	
	private FloatBuffer storeDataInFloatBuffer(float[] data) {
		FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		return buffer;
	}
	
	public void cleanUp() {
		for(int vao: vaos) {
			GL30.glDeleteVertexArrays(vao);
		}
		
		for(int vbo: vbos) {
			GL15.glDeleteBuffers(vbo);
		}
		
		for(int texture: textures) {
			GL11.glDeleteTextures(texture);
		}
	}

}
