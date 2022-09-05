package de.m_marvin.render.translation;

import de.m_marvin.univec.impl.Vec3d;
import de.m_marvin.univec.impl.Vec4f;

public class Matrix4f {

	public float m00;
	public float m01;
	public float m02;
	public float m03;
	public float m10;
	public float m11;
	public float m12;
	public float m13;
	public float m20;
	public float m21;
	public float m22;
	public float m23;
	public float m30;
	public float m31;
	public float m32;
	public float m33;
	
	public Matrix4f() {
	}
	
	public Matrix4f(float m00, float m01, float m02, float m03, float m10, float m11, float m12, float m13, float m20,
			float m21, float m22, float m23, float m30, float m31, float m32, float m33) {
		this.m00 = m00;
		this.m01 = m01;
		this.m02 = m02;
		this.m03 = m03;
		this.m10 = m10;
		this.m11 = m11;
		this.m12 = m12;
		this.m13 = m13;
		this.m20 = m20;
		this.m21 = m21;
		this.m22 = m22;
		this.m23 = m23;
		this.m30 = m30;
		this.m31 = m31;
		this.m32 = m32;
		this.m33 = m33;
	}
	
	public Matrix4f(Quaternionf quaternion) {
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
		this.m33 = 1.0F;
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
			case 3: return m03;
			}
		case 1:
			switch (y) {
			case 0: return m10;
			case 1: return m11;
			case 2: return m12;
			case 3: return m13;
			}
		case 2:
			switch (y) {
			case 0: return m20;
			case 1: return m21;
			case 2: return m22;
			case 3: return m23;
			}
		case 3:
			switch (y) {
			case 0: return m30;
			case 1: return m31;
			case 2: return m32;
			case 3: return m33;
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
			case 3: m03 = value; break;
			}
		case 1:
			switch (y) {
			case 0: m10 = value; break;
			case 1: m11 = value; break;
			case 2: m12 = value; break;
			case 3: m13 = value; break;
			}
		case 2:
			switch (y) {
			case 0: m20 = value; break;
			case 1: m21 = value; break;
			case 2: m22 = value; break;
			case 3: m23 = value; break;
			}
		case 3:
			switch (y) {
			case 0: m30 = value; break;
			case 1: m31 = value; break;
			case 2: m32 = value; break;
			case 3: m33 = value; break;
			}
		}
		throw new IndexOutOfBoundsException();
	}

	
	public Matrix4f copy() {
		return new Matrix4f(m00, m01, m02, m03, m10, m11, m12, m13, m20, m21, m22, m23, m30, m31, m32, m33);
	}

	
	public int columns() {
		return 4;
	}

	
	public int rows() {
		return 4;
	}
	
	public void mul(Matrix4f mat) {
		if (mat.rows() != this.rows() && mat.columns() != this.columns()) throw new IllegalArgumentException("Cant multiply matrices with diffrent dimensions!");
		float f = this.m00 * mat.m00 + this.m01 * mat.m10 + this.m02 * mat.m20 + this.m03 * mat.m30;
		float f1 = this.m00 * mat.m01 + this.m01 * mat.m11 + this.m02 * mat.m21 + this.m03 * mat.m31;
		float f2 = this.m00 * mat.m02 + this.m01 * mat.m12 + this.m02 * mat.m22 + this.m03 * mat.m32;
		float f3 = this.m00 * mat.m03 + this.m01 * mat.m13 + this.m02 * mat.m23 + this.m03 * mat.m33;
		float f4 = this.m10 * mat.m00 + this.m11 * mat.m10 + this.m12 * mat.m20 + this.m13 * mat.m30;
		float f5 = this.m10 * mat.m01 + this.m11 * mat.m11 + this.m12 * mat.m21 + this.m13 * mat.m31;
		float f6 = this.m10 * mat.m02 + this.m11 * mat.m12 + this.m12 * mat.m22 + this.m13 * mat.m32;
		float f7 = this.m10 * mat.m03 + this.m11 * mat.m13 + this.m12 * mat.m23 + this.m13 * mat.m33;
		float f8 = this.m20 * mat.m00 + this.m21 * mat.m10 + this.m22 * mat.m20 + this.m23 * mat.m30;
		float f9 = this.m20 * mat.m01 + this.m21 * mat.m11 + this.m22 * mat.m21 + this.m23 * mat.m31;
		float f10 = this.m20 * mat.m02 + this.m21 * mat.m12 + this.m22 * mat.m22 + this.m23 * mat.m32;
		float f11 = this.m20 * mat.m03 + this.m21 * mat.m13 + this.m22 * mat.m23 + this.m23 * mat.m33;
		float f12 = this.m30 * mat.m00 + this.m31 * mat.m10 + this.m32 * mat.m20 + this.m33 * mat.m30;
		float f13 = this.m30 * mat.m01 + this.m31 * mat.m11 + this.m32 * mat.m21 + this.m33 * mat.m31;
		float f14 = this.m30 * mat.m02 + this.m31 * mat.m12 + this.m32 * mat.m22 + this.m33 * mat.m32;
		float f15 = this.m30 * mat.m03 + this.m31 * mat.m13 + this.m32 * mat.m23 + this.m33 * mat.m33;
		this.m00 = f;
		this.m01 = f1;
		this.m02 = f2;
		this.m03 = f3;
		this.m10 = f4;
		this.m11 = f5;
		this.m12 = f6;
		this.m13 = f7;
		this.m20 = f8;
		this.m21 = f9;
		this.m22 = f10;
		this.m23 = f11;
		this.m30 = f12;
		this.m31 = f13;
		this.m32 = f14;
		this.m33 = f15;
	}
	
	public void mul(float n) {
		this.m00 *= n;
		this.m01 *= n;
		this.m02 *= n;
		this.m03 *= n;
		this.m10 *= n;
		this.m11 *= n;
		this.m12 *= n;
		this.m13 *= n;
		this.m20 *= n;
		this.m21 *= n;
		this.m22 *= n;
		this.m23 *= n;
		this.m30 *= n;
		this.m31 *= n;
		this.m32 *= n;
		this.m33 *= n;
	}
		
	public void add(Matrix4f mat) {
		this.m00 += mat.m00;
		this.m01 += mat.m01;
		this.m02 += mat.m02;
		this.m03 += mat.m03;
		this.m10 += mat.m10;
		this.m11 += mat.m11;
		this.m12 += mat.m12;
		this.m13 += mat.m13;
		this.m20 += mat.m20;
		this.m21 += mat.m21;
		this.m22 += mat.m22;
		this.m23 += mat.m23;
		this.m30 += mat.m30;
		this.m31 += mat.m31;
		this.m32 += mat.m32;
		this.m33 += mat.m33;
	}

	
	public void sub(Matrix4f mat) {
		this.m00 -= mat.m00;
		this.m01 -= mat.m01;
		this.m02 -= mat.m02;
		this.m03 -= mat.m03;
		this.m10 -= mat.m10;
		this.m11 -= mat.m11;
		this.m12 -= mat.m12;
		this.m13 -= mat.m13;
		this.m20 -= mat.m20;
		this.m21 -= mat.m21;
		this.m22 -= mat.m22;
		this.m23 -= mat.m23;
		this.m30 -= mat.m30;
		this.m31 -= mat.m31;
		this.m32 -= mat.m32;
		this.m33 -= mat.m33;
	}

	public void mul(Quaternionf quaternion) {
		this.mul(new Matrix4f(quaternion));
	}
	
	public static Matrix4f identity() {
		return new Matrix4f(
				1, 0, 0, 0,
				0, 1, 0, 0,
				0, 0, 1, 0,
				0, 0, 0, 1
			);
	}
	
	public static Matrix4f createTranslationMatrix(float x, float y, float z) {
		Matrix4f matrix = new Matrix4f();
		matrix.m00 = 1.0F;
		matrix.m11 = 1.0F;
		matrix.m22 = 1.0F;
		matrix.m33 = 1.0F;
		matrix.m03 = x;
		matrix.m13 = y;
		matrix.m23 = z;
		return matrix;
	}
	
	public static Matrix4f createScaleMatrix(float x, float y, float z) {
		Matrix4f matrix = new Matrix4f();
		matrix.m33 = 1.0F;
		matrix.m00 = x;
		matrix.m11 = y;
		matrix.m22 = z;
		return matrix;
	}

	public void transform(Vec4f vec) {
		vec.x = this.m00 * vec.x + m01 * vec.y + m02 * vec.z + m03 * vec.w;
		vec.x = this.m10 * vec.x + m11 * vec.y + m12 * vec.z + m13 * vec.w;
		vec.x = this.m20 * vec.x + m21 * vec.y + m22 * vec.z + m23 * vec.w;
		vec.x = this.m30 * vec.x + m31 * vec.y + m32 * vec.z + m33 * vec.w;
	}
	
}
