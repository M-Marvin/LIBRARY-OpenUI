package de.m_marvin.voxelengine.deprecated;

import de.m_marvin.gframe.translation.PoseStack;
import de.m_marvin.univec.impl.Vec2i;

public interface IUIElement {
	
	public static enum HoverEvent {
		ENTER_ELEMENT,OVER_ELEMENT,LEAVE_ELEMENT;
	}
	
	public void draw(PoseStack poseStack);
	public boolean isOverElement(Vec2i coursorPosition);
	
	public default void onHover(HoverEvent event, Vec2i coursorPosition) {}
	public default void tick() {}
	
}
