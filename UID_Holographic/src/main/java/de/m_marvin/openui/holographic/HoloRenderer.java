package de.m_marvin.openui.holographic;

import java.awt.Color;

import de.m_marvin.gframe.buffers.BufferBuilder;
import de.m_marvin.gframe.buffers.IBufferSource;
import de.m_marvin.gframe.resources.defimpl.ResourceLocation;
import de.m_marvin.gframe.translation.PoseStack;
import de.m_marvin.openui.core.UIRenderMode;
import de.m_marvin.openui.flatmono.UIRenderModes;

public class HoloRenderer {

	public static void drawFrame(int x, int y, int w, int h, Color frameColor, IBufferSource<UIRenderMode<ResourceLocation>> bufferSource, PoseStack matrixStack) {
		matrixStack.push();
		matrixStack.translate(x, y, 0);
		drawFrame(w, h, frameColor, bufferSource, matrixStack);
		matrixStack.pop();
	}
	
	public static void drawFrame(int w, int h, Color frameColor, IBufferSource<UIRenderMode<ResourceLocation>> bufferSource, PoseStack matrixStack) {

		float r = frameColor.getRed() / 255F;
		float g = frameColor.getGreen() / 255F;
		float b = frameColor.getBlue() / 255F;
		float a = frameColor.getAlpha() / 255F;

		float cs = 5;
		
		BufferBuilder buffer = bufferSource.startBuffer(UIRenderModes.lines(1));
		
		buffer.vertex(matrixStack, 0, 0, 0).color(r, g, b, a).endVertex();
		buffer.vertex(matrixStack, w, 0, 0).color(r, g, b, a).endVertex();
		buffer.vertex(matrixStack, 0, h, 0).color(r, g, b, a).endVertex();
		buffer.vertex(matrixStack, w, h, 0).color(r, g, b, a).endVertex();
		buffer.vertex(matrixStack, 0, 0, 0).color(r, g, b, a).endVertex();
		buffer.vertex(matrixStack, 0, h, 0).color(r, g, b, a).endVertex();
		buffer.vertex(matrixStack, w, 0, 0).color(r, g, b, a).endVertex();
		buffer.vertex(matrixStack, w, h, 0).color(r, g, b, a).endVertex();
		
		buffer.end();
		
		buffer = bufferSource.startBuffer(UIRenderModes.lines(3));

		buffer.vertex(matrixStack, 0, 0, 0).color(r, g, b, a).endVertex();
		buffer.vertex(matrixStack, cs, 0, 0).color(r, g, b, a).endVertex();
		buffer.vertex(matrixStack, 0, 0, 0).color(r, g, b, a).endVertex();
		buffer.vertex(matrixStack, 0, cs, 0).color(r, g, b, a).endVertex();

		buffer.vertex(matrixStack, w, 0, 0).color(r, g, b, a).endVertex();
		buffer.vertex(matrixStack, w -cs, 0, 0).color(r, g, b, a).endVertex();
		buffer.vertex(matrixStack, w, 0, 0).color(r, g, b, a).endVertex();
		buffer.vertex(matrixStack, w, cs, 0).color(r, g, b, a).endVertex();

		buffer.vertex(matrixStack, 0, h, 0).color(r, g, b, a).endVertex();
		buffer.vertex(matrixStack, cs, h, 0).color(r, g, b, a).endVertex();
		buffer.vertex(matrixStack, 0, h, 0).color(r, g, b, a).endVertex();
		buffer.vertex(matrixStack, 0, h - cs, 0).color(r, g, b, a).endVertex();

		buffer.vertex(matrixStack, w, h, 0).color(r, g, b, a).endVertex();
		buffer.vertex(matrixStack, w -cs, h, 0).color(r, g, b, a).endVertex();
		buffer.vertex(matrixStack, w, h, 0).color(r, g, b, a).endVertex();
		buffer.vertex(matrixStack, w, h - cs, 0).color(r, g, b, a).endVertex();
		
		buffer.end();
		
	}
	
}
