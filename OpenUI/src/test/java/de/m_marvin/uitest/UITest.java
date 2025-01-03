package de.m_marvin.uitest;

import java.io.IOException;
import java.net.URISyntaxException;

import de.m_marvin.gframe.GLFWStateManager;
import de.m_marvin.gframe.resources.defimpl.ResourceLocation;

public class UITest {

	public static void main(String... args) throws URISyntaxException, IOException, InterruptedException {
		
		// Start new logger
		//Logger.setDefaultLogger(new Logger());
		
		// Redirect run folder (since all resources are located in the test folder)
		//ResourceLoader.redirectRuntimeFolder(VoxelEngine.class.getClassLoader().getResource("").getPath().replace("bin/main/", "run/"));
		
		GLFWStateManager.initialize(System.out);
		
		TestWindow window = new TestWindow();
//		window.setAdjustMaxScale(true);
		
		window.start().thenAccept(e -> {
			window.maximize();
		});
		
		while (window.isOpen()) {
			GLFWStateManager.update();
		};
		
		GLFWStateManager.terminate();
	}
	
	private static UITest instance;
	private UITest() { instance = this; }
	
	public static UITest getInstance() {
		return instance;
	}
	
	public static final String NAMESPACE = "uitest";
	
	public static final ResourceLocation SHADER_LIB_LOCATION = new ResourceLocation(NAMESPACE, "glsl");
	public static final ResourceLocation WORLD_SHADER_LOCATION = new ResourceLocation(NAMESPACE, "world");
	public static final ResourceLocation OPENUI_SHADER_LOCATION = new ResourceLocation(NAMESPACE, "openui");
	
}
