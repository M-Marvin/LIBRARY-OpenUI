package de.m_marvin.render.vertex;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.system.MemoryUtil;

import de.m_marvin.render.vertex.VertexFormat.Format;
import de.m_marvin.render.vertex.VertexFormat.Mode;
import de.m_marvin.render.vertex.VertexFormat.Usage;
import de.m_marvin.render.vertex.VertexFormat.VertexElement;

public class VertexBuilder implements IVertexConsumer {
	
	/* Queue holding the pointers, modes, formats and vertex counts for the render calls */
	private List<DrawCall> drawCalls = new ArrayList<DrawCall>();
	/* Buffer to store all vertex data of this builders render type */
	private ByteBuffer buffer;
	/* Amount of bytes round up to if the buffer size is increased */
	private final int bufferAllocationModule;
	/* If true the buffer only contains data for a single render type and gets processed as one instance */
	private boolean fastMode;
	
	/* The index of the last buffer poped by the popNextBuffer method */
	private int lastPopedState;
	/* If true the vertex builder is in use to build a vertex */
	private boolean building;
	/* Mode used to draw the vertices */
	private Mode mode;
	/* Format that describes the order of the vertex data in the buffer */
	private VertexFormat format;
	/* The next element required to finish the current vertex */
	private VertexElement nextElement;
	/* Buffer position where the current render-call was started at */
	private int lastCallPosition;
	/* Format-index of the next required element to finish the buffer */
	private int nextElementIndex;
	/* Buffer-position of the next required element to finish the buffer */
	private int nextElementByte;
	/* Number of elements of the current render-call */
	private int vertecies;
	
	public static record DrawCall(Mode mode, VertexFormat format, int bufferPosition, int vertecies) {}
	public static record DrawState(DrawCall call, ByteBuffer buffer) { public boolean isEmpty() { return call == null || buffer == null; } };
	
	public VertexBuilder(int initialCapacity, int bufferAllocationModule, boolean fastMode) {
		this.bufferAllocationModule = bufferAllocationModule;
		this.buffer = MemoryUtil.memAlloc(initialCapacity);
		this.fastMode = fastMode;
	}
	
	public void begin(Mode mode, VertexFormat format) {
		if (format.getElements().length == 0) {
			throw new IllegalArgumentException("Empty vertex format!");
		}
		if (this.building) {
			throw new IllegalStateException("Vertex already building!");
		} else {
			this.building = true;
			
			// Start new draw-call (in fast mode only if a new frame)
			if (this.buffer.position() == 0) {
				this.mode = mode;
				this.format = format;
				this.lastCallPosition = this.nextElementByte;
				this.nextElementIndex = 0;
				this.nextElement = this.format.getElements()[this.nextElementIndex];
				this.vertecies = 0;
				this.buffer.position(nextElementByte);
			}
		}
	}
	
	public void end(boolean endOfFrame) {
		if (this.nextElementIndex != 0) {
			throw new IllegalStateException("Last vertex incompleted, can't finish buffer!");
		} else {
			this.building = false;
			
			// End draw-call (in fast mode only if the frame ends)
			if ((!fastMode || endOfFrame) && this.vertecies != 0) this.drawCalls.add(new DrawCall(this.mode, this.format, this.lastCallPosition, this.vertecies));
		}
	}
	
	public boolean isFastMode() {
		return fastMode;
	}
	
	public boolean isBuilding() {
		return building;
	}
	
	public ByteBuffer getBuffer() {
		return this.buffer;
	}
	
	public DrawState popNextBuffer() {
		if (this.drawCalls.size() <= lastPopedState) return new DrawState(null, null);
		DrawCall call = this.drawCalls.get(lastPopedState++);
		ByteBuffer buffer = this.buffer;
		if (!isFastMode()) {
			this.buffer.position(call.bufferPosition);
			this.buffer.limit(call.bufferPosition + call.vertecies * call.format.getVertexSize());
			buffer = this.buffer.slice();
		}
		return new DrawState(call, buffer);
	}
	
	public void reset() {
		this.lastPopedState = 0;
	}
	
	public void discard() {
		// Clear everything
		this.buffer.clear();
		this.drawCalls.clear();
		this.lastPopedState = 0;
		this.building = false;
		this.nextElement = null;
		this.lastCallPosition = 0;
		this.nextElementIndex = 0;
		this.nextElementByte = 0;
		this.vertecies = 0;
	}
	
	
	
	
	
