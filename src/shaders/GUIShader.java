package shaders;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

public class GUIShader extends ShaderProgram{

	private static final String VERTEX_SHADER = "/shaders/guiShader.vert";
	private static final String FRAGMENT_SHADER = "/shaders/guiShader.frag";
	
	private int location_transformation;
	private int location_texture;
	private int location_renderingBar;
	private int location_barColor;
	
	public GUIShader() {
		super(VERTEX_SHADER, FRAGMENT_SHADER);
		
	}

	@Override
	protected void getAllUniformLocations() {
		location_transformation = super.getUniformLocation("transform");		
		location_texture = super.getUniformLocation("textureSampler");
		location_renderingBar = super.getUniformLocation("renderingBar");
		location_barColor = super.getUniformLocation("barColor");
	}

	@Override
	protected void connectTextureUnits() {
		super.loadInt(location_texture, 0);
		
	}

	@Override
	public void bindAttributes() {
		super.bindAttribute(0, "position");
		
	}
	
	public void loadTransformationMatrix(Matrix4f matrix) {
		super.loadMatrix(location_transformation, matrix);
	}
	
	public void renderingBar(boolean value) {
		super.loadBoolean(location_renderingBar, value);
	}
	
	public void loadBarColor(Vector3f color) {
		super.loadVector3f(location_barColor, color);
	}

}
