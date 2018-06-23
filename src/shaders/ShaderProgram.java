package shaders;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

public abstract class ShaderProgram {
	
	private int program;
	private int vs;
	private int fs;
	
	private static FloatBuffer matrixBuffer = BufferUtils.createFloatBuffer(16);
	
	public ShaderProgram(String vertexShader, String fragmentShader) {
		
		this.program = GL20.glCreateProgram();

		if(vertexShader != null) {
			this.vs = loadShader(vertexShader, GL20.GL_VERTEX_SHADER);
			GL20.glAttachShader(program, vs);
		}
		if(fragmentShader != null) {
			this.fs = loadShader(fragmentShader, GL20.GL_FRAGMENT_SHADER);
			GL20.glAttachShader(program, fs);
		}
		
		bindAttributes();
		GL20.glLinkProgram(program);
		GL20.glValidateProgram(program);
		getAllUniformLocations();
		connectTextureUnits();
	}
	
	protected abstract void getAllUniformLocations();
	
	protected abstract void connectTextureUnits();
	
	protected int getUniformLocation(String name) {
		return GL20.glGetUniformLocation(program, name);
	}
	
	public void start() {
		GL20.glUseProgram(program);
	}
	
	public void stop() {
		GL20.glUseProgram(0);
	}
	
	public void cleanUp() {
		stop();
		GL20.glDetachShader(program, vs);
		GL20.glDetachShader(program, fs);
		GL20.glDeleteProgram(program);
		GL20.glDeleteShader(vs);
		GL20.glDeleteShader(fs);
	}
	
	public abstract void bindAttributes();
	
	
	protected void bindAttribute(int attributeNumber, String variable) {
		GL20.glBindAttribLocation(program, attributeNumber, variable);
	}
	
	protected void loadFloat(int location, float value) {
		GL20.glUniform1f(location, value);
	}
	
	protected void loadVector2f(int location, Vector2f vector) {
		GL20.glUniform2f(location, vector.x, vector.y);
	}
	
	protected void loadVector3f(int location, Vector3f vector) {
		GL20.glUniform3f(location, vector.x, vector.y, vector.z);
	}
	
	protected void loadVector4f(int location, Vector4f vector) {
		GL20.glUniform4f(location, vector.x, vector.y, vector.z, vector.w);
	}
	
	protected void loadBoolean(int location, boolean value) {
		GL20.glUniform1f(location, (value) ? 1 : 0);
	}
	
	protected void loadInt(int location, int value) {
		GL20.glUniform1i(location, value);
	}
	
	protected void loadMatrix(int location, Matrix4f matrix) {
		matrix.store(matrixBuffer);
		matrixBuffer.flip();
		GL20.glUniformMatrix4(location, false, matrixBuffer);
	}
	
	private static int loadShader(String file, int type) {
		StringBuilder source = new StringBuilder();
		
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(ShaderProgram.class.getResourceAsStream(file)));
			String line;
			while((line = br.readLine()) != null)
				source.append(line).append("\n");
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		int shaderID = GL20.glCreateShader(type);
		GL20.glShaderSource(shaderID, source);
		GL20.glCompileShader(shaderID);
		
		if(GL20.glGetShaderi(shaderID, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE) {
			System.out.println(GL20.glGetShaderInfoLog(shaderID, 500));
			System.err.println("Could not compile shader");
			System.exit(-1);
		}
		
		return shaderID;
	}
}