	@Override
	public void nextElement() {
		this.nextElementByte += this.nextElement.getSize();
		this.nextElementIndex += 1;
		if (this.format.getElements().length > this.nextElementIndex) this.nextElement = this.format.getElements()[this.nextElementIndex];
		this.buffer.position(this.nextElementByte);
	}
	
	@Override
	public IVertexConsumer putByte(int i, byte value) {
		this.buffer.put(i, value);
		return this;
	}

	@Override
	public IVertexConsumer putShort(int i, short value) {
		this.buffer.putShort(i, value);
		return this;
	}

	@Override
	public IVertexConsumer putInt(int i, int value) {
		this.buffer.putInt(i, value);
		return this;
	}

	@Override
	public IVertexConsumer putLong(int i, long value) {
		this.buffer.putLong(i, value);
		return this;
	}

	@Override
	public IVertexConsumer putFloat(int i, float value) {
		this.buffer.putFloat(i, value);
		return this;
	}

	@Override
	public IVertexConsumer putDouble(int i, double value) {
		this.buffer.putDouble(i, value);
		return this;
	}

	@Override
	public IVertexConsumer vertex(float x, float y, float z) {
		if (this.nextElement.getUsage() == Usage.VERTEX && this.nextElement.getFormat() == Format.FLOAT && this.nextElement.getCount() == 3) {
			this.buffer.putFloat(0, x);
			this.buffer.putFloat(4, y);
			this.buffer.putFloat(8, z);
			nextElement();
		} else {
			throw new IllegalStateException("Wrong vertex element order or wrong element format!");
		}
		return this;
	}
	
	@Override
	public IVertexConsumer normal(float x, float y, float z) {
		if (this.nextElement.getUsage() == Usage.NORMAL && this.nextElement.getFormat() == Format.FLOAT && this.nextElement.getCount() == 3) {
			this.buffer.putFloat(0, x);
			this.buffer.putFloat(4, y);
			this.buffer.putFloat(8, z);
			nextElement();
		} else {
			throw new IllegalStateException("Wrong vertex element order or wrong element format!");
		}
		return this;
	}

	@Override
	public IVertexConsumer color(byte red, byte green, byte blue, byte alpha) {
		if (this.nextElement.getUsage() == Usage.COLOR && this.nextElement.getFormat() == Format.UBYTE && this.nextElement.getCount() == 4) {
			this.buffer.putFloat(0, red);
			this.buffer.putFloat(1, green);
			this.buffer.putFloat(2, blue);
			this.buffer.putFloat(3, alpha);
			nextElement();
		} else {
			throw new IllegalStateException("Wrong vertex element order or wrong element format!");
		}
		return this;
	}

	@Override
	public IVertexConsumer uv(float u, float v) {
		if (this.nextElement.getUsage() == Usage.UV && this.nextElement.getFormat() == Format.FLOAT && this.nextElement.getCount() == 2) {
			this.buffer.putFloat(0, u);
			this.buffer.putFloat(4, v);
			nextElement();
		} else {
			throw new IllegalStateException("Wrong vertex element order or wrong element format!");
		}
		return this;
	}
	
	@Override
	public void endVertex() {
		if (this.nextElementIndex != this.format.getElements().length) {
			throw new IllegalStateException("Can't end incomplete vertex!");
		} else {
			
			this.nextElementIndex = 0;
			this.nextElement = this.format.getElements()[0];
			this.vertecies += 1;
			
			// Ensure buffer capacity for next vertex
			if (this.nextElementByte + this.format.getVertexSize() >= this.buffer.limit()) {
				int increasement = this.nextElementByte + this.format.getVertexSize() - this.buffer.capacity();
				int r = increasement % bufferAllocationModule;
				increasement += bufferAllocationModule - r;
				int newSize = this.buffer.capacity() + increasement;
				System.out.println("BuferBuilder needed to grow from " + this.buffer.capacity() + " to " + newSize + " bytes!");
				
				ByteBuffer increasedBuffer = MemoryUtil.memRealloc(this.buffer, newSize);
				increasedBuffer.rewind();
				this.buffer = increasedBuffer;
			}
		}
	}
	
}
