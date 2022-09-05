package de.m_marvin.render.vertex;

import de.m_marvin.render.translation.Matrix3f;
import de.m_marvin.render.translation.Matrix4f;
import de.m_marvin.univec.impl.Vec3f;
import de.m_marvin.univec.impl.Vec4f;

public interface IVertexConsumer {
	
	public void nextElement();
	public IVertexConsumer putByte(int i, byte value);
	public IVertexConsumer putShort(int i, short value);
	public IVertexConsumer putInt(int i, int value);
	public IVertexConsumer putLong(int i, long value);
	public IVertexConsumer putFloat(int i, float value);
	public IVertexConsumer putDouble(int i, double value);
	
	public IVertexConsumer vertex(float x, float y, float z);
	public IVertexConsumer normal(float x, float y, float z);
	public IVertexConsumer color(byte red, byte green, byte blue, byte alpha);
	public IVertexConsumer uv(float u, float v);
	public void endVertex();
	
	default public IVertexConsumer vertex(Matrix4f pose, double x, double y, double z) {
		Vec4f vertex = new Vec4f((float) x, (float) y, (float) z, 1F);
		pose.transform(vertex);
		return vertex(vertex.x(), vertex.y(), vertex.z());
	}
	default public IVertexConsumer normal(Matrix3f normal, float x, float y, float z) {
		Vec3f norm = new Vec3f(x, y, z);
		normal.transform(norm);
		return normal(norm.x(), norm.y(), norm.z());
	}
	default public IVertexConsumer color(float red, float green, float blue, float alpha) {
		return color((byte) ((red * 255) - 128), (byte) ((red * 255) - 128), (byte) ((red * 255) - 128), (byte) ((red * 255) - 128));
	}
	
}
