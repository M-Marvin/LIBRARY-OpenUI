package de.m_marvin.openui;

/**
 * Static variables used mostly for debugging
 */
public class OpenUI {
	
	private static boolean debugDrawEneabled = false;
	
	public static void setDebugDrawEneabled(boolean debugDrawEneabled) {
		OpenUI.debugDrawEneabled = debugDrawEneabled;
	}
	
	public static boolean isDebugDrawEneabled() {
		return debugDrawEneabled;
	}
	
}
