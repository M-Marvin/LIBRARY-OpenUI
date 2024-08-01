package de.m_marvin.openui;

import de.m_marvin.gframe.GLFWStateManager;
import openui.assets.holographic.TestWindow;

public class UITestHolographic {
	
	public static void main(String... args) {
		
		GLFWStateManager.initialize(System.out);
		
		TestWindow testWindow = new TestWindow();
//		WindowFlatMono t2 = new WindowFlatMono("test") {
//			
//			@Override
//			protected void initUI() {
//				// TODO Auto-generated method stub
//				
//			}
//		};
//		t2.start();
		
		testWindow.start();
		
		while (testWindow.isOpen()) {
			GLFWStateManager.update();
		}
		
		testWindow.stop();
		
		GLFWStateManager.terminate();
		
	}
	
}
