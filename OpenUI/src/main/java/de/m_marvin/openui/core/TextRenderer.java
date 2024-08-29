package de.m_marvin.openui.core;

import java.awt.Color;
import java.awt.Font;

import de.m_marvin.gframe.buffers.IBufferSource;
import de.m_marvin.gframe.fontrendering.FontRenderer;
import de.m_marvin.gframe.resources.defimpl.ResourceLocation;
import de.m_marvin.gframe.textures.TextureLoader;
import de.m_marvin.gframe.translation.PoseStack;
import de.m_marvin.openui.flatmono.UIRenderModes;

public class TextRenderer {
	
	public static final ResourceLocation FONT_ATLASES_LOCATION = new ResourceLocation("core", "ui/font");
	
	public static void drawText(String text, Font font, Color color, TextureLoader<ResourceLocation, ?> textureLoader, IBufferSource<UIRenderMode<ResourceLocation>> bufferSource, PoseStack matrixStack) {
		FontRenderer.renderString(text, color, font, FONT_ATLASES_LOCATION, UIRenderModes::texturedCutout, textureLoader, bufferSource, matrixStack);
	}
	
	public static void drawTextCentered(String text, Font font, Color color, TextureLoader<ResourceLocation, ?> textureLoader, IBufferSource<UIRenderMode<ResourceLocation>> bufferSource, PoseStack matrixStack) {
		int width = FontRenderer.calculateStringWidth(text, font);
		int height = FontRenderer.getFontHeight(font);
		matrixStack.push();
		matrixStack.translate(-width / 2, -height / 2, 0);
		drawText(text, font, color, textureLoader, bufferSource, matrixStack);
		matrixStack.pop();
	}

	public static void drawText(int x, int y, String text, Font font, Color color, TextureLoader<ResourceLocation, ?> textureLoader, IBufferSource<UIRenderMode<ResourceLocation>> bufferSource, PoseStack matrixStack) {
		matrixStack.push();
		matrixStack.translate(x, y, 0);
		drawText(text, font, color, textureLoader, bufferSource, matrixStack);
		matrixStack.pop();
	}
	
	public static void drawTextCentered(int x, int y, String text, Font font, Color color, TextureLoader<ResourceLocation, ?> textureLoader, IBufferSource<UIRenderMode<ResourceLocation>> bufferSource, PoseStack matrixStack) {
		matrixStack.push();
		matrixStack.translate(x, y, 0);
		drawTextCentered(text, font, color, textureLoader, bufferSource, matrixStack);
		matrixStack.pop();
	}
		
}
