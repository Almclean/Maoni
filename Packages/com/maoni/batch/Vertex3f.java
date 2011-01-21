package com.maoni.batch;

import org.jblas.FloatMatrix;

public class Vertex3f {
	
	private final FloatMatrix vector = new FloatMatrix(1, 3);
	
	public Vertex3f(final float x, final float y, final float z) {
		this.vector.put(0, x);
		this.vector.put(1, y);
		this.vector.put(2, z);
	}
	
	@Override
	public String toString() {
		return this.vector.toString();
	}
}
