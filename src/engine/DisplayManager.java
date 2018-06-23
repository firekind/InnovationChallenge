package engine;

import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glViewport;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

public class DisplayManager {
	
	public static final int WIDTH = 1280;
	public static final int HEIGHT = 720;
	
	private static final int FPS_CAP = 60;
	private static long lastFrameTime;
	private static float delta;

	public static void initDisplay() {
		try {
			Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT));
			Display.create();
			Display.setVSyncEnabled(true);
			
			Keyboard.create();
		}catch(LWJGLException e) {
			e.printStackTrace();
		}
		lastFrameTime = getCurrentTime();
	}
	
	public static void initGL() {
		glViewport(0, 0, WIDTH, HEIGHT);
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		glDisable(GL_DEPTH_TEST);
		glClearColor(0,0,0,0);
		
	}
	
	public static void updateDisplay() {
		Display.sync(FPS_CAP);
		Display.update();
		
		long currentFrameTime = getCurrentTime();
		delta = (currentFrameTime - lastFrameTime)/1000f;
		lastFrameTime = currentFrameTime;
	}
	
	public static float getFrameTimeSeconds() {
		return delta;
	}
	
	private static long getCurrentTime() {
		return Sys.getTime()*1000/Sys.getTimerResolution();
	}
	
	public static void closeDisplay() {
		Display.destroy();
	}
	
	
	
}
