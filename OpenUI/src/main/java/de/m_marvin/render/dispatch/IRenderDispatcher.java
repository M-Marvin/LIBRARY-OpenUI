package de.m_marvin.render.dispatch;

import java.nio.ByteBuffer;

import de.m_marvin.render.vertex.VertexFormat;

public interface IRenderDispatcher {
	
	public void dispatch(ByteBuffer vertexBuffer, VertexFormat format, VertexFormat.Mode mode, int vertecies, boolean useSingleBuffer);
	
}
