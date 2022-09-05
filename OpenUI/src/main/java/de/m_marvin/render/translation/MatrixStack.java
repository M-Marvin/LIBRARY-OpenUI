package de.m_marvin.render.translation;

import java.util.ArrayDeque;
import java.util.Deque;

public class MatrixStack {
	
	public static record Pose(Matrix4f pose, Matrix3f normal) {};
	
	private Deque<Pose> poseStack = new ArrayDeque<>();
	
	public MatrixStack(Matrix4f pose, Matrix3f normal) {
		poseStack.addLast(new Pose(pose, normal));
	}
	
	public Pose last() {
		return this.poseStack.getLast();
	}
	
	public void pop() {
		if (poseStack.size() == 1) throw new IllegalStateException("Cant pop a pose stack with one element!");
		this.poseStack.removeLast();
	}
	
	public void push() {
		this.poseStack.addLast(new Pose(last().pose.copy(), last().normal.copy()));
	}
	
	public void translate(double x, double y, double z) {
		Pose pose = last();
		pose.pose.mul(Matrix4f.createTranslationMatrix((float) x, (float) y, (float) z));
	}
	
	public void scale(float x, float y, float z) {
		Pose pose = last();
		pose.pose.mul(Matrix4f.createScaleMatrix(x, y, z));
		if (x == y && y == z) {
			if (x > 0) {
				return;
			}
			
			pose.normal.mul(-1);
		}
		
		float fx = 1 / x;
		float fy = 1 / y;
		float fz = 1 / z;
		float f = (float) Math.cbrt(fx * fy * fz);
		pose.normal.mul(Matrix3f.createScaleMatrix(fx * f, fy * f, fz * f));
	}
	
	public void rotate(Quaternionf quaternion) {
		Pose pose = last();
		pose.pose.mul(quaternion);
		pose.normal.mul(quaternion);
	}
	
}
