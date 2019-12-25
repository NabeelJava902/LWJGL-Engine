package guis;

import org.lwjgl.util.vector.Vector2f;

public class GuiTexture {
	
	private int textureID;
	private Vector2f position;
	private Vector2f scale;
	private float rotX, rotY, rotZ;
	
	public GuiTexture(int textureID, Vector2f position, Vector2f scale, float rotX, float rotY, float rotZ) {
		this.textureID = textureID;
		this.position = position;
		this.scale = scale;
		this.rotX = rotX;
		this.rotY = rotY;
		this.rotZ = rotZ;
	}

	public int getTextureID() {
		return textureID;
	}

	public Vector2f getPosition() {
		return position;
	}

	public Vector2f getScale() {
		return scale;
	}

	public float getRotX() {
		return rotX;
	}

	public void setRotX(float rotX) {
		this.rotX = rotX;
	}

	public float getRotY() {
		return rotY;
	}

	public void setRotY(float rotY) {
		this.rotY = rotY;
	}

	public float getRotZ() {
		return rotZ;
	}

	public void setRotZ(float rotZ) {
		this.rotZ = rotZ;
	}

	public void setPosition(Vector2f position) {
		this.position = position;
	}

	public void setScale(Vector2f scale) {
		this.scale = scale;
	}
}
