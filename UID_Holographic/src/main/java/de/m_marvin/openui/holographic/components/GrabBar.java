package de.m_marvin.openui.holographic.components;

import java.awt.Color;

import de.m_marvin.gframe.buffers.defimpl.SimpleBufferSource;
import de.m_marvin.gframe.resources.defimpl.ResourceLocation;
import de.m_marvin.gframe.translation.PoseStack;
import de.m_marvin.gframe.windows.Window;
import de.m_marvin.openui.core.UIRenderMode;
import de.m_marvin.openui.core.components.Component;
import de.m_marvin.openui.flatmono.UtilRenderer;
import de.m_marvin.univec.impl.Vec2d;
import de.m_marvin.univec.impl.Vec2i;

public class GrabBar extends Component<ResourceLocation> {
	
	protected Vec2d grabOffset = null;
	protected final Window window;
	
	public GrabBar(Window window) {
		this.window = window;
	}
	
	@Override
	public void drawBackground(SimpleBufferSource<ResourceLocation, UIRenderMode<ResourceLocation>> bufferSource, PoseStack matrixStack) {
		
		UtilRenderer.renderFrame(this.size.x, this.size.y, 2, Color.WHITE, bufferSource, matrixStack);
		
		super.drawBackground(bufferSource, matrixStack);
	}
	
	@Override
	protected void cursorMove(Vec2d position, boolean entered, boolean leaved) {
		if (this.grabOffset != null) {
			Vec2i windowPos = new Vec2i(this.window.getPosition());
			Vec2i cursorPos = new Vec2i(this.window.getCourserPos());
			this.window.setPosition(windowPos.add(cursorPos).sub(this.grabOffset));
		}
		super.cursorMove(position, entered, leaved);
	}
	
	@Override
	protected void onClicked(int button, boolean pressed, boolean repeated) {
		if (pressed & this.grabOffset == null) {
			this.grabOffset = this.window.getCourserPos();
		} else if (!pressed && this.grabOffset != null) {
			this.grabOffset = null;
		}
	}
	
}
