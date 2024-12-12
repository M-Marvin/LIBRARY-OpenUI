package de.m_marvin.voxelengine.physicengine.d3.univec;

import javax.vecmath.Vector3f;

import com.bulletphysics.collision.shapes.CollisionShape;
import com.bulletphysics.linearmath.MotionState;
import com.bulletphysics.linearmath.Transform;

import de.m_marvin.unimat.MatUtil;
import de.m_marvin.unimat.impl.Matrix4f;
import de.m_marvin.unimat.impl.Quaternionf;
import de.m_marvin.univec.impl.Vec3f;

public class UniVecHelper {
	
	public static Vec3f calculateInertia(CollisionShape shape, float mass) {
		Vector3f v = new Vector3f();
		shape.calculateLocalInertia(mass, v);
		return new Vec3f(v.x, v.y, v.z);
	}
	
	public static SimplifiedRigidBody rigidBody(float mass, MotionState motionState, CollisionShape shape, Vec3f inertia) {
		return new SimplifiedRigidBody(mass, motionState, shape, inertia);
	}

	public static Transform transform(Vec3f translation, Quaternionf rotation) {
		return transform(MatUtil.translateMatrixF(translation).mul(rotation));
	}

	public static Transform transform(Vec3f translation) {
		return transform(MatUtil.translateMatrixF(translation));
	}
	
	public static Transform transform(Matrix4f matrix) {
		return new Transform(new javax.vecmath.Matrix4f(
				matrix.m00(), matrix.m01(), matrix.m02(), matrix.m03(),
				matrix.m10(), matrix.m11(), matrix.m12(), matrix.m13(),
				matrix.m20(), matrix.m21(), matrix.m22(), matrix.m23(),
				matrix.m30(), matrix.m31(), matrix.m32(), matrix.m33()
		));
	}
	
}
