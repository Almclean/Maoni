package com.maoni.matrix;

import java.util.Stack;

import org.jblas.FloatMatrix;

public class MatrixStack {

	private final Stack<FloatMatrix> internalStack;

	public MatrixStack() {
		this.internalStack = new Stack<FloatMatrix>();
		internalStack.push(MatrixUtil.INSTANCE.genIdentityMatrix4f());
	}
	
	public void clear() {
		this.internalStack.removeAllElements();
		this.internalStack.push(MatrixUtil.INSTANCE.genIdentityMatrix4f());
	}

	public void push(final FloatMatrix f) {
		this.internalStack.push(f);
	}

	public void pop() {
		this.internalStack.pop();
	}

	public FloatMatrix getModelViewProjection() {
		FloatMatrix[] mats = internalStack.toArray(new FloatMatrix[]{});
		return MatrixUtil.INSTANCE.Multiply(mats);
	}
}

