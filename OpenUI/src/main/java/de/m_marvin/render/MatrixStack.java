package de.m_marvin.render;

import java.util.ArrayDeque;
import java.util.Deque;

import de.m_marvin.univec.api.IMatrix;
import de.m_marvin.univec.api.IMatrixMath;

public class MatrixStack<N extends Number, MP extends IMatrixMath<N, IMatrix<N>>, MN extends IMatrixMath<N, IMatrix<N>>> {

	public static record Pose<MP, MN>(MP pose, MN normal) {};
	
	private Deque<Pose<MP, MN>> poseStack = new ArrayDeque<>();
	
	public MatrixStack(MP pose, MN normal) {
		poseStack.addLast(new Pose<MP, MN>(pose, normal));
	}
	
	public Pose<MP, MN> last() {
		return this.poseStack.getLast();
	}
	
	public void pop() {
		if (poseStack.size() == 1) throw new IllegalStateException("Cant pop a pose stack with one element!");
		this.poseStack.removeLast();
	}
	
	public void push() {
		this.poseStack.addLast(new Pose<MP, MN>(last().pose.copy(), last().normal.copy()));
	}
	
}
