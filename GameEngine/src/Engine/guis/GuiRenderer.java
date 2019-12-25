package Engine.guis;

import java.util.List;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;

import Engine.models.RawModel;
import Engine.render.Loader;
import Engine.toolBox.Maths;

public class GuiRenderer {
	
	private final RawModel quad;
	private GuiShader guiShader;
	
	public GuiRenderer(Loader loader) {
		float[] positions = {-1, 1, -1, -1, 1, 1, 1, -1};
		quad = loader.loadToVAO(positions);
		guiShader = new GuiShader();
	}
	
	public void render(List<GuiTexture> guis) {
		guiShader.start();
		GL30.glBindVertexArray(quad.getVaoID());
		GL20.glEnableVertexAttribArray(0);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		for(GuiTexture gui : guis) {
			GL13.glActiveTexture(GL13.GL_TEXTURE0);
			GL11.glBindTexture(GL11.GL_TEXTURE_2D,  gui.getTextureID());
			Matrix4f transformationMatrix = Maths.createTransformationMatrix(gui.getPosition(), gui.getRotX(), gui.getRotY(), gui.getRotZ(), gui.getScale());
			guiShader.loadTransformationMatrix(transformationMatrix);
			GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP,  0,  quad.getVertexCount());
		}
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glDisable(GL11.GL_BLEND);
		GL20.glDisableVertexAttribArray(0);
		GL30.glBindVertexArray(0);
		guiShader.stop();
	}
	
	public void clean() {
		guiShader.clean();
	}
}
