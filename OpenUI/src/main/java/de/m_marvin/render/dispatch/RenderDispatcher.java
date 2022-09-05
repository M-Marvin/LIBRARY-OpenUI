package de.m_marvin.render.dispatch;

import static de.m_marvin.render.GLStateManager.*;

import java.nio.ByteBuffer;

import de.m_marvin.render.vertex.RenderType;
import de.m_marvin.render.vertex.VertexBuilder;
import de.m_marvin.render.vertex.VertexFormat;
import de.m_marvin.render.vertex.VertexBuilder.DrawState;
import de.m_marvin.render.vertex.VertexFormat.Mode;
import de.m_marvin.render.vertex.VertexFormat.VertexElement;

public class RenderDispatcher {
	
	private int vertexArray;
	private int vertexBuffer;
	private VertexFormat lastFormat;
	
	public RenderDispatcher() {
		
	}
	
	public void init() {
		this.vertexArray = createVertexArray();
		this.vertexBuffer = createBuffer();
	}
	
	public void dispatchBufferBuilder(VertexBuilder vertexBuffer) {
		
		DrawState drawState = vertexBuffer.popNextBuffer();
		if (!drawState.isEmpty()) {
			
			dispatchVertecies(drawState.buffer(), drawState.call().format(), drawState.call().mode(), drawState.call().vertecies());
			while (!(drawState = vertexBuffer.popNextBuffer()).isEmpty()) {
				dispatchVertecies(drawState.buffer(), drawState.call().format(), drawState.call().mode(), drawState.call().vertecies());
			}
			
		}
		vertexBuffer.discard();
	}
	
	public void dispatchVertecies(ByteBuffer buffer, VertexFormat format, Mode mode, int vertecies) {
		
		if (!format.equals(this.lastFormat)) updateFormat(format);
		upload(buffer);
		
		drawElements(mode.getGlType(), 0, vertecies);
		
	}
	
	
	public void upload(ByteBuffer buffer) {
		bindVertexArray(vertexArray);
		bindBuffer(GL_ARRAY_BUFFER, vertexBuffer);
		bufferData(GL_ARRAY_BUFFER, buffer, GL_DYNAMIC_DRAW);	
	}
	
	public void updateFormat(VertexFormat format) {
		VertexElement[] elements = format.getElements();
		int offset = 0;
		for (int attribute = 0; attribute < elements.length; attribute++) {
			VertexElement element = elements[attribute];
			enableVertexAttributeArray(attribute);
			vertexAttributePointer(attribute, element.getCount(), element.getFormat().getGlType(), false, format.getVertexSize() - element.getSize(), offset);
			offset += element.getSize();
		}
	}
	
}
