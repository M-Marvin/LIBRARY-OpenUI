package de.m_marvin.openui.core.window;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import org.lwjgl.glfw.GLFW;

import de.m_marvin.gframe.GLStateManager;
import de.m_marvin.gframe.inputbinding.UserInput;
import de.m_marvin.gframe.resources.IResourceProvider;
import de.m_marvin.gframe.resources.ISourceFolder;
import de.m_marvin.gframe.resources.ResourceLoader;
import de.m_marvin.gframe.shaders.ShaderLoader;
import de.m_marvin.gframe.textures.TextureLoader;
import de.m_marvin.gframe.windows.Window;
import de.m_marvin.gframe.windows.Window.WindowEventType;
import de.m_marvin.openui.core.components.Compound;
import de.m_marvin.simplelogging.Log;
import de.m_marvin.univec.impl.Vec2d;
import de.m_marvin.univec.impl.Vec2f;
import de.m_marvin.univec.impl.Vec2i;

public abstract class UIWindow<R extends IResourceProvider<R>, S extends ISourceFolder> {

	public UIWindow(S shaderFolder, S textureFolder, String windowName) {
		this(
				new UserInput(), 
				shaderFolder,
				textureFolder,
				windowName
		);
	}

	public UIWindow(UserInput userInput, S shaderFolder, S textureFolder, String windowName) {
		this(
				new ResourceLoader<>(),
				userInput, 
				shaderFolder,
				textureFolder,
				windowName
		);
	}
	
	public UIWindow(ResourceLoader<R, S> resourceLoader, UserInput userInput, S shaderFolder, S textureFolder, String windowName) {
		this(
				resourceLoader,
				new ShaderLoader<R, S>(shaderFolder, resourceLoader),
				new TextureLoader<R, S>(textureFolder, resourceLoader),
				userInput,
				windowName,
				true
		);
	}
	
	public UIWindow(ResourceLoader<R, S> resourceLoader, ShaderLoader<R, S> shaderLoader, TextureLoader<R, S> textureLoader, UserInput inputHandler, String windowName, boolean clearCachesOnClose) {
		
		this.windowName = windowName;
		this.clearCachesOnClose = clearCachesOnClose;
		this.inputHandler = inputHandler;
		this.resourceLoader = resourceLoader;
		this.shaderLoader = shaderLoader;
		this.textureLoader = textureLoader;
		
	}
	
	protected String windowName;
	protected boolean adjustMaxScale = false;
	protected Vec2f contenteScale = new Vec2f(1.0F, 1.0F);
	protected final boolean clearCachesOnClose;
	
	private final ResourceLoader<R, S> resourceLoader;
	private final ShaderLoader<R, S> shaderLoader;
	private final TextureLoader<R, S> textureLoader;
	private final UserInput inputHandler;

	private Thread renderThread;
	protected Window mainWindow;
	protected UIContainer<R> uiContainer;
	private int framesPerSecond;
	private int frameTime;
	private CompletableFuture<Boolean> startup;
	private CompletableFuture<Boolean> shutdown;
	private boolean shouldClose;
	
	public void setAdjustMaxScale(boolean adjustMaxScale) {
		this.adjustMaxScale = adjustMaxScale;
	}
	
	public boolean isOpen() {
		return !this.shouldClose;
	}
	
	public CompletableFuture<Boolean> start() {
		if (this.renderThread != null)
			return CompletableFuture.completedFuture(true);
		this.shouldClose = false;
		this.startup = new CompletableFuture<Boolean>().orTimeout(10, TimeUnit.SECONDS).exceptionally(e -> {
			Log.defaultLogger().error("Failed to start window:", e);
			return false;
		});
		this.renderThread = new Thread(this::init, "RenderThread[" + this.windowName + "]");
		this.renderThread.setDaemon(true);
		this.renderThread.start();
		return this.startup;
	}
	
