package de.m_marvin.openui.holographic;

import de.m_marvin.gframe.GLStateManager;
import de.m_marvin.gframe.windows.Window;
import de.m_marvin.openui.flatmono.WindowFlatMono;

public abstract class WindowHolographic extends WindowFlatMono {
	
	private float opacity = 1.0F;
	
	public WindowHolographic(String windowName) {
		super(windowName);
	}
	
	public void setOpacity(float opacity) {
		this.opacity = opacity;
		if (this.uiContainer != null) {
			this.uiContainer.getRenderThreadExecutor().execute(() -> {
				this.getMainWindow().setOpacity(this.opacity);
			});
		}
	}
	
	@Override
	protected void initWindow() {
		this.mainWindow = new Window(1000, 600, windowName, true, false, true);
	}
	
	@Override
	protected void initOpenGL() {
		super.initOpenGL();
		GLStateManager.clearColor(0, 0, 0, 0.2F);
		this.getMainWindow().setOpacity(this.opacity);
	}
	
}
