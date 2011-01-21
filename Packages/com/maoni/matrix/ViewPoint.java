package com.maoni.matrix;

import org.jblas.FloatMatrix;

public enum ViewPoint {
	
	INSTANCE;
	
	public FloatMatrix getPerspectiveMatrix(final int height, final int width, final float fznear, final float fzfar, final float pov) {
		float frustrumScale = calcFrustrumScale(pov);
		
		FloatMatrix perspectiveMatrix = FloatMatrix.eye(4);
		
		perspectiveMatrix.put(0, 0, frustrumScale * ((float) height / (float) width));
		perspectiveMatrix.put(1, 1, frustrumScale);
		perspectiveMatrix.put(2, 2, (fzfar + fznear) / (fznear - fzfar));
		perspectiveMatrix.put(2, 3, -1.0f);
		perspectiveMatrix.put(3, 2, (2.0f * fznear * fzfar) / (fznear - fzfar));
		
		return perspectiveMatrix;
	}
	
	private float calcFrustrumScale(float f) {
		double radVersion = Math.toRadians(f);
		return (float) (1.0f / Math.tan(radVersion / 2.0f));
	}

}
