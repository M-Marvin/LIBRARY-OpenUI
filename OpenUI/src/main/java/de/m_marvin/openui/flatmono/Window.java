package de.m_marvin.openui.flatmono;

import de.m_marvin.openui.core.UIResourceFolder;
import de.m_marvin.openui.core.window.UIWindow;
import de.m_marvin.renderengine.resources.defimpl.ResourceLocation;

public abstract class Window extends UIWindow<ResourceLocation, UIResourceFolder> {

	public static final String NAMESPACE = "flatmono";
	
	public Window(String windowName) {
		super(UIResourceFolder.SHADERS, UIResourceFolder.TEXTURES, windowName);
	}
	
}
