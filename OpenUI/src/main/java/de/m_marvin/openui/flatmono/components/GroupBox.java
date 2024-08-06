package de.m_marvin.openui.flatmono.components;

import java.awt.Color;

import de.m_marvin.gframe.buffers.defimpl.SimpleBufferSource;
import de.m_marvin.gframe.resources.defimpl.ResourceLocation;
import de.m_marvin.gframe.translation.PoseStack;
import de.m_marvin.openui.core.UIRenderMode;
import de.m_marvin.openui.core.components.Compound;
import de.m_marvin.openui.flatmono.UtilRenderer;

public class GroupBox extends Compound<ResourceLocation> {
	
	protected Color backgroundColor;
	protected Color frameColor;
	protected int frameWidth = 1;
	
	public GroupBox() {
		this(Color.WHITE);
	}
	
	public GroupBox(Color frameColor) {
		this(frameColor, Color.BLACK);
	}
	
	public GroupBox(Color frameColor, Color backgroundColor) {
		this.frameColor = frameColor;
		this.backgroundColor = backgroundColor;
	}
	
	public Color getFrameColor() {
		return frameColor;
	}
	
	public Color getBackgroundColor() {
		return backgroundColor;
	}
	
	public void setFrameWidth(int frameWidth) {
		this.frameWidth = frameWidth;
	}
	
	public int getFrameWidth() {
		return frameWidth;
	}
	
	@Override
	public void drawBackground(SimpleBufferSource<ResourceLocation, UIRenderMode<ResourceLocation>> bufferSource, PoseStack matrixStack) {
		
		UtilRenderer.drawRectangle(this.size.x, this.size.y, this.backgroundColor, bufferSource, matrixStack);
		
	}
	
	@Override
	public void drawForeground(SimpleBufferSource<ResourceLocation, UIRenderMode<ResourceLocation>> bufferSource, PoseStack matrixStack) {
		
		UtilRenderer.drawFrame(this.size.x, this.size.y, this.frameWidth, this.frameColor, bufferSource, matrixStack);
		
	}
	
}
