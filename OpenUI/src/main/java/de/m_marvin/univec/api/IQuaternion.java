package de.m_marvin.univec.api;

public interface IQuaternion<N extends Number> {
	
	public N i();
	default public N getI() { return i(); }
	public N j();
	default public N getJ() { return j(); }
	public N k();
	default public N getK() { return k(); }
	public N r();
	default public N getR() { return r(); }
	
	public void setI(N i);
	public void setJ(N j);
	public void setK(N k);
	public void setR(N r);

	public IMatrix<N> copy();
	
}
