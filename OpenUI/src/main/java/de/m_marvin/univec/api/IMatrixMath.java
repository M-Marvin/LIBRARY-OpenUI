package de.m_marvin.univec.api;

public interface IMatrixMath<N extends Number, MO extends IMatrix<N>> extends IMatrix<N> {

	public IMatrixMath<N, MO> copy();
	
}
