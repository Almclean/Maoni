package com.maoni;

import org.jblas.FloatMatrix;

import com.maoni.matrix.MatrixUtil;
public class MatrixPlay {

	public static void main(String[] args) {

    	FloatMatrix i = MatrixUtil.INSTANCE.genIdentityMatrix4f();
		FloatMatrix j = MatrixUtil.INSTANCE.genIdentityMatrix4f();
		FloatMatrix k = MatrixUtil.INSTANCE.genIdentityMatrix4f();
		FloatMatrix l = MatrixUtil.INSTANCE.genRotateMatrix(0.0f, 0.0f, 1.0f, 0.01f);
		
		float[] vecArray = {0.0f, 0.5f, 0.0f, 1.0f};
		FloatMatrix m = new FloatMatrix(vecArray);
		MatrixUtil.INSTANCE.printMatrix(i.mmul(l).mmul(m));
		
//		MatrixUtil.INSTANCE.printMatrix(MatrixUtil.INSTANCE.Multiply(i, j, k, l));
	}

}
