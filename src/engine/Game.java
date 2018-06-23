package engine;

import java.util.ArrayList;

import components.Component;
import components.MaterialComponent;
import gui.GUI;
import gui.GUIRenderer;
import shaders.StaticShader;

public class Game {

	public static ArrayList<Component> updateComponents = new ArrayList<Component>();
	public static ArrayList<Component> renderComponents = new ArrayList<Component>();
	public static ArrayList<GUI> guis = new ArrayList<GUI>();
	
	private Renderer renderer;
	private GUIRenderer guiRenderer;
	private StaticShader shader;	
	
	public Game(Renderer renderer, GUIRenderer guiRenderer, StaticShader shader) {
		this.renderer = renderer;
		this.guiRenderer = guiRenderer;
		this.shader = shader;
	}
	public void update() {
		///System.out.println("number of components: "+(updateComponents.size()+renderComponents.size()));
		for(int i=0;i<updateComponents.size();i++) {
			Component c = updateComponents.get(i);
			if(c.toRemove) {
				updateComponents.remove(c);
				continue;
			}
			
			if(c.getAttachedTo().isRealized())
				c.update();	
		}
		
	}
	
	public void renderEntities() {
		shader.start();
		
		if(Main.creator.getLight() != null)
			shader.loadLight(Main.creator.getLight());
		
		shader.isRenderingBg(true);
		renderer.render(Main.creator.getBg());
		shader.isRenderingBg(false);
		
		for(int i=0;i<renderComponents.size();i++) {
			Component c = renderComponents.get(i);
			if(c.toRemove) {
				renderComponents.remove(c);
				continue;
			}
			
			if(c.getAttachedTo().isRealized())
				renderer.render((MaterialComponent) c, shader);
		}
		shader.stop();
	}
	
	public void renderGUIs() {
		guiRenderer.render(guis);
	}
}
