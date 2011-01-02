package com.maoni.matrix;

import org.jblas.FloatMatrix;
import org.jblas.Geometry;

public enum MatrixUtil {
	INSTANCE;
	
	public FloatMatrix genIdentityMatrix4f() {
		return FloatMatrix.eye(4);
	}
	
	public FloatMatrix genTranslateMatrix(float x, float y, float z) {
		float[] vecArray = { x, y, z, 1.0f };
		return Translate(vecArray);
	}

	private FloatMatrix Translate(float[] vecArray) {
		FloatMatrix f = genIdentityMatrix4f();
		f.putColumn(3, new FloatMatrix(vecArray));
		return f;
	}
	
	public FloatMatrix genScaleMatrix(float x, float y, float z) {
		float[] vecArray = { x, y, z, 1.0f };
		return Scale(vecArray);
	}
	
	private FloatMatrix Scale(float[] vecArray) {
		return FloatMatrix.diag(new FloatMatrix(vecArray));
	}
	
	public FloatMatrix genRotationMatrix(float x, float y, float z, float angle) {
		float mag, s, c;
		float xx, yy, zz, xy, yz, zx, xs, ys, zs, one_c;
		
		float radAngle = (float) Math.toRadians(angle);
		s = (float) Math.sin(radAngle);
		c = (float) Math.cos(radAngle);

		mag = (float) Math.sqrt( x*x + y*y + z*z );

		// Identity matrix
		if (mag == 0.0f) {
			return genIdentityMatrix4f();
		}

		// Rotation matrix is normalized
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
		
		FloatMatrix M = genIdentityMatrix4f();
		
		M.put(0, 0,(one_c * xx) + c);
		M.put(0, 1, (one_c * xy) - zs);
		M.put(0, 2, (one_c * zx) + ys);
		M.put(0, 3, 0.0f);

		M.put(1, 0, (one_c * xy) + zs);
		M.put(1, 1, (one_c * yy) + c);
		M.put(1, 2, (one_c * yz) - xs);
		M.put(1, 3, 0.0f);

		M.put(2, 0, (one_c * zx) - ys);
		M.put(2, 1, (one_c * yz) + xs);
		M.put(2, 2, (one_c * zz) + c);
		M.put(2, 3, 0.0f);

		M.put(3, 0, 0.0f);
		M.put(3, 1, 0.0f);
		M.put(3, 2, 0.0f);
		M.put(3, 3, 1.0f);
		return M;
	}
	
	public FloatMatrix Multiply(FloatMatrix... matrices) {
		FloatMatrix i = genIdentityMatrix4f();
		
		for (FloatMatrix f : matrices) {
			i.mmuli(f);
		}
		return i;
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
