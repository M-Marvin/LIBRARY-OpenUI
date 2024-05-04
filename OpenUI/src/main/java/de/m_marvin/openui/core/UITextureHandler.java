package de.m_marvin.openui.core;

import de.m_marvin.renderengine.resources.IResourceProvider;
import de.m_marvin.renderengine.resources.ISourceFolder;
import de.m_marvin.renderengine.textures.TextureLoader;

public class UITextureHandler {
	
	public static <R extends IResourceProvider<R>, S extends ISourceFolder> void ensureSingleTexturesLoaded(TextureLoader<R, S> textureLoader, R texture) {
		if (!textureLoader.getTextureMapNames().contains(texture)) {
			R textureFolder = texture.getParent();
			textureLoader.buildSingleMapsFromTextures(textureFolder, 10);
		}
	}
	
	public static <R extends IResourceProvider<R>, S extends ISourceFolder> void ensureAtlasTexturesLoaded(TextureLoader<R, S> textureLoader, R texture, R atlasName, boolean loadInterpolated, boolean gammaCorrect) {
		if (!textureLoader.getTextureMapNames().contains(texture)) {
			R textureFolder = texture.getParent();
			textureLoader.buildAtlasMapFromTextures(textureFolder, atlasName, false, loadInterpolated, 10, gammaCorrect);
		}
	}
	
}
