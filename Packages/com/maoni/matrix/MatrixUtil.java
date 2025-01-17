package com.maoni.matrix;

import java.nio.FloatBuffer;

import org.jblas.FloatMatrix;
import org.jblas.Geometry;
import org.lwjgl.BufferUtils;

public enum MatrixUtil {
	INSTANCE;
	
	public FloatMatrix genIdentityMatrix4f() {
		return FloatMatrix.eye(4);
	}
	
	public FloatMatrix genTranslateMatrix(final float x, final float y, final float z) {
		float[] vecArray = { x, y, z, 1.0f };
		return Translate(vecArray);
	}

	private FloatMatrix Translate(final float[] vecArray) {
		FloatMatrix f = genIdentityMatrix4f();
		f.putColumn(3, new FloatMatrix(vecArray));
		return f;
	}
	
	public FloatMatrix genScaleMatrix(final float x, float y, float z) {
		float[] vecArray = { x, y, z, 1.0f };
		return Scale(vecArray);
	}
	
	private FloatMatrix Scale(final float[] vecArray) {
		return FloatMatrix.diag(new FloatMatrix(vecArray));
	}
	
	public FloatMatrix genRotationMatrix(float x, float y, float z, float angle) {
		float mag, s, c;
		float xx, yy, zz, xy, yz, zx, xs, ys, zs, one_c;
		
		float radAngle = (float) Math.toRadians(angle);
		s = (float) Math.sin(radAngle);
		c = (float) Math.cos(radAngle);

		mag = (float) Math.sqrt( x*x + y*y + z*z );

		// Identity matrix if no magnitude specified.
		if (mag == 0.0f) {
			return genIdentityMatrix4f();
		}

		// Normalise the input rotation axis.
		x /= mag;
		y /= mag;
		z /= mag;

		xx = x * x;
		yy = y * y;
		zz = z * z;
		xy = x * y;
		yz = y * z;
		zx = z * x;
		xs = x * s;
		ys = y * s;
		zs = z * s;
		one_c = 1.0f - c;
		
		FloatMatrix rotMatrix = genIdentityMatrix4f();
		
		rotMatrix.put(0, 0,(one_c * xx) + c);
		rotMatrix.put(0, 1, (one_c * xy) - zs);
		rotMatrix.put(0, 2, (one_c * zx) + ys);
		rotMatrix.put(0, 3, 0.0f);

		rotMatrix.put(1, 0, (one_c * xy) + zs);
		rotMatrix.put(1, 1, (one_c * yy) + c);
		rotMatrix.put(1, 2, (one_c * yz) - xs);
		rotMatrix.put(1, 3, 0.0f);

		rotMatrix.put(2, 0, (one_c * zx) - ys);
		rotMatrix.put(2, 1, (one_c * yz) + xs);
		rotMatrix.put(2, 2, (one_c * zz) + c);
		rotMatrix.put(2, 3, 0.0f);

		rotMatrix.put(3, 0, 0.0f);
		rotMatrix.put(3, 1, 0.0f);
		rotMatrix.put(3, 2, 0.0f);
		rotMatrix.put(3, 3, 1.0f);
		return rotMatrix;
	}
	
	public FloatMatrix Multiply(final FloatMatrix... matrices) {
		FloatMatrix i = genIdentityMatrix4f();
		
		for (FloatMatrix f : matrices) {
			i.mmuli(f);
		}
		return i;
	}
	
	public FloatBuffer genBuffer(final FloatMatrix f) {
		final FloatBuffer fb = BufferUtils.createFloatBuffer(16);
		fb.put(f.toArray());
		fb.flip();
		return fb;
	}

	public FloatMatrix Normalise(final FloatMatrix f) {
		return Geometry.normalize(f);
	}

	public void printMatrix(final FloatMatrix f) {
		for (int i = 0; i < f.rows; i++) {
			System.out.print(f.getRow(i) + "\n");
		}
	}
	
}
