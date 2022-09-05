package de.m_marvin.render.vertex;

public interface IBufferSource {
	
	public IVertexConsumer getBuffer(RenderType renderType);
	
}
