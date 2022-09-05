package de.m_marvin.render.vertex;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class MultiBufferSource implements IBufferSource {
	
	private VertexBuilder defaultBuffer;
	private Map<RenderType, VertexBuilder> fixedBuffers = new LinkedHashMap<RenderType, VertexBuilder>();
	private VertexBuilder activeBuffer;
	
	public MultiBufferSource(int initialCapacity, int bufferAllocationModule) {
		this.defaultBuffer = new VertexBuilder(initialCapacity, bufferAllocationModule, false);
	}
	
	public void addFixedBuffer(RenderType renderType, VertexBuilder fixedBuffer) {
		if (this.fixedBuffers.containsKey(renderType)) throw new IllegalStateException("RenderType " + renderType.toString() + " buffer already fixed!");
		this.fixedBuffers.put(renderType, fixedBuffer);
	}
	
	@Override
	public IVertexConsumer getBuffer(RenderType renderType) {
		VertexBuilder buffer = this.fixedBuffers.getOrDefault(renderType, this.defaultBuffer);
		if (activeBuffer != null) {
			if (activeBuffer != buffer) {
				activeBuffer.end(false); 
			} else {
				return activeBuffer;
			}
		}
		buffer.begin(renderType.mode(), renderType.format());
		activeBuffer = buffer;
		return buffer;
	}
	
	public void endOfFrame() {
		this.defaultBuffer.end(false);
		fixedBuffers.values().forEach((buffer) -> buffer.end(true));
		this.activeBuffer = null;
	}
	
	public Set<RenderType> getActiveRenderTypes() {
		return this.fixedBuffers.keySet();
	}
	
	public VertexBuilder getFinishedBuffer(RenderType type) {
		return this.fixedBuffers.getOrDefault(type, this.defaultBuffer);
	}
	
}
