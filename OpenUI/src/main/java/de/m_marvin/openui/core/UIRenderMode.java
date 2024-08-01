package de.m_marvin.openui.core;

import de.m_marvin.gframe.buffers.defimpl.IRenderMode;
import de.m_marvin.gframe.resources.IResourceProvider;
import de.m_marvin.gframe.shaders.ShaderInstance;
import de.m_marvin.gframe.vertices.RenderPrimitive;
import de.m_marvin.gframe.vertices.VertexFormat;
import de.m_marvin.openui.core.window.UIContainer;

public record UIRenderMode<R extends IResourceProvider<R>>(
		RenderPrimitive primitive, 
		VertexFormat vertexFormat, 
		R shader,
		IUIRenderModeSetup<R> setupRenderMode
) implements IRenderMode {
	
	public void setupRenderMode(ShaderInstance shader, UIContainer<R> container) {
		this.setupRenderMode().setup(shader, container);
	}
	
	@FunctionalInterface
	public static interface IUIRenderModeSetup<R extends IResourceProvider<R>> {
		public void setup(ShaderInstance shader, UIContainer<R> container);
	}
	
}
