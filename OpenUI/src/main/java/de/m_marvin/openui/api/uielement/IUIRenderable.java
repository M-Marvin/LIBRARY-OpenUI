package de.m_marvin.openui.api.uielement;

import de.m_marvin.univec.impl.Vec2f;

public interface IUIRenderable {
	
	public Vec2f getPosition();
	public void setPosition(Vec2f pos);
	
	public Vec2f getSize();
	public void setSize(Vec2f size);
	
}
