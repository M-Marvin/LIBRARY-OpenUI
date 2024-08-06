package de.m_marvin.openui.holographic.components;

import java.awt.Color;
import java.util.Optional;

import org.lwjgl.glfw.GLFW;

import de.m_marvin.gframe.buffers.defimpl.SimpleBufferSource;
import de.m_marvin.gframe.resources.defimpl.ResourceLocation;
import de.m_marvin.gframe.translation.PoseStack;
import de.m_marvin.gframe.windows.Window;
import de.m_marvin.openui.OpenUI;
import de.m_marvin.openui.core.UIRenderMode;
import de.m_marvin.openui.core.components.Component;
import de.m_marvin.openui.flatmono.UtilRenderer;
import de.m_marvin.univec.impl.Vec2d;
import de.m_marvin.univec.impl.Vec2f;
import de.m_marvin.univec.impl.Vec2i;

public class ResizeFrame extends Component<ResourceLocation> {
	
	protected final Window window;
	protected int grabFrameWidth = 20;
	protected int grabSideW = 0;
	protected int grabSideH = 0;
	protected Vec2d grabOffset = null;
	
	public ResizeFrame(Window window) {
		this.window = window;
	}
	
	public void setGrabFrameWidth(int grabFrameWidth) {
		this.grabFrameWidth = grabFrameWidth;
	}
	
	public int getGrabFrameWidth() {
		return grabFrameWidth;
	}
	
	protected static int glfwSizeValue(int val) {
		return val <= 0 ? GLFW.GLFW_DONT_CARE : val;
	}
	
	@Override
	public void setSizeMin(Vec2i sizeMin) {
		super.setSizeMin(sizeMin);
		this.window.setSizeLimits(glfwSizeValue(this.sizeMin.x), glfwSizeValue(this.sizeMin.y), glfwSizeValue(this.sizeMax.x), glfwSizeValue(this.sizeMax.y));
	}
	
	@Override
	public void setSizeMax(Vec2i sizeMax) {
		super.setSizeMax(sizeMax);
		this.window.setSizeLimits(glfwSizeValue(this.sizeMin.x), glfwSizeValue(this.sizeMin.y), glfwSizeValue(this.sizeMax.x), glfwSizeValue(this.sizeMax.y));
	}
	
	@Override
	public void resetMinSize() {
		this.setSizeMin(new Vec2i(30, 30));
	}
	
	@Override
	protected void onClicked(int button, boolean pressed, boolean repeated) {
		if (pressed && this.grabOffset == null) {
			Vec2f scale = this.window.getContentScale();
			Vec2d grabPos = this.window.getCourserPos().div(scale);
			Vec2d size = new Vec2d(this.size);
			
			// Check which sides where grabbed
			boolean l = grabPos.x <= this.grabFrameWidth;
			boolean r = grabPos.x >= size.x - this.grabFrameWidth;
			boolean u = grabPos.y <= this.grabFrameWidth;
			boolean b = grabPos.y >= size.y - this.grabFrameWidth;
			this.grabSideW = l ? -1 : r ? 1 : 0;
			this.grabSideH = u ? -1 : b ? 1 : 0;
			
			// Calculate offset
			this.grabOffset = new Vec2d();
			if (l) {
				this.grabOffset.x = grabPos.x;
			} else if (r) {
				this.grabOffset.x = grabPos.x - this.size.x;
			}
			if (u) {
				this.grabOffset.y = grabPos.y;
			} else if (b) {
				this.grabOffset.y = grabPos.y - this.size.y;
			}
		}
	}
	
	@Override
	protected void mouseEvent(Optional<Vec2d> scroll, int button, boolean pressed, boolean repeated) {
		// Detect release of the window
		if (!pressed && this.grabOffset != null) {
			this.grabOffset = null;
		}
		super.mouseEvent(scroll, button, pressed, repeated);
	}
	
	@Override
	protected void cursorMove(Vec2d position, boolean entered, boolean leaved) {
		if (this.grabOffset != null) {
			
			Vec2i windowSize = this.window.getSize();
			Vec2i windowPos = this.window.getPosition();
			Vec2i coursorPos = new Vec2i(this.window.getCourserPos());
			Vec2i minSize = new Vec2i(new Vec2f(this.getSizeMin()).mul(this.window.getContentScale()));
			Vec2i maxSize = new Vec2i(new Vec2f(this.getSizeMax()).mul(this.window.getContentScale()));
			
			// Resize width
			if (this.grabSideW == 1) {
				windowSize.x =  (int) (coursorPos.x - grabOffset.x);
				if (this.sizeMin.x != -1) windowSize.x = Math.max(minSize.x, windowSize.x);
				if (this.sizeMax.x != -1) windowSize.x = Math.min(maxSize.x, windowSize.x);
			} else if (this.grabSideW == -1) {
				int i = (int) (coursorPos.x - grabOffset.x);
				if (this.sizeMin.x != -1) i = Math.min(windowSize.x - minSize.x, i);
				if (this.sizeMax.x != -1) i = Math.max(windowSize.x - maxSize.x, i);
				windowPos.x += i;
				windowSize.x -= i;
			}
			
			// Resize height
			if (this.grabSideH == 1) {
				windowSize.y = (int) (coursorPos.y - grabOffset.y);
				if (this.sizeMin.y != -1) windowSize.y = Math.max(minSize.y, windowSize.y);
				if (this.sizeMax.y != -1) windowSize.y = Math.min(maxSize.y, windowSize.y);
			} else if (this.grabSideH == -1) {
				int i = (int) (coursorPos.y - grabOffset.y);
				if (this.sizeMin.y != -1) i = Math.min(windowSize.y - minSize.y, i);
				if (this.sizeMax.y != -1) i = Math.max(windowSize.y - maxSize.y, i);
				windowPos.y += i;
				windowSize.y -= i;
			}
			
			this.window.setSize(windowSize.x, windowSize.y);
			this.window.setPosition(windowPos);
		}
		super.cursorMove(position, entered, leaved);
	}
	
	@Override
	public void drawBackground(SimpleBufferSource<ResourceLocation, UIRenderMode<ResourceLocation>> bufferSource, PoseStack matrixStack) {
		if (OpenUI.isDebugDrawEneabled())
			UtilRenderer.drawFrame(this.size.x, this.size.y, this.grabFrameWidth, new Color(0, 0, 255, 128), bufferSource, matrixStack);
		super.drawBackground(bufferSource, matrixStack);
	}
	
}
