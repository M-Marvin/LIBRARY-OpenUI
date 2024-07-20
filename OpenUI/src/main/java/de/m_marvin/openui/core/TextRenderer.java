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
	
	public static void renderText(String text, Font font, Color color, TextureLoader<ResourceLocation, ?> textureLoader, IBufferSource<UIRenderMode<ResourceLocation>> bufferSource, PoseStack matrixStack) {
		FontRenderer.renderString(text, color, font, FONT_ATLASES_LOCATION, UIRenderModes::texturedSolid, textureLoader, bufferSource, matrixStack);
	}
	
	public static void renderTextCentered(String text, Font font, Color color, TextureLoader<ResourceLocation, ?> textureLoader, IBufferSource<UIRenderMode<ResourceLocation>> bufferSource, PoseStack matrixStack) {
		int width = FontRenderer.calculateStringWidth(text, font);
		int height = FontRenderer.getFontHeight(font);
		matrixStack.push();
		matrixStack.translate(-width / 2, -height / 2, 0);
		renderText(text, font, color, textureLoader, bufferSource, matrixStack);
		matrixStack.pop();
	}

	public static void renderText(int x, int y, String text, Font font, Color color, TextureLoader<ResourceLocation, ?> textureLoader, IBufferSource<UIRenderMode<ResourceLocation>> bufferSource, PoseStack matrixStack) {
		matrixStack.push();
		matrixStack.translate(x, y, 0);
		renderText(text, font, color, textureLoader, bufferSource, matrixStack);
		matrixStack.pop();
	}
	
	public static void renderTextCentered(int x, int y, String text, Font font, Color color, TextureLoader<ResourceLocation, ?> textureLoader, IBufferSource<UIRenderMode<ResourceLocation>> bufferSource, PoseStack matrixStack) {
		matrixStack.push();
		matrixStack.translate(x, y, 0);
		renderTextCentered(text, font, color, textureLoader, bufferSource, matrixStack);
		matrixStack.pop();
	}
		
}
