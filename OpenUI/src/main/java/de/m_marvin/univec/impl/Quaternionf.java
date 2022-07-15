package de.m_marvin.univec.impl;

import de.m_marvin.univec.api.IQuaternion;
import de.m_marvin.univec.api.IVector3;

public class Quaternionf implements IQuaternion<Float> {
	
	public float i;
	public float j;
	public float k;
	public float r;
	
	public Quaternionf(float i, float j, float k, float r) {
		this.i = i;
		this.j = j;
		this.k = k;
		this.r = r;
	}

	public Quaternionf(IVector3<? extends Number> rotationAxis, float rotationAngle) {
		float f = (float) Math.sin(rotationAngle / 2F);
		this.i = (float) rotationAxis.x() * f;
		this.j = (float) rotationAxis.y() * f;
		this.k = (float) rotationAxis.z() * f;
		this.r = (float) Math.cos(rotationAngle / 2F);
	}
	
	@Override
	public Float i() {
		return i;
	}

	@Override
	public Float j() {
		return j;
	}

	@Override
	public Float k() {
		return k;
	}

	@Override
	public Float r() {
		return r;
	}

	@Override
	public void setI(Float i) {
		this.i = i;
	}

	@Override
	public void setJ(Float j) {
		this.j = j;
	}

	@Override
	public void setK(Float k) {
		this.k = k;
	}

	@Override
	public void setR(Float r) {
		this.r = r;
	}
		
}
