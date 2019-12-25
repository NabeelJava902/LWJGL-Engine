package Engine.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import Engine.entities.Camera;
import Engine.entities.Entity;
import Engine.entities.Light;
import Engine.entities.Player;
import Engine.guis.GuiRenderer;
import Engine.guis.GuiTexture;
import Engine.models.RawModel;
import Engine.models.TexturedModel;
import Engine.objConverter.ModelData;
import Engine.objConverter.OBJFileLoader;
import Engine.render.DisplayManager;
import Engine.render.Loader;
import Engine.render.MasterRenderer;
import Engine.terrains.Terrain;
import Engine.textures.ModelTexture;
import Engine.textures.TerrainTexture;
import Engine.textures.TerrainTexturePack;
import Engine.toolBox.entityBuilder;

public class MainGameLoop {
	
	private static Terrain terrain, terrain2, terrain3, terrain4;

	public static void main(String[] args) {
		DisplayManager.createDisplay();
		
		MasterRenderer renderer = new MasterRenderer();
		Loader loader = new Loader();
		
		//**********TERRAIN STUFF**********
		
		TerrainTexture backgroundTexture = new TerrainTexture(loader.loadTexture("multiTextures/grass"));
		TerrainTexture rTexture = new TerrainTexture(loader.loadTexture("multiTextures/mud"));
		TerrainTexture gTexture = new TerrainTexture(loader.loadTexture("multiTextures/rock"));
		TerrainTexture bTexture = new TerrainTexture(loader.loadTexture("multiTextures/path"));
		TerrainTexture blendMap = new TerrainTexture(loader.loadTexture("multiTextures/blendMap2"));
		
		TerrainTexturePack texturePack = new TerrainTexturePack(backgroundTexture, rTexture, gTexture, bTexture);
		
		terrain = new Terrain(0, 0, loader, texturePack, blendMap, "heightMap");
		terrain2 = new Terrain(0, 1, loader, texturePack, blendMap, "heightMap");
		terrain3 = new Terrain(1, 1, loader, texturePack, blendMap, "heightMap");
		terrain4 = new Terrain(1, 0, loader, texturePack, blendMap, "heightMap");
		Terrain currentTerrain;
		
		//*********************************
		
		Light light = new Light(new Vector3f(20000,40000,20000),new Vector3f(1,1,1));
		
		Entity dragon = entityBuilder.buildOBJEntity("dragon/dragon", "dragon/white", 10, 1, false, new Vector3f(300, 50, 300), 0, 0, 0, 2);
		
		Entity stall = entityBuilder.buildOBJEntity("stall/stall", "stall/stallTexture", 10, 1, false, new Vector3f(300, checkTerrain(300, 150).getHeightOfTerrain(300, 150), 150), 0, 140, 0, 2);
		
		Random random = new Random();
		Entity[] lowPolyTrees = new Entity[50];
		for(int i=0; i<lowPolyTrees.length; i++) {
			float x = (float)random.nextInt(1500);
			float z = (float)random.nextInt(1500);
			currentTerrain = checkTerrain(x, z);
			float y = currentTerrain.getHeightOfTerrain(x, z);
			lowPolyTrees[i] = entityBuilder.buildOBJEntity("tree2/lowPolyTree", "tree2/lowPolytreeTexture", 10, 1, false, new Vector3f(x, y, z), 0, 0, 0, 1);
		}
		
		Entity tallGrass[] = new Entity[500];
		for(int i=0; i<tallGrass.length; i++) {
			float x = (float)random.nextInt(1500);
			float z = (float)random.nextInt(1500);
			currentTerrain = checkTerrain(x, z);
			float y = currentTerrain.getHeightOfTerrain(x, z);
			tallGrass[i] = entityBuilder.buildOBJEntity("tallGrass/tallGrassModel", "tallGrass/tallGrassTexture", 10, 1, true, new Vector3f(x, y, z), 0, 0, 0, 2);
		}
		
		Entity ferns[] = new Entity[250];
		ModelTexture fernTextureAtlas = new ModelTexture(loader.loadTexture("fern/fernAtlas"));
		fernTextureAtlas.setNumberOfRows(2);
		ModelData fernData = OBJFileLoader.loadOBJ("fern/fern");
		RawModel fernModel = loader.loadToVAO(fernData.getVertices(), fernData.getTextureCoords(), fernData.getNormals(), fernData.getIndices());
		TexturedModel texturedFern = new TexturedModel(fernModel, fernTextureAtlas);
		for(int i=0; i<ferns.length; i++) {
			float x = (float)random.nextInt(1500);
			float z = (float)random.nextInt(1500);
			currentTerrain = checkTerrain(x, z);
			float y = currentTerrain.getHeightOfTerrain(x, z);
			ferns[i] = new Entity(texturedFern, random.nextInt(4), new Vector3f(x, y, z), 0, 0, 0, 1);
		}
		
		Entity[] trees = new Entity[50];
		for(int i=0; i<trees.length; i++) {
			float x = (float)random.nextInt(1500);
			float z = (float)random.nextInt(1500);
			currentTerrain = checkTerrain(x, z);
			float y = currentTerrain.getHeightOfTerrain(x, z);
			trees[i] = entityBuilder.buildOBJEntity("tree/tree", "tree/treeTexture", 10, 1, false, new Vector3f(x, y, z), 0, 0, 0, 9);
		}
		
		Entity[] lamps = new Entity[50];
		for(int i=0; i<lamps.length; i++) {
			float x = (float)random.nextInt(1500);
			float z = (float)random.nextInt(1500);
			currentTerrain = checkTerrain(x, z);
			float y = currentTerrain.getHeightOfTerrain(x, z);
			lamps[i] = entityBuilder.buildOBJEntity("lamp/lamp", "lamp/lampTexture", 10, 1, false, new Vector3f(x, y, z), 0, 0, 0, 2);
		}
		
		Player player = entityBuilder.buildOBJPlayer("player/person", "player/humanTexture", 10, 1, false, new Vector3f(300, checkTerrain(300, 550).getHeightOfTerrain(300, 550), 550), 0, 100, 0, 1);
		Camera camera = new Camera(player);		
		
		Entity box = entityBuilder.buildOBJEntity("box/box", "box/boxTexture", 10, 1, false, new Vector3f(300, 0, 300), 0, 0, 0, 9);
		
		Entity[] rocks = new Entity[50];
		for(int i=0; i<rocks.length; i++) {
			float x = (float)random.nextInt(1500);
			float z = (float)random.nextInt(1500);
			currentTerrain = checkTerrain(x, z);
			float y = currentTerrain.getHeightOfTerrain(x, z);
			rocks[i] = entityBuilder.buildOBJEntity("rock/rock", "rock/rockTexture", 100, 0, false, new Vector3f(x, y, z), 0, 0, 0, 3);
		}
		
		Entity moon = entityBuilder.buildOBJEntity("moon/moon", "moon/moonTexture", 10, 1, false, new Vector3f(300, checkTerrain(300, 550).getHeightOfTerrain(300, 550)+20, 550), 0, 100, 0, 1);
		
		List<GuiTexture> guis = new ArrayList<GuiTexture>();
		GuiTexture gui = new GuiTexture(loader.loadTexture("guis/healthBarGui"), new Vector2f(-0.6f, 0.9f), new Vector2f(0.4f, 0.4f), 0, 0, 0);
		guis.add(gui);
		
		GuiRenderer guiRenderer = new GuiRenderer(loader);
		
		while(!Display.isCloseRequested()) {
			currentTerrain = checkTerrain(player.getPosition().x, player.getPosition().z);
			moon.setPosition(new Vector3f(player.getPosition().x, player.getPosition().y+20, player.getPosition().z));
			dragon.increaseRotation(0, 1, 0);
			camera.move();
			player.move(currentTerrain);
			
			renderer.processEntity(player);
			renderer.processTerrain(terrain);
			renderer.processTerrain(terrain2);
			renderer.processTerrain(terrain3);
			renderer.processTerrain(terrain4);
			renderer.processEntity(dragon);
			renderer.processEntity(stall);
			renderer.processEntity(box);
			
			for(Entity tree : lowPolyTrees) {
				renderer.processEntity(tree);
			}
			
			for(Entity grass : tallGrass) {
				renderer.processEntity(grass);
			}
			
			for(Entity fern : ferns) {
				renderer.processEntity(fern);
			}
			
			for(Entity tree : trees) {
				renderer.processEntity(tree);
			}
			
			for(Entity lamp : lamps) {
				renderer.processEntity(lamp);
			}
			
			for(Entity rock : rocks) {
				renderer.processEntity(rock);
			}
			
			renderer.render(light,  camera);
			guiRenderer.render(guis);
			DisplayManager.updateDisplay();
		}
		
		renderer.cleanUp();
		guiRenderer.clean();
		loader.cleanData();
		DisplayManager.closeDisplay();
	}
	
	private static Terrain checkTerrain(float x, float z) {
		Terrain terrainUnderPlayer = null;
		
		if(z <= 800 && x <= 800) {
			terrainUnderPlayer = terrain;
		}else if(z >= 800 && x <= 800) {
			terrainUnderPlayer = terrain2;
		}else if(z >= 800 && x >= 800) {
			terrainUnderPlayer = terrain3;
		}else if(z <= 800 && x >= 800) {
			terrainUnderPlayer = terrain4;
		}
		return terrainUnderPlayer;
	}
}