	public CompletableFuture<Boolean> stop() {
		if (this.startup == null) return CompletableFuture.completedFuture(true);
		return this.startup.thenApply(initialized -> {
			if (!initialized) return true;
			this.shutdown = new CompletableFuture<Boolean>().orTimeout(10, TimeUnit.SECONDS).exceptionally(e -> {
				Log.defaultLogger().error("Failed to stop window:", e);
				return false;
			});
			this.shouldClose = true;
			return this.shutdown.join();
		});
	}
	
	public void maximize() {
		this.mainWindow.maximize();
	}
	
	public void minimize() {
		this.mainWindow.minimize();
	}
	
	public Vec2f getContentScale() {
		return this.mainWindow.getContentScale();
	}
	
	public void setVisible(boolean visible) {
		this.mainWindow.setVisible(visible);
	}
	
	public boolean isInitialized() {
		return this.startup == null ? false : this.startup.isDone() ? this.startup.join() : false;
	}
	
	protected void initWindow() {
		mainWindow = new Window(1000, 600, this.windowName);
	}
	
	protected void initOpenGL() {
		mainWindow.makeContextCurrent();
		GLStateManager.clearColor(1, 0, 1, 1);
	}
	
	private void init() {
		
		try {
			
			// Setup main window
			initWindow();
			initOpenGL();
			
			// Setup input handler
			inputHandler.attachToWindow(mainWindow.windowId());
			
			// Setup and start ui render loop
			frameTime = 16; // ~60 FPS
			setup();
			startLoop();
			
		} catch (Exception e) {
			e.printStackTrace();
			this.stop();
			throw e;
		}

		if (this.clearCachesOnClose) {

			// Unload all shaders, textures and models
			shaderLoader.clearCached();
			textureLoader.clearCached();

		}

		if (this.mainWindow != null) {
			
			// Detach input handler
			inputHandler.detachWindow(mainWindow.windowId());

			// Destroy main window
			mainWindow.destroy();
			
		}
		
		this.renderThread = null;
		
		this.startup = null;
		if (this.shutdown != null) this.shutdown.complete(true);
		
	}

	private void startLoop() {

		long timeMillis = System.currentTimeMillis();
		float deltaFrame = 0;

		int frameCount = 0;
		long secondTimer = timeMillis;
		long lastFrameTime = 0;

		while (!mainWindow.shouldClose() && !this.shouldClose) {

			lastFrameTime = timeMillis;
			timeMillis = System.currentTimeMillis();
			deltaFrame += (timeMillis - lastFrameTime) / (float) frameTime;

			this.uiContainer.processTasks();
			
			if (deltaFrame >= 1) {
				deltaFrame--;
				frameCount++;
				frame();
				inputHandler.update();
			}

			if (timeMillis - secondTimer > 1000) {
				secondTimer += 1000;
				framesPerSecond = frameCount;
				frameCount = 0;
			}
			
		}
		
		this.shouldClose = true;

	}

	protected void setup() {
		
		try {
			this.uiContainer = new UIContainer<>(this.inputHandler);
			
			windowChangeContentScale(this.mainWindow.getContentScale());
			initUI();
			windowResized(new Vec2i(new Vec2f(this.mainWindow.getSize()).div(contenteScale)));
			this.startup.complete(true);
		} catch (Exception e) {
			this.startup.completeExceptionally(e);
		}
		
		this.mainWindow.registerWindowListener((windowResize, type) -> {
			if (windowResize.isPresent() && type == WindowEventType.RESIZED) {
				this.uiContainer.getRenderThreadExecutor().execute(() -> {
					windowResized(new Vec2i(windowResize.get().div(contenteScale)));
				});
			} else if (windowResize.isPresent() && type == WindowEventType.DPI_CHANGE) {
				this.uiContainer.getRenderThreadExecutor().execute(() -> {
					windowChangeContentScale(windowResize.get());
				});
			}
		});

	}
	
