package de.m_marvin.openui.holographic.components;

import java.awt.Color;

import de.m_marvin.gframe.buffers.defimpl.SimpleBufferSource;
import de.m_marvin.gframe.resources.defimpl.ResourceLocation;
import de.m_marvin.gframe.translation.PoseStack;
import de.m_marvin.gframe.windows.Window;
import de.m_marvin.openui.core.UIRenderMode;
import de.m_marvin.openui.core.components.Compound;
import de.m_marvin.openui.flatmono.UtilRenderer;

public class ResizableGroupBox extends Compound<ResourceLocation> {
	
	protected final Window window;
	
	public ResizableGroupBox(Window window) {
		this.window = window;
	}
	
	@Override
	public void drawBackground(SimpleBufferSource<ResourceLocation, UIRenderMode<ResourceLocation>> bufferSource, PoseStack matrixStack) {
		
		UtilRenderer.renderFrame(this.size.x, this.size.y, 2, Color.WHITE, bufferSource, matrixStack);
		
		super.drawBackground(bufferSource, matrixStack);
	}
	
}
