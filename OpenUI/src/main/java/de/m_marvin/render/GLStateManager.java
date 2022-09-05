package de.m_marvin.render;

import java.nio.ByteBuffer;

import org.lwjgl.opengl.GL30;

import static org.lwjgl.opengl.GL30.*;

public class GLStateManager {
	
	// Buffer targets
	public static final int GL_ARRAY_BUFFER = GL30.GL_ARRAY_BUFFER;
	// Buffer usages
	public static final int GL_STATIC_DRAW = GL30.GL_STATIC_DRAW;
	public static final int GL_STRAM_DRAW = GL30.GL_STREAM_DRAW;
	public static final int GL_DYNAMIC_DRAW = GL30.GL_DYNAMIC_DRAW;
	// GL Buffer buffer bits
	public static final int GL_COLOR_BUFFER_BIT = GL30.GL_COLOR_BUFFER_BIT;
	public static final int GL_DEPTH_BUFFER_BIT = GL30.GL_DEPTH_BUFFER_BIT;
	
	public static void clearColor(float red, float green, float blue, float alpha) {
		glClearColor(red, green, blue, alpha);
	}
	
	public static void clearBufferBit(int bufferBitMask) {
		glClear(bufferBitMask);
	}
	
	public static int createBuffer() {
		return glGenVertexArrays();
	}

	public static int createVertexArray() {
		return glGenBuffers();
	}
	
	public static void bindBuffer(int target, int buffer) {
		glBindBuffer(target, buffer);
	}
	
	public static void bindVertexArray(int array) {
		glBindVertexArray(array);
	}
	
	public static void bufferData(int target, ByteBuffer buffer, int usage) {
		glBufferData(target, buffer, usage);
	}
	
	public static void enableVertexAttributeArray(int attribute) {
		glEnableVertexAttribArray(attribute);
	}
	
	public static void vertexAttributePointer(int attribute, int count, int type, boolean normalized, int stride, int offset) {
		glVertexAttribPointer(attribute, count, type, normalized, stride, offset);
	}
	
	public static void drawElements(int mode, int first, int count) {
		glDrawArrays(mode, first, count);
	}
	
}
