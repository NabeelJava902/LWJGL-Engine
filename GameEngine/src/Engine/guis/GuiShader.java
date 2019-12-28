package Engine.guis;

import org.lwjgl.util.vector.Matrix4f;

import Engine.shaders.ShaderProgram;

public class GuiShader extends ShaderProgram {
	
	private static final String VERTEX_FILE = "GameEngine/src/Engine/guis/guiVertexShader.txt";
	private static final String FRAGMENT_FILE = "GameEngine/src/Engine/guis/guiFragmentShader.txt";

	private int location_transformationMatrix;
	
	public GuiShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
		
	}

	@Override
	protected void getAllUniformLocations() {
		location_transformationMatrix = super.getUniformLocation("transformationMatrix");
	}

	@Override
	protected void bindAttributes() {
		super.bindAttribute(0,  "position");
	}

	public void loadTransformationMatrix(Matrix4f matrix) {
		super.loadMatrix(location_transformationMatrix, matrix);
	}
}
