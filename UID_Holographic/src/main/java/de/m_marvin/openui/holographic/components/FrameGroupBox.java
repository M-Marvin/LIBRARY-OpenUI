package de.m_marvin.openui.holographic.components;

import java.awt.Color;

import de.m_marvin.gframe.buffers.defimpl.SimpleBufferSource;
import de.m_marvin.gframe.resources.defimpl.ResourceLocation;
import de.m_marvin.gframe.translation.PoseStack;
import de.m_marvin.openui.core.UIRenderMode;
import de.m_marvin.openui.flatmono.UtilRenderer;
import de.m_marvin.openui.flatmono.components.GroupBox;
import de.m_marvin.openui.holographic.HoloRenderer;

public class FrameGroupBox extends GroupBox {

	public FrameGroupBox() {
		this(new Color(0, 0, 0, 128));
	}
	
	public FrameGroupBox(Color backgroundColor) {
		this.backgroundColor = backgroundColor;
	}
	
	@Override
	public void drawBackground(SimpleBufferSource<ResourceLocation, UIRenderMode<ResourceLocation>> bufferSource, PoseStack matrixStack) {
		UtilRenderer.drawRectangle(this.size.x, this.size.y, this.backgroundColor, bufferSource, matrixStack);
	}
	
	@Override
	public void drawForeground(SimpleBufferSource<ResourceLocation, UIRenderMode<ResourceLocation>> bufferSource, PoseStack matrixStack) {
		HoloRenderer.drawFrame(this.size.x, this.size.y, Color.white, bufferSource, matrixStack);
	}
	
}