	public void setSize(Vec2i windowSize) {
		Vec2i size = new Vec2i(contenteScale.mul(windowSize));
		this.mainWindow.setSize(size.x, size.y);
	}
	
	protected void autoSetMinSize() {
		Vec2i minSize = new Vec2i(contenteScale.mul(this.uiContainer.calculateMinScreenSize()));
		this.mainWindow.setMinSize(minSize.x, minSize.y);
	}

	protected void autoSetMinAndMaxSize() {
		Vec2i minSize = new Vec2i(contenteScale.mul(this.uiContainer.calculateMinScreenSize()));
		Vec2i maxSize = new Vec2i(contenteScale.mul(this.uiContainer.calculateMaxScreenSize()));
		/* some platforms seem to don't handle it well if only one of the upper size limits is DONT_CARE */
		if (minSize.x == -1 && minSize.y != -1) minSize.x = 0;
		if (minSize.y == -1 && minSize.x != -1) minSize.y = 0;
		if (maxSize.x == -1 && maxSize.y != -1) maxSize.x = 1000000;
		if (maxSize.y == -1 && maxSize.x != -1) maxSize.y = 1000000;
		this.mainWindow.setSizeLimits(minSize.x, minSize.y, maxSize.x, maxSize.y);
	}
	
	protected void resetSizeLimits() {
		this.mainWindow.setMinSize(GLFW.GLFW_DONT_CARE, GLFW.GLFW_DONT_CARE);
	}
	
	protected void windowChangeContentScale(Vec2f scale) {
		this.contenteScale = scale;
		// Handle this as if the windows was resized (update layout, update viewport, etc)
		windowResized(mainWindow.getSize());
	}
	
	protected void windowResized(Vec2i screenSize) {
		GLStateManager.resizeViewport(0, 0, (int) (screenSize.x * contenteScale.x), (int) (screenSize.y * contenteScale.y));
		
		if (this.adjustMaxScale) {
			Vec2i compoundSize = this.uiContainer.getRootCompound().getSizeMin().copy();
			if (compoundSize.x == 0 || compoundSize.y == 0) {
				Log.defaultLogger().warn("Root min size not set, can not adjust scale!");
				return;
			}
			float compoundRatio = compoundSize.x / (float) compoundSize.y; 
			float screenRatio = screenSize.x / (float) screenSize.y;
			
			if (screenRatio < compoundRatio) compoundSize.y *= compoundRatio / screenRatio; 
			if (screenRatio > compoundRatio) compoundSize.x *= screenRatio / compoundRatio; 
			
			float scale = compoundSize.x / (float) screenSize.x;
			this.inputHandler.setCursorScale(new Vec2d(scale, scale).div(contenteScale));
			this.uiContainer.screenResize(compoundSize);
		} else {
			this.inputHandler.setCursorScale(new Vec2d(1, 1).div(contenteScale));
			this.uiContainer.screenResize(screenSize);
		}
	}

	protected void draw() {
		this.uiContainer.updateComponentVAOs(textureLoader, getContentScale());
		this.uiContainer.renderVAOs(shaderLoader, textureLoader);
	}
	
	protected void frame() {
		GLStateManager.clear();
		draw();
		mainWindow.glSwapFrames();
	}
	
	protected abstract void initUI();
	
	public ResourceLoader<R, S> getResourceLoader() {
		return resourceLoader;
	}

	public ShaderLoader<R, S> getShaderLoader() {
		return shaderLoader;
	}

	public TextureLoader<R, S> getTextureLoader() {
		return textureLoader;
	}

	public Window getMainWindow() {
		return mainWindow;
	}

	public int getFramesPerSecond() {
		return framesPerSecond;
	}
	
	public String getWindowName() {
		return windowName;
	}
	
	public void setWindowName(String windowName) {
		this.windowName = windowName;
		this.mainWindow.setTitle(windowName);
	}
	
	public Compound<R> getRootComponent() {
		return this.uiContainer.getRootCompound();
	}
	
}
