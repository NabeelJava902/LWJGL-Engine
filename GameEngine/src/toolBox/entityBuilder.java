package toolBox;

import org.lwjgl.util.vector.Vector3f;

import entities.Entity;
import entities.Player;
import models.RawModel;
import models.TexturedModel;
import objConverter.ModelData;
import objConverter.OBJFileLoader;
import renderEngine.Loader;
import textures.ModelTexture;

public class entityBuilder {

	private static Loader loader = new Loader();
	
	public static Entity buildOBJEntity(String objModel, String objTexture, int shineDamper, int reflectivity, boolean hasTransparency,
			Vector3f position, float rotX, float rotY, float rotZ, float scale) {
		
		ModelData data = OBJFileLoader.loadOBJ(objModel);
		RawModel model = loader.loadToVAO(data.getVertices(), data.getTextureCoords(), data.getNormals(), data.getIndices());
		TexturedModel texturedModel = new TexturedModel(model, new ModelTexture(loader.loadTexture(objTexture)));
		texturedModel.getTexture().setReflectivity(reflectivity);
		texturedModel.getTexture().setShineDamper(shineDamper);
		texturedModel.getTexture().setHasTransparency(hasTransparency);
		if(hasTransparency) {
			texturedModel.getTexture().setUseFakeLighting(true);
		}
		return new Entity(texturedModel, position, rotX, rotY, rotZ, scale);
	}
	
	public static Player buildOBJPlayer(String objModel, String objTexture, int shineDamper, int reflectivity, boolean hasTransparency,
			Vector3f position, float rotX, float rotY, float rotZ, float scale) {
		
		ModelData data = OBJFileLoader.loadOBJ(objModel);
		RawModel model = loader.loadToVAO(data.getVertices(), data.getTextureCoords(), data.getNormals(), data.getIndices());
		TexturedModel texturedModel = new TexturedModel(model, new ModelTexture(loader.loadTexture(objTexture)));
		texturedModel.getTexture().setReflectivity(reflectivity);
		texturedModel.getTexture().setShineDamper(shineDamper);
		texturedModel.getTexture().setHasTransparency(hasTransparency);
		if(hasTransparency) {
			texturedModel.getTexture().setUseFakeLighting(true);
		}
		return new Player(texturedModel, position, rotX, rotY, rotZ, scale);
	}
}
