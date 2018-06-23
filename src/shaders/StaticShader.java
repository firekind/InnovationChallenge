package shaders;

import org.lwjgl.util.vector.Matrix4f;

import entity.Light;

public class StaticShader extends ShaderProgram{

	private static final String VERTEX_SHADER = "/shaders/vertexShader.vert";
	private static final String FRAGMENT_SHADER = "/shaders/fragmentShader.frag";
	
	private int location_tranform;
	private int location_projection;
	private int location_texture;
	private int location_bg;
	private int location_lightPos;
	private int location_lightColor;
	private int location_radius;
	private int location_intensity;
	
	public StaticShader() {
		super(VERTEX_SHADER, FRAGMENT_SHADER);
	}

	@Override
	public void bindAttributes() {
		bindAttribute(0, "position");
		bindAttribute(1, "textureCoords");
	}

	@Override
	protected void getAllUniformLocations() {
		location_tranform = super.getUniformLocation("transform");
		location_projection = super.getUniformLocation("projection");
		location_texture = super.getUniformLocation("textureSampler");
		location_bg = super.getUniformLocation("renderingBG");
		location_lightPos = super.getUniformLocation("lightPos");
		location_lightColor = super.getUniformLocation("lightColor");
		location_intensity = super.getUniformLocation("lightIntensity");
		location_radius = super.getUniformLocation("radius");
	}
	
	@Override
	protected void connectTextureUnits() {
		super.loadInt(location_texture, 0);
	}
	
	public void loadLight(Light light) {
		super.loadVector2f(location_lightPos, light.getPos());
		super.loadVector3f(location_lightColor, light.getColor());
		super.loadVector3f(location_intensity, light.getIntensity());
		super.loadFloat(location_radius, light.getRadius());
	}
	
	
	public void loadTransformationMatrix(Matrix4f matrix) {
		super.loadMatrix(location_tranform, matrix);
		
	}
	
	public void loadProjectionMatrix(Matrix4f projection) {
		super.loadMatrix(location_projection, projection);
	}
	
	public void isRenderingBg(boolean value) {
		super.loadBoolean(location_bg, value);
	}
}
