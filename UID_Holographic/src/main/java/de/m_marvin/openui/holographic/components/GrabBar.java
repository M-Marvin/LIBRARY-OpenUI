package de.m_marvin.openui.holographic.components;

import java.awt.Color;
import java.awt.Font;

import de.m_marvin.gframe.buffers.defimpl.SimpleBufferSource;
import de.m_marvin.gframe.fontrendering.FontRenderer;
import de.m_marvin.gframe.resources.defimpl.ResourceLocation;
import de.m_marvin.gframe.translation.PoseStack;
import de.m_marvin.gframe.windows.Window;
import de.m_marvin.openui.core.TextRenderer;
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
	protected String title;
	protected Font font = DEFAULT_FONT;
	
	protected static final int FILL_MARGIN = 5;
	
	public GrabBar(Window window, String title) {
		this.window = window;
		this.title = title;
		this.setSizeMin(new Vec2i(60, 20));
		this.setSizeMax(new Vec2i(-1, 20));
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setFont(Font font) {
		this.font = font;
	}
	
	public Font getFont() {
		return font;
	}
	
	public GrabBar(Window window, String title, Color color) {
		this(window, title);
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

		String text = FontRenderer.fitText(this.title, this.font, this.size.x - FILL_MARGIN * 2);
		
		int textWidth = FontRenderer.calculateStringWidth(text, this.font);
		int frame1Width = textWidth + FILL_MARGIN * 2;
		int frame2Width =  (this.size.x - frame1Width) - FILL_MARGIN;
		
		HoloRenderer.drawFrame(frame1Width, this.size.y, Color.white, bufferSource, matrixStack);
		
		if (frame2Width > this.size.y) {

			HoloRenderer.drawFrame(frame1Width + FILL_MARGIN, 0, frame2Width, this.size.y, Color.white, bufferSource, matrixStack);
			UtilRenderer.drawRectangle(frame1Width + FILL_MARGIN * 2, FILL_MARGIN, frame2Width - FILL_MARGIN * 2, this.size.y - FILL_MARGIN * 2, this.color, bufferSource, matrixStack);
			
		}
		
		super.drawBackground(bufferSource, matrixStack);
	}
	
	@Override
	public void drawForeground(SimpleBufferSource<ResourceLocation, UIRenderMode<ResourceLocation>> bufferSource, PoseStack matrixStack) {
		
		String text = FontRenderer.fitText(this.title, this.font, this.size.x - FILL_MARGIN * 2);
		int textWidth = FontRenderer.calculateStringWidth(text, font);
		
		TextRenderer.drawTextCentered(FILL_MARGIN + textWidth / 2, this.size.y / 2, text, font, Color.white, this.container.getActiveTextureLoader(), bufferSource, matrixStack);
		
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
