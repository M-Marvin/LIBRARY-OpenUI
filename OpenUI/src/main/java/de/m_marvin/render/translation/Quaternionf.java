package de.m_marvin.render.translation;

import de.m_marvin.univec.api.IVector3;

public class Quaternionf {
	
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
	
	public Float i() {
		return i;
	}

	public Float j() {
		return j;
	}

	public Float k() {
		return k;
	}

	public Float r() {
		return r;
	}

	public void setI(Float i) {
		this.i = i;
	}

	public void setJ(Float j) {
		this.j = j;
	}

	public void setK(Float k) {
		this.k = k;
	}

	public void setR(Float r) {
		this.r = r;
	}
	
	public Quaternionf copy() {
		return new Quaternionf(this.i, this.j, this.k, this.r);
	}
	
}
