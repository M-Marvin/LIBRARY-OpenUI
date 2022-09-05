package de.m_marvin.render.vertex;

import java.util.LinkedHashMap;
import java.util.Map;

import org.lwjgl.opengl.GL11;

public class VertexFormat {
	
	private final int vertexSize;
	private final Map<String, VertexElement> elements;
	
	public VertexFormat(Map<String, VertexElement> elements, int vertexSize) {
		this.elements = elements;
		this.vertexSize = vertexSize;
	}
	
	public Map<String, VertexElement> getElementMappings() {
		return elements;
	}
	
	public VertexElement[] getElements() {
		return elements.values().toArray((length) -> new VertexElement[length]);
	}

	public int getVertexSize() {
		return vertexSize;
	}
	
	public static class Builder {
		
		private Map<String, VertexElement> elements = new LinkedHashMap<String, VertexElement>();
		private int vertexSize;
		
		public Builder put(String name, VertexElement element) {
			this.elements.put(name, element);
			this.vertexSize += element.getSize();
			return this;
		}
		
		public VertexFormat build() {
			return new VertexFormat(elements, vertexSize);
		}
		
	}
	
	public static class VertexElement {
		
		private Format format;
		private Usage usage;
		private int count;
		
		public VertexElement(Format format, Usage usage, int count) {
			this.format = format;
			this.usage = usage;
			this.count = count;
		}
		
		public Integer getSize() {
			return this.format.byteCount() * this.count;
		}

		public Format getFormat() {
			return format;
		}
		
		public Usage getUsage() {
			return usage;
		}
		
		public int getCount() {
			return count;
		}
		
	}
	
	public static enum Usage {
		VERTEX,
		NORMAL,
		COLOR,
		UV,
		GENRIC
	}
	
	public static enum Format {
		FLOAT(GL11.GL_FLOAT, Float.BYTES),
		UINT(GL11.GL_UNSIGNED_INT, Integer.BYTES),
		INT(GL11.GL_INT, Integer.BYTES),
		USHORT(GL11.GL_UNSIGNED_SHORT, Short.BYTES),
		SHORT(GL11.GL_SHORT, Short.BYTES),
		UBYTE(GL11.GL_UNSIGNED_BYTE, Byte.BYTES),
		BYTE(GL11.GL_BYTE, Byte.BYTES);
		
		private int glType;
		private int byteCount;
		
		private Format(int glType, int byteCount) {
			this.glType = glType;
			this.byteCount = byteCount;
		}
		
		public int getGlType() {
			return glType;
		}
				
		public int byteCount() {
			return byteCount;
		}
		
		@Override
		public String toString() {
			return this.name();
		}
	}
	
	public static enum Mode {
		LINE(GL11.GL_LINE),
		LINE_STRIP(GL11.GL_LINE_STRIP),
		LINE_LOOP(GL11.GL_LINE_LOOP),
		TRIANGLE(GL11.GL_TRIANGLES),
		TRIANGLE_STRIP(GL11.GL_TRIANGLE_STRIP),
		TRIANGLE_FAN(GL11.GL_TRIANGLE_FAN);
		
		private int glType;
		
		private Mode(int glType) {
			this.glType = glType;
		}
		
		public int getGlType() {
			return glType;
		}
		
		@Override
		public String toString() {
			return this.name();
		}
	}
	
}
