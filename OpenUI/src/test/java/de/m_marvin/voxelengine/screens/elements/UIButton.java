package de.m_marvin.voxelengine.screens.elements;

import java.awt.Color;

import de.m_marvin.gframe.buffers.BufferBuilder;
import de.m_marvin.gframe.resources.defimpl.ResourceLocation;
import de.m_marvin.gframe.textures.maps.AbstractTextureMap;
import de.m_marvin.gframe.translation.PoseStack;
import de.m_marvin.univec.impl.Vec2i;
import de.m_marvin.voxelengine.VoxelEngine;
import de.m_marvin.voxelengine.deprecated.UIButtonElement;
import de.m_marvin.voxelengine.rendering.BufferSource;
import de.m_marvin.voxelengine.rendering.RenderType;

public class UIButton extends UIButtonElement {
	
	public static final Color TEXT_COLOR_WHITE = new Color(255, 255, 255, 255);
	public static final Color BUTTON_COLOR_BLACK = new Color(0, 0, 0, 200);
	public static final Color BUTTON_COLOR_RED = new Color(0, 0, 0, 200);
		
	public UIButton(Vec2i position, Vec2i size, String title, Color colorBack, Color colorFront) {
		super(position, size, title, colorBack, colorFront);
	}
	
	@Override
	public void onHover(HoverEvent event, Vec2i coursorPosition) {
		
		if (VoxelEngine.getInstance().getInputHandler().isBindingTyped("misc.click.primary")) {
			
			System.out.println("CLICK");
			
		}
		
	}
	
	@Override
	public void draw(PoseStack poseStack) {
				
		float xl = position.x;
		float yl = position.y;
		float xh = xl + size.x;
		float yh = yl + size.y;
		float r = colorBack.getRed() / 255F;
		float g = colorBack.getGreen() / 255F;
		float b = colorBack.getBlue() / 255F;
		float a = colorBack.getAlpha() / 255F;
		
		BufferSource bufferSource = VoxelEngine.getInstance().getGameRenderer().getBufferSource();
		
		ResourceLocation textureLoc = new ResourceLocation(VoxelEngine.NAMESPACE, "screen/test");
		AbstractTextureMap<ResourceLocation> texture = VoxelEngine.getInstance().getTextureLoader().getTexture(textureLoc);
		
		BufferBuilder buffer = bufferSource.startBuffer(RenderType.screenTextured(textureLoc));
		
		buffer.vertex(poseStack, xl, yl).color(r, g, b, a).uv(texture, 0, 0).endVertex();
		buffer.vertex(poseStack, xh, yl).color(r, g, b, a).uv(texture, 1, 0).endVertex();
		buffer.vertex(poseStack, xh, yh).color(r, g, b, a).uv(texture, 1, 1).endVertex();
		buffer.vertex(poseStack, xl, yh).color(r, g, b, a).uv(texture, 0, 1).endVertex();
		buffer.end();
	}

}
