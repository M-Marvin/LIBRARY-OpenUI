package de.m_marvin.openui.holographic.components;

import java.awt.Color;

import de.m_marvin.gframe.buffers.defimpl.SimpleBufferSource;
import de.m_marvin.gframe.resources.defimpl.ResourceLocation;
import de.m_marvin.gframe.translation.PoseStack;
import de.m_marvin.gframe.windows.Window;
import de.m_marvin.openui.core.UIRenderMode;
import de.m_marvin.openui.core.components.Component;
import de.m_marvin.openui.flatmono.UtilRenderer;
import de.m_marvin.openui.holographic.HoloRenderer;
import de.m_marvin.univec.impl.Vec2d;
import de.m_marvin.univec.impl.Vec2i;

public class GrabBar extends Component<ResourceLocation> {
	
	protected final Window window;
	protected Vec2d grabOffset = null;
	protected Color color = new Color(255, 0, 0, 128);
	
	protected static final int FILL_MARGIN = 5;
	
	public GrabBar(Window window) {
		this.window = window;
		this.setSizeMin(new Vec2i(20, 20));
		this.fixSize();
	}
	
	public GrabBar(Window window, Color color) {
		this(window);
		this.color = color;
	}
	
	public void setColor(Color color) {
		this.color = color;
	}
	
	public Color getColor() {
		return color;
	}
	
	@Override
	public void drawBackground(SimpleBufferSource<ResourceLocation, UIRenderMode<ResourceLocation>> bufferSource, PoseStack matrixStack) {
		
		HoloRenderer.drawFrame(this.size.x, this.size.y, Color.white, bufferSource, matrixStack);
		UtilRenderer.drawRectangle(FILL_MARGIN, FILL_MARGIN, this.size.x - FILL_MARGIN * 2, this.size.y - FILL_MARGIN * 2, this.color, bufferSource, matrixStack);
		
		super.drawBackground(bufferSource, matrixStack);
	}
	
	@Override
	protected void cursorMove(Vec2d position, boolean entered, boolean leaved) {
		if (this.grabOffset != null) {
			Vec2i windowPos = this.window.getPosition();
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
