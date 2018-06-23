package gui;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import abilities.TimeFreeze;
import engine.Loader;
import engine.Main;
import engine.Material;
import shaders.GUIShader;
import toolbox.Maths;

public class GUIRenderer {
	
	private final Material quad;
	
	private GUIShader shader;
	
	public GUIRenderer(Loader loader, GUIShader shader) {
		float[] vertices = {-1,1,-1,-1,1,1,1,-1};
		this.quad = loader.loadToVAO(vertices);
		this.shader = shader;
	}
	
	public void render(ArrayList<GUI> guis) {
		shader.start();
		GL30.glBindVertexArray(quad.getVaoID());
		{
			GL20.glEnableVertexAttribArray(0);
			{
				for(GUI gui : guis) {
					shader.loadTransformationMatrix(Maths.createTransformationMatrix(gui.getPos(), gui.getScale()));
					
					if(gui.hasTexture()) {
						GL13.glActiveTexture(GL13.GL_TEXTURE0);
						GL11.glBindTexture(GL11.GL_TEXTURE_2D, gui.getTexID());
						
					}else if(gui.getID() == GuiID.healthbar){
						shader.renderingBar(true);
						float x = (gui.getAttachedTo().health.getCurrentHealth()/gui.getAttachedTo().health.getMaxHealth())*100; //converts health into value between 0 and 100
						shader.loadBarColor(Maths.hslToRgb(x*(1.2f/360), 1, 0.5f));
						
					}else if(gui.getID() == GuiID.timeFreezeBar) {
						shader.renderingBar(true);
						TimeFreeze ability = (TimeFreeze)Main.creator.getPlayer().abilityComponent.getCurrentAbility();
						float x = (ability.getCurrentAmount()/ability.getMaxAmount())*100;
						shader.loadBarColor(Maths.hslToRgb(x*(2.2f/360), 1, 0.5f));
					}
					
					GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, quad.getVertexCount());
					shader.renderingBar(false);
				}
			}
			GL20.glDisableVertexAttribArray(0);
		}
		GL30.glBindVertexArray(0);
		shader.stop();
	}
}
