package de.m_marvin.render.translation;

import de.m_marvin.univec.impl.Vec3f;

public class Matrix3f {
	
	public float m00;
	public float m01;
	public float m02;
	public float m10;
	public float m11;
	public float m12;
	public float m20;
	public float m21;
	public float m22;
	
	public Matrix3f() {
	}
	
	public Matrix3f(float m00, float m01, float m02, float m10, float m11, float m12, float m20, float m21, float m22) {
		this.m00 = m00;
		this.m01 = m01;
		this.m02 = m02;
		this.m10 = m10;
		this.m11 = m11;
		this.m12 = m12;
		this.m20 = m20;
		this.m21 = m21;
		this.m22 = m22;
	}
	
	public Matrix3f(Quaternionf quaternion) {
		float f = quaternion.i();
		float f1 = quaternion.j();
		float f2 = quaternion.k();
		float f3 = quaternion.r();
		float f4 = 2.0F * f * f;
		float f5 = 2.0F * f1 * f1;
		float f6 = 2.0F * f2 * f2;
		this.m00 = 1.0F - f5 - f6;
		this.m11 = 1.0F - f6 - f4;
		this.m22 = 1.0F - f4 - f5;
		float f7 = f * f1;
		float f8 = f1 * f2;
		float f9 = f2 * f;
		float f10 = f * f3;
		float f11 = f1 * f3;
		float f12 = f2 * f3;
		this.m10 = 2.0F * (f7 + f12);
		this.m01 = 2.0F * (f7 - f12);
		this.m20 = 2.0F * (f9 - f11);
		this.m02 = 2.0F * (f9 + f11);
		this.m21 = 2.0F * (f8 + f10);
		this.m12 = 2.0F * (f8 - f10);
	}

	public Float get(int x, int y) {
		switch (x) {
		case 0:
			switch (y) {
			case 0: return m00;
			case 1: return m01;
			case 2: return m02;
			}
		case 1:
			switch (y) {
			case 0: return m10;
			case 1: return m11;
			case 2: return m12;
			}
		case 2:
			switch (y) {
			case 0: return m20;
			case 1: return m21;
			case 2: return m22;
			}
		}
		throw new IndexOutOfBoundsException();
	}
	
	public void set(int x, int y, Float value) {
		switch (x) {
		case 0:
			switch (y) {
			case 0: m00 = value; break;
			case 1: m01 = value; break;
			case 2: m02 = value; break;
			}
		case 1:
			switch (y) {
			case 0: m10 = value; break;
			case 1: m11 = value; break;
			case 2: m12 = value; break;
			}
		case 2:
			switch (y) {
			case 0: m20 = value; break;
			case 1: m21 = value; break;
			case 2: m22 = value; break;
			}
		}
		throw new IndexOutOfBoundsException();
	}
	
	public Matrix3f copy() {
		return new Matrix3f(m00, m01, m02, m10, m11, m12, m20, m21, m22);
	}
	
	public int columns() {
		return 3;
	}
	
	public int rows() {
		return 3;
	}
	
	public Matrix3f mul(Matrix3f mat) {
		if (mat.rows() != this.rows() && mat.columns() != this.columns()) throw new IllegalArgumentException("Cant multiply matrices with diffrent dimensions!");
		float f = this.m00 * mat.m00 + this.m01 * mat.m10 + this.m02 * mat.m20 ;
		float f1 = this.m00 * mat.m01 + this.m01 * mat.m11 + this.m02 * mat.m21;
		float f2 = this.m00 * mat.m02 + this.m01 * mat.m12 + this.m02 * mat.m22;
		float f4 = this.m10 * mat.m00 + this.m11 * mat.m10 + this.m12 * mat.m20;
		float f5 = this.m10 * mat.m01 + this.m11 * mat.m11 + this.m12 * mat.m21;
		float f6 = this.m10 * mat.m02 + this.m11 * mat.m12 + this.m12 * mat.m22;
		float f8 = this.m20 * mat.m00 + this.m21 * mat.m10 + this.m22 * mat.m20;
		float f9 = this.m20 * mat.m01 + this.m21 * mat.m11 + this.m22 * mat.m21;
		float f10 = this.m20 * mat.m02 + this.m21 * mat.m12 + this.m22 * mat.m22;
		this.m00 = f;
		this.m01 = f1;
		this.m02 = f2;
		this.m10 = f4;
		this.m11 = f5;
		this.m12 = f6;
		this.m20 = f8;
		this.m21 = f9;
		this.m22 = f10;
		return this;
	}
	
	public Matrix3f mul(float n) {
		this.m00 *= n;
		this.m01 *= n;
		this.m02 *= n;
		this.m10 *= n;
		this.m11 *= n;
		this.m12 *= n;
		this.m20 *= n;
		this.m21 *= n;
		this.m22 *= n;
		return this;
	}
		
	public Matrix3f add(Matrix3f mat) {
		this.m00 += mat.m00;
		this.m01 += mat.m01;
		this.m02 += mat.m02;
		this.m10 += mat.m10;
		this.m11 += mat.m11;
		this.m12 += mat.m12;
		this.m20 += mat.m20;
		this.m21 += mat.m21;
		this.m22 += mat.m22;
		return this;
	}

	
	public Matrix3f sub(Matrix3f mat) {
		this.m00 -= mat.m00;
		this.m01 -= mat.m01;
		this.m02 -= mat.m02;
		this.m10 -= mat.m10;
		this.m11 -= mat.m11;
		this.m12 -= mat.m12;
		this.m20 -= mat.m20;
		this.m21 -= mat.m21;
		this.m22 -= mat.m22;
		return this;
	}

	public void mul(Quaternionf quaternion) {
		this.mul(new Matrix3f(quaternion));
	}

	public static Matrix3f identity() {
		return new Matrix3f(
				1, 0, 0,
				0, 1, 0,
				0, 0, 1
			);
	}
	
	public static Matrix3f createScaleMatrix(float x, float y, float z) {
		Matrix3f matrix = new Matrix3f();
		matrix.m00 = x;
		matrix.m11 = y;
		matrix.m22 = z;
		return matrix;
	}

	public void transform(Vec3f vec) {
		vec.x = this.m00 * vec.x + m01 * vec.y + m02 * vec.z;
		vec.x = this.m10 * vec.x + m11 * vec.y + m12 * vec.z;
		vec.x = this.m20 * vec.x + m21 * vec.y + m22 * vec.z;
	}
	
}
