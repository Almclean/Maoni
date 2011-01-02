package com.maoni;

import org.jblas.FloatMatrix;
import com.maoni.matrix.MatrixUtil;
public class MatrixPlay {

	public static void main(String[] args) {
		FloatMatrix f = MatrixUtil.INSTANCE.genIdentityMatrix4f();
		long counter = 0;
		long start = System.nanoTime();
		while (counter < 100000) {
			MatrixUtil.INSTANCE.genScaleMatrix(0.0f, 0.0f, 0.5f);
			counter++;
		}
		long end = System.nanoTime();
		System.out.println("Performed " + counter + " scales in " + ((end - start) / 10e6) + " seconds");
		MatrixUtil.INSTANCE.printMatrix(MatrixUtil.INSTANCE.genScaleMatrix(0.0f, 2.0f, 0.0f));
	}

}
