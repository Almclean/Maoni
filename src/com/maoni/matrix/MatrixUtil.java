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
	
	public FloatMatrix genRotateMatrix (float x, float y, float z, float angle) {
		float[] vecArray = { x, y, z, 1.0f };
		return Rotate(vecArray, angle);
	}
	
	private FloatMatrix Rotate(float[] vecArray, float angle) {
		FloatMatrix axis = Normalise(new FloatMatrix(vecArray));
		double radVersion = Math.toRadians(angle);
		float fcos = (float) Math.cos(radVersion);
		float fInvCos = 1.0f - fcos;
		float fsin = (float) Math.sin(radVersion);
		float norm[] = axis.toArray();
		
		FloatMatrix i = genIdentityMatrix4f();
		// x vals
		float x0Val = (norm[0] * norm[0]) + ((1 - norm[0] * norm[0]) * fcos); 
		float x1Val = norm[0] * norm[1] * (fInvCos) - (norm[2] * fsin);
		float x2Val = norm[0] * norm[2] * (fInvCos) + (norm[1] * fsin);
		i.put(0, 0, x0Val);
		i.put(0, 1, x1Val);
		i.put(0, 2, x2Val);
		
		// y vals
		float y0Val = norm[0] * norm[1] * (fInvCos) + (norm[2] * fsin);
		float y1Val = (norm[1] * norm[1]) + ((1 - norm[1] * norm[1]) * fcos);
		float y2Val = norm[1] * norm[2] * (fInvCos) - (norm[0] * fsin);
		i.put(1, 0, y0Val);
		i.put(1, 1, y1Val);
		i.put(1, 2, y2Val);
			
	    // z vals
		float z0Val = norm[0] * norm[2] * (fInvCos) - (norm[1] * fsin);
		float z1Val = norm[1] * norm[2] * (fInvCos) - (norm[0] * fsin);
		float z2Val = (norm[2] * norm[2]) + ((1 - norm[2] * norm[2]) * fcos);
		i.put(2, 0, z0Val);
		i.put(2, 1, z1Val);
		i.put(2, 2, z2Val);
			
		return i;
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
