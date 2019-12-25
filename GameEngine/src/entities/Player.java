package entities;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;

import models.TexturedModel;
import renderEngine.DisplayManager;
import terrains.Terrain;

public class Player extends Entity{
	
	private static final float RUN_SPEED = 50; //units per second
	private static final float TURN_SPEED = 160; //degrees per second
	private static final float GRAVITY = -50;
	private static final float JUMP_HEIGHT = 30;
	
	private float currentSpeed = 0;
	private float currentTurnSpeed = 0;
	private float upwardsSpeed = 0;
	
	private boolean isInAir = false;

	public Player(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale) {
		super(model, position, rotX, rotY, rotZ, scale);
		
	}

	public void move(Terrain terrain) {
		checkInputs();
		super.increaseRotation(0, currentTurnSpeed * DisplayManager.getFrameTimeSeconds(), 0);
		float distance = currentSpeed * DisplayManager.getFrameTimeSeconds();
		float dx = (float) (distance * Math.sin(Math.toRadians(super.getRotY())));
		float dz = (float) (distance * Math.cos(Math.toRadians(super.getRotY())));
		super.increasePosition(dx, 0, dz);
		upwardsSpeed += GRAVITY * DisplayManager.getFrameTimeSeconds();
		super.increasePosition(0, upwardsSpeed * DisplayManager.getFrameTimeSeconds(), 0);
		float terrainHeight = terrain.getHeightOfTerrain(super.getPosition().x, super.getPosition().z);
		if(getPosition().y < terrainHeight) {
			if(this.getPosition().x > 1600 || this.getPosition().x < 0 || this.getPosition().z > 1600 || this.getPosition().z < 0) {
				upwardsSpeed = -60;
			}else {
				upwardsSpeed = 0;
				isInAir = false;
				super.getPosition().y = terrainHeight;
			}
		}
	}
	
	private void jump() {
		upwardsSpeed = JUMP_HEIGHT;
		
	}
	
	private void checkInputs() {
		if(Keyboard.isKeyDown(Keyboard.KEY_W)) {
			this.currentSpeed = RUN_SPEED;
			if(Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
				this.currentSpeed = RUN_SPEED * 2;
			}
		}else if(Keyboard.isKeyDown(Keyboard.KEY_S)) {
			this.currentSpeed = -RUN_SPEED;
		}else {
			this.currentSpeed = 0;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_D)) {
			this.currentTurnSpeed = -TURN_SPEED;
		}else if(Keyboard.isKeyDown(Keyboard.KEY_A)) {
			this.currentTurnSpeed = TURN_SPEED;
		}else {
			this.currentTurnSpeed = 0;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
			if(!isInAir) {
				jump();
				isInAir = true;
			}
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_MINUS)) {
			this.getPosition().x = 300;
			this.getPosition().z = 550;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_EQUALS)) {
			System.out.println("X: " + this.getPosition().x);
			System.out.println("Z: " + this.getPosition().z + "\n");
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
